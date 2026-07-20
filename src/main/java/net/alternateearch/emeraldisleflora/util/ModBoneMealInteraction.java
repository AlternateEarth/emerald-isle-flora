package net.alternateearch.emeraldisleflora.util;

import net.alternateearch.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearch.emeraldisleflora.registry.ModBlocks;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
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

            // Don't do anything if the block isn't one of our custom blocks.
            if (!state.isOf(ModBlocks.BELLS_OF_IRELAND) && 
                !state.isOf(ModBlocks.GROWN_BELLS_OF_IRELAND) && 
                !state.isOf(ModBlocks.POTTED_BELLS_OF_IRELAND) && 
                !state.isOf(ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND)) {
                return ActionResult.PASS;
            }

            // If we're on the server, perform the action: either replace the block or drop an item.
            if (!world.isClient() && world instanceof ServerWorld serverWorld) {
                if (ModCommonLogic.growOrHarvest(serverWorld, pos) && !player.getAbilities().creativeMode) {
                    heldStack.decrement(1);
                }
            }

            return ActionResult.SUCCESS;
        });

        EmeraldIsleFlora.LOGGER.info("Finished registering Bone Meal Interaction for " + EmeraldIsleFlora.MOD_ID);
    }
}
