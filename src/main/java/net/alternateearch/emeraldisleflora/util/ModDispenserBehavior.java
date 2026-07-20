package net.alternateearch.emeraldisleflora.util;

import net.alternateearch.emeraldisleflora.EmeraldIsleFlora;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public final class ModDispenserBehavior {
    public static void register() {
        EmeraldIsleFlora.LOGGER.info("Registering Dispenser Behavior for " + EmeraldIsleFlora.MOD_ID);

        DispenserBlock.registerBehavior(Items.BONE_MEAL, new FallibleItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                World world = pointer.getWorld();
                BlockPos targetPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));

                // If we're on the server, try to grow or harvest the block at the target position. If successful, decrement the stack and return it.
                if (!world.isClient() && world instanceof ServerWorld serverWorld && ModCommonLogic.growOrHarvest(serverWorld, targetPos)) {
                    this.setSuccess(true);
                    stack.decrement(1);
                    return stack;
                }

                // Vanilla fallback: fertilizing crops, growing grass/saplings from
                // dispensed-on-ground bone meal, etc. - see the javadoc above.
                if (!BoneMealItem.useOnFertilizable(stack, world, targetPos)
                        && !BoneMealItem.useOnGround(stack, world, targetPos, (Direction) null)) {
                    this.setSuccess(false);
                } else {
                    this.setSuccess(true);
                    if (!world.isClient()) {
                        world.syncWorldEvent(1505, targetPos, 0);
                    }
                }

                return stack;
            }
        });

        EmeraldIsleFlora.LOGGER.info("Finished registering Dispenser Behavior for " + EmeraldIsleFlora.MOD_ID);
    }
}
