package net.alternateearth.emeraldisleflora.util;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;

import java.util.HashMap;
import java.util.Map;
/*? if <26.2 {*/
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
*/
/*?}*/

public final class ModCommonLogic {

    // One row per flower: the plain block, its grown form, and the potted version of
    // each. Adding a new flower to the mod means adding one row here - growOrHarvest()
    // and isModManaged() below are driven entirely off this table, no per-flower branches.
    private record FlowerVariants(Block plain, Block grown, Block pottedPlain, Block pottedGrown) {}

    private static final FlowerVariants[] FLOWERS = {
            new FlowerVariants(ModBlocks.BELLS_OF_IRELAND, ModBlocks.GROWN_BELLS_OF_IRELAND,
                    ModBlocks.POTTED_BELLS_OF_IRELAND, ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND),
            new FlowerVariants(ModBlocks.BOG_ROSEMARY, ModBlocks.GROWN_BOG_ROSEMARY,
                    ModBlocks.POTTED_BOG_ROSEMARY, ModBlocks.POTTED_GROWN_BOG_ROSEMARY),
            new FlowerVariants(ModBlocks.BULBOUS_BUTTERCUP, ModBlocks.GROWN_BULBOUS_BUTTERCUP,
                    ModBlocks.POTTED_BULBOUS_BUTTERCUP, ModBlocks.POTTED_GROWN_BULBOUS_BUTTERCUP),
            new FlowerVariants(ModBlocks.BLUEBELL, ModBlocks.GROWN_BLUEBELL,
                    ModBlocks.POTTED_BLUEBELL, ModBlocks.POTTED_GROWN_BLUEBELL),
    };

    // Ungrown block (plain or potted) -> the grown block it grows into.
    private static final Map<Block, Block> GROW_TARGETS = new HashMap<>();
    // Grown block (plain or potted) -> the plain block whose item drops on harvest.
    private static final Map<Block, Block> HARVEST_SOURCES = new HashMap<>();

    static {
        for (FlowerVariants flower : FLOWERS) {
            GROW_TARGETS.put(flower.plain(), flower.grown());
            GROW_TARGETS.put(flower.pottedPlain(), flower.pottedGrown());
            HARVEST_SOURCES.put(flower.grown(), flower.plain());
            HARVEST_SOURCES.put(flower.pottedGrown(), flower.plain());
        }
    }

    /** Whether this state is one of this mod's flower blocks, in any growth/potted state. */
    public static boolean isModManaged(BlockState state) {
        Block block = state.getBlock();
        return GROW_TARGETS.containsKey(block) || HARVEST_SOURCES.containsKey(block);
    }

    // The world-facing plumbing (setting block state, dropping items, sound/particles)
    // is genuinely different per mapping, so - like the rest of this codebase - it's
    // duplicated per branch below. Unlike the old growOrHarvest, this duplication no
    // longer scales with the number of flowers: it's a fixed cost paid once, and the
    // flower-specific logic above (FLOWERS/GROW_TARGETS/HARVEST_SOURCES) is shared.
    /*? if <26.2 {*/
    private static void itemSoundAndParticles(ServerWorld world, BlockPos pos){
        world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.spawnParticles(
                ParticleTypes.HAPPY_VILLAGER,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                10, 0.25, 0.25, 0.25, 0.0);
    }

    public static boolean growOrHarvest(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        Block growTarget = GROW_TARGETS.get(block);
        if (growTarget != null) {
            if (!EmeraldIsleFlora.getConfig().enableGrownFlowering) {
                return false;
            }
            world.setBlockState(pos, growTarget.getDefaultState());
            itemSoundAndParticles(world, pos);
            return true;
        }

        Block harvestSource = HARVEST_SOURCES.get(block);
        if (harvestSource != null) {
            if (!EmeraldIsleFlora.getConfig().enableGrownFlowerHarvesting) {
                return false;
            }
            Block.dropStack(world, pos, new ItemStack(harvestSource.asItem()));
            world.emitGameEvent(null, GameEvent.ENTITY_PLACE, pos);
            itemSoundAndParticles(world, pos);
            return true;
        }

        return false;
    }
    /*?} else {*/
    /*
    private static void itemSoundAndParticles(ServerLevel world, BlockPos pos){
        world.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.sendParticles(
                ParticleTypes.HAPPY_VILLAGER,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                10, 0.25, 0.25, 0.25, 0.0);
    }

    public static boolean growOrHarvest(ServerLevel world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        Block growTarget = GROW_TARGETS.get(block);
        if (growTarget != null) {
            if (!EmeraldIsleFlora.getConfig().enableGrownFlowering) {
                return false;
            }
            world.setBlockAndUpdate(pos, growTarget.defaultBlockState());
            itemSoundAndParticles(world, pos);
            return true;
        }

        Block harvestSource = HARVEST_SOURCES.get(block);
        if (harvestSource != null) {
            if (!EmeraldIsleFlora.getConfig().enableGrownFlowerHarvesting) {
                return false;
            }
            Block.popResource(world, pos, new ItemStack(harvestSource.asItem()));
            world.gameEvent(null, GameEvent.ENTITY_PLACE, pos);
            itemSoundAndParticles(world, pos);
            return true;
        }

        return false;
    }
    */
    /*?}*/
}
