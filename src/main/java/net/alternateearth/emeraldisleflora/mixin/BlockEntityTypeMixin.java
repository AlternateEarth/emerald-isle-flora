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
 * Widens vanilla's own, otherwise-immutable hanging-sign block-entity-type check to also
 * accept {@link ModBlocks#YEW_HANGING_SIGN}/{@link ModBlocks#YEW_WALL_HANGING_SIGN}.
 * <p>
 * {@code HangingSignBlockEntity}'s only constructor hardcodes vanilla's own static
 * {@code BlockEntityType.HANGING_SIGN}/{@code BlockEntityTypes.HANGING_SIGN} - confirmed via
 * {@code javap} against the real 1.21.1/1.21.11/26.2 (Fabric and NeoForge) jars, not
 * assumed - with no way to inject a custom type. Since 1.21.1 (confirmed absent on 1.20.1
 * via the same {@code javap} check), {@code BlockEntity}'s constructor calls a
 * validation method that reads that hardcoded type's raw block-set field directly (not the
 * virtual {@code getType()} override
 * {@link net.alternateearth.emeraldisleflora.registry.ModHangingSignBlockEntity} already
 * provides for every other consumer - ticker, renderer, etc., all genuinely fine) - so a
 * placed {@code emeraldisleflora:yew_hanging_sign} always fails vanilla's own,
 * hardcoded-at-bootstrap block-set membership check and crashes with an
 * {@code IllegalStateException}, unconditionally, every time.
 * <p>
 * That validation method is named {@code supports(BlockState)} on Yarn (1.20.1/1.21.1/
 * 1.21.11 alike - unchanged shape, only 1.20.1 lacks the method/check entirely) but
 * {@code isValid(BlockState)} on Mojmap (26.2) - confirmed via {@code javap}, not assumed
 * to land on the same name just because every other vanilla method in this codebase's
 * Yarn/Mojmap split kept its shape. The 1.20.1 case (method doesn't exist at all) is left
 * to the mixin config's {@code "required": false} to downgrade to a harmless warning rather
 * than a hard error, rather than a third Stonecutter branch, since 1.20.1 never needed this
 * fix in the first place - see {@code ModHangingSignBlockEntity}'s own doc comment for why.
 * <p>
 * This is the same technique TerraformersMC's real, published Terraform Wood API library
 * uses to solve the identical problem for its own custom sign wood types (confirmed by
 * reading that library's real source, {@code TerraformersMC/Terraform}, branch {@code 1.21}) -
 * a targeted {@code @Inject} at the {@code HEAD} of the validation method, cancelling with
 * {@code true} for the specific blocks that need it, rather than trying to mutate vanilla's
 * immutable block set or give up vanilla's own {@code HangingSignBlockEntity} (the latter
 * would also silently break the client's own hardcoded
 * {@code instanceof HangingSignBlockEntity} check that decides which sign-editor screen to
 * open - confirmed via {@code javap} against {@code ClientPlayerEntity.openEditSignScreen}).
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
