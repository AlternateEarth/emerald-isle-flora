package net.alternateearth.emeraldisleflora.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

/**
 * The Dark Forest grove is a hand-written placed feature (the int-provider JSON it needs differs between 1.20.1
 * and 1.21.1+, so datagen cannot share one file). This keeps the two files in step with {@link YewNaturalMixes}.
 */
class YewGroveDataTest {

    private static final String PATH = "data/emeraldisleflora/worldgen/placed_feature/yew_grove_dark_forest.json";

    private static Path versionsData() {
        Path dir = Path.of("").toAbsolutePath();
        while (dir != null && !Files.isDirectory(dir.resolve("versions/data"))) dir = dir.getParent();
        assertTrue(dir != null, "could not find the repository root");
        return dir.resolve("versions/data");
    }

    private static String read(String versionDir) throws IOException {
        return Files.readString(versionsData().resolve(versionDir).resolve("src/main/resources").resolve(PATH));
    }

    private static int intAfter(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*(-?\\d+)").matcher(json);
        assertTrue(m.find(), key);
        return Integer.parseInt(m.group(1));
    }

    private static void check(String json) {
        assertTrue(json.contains("\"feature\": \"emeraldisleflora:yew_tree_dark_woodland\""));
        assertEquals(YewNaturalMixes.GROVE_RARITY, intAfter(json, "chance"));
        assertEquals(YewNaturalMixes.GROVE_MIN_TREES, intAfter(json, "min_inclusive"));
        // The first uniform provider is the tree count, the second the sideways spread.
        Matcher min = Pattern.compile("\"min_inclusive\"\\s*:\\s*(-?\\d+)").matcher(json);
        Matcher max = Pattern.compile("\"max_inclusive\"\\s*:\\s*(-?\\d+)").matcher(json);
        assertTrue(min.find() && max.find());
        assertEquals(YewNaturalMixes.GROVE_MIN_TREES, Integer.parseInt(min.group(1)));
        assertEquals(YewNaturalMixes.GROVE_MAX_TREES, Integer.parseInt(max.group(1)));
        assertTrue(min.find() && max.find());
        assertEquals(-YewNaturalMixes.GROVE_SPREAD, Integer.parseInt(min.group(1)));
        assertEquals(YewNaturalMixes.GROVE_SPREAD, Integer.parseInt(max.group(1)));
    }

    @Test
    void groveFileFor1201MatchesTheConstants() throws IOException {
        check(read("1.20.1"));
    }

    @Test
    void groveFileFor121AndLaterMatchesTheConstants() throws IOException {
        check(read("1.21.1-plus"));
    }
}
