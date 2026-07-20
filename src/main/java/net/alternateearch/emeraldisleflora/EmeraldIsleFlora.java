package net.alternateearch.emeraldisleflora;

import net.alternateearch.emeraldisleflora.config.ModConfig;
import net.alternateearch.emeraldisleflora.registry.ModBlocks;
import net.alternateearch.emeraldisleflora.registry.ModItemGroups;
import net.alternateearch.emeraldisleflora.util.ModBoneMealInteraction;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main (common) entrypoint. Runs on both the client and the dedicated server.
 * <p>
 * This is an intentionally empty starter mod: no blocks or items are registered yet.
 * See registry.ModItemGroups for the pre-wired (currently empty) creative tab, and
 * config.ModConfig for the sample config option.
 */
public class EmeraldIsleFlora implements ModInitializer {

	/** Must match the "id" field in fabric.mod.json. */
	public static final String MOD_ID = "emeraldisleflora";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static ModConfig config;

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Emerald Isle Flora...");
		config = ModConfig.load();

		ModItemGroups.register();
		ModBlocks.register();
		ModBoneMealInteraction.register();

		if (config.logStartupMessage) {
			LOGGER.info("Emerald Isle Flora has loaded!");
		}
	}

	/**
	 * The loaded config instance. Available after {@link #onInitialize()} has run, which
	 * Fabric Loader guarantees happens before any other mod's client/server code runs.
	 */
	public static ModConfig getConfig() {
		return config;
	}
}
