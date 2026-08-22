package net.alternateearth.emeraldisleflora.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if fabric {*/
import net.fabricmc.loader.api.FabricLoader;
/*?}*/
/*? if forge {*/
/*import net.minecraftforge.fml.loading.FMLPaths;*/
/*?}*/
/*? if neoforge {*/
/*import net.neoforged.fml.loading.FMLPaths;*/
/*?}*/

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A small, hand-rolled JSON config (POJO + Gson, no config framework), saved to config/emerald-isle-flora.json.
 * Add new fields here, then wire each up to a matching entry in client.ModMenuIntegration.
 */
public class ModConfig {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	/*? if fabric {*/
	private static final Path CONFIG_PATH =
			FabricLoader.getInstance().getConfigDir().resolve(EmeraldIsleFlora.MOD_ID + ".json");
	/*?}*/
	/*? if forgeLike {*/
	/*private static final Path CONFIG_PATH =
			FMLPaths.CONFIGDIR.get().resolve(EmeraldIsleFlora.MOD_ID + ".json");*/
	/*?}*/

	/**
	 * When true (default), bone meal on an already-grown flower drops an extra item without reverting the block. See util.ModCommonLogic.
	 */
	public boolean enableGrownFlowerHarvesting = true;

	/**
	 * When true (default), bone meal on a flower (Bells of Ireland or its potted variant) grows
	 * it into its grown variant; when false, nothing happens. See util.ModCommonLogic.
	 */
	public boolean enableGrownFlowering = true;

	public static ModConfig load() {
		if (Files.exists(CONFIG_PATH)) {
			try (Reader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
				ModConfig loaded = GSON.fromJson(reader, ModConfig.class);
				if (loaded != null) {
					return loaded;
				}
			} catch (IOException e) {
				EmeraldIsleFlora.LOGGER.warn(
						"Failed to read {}, falling back to default config", CONFIG_PATH.getFileName(), e);
			}
		}

		ModConfig defaults = new ModConfig();
		defaults.save();
		return defaults;
	}

	public void save() {
		try {
			Files.createDirectories(CONFIG_PATH.getParent());
			try (Writer writer = Files.newBufferedWriter(CONFIG_PATH, StandardCharsets.UTF_8)) {
				GSON.toJson(this, writer);
			}
		} catch (IOException e) {
			EmeraldIsleFlora.LOGGER.warn("Failed to save {}", CONFIG_PATH.getFileName(), e);
		}
	}
}
