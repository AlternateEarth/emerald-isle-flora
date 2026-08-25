package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
import java.util.Set;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
/*? if >=1.21.11 {*/
/*import net.minecraft.block.entity.ShelfBlockEntity;*/
/*?}*/
/*?} else {*/
/*import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShelfBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;*/
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
/*? if neoforge && <26.2 {*/
/*import net.minecraft.registry.RegistryKeys;*/
/*?}*/
/*? if neoforge && >=26.2 {*/
/*import net.minecraft.core.registries.Registries;*/
/*?}*/
/*? if fabric && >=1.21.11 && <26.2 {*/
/*import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;*/
/*?}*/

/**
 * Owns a mod-specific {@link BlockEntityType} covering both {@link ModBlocks#YEW_SIGN} and
 * {@link ModBlocks#YEW_WALL_SIGN}, mirroring vanilla's own sign type. Without this, our
 * signs inherit vanilla's static {@code BlockEntityType.SIGN}, whose supports()/isValid()
 * check only lists vanilla sign blocks - producing a real "Block entity ... invalid for
 * ticking" warning and skipping the sign's editor-timeout ticker.
 * <p>
 * The factory is a lambda rather than a plain constructor reference so every
 * {@code SignBlockEntity} this type creates - including ones rebuilt from saved NBT - gets
 * stamped with this type, not a default one.
 */
public final class ModBlockEntities {
    private ModBlockEntities() { }

    /*? if <1.21.11 {*/
    public static final BlockEntityType<SignBlockEntity> YEW_SIGN = BlockEntityType.Builder
            .create((pos, state) -> new SignBlockEntity(ModBlockEntities.YEW_SIGN, pos, state), ModBlocks.YEW_SIGN, ModBlocks.YEW_WALL_SIGN)
            .build(null);
    /*?}*/
    /*? if fabric && >=1.21.11 && <26.2 {*/
    /*public static final BlockEntityType<SignBlockEntity> YEW_SIGN = FabricBlockEntityTypeBuilder
            .create((pos, state) -> new SignBlockEntity(ModBlockEntities.YEW_SIGN, pos, state), ModBlocks.YEW_SIGN, ModBlocks.YEW_WALL_SIGN)
            .build();*/
    /*?}*/
    /*? if neoforge && >=1.21.11 && <26.2 {*/
    /*public static final BlockEntityType<SignBlockEntity> YEW_SIGN = new BlockEntityType<>(
            (pos, state) -> new SignBlockEntity(ModBlockEntities.YEW_SIGN, pos, state), Set.of(ModBlocks.YEW_SIGN, ModBlocks.YEW_WALL_SIGN));*/
    /*?}*/
    /*? if >=26.2 {*/
    /*public static final BlockEntityType<SignBlockEntity> YEW_SIGN = new BlockEntityType<>(
            (pos, state) -> new SignBlockEntity(ModBlockEntities.YEW_SIGN, pos, state), Set.of(ModBlocks.YEW_SIGN, ModBlocks.YEW_WALL_SIGN));*/
    /*?}*/

    /*? if <1.21.11 {*/
    public static final BlockEntityType<SignBlockEntity> YEW_HANGING_SIGN = BlockEntityType.Builder
            .<SignBlockEntity>create((pos, state) -> new ModHangingSignBlockEntity(pos, state), ModBlocks.YEW_HANGING_SIGN, ModBlocks.YEW_WALL_HANGING_SIGN)
            .build(null);
    /*?}*/
    /*? if fabric && >=1.21.11 && <26.2 {*/
    /*public static final BlockEntityType<SignBlockEntity> YEW_HANGING_SIGN = FabricBlockEntityTypeBuilder
            .<SignBlockEntity>create((pos, state) -> new ModHangingSignBlockEntity(pos, state), ModBlocks.YEW_HANGING_SIGN, ModBlocks.YEW_WALL_HANGING_SIGN)
            .build();*/
    /*?}*/
    /*? if neoforge && >=1.21.11 && <26.2 {*/
    /*public static final BlockEntityType<SignBlockEntity> YEW_HANGING_SIGN = new BlockEntityType<>(
            (pos, state) -> new ModHangingSignBlockEntity(pos, state), Set.of(ModBlocks.YEW_HANGING_SIGN, ModBlocks.YEW_WALL_HANGING_SIGN));*/
    /*?}*/
    /*? if >=26.2 {*/
    /*public static final BlockEntityType<SignBlockEntity> YEW_HANGING_SIGN = new BlockEntityType<>(
            (pos, state) -> new ModHangingSignBlockEntity(pos, state), Set.of(ModBlocks.YEW_HANGING_SIGN, ModBlocks.YEW_WALL_HANGING_SIGN));*/
    /*?}*/

    /*? if fabric && >=1.21.11 && <26.2 {*/
    /*public static final BlockEntityType<ShelfBlockEntity> YEW_SHELF = FabricBlockEntityTypeBuilder
            .<ShelfBlockEntity>create(ModShelfBlockEntity::new, ModBlocks.YEW_SHELF)
            .build();*/
    /*?}*/
    /*? if neoforge && >=1.21.11 && <26.2 {*/
    /*public static final BlockEntityType<ShelfBlockEntity> YEW_SHELF = new BlockEntityType<>(
            ModShelfBlockEntity::new, Set.of(ModBlocks.YEW_SHELF));*/
    /*?}*/
    /*? if >=26.2 {*/
    /*public static final BlockEntityType<ShelfBlockEntity> YEW_SHELF = new BlockEntityType<>(
            ModShelfBlockEntity::new, Set.of(ModBlocks.YEW_SHELF));*/
    /*?}*/

    /** Fabric-only: registers directly. Forge/NeoForge instead use {@link #onRegister}. */
    /*? if fabric {*/
    public static void register() {
        /*? if <26.2 {*/
        Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
        Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
        /*? if >=1.21.11 {*/
        /*Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_shelf"), YEW_SHELF);*/
        /*?}*/
        /*?} else {*/
        /*Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_shelf"), YEW_SHELF);*/
        /*?}*/
    }
    /*?}*/

    /*? if forge {*/
    /*
    public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, helper -> {
                helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
                helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
        });
    }
    */
    /*?}*/

    /*? if neoforge && <1.21.11 {*/
    /*
    public static void onRegister(RegisterEvent event) {
        event.register(RegistryKeys.BLOCK_ENTITY_TYPE, helper -> {
                helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
                helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
        });
    }
    */
    /*?}*/
    /*? if neoforge && >=1.21.11 && <26.2 {*/
    /*
    public static void onRegister(RegisterEvent event) {
        event.register(RegistryKeys.BLOCK_ENTITY_TYPE, helper -> {
                helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
                helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
                helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_shelf"), YEW_SHELF);
        });
    }
    */
    /*?}*/
    /*? if neoforge && >=26.2 {*/
    /*
    public static void onRegister(RegisterEvent event) {
        event.register(Registries.BLOCK_ENTITY_TYPE, helper -> {
                helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
                helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
                helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_shelf"), YEW_SHELF);
        });
    }
    */
    /*?}*/
}
