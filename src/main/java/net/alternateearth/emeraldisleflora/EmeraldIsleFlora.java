package net.alternateearth.emeraldisleflora;

import net.alternateearth.emeraldisleflora.config.ModConfig;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.alternateearth.emeraldisleflora.registry.ModItemGroups;
import net.alternateearth.emeraldisleflora.registry.ModWorldGen;
import net.alternateearth.emeraldisleflora.util.ModBoneMealInteraction;
import net.alternateearth.emeraldisleflora.util.ModDispenserBehavior;
/*? if fabric {*/
import net.fabricmc.api.ModInitializer;
/*?}*/
/*? if forge {*/
/*import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
*/
/*?}*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main (common) entrypoint. Runs on both the client and the dedicated server.
 * <p>
 * See registry.ModItemGroups for the creative tab, and config.ModConfig for the config
 * options.
 * <p>
 * Block/item registration timing differs by loader: Fabric registers directly here;
 * Forge defers to its own RegisterEvent (see ModBlocks#onRegister /
 * ModItemGroups#onRegisterCreativeTab), which is why this class's two loader bodies
 * don't share a single "commonInit" helper - the two loaders genuinely don't call the
 * same registration methods at the same point in startup.
 */
/*? if forge {*/
/*@Mod(EmeraldIsleFlora.MOD_ID)*/
/*?}*/
/*? if fabric {*/
public class EmeraldIsleFlora implements ModInitializer {
/*?}*/
/*? if forge {*/
/*public class EmeraldIsleFlora {*/
/*?}*/

	/** Must match the "id" field in fabric.mod.json / mods.toml. */
	public static final String MOD_ID = "emeraldisleflora";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static ModConfig config;

	/*? if forge {*/
	/*public EmeraldIsleFlora() {
		LOGGER.info("Initializing Emerald Isle Flora...");
		config = ModConfig.load();

		var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(ModBlocks::onRegister);
		modEventBus.addListener(ModItemGroups::onRegisterCreativeTab);
		modEventBus.addListener(ModItemGroups::onBuildCreativeTabContents);
		MinecraftForge.EVENT_BUS.addListener(ModBoneMealInteraction::onRightClickBlock);

		ModBoneMealInteraction.register();
		ModDispenserBehavior.register();
		ModWorldGen.register();

		LOGGER.info("Emerald Isle Flora has loaded!");
	}*/
	/*?}*/

	/*? if fabric {*/
	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Emerald Isle Flora...");
		config = ModConfig.load();

		ModItemGroups.register();
		ModBlocks.register();
		ModBoneMealInteraction.register();
		ModDispenserBehavior.register();
		ModWorldGen.register();

		LOGGER.info("Emerald Isle Flora has loaded!");
	}
	/*?}*/

	/**
	 * The loaded config instance. Available after mod init has run, which every loader
	 * guarantees happens before any other mod's client/server code runs.
	 */
	public static ModConfig getConfig() {
		return config;
	}
}
