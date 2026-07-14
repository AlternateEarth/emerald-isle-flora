package net.alternateearch.emeraldisleflora.registry;

import net.alternateearch.emeraldisleflora.EmeraldIsleFlora;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
		Registry.register(
				Registries.ITEM_GROUP,
				EMERALD_ISLE_FLORA_GROUP,
				FabricItemGroup.builder()
						.icon(() -> new ItemStack(Items.OAK_SAPLING))
						.displayName(Text.translatable("itemGroup." + EmeraldIsleFlora.MOD_ID + ".main"))
						.entries((displayContext, entries) -> {
							entries.add(ModBlocks.BELLS_OF_IRELAND);
						})
						.build());
	}
}
