package net.alternateearch.emeraldisleflora.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.alternateearch.emeraldisleflora.EmeraldIsleFlora;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

/**
 * Wires this mod's config into Mod Menu's "config" button, using Cloth Config to build
 * the actual screen. This class is only ever loaded/invoked by Mod Menu itself (via the
 * "modmenu" entrypoint declared in fabric.mod.json), so nothing here runs, and neither
 * Mod Menu nor Cloth Config's screen classes need to load, if Mod Menu isn't installed.
 * <p>
 * Add a new entry here for each new field you add to ModConfig.
 * <p>
 * Client-only (see the note in EmeraldIsleFloraClient about why this is annotated
 * rather than compile-time enforced in this single-source-set project).
 */
@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

	private static final String TRANSLATION_PREFIX = "config." + EmeraldIsleFlora.MOD_ID + ".";

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parentScreen -> {
			ModConfig config = EmeraldIsleFlora.getConfig();

			ConfigBuilder builder = ConfigBuilder.create()
					.setParentScreen(parentScreen)
					.setTitle(Text.translatable(TRANSLATION_PREFIX + "title"))
					.setSavingRunnable(config::save);

			ConfigCategory general =
					builder.getOrCreateCategory(Text.translatable(TRANSLATION_PREFIX + "category.general"));
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();

			general.addEntry(entryBuilder
					.startBooleanToggle(
							Text.translatable(TRANSLATION_PREFIX + "enableGrownFlowering"),
							config.enableGrownFlowering)
					.setDefaultValue(true)
					.setTooltip(Text.translatable(TRANSLATION_PREFIX + "enableGrownFlowering.tooltip"))
					.setSaveConsumer(value -> config.enableGrownFlowering = value)
					.build());

			general.addEntry(entryBuilder
					.startBooleanToggle(
							Text.translatable(TRANSLATION_PREFIX + "enableGrownFlowerHarvesting"),
							config.enableGrownFlowerHarvesting)
					.setDefaultValue(true)
					.setTooltip(Text.translatable(TRANSLATION_PREFIX + "enableGrownFlowerHarvesting.tooltip"))
					.setSaveConsumer(value -> config.enableGrownFlowerHarvesting = value)
					.build());

			return builder.build();
		};
	}
}
