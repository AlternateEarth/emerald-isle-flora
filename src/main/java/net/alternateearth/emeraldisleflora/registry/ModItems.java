package net.alternateearth.emeraldisleflora.registry;

/*? if <1.21 {*/
import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
/*? if forge {*/
/*import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
*/
/*?}*/

public final class ModItems {
    
    public static final Item YEW_BERRY = new Item(new Item.Settings().food(FoodComponents.POISONOUS_POTATO));

    public static void register() {
        /*? if fabric {*/
        EmeraldIsleFlora.LOGGER.info("Registering Items for " + EmeraldIsleFlora.MOD_ID);

        Registry.register(Registries.ITEM, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY);

        EmeraldIsleFlora.LOGGER.info("Finished registering Items for " + EmeraldIsleFlora.MOD_ID);
        /*?}*/
    }

    /*? if forge {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ITEMS, helper ->
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY));
    }*/
    /*?}*/
}
/*?}*/