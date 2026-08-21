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

	/** Fabric-only: registers directly. Forge instead uses {@link #onRegisterCreativeTab} / {@link #onBuildCreativeTabContents}. */
	public static void register() {
		/*? if fabric {*/
		EmeraldIsleFlora.LOGGER.info("Registering Item Groups for " + EmeraldIsleFlora.MOD_ID);

		registerToCustomGroup();
		registerToBuildingBlocks();
		registerToNaturalBlocks();
		registerToFunctionalBlocks();
		registerToFoodAndDrinkBlocks();

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
					entries.add(ModItems.YEW_BERRY);
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
					entries.accept(ModItems.YEW_BERRY);
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
			content.addAfter(ModBlocks.BULBOUS_BUTTERCUP, ModBlocks.GROWN_BELLS_OF_IRELAND);
			content.addAfter(ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.GROWN_BOG_ROSEMARY);
			content.addAfter(ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.GROWN_BULBOUS_BUTTERCUP);
			content.addAfter(ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BLUEBELL);
			content.addAfter(ModBlocks.BLUEBELL, ModBlocks.GROWN_BLUEBELL);
			content.addAfter(Blocks.WARPED_STEM, ModBlocks.YEW_LOG);
			content.addAfter(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.YEW_LEAVES);
			content.addAfter(Blocks.FLOWERING_AZALEA, ModBlocks.YEW_SAPLING);
		});
		/*?} else {*/
		/*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(content -> {
			content.insertAfter(Blocks.WITHER_ROSE, ModBlocks.BELLS_OF_IRELAND);
			content.insertAfter(ModBlocks.BELLS_OF_IRELAND, ModBlocks.BOG_ROSEMARY);
			content.insertAfter(ModBlocks.BOG_ROSEMARY, ModBlocks.BULBOUS_BUTTERCUP);
			content.insertAfter(ModBlocks.BULBOUS_BUTTERCUP, ModBlocks.GROWN_BELLS_OF_IRELAND);
			content.insertAfter(ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.GROWN_BOG_ROSEMARY);
			content.insertAfter(ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.GROWN_BULBOUS_BUTTERCUP);
			content.insertAfter(ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BLUEBELL);
			content.insertAfter(ModBlocks.BLUEBELL, ModBlocks.GROWN_BLUEBELL);
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
						entries.accept(ModItems.YEW_BERRY);
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
	// equivalent of Fabric's ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL) - and
	// Yew Berry into "Food and Drink", the equivalent for ItemGroups.FOOD_AND_DRINK.
	// Forge's BuildCreativeModeTabContentsEvent adds its own accept(Supplier) helper on
	// top of vanilla's ItemGroup.Entries - NeoForge's equivalent event doesn't have it,
	// see the neoforge branch below for the vanilla ItemGroup.Entries#add() equivalent.
	public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == ItemGroups.NATURAL) {
			event.accept(() -> ModBlocks.BELLS_OF_IRELAND);
			event.accept(() -> ModBlocks.BOG_ROSEMARY);
			event.accept(() -> ModBlocks.BULBOUS_BUTTERCUP);
			event.accept(() -> ModBlocks.BLUEBELL);
			event.accept(() -> ModBlocks.GROWN_BELLS_OF_IRELAND);
			event.accept(() -> ModBlocks.GROWN_BOG_ROSEMARY);
			event.accept(() -> ModBlocks.GROWN_BULBOUS_BUTTERCUP);
			event.accept(() -> ModBlocks.GROWN_BLUEBELL);
			return;
		}

		if (event.getTabKey() == ItemGroups.FOOD_AND_DRINK) {
			event.accept(() -> ModItems.YEW_BERRY);
		}
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
		if (event.getTabKey() == ItemGroups.NATURAL) {
			event.add(ModBlocks.BELLS_OF_IRELAND);
			event.add(ModBlocks.BOG_ROSEMARY);
			event.add(ModBlocks.BULBOUS_BUTTERCUP);
			event.add(ModBlocks.BLUEBELL);
			event.add(ModBlocks.GROWN_BELLS_OF_IRELAND);
			event.add(ModBlocks.GROWN_BOG_ROSEMARY);
			event.add(ModBlocks.GROWN_BULBOUS_BUTTERCUP);
			event.add(ModBlocks.GROWN_BLUEBELL);
			return;
		}

		if (event.getTabKey() == ItemGroups.FOOD_AND_DRINK) {
			event.add(ModItems.YEW_BERRY);
		}
	}
	*/
	/*?}*/
	/*? if neoforge && >=26.2 {*/
	/*
	// 26.2: vanilla's CreativeModeTab.Output (what BuildCreativeModeTabContentsEvent
	// implements) uses accept(ItemLike) as its own standard default method - not add(...)
	// (that was a Yarn-specific name on the older ItemGroup.Entries interface) - so this
	// is now the same accept(...) call used on every loader/registration path here.
	// NATURAL_BLOCKS/FOOD_AND_DRINKS referenced via CreativeModeTabs directly - both are
	// real public fields on the real jar (confirmed via javap), matching the same class
	// this file's own >=26.2 register() methods already reference.
	public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.accept(ModBlocks.BELLS_OF_IRELAND);
			event.accept(ModBlocks.BOG_ROSEMARY);
			event.accept(ModBlocks.BULBOUS_BUTTERCUP);
			event.accept(ModBlocks.BLUEBELL);
			event.accept(ModBlocks.GROWN_BELLS_OF_IRELAND);
			event.accept(ModBlocks.GROWN_BOG_ROSEMARY);
			event.accept(ModBlocks.GROWN_BULBOUS_BUTTERCUP);
			event.accept(ModBlocks.GROWN_BLUEBELL);
			return;
		}

		if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			event.accept(ModItems.YEW_BERRY);
		}
	}
	*/
	/*?}*/
}
