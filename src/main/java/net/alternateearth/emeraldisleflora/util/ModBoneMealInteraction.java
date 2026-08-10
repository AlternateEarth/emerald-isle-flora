package net.alternateearth.emeraldisleflora.util;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if fabric {*/
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
/*?}*/
/*? if <26.2 {*/
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
*/
/*?}*/
/*? if forge {*/
/*import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
*/
/*?}*/
/*? if neoforge && <26.2 {*/
/*import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
*/
/*?}*/
/*? if neoforge && >=26.2 {*/
/*import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
*/
/*?}*/

public final class ModBoneMealInteraction {
    public static void register() {
        EmeraldIsleFlora.LOGGER.info("Registering Bone Meal Interaction for " + EmeraldIsleFlora.MOD_ID);

        /*? if fabric {*/
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            // Don't do anyething if the player is in spectator mode.
            if (player.isSpectator()) {
                /*? if <26.2 {*/
                return ActionResult.PASS;
                /*?} else {*/
                /*return InteractionResult.PASS;*/
                /*?}*/
            }

            // Don't do anything if the player isn't holding bone meal.
            /*? if <26.2 {*/
            ItemStack heldStack = player.getStackInHand(hand);
            if (!heldStack.isOf(Items.BONE_MEAL)) {
                return ActionResult.PASS;
            }
            /*?} else {*/
            /*ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.getItem() != Items.BONE_MEAL) {
                return InteractionResult.PASS;
            }*/
            /*?}*/

            BlockPos pos = hitResult.getBlockPos();

            if (!ModCommonLogic.isModManaged(world.getBlockState(pos))) {
                /*? if <26.2 {*/
                return ActionResult.PASS;
                /*?} else {*/
                /*return InteractionResult.PASS;*/
                /*?}*/
            }

            // If we're on the server, perform the action: either replace the block or drop an item.
            /*? if <26.2 {*/
            if (!world.isClient() && world instanceof ServerWorld serverWorld) {
                if (ModCommonLogic.growOrHarvest(serverWorld, pos) && !player.getAbilities().creativeMode) {
                    heldStack.decrement(1);
                }
            }

            return ActionResult.SUCCESS;
            /*?} else {*/
            /*if (!world.isClientSide() && world instanceof ServerLevel serverWorld) {
                if (ModCommonLogic.growOrHarvest(serverWorld, pos) && !player.getAbilities().instabuild) {
                    heldStack.shrink(1);
                }
            }

            return InteractionResult.SUCCESS;*/
            /*?}*/
        });
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering Bone Meal Interaction for " + EmeraldIsleFlora.MOD_ID);
    }

    /*? if forge {*/
    /*
    // Forge/NeoForge equivalent of the Fabric UseBlockCallback registered above,
    // subscribed on the global event bus (not the mod bus - this is a gameplay event,
    // not a startup/lifecycle event).
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

        if (!ModCommonLogic.isModManaged(world.getBlockState(pos))) {
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

    /*? if neoforge && <26.2 {*/
    /*
    // Forge/NeoForge equivalent of the Fabric UseBlockCallback registered above,
    // subscribed on the global event bus (not the mod bus - this is a gameplay event,
    // not a startup/lifecycle event).
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

        if (!ModCommonLogic.isModManaged(world.getBlockState(pos))) {
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
    /*? if neoforge && >=26.2 {*/
    /*
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();

        if (player.isSpectator()) {
            return;
        }

        ItemStack heldStack = event.getItemStack();
        if (heldStack.getItem() != Items.BONE_MEAL) {
            return;
        }

        BlockPos pos = event.getPos();
        Level world = event.getLevel();

        if (!ModCommonLogic.isModManaged(world.getBlockState(pos))) {
            return;
        }

        if (!world.isClientSide() && world instanceof ServerLevel serverWorld) {
            if (ModCommonLogic.growOrHarvest(serverWorld, pos) && !player.getAbilities().instabuild) {
                heldStack.shrink(1);
            }
        }

        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }
    */
    /*?}*/
}
