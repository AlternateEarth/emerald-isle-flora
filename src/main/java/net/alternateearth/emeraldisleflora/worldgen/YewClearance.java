package net.alternateearth.emeraldisleflora.worldgen;

/**
 * The walkable-clearance rule for yew canopies, with no Minecraft imports so it is identical on every
 * version/loader and can be unit tested.
 *
 * <p>The rule: a leaf must sit at least {@code clearance} blocks above the ground at its own column, so the
 * {@code clearance} blocks directly under it are free. Leaves, the tree's own logs and air are not ground.
 */
public final class YewClearance {

    /** Tells whether a block at a position lets the scan continue downward (air, leaves, logs, plants). */
    @FunctionalInterface
    public interface Passable {
        boolean test(int x, int y, int z);
    }

    private YewClearance() {}

    /**
     * Scans straight down from just below a leaf. The leaf is too low when ground turns up within
     * {@code clearance} blocks beneath it: the leaf is then fewer than {@code clearance} blocks above ground.
     */
    public static boolean isTooLow(int x, int y, int z, int clearance, Passable passable) {
        for (int i = 1; i <= clearance; i++) {
            if (!passable.test(x, y - i, z)) return true;
        }
        return false;
    }
}
