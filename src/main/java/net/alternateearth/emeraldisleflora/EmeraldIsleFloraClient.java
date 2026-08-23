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
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;*/
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
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;*/
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
		/*?} else {*/
		/*
		// 26.2: SignBlockEntityRenderer was replaced by StandingSignRenderer.
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_SIGN, StandingSignRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_HANGING_SIGN, HangingSignRenderer::new);
		*/
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
// Split into two flat, mutually-exclusive blocks instead of a nested <26.2/else marker,
// since a nested Stonecutter marker would be invisible inside this disabled block comment.
/*? if forgeLike && <26.2 {*/
/*public final class EmeraldIsleFloraClient {
	private EmeraldIsleFloraClient() { }

	// Forge/NeoForge's equivalent client-only extension point.
	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_SIGN, SignBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_HANGING_SIGN, HangingSignBlockEntityRenderer::new);
	}
}*/
/*?}*/
/*? if forgeLike && >=26.2 {*/
/*public final class EmeraldIsleFloraClient {
	private EmeraldIsleFloraClient() { }

	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_SIGN, StandingSignRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.YEW_HANGING_SIGN, HangingSignRenderer::new);
	}
}*/
/*?}*/
