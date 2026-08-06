package net.alternateearth.emeraldisleflora;

/*? if fabric {*/
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
/*?}*/

/**
 * Client-only entrypoint. Runs only on the physical client, never on a dedicated server.
 * <p>
 * Put client-only setup here: renderers, key bindings, HUD hooks, etc. (The Mod Menu
 * integration lives in its own class, ModMenuIntegration, since it's registered through
 * a separate "modmenu" entrypoint.)
 * <p>
 * Fabric-only: the cross-shaped-block cutout render layer needs an explicit
 * {@code BlockRenderLayerMap} call on Fabric, but Forge reads render layer from a
 * {@code "render_type"} key in the block model JSON instead (see the block model files
 * under assets/emeraldisleflora/models/block/), so Forge needs no Java for this at all
 * and has no client entrypoint class.
 * <p>
 * This project uses a single (unsplit) source set, so this class is NOT compile-time
 * guaranteed to be client-only the way it would be with
 * {@code loom.splitEnvironmentSourceSets()}. The {@code @Environment(EnvType.CLIENT)}
 * annotation below is a hint for tooling/reviewers; the real safety comes from never
 * calling anything in this class from common (server-reachable) code — only Fabric
 * Loader's "client" entrypoint and Mod Menu ever touch these classes.
 */
/*? if fabric {*/
@Environment(EnvType.CLIENT)
public class EmeraldIsleFloraClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		//---------------------------Flowers---------------------------
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BELLS_OF_IRELAND, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GROWN_BELLS_OF_IRELAND, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOG_ROSEMARY, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GROWN_BOG_ROSEMARY, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BULBOUS_BUTTERCUP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GROWN_BULBOUS_BUTTERCUP, RenderLayer.getCutout());

		//-----------------------Potted Flowers------------------------
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BELLS_OF_IRELAND, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BOG_ROSEMARY, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_GROWN_BOG_ROSEMARY, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BULBOUS_BUTTERCUP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_GROWN_BULBOUS_BUTTERCUP, RenderLayer.getCutout());
	}
}
/*?}*/
