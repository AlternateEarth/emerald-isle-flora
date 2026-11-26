package net.alternateearth.emeraldisleflora.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.alternateearth.emeraldisleflora.worldgen.YewFoliageLayout.Offset;
import org.junit.jupiter.api.Test;

class YewLeafDistanceTest {

    /** A straight row of leaves 1..count blocks east of a log at the origin. */
    private static Set<Offset> row(int count) {
        Set<Offset> leaves = new HashSet<>();
        for (int x = 1; x <= count; x++) leaves.add(new Offset(x, 0, 0));
        return leaves;
    }

    private static final List<Offset> LOG = List.of(new Offset(0, 0, 0));

    @Test
    void leavesWithinSixStepsAreKept() {
        assertEquals(6, YewLeafDistance.reachable(LOG, row(6)).size());
    }

    @Test
    void theSeventhLeafInARowWouldDecay() {
        Set<Offset> lost = YewLeafDistance.unreachable(LOG, row(7));
        assertEquals(Set.of(new Offset(7, 0, 0)), lost);
    }

    @Test
    void removingAMiddleLeafCutsOffTheOnesBeyondIt() {
        Set<Offset> leaves = row(5);
        leaves.remove(new Offset(2, 0, 0));
        Set<Offset> lost = YewLeafDistance.unreachable(LOG, leaves);
        assertEquals(Set.of(new Offset(3, 0, 0), new Offset(4, 0, 0), new Offset(5, 0, 0)), lost);
    }

    @Test
    void aLeafWithAnotherRouteToALogSurvivesTheRemovalOfOneRoute() {
        // Two logs, a leaf between them: removing the leaf next to one log does not cut it off.
        List<Offset> logs = List.of(new Offset(0, 0, 0), new Offset(4, 0, 0));
        Set<Offset> leaves = new HashSet<>(List.of(new Offset(1, 0, 0), new Offset(2, 0, 0), new Offset(3, 0, 0)));
        leaves.remove(new Offset(1, 0, 0));
        assertTrue(YewLeafDistance.unreachable(logs, leaves).isEmpty());
    }

    @Test
    void noLogsMeansEveryLeafDecays() {
        assertEquals(3, YewLeafDistance.unreachable(List.of(), row(3)).size());
    }

    @Test
    void connectivityIsThroughFacesOnly() {
        Set<Offset> leaves = Set.of(new Offset(1, 1, 0));
        assertEquals(1, YewLeafDistance.unreachable(LOG, leaves).size());
    }
}
