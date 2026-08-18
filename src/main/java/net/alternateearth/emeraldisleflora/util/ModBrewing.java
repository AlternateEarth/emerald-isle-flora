package net.alternateearth.emeraldisleflora.util;

/*? if <1.21 {*/
import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.registry.ModItems;
import net.minecraft.potion.Potions;
/*? if fabric {*/
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;
/*?}*/
/*? if forge {*/
/*import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
*/
/*?}*/

public final class ModBrewing {
    public static void register() {
        EmeraldIsleFlora.LOGGER.info("Registering Brewing Recipes for " + EmeraldIsleFlora.MOD_ID);
        
        /*? if fabric {*/
        FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Ingredient.ofItems(ModItems.YEW_BERRY), Potions.POISON);
        /*?} else {*/
        // Forge's BrewingRecipeRegistry.addRecipe(Ingredient, Ingredient, ItemStack)
        // matches by generic item-stack predicate, not by potion type specifically - a
        // custom IBrewingRecipe reading the real potion type off the input stack (via
        // PotionUtil, vanilla's NBT-backed potion accessor at this version) is used
        // instead, so this only ever matches an actual Awkward Potion, not any potion.
        /*BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
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
        });*/
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering Brewing Recipes for " + EmeraldIsleFlora.MOD_ID);
    }
}
/*?}*/