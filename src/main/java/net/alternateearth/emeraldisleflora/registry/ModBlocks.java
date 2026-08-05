package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.minecraft.block.Blocks.*;

public final class ModBlocks {

    //---------------------------Flowers---------------------------
    public static final Block BELLS_OF_IRELAND = new FlowerBlock(StatusEffects.REGENERATION, 100, FabricBlockSettings.copyOf(DANDELION));
    public static final Block GROWN_BELLS_OF_IRELAND = new GrowableFlower(StatusEffects.REGENERATION, 100, FabricBlockSettings.copyOf(BELLS_OF_IRELAND));

    public static final Block BOG_ROSEMARY = new FlowerBlock(StatusEffects.NAUSEA, 150, FabricBlockSettings.copyOf(DANDELION));
    public static final Block GROWN_BOG_ROSEMARY = new GrowableFlower(StatusEffects.NAUSEA, 150, FabricBlockSettings.copyOf(BOG_ROSEMARY));

    public static final Block BULBOUS_BUTTERCUP = new FlowerBlock(StatusEffects.NAUSEA, 100, FabricBlockSettings.copyOf(DANDELION));
    public static final Block GROWN_BULBOUS_BUTTERCUP = new GrowableFlower(StatusEffects.NAUSEA, 100, FabricBlockSettings.copyOf(BULBOUS_BUTTERCUP));

    //-----------------------Potted Flowers------------------------
    public static final Block POTTED_BELLS_OF_IRELAND = new FlowerPotBlock(BELLS_OF_IRELAND, FabricBlockSettings.copyOf(POTTED_DANDELION));
    public static final Block POTTED_GROWN_BELLS_OF_IRELAND = new FlowerPotBlock(GROWN_BELLS_OF_IRELAND, FabricBlockSettings.copyOf(POTTED_BELLS_OF_IRELAND));

    public static final Block POTTED_BOG_ROSEMARY = new FlowerPotBlock(BOG_ROSEMARY, FabricBlockSettings.copyOf(POTTED_DANDELION));
    public static final Block POTTED_GROWN_BOG_ROSEMARY = new FlowerPotBlock(GROWN_BOG_ROSEMARY, FabricBlockSettings.copyOf(POTTED_BOG_ROSEMARY));

    public static final Block POTTED_BULBOUS_BUTTERCUP = new FlowerPotBlock(BULBOUS_BUTTERCUP, FabricBlockSettings.copyOf(POTTED_DANDELION));
    public static final Block POTTED_GROWN_BULBOUS_BUTTERCUP = new FlowerPotBlock(GROWN_BULBOUS_BUTTERCUP, FabricBlockSettings.copyOf(POTTED_BULBOUS_BUTTERCUP));

    public static void register() {
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

        // Register this block to be compostable.
        CompostingChanceRegistry.INSTANCE.add(BELLS_OF_IRELAND, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(GROWN_BELLS_OF_IRELAND, 0.95f);
        CompostingChanceRegistry.INSTANCE.add(BOG_ROSEMARY, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(GROWN_BOG_ROSEMARY, 0.95f);
        CompostingChanceRegistry.INSTANCE.add(BULBOUS_BUTTERCUP, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(GROWN_BULBOUS_BUTTERCUP, 0.95f);

        EmeraldIsleFlora.LOGGER.info("Finished registering Blocks for " + EmeraldIsleFlora.MOD_ID);
    }

    private static void register(String name, Block block, boolean includeItem) {
        Identifier identifier = new Identifier(EmeraldIsleFlora.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);

        if(includeItem) {
            Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        }
    }
}