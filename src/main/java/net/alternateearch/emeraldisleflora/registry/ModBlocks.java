package net.alternateearch.emeraldisleflora.registry;

import net.alternateearch.emeraldisleflora.EmeraldIsleFlora;
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

    public static final Block BELLS_OF_IRELAND = new FlowerBlock(StatusEffects.REGENERATION, 100, FabricBlockSettings.copyOf(DANDELION));
    public static final Block POTTED_BELLS_OF_IRELAND = new FlowerPotBlock(BELLS_OF_IRELAND, FabricBlockSettings.copyOf(POTTED_DANDELION));

    public static void register() {
        register("bells_of_ireland", BELLS_OF_IRELAND, true);
        register("potted_bells_of_ireland", POTTED_BELLS_OF_IRELAND, false);

        // Register this block to be compostable.
        CompostingChanceRegistry.INSTANCE.add(BELLS_OF_IRELAND, 0.65f);
    }

    private static void register(String name, Block block, boolean includeItem) {
        Identifier identifier = new Identifier(EmeraldIsleFlora.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);

        if(includeItem) {
            Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        }
    }
}