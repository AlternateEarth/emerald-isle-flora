package net.alternateearth.emeraldisleflora.util;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
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
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
*/
/*?}*/

public final class ModDispenserBehavior {
    public static void register() {
        EmeraldIsleFlora.LOGGER.info("Registering Dispenser Behavior for " + EmeraldIsleFlora.MOD_ID);

        /*? if <26.2 {*/
        DispenserBlock.registerBehavior(Items.BONE_MEAL, new FallibleItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                /*? if <1.21 {*/
                World world = pointer.getWorld();
                BlockPos targetPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                /*?} else {*/
                /*
                World world = pointer.world();
                BlockPos targetPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                */
                /*?}*/

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
        /*?} else {*/
        /*
        // 26.2: FallibleItemDispenserBehavior doesn't exist anymore - its equivalent
        // (still has setSuccess/isSuccess) is OptionalDispenseItemBehavior, one level up
        // the hierarchy from the more basic DefaultDispenseItemBehavior. BoneMealItem's
        // own API changed too:
        // useOnFertilizable(...)/useOnGround(...) were replaced by growCrop(...) (the
        // generic BonemealableBlock-interface dispatch - despite the name, this is what
        // grows grass/saplings/etc., not just crops) and growWaterPlant(...) (seagrass
        // specifically). growWaterPlant dereferences its Direction argument (calls
        // .getAxis() on it), so - unlike the old useOnGround call, which tolerated a null
        // direction - passing null here would NPE; use the dispenser's own facing instead,
        // which we already have on hand for computing targetPos.
        DispenserBlock.registerBehavior(Items.BONE_MEAL, new OptionalDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource pointer, ItemStack stack) {
                Direction facing = pointer.state().getValue(DispenserBlock.FACING);
                Level world = pointer.level();
                BlockPos targetPos = pointer.pos().relative(facing);

                if (!world.isClientSide() && world instanceof ServerLevel serverWorld && ModCommonLogic.growOrHarvest(serverWorld, targetPos)) {
                    this.setSuccess(true);
                    stack.shrink(1);
                    return stack;
                }

                if (!BoneMealItem.growCrop(stack, world, targetPos)
                        && !BoneMealItem.growWaterPlant(stack, world, targetPos, facing)) {
                    this.setSuccess(false);
                } else {
                    this.setSuccess(true);
                    if (!world.isClientSide()) {
                        world.levelEvent(1505, targetPos, 0);
                    }
                }

                return stack;
            }
        });
        */
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering Dispenser Behavior for " + EmeraldIsleFlora.MOD_ID);
    }
}
