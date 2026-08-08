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
 * ingredient creation) and still ship their pre-existing static recipe JSON for now.
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
