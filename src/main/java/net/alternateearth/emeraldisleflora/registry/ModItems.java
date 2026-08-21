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
import net.minecraft.registry.RegistryKey;*/
/*?}*/
/*?} else {*/
/*import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.Foods;
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

/**
 * First plain (non-BlockItem) item in this mod - see ModBlocks for the block-registration
 * shape this mirrors. Yew Berry reuses vanilla's own POISONOUS_POTATO food/consumable
 * constants directly rather than hand-rolling hunger/saturation/poison-chance numbers, so
 * its eat behavior is guaranteed identical to a real Poisonous Potato on every version.
 * <p>
 * Food/consumable API genuinely reshapes across this mod's version range: FoodComponent
 * carried both nutrition and status effects directly through 1.21.1, then 1.21.11 split
 * status effects out into a separate ConsumableComponent and changed Item.Settings#food to
 * a two-arg overload - confirmed via javap/tiny-mappings against the real jars for each
 * version, not assumed.
 */
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

    // Same >=1.21.11 registry-id requirement ModBlocks.itemId() exists for - see its doc
    // comment. Duplicated here rather than reused from ModBlocks since that one's private.
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
        /*?} else {*/
        /*Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY);*/
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering Items for " + EmeraldIsleFlora.MOD_ID);
        /*?}*/
    }

    /*? if forge {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ITEMS, helper ->
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY));
    }*/
    /*?}*/

    /*? if neoforge && <26.2 {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(RegistryKeys.ITEM, helper ->
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY));
    }*/
    /*?}*/
    /*? if neoforge && >=26.2 {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(Registries.ITEM, helper ->
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_berry"), YEW_BERRY));
    }*/
    /*?}*/
}
