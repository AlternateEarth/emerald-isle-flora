package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
/*? if forge {*/
/*import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
*/
/*?}*/

import static net.minecraft.block.Blocks.*;

public final class ModBlocks {

    //---------------------------Flowers---------------------------
    public static final Block BELLS_OF_IRELAND = new FlowerBlock(StatusEffects.REGENERATION, 100, AbstractBlock.Settings.copy(DANDELION));
    public static final Block GROWN_BELLS_OF_IRELAND = new GrowableFlower(StatusEffects.REGENERATION, 100, AbstractBlock.Settings.copy(BELLS_OF_IRELAND));

    public static final Block BOG_ROSEMARY = new FlowerBlock(StatusEffects.NAUSEA, 150, AbstractBlock.Settings.copy(DANDELION));
    public static final Block GROWN_BOG_ROSEMARY = new GrowableFlower(StatusEffects.NAUSEA, 150, AbstractBlock.Settings.copy(BOG_ROSEMARY));

    public static final Block BULBOUS_BUTTERCUP = new FlowerBlock(StatusEffects.NAUSEA, 100, AbstractBlock.Settings.copy(DANDELION));
    public static final Block GROWN_BULBOUS_BUTTERCUP = new GrowableFlower(StatusEffects.NAUSEA, 100, AbstractBlock.Settings.copy(BULBOUS_BUTTERCUP));

    //-----------------------Potted Flowers------------------------
    public static final Block POTTED_BELLS_OF_IRELAND = new FlowerPotBlock(BELLS_OF_IRELAND, AbstractBlock.Settings.copy(POTTED_DANDELION));
    public static final Block POTTED_GROWN_BELLS_OF_IRELAND = new FlowerPotBlock(GROWN_BELLS_OF_IRELAND, AbstractBlock.Settings.copy(POTTED_BELLS_OF_IRELAND));

    public static final Block POTTED_BOG_ROSEMARY = new FlowerPotBlock(BOG_ROSEMARY, AbstractBlock.Settings.copy(POTTED_DANDELION));
    public static final Block POTTED_GROWN_BOG_ROSEMARY = new FlowerPotBlock(GROWN_BOG_ROSEMARY, AbstractBlock.Settings.copy(POTTED_BOG_ROSEMARY));

    public static final Block POTTED_BULBOUS_BUTTERCUP = new FlowerPotBlock(BULBOUS_BUTTERCUP, AbstractBlock.Settings.copy(POTTED_DANDELION));
    public static final Block POTTED_GROWN_BULBOUS_BUTTERCUP = new FlowerPotBlock(GROWN_BULBOUS_BUTTERCUP, AbstractBlock.Settings.copy(POTTED_BULBOUS_BUTTERCUP));

    /** Fabric-only: registers directly. Forge instead uses {@link #onRegister}, deferred to Forge's own RegisterEvent. */
    public static void register() {
        /*? if fabric {*/
        EmeraldIsleFlora.LOGGER.info("Registering Blocks for " + EmeraldIsleFlora.MOD_ID);

        register("bells_of_ireland", BELLS_OF_IRELAND, true);
        register("grown_bells_of_ireland", GROWN_BELLS_OF_IRELAND, true);
        register("potted_bells_of_ireland", POTTED_BELLS_OF_IRELAND, false);
        register("potted_grown_bells_of_ireland", POTTED_GROWN_BELLS_OF_IRELAND, false);

        register("bog_rosemary", BOG_ROSEMARY, true);
        register("grown_bog_rosemary", GROWN_BOG_ROSEMARY, true);
        register("potted_bog_rosemary", POTTED_BOG_ROSEMARY, false);
        register("potted_grown_bog_rosemary", POTTED_GROWN_BOG_ROSEMARY, false);

        register("bulbous_buttercup", BULBOUS_BUTTERCUP, true);
        register("grown_bulbous_buttercup", GROWN_BULBOUS_BUTTERCUP, true);
        register("potted_bulbous_buttercup", POTTED_BULBOUS_BUTTERCUP, false);
        register("potted_grown_bulbous_buttercup", POTTED_GROWN_BULBOUS_BUTTERCUP, false);

        registerComposting();

        EmeraldIsleFlora.LOGGER.info("Finished registering Blocks for " + EmeraldIsleFlora.MOD_ID);
        /*?}*/
    }

    /*? if fabric {*/
    private static void register(String name, Block block, boolean includeItem) {
        Identifier identifier = new Identifier(EmeraldIsleFlora.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);

        if(includeItem) {
            Registry.register(Registries.ITEM, identifier, new BlockItem(block, new Item.Settings()));
        }
    }
    /*?}*/

    /*? if forge {*/
    /*
    // Forge: blocks/items are already-built singletons (same instances Fabric uses),
    // just registered via the RegisterEvent helper instead of a direct Registry.register
    // call, since Forge's registries aren't open for modification until this event fires.
    public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BLOCKS, helper -> {
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), BELLS_OF_IRELAND);
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), GROWN_BELLS_OF_IRELAND);
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "potted_bells_of_ireland"), POTTED_BELLS_OF_IRELAND);
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "potted_grown_bells_of_ireland"), POTTED_GROWN_BELLS_OF_IRELAND);

            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), BOG_ROSEMARY);
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), GROWN_BOG_ROSEMARY);
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "potted_bog_rosemary"), POTTED_BOG_ROSEMARY);
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "potted_grown_bog_rosemary"), POTTED_GROWN_BOG_ROSEMARY);

            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), BULBOUS_BUTTERCUP);
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), GROWN_BULBOUS_BUTTERCUP);
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "potted_bulbous_buttercup"), POTTED_BULBOUS_BUTTERCUP);
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "potted_grown_bulbous_buttercup"), POTTED_GROWN_BULBOUS_BUTTERCUP);
        });

        event.register(ForgeRegistries.Keys.ITEMS, helper -> {
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), new BlockItem(BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), new BlockItem(GROWN_BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), new BlockItem(BOG_ROSEMARY, new Item.Settings()));
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), new BlockItem(GROWN_BOG_ROSEMARY, new Item.Settings()));
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), new BlockItem(BULBOUS_BUTTERCUP, new Item.Settings()));
            helper.register(new Identifier(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), new BlockItem(GROWN_BULBOUS_BUTTERCUP, new Item.Settings()));
        });

        registerComposting();
    }
    */
    /*?}*/

    private static void registerComposting() {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BELLS_OF_IRELAND, 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BELLS_OF_IRELAND, 0.95f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BOG_ROSEMARY, 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BOG_ROSEMARY, 0.95f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BULBOUS_BUTTERCUP, 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BULBOUS_BUTTERCUP, 0.95f);
    }
}