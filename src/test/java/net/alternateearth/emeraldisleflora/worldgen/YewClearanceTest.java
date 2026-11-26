package net.alternateearth.emeraldisleflora.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import net.alternateearth.emeraldisleflora.worldgen.YewFoliageLayout.Offset;
import org.junit.jupiter.api.Test;

class YewClearanceTest {

    private static final int CLEARANCE = 4;

    @Test
    void flatGroundBoundary() {
        // Ground block at y = 0. A leaf at y = 5 has exactly 4 free blocks under it (y 1 to 4); y = 4 has only 3.
        YewClearance.Passable flat = (x, y, z) -> y > 0;
        assertFalse(YewClearance.isTooLow(0, 5, 0, CLEARANCE, flat));
        assertTrue(YewClearance.isTooLow(0, 4, 0, CLEARANCE, flat));
        assertTrue(YewClearance.isTooLow(0, 1, 0, CLEARANCE, flat));
    }

    @Test
    void zeroClearanceNeverRemovesAnything() {
        assertFalse(YewClearance.isTooLow(0, 1, 0, 0, (x, y, z) -> y > 0));
    }

    /** Builds a whole tree on a slope, applies the rule, and checks every surviving leaf independently. */
    private static void checkTreeOnTerrain(IntBinaryOperator ground, String label) {
        YewTrunkLayout.Params trunkShape = new YewTrunkLayout.Params(2, 0, 3, 2, 3, 15, 25);
        YewFoliageLayout.Params canopyShape = new YewFoliageLayout.Params(6, 2, 0, 5, 4, 3, 1, true);
        for (int seed = 0; seed < 25; seed++) {
            Random r = new Random(seed);
            int height = 5 + r.nextInt(3);
            int baseY = ground.applyAsInt(0, 0) + 1;
            YewTrunkLayout.Result trunk = YewTrunkLayout.generate(trunkShape, height, r::nextInt);
            Set<Offset> logs = new HashSet<>();
            for (YewTrunkLayout.Block b : trunk.blocks()) logs.add(new Offset(b.x(), baseY + b.y(), b.z()));

            YewTrunkLayout.Node main = trunk.nodes().get(0);
            // Foliage clearance alone is measured from the trunk base, as in the real placer.
            List<Offset> leaves = YewFoliageLayout.generate(canopyShape, true, CLEARANCE - height, r::nextInt, null);

            Set<Offset> leafSet = new HashSet<>();
            for (Offset o : leaves) leafSet.add(new Offset(main.x() + o.dx(), baseY + main.y() + o.dy(), main.z() + o.dz()));

            // Terrain, own logs and air/leaves: only terrain blocks the scan.
            YewClearance.Passable passable = (x, y, z) -> y > ground.applyAsInt(x, z);
            int removed = 0;
            for (Offset leaf : leafSet) {
                boolean tooLow = YewClearance.isTooLow(leaf.dx(), leaf.dy(), leaf.dz(), CLEARANCE, passable);
                boolean actuallyLow = leaf.dy() - ground.applyAsInt(leaf.dx(), leaf.dz()) - 1 < CLEARANCE;
                assertEquals(actuallyLow, tooLow, label + " leaf " + leaf + " seed " + seed);
                if (tooLow) removed++;
            }
            if (label.equals("flat")) assertEquals(0, removed, "flat ground should need no trimming, seed " + seed);
        }
    }

    @Test
    void flatGroundNeedsNoTrimmingWhenTheFoliageHonoursClearance() {
        checkTreeOnTerrain((x, z) -> 0, "flat");
    }

    @Test
    void ruleMatchesIndependentCheckOnASlope() {
        checkTreeOnTerrain((x, z) -> Math.floorDiv(x, 2), "slope");
    }

    @Test
    void slopeAddsLeavesToTrimComparedToFlat() {
        YewFoliageLayout.Params canopyShape = new YewFoliageLayout.Params(6, 2, 0, 5, 4, 3, 1, true);
        int height = 6;
        List<Offset> leaves = YewFoliageLayout.generate(canopyShape, true, CLEARANCE - height, new Random(3)::nextInt, null);
        IntBinaryOperator slope = (x, z) -> Math.floorDiv(x, 2);
        int tooLow = 0;
        for (Offset o : leaves) {
            int y = height + o.dy();
            if (YewClearance.isTooLow(o.dx(), y, o.dz(), CLEARANCE, (x, yy, z) -> yy > slope.applyAsInt(x, z))) tooLow++;
        }
        assertTrue(tooLow > 0, "an uphill canopy edge must have leaves that need trimming");
    }
}
