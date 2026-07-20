package net.alternateearch.emeraldisleflora.util;

import net.alternateearch.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearch.emeraldisleflora.registry.ModBlocks;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public final class ModBoneMealInteraction {
    public static void register() {
        EmeraldIsleFlora.LOGGER.info("Registering Bone Meal Interaction for " + EmeraldIsleFlora.MOD_ID);

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            // Don't do anyething if the player is in spectator mode.
            if (player.isSpectator()) {
                return ActionResult.PASS;
            }

            // Don't do anything if the player isn't holding bone meal.
            ItemStack heldStack = player.getStackInHand(hand);
            if (!heldStack.isOf(Items.BONE_MEAL)) {
                return ActionResult.PASS;
            }

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);

            // Determine the appropriate action based on the block state.
            Item dropItem;
            Block resultBlock;
            if (state.isOf(ModBlocks.BELLS_OF_IRELAND)) {
                dropItem = null;
                resultBlock = ModBlocks.GROWN_BELLS_OF_IRELAND;
            } else if (state.isOf(ModBlocks.GROWN_BELLS_OF_IRELAND)) {
                dropItem = ModBlocks.BELLS_OF_IRELAND.asItem();
                resultBlock = null;
            } else {
                return ActionResult.PASS;
            }

            // If we're on the server, perform the action: either replace the block or drop an item.
            if (!world.isClient() && world instanceof ServerWorld serverWorld) {
                if (resultBlock != null) {
                    world.setBlockState(pos, resultBlock.getDefaultState());
                } else {
                    Block.dropStack(world, pos, new ItemStack(dropItem));
                }

                // If the player isn't in creative mode, consume one bone meal from their hand.
                if (!player.getAbilities().creativeMode) {
                    heldStack.decrement(1);
                }

                world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                serverWorld.spawnParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        10, 0.25, 0.25, 0.25, 0.0);
            }

            return ActionResult.SUCCESS;
        });

        EmeraldIsleFlora.LOGGER.info("Finished registering Bone Meal Interaction for " + EmeraldIsleFlora.MOD_ID);
    }
}
