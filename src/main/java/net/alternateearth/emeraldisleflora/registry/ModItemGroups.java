package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if fabric && <26.2 {*/
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
/*?}*/
/*? if fabric && >=26.2 {*/
/*import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
*/
/*?}*/
/*? if <26.2 {*/
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
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
 * A creative inventory tab for this mod's items/blocks, registered on startup by
 * EmeraldIsleFlora#onInitialize. Add new items via the entries() callbacks below.
 */
public final class ModItemGroups {

	/*? if <26.2 {*/
	public static final RegistryKey<ItemGroup> EMERALD_ISLE_FLORA_GROUP = RegistryKey.of(
			RegistryKeys.ITEM_GROUP, Identifier.of(EmeraldIsleFlora.MOD_ID, "emerald_isle_flora"));
	/*?} else {*/
	/*public static final ResourceKey<CreativeModeTab> EMERALD_ISLE_FLORA_GROUP = ResourceKey.create(
			Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "emerald_isle_flora"));*/
	/*?}*/

	/** Fabric-only: registers directly. Forge instead uses {@link #onRegisterCreativeTab} / {@link #onBuildCreativeTabContents}. */
	public static void register() {
		/*? if fabric {*/
		EmeraldIsleFlora.LOGGER.info("Registering Item Groups for " + EmeraldIsleFlora.MOD_ID);

		registerToCustomGroup();
		registerToBuildingBlocks();
		registerToNaturalBlocks();
		registerToFunctionalBlocks();
		registerToFoodAndDrinkBlocks();
		registerToToolsBlocks();

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
					entries.add(ModBlocks.YEW_LOG);
					entries.add(ModBlocks.YEW_WOOD);
					entries.add(ModBlocks.STRIPPED_YEW_LOG);
					entries.add(ModBlocks.STRIPPED_YEW_WOOD);
					entries.add(ModBlocks.YEW_PLANKS);
					entries.add(ModBlocks.YEW_LEAVES);
					entries.add(ModBlocks.YEW_SAPLING);
					entries.add(ModBlocks.YEW_STAIRS);
					entries.add(ModBlocks.YEW_SLAB);
					entries.add(ModBlocks.YEW_FENCE);
					entries.add(ModBlocks.YEW_FENCE_GATE);
					entries.add(ModBlocks.YEW_DOOR);
					entries.add(ModBlocks.YEW_TRAPDOOR);
					entries.add(ModBlocks.YEW_PRESSURE_PLATE);
					entries.add(ModBlocks.YEW_BUTTON);
					entries.add(ModBlocks.YEW_SIGN);
					entries.add(ModBlocks.YEW_HANGING_SIGN);
					entries.add(ModItems.YEW_BERRY);
					entries.add(ModItems.YEW_BOAT);
					entries.add(ModItems.YEW_CHEST_BOAT);
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
					entries.accept(ModBlocks.YEW_LOG);
					entries.accept(ModBlocks.YEW_WOOD);
					entries.accept(ModBlocks.STRIPPED_YEW_LOG);
					entries.accept(ModBlocks.STRIPPED_YEW_WOOD);
					entries.accept(ModBlocks.YEW_PLANKS);
					entries.accept(ModBlocks.YEW_LEAVES);
					entries.accept(ModBlocks.YEW_SAPLING);
					entries.accept(ModBlocks.YEW_STAIRS);
					entries.accept(ModBlocks.YEW_SLAB);
					entries.accept(ModBlocks.YEW_FENCE);
					entries.accept(ModBlocks.YEW_FENCE_GATE);
					entries.accept(ModBlocks.YEW_DOOR);
					entries.accept(ModBlocks.YEW_TRAPDOOR);
					entries.accept(ModBlocks.YEW_PRESSURE_PLATE);
					entries.accept(ModBlocks.YEW_BUTTON);
					entries.accept(ModBlocks.YEW_SIGN);
					entries.accept(ModBlocks.YEW_HANGING_SIGN);
					entries.accept(ModBlocks.YEW_SHELF);
					entries.accept(ModItems.YEW_BERRY);
					entries.accept(ModItems.YEW_BOAT);
					entries.accept(ModItems.YEW_CHEST_BOAT);
				})
				.build());*/
		/*?}*/

		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Custom Group for " + EmeraldIsleFlora.MOD_ID);
	}

	private static void registerToBuildingBlocks() {
		EmeraldIsleFlora.LOGGER.info("Registering Items in Building Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);

		/*? if <26.2 {*/
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
			content.addAfter(Blocks.WARPED_BUTTON, ModBlocks.YEW_LOG);
			content.addAfter(ModBlocks.YEW_LOG, ModBlocks.YEW_WOOD);
			content.addAfter(ModBlocks.YEW_WOOD, ModBlocks.STRIPPED_YEW_LOG);
			content.addAfter(ModBlocks.STRIPPED_YEW_LOG, ModBlocks.STRIPPED_YEW_WOOD);
			content.addAfter(ModBlocks.STRIPPED_YEW_WOOD, ModBlocks.YEW_PLANKS);
			content.addAfter(ModBlocks.YEW_PLANKS, ModBlocks.YEW_STAIRS);
			content.addAfter(ModBlocks.YEW_STAIRS, ModBlocks.YEW_SLAB);
			content.addAfter(ModBlocks.YEW_SLAB, ModBlocks.YEW_FENCE);
			content.addAfter(ModBlocks.YEW_FENCE, ModBlocks.YEW_FENCE_GATE);
			content.addAfter(ModBlocks.YEW_FENCE_GATE, ModBlocks.YEW_DOOR);
			content.addAfter(ModBlocks.YEW_DOOR, ModBlocks.YEW_TRAPDOOR);
			content.addAfter(ModBlocks.YEW_TRAPDOOR, ModBlocks.YEW_PRESSURE_PLATE);
			content.addAfter(ModBlocks.YEW_PRESSURE_PLATE, ModBlocks.YEW_BUTTON);

		});
		/*?} else {*/
		/*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(content -> {
			content.insertAfter(Blocks.WARPED_BUTTON, ModBlocks.YEW_LOG);
			content.insertAfter(ModBlocks.YEW_LOG, ModBlocks.YEW_WOOD);
			content.insertAfter(ModBlocks.YEW_WOOD, ModBlocks.STRIPPED_YEW_LOG);
			content.insertAfter(ModBlocks.STRIPPED_YEW_LOG, ModBlocks.STRIPPED_YEW_WOOD);
			content.insertAfter(ModBlocks.STRIPPED_YEW_WOOD, ModBlocks.YEW_PLANKS);
			content.insertAfter(ModBlocks.YEW_PLANKS, ModBlocks.YEW_STAIRS);
			content.insertAfter(ModBlocks.YEW_STAIRS, ModBlocks.YEW_SLAB);
			content.insertAfter(ModBlocks.YEW_SLAB, ModBlocks.YEW_FENCE);
			content.insertAfter(ModBlocks.YEW_FENCE, ModBlocks.YEW_FENCE_GATE);
			content.insertAfter(ModBlocks.YEW_FENCE_GATE, ModBlocks.YEW_DOOR);
			content.insertAfter(ModBlocks.YEW_DOOR, ModBlocks.YEW_TRAPDOOR);
			content.insertAfter(ModBlocks.YEW_TRAPDOOR, ModBlocks.YEW_PRESSURE_PLATE);
			content.insertAfter(ModBlocks.YEW_PRESSURE_PLATE, ModBlocks.YEW_BUTTON);
			content.insertAfter(ModBlocks.YEW_BUTTON, ModBlocks.YEW_SHELF);
		});*/
		/*?}*/

		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Building Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);
	}

	private static void registerToNaturalBlocks() {
		EmeraldIsleFlora.LOGGER.info("Registering Items in Natural Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);

		/*? if <26.2 {*/
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
			content.addAfter(Blocks.WITHER_ROSE, ModBlocks.BELLS_OF_IRELAND);
			content.addAfter(ModBlocks.BELLS_OF_IRELAND, ModBlocks.BOG_ROSEMARY);
			content.addAfter(ModBlocks.BOG_ROSEMARY, ModBlocks.BULBOUS_BUTTERCUP);
			content.addAfter(ModBlocks.BULBOUS_BUTTERCUP, ModBlocks.BLUEBELL);
			content.addAfter(ModBlocks.BLUEBELL, ModBlocks.GROWN_BELLS_OF_IRELAND);
			content.addAfter(ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.GROWN_BOG_ROSEMARY);
			content.addAfter(ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.GROWN_BULBOUS_BUTTERCUP);
			content.addAfter(ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.GROWN_BLUEBELL);
			content.addAfter(Blocks.WARPED_STEM, ModBlocks.YEW_LOG);
			content.addAfter(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.YEW_LEAVES);
			content.addAfter(Blocks.FLOWERING_AZALEA, ModBlocks.YEW_SAPLING);
		});
		/*?} else {*/
		/*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(content -> {
			content.insertAfter(Blocks.WITHER_ROSE, ModBlocks.BELLS_OF_IRELAND);
			content.insertAfter(ModBlocks.BELLS_OF_IRELAND, ModBlocks.BOG_ROSEMARY);
			content.insertAfter(ModBlocks.BOG_ROSEMARY, ModBlocks.BULBOUS_BUTTERCUP);
			content.insertAfter(ModBlocks.BULBOUS_BUTTERCUP, ModBlocks.GROWN_BLUEBELL);
			content.insertAfter(ModBlocks.GROWN_BLUEBELL, ModBlocks.GROWN_BELLS_OF_IRELAND);
			content.insertAfter(ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.GROWN_BOG_ROSEMARY);
			content.insertAfter(ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.GROWN_BULBOUS_BUTTERCUP);
			content.insertAfter(ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.GROWN_BLUEBELL);
			content.insertAfter(Blocks.WARPED_STEM, ModBlocks.YEW_LOG);
			content.insertAfter(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.YEW_LEAVES);
			content.insertAfter(Blocks.FLOWERING_AZALEA, ModBlocks.YEW_SAPLING);
		});*/
		/*?}*/

		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Natural Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);
	}
	
	private static void registerToFunctionalBlocks() {
		EmeraldIsleFlora.LOGGER.info("Registering Items in Functional Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);

		/*? if <26.2 {*/
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
			content.addAfter(Blocks.WARPED_HANGING_SIGN, ModBlocks.YEW_SIGN);
			content.addAfter(ModBlocks.YEW_SIGN, ModBlocks.YEW_HANGING_SIGN);
		});
		/*?} else {*/
		/*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(content -> {
			content.insertAfter(Blocks.WARPED_HANGING_SIGN, ModBlocks.YEW_SIGN);
			content.insertAfter(ModBlocks.YEW_SIGN, ModBlocks.YEW_HANGING_SIGN);
		});*/
		/*?}*/

		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Functional Blocks Item Group for " + EmeraldIsleFlora.MOD_ID);
	}

	private static void registerToFoodAndDrinkBlocks() {
		EmeraldIsleFlora.LOGGER.info("Registering Items in Food and Drink Item Group for " + EmeraldIsleFlora.MOD_ID);

		/*? if <26.2 {*/
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
			content.addAfter(Items.BEETROOT, ModItems.YEW_BERRY);
		});
		/*?} else {*/
		/*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(content -> {
			content.insertAfter(Items.BEETROOT, ModItems.YEW_BERRY);
		});*/
		/*?}*/

		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Food and Drink Item Group for " + EmeraldIsleFlora.MOD_ID);
	}

	private static void registerToToolsBlocks() {
		EmeraldIsleFlora.LOGGER.info("Registering Items in Tools and Utilities Item Group for " + EmeraldIsleFlora.MOD_ID);

		/*? if <26.2 {*/
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
			content.addAfter(Items.BAMBOO_CHEST_RAFT, ModItems.YEW_BOAT);
			content.addAfter(ModItems.YEW_BOAT, ModItems.YEW_CHEST_BOAT);
		});
		/*?} else {*/
		/*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> {
			content.insertAfter(Items.BAMBOO_CHEST_RAFT, ModItems.YEW_BOAT);
			content.insertAfter(ModItems.YEW_BOAT, ModItems.YEW_CHEST_BOAT);
		});*/
		/*?}*/

		EmeraldIsleFlora.LOGGER.info("Finished registering Items in Tools and Utilities Item Group for " + EmeraldIsleFlora.MOD_ID);
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
						entries.add(ModBlocks.YEW_LOG);
						entries.add(ModBlocks.YEW_WOOD);
						entries.add(ModBlocks.STRIPPED_YEW_LOG);
						entries.add(ModBlocks.STRIPPED_YEW_WOOD);
						entries.add(ModBlocks.YEW_PLANKS);
						entries.add(ModBlocks.YEW_LEAVES);
						entries.add(ModBlocks.YEW_SAPLING);
						entries.add(ModBlocks.YEW_STAIRS);
						entries.add(ModBlocks.YEW_SLAB);
						entries.add(ModBlocks.YEW_FENCE);
						entries.add(ModBlocks.YEW_FENCE_GATE);
						entries.add(ModBlocks.YEW_DOOR);
						entries.add(ModBlocks.YEW_TRAPDOOR);
						entries.add(ModBlocks.YEW_PRESSURE_PLATE);
						entries.add(ModBlocks.YEW_BUTTON);
						entries.add(ModBlocks.YEW_SIGN);
						entries.add(ModBlocks.YEW_HANGING_SIGN);
						entries.add(ModItems.YEW_BERRY);
						entries.add(ModItems.YEW_BOAT);
						entries.add(ModItems.YEW_CHEST_BOAT);
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
						entries.accept(ModBlocks.YEW_LOG);
						entries.accept(ModBlocks.YEW_WOOD);
						entries.accept(ModBlocks.STRIPPED_YEW_LOG);
						entries.accept(ModBlocks.STRIPPED_YEW_WOOD);
						entries.accept(ModBlocks.YEW_PLANKS);
						entries.accept(ModBlocks.YEW_LEAVES);
						entries.accept(ModBlocks.YEW_SAPLING);
						entries.accept(ModBlocks.YEW_STAIRS);
						entries.accept(ModBlocks.YEW_SLAB);
						entries.accept(ModBlocks.YEW_FENCE);
						entries.accept(ModBlocks.YEW_FENCE_GATE);
						entries.accept(ModBlocks.YEW_DOOR);
						entries.accept(ModBlocks.YEW_TRAPDOOR);
						entries.accept(ModBlocks.YEW_PRESSURE_PLATE);
						entries.accept(ModBlocks.YEW_BUTTON);
						entries.accept(ModBlocks.YEW_SIGN);
						entries.accept(ModBlocks.YEW_HANGING_SIGN);
						entries.accept(ModBlocks.YEW_SHELF);
						entries.accept(ModItems.YEW_BERRY);
						entries.accept(ModItems.YEW_BOAT);
						entries.accept(ModItems.YEW_CHEST_BOAT);
					})
					.build();
			helper.register(EMERALD_ISLE_FLORA_GROUP.identifier(), group);
		});
	}
	*/
	/*?}*/

	/*? if forge {*/
	/*
	// Forge equivalent of Fabric's ItemGroupEvents.modifyEntriesEvent. BuildCreativeModeTabContentsEvent
	// itself only has a plain accept(...); ordered placement instead goes through its backing
	// MutableHashedLinkedMap's putAfter(existing, new, visibility) - Forge's analogue of Fabric's
	// insertAfter(...) - so entries land in the same spot as the Fabric build.
	public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == ItemGroups.NATURAL) {
			event.getEntries().putAfter(new ItemStack(Blocks.WITHER_ROSE), new ItemStack(ModBlocks.BELLS_OF_IRELAND), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.BELLS_OF_IRELAND), new ItemStack(ModBlocks.BOG_ROSEMARY), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.BOG_ROSEMARY), new ItemStack(ModBlocks.BULBOUS_BUTTERCUP), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.BULBOUS_BUTTERCUP), new ItemStack(ModBlocks.BLUEBELL), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.BLUEBELL), new ItemStack(ModBlocks.GROWN_BELLS_OF_IRELAND), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.GROWN_BELLS_OF_IRELAND), new ItemStack(ModBlocks.GROWN_BOG_ROSEMARY), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.GROWN_BOG_ROSEMARY), new ItemStack(ModBlocks.GROWN_BULBOUS_BUTTERCUP), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.GROWN_BULBOUS_BUTTERCUP), new ItemStack(ModBlocks.GROWN_BLUEBELL), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(Blocks.WARPED_STEM), new ItemStack(ModBlocks.YEW_LOG), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(Blocks.FLOWERING_AZALEA_LEAVES), new ItemStack(ModBlocks.YEW_LEAVES), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(Blocks.FLOWERING_AZALEA), new ItemStack(ModBlocks.YEW_SAPLING), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == ItemGroups.BUILDING_BLOCKS) {
			event.getEntries().putAfter(new ItemStack(Blocks.WARPED_BUTTON), new ItemStack(ModBlocks.YEW_LOG), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_LOG), new ItemStack(ModBlocks.YEW_WOOD), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_WOOD), new ItemStack(ModBlocks.STRIPPED_YEW_LOG), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.STRIPPED_YEW_LOG), new ItemStack(ModBlocks.STRIPPED_YEW_WOOD), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.STRIPPED_YEW_WOOD), new ItemStack(ModBlocks.YEW_PLANKS), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_PLANKS), new ItemStack(ModBlocks.YEW_STAIRS), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_STAIRS), new ItemStack(ModBlocks.YEW_SLAB), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_SLAB), new ItemStack(ModBlocks.YEW_FENCE), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_FENCE), new ItemStack(ModBlocks.YEW_FENCE_GATE), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_FENCE_GATE), new ItemStack(ModBlocks.YEW_DOOR), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_DOOR), new ItemStack(ModBlocks.YEW_TRAPDOOR), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_TRAPDOOR), new ItemStack(ModBlocks.YEW_PRESSURE_PLATE), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_PRESSURE_PLATE), new ItemStack(ModBlocks.YEW_BUTTON), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == ItemGroups.FUNCTIONAL) {
			event.getEntries().putAfter(new ItemStack(Blocks.WARPED_HANGING_SIGN), new ItemStack(ModBlocks.YEW_SIGN), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModBlocks.YEW_SIGN), new ItemStack(ModBlocks.YEW_HANGING_SIGN), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == ItemGroups.FOOD_AND_DRINK) {
			event.getEntries().putAfter(new ItemStack(Items.BEETROOT), new ItemStack(ModItems.YEW_BERRY), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == ItemGroups.TOOLS) {
			event.getEntries().putAfter(new ItemStack(Items.BAMBOO_CHEST_RAFT), new ItemStack(ModItems.YEW_BOAT), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.getEntries().putAfter(new ItemStack(ModItems.YEW_BOAT), new ItemStack(ModItems.YEW_CHEST_BOAT), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
		}
	}
	*/
	/*?}*/

	/*? if neoforge && <26.2 {*/
	/*
	// NeoForge's insertAfter(...) is the direct equivalent of Fabric's insertAfter(...) for
	// ordered placement, so entries land in the same spot as the Fabric build. Unlike Fabric's
	// insertAfter(ItemConvertible, ItemConvertible), it takes ItemStacks and an explicit visibility.
	public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == ItemGroups.NATURAL) {
			event.insertAfter(new ItemStack(Blocks.WITHER_ROSE), new ItemStack(ModBlocks.BELLS_OF_IRELAND), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.BELLS_OF_IRELAND), new ItemStack(ModBlocks.BOG_ROSEMARY), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.BOG_ROSEMARY), new ItemStack(ModBlocks.BULBOUS_BUTTERCUP), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.BULBOUS_BUTTERCUP), new ItemStack(ModBlocks.BLUEBELL), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.BLUEBELL), new ItemStack(ModBlocks.GROWN_BELLS_OF_IRELAND), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.GROWN_BELLS_OF_IRELAND), new ItemStack(ModBlocks.GROWN_BOG_ROSEMARY), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.GROWN_BOG_ROSEMARY), new ItemStack(ModBlocks.GROWN_BULBOUS_BUTTERCUP), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.GROWN_BULBOUS_BUTTERCUP), new ItemStack(ModBlocks.GROWN_BLUEBELL), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(Blocks.WARPED_STEM), new ItemStack(ModBlocks.YEW_LOG), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(Blocks.FLOWERING_AZALEA_LEAVES), new ItemStack(ModBlocks.YEW_LEAVES), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(Blocks.FLOWERING_AZALEA), new ItemStack(ModBlocks.YEW_SAPLING), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == ItemGroups.BUILDING_BLOCKS) {
			event.insertAfter(new ItemStack(Blocks.WARPED_BUTTON), new ItemStack(ModBlocks.YEW_LOG), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_LOG), new ItemStack(ModBlocks.YEW_WOOD), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_WOOD), new ItemStack(ModBlocks.STRIPPED_YEW_LOG), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.STRIPPED_YEW_LOG), new ItemStack(ModBlocks.STRIPPED_YEW_WOOD), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.STRIPPED_YEW_WOOD), new ItemStack(ModBlocks.YEW_PLANKS), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_PLANKS), new ItemStack(ModBlocks.YEW_STAIRS), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_STAIRS), new ItemStack(ModBlocks.YEW_SLAB), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_SLAB), new ItemStack(ModBlocks.YEW_FENCE), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_FENCE), new ItemStack(ModBlocks.YEW_FENCE_GATE), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_FENCE_GATE), new ItemStack(ModBlocks.YEW_DOOR), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_DOOR), new ItemStack(ModBlocks.YEW_TRAPDOOR), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_TRAPDOOR), new ItemStack(ModBlocks.YEW_PRESSURE_PLATE), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_PRESSURE_PLATE), new ItemStack(ModBlocks.YEW_BUTTON), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == ItemGroups.FUNCTIONAL) {
			event.insertAfter(new ItemStack(Blocks.WARPED_HANGING_SIGN), new ItemStack(ModBlocks.YEW_SIGN), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_SIGN), new ItemStack(ModBlocks.YEW_HANGING_SIGN), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == ItemGroups.FOOD_AND_DRINK) {
			event.insertAfter(new ItemStack(Items.BEETROOT), new ItemStack(ModItems.YEW_BERRY), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == ItemGroups.TOOLS) {
			event.insertAfter(new ItemStack(Items.BAMBOO_CHEST_RAFT), new ItemStack(ModItems.YEW_BOAT), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModItems.YEW_BOAT), new ItemStack(ModItems.YEW_CHEST_BOAT), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
		}
	}
	*/
	/*?}*/
	/*? if neoforge && >=26.2 {*/
	/*
	// 26.2: same insertAfter(...) API as pre-26.2 NeoForge, so entries land in the same spot as
	// the Fabric build, just under Mojmap names (CreativeModeTab.TabVisibility instead of Yarn's
	// ItemGroup.StackVisibility).
	public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.insertAfter(new ItemStack(Blocks.WITHER_ROSE), new ItemStack(ModBlocks.BELLS_OF_IRELAND), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.BELLS_OF_IRELAND), new ItemStack(ModBlocks.BOG_ROSEMARY), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.BOG_ROSEMARY), new ItemStack(ModBlocks.BULBOUS_BUTTERCUP), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.BULBOUS_BUTTERCUP), new ItemStack(ModBlocks.BLUEBELL), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.BLUEBELL), new ItemStack(ModBlocks.GROWN_BELLS_OF_IRELAND), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.GROWN_BELLS_OF_IRELAND), new ItemStack(ModBlocks.GROWN_BOG_ROSEMARY), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.GROWN_BOG_ROSEMARY), new ItemStack(ModBlocks.GROWN_BULBOUS_BUTTERCUP), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.GROWN_BULBOUS_BUTTERCUP), new ItemStack(ModBlocks.GROWN_BLUEBELL), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(Blocks.WARPED_STEM), new ItemStack(ModBlocks.YEW_LOG), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(Blocks.FLOWERING_AZALEA_LEAVES), new ItemStack(ModBlocks.YEW_LEAVES), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(Blocks.FLOWERING_AZALEA), new ItemStack(ModBlocks.YEW_SAPLING), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.insertAfter(new ItemStack(Blocks.WARPED_BUTTON), new ItemStack(ModBlocks.YEW_LOG), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_LOG), new ItemStack(ModBlocks.YEW_WOOD), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_WOOD), new ItemStack(ModBlocks.STRIPPED_YEW_LOG), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.STRIPPED_YEW_LOG), new ItemStack(ModBlocks.STRIPPED_YEW_WOOD), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.STRIPPED_YEW_WOOD), new ItemStack(ModBlocks.YEW_PLANKS), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_PLANKS), new ItemStack(ModBlocks.YEW_STAIRS), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_STAIRS), new ItemStack(ModBlocks.YEW_SLAB), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_SLAB), new ItemStack(ModBlocks.YEW_FENCE), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_FENCE), new ItemStack(ModBlocks.YEW_FENCE_GATE), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_FENCE_GATE), new ItemStack(ModBlocks.YEW_DOOR), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_DOOR), new ItemStack(ModBlocks.YEW_TRAPDOOR), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_TRAPDOOR), new ItemStack(ModBlocks.YEW_PRESSURE_PLATE), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_PRESSURE_PLATE), new ItemStack(ModBlocks.YEW_BUTTON), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_BUTTON), new ItemStack(ModBlocks.YEW_SHELF), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			event.insertAfter(new ItemStack(Blocks.WARPED_HANGING_SIGN), new ItemStack(ModBlocks.YEW_SIGN), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModBlocks.YEW_SIGN), new ItemStack(ModBlocks.YEW_HANGING_SIGN), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			event.insertAfter(new ItemStack(Items.BEETROOT), new ItemStack(ModItems.YEW_BERRY), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			return;
		}

		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.insertAfter(new ItemStack(Items.BAMBOO_CHEST_RAFT), new ItemStack(ModItems.YEW_BOAT), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(new ItemStack(ModItems.YEW_BOAT), new ItemStack(ModItems.YEW_CHEST_BOAT), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		}
	}
	*/
	/*?}*/
}
