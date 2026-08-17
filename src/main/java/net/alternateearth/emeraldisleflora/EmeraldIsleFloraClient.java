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
// Forge/NeoForge: flat sibling blocks rather than nesting inside a shared "forgeLike"
// wrapper - this whole import section is one big disabled block comment when forgeLike
// is inactive, so a nested Stonecutter marker inside it would be invisible to the
// scanner (same reasoning as the class body split further down).
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
 * Client-only setup. Runs only on the physical client, never on a dedicated server.
 * <p>
 * Fabric has a real {@code ClientModInitializer} entrypoint (a Fabric Loader guarantee
 * that this class is never even loaded on a dedicated server); Forge/NeoForge instead
 * expose client setup via a mod-event-bus event
 * ({@code EntityRenderersEvent.RegisterRenderers}) that simply never fires on a
 * dedicated server, wired up from {@code EmeraldIsleFlora}'s Forge/NeoForge
 * constructors exactly like every other {@code onRegister} listener there - so this
 * file has two separate, loader-gated class bodies below rather than one shared shape.
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
		// >=26.2: nothing to do here for render layers - see the class javadoc above,
		// they're auto-detected from sprite pixel data now.

		// Vanilla's own block-entity-renderer registry is populated once, at bootstrap,
		// via a private static method - the same "hardcoded once, no public extension
		// point" shape as BlockEntityType's own supported-blocks Set (see
		// ModBlockEntities' doc comment). BlockEntityRendererRegistry is Fabric API's
		// wrapper around it. Reuses vanilla's own sign renderer class directly - it's a
		// generic renderer keyed by WoodType/attachment, not hardcoded to vanilla's own
		// sign blocks, so it renders ours correctly with no subclassing needed.
		/*? if <26.2 {*/
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_SIGN, SignBlockEntityRenderer::new);
		// Hanging signs need their own dedicated renderer class (chain+board model, not
		// the plain sign post) - confirmed via javap, not assumed from naming parity.
		// Handles both the standing/ceiling and wall variants internally, same as the
		// regular sign's renderer - no separate wall-hanging-sign renderer class exists.
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_HANGING_SIGN, HangingSignBlockEntityRenderer::new);
		/*?} else {*/
		/*
		// 26.2: SignBlockEntityRenderer was replaced by the new render-state-based
		// StandingSignRenderer (part of Mojang's own entity/block-entity-render-state
		// rework) - confirmed via javap against the real 26.2 jar. Despite the name, it
		// (like the old SignBlockEntityRenderer) handles both standing and wall signs
		// internally via the sign's PlainSignBlock.Attachment, not a separate class per
		// variant - no WallSignRenderer exists.
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_SIGN, StandingSignRenderer::new);
		// Same "own dedicated renderer" story as <26.2's HangingSignBlockEntityRenderer -
		// confirmed via javap against the real 26.2 jar.
		BlockEntityRendererRegistry.register(ModBlockEntities.YEW_HANGING_SIGN, HangingSignRenderer::new);
		*/
		/*?}*/
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
// Forge/NeoForge: split into two flat, mutually-exclusive blocks (rather than one body
// with a nested <26.2/else marker) because this whole class is already one big disabled
// block comment when forgeLike is inactive - a nested Stonecutter marker inside it is
// invisible to the scanner, the same reason ModBlocks#onRegister and ModBlockEntities'
// field are split this way instead of nested.
/*? if forgeLike && <26.2 {*/
/*public final class EmeraldIsleFloraClient {
	private EmeraldIsleFloraClient() { }

	// Same "why" as Fabric's onInitializeClient block-entity-renderer call above - see
	// that doc comment. Forge/NeoForge's own vanilla renderer registry is likewise
	// bootstrap-only/private, and EntityRenderersEvent.RegisterRenderers is their
	// equivalent public extension point, fired only on the physical client.
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
