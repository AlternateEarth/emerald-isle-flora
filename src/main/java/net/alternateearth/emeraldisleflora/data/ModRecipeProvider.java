package net.alternateearth.emeraldisleflora.data;

/**
 * Generates this mod's dye-from-flower and yew wood-set recipes via datagen.
 */
/*? if fabric && <1.21 {*/
import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.alternateearth.emeraldisleflora.registry.ModItems;
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

    private static void offerGrownFromFlowersRecipe(
            Consumer<RecipeJsonProvider> exporter, Block grown, Block flower, String recipeId) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, grown.asItem())
                .input(flower.asItem(), 2)
                .criterion(hasItem(flower.asItem()), conditionsFromItem(flower.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, recipeId));
    }

    private static void offerYewWoodSetRecipes(Consumer<RecipeJsonProvider> exporter) {
        // Any of the 4 log-family items convert to planks, same as vanilla's per-species log tag.
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.YEW_BOAT)
                .pattern("# #")
                .pattern("###")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .group("boat")
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_boat"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.YEW_CHEST_BOAT)
                .input(Items.CHEST)
                .input(ModItems.YEW_BOAT)
                .group("chest_boat")
                .criterion(hasItem(ModItems.YEW_BOAT), conditionsFromItem(ModItems.YEW_BOAT))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"));
    }
}
/*?}*/
/*? if fabric && >=1.21 && <1.21.11 {*/
/*import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.alternateearth.emeraldisleflora.registry.ModItems;
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.YEW_BOAT)
                .pattern("# #")
                .pattern("###")
                .input('#', ModBlocks.YEW_PLANKS.asItem())
                .group("boat")
                .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_boat"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.YEW_CHEST_BOAT)
                .input(Items.CHEST)
                .input(ModItems.YEW_BOAT)
                .group("chest_boat")
                .criterion(hasItem(ModItems.YEW_BOAT), conditionsFromItem(ModItems.YEW_BOAT))
                .offerTo(exporter, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"));
    }
}
*/
/*?}*/
/*? if fabric && >=1.21.11 && <26.2 {*/
/*import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.alternateearth.emeraldisleflora.registry.ModItems;
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

    // getName() became abstract when FabricRecipeProvider moved onto the new
    // RecipeGenerator-based hierarchy, so it must be implemented here.
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
        public void generate() {
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

        // Explicit mod-namespaced RegistryKey: RecipeExporter writes under whatever
        // namespace it's given, so an auto-derived id would register under "minecraft:".
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

            // Items.CHAIN was renamed to Items.IRON_CHAIN between 1.21.1 and 1.21.11.
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

            createShaped(RecipeCategory.MISC, ModItems.YEW_BOAT, 1)
                    .pattern("# #")
                    .pattern("###")
                    .input('#', ModBlocks.YEW_PLANKS.asItem())
                    .group("boat")
                    .criterion(hasItem(ModBlocks.YEW_PLANKS.asItem()), conditionsFromItem(ModBlocks.YEW_PLANKS.asItem()))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_boat")));

            createShapeless(RecipeCategory.MISC, ModItems.YEW_CHEST_BOAT, 1)
                    .input(Items.CHEST)
                    .input(ModItems.YEW_BOAT)
                    .group("chest_boat")
                    .criterion(hasItem(ModItems.YEW_BOAT), conditionsFromItem(ModItems.YEW_BOAT))
                    .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_chest_boat")));
        }
    }
}
*/
/*?}*/
/*? if fabric && >=26.2 {*/
/*import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.alternateearth.emeraldisleflora.registry.ModItems;
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
        public void buildRecipes() {
            // 26.2: per-color dye constants (Items.GREEN_DYE etc.) are gone; only
            // Items.DYE (a ColorCollection<Item>) exists now, picked by DyeColor.
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

        // Explicit mod-namespaced ResourceKey, not the auto-id convenience path:
        // RecipeOutput.accept(...) writes under whatever namespace it's given.
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

            shaped(RecipeCategory.MISC, ModItems.YEW_BOAT, 1)
                    .pattern("# #")
                    .pattern("###")
                    .define('#', ModBlocks.YEW_PLANKS.asItem())
                    .group("boat")
                    .unlockedBy(getHasName(ModBlocks.YEW_PLANKS.asItem()), has(ModBlocks.YEW_PLANKS.asItem()))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_boat")));

            shapeless(RecipeCategory.MISC, ModItems.YEW_CHEST_BOAT, 1)
                    .requires(Items.CHEST)
                    .requires(ModItems.YEW_BOAT)
                    .group("chest_boat")
                    .unlockedBy(getHasName(ModItems.YEW_BOAT), has(ModItems.YEW_BOAT))
                    .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_chest_boat")));
        }
    }
}
*/
/*?}*/
