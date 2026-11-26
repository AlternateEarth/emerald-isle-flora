package net.alternateearth.emeraldisleflora.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.alternateearth.emeraldisleflora.worldgen.YewTrunkLayout.Block;
import net.alternateearth.emeraldisleflora.worldgen.YewTrunkLayout.Kind;
import net.alternateearth.emeraldisleflora.worldgen.YewTrunkLayout.Params;
import net.alternateearth.emeraldisleflora.worldgen.YewTrunkLayout.Result;
import org.junit.jupiter.api.Test;

class YewTrunkLayoutTest {

    private static Result gen(Params p, int height, long seed) {
        Random r = new Random(seed);
        return YewTrunkLayout.generate(p, height, r::nextInt);
    }

    private static Set<String> keys(Result res) {
        Set<String> s = new HashSet<>();
        for (Block b : res.blocks()) s.add(b.x() + "," + b.y() + "," + b.z());
        return s;
    }

    @Test
    void twoByTwoTrunkOccupiesFourColumnsAtEveryLevel() {
        Params p = new Params(2, 0, 0, 1, 1, 15, 25);
        Result res = gen(p, 9, 1);
        Set<String> k = keys(res);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 2; x++) {
                for (int z = 0; z < 2; z++) assertTrue(k.contains(x + "," + y + "," + z), x + "," + y + "," + z);
            }
        }
        assertEquals(36, res.blocks().size());
    }

    @Test
    void trunkMixesLogStrippedLogAndWood() {
        Params p = new Params(2, 0, 0, 1, 1, 15, 25);
        Set<Kind> seen = EnumSet.noneOf(Kind.class);
        for (int seed = 0; seed < 20; seed++) {
            for (Block b : gen(p, 9, seed).blocks()) seen.add(b.kind());
        }
        assertEquals(EnumSet.allOf(Kind.class), seen);
    }

    @Test
    void lowestLevelsAreAlwaysPlainLog() {
        Params p = new Params(2, 0, 0, 1, 1, 40, 60);
        for (int seed = 0; seed < 50; seed++) {
            for (Block b : gen(p, 9, seed).blocks()) {
                if (b.y() < 2) assertEquals(Kind.LOG, b.kind());
            }
        }
    }

    @Test
    void straightOneByOneTrunkIsAColumn() {
        Result res = gen(new Params(1, 0, 0, 1, 1, 0, 0), 10, 3);
        assertEquals(10, res.blocks().size());
        for (Block b : res.blocks()) {
            assertEquals(0, b.x());
            assertEquals(0, b.z());
        }
    }

    @Test
    void twistedTrunkLeavesItsBaseColumn() {
        boolean everLeaves = true;
        for (int seed = 0; seed < 30; seed++) {
            Result res = gen(new Params(1, 2, 0, 1, 1, 0, 0), 9, seed);
            boolean leaves = res.blocks().stream().anyMatch(b -> b.x() != 0 || b.z() != 0);
            everLeaves &= leaves;
        }
        assertTrue(everLeaves, "every twisted trunk should drift off its base column");
    }

    @Test
    void everyBlockIsFaceConnectedToTheTrunk() {
        Params[] all = {
            new Params(1, 2, 0, 1, 1, 10, 10),
            new Params(1, 0, 2, 2, 3, 10, 10),
            new Params(2, 0, 3, 2, 3, 15, 25),
            new Params(2, 1, 0, 1, 1, 15, 25),
        };
        for (Params p : all) {
            for (int seed = 0; seed < 40; seed++) {
                Result res = gen(p, 8, seed);
                Set<String> k = keys(res);
                for (Block b : res.blocks()) {
                    if (b.y() == 0) continue;
                    boolean connected = k.contains(b.x() + "," + (b.y() - 1) + "," + b.z())
                        || k.contains((b.x() + 1) + "," + b.y() + "," + b.z())
                        || k.contains((b.x() - 1) + "," + b.y() + "," + b.z())
                        || k.contains(b.x() + "," + b.y() + "," + (b.z() + 1))
                        || k.contains(b.x() + "," + b.y() + "," + (b.z() - 1));
                    assertTrue(connected, "floating block at " + b + " for " + p + " seed " + seed);
                }
            }
        }
    }

    @Test
    void mainNodeSitsOnTopOfTheTrunkAndFlagsDoubleTrunk() {
        Result thin = gen(new Params(1, 0, 0, 1, 1, 0, 0), 10, 1);
        assertEquals(10, thin.nodes().get(0).y());
        assertFalse(thin.nodes().get(0).doubleTrunk());
        Result thick = gen(new Params(2, 0, 0, 1, 1, 0, 0), 7, 1);
        assertEquals(7, thick.nodes().get(0).y());
        assertTrue(thick.nodes().get(0).doubleTrunk());
    }

    @Test
    void branchesAddOneNodeEach() {
        Result res = gen(new Params(2, 0, 3, 2, 3, 0, 0), 9, 5);
        assertEquals(4, res.nodes().size());
    }

    @Test
    void branchesStayOutsideTheTrunkFootprint() {
        for (int seed = 0; seed < 40; seed++) {
            Result res = gen(new Params(2, 0, 3, 2, 3, 0, 0), 9, seed);
            for (var n : res.nodes().subList(1, res.nodes().size())) {
                assertTrue(n.x() < 0 || n.x() > 1 || n.z() < 0 || n.z() > 1, "branch tip inside trunk: " + n);
            }
        }
    }

    @Test
    void rejectsUnsupportedThickness() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> new Params(3, 0, 0, 1, 1, 0, 0));
    }

    @Test
    void clampsCrossFieldValuesInsteadOfThrowing() {
        Params p = new Params(1, 0, 0, 5, 2, 80, 60);
        assertEquals(5, p.branchMax());
        assertEquals(60, p.woodPercent());
        assertEquals(40, p.strippedPercent());
    }
}
