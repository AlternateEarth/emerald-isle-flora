package net.alternateearth.emeraldisleflora;

/*? if fabric {*/
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
/*? if <1.21.11 {*/
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
/*?} else {*/
/*import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
*/
/*?}*/
/*?}*/

/**
 * Client-only entrypoint. Runs only on the physical client, never on a dedicated server.
 * <p>
 * Put client-only setup here: renderers, key bindings, HUD hooks, etc. (The Mod Menu
 * integration lives in its own class, ModMenuIntegration, since it's registered through
 * a separate "modmenu" entrypoint.)
 * <p>
 * Fabric-only: the cross-shaped-block cutout render layer needs an explicit
 * {@code BlockRenderLayerMap} call on Fabric (Forge/NeoForge read a
 * {@code "render_type"} key from the block model JSON instead - see the block model
 * files under assets/emeraldisleflora/models/block/ - and never need Java for this).
 * {@code net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap} (its own
 * dedicated module, "fabric-blockrenderlayer-v1") was removed from Fabric API entirely
 * for 1.21.11 as part of the same rendering pipeline rework that moved
 * {@code RenderLayer.getCutout()} - confirmed via a real {@code NoClassDefFoundError}
 * crash on a real 1.21.11-fabric install, not just a guess. Its replacement,
 * {@code net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap} (living in the
 * general "fabric-rendering-v1" module instead), is a different class with a different,
 * simpler static API taking a {@code BlockRenderLayer} enum constant instead of a
 * {@code RenderLayer}/{@code RenderLayers} object - vanilla's own model JSON
 * {@code "render_type"} key is Forge/NeoForge-only and was never read by Fabric at all
 * (confirmed: not referenced anywhere in vanilla's own 1.21.11 classes), so Fabric always
 * needs one of these two Java-side calls, never the JSON key alone.
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
		putCutout(ModBlocks.BELLS_OF_IRELAND);
		putCutout(ModBlocks.GROWN_BELLS_OF_IRELAND);
		putCutout(ModBlocks.BOG_ROSEMARY);
		putCutout(ModBlocks.GROWN_BOG_ROSEMARY);
		putCutout(ModBlocks.BULBOUS_BUTTERCUP);
		putCutout(ModBlocks.GROWN_BULBOUS_BUTTERCUP);

		//-----------------------Potted Flowers------------------------
		putCutout(ModBlocks.POTTED_BELLS_OF_IRELAND);
		putCutout(ModBlocks.POTTED_GROWN_BELLS_OF_IRELAND);
		putCutout(ModBlocks.POTTED_BOG_ROSEMARY);
		putCutout(ModBlocks.POTTED_GROWN_BOG_ROSEMARY);
		putCutout(ModBlocks.POTTED_BULBOUS_BUTTERCUP);
		putCutout(ModBlocks.POTTED_GROWN_BULBOUS_BUTTERCUP);
	}

	// <1.21.11: net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap, an
	// instance singleton taking a RenderLayer. >=1.21.11: a different class
	// (net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap, living in the
	// general fabric-rendering-v1 module since fabric-blockrenderlayer-v1 was dropped),
	// with a static API taking a simpler BlockRenderLayer enum constant instead.
	/*? if <1.21.11 {*/
	private static void putCutout(Block block) {
		BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
	}
	/*?} else {*/
	/*private static void putCutout(Block block) {
		BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
	}*/
	/*?}*/
}
/*?}*/
