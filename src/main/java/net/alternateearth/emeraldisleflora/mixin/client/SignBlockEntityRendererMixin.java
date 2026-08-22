package net.alternateearth.emeraldisleflora.mixin.client;

/*? if >=1.21 && <26.2 {*/
/*import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.minecraft.block.WoodType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/^*
 * Fixes the regular sign's texture: Fabric API's {@code WoodTypeBuilder} stores the full
 * {@code "emeraldisleflora:yew"} string as the {@code WoodType} name, but vanilla's sprite-id
 * builder hardcodes the {@code minecraft} namespace and doesn't split on {@code :}, so the
 * sprite never resolves. See {@link HangingSignBlockEntityRendererMixin} for the hanging sign.
 ^/
@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {

    @Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
    private void emeraldisleflora$fixYewSignTexture(WoodType type, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if (type == ModBlocks.YEW_WOOD_TYPE) {
            Identifier woodTypeId = Identifier.of(type.name());
            cir.setReturnValue(new SpriteIdentifier(
                    TexturedRenderLayers.SIGNS_ATLAS_TEXTURE,
                    Identifier.of(woodTypeId.getNamespace(), "entity/signs/" + woodTypeId.getPath())));
        }
    }
}
*//*?}*/
