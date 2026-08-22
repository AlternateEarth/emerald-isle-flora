package net.alternateearth.emeraldisleflora.mixin.client;

/*? if >=1.21 && <26.2 {*/
/*import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.minecraft.block.WoodType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/^*
 * Hanging-sign counterpart to {@link SignBlockEntityRendererMixin} (same root cause) -
 * needs its own mixin since {@code HangingSignBlockEntityRenderer} overrides
 * {@code getTextureId} with its own method body rather than inheriting it.
 ^/
@Mixin(HangingSignBlockEntityRenderer.class)
public class HangingSignBlockEntityRendererMixin {

    @Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
    private void emeraldisleflora$fixYewHangingSignTexture(WoodType type, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if (type == ModBlocks.YEW_WOOD_TYPE) {
            Identifier woodTypeId = Identifier.of(type.name());
            cir.setReturnValue(new SpriteIdentifier(
                    TexturedRenderLayers.SIGNS_ATLAS_TEXTURE,
                    Identifier.of(woodTypeId.getNamespace(), "entity/signs/hanging/" + woodTypeId.getPath())));
        }
    }
}
*//*?}*/
