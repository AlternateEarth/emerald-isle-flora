package net.alternateearth.emeraldisleflora.util;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
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
    /*? if <26.2 {*/
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
        } else if (state.isOf(ModBlocks.BOG_ROSEMARY)) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockState(pos, ModBlocks.GROWN_BOG_ROSEMARY.getDefaultState());
        } else if (state.isOf(ModBlocks.POTTED_BOG_ROSEMARY)) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockState(pos, ModBlocks.POTTED_GROWN_BOG_ROSEMARY.getDefaultState());
        } else if (state.isOf(ModBlocks.GROWN_BOG_ROSEMARY) || state.isOf(ModBlocks.POTTED_GROWN_BOG_ROSEMARY)) {
            if (!harvestingEnabled) {
                return false;
            }
            Block.dropStack(world, pos, new ItemStack(ModBlocks.BOG_ROSEMARY.asItem()));
            world.emitGameEvent(null, GameEvent.ENTITY_PLACE, pos);
        } else if (state.isOf(ModBlocks.BULBOUS_BUTTERCUP)) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockState(pos, ModBlocks.GROWN_BULBOUS_BUTTERCUP.getDefaultState());
        } else if (state.isOf(ModBlocks.POTTED_BULBOUS_BUTTERCUP)) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockState(pos, ModBlocks.POTTED_GROWN_BULBOUS_BUTTERCUP.getDefaultState());
        } else if (state.isOf(ModBlocks.GROWN_BULBOUS_BUTTERCUP) || state.isOf(ModBlocks.POTTED_GROWN_BULBOUS_BUTTERCUP)) {
            if (!harvestingEnabled) {
                return false;
            }
            Block.dropStack(world, pos, new ItemStack(ModBlocks.BULBOUS_BUTTERCUP.asItem()));
            world.emitGameEvent(null, GameEvent.ENTITY_PLACE, pos);
        } else if (state.isOf(ModBlocks.BLUEBELL)) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockState(pos, ModBlocks.GROWN_BLUEBELL.getDefaultState());
        } else if (state.isOf(ModBlocks.POTTED_BLUEBELL)) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockState(pos, ModBlocks.POTTED_GROWN_BLUEBELL.getDefaultState());
        } else if (state.isOf(ModBlocks.GROWN_BLUEBELL) || state.isOf(ModBlocks.POTTED_GROWN_BLUEBELL)) {
            if (!harvestingEnabled) {
                return false;
            }
            Block.dropStack(world, pos, new ItemStack(ModBlocks.BLUEBELL.asItem()));
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
    /*?} else {*/
    /*
    // 26.2: same shape, Mojmap names - BlockState#isOf(Block) doesn't exist (only
    // Predicate-based is() overloads do, confirmed via javap), use getBlock() == X
    // singleton comparison instead (same technique already used in
    // ModBoneMealInteraction#isOurBlock); Block#getDefaultState() -> defaultBlockState();
    // World#emitGameEvent(Entity, GameEvent, BlockPos) -> LevelAccessor#gameEvent(Entity,
    // Holder<GameEvent>, BlockPos) (renamed, and GameEvent.ENTITY_PLACE is already a
    // Holder<GameEvent> in Mojmap, so it plugs in directly with no further wrapping).
    public static boolean growOrHarvest(ServerLevel world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        boolean harvestingEnabled = EmeraldIsleFlora.getConfig().enableGrownFlowerHarvesting;
        boolean growingEnabled = EmeraldIsleFlora.getConfig().enableGrownFlowering;

        if (state.getBlock() == ModBlocks.BELLS_OF_IRELAND) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockAndUpdate(pos, ModBlocks.GROWN_BELLS_OF_IRELAND.defaultBlockState());
        } else if (state.getBlock() == ModBlocks.POTTED_BELLS_OF_IRELAND) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockAndUpdate(pos, ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND.defaultBlockState());
        } else if (state.getBlock() == ModBlocks.GROWN_BELLS_OF_IRELAND || state.getBlock() == ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND) {
            if (!harvestingEnabled) {
                return false;
            }
            Block.popResource(world, pos, new ItemStack(ModBlocks.BELLS_OF_IRELAND.asItem()));
            world.gameEvent(null, GameEvent.ENTITY_PLACE, pos);
        } else if (state.getBlock() == ModBlocks.BOG_ROSEMARY) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockAndUpdate(pos, ModBlocks.GROWN_BOG_ROSEMARY.defaultBlockState());
        } else if (state.getBlock() == ModBlocks.POTTED_BOG_ROSEMARY) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockAndUpdate(pos, ModBlocks.POTTED_GROWN_BOG_ROSEMARY.defaultBlockState());
        } else if (state.getBlock() == ModBlocks.GROWN_BOG_ROSEMARY || state.getBlock() == ModBlocks.POTTED_GROWN_BOG_ROSEMARY) {
            if (!harvestingEnabled) {
                return false;
            }
            Block.popResource(world, pos, new ItemStack(ModBlocks.BOG_ROSEMARY.asItem()));
            world.gameEvent(null, GameEvent.ENTITY_PLACE, pos);
        } else if (state.getBlock() == ModBlocks.BULBOUS_BUTTERCUP) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockAndUpdate(pos, ModBlocks.GROWN_BULBOUS_BUTTERCUP.defaultBlockState());
        } else if (state.getBlock() == ModBlocks.POTTED_BULBOUS_BUTTERCUP) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockAndUpdate(pos, ModBlocks.POTTED_GROWN_BULBOUS_BUTTERCUP.defaultBlockState());
        } else if (state.getBlock() == ModBlocks.GROWN_BULBOUS_BUTTERCUP || state.getBlock() == ModBlocks.POTTED_GROWN_BULBOUS_BUTTERCUP) {
            if (!harvestingEnabled) {
                return false;
            }
            Block.popResource(world, pos, new ItemStack(ModBlocks.BULBOUS_BUTTERCUP.asItem()));
            world.gameEvent(null, GameEvent.ENTITY_PLACE, pos);
        } else if (state.getBlock() == ModBlocks.BLUEBELL) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockAndUpdate(pos, ModBlocks.GROWN_BLUEBELL.defaultBlockState());
        } else if (state.getBlock() == ModBlocks.POTTED_BLUEBELL) {
            if (!growingEnabled) {
                return false;
            }
            world.setBlockAndUpdate(pos, ModBlocks.POTTED_GROWN_BLUEBELL.defaultBlockState());
        } else if (state.getBlock() == ModBlocks.GROWN_BLUEBELL || state.getBlock() == ModBlocks.POTTED_GROWN_BLUEBELL) {
            if (!harvestingEnabled) {
                return false;
            }
            Block.popResource(world, pos, new ItemStack(ModBlocks.BLUEBELL.asItem()));
            world.gameEvent(null, GameEvent.ENTITY_PLACE, pos);
        } else {
            return false;
        }

        world.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.sendParticles(
                ParticleTypes.HAPPY_VILLAGER,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                10, 0.25, 0.25, 0.25, 0.0);
        return true;
    }
    */
    /*?}*/
}
