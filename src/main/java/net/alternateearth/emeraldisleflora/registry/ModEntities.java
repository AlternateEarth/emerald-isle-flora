package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
/*? if >=1.21.11 {*/
/*import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.registry.RegistryKey;*/
/*?}*/
/*?} else {*/
/*import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
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

public final class ModEntities {

    private ModEntities() {
    }

    // EntityType.Builder's dimensions setter was renamed setDimensions -> dimensions at 1.21.
    /*? if <1.21 {*/
    public static final EntityType<ModBoatEntity> YEW_BOAT = EntityType.Builder
            .<ModBoatEntity>create(ModBoatEntity::new, SpawnGroup.MISC)
            .setDimensions(1.375F, 0.5625F)
            .build("yew_boat");
    public static final EntityType<ModChestBoatEntity> YEW_CHEST_BOAT = EntityType.Builder
            .<ModChestBoatEntity>create(ModChestBoatEntity::new, SpawnGroup.MISC)
            .setDimensions(1.375F, 0.5625F)
            .build("yew_chest_boat");
    /*?}*/
    /*? if >=1.21 && <1.21.11 {*/
    /*public static final EntityType<ModBoatEntity> YEW_BOAT = EntityType.Builder
            .<ModBoatEntity>create(ModBoatEntity::new, SpawnGroup.MISC)
            .dimensions(1.375F, 0.5625F)
            .build("yew_boat");
    public static final EntityType<ModChestBoatEntity> YEW_CHEST_BOAT = EntityType.Builder
            .<ModChestBoatEntity>create(ModChestBoatEntity::new, SpawnGroup.MISC)
            .dimensions(1.375F, 0.5625F)
            .build("yew_chest_boat");*/
    /*?}*/
    /*? if fabric && >=1.21.11 && <26.2 {*/
    /*public static final EntityType<BoatEntity> YEW_BOAT = EntityType.Builder
            .<BoatEntity>create((type, world) -> new BoatEntity(type, world, () -> ModItems.YEW_BOAT), SpawnGroup.MISC)
            .dimensions(1.375F, 0.5625F)
            .build(entityId("yew_boat"));
    public static final EntityType<ChestBoatEntity> YEW_CHEST_BOAT = EntityType.Builder
            .<ChestBoatEntity>create((type, world) -> new ChestBoatEntity(type, world, () -> ModItems.YEW_CHEST_BOAT), SpawnGroup.MISC)
            .dimensions(1.375F, 0.5625F)
            .build(entityId("yew_chest_boat"));*/
    /*?}*/
    /*? if neoforge && >=1.21.11 && <26.2 {*/
    /*public static final EntityType<BoatEntity> YEW_BOAT = EntityType.Builder
            .<BoatEntity>create((type, world) -> new BoatEntity(type, world, () -> ModItems.YEW_BOAT), SpawnGroup.MISC)
            .dimensions(1.375F, 0.5625F)
            .build(entityId("yew_boat"));
    public static final EntityType<ChestBoatEntity> YEW_CHEST_BOAT = EntityType.Builder
            .<ChestBoatEntity>create((type, world) -> new ChestBoatEntity(type, world, () -> ModItems.YEW_CHEST_BOAT), SpawnGroup.MISC)
            .dimensions(1.375F, 0.5625F)
            .build(entityId("yew_chest_boat"));*/
    /*?}*/
    /*? if >=26.2 {*/
    /*public static final EntityType<Boat> YEW_BOAT = EntityType.Builder
            .<Boat>of((type, level) -> new Boat(type, level, () -> ModItems.YEW_BOAT), MobCategory.MISC)
            .sized(1.375F, 0.5625F)
            .build(entityId("yew_boat"));
    public static final EntityType<ChestBoat> YEW_CHEST_BOAT = EntityType.Builder
            .<ChestBoat>of((type, level) -> new ChestBoat(type, level, () -> ModItems.YEW_CHEST_BOAT), MobCategory.MISC)
            .sized(1.375F, 0.5625F)
            .build(entityId("yew_chest_boat"));*/
    /*?}*/

    // >=1.21.11 registry-id requirement (see ModItems' itemId() for the same requirement on items).
    /*? if <26.2 {*/
    /*? if >=1.21.11 {*/
    /*private static RegistryKey<EntityType<?>> entityId(String name) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(EmeraldIsleFlora.MOD_ID, name));
    }*/
    /*?}*/
    /*?} else {*/
    /*private static ResourceKey<EntityType<?>> entityId(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, name));
    }*/
    /*?}*/

    /** Fabric-only: registers directly. Forge/NeoForge instead use {@link #onRegister}. */
    public static void register() {
        /*? if fabric {*/
        EmeraldIsleFlora.LOGGER.info("Registering Entities for " + EmeraldIsleFlora.MOD_ID);

        /*? if <26.2 {*/
        Registry.register(Registries.ENTITY_TYPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
        Registry.register(Registries.ENTITY_TYPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);
        /*?} else {*/
        /*Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);*/
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering Entities for " + EmeraldIsleFlora.MOD_ID);
        /*?}*/
    }

    /*? if forge {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);
        });
    }*/
    /*?}*/

    /*? if neoforge && <26.2 {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(RegistryKeys.ENTITY_TYPE, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);
        });
    }*/
    /*?}*/
    /*? if neoforge && >=26.2 {*/
    /*public static void onRegister(RegisterEvent event) {
        event.register(Registries.ENTITY_TYPE, helper -> {
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_boat"), YEW_BOAT);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_chest_boat"), YEW_CHEST_BOAT);
        });
    }*/
    /*?}*/
}
