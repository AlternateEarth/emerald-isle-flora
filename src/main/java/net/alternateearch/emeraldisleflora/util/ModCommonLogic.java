package net.alternateearch.emeraldisleflora.util;

import net.alternateearch.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearch.emeraldisleflora.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public final class ModCommonLogic {
    public static boolean growOrHarvest(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        boolean harvestingEnabled = EmeraldIsleFlora.getConfig().enableGrownFlowerHarvesting;
        boolean growingEnabled = EmeraldIsleFlora.getConfig().enableGrownFlowering;

        if (state.isOf(ModBlocks.BELLS_OF_IRELAND)) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockState(pos, ModBlocks.GROWN_BELLS_OF_IRELAND.getDefaultState());
        } else if (state.isOf(ModBlocks.POTTED_BELLS_OF_IRELAND)) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockState(pos, ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND.getDefaultState());
        } else if (state.isOf(ModBlocks.GROWN_BELLS_OF_IRELAND) || state.isOf(ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND)) {
            if (!harvestingEnabled) {
                return false;
            }
            Block.dropStack(world, pos, new ItemStack(ModBlocks.BELLS_OF_IRELAND.asItem()));
            world.emitGameEvent(null, GameEvent.ENTITY_PLACE, pos);
        } else {
            return false;
        }

        world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.spawnParticles(
                ParticleTypes.HAPPY_VILLAGER,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                10, 0.25, 0.25, 0.25, 0.0);
        return true;
    }
}
