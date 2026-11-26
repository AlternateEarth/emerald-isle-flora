package net.alternateearth.emeraldisleflora.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.alternateearth.emeraldisleflora.worldgen.YewNaturalMixes.Entry;
import net.alternateearth.emeraldisleflora.worldgen.YewNaturalMixes.Mix;
import org.junit.jupiter.api.Test;

class YewNaturalMixesTest {

    /** The share a shape ends up with when the selector applies the derived chances in order. */
    private static float effectiveShare(Mix mix, int index) {
        float[] chances = mix.chances();
        float left = 1f;
        for (int i = 0; i < index; i++) left *= 1f - chances[i];
        return index < chances.length ? left * chances[index] : left;
    }

    @Test
    void derivedChancesGiveTheStatedShares() {
        for (Mix mix : YewNaturalMixes.ALL) {
            for (int i = 0; i < mix.entries().size(); i++) {
                assertEquals(mix.entries().get(i).share(), effectiveShare(mix, i), 1e-4f, mix.name() + " entry " + i);
            }
        }
    }

    @Test
    void chancesMatchTheSpecTable() {
        // Forest: tall & narrow 65%, then twisted 20 of the remaining 35 percent.
        assertEquals(0.65f, YewNaturalMixes.WOODLAND.chances()[0], 1e-4f);
        assertEquals(0.20f / 0.35f, YewNaturalMixes.WOODLAND.chances()[1], 1e-4f);
        assertEquals(YewTreeShapes.SLIM_WIDE_LOW, YewNaturalMixes.WOODLAND.defaultShape());
    }

    @Test
    void sharesAreTheOnesInTheSpec() {
        assertEquals(List.of(0.65f, 0.20f, 0.15f), shares(YewNaturalMixes.WOODLAND));
        assertEquals(List.of(0.50f, 0.25f, 0.25f), shares(YewNaturalMixes.DARK_WOODLAND));
        assertEquals(List.of(0.50f, 0.35f, 0.15f), shares(YewNaturalMixes.ROCKY));
        assertEquals(List.of(0.60f, 0.40f), shares(YewNaturalMixes.OPEN));
    }

    private static List<Float> shares(Mix mix) {
        return mix.entries().stream().map(Entry::share).toList();
    }

    @Test
    void shapesSuitTheirHabitat() {
        // Forest has no 2x2 trees (they would not fit under a canopy); dense appears only in the dark woodland.
        for (Entry e : YewNaturalMixes.WOODLAND.entries()) assertEquals(1, e.shape().trunk().thickness());
        for (Entry e : YewNaturalMixes.ROCKY.entries()) assertEquals(1, e.shape().trunk().thickness());
        for (Mix mix : List.of(YewNaturalMixes.WOODLAND, YewNaturalMixes.ROCKY, YewNaturalMixes.OPEN)) {
            for (Entry e : mix.entries()) assertTrue(e.shape() != YewTreeShapes.DENSE, mix.name());
        }
        assertTrue(YewNaturalMixes.DARK_WOODLAND.entries().stream().anyMatch(e -> e.shape() == YewTreeShapes.DENSE));
        // The open ground mix is the wide shapes only.
        for (Entry e : YewNaturalMixes.OPEN.entries()) {
            assertTrue(e.shape() == YewTreeShapes.WIDE_LOW || e.shape() == YewTreeShapes.SLIM_WIDE_LOW);
        }
    }

    @Test
    void rejectsSharesThatDoNotAddUp() {
        assertThrows(IllegalArgumentException.class,
            () -> new Mix("bad", List.of(new Entry(YewTreeShapes.TWISTED, 0.5f), new Entry(YewTreeShapes.DENSE, 0.4f))));
    }

    @Test
    void groveRangeIsFiveToNine() {
        assertEquals(5, YewNaturalMixes.GROVE_MIN_TREES);
        assertEquals(9, YewNaturalMixes.GROVE_MAX_TREES);
        assertEquals(8, YewNaturalMixes.GROVE_SPREAD);
    }
}
