package net.alternateearth.emeraldisleflora.worldgen;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.alternateearth.emeraldisleflora.worldgen.YewFoliageLayout.Offset;

/**
 * Vanilla's leaf-decay rule, with no Minecraft imports so it can be unit tested: a leaf stays only if a log
 * is at most {@link YewFoliageLayout#MAX_LEAF_DISTANCE} steps away, counting steps through connected leaves.
 * Used both when placing a canopy and after the clearance pass removes leaves, since removing a leaf can cut
 * off the leaves that were connected to the trunk through it.
 */
public final class YewLeafDistance {

    private static final int[][] STEPS = {{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};

    private YewLeafDistance() {}

    /** The leaves that are within decay distance of a log, through connected leaves. */
    public static Set<Offset> reachable(Collection<Offset> logs, Set<Offset> leaves) {
        ArrayDeque<Offset> queue = new ArrayDeque<>();
        Map<Offset, Integer> dist = new HashMap<>();
        for (Offset log : logs) {
            if (dist.putIfAbsent(log, 0) == null) queue.add(log);
        }
        while (!queue.isEmpty()) {
            Offset cur = queue.poll();
            int d = dist.get(cur);
            if (d >= YewFoliageLayout.MAX_LEAF_DISTANCE) continue;
            for (int[] s : STEPS) {
                Offset n = new Offset(cur.dx() + s[0], cur.dy() + s[1], cur.dz() + s[2]);
                if (!leaves.contains(n) || dist.containsKey(n)) continue;
                dist.put(n, d + 1);
                queue.add(n);
            }
        }
        Set<Offset> kept = new HashSet<>();
        for (Offset o : leaves) if (dist.containsKey(o)) kept.add(o);
        return kept;
    }

    /** The leaves that would decay: those not in {@link #reachable}. */
    public static Set<Offset> unreachable(Collection<Offset> logs, Set<Offset> leaves) {
        Set<Offset> lost = new HashSet<>(leaves);
        lost.removeAll(reachable(logs, leaves));
        return lost;
    }
}
