package net.alternateearth.emeraldisleflora.worldgen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntUnaryOperator;

/**
 * Pure-geometry core of {@link YewTrunkPlacer}: decides which trunk blocks a yew tree has and where its
 * foliage attaches, using only integers. It has no Minecraft imports, so it is identical on every
 * version/loader (no Stonecutter conditionals) and can be unit tested without launching the game.
 *
 * <p>Coordinates are offsets from the tree origin. For a 2x2 trunk the origin is the north-west corner of
 * the footprint, the same convention as vanilla's giant trunks.
 */
public final class YewTrunkLayout {

    public enum Kind { LOG, STRIPPED_LOG, WOOD }

    public enum Axis { X, Y, Z }

    /** One trunk or branch block. */
    public record Block(int x, int y, int z, Kind kind, Axis axis) {}

    /** A point foliage attaches to, like vanilla's foliage attachment. */
    public record Node(int x, int y, int z, int radiusOffset, boolean doubleTrunk) {}

    public record Result(List<Block> blocks, List<Node> nodes) {}

    /**
     * @param thickness       1 for a 1x1 trunk, 2 for a 2x2 trunk
     * @param twist           how far (in blocks) the trunk spirals away from its base; 0 for a straight trunk
     * @param branches        number of side branches
     * @param branchMin       shortest side branch, in blocks
     * @param branchMax       longest side branch, in blocks
     * @param strippedPercent chance (0 to 100) that a trunk block is stripped yew log
     * @param woodPercent     chance (0 to 100) that a trunk block is yew wood
     */
    public record Params(int thickness, int twist, int branches, int branchMin, int branchMax,
                         int strippedPercent, int woodPercent) {
        /**
         * Thickness must be 1 or 2. The other fields are clamped into a consistent shape instead of rejected,
         * so a bad data-pack value degrades a tree rather than crashing world generation: the longest branch
         * is never shorter than the shortest, and the block percentages never add past 100.
         */
        public Params {
            if (thickness != 1 && thickness != 2) throw new IllegalArgumentException("thickness must be 1 or 2");
            twist = Math.max(0, twist);
            branches = Math.max(0, branches);
            branchMin = Math.max(1, branchMin);
            branchMax = Math.max(branchMin, branchMax);
            woodPercent = Math.max(0, Math.min(100, woodPercent));
            strippedPercent = Math.max(0, Math.min(100 - woodPercent, strippedPercent));
        }
    }

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private YewTrunkLayout() {}

    /**
     * @param nextInt returns a uniform value in {@code [0, bound)}
     */
    public static Result generate(Params p, int height, IntUnaryOperator nextInt) {
        List<Block> blocks = new ArrayList<>();
        Set<Long> taken = new HashSet<>();
        List<Node> nodes = new ArrayList<>();
        int size = p.thickness();

        // Column path: one (x, z) origin per level, moving at most one block along one axis per level.
        int[] cx = new int[height];
        int[] cz = new int[height];
        double phase = p.twist() > 0 ? nextInt.applyAsInt(628) / 100.0 : 0;
        double period = Math.max(6, height);
        for (int y = 1; y < height; y++) {
            cx[y] = cx[y - 1];
            cz[y] = cz[y - 1];
            if (p.twist() == 0) continue;
            double a = 2 * Math.PI * y / period + phase;
            long tx = Math.round(p.twist() * (Math.sin(a) - Math.sin(phase)));
            long tz = Math.round(p.twist() * (Math.cos(a) - Math.cos(phase)));
            long ex = tx - cx[y], ez = tz - cz[y];
            if (Math.abs(ex) >= Math.abs(ez) && ex != 0) cx[y] += Long.signum(ex);
            else if (ez != 0) cz[y] += Long.signum(ez);
        }

        for (int y = 0; y < height; y++) {
            addFootprint(blocks, taken, cx[y], y, cz[y], size, p, nextInt, y >= 2);
            // At a step, keep the previous column one more level so the trunk stays face-connected.
            if (y > 0 && (cx[y] != cx[y - 1] || cz[y] != cz[y - 1])) {
                addFootprint(blocks, taken, cx[y - 1], y, cz[y - 1], size, p, nextInt, false);
            }
        }

        int top = height - 1;
        nodes.add(new Node(cx[top], height, cz[top], 0, size == 2));

        int lowest = Math.max(2, (int) (height * 0.45));
        int highest = Math.max(lowest, height - 2);
        for (int i = 0; i < p.branches(); i++) {
            int y0 = lowest + nextInt.applyAsInt(highest - lowest + 1);
            int[] d = DIRS[nextInt.applyAsInt(4)];
            int length = p.branchMin() + nextInt.applyAsInt(p.branchMax() - p.branchMin() + 1);
            addBranch(blocks, taken, nodes, cx[y0], y0, cz[y0], size, d, length);
        }
        return new Result(blocks, nodes);
    }

    private static void addFootprint(List<Block> blocks, Set<Long> taken, int ox, int y, int oz, int size,
                                     Params p, IntUnaryOperator nextInt, boolean allowVariety) {
        for (int dx = 0; dx < size; dx++) {
            for (int dz = 0; dz < size; dz++) {
                Kind kind = Kind.LOG;
                if (allowVariety) {
                    int r = nextInt.applyAsInt(100);
                    if (r < p.woodPercent()) kind = Kind.WOOD;
                    else if (r < p.woodPercent() + p.strippedPercent()) kind = Kind.STRIPPED_LOG;
                }
                add(blocks, taken, ox + dx, y, oz + dz, kind, Axis.Y);
            }
        }
    }

    private static void addBranch(List<Block> blocks, Set<Long> taken, List<Node> nodes, int ox, int y0, int oz,
                                  int size, int[] d, int length) {
        // Start on the footprint edge facing the branch direction, then step outward from it.
        int x = ox + (d[0] > 0 ? size - 1 : 0);
        int z = oz + (d[1] > 0 ? size - 1 : 0);
        int y = y0;
        Axis axis = d[0] != 0 ? Axis.X : Axis.Z;
        for (int s = 1; s <= length; s++) {
            x += d[0];
            z += d[1];
            if (s % 2 == 0) {
                y++;
                // Rise in place first so consecutive blocks always share a face.
                add(blocks, taken, x - d[0], y, z - d[1], Kind.LOG, Axis.Y);
            }
            add(blocks, taken, x, y, z, Kind.LOG, axis);
        }
        nodes.add(new Node(x, y + 1, z, 0, false));
    }

    private static void add(List<Block> blocks, Set<Long> taken, int x, int y, int z, Kind kind, Axis axis) {
        long key = ((long) (x + 512) << 40) | ((long) (y + 512) << 20) | (z + 512);
        if (taken.add(key)) blocks.add(new Block(x, y, z, kind, axis));
    }
}
