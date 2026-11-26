package net.alternateearth.emeraldisleflora.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.alternateearth.emeraldisleflora.worldgen.YewFoliageLayout.LogTest;
import net.alternateearth.emeraldisleflora.worldgen.YewFoliageLayout.Offset;
import net.alternateearth.emeraldisleflora.worldgen.YewFoliageLayout.Params;
import org.junit.jupiter.api.Test;

class YewFoliageLayoutTest {

    private static final int NO_FLOOR = -1000;

    private static List<Offset> gen(Params p, boolean doubleTrunk, int minDy, long seed, LogTest logs) {
        Random r = new Random(seed);
        return YewFoliageLayout.generate(p, doubleTrunk, minDy, r::nextInt, logs);
    }

    /** A straight trunk column at the origin, running down from just below the node. */
    private static LogTest straightTrunk(boolean thick, int length) {
        return (x, y, z) -> y < 0 && y >= -length && x >= 0 && x < (thick ? 2 : 1) && z >= 0 && z < (thick ? 2 : 1);
    }

    private static int width(List<Offset> leaves) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (Offset o : leaves) {
            min = Math.min(min, Math.min(o.dx(), o.dz()));
            max = Math.max(max, Math.max(o.dx(), o.dz()));
        }
        return max - min + 1;
    }

    private static int tall(List<Offset> leaves) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (Offset o : leaves) {
            min = Math.min(min, o.dy());
            max = Math.max(max, o.dy());
        }
        return max - min + 1;
    }

    private static final Params TALL_NARROW = new Params(2, 4, -2, 0, 0, 1, 0, false);
    private static final Params WIDE_LOW = new Params(5, 2, -1, 4, 4, 3, 1, true);
    private static final Params DENSE = new Params(4, 3, -2, 6, 3, 3, 1, false);

    @Test
    void neverPlacesLeavesBelowTheMinimum() {
        for (int seed = 0; seed < 40; seed++) {
            for (Params p : new Params[] {TALL_NARROW, WIDE_LOW, DENSE}) {
                for (Offset o : gen(p, false, -3, seed, null)) assertTrue(o.dy() >= -3, o + " seed " + seed);
            }
        }
    }

    @Test
    void withoutAFloorTheCanopyReachesBelowTheNode() {
        assertTrue(gen(TALL_NARROW, false, NO_FLOOR, 1, null).stream().anyMatch(o -> o.dy() < -3));
    }

    @Test
    void tallNarrowIsTallerThanWide() {
        for (int seed = 0; seed < 20; seed++) {
            List<Offset> leaves = gen(TALL_NARROW, false, NO_FLOOR, seed, null);
            assertTrue(tall(leaves) > width(leaves), "seed " + seed);
        }
    }

    @Test
    void wideLowIsWiderThanTall() {
        for (int seed = 0; seed < 20; seed++) {
            List<Offset> leaves = gen(WIDE_LOW, false, NO_FLOOR, seed, null);
            assertTrue(width(leaves) > tall(leaves), "seed " + seed);
        }
    }

    @Test
    void flatTopCutsTheCanopyOff() {
        Params flat = new Params(5, 3, 0, 0, 0, 1, 0, true);
        Params round = new Params(5, 3, 0, 0, 0, 1, 0, false);
        int flatTop = gen(flat, false, NO_FLOOR, 3, null).stream().mapToInt(Offset::dy).max().getAsInt();
        int roundTop = gen(round, false, NO_FLOOR, 3, null).stream().mapToInt(Offset::dy).max().getAsInt();
        assertTrue(flatTop < roundTop);
    }

    @Test
    void lobesWidenTheCanopyBeyondTheMainEllipsoid() {
        Params none = new Params(3, 2, 0, 0, 0, 2, 0, false);
        Params some = new Params(3, 2, 0, 6, 4, 2, 0, false);
        int plain = 0, lobed = 0;
        for (int seed = 0; seed < 20; seed++) {
            plain += gen(none, false, NO_FLOOR, seed, null).size();
            lobed += gen(some, false, NO_FLOOR, seed, null).size();
        }
        assertTrue(lobed > plain);
    }

    @Test
    void droopHangsLeavesBelowTheCanopy() {
        Params noDroop = new Params(4, 2, 0, 0, 0, 1, 0, false);
        Params droop = new Params(4, 2, 0, 0, 0, 1, 3, false);
        int lowestPlain = gen(noDroop, false, NO_FLOOR, 4, null).stream().mapToInt(Offset::dy).min().getAsInt();
        int lowestDroop = Integer.MAX_VALUE;
        for (int seed = 0; seed < 20; seed++) {
            lowestDroop = Math.min(lowestDroop, gen(droop, false, NO_FLOOR, seed, null).stream().mapToInt(Offset::dy).min().getAsInt());
        }
        assertTrue(lowestDroop < lowestPlain);
    }

    @Test
    void doubleTrunkCentresTheCanopyOnTheTrunk() {
        Params p = new Params(3, 2, 0, 0, 0, 1, 0, false);
        double sum = 0;
        int n = 0;
        for (int seed = 0; seed < 30; seed++) {
            for (Offset o : gen(p, true, NO_FLOOR, seed, null)) {
                sum += o.dx();
                n++;
            }
        }
        assertEquals(0.5, sum / n, 0.15);
    }

    @Test
    void everyKeptLeafIsWithinDecayDistanceOfALog() {
        for (int seed = 0; seed < 30; seed++) {
            for (Params p : new Params[] {TALL_NARROW, WIDE_LOW, DENSE}) {
                LogTest trunk = straightTrunk(true, 6);
                List<Offset> leaves = gen(p, true, NO_FLOOR, seed, trunk);
                Set<Offset> set = new HashSet<>(leaves);
                Map<Offset, Integer> dist = new HashMap<>();
                ArrayDeque<Offset> q = new ArrayDeque<>();
                for (int x = -12; x <= 12; x++) for (int y = -12; y <= 12; y++) for (int z = -12; z <= 12; z++) {
                    if (trunk.isLog(x, y, z)) { Offset l = new Offset(x, y, z); dist.put(l, 0); q.add(l); }
                }
                int[][] st = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};
                while (!q.isEmpty()) {
                    Offset c = q.poll();
                    for (int[] s : st) {
                        Offset nb = new Offset(c.dx() + s[0], c.dy() + s[1], c.dz() + s[2]);
                        if (set.contains(nb) && !dist.containsKey(nb)) { dist.put(nb, dist.get(c) + 1); q.add(nb); }
                    }
                }
                for (Offset o : leaves) {
                    Integer d = dist.get(o);
                    assertTrue(d != null && d <= YewFoliageLayout.MAX_LEAF_DISTANCE, o + " would decay, seed " + seed);
                }
            }
        }
    }

    @Test
    void pruningRemovesLeavesFarFromAnyLog() {
        Params huge = new Params(7, 2, 0, 0, 0, 1, 0, false);
        LogTest trunk = straightTrunk(false, 3);
        List<Offset> all = gen(huge, false, NO_FLOOR, 1, null);
        List<Offset> kept = gen(huge, false, NO_FLOOR, 1, trunk);
        assertTrue(kept.size() < all.size());
    }

    @Test
    void sameSeedGivesTheSameCanopy() {
        assertEquals(gen(DENSE, true, -2, 9, null), gen(DENSE, true, -2, 9, null));
    }

    @Test
    void clampsParametersInsteadOfThrowing() {
        Params p = new Params(0, 0, 0, -1, -1, 0, -1, false);
        assertEquals(1, p.radius());
        assertEquals(1, p.height());
        assertEquals(0, p.lobes());
        assertEquals(0, p.droop());
        assertFalse(gen(p, false, NO_FLOOR, 1, null).isEmpty());
    }
}
