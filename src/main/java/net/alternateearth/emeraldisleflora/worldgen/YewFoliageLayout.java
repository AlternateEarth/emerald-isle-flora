package net.alternateearth.emeraldisleflora.worldgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntUnaryOperator;

/**
 * Pure-geometry core of {@link YewFoliagePlacer}: decides where the leaves of one foliage node go, using only
 * integers and doubles. It has no Minecraft imports, so it is identical on every version/loader and can be
 * unit tested without launching the game.
 *
 * <p>A canopy is one main ellipsoid plus a ring of smaller lobes, so the outline is clustered instead of a
 * symmetric blob. Leaves at the outer edge may hang down (droop). Offsets are relative to the foliage node.
 */
public final class YewFoliageLayout {

    /** Vanilla leaves with no log within this many steps (through connected leaves) decay. */
    public static final int MAX_LEAF_DISTANCE = 6;

    public record Offset(int dx, int dy, int dz) {}

    /** Tells whether a trunk block sits at an offset from the node. */
    @FunctionalInterface
    public interface LogTest {
        boolean isLog(int dx, int dy, int dz);
    }

    /**
     * @param radius      horizontal radius of the main ellipsoid
     * @param height      vertical radius of the main ellipsoid
     * @param centerY     vertical offset of the ellipsoid centre from the node (negative is lower)
     * @param lobes       number of extra lobes around the main ellipsoid
     * @param lobeSpread  farthest a lobe centre may sit from the main centre, in blocks
     * @param lobeRadius  horizontal radius of each lobe
     * @param droop       most blocks a leaf on the outer edge may hang below its column
     * @param flatTop     cut the canopy off flat above its middle
     */
    public record Params(int radius, int height, int centerY, int lobes, int lobeSpread, int lobeRadius,
                         int droop, boolean flatTop) {
        /** Values are clamped into a workable shape instead of rejected, so bad data degrades a tree. */
        public Params {
            radius = Math.max(1, radius);
            height = Math.max(1, height);
            lobes = Math.max(0, lobes);
            lobeSpread = Math.max(0, lobeSpread);
            lobeRadius = Math.max(1, lobeRadius);
            droop = Math.max(0, droop);
        }
    }

    private YewFoliageLayout() {}

    /**
     * @param doubleTrunk whether the node sits on a 2x2 trunk, which puts the canopy centre half a block off
     * @param minDy       lowest allowed leaf, relative to the node; leaves below it are not placed
     * @param nextInt     returns a uniform value in {@code [0, bound)}
     * @param logs        trunk block test used to drop leaves that would decay, or null to skip that step
     */
    public static List<Offset> generate(Params p, boolean doubleTrunk, int minDy, IntUnaryOperator nextInt,
                                        LogTest logs) {
        double cx = doubleTrunk ? 0.5 : 0;
        double cz = cx;
        double cy = p.centerY();
        Set<Offset> leaves = new LinkedHashSet<>();

        addEllipsoid(leaves, cx, cy, cz, p.radius() + 0.5, p.height() + 0.5, nextInt);

        if (p.lobes() > 0 && p.lobeSpread() > 0) {
            int lobeHeight = Math.max(1, (p.height() * p.lobeRadius() + p.radius() - 1) / p.radius());
            double jitter = nextInt.applyAsInt(100) / 100.0;
            for (int i = 0; i < p.lobes(); i++) {
                double angle = 2 * Math.PI * (i + jitter) / p.lobes();
                int reach = Math.max(1, p.lobeSpread() / 2 + nextInt.applyAsInt(p.lobeSpread() / 2 + 1));
                double lx = cx + Math.round(Math.cos(angle) * reach);
                double lz = cz + Math.round(Math.sin(angle) * reach);
                double ly = cy + nextInt.applyAsInt(3) - 1;
                addEllipsoid(leaves, lx, ly, lz, p.lobeRadius() + 0.5, lobeHeight + 0.5, nextInt);
            }
        }

        if (p.flatTop()) {
            int limit = p.centerY() + Math.max(1, p.height() / 2);
            leaves.removeIf(o -> o.dy() > limit);
        }
        if (p.droop() > 0) addDroop(leaves, cx, cz, p.droop(), nextInt);

        leaves.removeIf(o -> o.dy() < minDy);
        if (logs != null) pruneDecaying(leaves, logs);
        return new ArrayList<>(leaves);
    }

    private static void addEllipsoid(Set<Offset> out, double cx, double cy, double cz, double ra, double rb,
                                     IntUnaryOperator nextInt) {
        int rx = (int) Math.ceil(ra), ry = (int) Math.ceil(rb);
        for (int dx = (int) Math.floor(cx) - rx; dx <= (int) Math.ceil(cx) + rx; dx++) {
            for (int dy = (int) Math.floor(cy) - ry; dy <= (int) Math.ceil(cy) + ry; dy++) {
                for (int dz = (int) Math.floor(cz) - rx; dz <= (int) Math.ceil(cz) + rx; dz++) {
                    double ux = (dx - cx) / ra, uy = (dy - cy) / rb, uz = (dz - cz) / ra;
                    double d = ux * ux + uy * uy + uz * uz;
                    if (d > 1) continue;
                    // Ragged edge: the outer shell loses some leaves so the outline is not a smooth ball.
                    if (d > 0.72 && nextInt.applyAsInt(100) < 30) continue;
                    out.add(new Offset(dx, dy, dz));
                }
            }
        }
    }

    private static void addDroop(Set<Offset> leaves, double cx, double cz, int droop, IntUnaryOperator nextInt) {
        Map<Long, Integer> lowest = new HashMap<>();
        double maxReach = 0;
        for (Offset o : leaves) {
            lowest.merge(key(o.dx(), o.dz()), o.dy(), Math::min);
            maxReach = Math.max(maxReach, Math.hypot(o.dx() - cx, o.dz() - cz));
        }
        List<Offset> extra = new ArrayList<>();
        for (Map.Entry<Long, Integer> e : lowest.entrySet()) {
            int dx = (int) (e.getKey() >> 32), dz = (int) (long) e.getKey();
            if (Math.hypot(dx - cx, dz - cz) <= maxReach * 0.6) continue;
            if (nextInt.applyAsInt(100) >= 50) continue;
            int hang = 1 + nextInt.applyAsInt(droop);
            for (int i = 1; i <= hang; i++) extra.add(new Offset(dx, e.getValue() - i, dz));
        }
        leaves.addAll(extra);
    }

    /** Drops leaves that vanilla would decay: more than {@link #MAX_LEAF_DISTANCE} steps from a log. */
    private static void pruneDecaying(Set<Offset> leaves, LogTest logs) {
        if (leaves.isEmpty()) return;
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE;
        for (Offset o : leaves) {
            minX = Math.min(minX, o.dx()); maxX = Math.max(maxX, o.dx());
            minY = Math.min(minY, o.dy()); maxY = Math.max(maxY, o.dy());
            minZ = Math.min(minZ, o.dz()); maxZ = Math.max(maxZ, o.dz());
        }
        List<Offset> found = new ArrayList<>();
        for (int x = minX - 1; x <= maxX + 1; x++) {
            for (int y = minY - 1; y <= maxY + 1; y++) {
                for (int z = minZ - 1; z <= maxZ + 1; z++) {
                    if (logs.isLog(x, y, z)) found.add(new Offset(x, y, z));
                }
            }
        }
        leaves.retainAll(YewLeafDistance.reachable(found, leaves));
    }

    private static long key(int dx, int dz) {
        return ((long) dx << 32) | (dz & 0xFFFFFFFFL);
    }
}
