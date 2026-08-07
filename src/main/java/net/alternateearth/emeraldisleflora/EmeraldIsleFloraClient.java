package net.alternateearth.emeraldisleflora;

/*? if fabric {*/
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
/*? if <26.2 {*/
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
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
/*?}*/

/**
 * Client-only entrypoint. Runs only on the physical client, never on a dedicated server.
 * <p>
 * Put client-only setup here: renderers, key bindings, HUD hooks, etc. (The Mod Menu
 * integration lives in its own class, ModMenuIntegration, since it's registered through
 * a separate "modmenu" entrypoint.)
 * <p>
 * Fabric-only: on &lt;26.2, the cross-shaped-block cutout render layer needs an explicit
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
 * (confirmed: not referenced anywhere in vanilla's own 1.21.11 classes), so &lt;26.2 Fabric
 * always needs one of these two Java-side calls, never the JSON key alone.
 * <p>
 * As of 26.1+, this entire mechanism (both the vanilla JSON key and every Fabric
 * BlockRenderLayerMap variant) is obsolete: Mojang's own rendering rework makes vanilla
 * automatically assign each quad's {@code ChunkSectionLayer} (the new name for
 * render-layer/render-type) from the sprite's own pixel data - translucent pixels present
 * -> translucent layer, only fully-opaque/fully-transparent pixels -> cutout layer,
 * neither -> solid layer - with no per-block registration needed for the common case at
 * all (confirmed via Fabric's own 26.1 release notes; overriding it still exists via a
 * block model or MutableQuadView/Model Loading API for the rare block that needs to
 * force a specific layer despite its texture, which this mod doesn't need). Since our
 * flower models are a plain "cross" parent with a texture that already has fully
 * transparent (not translucent) pixels around the sprite - the same shape vanilla's own
 * flowers use - they get auto-detected as cutout with zero registration code for
 * &gt;=26.2, so this class has nothing left to do there.
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
		/*? if <26.2 {*/
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
		/*?}*/
		// >=26.2: nothing to do here - see the class javadoc above, render layers are
		// auto-detected from sprite pixel data now.
	}

	/*? if <26.2 {*/
	// <1.21.11: net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap, an
	// instance singleton taking a RenderLayer. >=1.21.11 (and <26.2): a different class
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
	/*?}*/
}
/*?}*/
