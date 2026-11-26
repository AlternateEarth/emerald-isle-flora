package net.alternateearth.emeraldisleflora.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.alternateearth.emeraldisleflora.worldgen.YewFoliageLayout.Offset;
import net.alternateearth.emeraldisleflora.worldgen.YewTreeShapes.Shape;
import org.junit.jupiter.api.Test;

/** The real shape values (what the tree configs use): heights, shares, and trunk plus canopy on flat ground. */
class YewTreeGeometryTest {

    private static final int CLEARANCE = YewTreeShapes.CLEARANCE;

    @Test
    void trunkHeightRangesMatchTheSpec() {
        assertRange(YewTreeShapes.TALL_NARROW, 10, 14);
        assertRange(YewTreeShapes.TWISTED, 7, 10);
        assertRange(YewTreeShapes.SLIM_WIDE_LOW, 6, 8);
        assertRange(YewTreeShapes.WIDE_LOW, 5, 7);
        assertRange(YewTreeShapes.DENSE, 9, 13);
    }

    private static void assertRange(Shape s, int min, int max) {
        assertEquals(min, s.minHeight(), s.name());
        assertEquals(max, s.maxHeight(), s.name());
    }

    @Test
    void trunkSizesMatchTheSpec() {
        assertEquals(1, YewTreeShapes.TALL_NARROW.trunk().thickness());
        assertEquals(1, YewTreeShapes.TWISTED.trunk().thickness());
        assertEquals(1, YewTreeShapes.SLIM_WIDE_LOW.trunk().thickness());
        assertEquals(2, YewTreeShapes.WIDE_LOW.trunk().thickness());
        assertEquals(2, YewTreeShapes.DENSE.trunk().thickness());
        assertTrue(YewTreeShapes.TWISTED.trunk().twist() > 0, "twisted trunks bend");
    }

    @Test
    void denseIsAboutNinetyPercentOfTallNarrowHeight() {
        double tall = (YewTreeShapes.TALL_NARROW.minHeight() + YewTreeShapes.TALL_NARROW.maxHeight()) / 2.0;
        double dense = (YewTreeShapes.DENSE.minHeight() + YewTreeShapes.DENSE.maxHeight()) / 2.0;
        assertEquals(0.9, dense / tall, 0.04);
    }

    @Test
    void singleSaplingSharesAreTheFinalSharesInTheSpec() {
        assertEquals(0.486, YewTreeShapes.tallNarrowShare(), 0.001);
        assertEquals(0.364, YewTreeShapes.slimWideLowShare(), 0.001);
        assertEquals(0.15, YewTreeShapes.TWISTED_SHARE, 0.0001);
        assertEquals(1.0, YewTreeShapes.tallNarrowShare() + YewTreeShapes.slimWideLowShare() + YewTreeShapes.TWISTED_SHARE, 0.0001);
        // The 40:30 ratio between tall & narrow and slim wide & low is kept.
        assertEquals(4.0 / 3.0, YewTreeShapes.tallNarrowShare() / YewTreeShapes.slimWideLowShare(), 0.001);
    }

    @Test
    void sequentialSelectorChancesProduceTheFinalShares() {
        // Mirrors how the random selector applies entries in order: twisted first, then tall & narrow, then the default.
        Random r = new Random(11);
        int n = 200_000, twisted = 0, tall = 0, slim = 0;
        for (int i = 0; i < n; i++) {
            if (r.nextFloat() < YewTreeShapes.TWISTED_SHARE) twisted++;
            else if (r.nextFloat() < YewTreeShapes.TALL_NARROW_OF_REMAINDER) tall++;
            else slim++;
        }
        assertEquals(0.15, twisted / (double) n, 0.005);
        assertEquals(YewTreeShapes.tallNarrowShare(), tall / (double) n, 0.005);
        assertEquals(YewTreeShapes.slimWideLowShare(), slim / (double) n, 0.005);
    }

    @Test
    void mainCanopyNeverComesWithinClearanceOfTheGround() {
        for (Shape s : YewTreeShapes.ALL) {
            for (int height = s.minHeight(); height <= s.maxHeight(); height++) {
                for (int seed = 0; seed < 25; seed++) {
                    Random r = new Random(seed);
                    YewTrunkLayout.Result trunk = YewTrunkLayout.generate(s.trunk(), height, r::nextInt);
                    Set<Offset> logs = new HashSet<>();
                    for (YewTrunkLayout.Block b : trunk.blocks()) logs.add(new Offset(b.x(), b.y(), b.z()));

                    YewTrunkLayout.Node main = trunk.nodes().get(0);
                    List<Offset> leaves = YewFoliageLayout.generate(
                        s.canopy(), main.doubleTrunk(), CLEARANCE - height, r::nextInt,
                        (dx, dy, dz) -> logs.contains(new Offset(main.x() + dx, main.y() + dy, main.z() + dz)));

                    assertFalse(leaves.isEmpty(), s.name() + " grew no leaves, height " + height + " seed " + seed);
                    for (Offset o : leaves) {
                        int y = main.y() + o.dy();
                        assertTrue(y >= CLEARANCE, s.name() + " leaf at y=" + y + ", height " + height + " seed " + seed);
                    }
                }
            }
        }
    }

    @Test
    void everyShapeKeepsLeavesAboveItsTrunkTop() {
        for (Shape s : YewTreeShapes.ALL) {
            Random r = new Random(7);
            int height = s.maxHeight();
            YewTrunkLayout.Node main = YewTrunkLayout.generate(s.trunk(), height, r::nextInt).nodes().get(0);
            List<Offset> leaves = YewFoliageLayout.generate(s.canopy(), main.doubleTrunk(), CLEARANCE - height, r::nextInt, null);
            assertTrue(leaves.stream().anyMatch(o -> o.dy() >= 0), s.name() + " has no leaves at or above the node");
        }
    }

    @Test
    void wideShapesAreWiderThanTallAndTallShapesAreTaller() {
        for (Shape s : List.of(YewTreeShapes.SLIM_WIDE_LOW, YewTreeShapes.WIDE_LOW)) {
            assertTrue(s.canopy().radius() * 2 + 1 > s.canopy().height() * 2 + 1, s.name());
            assertTrue(s.canopy().height() >= 3, s.name() + " canopy is too flat");
            assertTrue(!s.canopy().flatTop(), s.name() + " should be domed, not cut flat");
        }
        Shape tall = YewTreeShapes.TALL_NARROW;
        assertTrue(tall.canopy().height() * 2 + 1 > tall.canopy().radius() * 2 + 1);
    }
}
