package net.alternateearth.emeraldisleflora.mixin;

import net.alternateearth.emeraldisleflora.registry.ModBlocks;
/*? if <26.2 {*/
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
/*?} else {*/
/*import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Widens vanilla's hardcoded {@code BlockEntityType.HANGING_SIGN} block-set check (which
 * otherwise crashes on any modded block) to also accept {@link ModBlocks#YEW_HANGING_SIGN}/
 * {@link ModBlocks#YEW_WALL_HANGING_SIGN}. Method is {@code supports} on Yarn, {@code isValid}
 * on Mojmap; missing on 1.20.1, tolerated via the mixin config's {@code "required": false}.
 */
/*? if <26.2 {*/
@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    private void emeraldisleflora$supportYewHangingSign(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        Block block = state.getBlock();
        if ((block == ModBlocks.YEW_HANGING_SIGN || block == ModBlocks.YEW_WALL_HANGING_SIGN)
                && BlockEntityType.HANGING_SIGN.equals((BlockEntityType<?>) (Object) this)) {
            cir.setReturnValue(true);
        }
    }
}
/*?} else {*/
/*
@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void emeraldisleflora$supportYewHangingSign(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        Block block = state.getBlock();
        if ((block == ModBlocks.YEW_HANGING_SIGN || block == ModBlocks.YEW_WALL_HANGING_SIGN)
                && BlockEntityTypes.HANGING_SIGN.equals((BlockEntityType<?>) (Object) this)) {
            cir.setReturnValue(true);
        }
    }
}
*/
/*?}*/
