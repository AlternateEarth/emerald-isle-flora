package net.alternateearth.emeraldisleflora;

/*? if fabric {*/
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.alternateearth.emeraldisleflora.registry.ModBlockEntities;
/*? if <26.2 {*/
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
/*? if <1.21.11 {*/
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
/*?} else {*/
/*import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
*/
/*?}*/
/*?} else {*/
/*import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.ShelfRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;*/
/*?}*/
/*?}*/
/*? if fabric {*/
import net.alternateearth.emeraldisleflora.registry.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
/*? if <1.21.11 {*/
import net.alternateearth.emeraldisleflora.registry.ModBoatEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.ChestBoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
/*?} else if <26.2 {*/
/*import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
*/
/*?} else {*/
/*import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.Identifier;
*/
/*?}*/
/*?}*/
// Forge/NeoForge use flat sibling blocks instead of a shared "forgeLike" wrapper, since a nested Stonecutter marker would be invisible inside this disabled block comment.
/*? if forgeLike && <26.2 {*/
/*import net.alternateearth.emeraldisleflora.registry.ModBlockEntities;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;*/
/*?}*/
/*? if forgeLike && >=26.2 {*/
/*import net.alternateearth.emeraldisleflora.registry.ModBlockEntities;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.ShelfRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;*/
/*?}*/
/*? if forgeLike && <1.21.11 {*/
/*import net.alternateearth.emeraldisleflora.registry.ModBoatEntityRenderer;
import net.alternateearth.emeraldisleflora.registry.ModEntities;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.ChestBoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;*/
/*?}*/
/*? if forgeLike && >=1.21.11 && <26.2 {*/
/*import net.alternateearth.emeraldisleflora.registry.ModEntities;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;*/
/*?}*/
/*? if forgeLike && >=26.2 {*/
/*import net.alternateearth.emeraldisleflora.registry.ModEntities;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.Identifier;*/
/*?}*/
/*? if forge {*/
/*import net.minecraftforge.client.event.EntityRenderersEvent;*/
/*?}*/
/*? if neoforge {*/
/*import net.neoforged.neoforge.client.event.EntityRenderersEvent;*/
/*?}*/

/**
 * Client-only setup; runs only on the physical client, never on a dedicated server. This project
 * uses an unsplit source set, so {@code @Environment(EnvType.CLIENT)} below is just a hint - real 
 * safety comes from only Fabric's client entrypoint and Mod Menu ever touching these classes.
 */
/*? if fabric {*/
@Environment(EnvType.CLIENT)
public class EmeraldIsleFloraClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		/*? if <26.2 {*/
		//---------------------------Flowers---------------------------
		putCutout(ModBlocks.BELLS_OF_IRELAND);
		putCutout(ModBlocks.GROWN_BELLS_OF_IRELAND);
		putCutout(ModBlocks.BOG_ROSEMARY);
		putCutout(ModBlocks.GROWN_BOG_ROSEMARY);
		putCutout(ModBlocks.BULBOUS_BUTTERCUP);
		putCutout(ModBlocks.GROWN_BULBOUS_BUTTERCUP);
		putCutout(ModBlocks.BLUEBELL);
		putCutout(ModBlocks.GROWN_BLUEBELL);

		//-----------------------Potted Flowers------------------------
		putCutout(ModBlocks.POTTED_BELLS_OF_IRELAND);
		putCutout(ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND);
		putCutout(ModBlocks.POTTED_BOG_ROSEMARY);
		putCutout(ModBlocks.POTTED_GROWN_BOG_ROSEMARY);
		putCutout(ModBlocks.POTTED_BULBOUS_BUTTERCUP);
		putCutout(ModBlocks.POTTED_GROWN_BULBOUS_BUTTERCUP);
		putCutout(ModBlocks.POTTED_BLUEBELL);
		putCutout(ModBlocks.POTTED_GROWN_BLUEBELL);

		//---------------------------Wood---------------------------
		putCutout(ModBlocks.YEW_LEAVES);
		putCutout(ModBlocks.YEW_SAPLING);
		putCutout(ModBlocks.YEW_DOOR);
		putCutout(ModBlocks.YEW_TRAPDOOR);
		/*?}*/
		// >=26.2: nothing to do here, render layers are auto-detected now.

		// Reuses vanilla's own sign renderer, which is generic by WoodType/attachment, not hardcoded to vanilla's sign blocks.
		/*? if <26.2 {*/
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_SIGN, SignBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_HANGING_SIGN, HangingSignBlockEntityRenderer::new);
		/*? if >=1.21.11 {*/
		/*BlockEntityRendererRegistry.register(ModBlockEntities.YEW_SHELF, ShelfBlockEntityRenderer::new);*/
		/*?}*/
		/*?} else {*/
		/*
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_SIGN, StandingSignRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_HANGING_SIGN, HangingSignRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_SHELF, ShelfRenderer::new);
		*/
		/*?}*/

		/*? if <1.21.11 {*/
		EntityModelLayerRegistry.registerModelLayer(new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main"), BoatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main"), ChestBoatEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(ModEntities.YEW_BOAT, context -> new ModBoatEntityRenderer(context, false));
		EntityRendererRegistry.register(ModEntities.YEW_CHEST_BOAT, context -> new ModBoatEntityRenderer(context, true));
		/*?} else if <26.2 {*/
		/*EntityModelLayerRegistry.registerModelLayer(new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main"), BoatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main"), BoatEntityModel::getChestTexturedModelData);
		EntityRendererRegistry.register(ModEntities.YEW_BOAT, context -> new BoatEntityRenderer(context, new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main")));
		EntityRendererRegistry.register(ModEntities.YEW_CHEST_BOAT, context -> new BoatEntityRenderer(context, new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main")));*/
		/*?} else {*/
		/*ModelLayerRegistry.registerModelLayer(new ModelLayerLocation(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main"), BoatModel::createBoatModel);
		ModelLayerRegistry.registerModelLayer(new ModelLayerLocation(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main"), BoatModel::createChestBoatModel);
		EntityRendererRegistry.register(ModEntities.YEW_BOAT, context -> new BoatRenderer(context, new ModelLayerLocation(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main")));
		EntityRendererRegistry.register(ModEntities.YEW_CHEST_BOAT, context -> new BoatRenderer(context, new ModelLayerLocation(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main")));*/
		/*?}*/
	}

	/*? if <26.2 {*/
	// <1.21.11 uses the fabric-blockrenderlayer-v1 BlockRenderLayerMap instance API; >=1.21.11 uses the newer fabric-rendering-v1 static API.
	/*? if <1.21.11 {*/
	private static void putCutout(Block block) {
		BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
	}
	/*?} else {*/
	/*private static void putCutout(Block block) {
		BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
	}*/
	/*?}*/
	/*?}*/
}
/*?}*/
// Split into flat, mutually-exclusive blocks instead of nesting a version marker inside an
// already-disabled comment block - see AGENTS.md's Stonecutter nesting gotcha.
/*? if forgeLike && <1.21.11 {*/
/*public final class EmeraldIsleFloraClient {
	private EmeraldIsleFloraClient() { }

	// Forge/NeoForge's equivalent client-only extension point.
	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_SIGN, SignBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_HANGING_SIGN, HangingSignBlockEntityRenderer::new);
		event.registerEntityRenderer(ModEntities.YEW_BOAT, context -> new ModBoatEntityRenderer(context, false));
		event.registerEntityRenderer(ModEntities.YEW_CHEST_BOAT, context -> new ModBoatEntityRenderer(context, true));
	}

	public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main"), BoatEntityModel::getTexturedModelData);
		event.registerLayerDefinition(new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main"), ChestBoatEntityModel::getTexturedModelData);
	}
}*/
/*?}*/
/*? if forgeLike && >=1.21.11 && <26.2 {*/
/*public final class EmeraldIsleFloraClient {
	private EmeraldIsleFloraClient() { }

	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_SIGN, SignBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_HANGING_SIGN, HangingSignBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_SHELF, ShelfBlockEntityRenderer::new);
		event.registerEntityRenderer(ModEntities.YEW_BOAT, context -> new BoatEntityRenderer(context, new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main")));
		event.registerEntityRenderer(ModEntities.YEW_CHEST_BOAT, context -> new BoatEntityRenderer(context, new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main")));
	}

	public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main"), BoatEntityModel::getTexturedModelData);
		event.registerLayerDefinition(new EntityModelLayer(Identifier.of(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main"), BoatEntityModel::getChestTexturedModelData);
	}
}*/
/*?}*/
/*? if forgeLike && >=26.2 {*/
/*public final class EmeraldIsleFloraClient {
	private EmeraldIsleFloraClient() { }

	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_SIGN, StandingSignRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_HANGING_SIGN, HangingSignRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_SHELF, ShelfRenderer::new);
		event.registerEntityRenderer(ModEntities.YEW_BOAT, context -> new BoatRenderer(context, new ModelLayerLocation(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main")));
		event.registerEntityRenderer(ModEntities.YEW_CHEST_BOAT, context -> new BoatRenderer(context, new ModelLayerLocation(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main")));
	}

	public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(new ModelLayerLocation(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "boat/yew"), "main"), BoatModel::createBoatModel);
		event.registerLayerDefinition(new ModelLayerLocation(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "chest_boat/yew"), "main"), BoatModel::createChestBoatModel);
	}
}*/
/*?}*/
