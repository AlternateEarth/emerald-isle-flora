package net.alternateearth.emeraldisleflora.util;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModItems;
/*? if fabric && <1.21 {*/
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
/*?}*/
/*? if fabric && >=1.21 && <26.2 {*/
/*import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
*/
/*?}*/
/*? if fabric && >=26.2 {*/
/*import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
*/
/*?}*/
/*? if forge {*/
/*import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
*/
/*?}*/
/*? if neoforge && <26.2 {*/
/*import net.minecraft.potion.Potions;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
*/
/*?}*/
/*? if neoforge && >=26.2 {*/
/*import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
*/
/*?}*/

/**
 * Registers Yew Berry as an additional brewing-stand ingredient for vanilla's Potion of
 * Poison (Awkward Potion + Yew Berry), alongside vanilla's own Spider Eye recipe.
 * Fabric's brewing-registry API and Forge/NeoForge's registration mechanism both differ
 * across this mod's version range - see the per-branch comments below.
 */
public final class ModBrewing {

    /** Fabric/Forge: registers directly (neither needs a registry-freeze event at this
     * version). NeoForge instead uses {@link #onRegisterBrewingRecipes}. */
    public static void register() {
        EmeraldIsleFlora.LOGGER.info("Registering Brewing Recipes for " + EmeraldIsleFlora.MOD_ID);

        /*? if fabric {*/
        /*? if <1.21 {*/
        FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Ingredient.ofItems(ModItems.YEW_BERRY), Potions.POISON);
        /*?}*/
        /*? if >=1.21 && <26.2 {*/
        /*FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
            builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.ofItems(ModItems.YEW_BERRY), Potions.POISON));*/
        /*?}*/
        /*? if >=26.2 {*/
        /*FabricPotionBrewingBuilder.BUILD.register(builder ->
            builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(ModItems.YEW_BERRY), Potions.POISON));*/
        /*?}*/
        /*?}*/

        /*? if forge {*/
        /*
        // Forge's addRecipe matches by generic item-stack predicate, not potion type, so a
        // custom IBrewingRecipe reads the real potion off the stack via PotionUtil instead.
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack stack) {
                return stack.getItem() == Items.POTION && PotionUtil.getPotion(stack) == Potions.AWKWARD;
            }

            @Override
            public boolean isIngredient(ItemStack stack) {
                return stack.getItem() == ModItems.YEW_BERRY;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                if (!isInput(input) || !isIngredient(ingredient)) {
                    return ItemStack.EMPTY;
                }
                return PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.POISON);
            }
        });
        */
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering Brewing Recipes for " + EmeraldIsleFlora.MOD_ID);
    }

    // The builder's registration method is renamed: registerPotionRecipe on Yarn's
    // BrewingRecipeRegistry.Builder, addMix on Mojmap's PotionBrewing.Builder.
    /*? if neoforge && <26.2 {*/
    /*public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().registerPotionRecipe(Potions.AWKWARD, ModItems.YEW_BERRY, Potions.POISON);
    }*/
    /*?}*/
    /*? if neoforge && >=26.2 {*/
    /*public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addMix(Potions.AWKWARD, ModItems.YEW_BERRY, Potions.POISON);
    }*/
    /*?}*/
}
