package net.alternateearch.emeraldisleflora.registry;

import net.alternateearch.emeraldisleflora.EmeraldIsleFlora;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * A single, currently-empty creative inventory tab for this mod's future items and
 * blocks. Registered on startup by EmeraldIsleFlora#onInitialize.
 * <p>
 * To add items/blocks to the tab, register them (usually in their own registry class,
 * e.g. ModItems / ModBlocks) and then add them inside the entries() callback below.
 */
public final class ModItemGroups {

	public static final RegistryKey<ItemGroup> EMERALD_ISLE_FLORA_GROUP = RegistryKey.of(
			RegistryKeys.ITEM_GROUP, new Identifier(EmeraldIsleFlora.MOD_ID, "emerald_isle_flora"));

	public static void register() {
		EmeraldIsleFlora.LOGGER.info("Registering Item Groups for " + EmeraldIsleFlora.MOD_ID);

		registerToCustomGroup();
		registerToNaturalBlocks();
		
		EmeraldIsleFlora.LOGGER.info("Finished registering Item Groups for " + EmeraldIsleFlora.MOD_ID);
	}

	private static void registerToCustomGroup(){
		EmeraldIsleFlora.LOGGER.info("Registering Items in Custom Group for " + EmeraldIsleFlora.MOD_ID);

		Registry.register(
				Registries.ITEM_GROUP,
				EMERALD_ISLE_FLORA_GROUP,
				FabricItemGroup.builder()
						.icon(() -> new ItemStack(ModBlocks.BELLS_OF_IRELAND))
						.displayName(Text.translatable("itemGroup." + EmeraldIsleFlora.MOD_ID + ".main"))
						.entries((displayContext, entries) -> {
							entries.add(ModBlocks.BELLS_OF_IRELAND);
							entries.add(ModBlocks.GROWN_BELLS_OF_IRELAND);
						})
						.build());
		
		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Custom Group for " + EmeraldIsleFlora.MOD_ID);	
	}

	private static void registerToNaturalBlocks() {
		EmeraldIsleFlora.LOGGER.info("Registering Items in Natural Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
			content.addAfter(Blocks.WITHER_ROSE, ModBlocks.BELLS_OF_IRELAND);
			content.addAfter(ModBlocks.BELLS_OF_IRELAND, ModBlocks.GROWN_BELLS_OF_IRELAND);
		});

		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Natural Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);
	}
}
