package net.alternateearth.emeraldisleflora.data;

/**
 * Generates this mod's dye-from-flower recipes and its yew wood-set recipes (and their
 * paired recipe-unlock advancements, produced automatically alongside each recipe by
 * offerTo) instead of hand-maintaining static JSON.
 * <p>
 * This exists because Mojang's own recipe/advancement data format genuinely changed
 * twice across this mod's supported version range - the recipe/advancement directory
 * was renamed (recipes/advancements -> recipe/advancement) and the recipe result field
 * was renamed (item -> id) at 1.21, then the ingredient format was flattened from
 * {"item": "x"} objects to bare "x" strings at 1.21.11 - confirmed by decompiling the
 * real game code for each version (ItemStack's result codec, Ingredient's codec,
 * RegistryKeys.RECIPE's registry path) rather than assumed from changelog notes. Hand
 * JSON shared unconditionally across every Stonecutter target broke silently wherever
 * the format had moved on (see issue #17).
 * <p>
 * Covers all four Minecraft versions, split into four full sibling Stonecutter branches
 * - deliberately NOT nested (a `<1.21`/else split inside a `<1.21.11` block corrupted
 * Stonecutter's output the moment a target outside `<1.21.11` needed the whole outer
 * block disabled - a new, previously-unseen failure mode; see AGENTS.md's nesting
 * gotcha, this is the same underlying rule, just tripped a different way) - since the
 * recipe/advancement API genuinely reshapes three times across this range, not just the
 * JSON it emits:
 * <ul>
 *   <li>{@code <1.21}: {@code Consumer<RecipeJsonProvider>}-based, real live datagen
 *       output ({@code versions/data/1.20.1}).
 *   <li>{@code >=1.21 && <1.21.11}: {@code RecipeExporter}-based, same shape otherwise.
 *       Real datagen ({@code :1.21.1-fabric:runDatagen}) crashes on a real, reproduced-
 *       on-two-machines Fabric API bug unrelated to this mod (fabric-mining-level-api-
 *       v1's SwordItemMixin fails to apply in the dev-launch bootstrap sequence for this
 *       exact Minecraft/mappings build) - its output
 *       ({@code versions/data/1.21.1}) was hand-derived once from the
 *       1.20.1 output instead, applying only the schema changes already independently
 *       confirmed against the real 1.21.1 codecs. See build.gradle.kts's
 *       recipeGeneratedResources comment and CONTRIBUTING.md.
 *   <li>{@code >=1.21.11 && <26.2}: real API restructuring, not just a rename -
 *       {@code RecipeGenerator} (registry-lookup-based ingredient/recipe-key creation)
 *       replaces the old {@code RecipeProvider}/{@code Consumer} pattern entirely.
 *       {@code :1.21.11-fabric:runDatagen} hits the same Fabric API dev-launch bug as
 *       1.21.1, so this branch's output was hand-derived the same way, from what this
 *       code would produce (schema confirmed via the real 1.21.11 codecs).
 *   <li>{@code >=26.2}: Mojmap names for the same {@code RecipeGenerator}-shaped API
 *       ({@code RecipeProvider}/{@code RecipeOutput} in Mojmap's own naming).
 *       {@code :26.2-fabric:runDatagen} actually works, so this one's output is real,
 *       verified datagen output, not hand-derived.
 * </ul>
 * The two {@code >=1.21.11} branches also can't rely on the old convenience auto-id path
 * ({@code offerSingleOutputShapelessRecipe}/bare {@code offerTo(exporter)}) the way the
 * two earlier branches safely do: confirmed via decompiling the real Fabric API code for
 * both versions that {@code RecipeExporter}/{@code RecipeOutput}'s write path uses
 * whatever registry key it's given directly, with no namespace-rewriting onto this mod's
 * own ID happening anywhere anymore - a bare/derived id there would silently register
 * under {@code minecraft:}, not this mod's namespace. Every recipe in those two branches
 * is built with an explicit, mod-namespaced key instead.
 * <p>
 * The yew wood-set recipes below mirror vanilla's own oak recipe shapes/counts/groups
 * (planks, stairs, slab, fence, fence gate, door, trapdoor, pressure plate, button, sign,
 * hanging sign) and use explicit mod-namespaced ids throughout, same reasoning as above.
 * Unlike the dye recipes, none of this content could be verified against a real
 * {@code runDatagen} run in the environment this was written in (outbound access to the
 * Fabric/Mojang Maven hosts Loom needs was not available) - the static JSON mirrors under
 * {@code versions/data/*} were hand-derived directly from this class instead
 * of copied from real datagen output. Worth a real {@code runDatagen} diff once a
 * machine with full Maven access is available, same spirit as the 1.21.1/1.21.11 hand-
 * derivations above.
 */
/*? if fabric && <1.21 {*/
import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // Grown flowers yield 2 dye, not 1 - a real, intentional detail of the original
        // hand-written recipes (confirmed via git history) that offerSingleOutputShapelessRecipe
        // (fixed at 1) would silently drop; offerShapelessRecipe takes an explicit count.
        offerShapelessRecipe(exporter, Items.GREEN_DYE, ModBlocks.BELLS_OF_IRELAND.asItem(), "green_dye", 1);
        offerShapelessRecipe(exporter, Items.GREEN_DYE, ModBlocks.GROWN_BELLS_OF_IRELAND.asItem(), "green_dye", 2);

        offerShapelessRecipe(exporter, Items.PINK_DYE, ModBlocks.BOG_ROSEMARY.asItem(), "pink_dye", 1);
        offerShapelessRecipe(exporter, Items.PINK_DYE, ModBlocks.GROWN_BOG_ROSEMARY.asItem(), "pink_dye", 2);

        offerShapelessRecipe(exporter, Items.YELLOW_DYE, ModBlocks.BULBOUS_BUTTERCUP.asItem(), "yellow_dye", 1);
        offerShapelessRecipe(exporter, Items.YELLOW_DYE, ModBlocks.GROWN_BULBOUS_BUTTERCUP.asItem(), "yellow_dye", 2);

        offerShapelessRecipe(exporter, Items.BLUE_DYE, ModBlocks.BLUEBELL.asItem(), "blue_dye", 1);
        offerShapelessRecipe(exporter, Items.BLUE_DYE, ModBlocks.GROWN_BLUEBELL.asItem(), "blue_dye", 2);

        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.BELLS_OF_IRELAND, "grown_bells_of_ireland_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.BOG_ROSEMARY, "grown_bog_rosemary_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BULBOUS_BUTTERCUP, "grown_bulbous_buttercup_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BLUEBELL, ModBlocks.BLUEBELL, "grown_bluebell_from_flowers");

        offerYewWoodSetRecipes(exporter);
    }

    // Not offerSingleOutputShapelessRecipe: that helper only supports a single input
    // item, but this recipe needs 2 of the base flower - matches the original hand-
    // written recipes' shape (2 base flowers -> 1 grown flower) and their custom
    // "_from_flowers" recipe id (convertBetween's auto id would instead produce
    // "grown_x_from_x", the base flower's own item name, not "flowers").
    private static void offerGrownFromFlowersRecipe(
            Consumer<RecipeJsonProvider> exporter, Block grown, Block flower, String recipeId) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, grown.asItem())
                .input(flower.asItem(), 2)
                .criterion(hasItem(flower.asItem()), conditionsFromItem(flower.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, recipeId));
    }

    // Mirrors vanilla's own oak recipe set - same shapes/counts/groups/recipe-book
    // categories as oak_planks/oak_stairs/.../oak_hanging_sign.json, just yew-namespaced.
    private static void offerYewWoodSetRecipes(Consumer<RecipeJsonProvider> exporter) {
        // Any of the 4 log-family items convert to planks, same as vanilla's per-species
        // log tag - Ingredient.ofItems (not a mod-namespaced tag) keeps this self-
        // contained without needing a new tag file just for one recipe's ingredient list.
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_PLANKS.asItem(), 4)
                .input(Ingredient.ofItems(ModBlocks.YEW_LOG.asItem(), ModBlocks.YEW_WOOD.asItem(),
                        ModBlocks.STRIPPED_YEW_LOG.asItem(), ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                .group("planks")
                .criterion(hasItem(ModBlocks.YEW_LOG.asItem()), conditionsFromItem(ModBlocks.YEW_LOG.asItem()))
                .criterion(hasItem(ModBlocks.YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.YEW_WOOD.asItem()))
                .criterion(hasItem(ModBlocks.STRIPPED_YEW_LOG.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_LOG.asItem()))
                .criterion(hasItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_planks"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_STAIRS.asItem(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_stairs"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_SLAB.asItem(), 6)
                .pattern("###")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_slab"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.YEW_FENCE.asItem(), 3)
                .pattern("W#W")
                .pattern("W#W")
                .input('W', ModBlocks.YEW_PLANKS.asItem())
                .input('#', Items.STICK)
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_FENCE_GATE.asItem())
                .pattern("#W#")
                .pattern("#W#")
                .input('W', ModBlocks.YEW_PLANKS.asItem())
                .input('#', Items.STICK)
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_DOOR.asItem(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_door"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_TRAPDOOR.asItem(), 2)
                .pattern("###")
                .pattern("###")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_PRESSURE_PLATE.asItem())
                .pattern("##")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_BUTTON.asItem())
                .input(ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_button"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.YEW_SIGN.asItem(), 3)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .input('X', Items.STICK)
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.YEW_HANGING_SIGN.asItem(), 6)
                .pattern("C C")
                .pattern("###")
                .pattern("###")
                .input('C', Items.CHAIN)
                .input('#', Ingredient.ofItems(ModBlocks.YEW_LOG.asItem(), ModBlocks.YEW_WOOD.asItem(),
                        ModBlocks.STRIPPED_YEW_LOG.asItem(), ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                .criterion(hasItem(ModBlocks.YEW_LOG.asItem()), conditionsFromItem(ModBlocks.YEW_LOG.asItem()))
                .criterion(hasItem(ModBlocks.YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.YEW_WOOD.asItem()))
                .criterion(hasItem(ModBlocks.STRIPPED_YEW_LOG.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_LOG.asItem()))
                .criterion(hasItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"));
    }
}
/*?}*/
/*? if fabric && >=1.21 && <1.21.11 {*/
/*import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // Grown flowers yield 2 dye, not 1 - see the <1.21 sibling branch's comment.
        offerShapelessRecipe(exporter, Items.GREEN_DYE, ModBlocks.BELLS_OF_IRELAND.asItem(), "green_dye", 1);
        offerShapelessRecipe(exporter, Items.GREEN_DYE, ModBlocks.GROWN_BELLS_OF_IRELAND.asItem(), "green_dye", 2);

        offerShapelessRecipe(exporter, Items.PINK_DYE, ModBlocks.BOG_ROSEMARY.asItem(), "pink_dye", 1);
        offerShapelessRecipe(exporter, Items.PINK_DYE, ModBlocks.GROWN_BOG_ROSEMARY.asItem(), "pink_dye", 2);

        offerShapelessRecipe(exporter, Items.YELLOW_DYE, ModBlocks.BULBOUS_BUTTERCUP.asItem(), "yellow_dye", 1);
        offerShapelessRecipe(exporter, Items.YELLOW_DYE, ModBlocks.GROWN_BULBOUS_BUTTERCUP.asItem(), "yellow_dye", 2);

        offerShapelessRecipe(exporter, Items.BLUE_DYE, ModBlocks.BLUEBELL.asItem(), "blue_dye", 1);
        offerShapelessRecipe(exporter, Items.BLUE_DYE, ModBlocks.GROWN_BLUEBELL.asItem(), "blue_dye", 2);

        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.BELLS_OF_IRELAND, "grown_bells_of_ireland_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.BOG_ROSEMARY, "grown_bog_rosemary_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BULBOUS_BUTTERCUP, "grown_bulbous_buttercup_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BLUEBELL, ModBlocks.BLUEBELL, "grown_bluebell_from_flowers");

        offerYewWoodSetRecipes(exporter);
    }

    private static void offerGrownFromFlowersRecipe(RecipeExporter exporter, Block grown, Block flower, String recipeId) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, grown.asItem())
                .input(flower.asItem(), 2)
                .criterion(hasItem(flower.asItem()), conditionsFromItem(flower.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, recipeId));
    }

    // Mirrors vanilla's own oak recipe set - see the <1.21 sibling branch's comment.
    private static void offerYewWoodSetRecipes(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_PLANKS.asItem(), 4)
                .input(Ingredient.ofItems(ModBlocks.YEW_LOG.asItem(), ModBlocks.YEW_WOOD.asItem(),
                        ModBlocks.STRIPPED_YEW_LOG.asItem(), ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                .group("planks")
                .criterion(hasItem(ModBlocks.YEW_LOG.asItem()), conditionsFromItem(ModBlocks.YEW_LOG.asItem()))
                .criterion(hasItem(ModBlocks.YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.YEW_WOOD.asItem()))
                .criterion(hasItem(ModBlocks.STRIPPED_YEW_LOG.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_LOG.asItem()))
                .criterion(hasItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_planks"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_STAIRS.asItem(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_stairs"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_SLAB.asItem(), 6)
                .pattern("###")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_slab"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.YEW_FENCE.asItem(), 3)
                .pattern("W#W")
                .pattern("W#W")
                .input('W', ModBlocks.YEW_PLANKS.asItem())
                .input('#', Items.STICK)
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_FENCE_GATE.asItem())
                .pattern("#W#")
                .pattern("#W#")
                .input('W', ModBlocks.YEW_PLANKS.asItem())
                .input('#', Items.STICK)
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_DOOR.asItem(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_door"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_TRAPDOOR.asItem(), 2)
                .pattern("###")
                .pattern("###")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_PRESSURE_PLATE.asItem())
                .pattern("##")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.YEW_BUTTON.asItem())
                .input(ModBlocks.YEW_PLANKS.asItem())
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_button"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.YEW_SIGN.asItem(), 3)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .input('X', Items.STICK)
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.YEW_HANGING_SIGN.asItem(), 6)
                .pattern("C C")
                .pattern("###")
                .pattern("###")
                .input('C', Items.CHAIN)
                .input('#', Ingredient.ofItems(ModBlocks.YEW_LOG.asItem(), ModBlocks.YEW_WOOD.asItem(),
                        ModBlocks.STRIPPED_YEW_LOG.asItem(), ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                .criterion(hasItem(ModBlocks.YEW_LOG.asItem()), conditionsFromItem(ModBlocks.YEW_LOG.asItem()))
                .criterion(hasItem(ModBlocks.YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.YEW_WOOD.asItem()))
                .criterion(hasItem(ModBlocks.STRIPPED_YEW_LOG.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_LOG.asItem()))
                .criterion(hasItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"));
    }
}
*/
/*?}*/
/*? if fabric && >=1.21.11 && <26.2 {*/
/*import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    // <1.21.11's FabricRecipeProvider extended vanilla's own RecipeProvider, whose
    // getName() was final and returned "Recipes" automatically - the new RecipeGenerator
    // -based hierarchy (RecipeGenerator.RecipeProvider, which FabricRecipeProvider now
    // extends) leaves it abstract, so it's ours to implement.
    @Override
    public String getName() {
        return "Recipes";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new Generator(registries, exporter);
    }

    private static final class Generator extends RecipeGenerator {
        Generator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
            super(registries, exporter);
        }

        @Override
        // public, not the vanilla-declared protected: confirmed via a real compile error
        // ("attempting to assign weaker access privileges; was public") that Fabric's own
        // access widener widens this specific method to public on the real classpath,
        // even though the raw un-widened jar declares it protected - Java requires an
        // override's access to be the same or wider, never narrower.
        public void generate() {
            // Grown flowers yield 2 dye, not 1 - see the <1.21 branch's comment.
            offerDyeRecipe(Items.GREEN_DYE, ModBlocks.BELLS_OF_IRELAND, 1, "green_dye", "green_dye_from_bells_of_ireland");
            offerDyeRecipe(Items.GREEN_DYE, ModBlocks.GROWN_BELLS_OF_IRELAND, 2, "green_dye", "green_dye_from_grown_bells_of_ireland");

            offerDyeRecipe(Items.PINK_DYE, ModBlocks.BOG_ROSEMARY, 1, "pink_dye", "pink_dye_from_bog_rosemary");
            offerDyeRecipe(Items.PINK_DYE, ModBlocks.GROWN_BOG_ROSEMARY, 2, "pink_dye", "pink_dye_from_grown_bog_rosemary");

            offerDyeRecipe(Items.YELLOW_DYE, ModBlocks.BULBOUS_BUTTERCUP, 1, "yellow_dye", "yellow_dye_from_bulbous_buttercup");
            offerDyeRecipe(Items.YELLOW_DYE, ModBlocks.GROWN_BULBOUS_BUTTERCUP, 2, "yellow_dye", "yellow_dye_from_grown_bulbous_buttercup");

            offerDyeRecipe(Items.BLUE_DYE, ModBlocks.BLUEBELL, 1, "blue_dye", "blue_dye_from_bluebell");
            offerDyeRecipe(Items.BLUE_DYE, ModBlocks.GROWN_BLUEBELL, 2, "blue_dye", "blue_dye_from_grown_bluebell");

            offerGrownFromFlowersRecipe(ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.BELLS_OF_IRELAND, "grown_bells_of_ireland_from_flowers");
            offerGrownFromFlowersRecipe(ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.BOG_ROSEMARY, "grown_bog_rosemary_from_flowers");
            offerGrownFromFlowersRecipe(ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BULBOUS_BUTTERCUP, "grown_bulbous_buttercup_from_flowers");
            offerGrownFromFlowersRecipe(ModBlocks.GROWN_BLUEBELL, ModBlocks.BLUEBELL, "grown_bluebell_from_flowers");

            offerYewWoodSetRecipes();
        }

        // Explicit mod-namespaced RegistryKey, not the convertBetween-based auto-id
        // convenience path (offerSingleOutputShapelessRecipe / bare offerTo(exporter)):
        // confirmed via decompiling both this version's and 26.2's real Fabric API code
        // that RecipeExporter.accept(...) uses the RegistryKey's own namespace directly
        // with no rewriting onto this mod's namespace happening anywhere in the write
        // path anymore (unlike this class's <1.21.11 branches' getRecipeIdentifier
        // behavior) - a bare/auto-derived id here would silently register under
        // "minecraft:", not this mod's namespace.
        private void offerDyeRecipe(Item dye, Block flower, int dyeCount, String group, String recipeId) {
            createShapeless(RecipeCategory.MISC, dye, dyeCount)
                    .input(flower.asItem())
                    .group(group)
                    .criterion(hasItem(flower.asItem()), conditionsFromItem(flower.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, recipeId)));
        }

        private void offerGrownFromFlowersRecipe(Block grown, Block flower, String recipeId) {
            createShapeless(RecipeCategory.MISC, grown.asItem())
                    .input(flower.asItem(), 2)
                    .criterion(hasItem(flower.asItem()), conditionsFromItem(flower.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, recipeId)));
        }

        // Mirrors vanilla's own oak recipe set - see the <1.21 sibling branch's comment.
        private void offerYewWoodSetRecipes() {
            createShapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_PLANKS.asItem(), 4)
                    .input(Ingredient.ofItems(ModBlocks.YEW_LOG.asItem(), ModBlocks.YEW_WOOD.asItem(),
                            ModBlocks.STRIPPED_YEW_LOG.asItem(), ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                    .group("planks")
                    .criterion(hasItem(ModBlocks.YEW_LOG.asItem()), conditionsFromItem(ModBlocks.YEW_LOG.asItem()))
                    .criterion(hasItem(ModBlocks.YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.YEW_WOOD.asItem()))
                    .criterion(hasItem(ModBlocks.STRIPPED_YEW_LOG.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_LOG.asItem()))
                    .criterion(hasItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_planks")));

            createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_STAIRS.asItem(), 4)
                    .pattern("#  ")
                    .pattern("## ")
                    .pattern("###")
                    .input('#', ModBlocks.YEW_PLANKS.asItem())
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_stairs")));

            createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_SLAB.asItem(), 6)
                    .pattern("###")
                    .input('#', ModBlocks.YEW_PLANKS.asItem())
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_slab")));

            createShaped(RecipeCategory.DECORATIONS, ModBlocks.YEW_FENCE.asItem(), 3)
                    .pattern("W#W")
                    .pattern("W#W")
                    .input('W', ModBlocks.YEW_PLANKS.asItem())
                    .input('#', Items.STICK)
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence")));

            createShaped(RecipeCategory.REDSTONE, ModBlocks.YEW_FENCE_GATE.asItem(), 1)
                    .pattern("#W#")
                    .pattern("#W#")
                    .input('W', ModBlocks.YEW_PLANKS.asItem())
                    .input('#', Items.STICK)
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence_gate")));

            createShaped(RecipeCategory.REDSTONE, ModBlocks.YEW_DOOR.asItem(), 3)
                    .pattern("##")
                    .pattern("##")
                    .pattern("##")
                    .input('#', ModBlocks.YEW_PLANKS.asItem())
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_door")));

            createShaped(RecipeCategory.REDSTONE, ModBlocks.YEW_TRAPDOOR.asItem(), 2)
                    .pattern("###")
                    .pattern("###")
                    .input('#', ModBlocks.YEW_PLANKS.asItem())
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_trapdoor")));

            createShaped(RecipeCategory.REDSTONE, ModBlocks.YEW_PRESSURE_PLATE.asItem(), 1)
                    .pattern("##")
                    .input('#', ModBlocks.YEW_PLANKS.asItem())
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate")));

            createShapeless(RecipeCategory.REDSTONE, ModBlocks.YEW_BUTTON.asItem(), 1)
                    .input(ModBlocks.YEW_PLANKS.asItem())
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_button")));

            createShaped(RecipeCategory.DECORATIONS, ModBlocks.YEW_SIGN.asItem(), 3)
                    .pattern("###")
                    .pattern("###")
                    .pattern(" X ")
                    .input('#', ModBlocks.YEW_PLANKS.asItem())
                    .input('X', Items.STICK)
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign")));

            // Items.CHAIN was renamed Items.IRON_CHAIN at some point between 1.21.1 and
            // 1.21.11 (confirmed via javap - present as CHAIN on the real 1.21.1 jar,
            // gone from 1.21.11's Items class entirely, replaced by IRON_CHAIN alongside
            // new COPPER_CHAINS variants) - a real rename, not assumed to land on the
            // same boundary as this file's other >=1.21.11 splits.
            createShaped(RecipeCategory.DECORATIONS, ModBlocks.YEW_HANGING_SIGN.asItem(), 6)
                    .pattern("C C")
                    .pattern("###")
                    .pattern("###")
                    .input('C', Items.IRON_CHAIN)
                    .input('#', Ingredient.ofItems(ModBlocks.YEW_LOG.asItem(), ModBlocks.YEW_WOOD.asItem(),
                            ModBlocks.STRIPPED_YEW_LOG.asItem(), ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                    .criterion(hasItem(ModBlocks.YEW_LOG.asItem()), conditionsFromItem(ModBlocks.YEW_LOG.asItem()))
                    .criterion(hasItem(ModBlocks.YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.YEW_WOOD.asItem()))
                    .criterion(hasItem(ModBlocks.STRIPPED_YEW_LOG.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_LOG.asItem()))
                    .criterion(hasItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()), conditionsFromItem(ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign")));
        }
    }
}
*/
/*?}*/
/*? if fabric && >=26.2 {*/
/*import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    // See the >=1.21.11 sibling branch's getName() comment - same reasoning, Mojmap
    // naming (RecipeProvider.Runner here instead of RecipeGenerator.RecipeProvider).
    @Override
    public String getName() {
        return "Recipes";
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new Generator(registries, output);
    }

    private static final class Generator extends RecipeProvider {
        Generator(HolderLookup.Provider registries, RecipeOutput output) {
            super(registries, output);
        }

        @Override
        // public, not the vanilla-declared protected - see the >=1.21.11 sibling
        // branch's generate() comment, same access-widener reasoning.
        public void buildRecipes() {
            // 26.2: individual per-color dye item constants (Items.GREEN_DYE etc.) are
            // gone entirely - confirmed via javap against the real 26.2 client jar, only
            // Items.DYE (a ColorCollection<Item>, a new grouping abstraction also used
            // for DYED_TERRACOTTA/DYED_SHULKER_BOX/etc.) exists now, picked by DyeColor.
            // Grown flowers yield 2 dye, not 1 - see the <1.21 branch's comment.
            offerDyeRecipe(Items.DYE.pick(DyeColor.GREEN), ModBlocks.BELLS_OF_IRELAND, 1, "green_dye", "green_dye_from_bells_of_ireland");
            offerDyeRecipe(Items.DYE.pick(DyeColor.GREEN), ModBlocks.GROWN_BELLS_OF_IRELAND, 2, "green_dye", "green_dye_from_grown_bells_of_ireland");

            offerDyeRecipe(Items.DYE.pick(DyeColor.PINK), ModBlocks.BOG_ROSEMARY, 1, "pink_dye", "pink_dye_from_bog_rosemary");
            offerDyeRecipe(Items.DYE.pick(DyeColor.PINK), ModBlocks.GROWN_BOG_ROSEMARY, 2, "pink_dye", "pink_dye_from_grown_bog_rosemary");

            offerDyeRecipe(Items.DYE.pick(DyeColor.YELLOW), ModBlocks.BULBOUS_BUTTERCUP, 1, "yellow_dye", "yellow_dye_from_bulbous_buttercup");
            offerDyeRecipe(Items.DYE.pick(DyeColor.YELLOW), ModBlocks.GROWN_BULBOUS_BUTTERCUP, 2, "yellow_dye", "yellow_dye_from_grown_bulbous_buttercup");

            offerDyeRecipe(Items.DYE.pick(DyeColor.BLUE), ModBlocks.BLUEBELL, 1, "blue_dye", "blue_dye_from_bluebell");
            offerDyeRecipe(Items.DYE.pick(DyeColor.BLUE), ModBlocks.GROWN_BLUEBELL, 2, "blue_dye", "blue_dye_from_grown_bluebell");

            offerGrownFromFlowersRecipe(ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.BELLS_OF_IRELAND, "grown_bells_of_ireland_from_flowers");
            offerGrownFromFlowersRecipe(ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.BOG_ROSEMARY, "grown_bog_rosemary_from_flowers");
            offerGrownFromFlowersRecipe(ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BULBOUS_BUTTERCUP, "grown_bulbous_buttercup_from_flowers");
            offerGrownFromFlowersRecipe(ModBlocks.GROWN_BLUEBELL, ModBlocks.BLUEBELL, "grown_bluebell_from_flowers");

            offerYewWoodSetRecipes();
        }

        // Explicit mod-namespaced ResourceKey, not the getConversionRecipeName-based
        // auto-id convenience path (oneToOneConversionRecipe / bare save(output) alone):
        // confirmed via decompiling this version's real Fabric API code that
        // RecipeOutput.accept(...) uses the ResourceKey's own namespace directly, with no
        // rewriting onto this mod's namespace happening anywhere in the write path.
        private void offerDyeRecipe(Item dye, Block flower, int dyeCount, String group, String recipeId) {
            shapeless(RecipeCategory.MISC, dye, dyeCount)
                    .requires(flower.asItem())
                    .group(group)
                    .unlockedBy(getHasName(flower.asItem()), has(flower.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, recipeId)));
        }

        private void offerGrownFromFlowersRecipe(Block grown, Block flower, String recipeId) {
            shapeless(RecipeCategory.MISC, grown.asItem())
                    .requires(flower.asItem(), 2)
                    .unlockedBy(getHasName(flower.asItem()), has(flower.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, recipeId)));
        }

        // Mirrors vanilla's own oak recipe set - see the <1.21 sibling branch's comment.
        private void offerYewWoodSetRecipes() {
            shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_PLANKS.asItem(), 4)
                    .requires(Ingredient.of(ModBlocks.YEW_LOG.asItem(), ModBlocks.YEW_WOOD.asItem(),
                            ModBlocks.STRIPPED_YEW_LOG.asItem(), ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                    .group("planks")
                    .unlockedBy(getHasName(ModBlocks.YEW_LOG.asItem()), has(ModBlocks.YEW_LOG.asItem()))
                    .unlockedBy(getHasName(ModBlocks.YEW_WOOD.asItem()), has(ModBlocks.YEW_WOOD.asItem()))
                    .unlockedBy(getHasName(ModBlocks.STRIPPED_YEW_LOG.asItem()), has(ModBlocks.STRIPPED_YEW_LOG.asItem()))
                    .unlockedBy(getHasName(ModBlocks.STRIPPED_YEW_WOOD.asItem()), has(ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_planks")));

            shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_STAIRS.asItem(), 4)
                    .pattern("#  ")
                    .pattern("## ")
                    .pattern("###")
                    .define('#', ModBlocks.YEW_PLANKS.asItem())
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_stairs")));

            shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.YEW_SLAB.asItem(), 6)
                    .pattern("###")
                    .define('#', ModBlocks.YEW_PLANKS.asItem())
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_slab")));

            shaped(RecipeCategory.DECORATIONS, ModBlocks.YEW_FENCE.asItem(), 3)
                    .pattern("W#W")
                    .pattern("W#W")
                    .define('W', ModBlocks.YEW_PLANKS.asItem())
                    .define('#', Items.STICK)
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_fence")));

            shaped(RecipeCategory.REDSTONE, ModBlocks.YEW_FENCE_GATE.asItem(), 1)
                    .pattern("#W#")
                    .pattern("#W#")
                    .define('W', ModBlocks.YEW_PLANKS.asItem())
                    .define('#', Items.STICK)
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_fence_gate")));

            shaped(RecipeCategory.REDSTONE, ModBlocks.YEW_DOOR.asItem(), 3)
                    .pattern("##")
                    .pattern("##")
                    .pattern("##")
                    .define('#', ModBlocks.YEW_PLANKS.asItem())
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_door")));

            shaped(RecipeCategory.REDSTONE, ModBlocks.YEW_TRAPDOOR.asItem(), 2)
                    .pattern("###")
                    .pattern("###")
                    .define('#', ModBlocks.YEW_PLANKS.asItem())
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_trapdoor")));

            shaped(RecipeCategory.REDSTONE, ModBlocks.YEW_PRESSURE_PLATE.asItem(), 1)
                    .pattern("##")
                    .define('#', ModBlocks.YEW_PLANKS.asItem())
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate")));

            shapeless(RecipeCategory.REDSTONE, ModBlocks.YEW_BUTTON.asItem(), 1)
                    .requires(ModBlocks.YEW_PLANKS.asItem())
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_button")));

            shaped(RecipeCategory.DECORATIONS, ModBlocks.YEW_SIGN.asItem(), 3)
                    .pattern("###")
                    .pattern("###")
                    .pattern(" X ")
                    .define('#', ModBlocks.YEW_PLANKS.asItem())
                    .define('X', Items.STICK)
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_sign")));

            // Same Items.CHAIN -> Items.IRON_CHAIN rename as the >=1.21.11 branch above -
            // confirmed still IRON_CHAIN (not reverted) on the real 26.2 jar via javap.
            shaped(RecipeCategory.DECORATIONS, ModBlocks.YEW_HANGING_SIGN.asItem(), 6)
                    .pattern("C C")
                    .pattern("###")
                    .pattern("###")
                    .define('C', Items.IRON_CHAIN)
                    .define('#', Ingredient.of(ModBlocks.YEW_LOG.asItem(), ModBlocks.YEW_WOOD.asItem(),
                            ModBlocks.STRIPPED_YEW_LOG.asItem(), ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                    .unlockedBy(getHasName(ModBlocks.YEW_LOG.asItem()), has(ModBlocks.YEW_LOG.asItem()))
                    .unlockedBy(getHasName(ModBlocks.YEW_WOOD.asItem()), has(ModBlocks.YEW_WOOD.asItem()))
                    .unlockedBy(getHasName(ModBlocks.STRIPPED_YEW_LOG.asItem()), has(ModBlocks.STRIPPED_YEW_LOG.asItem()))
                    .unlockedBy(getHasName(ModBlocks.STRIPPED_YEW_WOOD.asItem()), has(ModBlocks.STRIPPED_YEW_WOOD.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign")));
        }
    }
}
*/
/*?}*/
