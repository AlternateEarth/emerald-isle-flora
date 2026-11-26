package net.alternateearth.emeraldisleflora.worldgen;

import java.util.List;
import net.alternateearth.emeraldisleflora.worldgen.YewFoliageLayout.Params;

/**
 * The five yew tree shapes and the shares at which saplings pick them, in one place. The tree configs read
 * these values and the tests check them, so the two cannot drift apart. It has no Minecraft imports.
 *
 * <p>Trunk height is {@code baseHeight + nextInt(heightRandA + 1) + nextInt(heightRandB + 1)}, as for vanilla
 * trunk placers. The numbers are starting values to tune by eye in-game.
 */
public final class YewTreeShapes {

    /** Free blocks required between the ground and the lowest leaf, so a player can walk under a canopy. */
    public static final int CLEARANCE = 4;

    public record Shape(String name, int baseHeight, int heightRandA, int heightRandB,
                        YewTrunkLayout.Params trunk, YewFoliageLayout.Params canopy) {

        public int minHeight() {
            return baseHeight;
        }

        public int maxHeight() {
            return baseHeight + heightRandA + heightRandB;
        }

        /** Vanilla feature-size check: a 2x2 trunk needs room for its second column and a wider margin. */
        public int sizeLowerRadius() {
            return trunk.thickness() == 2 ? 1 : 0;
        }

        public int sizeUpperRadius() {
            return trunk.thickness() == 2 ? 2 : 1;
        }
    }

    // 1x1 trunks, grown from a single sapling.
    public static final Shape TALL_NARROW = new Shape("tall_narrow", 10, 2, 2,
        new YewTrunkLayout.Params(1, 0, 0, 1, 1, 0, 0),
        new Params(3, 4, -3, 0, 0, 1, 0, false));

    public static final Shape TWISTED = new Shape("twisted", 7, 2, 1,
        new YewTrunkLayout.Params(1, 2, 0, 1, 1, 0, 0),
        new Params(3, 3, -1, 3, 3, 2, 1, false));

    public static final Shape SLIM_WIDE_LOW = new Shape("slim_wide_low", 6, 1, 1,
        new YewTrunkLayout.Params(1, 0, 2, 2, 3, 0, 0),
        new Params(4, 3, 1, 4, 3, 3, 1, false));

    // 2x2 trunks, grown from four saplings in a square.
    public static final Shape WIDE_LOW = new Shape("wide_low", 5, 1, 1,
        new YewTrunkLayout.Params(2, 0, 3, 2, 3, 0, 0),
        new Params(5, 3, 1, 5, 4, 3, 1, false));

    public static final Shape DENSE = new Shape("dense", 9, 2, 2,
        new YewTrunkLayout.Params(2, 0, 1, 2, 2, 0, 0),
        new Params(4, 4, -2, 6, 3, 3, 1, false));

    public static final List<Shape> ALL = List.of(TALL_NARROW, TWISTED, SLIM_WIDE_LOW, WIDE_LOW, DENSE);

    /** Share of single-sapling trees that are twisted. */
    public static final float TWISTED_SHARE = 0.15f;

    /**
     * Tall & narrow's chance among the single-sapling trees that are not twisted. 4/7 keeps the 40:30 ratio
     * between tall & narrow and slim wide & low, giving shares of 48.6%, 36.4% and 15%.
     */
    public static final float TALL_NARROW_OF_REMAINDER = 4f / 7f;

    /** Share of 2x2 trees that are wide & low; the rest are dense. */
    public static final float WIDE_LOW_SHARE = 0.5f;

    /** Final share of single-sapling trees that are tall & narrow. */
    public static float tallNarrowShare() {
        return (1 - TWISTED_SHARE) * TALL_NARROW_OF_REMAINDER;
    }

    /** Final share of single-sapling trees that are slim wide & low (the selector's default). */
    public static float slimWideLowShare() {
        return (1 - TWISTED_SHARE) * (1 - TALL_NARROW_OF_REMAINDER);
    }

    private YewTreeShapes() {}
}
