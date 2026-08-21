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
 * Hanging-sign counterpart to {@link SignBlockEntityRendererMixin} - see that class's doc
 * comment for the full root-cause explanation (vanilla's {@code Identifier.ofVanilla(...)}
 * mishandling a {@code WoodType} name that already contains a colon). This class exists
 * separately because {@code HangingSignBlockEntityRenderer} overrides {@code getTextureId}
 * with its own method body (confirmed via {@code javap}) rather than inheriting
 * {@code SignBlockEntityRenderer}'s, calling
 * {@code TexturedRenderLayers#createHangingSignTextureId} instead of
 * {@code createSignTextureId} - same bug, different (and separately mixin-able) call site.
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
