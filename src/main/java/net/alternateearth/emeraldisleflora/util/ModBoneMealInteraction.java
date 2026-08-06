package net.alternateearth.emeraldisleflora.util;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
/*? if fabric {*/
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
/*?}*/
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
/*? if forge {*/
/*import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
*/
/*?}*/

public final class ModBoneMealInteraction {
    public static void register() {
        EmeraldIsleFlora.LOGGER.info("Registering Bone Meal Interaction for " + EmeraldIsleFlora.MOD_ID);

        /*? if fabric {*/
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

            if (!isOurBlock(world.getBlockState(pos))) {
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
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering Bone Meal Interaction for " + EmeraldIsleFlora.MOD_ID);
    }

    /*? if forge {*/
    /*
    // Forge equivalent of the Fabric UseBlockCallback registered above, subscribed on
    // the global Forge event bus (not the mod bus - this is a gameplay event, not a
    // startup/lifecycle event).
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getEntity();

        if (player.isSpectator()) {
            return;
        }

        ItemStack heldStack = event.getItemStack();
        if (!heldStack.isOf(Items.BONE_MEAL)) {
            return;
        }

        BlockPos pos = event.getPos();
        World world = event.getLevel();

        if (!isOurBlock(world.getBlockState(pos))) {
            return;
        }

        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            if (ModCommonLogic.growOrHarvest(serverWorld, pos) && !player.getAbilities().creativeMode) {
                heldStack.decrement(1);
            }
        }

        event.setCancellationResult(ActionResult.SUCCESS);
        event.setCanceled(true);
    }
    */
    /*?}*/

    private static boolean isOurBlock(BlockState state) {
        return state.isOf(ModBlocks.BELLS_OF_IRELAND) ||
                state.isOf(ModBlocks.GROWN_BELLS_OF_IRELAND) ||
                state.isOf(ModBlocks.POTTED_BELLS_OF_IRELAND) ||
                state.isOf(ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND) ||
                state.isOf(ModBlocks.BOG_ROSEMARY) ||
                state.isOf(ModBlocks.GROWN_BOG_ROSEMARY) ||
                state.isOf(ModBlocks.POTTED_BOG_ROSEMARY) ||
                state.isOf(ModBlocks.POTTED_GROWN_BOG_ROSEMARY) ||
                state.isOf(ModBlocks.BULBOUS_BUTTERCUP) ||
                state.isOf(ModBlocks.GROWN_BULBOUS_BUTTERCUP) ||
                state.isOf(ModBlocks.POTTED_BULBOUS_BUTTERCUP) ||
                state.isOf(ModBlocks.POTTED_GROWN_BULBOUS_BUTTERCUP);
    }
}
