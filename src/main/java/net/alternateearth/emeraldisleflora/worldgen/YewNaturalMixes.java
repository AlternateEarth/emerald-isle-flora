package net.alternateearth.emeraldisleflora.worldgen;

import java.util.List;
import net.alternateearth.emeraldisleflora.worldgen.YewTreeShapes.Shape;

/**
 * The shape mixes and rarities of naturally generated yew trees, in one place. The tree configs read these
 * values and the tests check them. It has no Minecraft imports.
 *
 * <p>Each habitat picks a shape with the stated final shares. The vanilla-style random selector checks its
 * entries in order and applies each chance to what the earlier entries left, so {@link Mix#chances()} derives
 * those sequential chances. The last entry is the selector's default and needs no chance.
 *
 * <p>Shares are per placement attempt. A 2x2 shape needs more room than a 1x1 shape, so in tight spots it fails
 * more often and its share of the trees actually placed is a little lower.
 *
 * <p>The numbers are starting values to tune by eye in a world.
 */
public final class YewNaturalMixes {

    private YewNaturalMixes() {}

    public record Entry(Shape shape, float share) {}

    public record Mix(String name, List<Entry> entries) {

        public Mix {
            entries = List.copyOf(entries);
            float sum = 0;
            for (Entry e : entries) sum += e.share();
            if (entries.isEmpty() || Math.abs(sum - 1f) > 1e-4f) {
                throw new IllegalArgumentException(name + " shares must add up to 1, got " + sum);
            }
        }

        /** The chance for every entry but the last, each applied to what the earlier entries left. */
        public float[] chances() {
            float[] out = new float[entries.size() - 1];
            float left = 1f;
            for (int i = 0; i < out.length; i++) {
                out[i] = entries.get(i).share() / left;
                left -= entries.get(i).share();
            }
            return out;
        }

        public Shape defaultShape() {
            return entries.get(entries.size() - 1).shape();
        }
    }

    /** Forest: scattered understorey trees among other trees, so mostly upright. */
    public static final Mix WOODLAND = new Mix("woodland", List.of(
        new Entry(YewTreeShapes.TALL_NARROW, 0.65f),
        new Entry(YewTreeShapes.TWISTED, 0.20f),
        new Entry(YewTreeShapes.SLIM_WIDE_LOW, 0.15f)));

    /** Dark Forest and its groves: shaded and dense. */
    public static final Mix DARK_WOODLAND = new Mix("dark_woodland", List.of(
        new Entry(YewTreeShapes.DENSE, 0.50f),
        new Entry(YewTreeShapes.TALL_NARROW, 0.25f),
        new Entry(YewTreeShapes.TWISTED, 0.25f)));

    /** Windswept biomes: crags and thin rocky soil, so twisted and low. */
    public static final Mix ROCKY = new Mix("rocky", List.of(
        new Entry(YewTreeShapes.TWISTED, 0.50f),
        new Entry(YewTreeShapes.SLIM_WIDE_LOW, 0.35f),
        new Entry(YewTreeShapes.TALL_NARROW, 0.15f)));

    /** Plains: a lone open-grown tree, wide and low. */
    public static final Mix OPEN = new Mix("open", List.of(
        new Entry(YewTreeShapes.WIDE_LOW, 0.60f),
        new Entry(YewTreeShapes.SLIM_WIDE_LOW, 0.40f)));

    public static final List<Mix> ALL = List.of(WOODLAND, DARK_WOODLAND, ROCKY, OPEN);

    // Rarity, as placement attempts. "Rarity" is one attempt per N chunks on average.
    public static final int FOREST_RARITY = 6;
    /** Dark Forest: 2 attempts per chunk, each kept one time in 3 (so 2/3 of an attempt per chunk). */
    public static final int DARK_FOREST_COUNT = 2;
    public static final int DARK_FOREST_RARITY = 3;
    public static final int WINDSWEPT_RARITY = 6;
    public static final int PLAINS_RARITY = 32;

    /** Groves: one per this many Dark Forest chunks, with 5 to 9 trees within 8 blocks of a centre. */
    public static final int GROVE_RARITY = 36;
    public static final int GROVE_MIN_TREES = 5;
    public static final int GROVE_MAX_TREES = 9;
    public static final int GROVE_SPREAD = 8;
}
