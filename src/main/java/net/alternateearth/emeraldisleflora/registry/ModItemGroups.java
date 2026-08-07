package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if fabric && <26.2 {*/
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
/*?}*/
/*? if fabric && >=26.2 {*/
/*import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.world.level.block.Blocks;
*/
/*?}*/
/*? if <26.2 {*/
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
/*?} else {*/
/*import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
*/
/*?}*/
/*? if forge {*/
/*import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegisterEvent;
*/
/*?}*/
/*? if neoforge {*/
/*import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
*/
/*?}*/

/**
 * A single, currently-empty creative inventory tab for this mod's future items and
 * blocks. Registered on startup by EmeraldIsleFlora#onInitialize.
 * <p>
 * To add items/blocks to the tab, register them (usually in their own registry class,
 * e.g. ModItems / ModBlocks) and then add them inside the entries() callback below.
 */
public final class ModItemGroups {

	/*? if <26.2 {*/
	public static final RegistryKey<ItemGroup> EMERALD_ISLE_FLORA_GROUP = RegistryKey.of(
			RegistryKeys.ITEM_GROUP, Identifier.of(EmeraldIsleFlora.MOD_ID, "emerald_isle_flora"));
	/*?} else {*/
	/*public static final ResourceKey<CreativeModeTab> EMERALD_ISLE_FLORA_GROUP = ResourceKey.create(
			Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "emerald_isle_flora"));*/
	/*?}*/

	// 26.2: vanilla's own CreativeModeTabs.NATURAL_BLOCKS field is private - this
	// reconstructs the same ResourceKey by hand (ResourceKey instances are interned by
	// registry+id, so this is the same key vanilla itself uses internally, just built
	// from outside the class instead of read off its private field).
	/*? if >=26.2 {*/
	/*private static final ResourceKey<CreativeModeTab> NATURAL_BLOCKS = ResourceKey.create(
			Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("natural_blocks"));*/
	/*?}*/

	/** Fabric-only: registers directly. Forge instead uses {@link #onRegisterCreativeTab} / {@link #onBuildCreativeTabContents}. */
	public static void register() {
		/*? if fabric {*/
		EmeraldIsleFlora.LOGGER.info("Registering Item Groups for " + EmeraldIsleFlora.MOD_ID);

		registerToCustomGroup();
		registerToNaturalBlocks();

		EmeraldIsleFlora.LOGGER.info("Finished registering Item Groups for " + EmeraldIsleFlora.MOD_ID);
		/*?}*/
	}

	/*? if fabric {*/
	private static void registerToCustomGroup(){
		EmeraldIsleFlora.LOGGER.info("Registering Items in Custom Group for " + EmeraldIsleFlora.MOD_ID);

		/*? if <26.2 {*/
		Registry.register(
			Registries.ITEM_GROUP,
			EMERALD_ISLE_FLORA_GROUP,
			FabricItemGroup.builder()
				.icon(() -> new ItemStack(ModBlocks.BELLS_OF_IRELAND))
				.displayName(Text.translatable("itemGroup." + EmeraldIsleFlora.MOD_ID + ".main"))
				.entries((displayContext, entries) -> {
					entries.add(ModBlocks.BELLS_OF_IRELAND);
					entries.add(ModBlocks.BOG_ROSEMARY);
					entries.add(ModBlocks.BULBOUS_BUTTERCUP);
					entries.add(ModBlocks.BLUEBELL);
					entries.add(ModBlocks.GROWN_BELLS_OF_IRELAND);
					entries.add(ModBlocks.GROWN_BOG_ROSEMARY);
					entries.add(ModBlocks.GROWN_BULBOUS_BUTTERCUP);
					entries.add(ModBlocks.GROWN_BLUEBELL);
				})
				.build());
		/*?} else {*/
		/*Registry.register(
			BuiltInRegistries.CREATIVE_MODE_TAB,
			EMERALD_ISLE_FLORA_GROUP,
			FabricCreativeModeTab.builder()
				.icon(() -> new ItemStack(ModBlocks.BELLS_OF_IRELAND))
				.title(Component.translatable("itemGroup." + EmeraldIsleFlora.MOD_ID + ".main"))
				.displayItems((displayContext, entries) -> {
					entries.accept(ModBlocks.BELLS_OF_IRELAND);
					entries.accept(ModBlocks.BOG_ROSEMARY);
					entries.accept(ModBlocks.BULBOUS_BUTTERCUP);
					entries.accept(ModBlocks.BLUEBELL);
					entries.accept(ModBlocks.GROWN_BELLS_OF_IRELAND);
					entries.accept(ModBlocks.GROWN_BOG_ROSEMARY);
					entries.accept(ModBlocks.GROWN_BULBOUS_BUTTERCUP);
					entries.accept(ModBlocks.GROWN_BLUEBELL);
				})
				.build());*/
		/*?}*/

		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Custom Group for " + EmeraldIsleFlora.MOD_ID);
	}

	private static void registerToNaturalBlocks() {
		EmeraldIsleFlora.LOGGER.info("Registering Items in Natural Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);

		/*? if <26.2 {*/
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
			content.addAfter(Blocks.WITHER_ROSE, ModBlocks.BELLS_OF_IRELAND);
			content.addAfter(ModBlocks.BELLS_OF_IRELAND, ModBlocks.BOG_ROSEMARY);
			content.addAfter(ModBlocks.BOG_ROSEMARY, ModBlocks.BULBOUS_BUTTERCUP);
			content.addAfter(ModBlocks.BULBOUS_BUTTERCUP, ModBlocks.GROWN_BELLS_OF_IRELAND);
			content.addAfter(ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.GROWN_BOG_ROSEMARY);
			content.addAfter(ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.GROWN_BULBOUS_BUTTERCUP);
			content.addAfter(ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BLUEBELL);
			content.addAfter(ModBlocks.BLUEBELL, ModBlocks.GROWN_BLUEBELL);
		});
		/*?} else {*/
		/*CreativeModeTabEvents.modifyOutputEvent(NATURAL_BLOCKS).register(content -> {
			content.insertAfter(Blocks.WITHER_ROSE, ModBlocks.BELLS_OF_IRELAND);
			content.insertAfter(ModBlocks.BELLS_OF_IRELAND, ModBlocks.BOG_ROSEMARY);
			content.insertAfter(ModBlocks.BOG_ROSEMARY, ModBlocks.BULBOUS_BUTTERCUP);
			content.insertAfter(ModBlocks.BULBOUS_BUTTERCUP, ModBlocks.GROWN_BELLS_OF_IRELAND);
			content.insertAfter(ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.GROWN_BOG_ROSEMARY);
			content.insertAfter(ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.GROWN_BULBOUS_BUTTERCUP);
			content.insertAfter(ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BLUEBELL);
			content.insertAfter(ModBlocks.BLUEBELL, ModBlocks.GROWN_BLUEBELL);
		});*/
		/*?}*/

		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Natural Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);
	}
	/*?}*/

	/*? if forgeLike && <26.2 {*/
	/*
	public static void onRegisterCreativeTab(RegisterEvent event) {
		event.register(RegistryKeys.ITEM_GROUP, helper -> {
			ItemGroup group = ItemGroup.create(ItemGroup.Row.TOP, 0)
					.icon(() -> new ItemStack(ModBlocks.BELLS_OF_IRELAND))
					.displayName(Text.translatable("itemGroup." + EmeraldIsleFlora.MOD_ID + ".main"))
					.entries((displayContext, entries) -> {
						entries.add(ModBlocks.BELLS_OF_IRELAND);
						entries.add(ModBlocks.BOG_ROSEMARY);
						entries.add(ModBlocks.BULBOUS_BUTTERCUP);
						entries.add(ModBlocks.BLUEBELL);
						entries.add(ModBlocks.GROWN_BELLS_OF_IRELAND);
						entries.add(ModBlocks.GROWN_BOG_ROSEMARY);
						entries.add(ModBlocks.GROWN_BULBOUS_BUTTERCUP);
						entries.add(ModBlocks.GROWN_BLUEBELL);
					})
					.build();
			helper.register(EMERALD_ISLE_FLORA_GROUP.getValue(), group);
		});
	}
	*/
	/*?}*/
	/*? if neoforge && >=26.2 {*/
	/*
	public static void onRegisterCreativeTab(RegisterEvent event) {
		event.register(Registries.CREATIVE_MODE_TAB, helper -> {
			CreativeModeTab group = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
					.icon(() -> new ItemStack(ModBlocks.BELLS_OF_IRELAND))
					.title(Component.translatable("itemGroup." + EmeraldIsleFlora.MOD_ID + ".main"))
					.displayItems((displayContext, entries) -> {
						entries.accept(ModBlocks.BELLS_OF_IRELAND);
						entries.accept(ModBlocks.BOG_ROSEMARY);
						entries.accept(ModBlocks.BULBOUS_BUTTERCUP);
						entries.accept(ModBlocks.BLUEBELL);
						entries.accept(ModBlocks.GROWN_BELLS_OF_IRELAND);
						entries.accept(ModBlocks.GROWN_BOG_ROSEMARY);
						entries.accept(ModBlocks.GROWN_BULBOUS_BUTTERCUP);
						entries.accept(ModBlocks.GROWN_BLUEBELL);
					})
					.build();
			helper.register(EMERALD_ISLE_FLORA_GROUP.identifier(), group);
		});
	}
	*/
	/*?}*/

	/*? if forge {*/
	/*
	// Adds this mod's flowers into vanilla's existing "Natural Blocks" tab, the Forge
	// equivalent of Fabric's ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).
	// Forge's BuildCreativeModeTabContentsEvent adds its own accept(Supplier) helper on
	// top of vanilla's ItemGroup.Entries - NeoForge's equivalent event doesn't have it,
	// see the neoforge branch below for the vanilla ItemGroup.Entries#add() equivalent.
	public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() != ItemGroups.NATURAL) {
			return;
		}

		event.accept(() -> ModBlocks.BELLS_OF_IRELAND);
		event.accept(() -> ModBlocks.BOG_ROSEMARY);
		event.accept(() -> ModBlocks.BULBOUS_BUTTERCUP);
		event.accept(() -> ModBlocks.BLUEBELL);
		event.accept(() -> ModBlocks.GROWN_BELLS_OF_IRELAND);
		event.accept(() -> ModBlocks.GROWN_BOG_ROSEMARY);
		event.accept(() -> ModBlocks.GROWN_BULBOUS_BUTTERCUP);
		event.accept(() -> ModBlocks.GROWN_BLUEBELL);
	}
	*/
	/*?}*/

	/*? if neoforge && <26.2 {*/
	/*
	// NeoForge's BuildCreativeModeTabContentsEvent implements vanilla's
	// ItemGroup.Entries directly (no Forge-style accept(Supplier) helper), so this uses
	// the same add(ItemConvertible) vanilla default method as the Fabric/Forge
	// entries.add(...) calls above.
	public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() != ItemGroups.NATURAL) {
			return;
		}

		event.add(ModBlocks.BELLS_OF_IRELAND);
		event.add(ModBlocks.BOG_ROSEMARY);
		event.add(ModBlocks.BULBOUS_BUTTERCUP);
		event.add(ModBlocks.BLUEBELL);
		event.add(ModBlocks.GROWN_BELLS_OF_IRELAND);
		event.add(ModBlocks.GROWN_BOG_ROSEMARY);
		event.add(ModBlocks.GROWN_BULBOUS_BUTTERCUP);
		event.add(ModBlocks.GROWN_BLUEBELL);
	}
	*/
	/*?}*/
	/*? if neoforge && >=26.2 {*/
	/*
	// 26.2: vanilla's CreativeModeTab.Output (what BuildCreativeModeTabContentsEvent
	// implements) uses accept(ItemLike) as its own standard default method - not add(...)
	// (that was a Yarn-specific name on the older ItemGroup.Entries interface) - so this
	// is now the same accept(...) call used on every loader/registration path here.
	public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() != NATURAL_BLOCKS) {
			return;
		}

		event.accept(ModBlocks.BELLS_OF_IRELAND);
		event.accept(ModBlocks.BOG_ROSEMARY);
		event.accept(ModBlocks.BULBOUS_BUTTERCUP);
		event.accept(ModBlocks.BLUEBELL);
		event.accept(ModBlocks.GROWN_BELLS_OF_IRELAND);
		event.accept(ModBlocks.GROWN_BOG_ROSEMARY);
		event.accept(ModBlocks.GROWN_BULBOUS_BUTTERCUP);
		event.accept(ModBlocks.GROWN_BLUEBELL);
	}
	*/
	/*?}*/
}
