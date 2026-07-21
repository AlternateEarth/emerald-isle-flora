package net.alternateearch.emeraldisleflora.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.alternateearch.emeraldisleflora.EmeraldIsleFlora;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A small, hand-rolled JSON config, saved to config/emerald-isle-flora.json.
 * <p>
 * This is deliberately simple (a POJO + Gson) rather than routed through a config
 * framework, so it works with or without Cloth Config / Mod Menu installed. The Cloth
 * Config screen in the client sourceset just reads and writes the fields on this class.
 * <p>
 * Add new fields here as you add new config options, then wire each one up to a matching
 * entry in client.ModMenuIntegration.
 */
public class ModConfig {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH =
			FabricLoader.getInstance().getConfigDir().resolve(EmeraldIsleFlora.MOD_ID + ".json");

	/**
	 * When true (default), using bone meal on an already-grown flower (Grown Bells of
	 * Ireland or its potted variant) drops an extra flower item without reverting the
	 * block - a small renewable flower source. When false, that specific interaction
	 * does nothing. Growing a base Bells of Ireland into its grown variant is
	 * unaffected either way - this only gates the repeatable-harvest half of the
	 * mechanic, not the initial grow transition. See util.ModCommonLogic.
	 */
	public boolean enableGrownFlowerHarvesting = true;

	/**
	 * When true (default), using bone meal on a flower (Bells of Ireland or its potted 
	 * variant) will cause it to grow into its grown variant. When false, that specific 
	 * interaction does nothing. See util.ModCommonLogic.
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
