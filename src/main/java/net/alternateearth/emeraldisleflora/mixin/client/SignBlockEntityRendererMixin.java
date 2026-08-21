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
 * Fixes vanilla's own sign texture lookup for {@link ModBlocks#YEW_WOOD_TYPE} - covers the
 * regular (non-hanging) sign; see {@link HangingSignBlockEntityRendererMixin} for the
 * hanging sign, which needs its own separate mixin since
 * {@code HangingSignBlockEntityRenderer} overrides {@code getTextureId} with its own method
 * body rather than inheriting this class's.
 * <p>
 * Fabric API's {@code WoodTypeBuilder#register(Identifier, BlockSetType)} (the only
 * registration path for a real {@link WoodType} from 1.21 on, per {@link
 * net.alternateearth.emeraldisleflora.registry.ModWoodTypes}'s own doc comment) stores the
 * <i>full</i> {@code "emeraldisleflora:yew"} identifier string as the resulting
 * {@code WoodType}'s {@code name()} - confirmed via {@code javap} against the real Fabric
 * API 15.2.1 jar, not assumed. Vanilla's own
 * {@code TexturedRenderLayers#createSignTextureId(WoodType)} then builds the sprite's
 * {@code Identifier} as {@code Identifier.ofVanilla("entity/signs/" + type.name())} -
 * {@code ofVanilla} hardcodes the {@code minecraft} namespace unconditionally (confirmed via
 * {@code javap}) and does not split on {@code :}, so the colon already embedded in
 * {@code type.name()} ends up baked verbatim into the resulting path, producing a
 * nonsensical, never-stitched sprite ID like
 * {@code minecraft:entity/signs/emeraldisleflora:yew} - the sign silently renders with no
 * texture at all (not vanilla's usual missing-texture checkerboard, since this is an atlas
 * sprite lookup, not a model texture binding).
 * <p>
 * This is the same underlying vanilla limitation TerraformersMC's real, published Terraform
 * Wood API library works around for its own custom sign wood types (confirmed by reading
 * that library's real source, {@code TerraformersMC/Terraform}, branch {@code 1.21}) - a
 * targeted {@code @Inject} at the {@code HEAD} of {@code getTextureId}, overriding the
 * result for the specific wood type that needs it, rather than trying to make vanilla's own
 * {@code WoodType.name()}-based resolution work unmodified.
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
