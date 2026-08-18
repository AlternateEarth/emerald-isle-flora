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
 * Registers Yew Berry as an additional brewing-stand ingredient for vanilla's own Potion
 * of Poison: Awkward Potion + Yew Berry -> Potion of Poison, alongside vanilla's existing
 * Awkward Potion + Spider Eye recipe (confirmed via decompiling vanilla's real
 * BrewingRecipeRegistry/PotionBrewing bootstrap that Spider Eye, not Fermented Spider Eye,
 * is the real vanilla ingredient here - Fermented Spider Eye is only used for
 * potion-reversal mixes).
 * <p>
 * Fabric's brewing-registry API reshapes twice across this mod's version range: a plain
 * static call at 1.20.1 (FabricBrewingRecipeRegistry), a BUILD-event Builder from 1.21.1
 * (FabricBrewingRecipeRegistryBuilder), then a Mojmap rename at 26.2
 * (FabricPotionBrewingBuilder) - confirmed via javap against the real resolved jar for
 * each target, not assumed. Forge (1.20.1 only in this project's matrix) and NeoForge
 * (1.21.1+) don't share an API at all: Forge's BrewingRecipeRegistry is a plain static
 * call with no registry-freeze event, NeoForge instead fires RegisterBrewingRecipesEvent
 * on NeoForge.EVENT_BUS (the game bus, not the mod bus) handing you the same vanilla
 * PotionBrewing.Builder/BrewingRecipeRegistry.Builder Fabric's BUILD callback also uses.
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
        // Forge's BrewingRecipeRegistry.addRecipe(Ingredient, Ingredient, ItemStack)
        // matches by generic item-stack predicate, not by potion type specifically - a
        // custom IBrewingRecipe reading the real potion type off the input stack (via
        // PotionUtil, vanilla's NBT-backed potion accessor at this version) is used
        // instead, so this only ever matches an actual Awkward Potion, not any potion.
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

    // The builder's own registration method is renamed between Yarn and Mojmap -
    // registerPotionRecipe(RegistryEntry<Potion>, Item, RegistryEntry<Potion>) on Yarn's
    // BrewingRecipeRegistry.Builder, addMix(Holder<Potion>, Item, Holder<Potion>) on
    // Mojmap's PotionBrewing.Builder - confirmed via javap against the real jar for each,
    // not assumed (this rename is exactly the kind this project's docs warn about).
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
