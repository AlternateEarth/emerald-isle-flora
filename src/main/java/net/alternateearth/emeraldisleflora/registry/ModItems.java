package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
/*? if <1.21 {*/
import net.minecraft.item.FoodComponents;
/*?}*/
/*? if >=1.21 && <1.21.11 {*/
/*import net.minecraft.component.type.FoodComponents;*/
/*?}*/
/*? if >=1.21.11 {*/
/*import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.BoatItem;
import net.minecraft.registry.RegistryKey;*/
/*?}*/
/*?} else {*/
/*import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumables;
*/
/*?}*/
/*? if forge {*/
/*import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
*/
/*?}*/
/*? if neoforge {*/
/*import net.neoforged.neoforge.registries.RegisterEvent;
*/
/*?}*/

public final class ModItems {

    /*? if <26.2 {*/
    /*? if <1.21 {*/
    public static final Item YEW_BERRY = new Item(new Item.Settings().food(FoodComponents.POISONOUS_POTATO));
    /*?}*/
    /*? if >=1.21 && <1.21.11 {*/
    /*public static final Item YEW_BERRY = new Item(new Item.Settings().food(FoodComponents.POISONOUS_POTATO));*/
    /*?}*/
    /*? if >=1.21.11 {*/
    /*public static final Item YEW_BERRY = new Item(new Item.Settings()
            .registryKey(itemId("yew_berry"))
            .food(FoodComponents.POISONOUS_POTATO, ConsumableComponents.POISONOUS_POTATO));*/
    /*?}*/
    /*?} else {*/
    /*public static final Item YEW_BERRY = new Item(new Item.Properties()
            .setId(itemId("yew_berry"))
            .food(Foods.POISONOUS_POTATO, Consumables.POISONOUS_POTATO));*/
    /*?}*/

    /*? if <1.21.11 {*/
    public static final Item YEW_BOAT = new ModBoatItem(false, new Item.Settings().maxCount(1));
    public static final Item YEW_CHEST_BOAT = new ModBoatItem(true, new Item.Settings().maxCount(1));
    /*?}*/
    /*? if >=1.21.11 && <26.2 {*/
    /*public static final Item YEW_BOAT = new BoatItem(ModEntities.YEW_BOAT, new Item.Settings().registryKey(itemId("yew_boat")).maxCount(1));
    public static final Item YEW_CHEST_BOAT = new BoatItem(ModEntities.YEW_CHEST_BOAT, new Item.Settings().registryKey(itemId("yew_chest_boat")).maxCount(1));*/
    /*?}*/
    /*? if >=26.2 {*/
    /*public static final Item YEW_BOAT = new BoatItem(ModEntities.YEW_BOAT, new Item.Properties().setId(itemId("yew_boat")).stacksTo(1));
    public static final Item YEW_CHEST_BOAT = new BoatItem(ModEntities.YEW_CHEST_BOAT, new Item.Properties().setId(itemId("yew_chest_boat")).stacksTo(1));*/
    /*?}*/

    // >=1.21.11 registry-id requirement
    /*? if <26.2 {*/
    /*? if >=1.21.11 {*/
    /*private static RegistryKey<Item> itemId(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(EmeraldIsleFlora.MOD_ID, name));
    }*/
    /*?}*/
    /*?} else {*/
    /*private static ResourceKey<Item> itemId(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, name));
    }*/
    /*?}*/

    /** Fabric-only: registers directly. Forge/NeoForge instead use {@link #onRegister}. */
    public static void register() {
        /*? if fabric {*/
        EmeraldIsleFlora.LOGGER.info("Registering Items for " + EmeraldIsleFlora.MOD_ID);

        /*? if <26.2 {*/
        Registry.register(Registries.ITEM, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY);
        Registry.register(Registries.ITEM, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
        Registry.register(Registries.ITEM, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);
        /*?} else {*/
        /*Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);*/
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering Items for " + EmeraldIsleFlora.MOD_ID);
        /*?}*/
    }

    /*? if forge {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ITEMS, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);
        });
    }*/
    /*?}*/

    /*? if neoforge && <26.2 {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(RegistryKeys.ITEM, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);
        });
    }*/
    /*?}*/
    /*? if neoforge && >=26.2 {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(Registries.ITEM, helper -> {
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);
        });
    }*/
    /*?}*/
}
