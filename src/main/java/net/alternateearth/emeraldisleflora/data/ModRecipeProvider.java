package net.alternateearth.emeraldisleflora.data;

/*? if fabric && <1.21.11 {*/
import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
/*? if <1.21 {*/
import java.util.function.Consumer;

import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;
/*?} else {*/
/*import java.util.concurrent.CompletableFuture;

import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
*/
/*?}*/

/**
 * Generates this mod's dye-from-flower recipes (and their paired recipe-unlock
 * advancements, produced automatically alongside each recipe by
 * offerSingleOutputShapelessRecipe) instead of hand-maintaining static JSON.
 * <p>
 * This exists because Mojang's own recipe/advancement data format genuinely changed
 * twice across this mod's supported version range - the recipe/advancement directory
 * was renamed (recipes/advancements -> recipe/advancement) and the recipe result field
 * was renamed (item -> id) at 1.21, then the ingredient format was flattened from
 * {"item": "x"} objects to bare "x" strings at 1.21.11 - confirmed by decompiling the
 * real game code for each version (ItemStack's result codec, Ingredient's codec,
 * RegistryKeys.RECIPE's registry path) rather than assumed from changelog notes. Hand
 * JSON shared unconditionally across every Stonecutter target broke silently wherever
 * the format had moved on (see issue #17); datagen sidesteps this because
 * ShapelessRecipeJsonBuilder/offerTo serializes using whichever version's real codec is
 * on the classpath, matching how ModConfiguredFeatures/ModPlacedFeatures already avoid
 * the equivalent Yarn/Mojmap worldgen-schema drift.
 * <p>
 * Only covers &lt;1.21.11 so far (the versions issue #17 confirmed broken) - 1.21.11 and
 * 26.2 need a further Ingredient/RecipeGenerator API rework (registry-lookup-based
 * ingredient creation) and still ship no recipes for now.
 * <p>
 * The &lt;1.21 branch's output ({@code src/main/generated-recipes-1.20.1}) is the real,
 * live datagen output. The {@code else} branch below is still written and kept correct
 * against 1.21.1's real API (confirmed against the actual game classes), but its output
 * ({@code src/main/generated-recipes-1.21.1}) was hand-derived once from the 1.20.1
 * output instead of actually running {@code :1.21.1-fabric:runDatagen} - that task
 * crashes on a real, reproduced-on-two-machines Fabric API bug unrelated to this mod
 * (fabric-mining-level-api-v1's SwordItemMixin fails to apply in the dev-launch
 * bootstrap sequence for this exact Minecraft/mappings build) - see
 * build.gradle.kts's recipeGeneratedResources comment. Revisit with a real datagen run
 * if that bug ever gets fixed upstream; until then, this branch exists so the code is
 * ready and the next person doesn't have to re-derive 1.21.1's API shape from scratch.
 */
public class ModRecipeProvider extends FabricRecipeProvider {
    /*? if <1.21 {*/
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSingleOutputShapelessRecipe(exporter, Items.GREEN_DYE, ModBlocks.BELLS_OF_IRELAND.asItem(), "green_dye");
        offerSingleOutputShapelessRecipe(exporter, Items.GREEN_DYE, ModBlocks.GROWN_BELLS_OF_IRELAND.asItem(), "green_dye");

        offerSingleOutputShapelessRecipe(exporter, Items.PINK_DYE, ModBlocks.BOG_ROSEMARY.asItem(), "pink_dye");
        offerSingleOutputShapelessRecipe(exporter, Items.PINK_DYE, ModBlocks.GROWN_BOG_ROSEMARY.asItem(), "pink_dye");

        offerSingleOutputShapelessRecipe(exporter, Items.YELLOW_DYE, ModBlocks.BULBOUS_BUTTERCUP.asItem(), "yellow_dye");
        offerSingleOutputShapelessRecipe(exporter, Items.YELLOW_DYE, ModBlocks.GROWN_BULBOUS_BUTTERCUP.asItem(), "yellow_dye");

        offerSingleOutputShapelessRecipe(exporter, Items.BLUE_DYE, ModBlocks.BLUEBELL.asItem(), "blue_dye");
        offerSingleOutputShapelessRecipe(exporter, Items.BLUE_DYE, ModBlocks.GROWN_BLUEBELL.asItem(), "blue_dye");

        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.BELLS_OF_IRELAND, "grown_bells_of_ireland_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.BOG_ROSEMARY, "grown_bog_rosemary_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BULBOUS_BUTTERCUP, "grown_bulbous_buttercup_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BLUEBELL, ModBlocks.BLUEBELL, "grown_bluebell_from_flowers");
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
    /*?} else {*/
    /*
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerSingleOutputShapelessRecipe(exporter, Items.GREEN_DYE, ModBlocks.BELLS_OF_IRELAND.asItem(), "green_dye");
        offerSingleOutputShapelessRecipe(exporter, Items.GREEN_DYE, ModBlocks.GROWN_BELLS_OF_IRELAND.asItem(), "green_dye");

        offerSingleOutputShapelessRecipe(exporter, Items.PINK_DYE, ModBlocks.BOG_ROSEMARY.asItem(), "pink_dye");
        offerSingleOutputShapelessRecipe(exporter, Items.PINK_DYE, ModBlocks.GROWN_BOG_ROSEMARY.asItem(), "pink_dye");

        offerSingleOutputShapelessRecipe(exporter, Items.YELLOW_DYE, ModBlocks.BULBOUS_BUTTERCUP.asItem(), "yellow_dye");
        offerSingleOutputShapelessRecipe(exporter, Items.YELLOW_DYE, ModBlocks.GROWN_BULBOUS_BUTTERCUP.asItem(), "yellow_dye");

        offerSingleOutputShapelessRecipe(exporter, Items.BLUE_DYE, ModBlocks.BLUEBELL.asItem(), "blue_dye");
        offerSingleOutputShapelessRecipe(exporter, Items.BLUE_DYE, ModBlocks.GROWN_BLUEBELL.asItem(), "blue_dye");

        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BELLS_OF_IRELAND, ModBlocks.BELLS_OF_IRELAND, "grown_bells_of_ireland_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BOG_ROSEMARY, ModBlocks.BOG_ROSEMARY, "grown_bog_rosemary_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BULBOUS_BUTTERCUP, ModBlocks.BULBOUS_BUTTERCUP, "grown_bulbous_buttercup_from_flowers");
        offerGrownFromFlowersRecipe(exporter, ModBlocks.GROWN_BLUEBELL, ModBlocks.BLUEBELL, "grown_bluebell_from_flowers");
    }

    private static void offerGrownFromFlowersRecipe(RecipeExporter exporter, Block grown, Block flower, String recipeId) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, grown.asItem())
                .input(flower.asItem(), 2)
                .criterion(hasItem(flower.asItem()), conditionsFromItem(flower.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, recipeId));
    }
    */
    /*?}*/
}
/*?}*/
