package net.alternateearth.emeraldisleflora.registry;

/*? if <1.21.11 {*/
import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.ChestBoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ModelWithWaterPatch;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

public class ModBoatEntityRenderer extends BoatEntityRenderer {

    private final Identifier texture;
    private final EntityModel<BoatEntity> model;

    public ModBoatEntityRenderer(EntityRendererFactory.Context context, boolean chest) {
        super(context, chest);
        this.shadowRadius = 0.8F;
        this.texture = Identifier.of(EmeraldIsleFlora.MOD_ID, chest ? "textures/entity/chest_boat/yew.png" : "textures/entity/boat/yew.png");
        EntityModelLayer layer = new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, (chest ? "chest_boat/" : "boat/") + "yew"), "main");
        ModelPart part = context.getPart(layer);
        this.model = chest ? new ChestBoatEntityModel(part) : new BoatEntityModel(part);
    }

    @Override
    public void render(BoatEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0.0F, 0.375F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - yaw));

        float hurtTime = entity.getDamageWobbleTicks() - tickDelta;
        float damage = entity.getDamageWobbleStrength() - tickDelta;
        if (damage < 0.0F) {
            damage = 0.0F;
        }
        if (hurtTime > 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.sin(hurtTime) * hurtTime * damage / 10.0F * (float) entity.getDamageWobbleSide()));
        }

        float bubbleAngle = entity.interpolateBubbleWobble(tickDelta);
        if (bubbleAngle != 0.0F) {
            matrices.multiply(new Quaternionf().rotateAxis(bubbleAngle * ((float) Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }

        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
        this.model.setAngles(entity, tickDelta, 0.0F, -0.1F, 0.0F, 0.0F);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.texture));
        /*? if <1.21 {*/
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        /*?} else {*/
        /*this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);*/
        /*?}*/

        if (!entity.isSubmergedInWater() && this.model instanceof ModelWithWaterPatch waterPatchModel) {
            VertexConsumer waterConsumer = vertexConsumers.getBuffer(RenderLayer.getWaterMask());
            waterPatchModel.getWaterPatch().render(matrices, waterConsumer, light, OverlayTexture.DEFAULT_UV);
        }

        matrices.pop();

        if (this.hasLabel(entity)) {
            /*? if <1.21 {*/
            this.renderLabelIfPresent(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
            /*?} else {*/
            /*this.renderLabelIfPresent(entity, entity.getDisplayName(), matrices, vertexConsumers, light, tickDelta);*/
            /*?}*/
        }
    }

    @Override
    public Identifier getTexture(BoatEntity entity) {
        return this.texture;
    }
}
/*?}*/
