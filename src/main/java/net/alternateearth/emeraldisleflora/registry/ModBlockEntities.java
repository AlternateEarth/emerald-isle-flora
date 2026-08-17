package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
import java.util.Set;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
/*?} else {*/
/*import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
 * Owns a mod-specific {@link BlockEntityType} covering both {@link ModBlocks#YEW_SIGN}
 * and {@link ModBlocks#YEW_WALL_SIGN} - mirrors vanilla's own {@code BlockEntityType.SIGN}
 * / {@code BlockEntityTypes.SIGN}, which likewise covers both the standing and wall
 * variant under one type.
 * <p>
 * {@link ModSignBlock} and {@link ModWallSignBlock} both extend vanilla's sign classes
 * directly without this - inheriting vanilla's default block-entity construction, which
 * stamps every placed block entity with vanilla's own static {@code BlockEntityType.SIGN}.
 * That type's supports()/isValid() check tests membership in a {@code private final
 * Set<Block>} vanilla populates once, at its own bootstrap, listing only vanilla sign
 * blocks - {@code emeraldisleflora:yew_sign} was never in it, producing a real
 * "Block entity ... invalid for ticking" warning (confirmed from a real launch log) and
 * skipping the sign's real ticker (editor-timeout handling in
 * {@code SignBlockEntity.tick}).
 * <p>
 * Registering our own type and overriding {@code createBlockEntity}/{@code newBlockEntity}
 * and {@code getTicker} in {@link ModSignBlock}/{@link ModWallSignBlock} to use it is the
 * fix - confirmed against a real working reference implementation
 * ({@code bonsaistudi0s/Ghosts}, which registers its own {@code BlockEntityType} for its
 * custom signs rather than patching vanilla's set via a mixin). No custom
 * {@code BlockEntity} subclass is needed - vanilla's own {@code SignBlockEntity
 * (BlockEntityType, BlockPos, BlockState)} constructor already stores whatever type is
 * passed to it.
 * <p>
 * The factory below is a lambda (not a {@code SignBlockEntity::new} method reference to
 * the plain 2-arg constructor) specifically so that every {@code SignBlockEntity} this
 * type ever instantiates - including ones vanilla reconstructs from saved NBT on chunk
 * load, which goes through this type's factory rather than {@code createBlockEntity} -
 * is stamped with *this* type, not whatever the 2-arg constructor defaults to. The
 * lambda's reference to {@code ModBlockEntities.YEW_SIGN} is safe despite looking like a
 * forward reference to its own not-yet-initialized field: lambda bodies are deferred
 * (compiled as a separate method, not inlined into the static initializer), so it's only
 * evaluated on first real invocation, long after this class has finished initializing.
 */
public final class ModBlockEntities {
    private ModBlockEntities() { }

    // Construction differs by both mapping scheme and loader, confirmed via a real build
    // failure (not assumed) after the original 2-way <26.2/else split didn't compile:
    // Yarn's BlockEntityType.Builder (used through 1.21.1) is gone entirely at 1.21.11 -
    // its factory-taking constructor became private with it, so 1.21.11-fabric needs
    // Fabric API's own FabricBlockEntityTypeBuilder (no vanilla AT widening on Fabric),
    // while 1.21.11-neoforge can construct BlockEntityType directly - NeoForge's own
    // access transformer widens its constructor to public (same pattern already used by
    // ModWoodTypes for WoodType.register). Mojmap's own constructor (26.2) is public in
    // vanilla directly, no AT/Fabric API needed there for either loader - confirmed via
    // javap against the real, loader-agnostic 26.2 merged jar.
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

    // Same shape as YEW_SIGN above, but for the hanging sign - see
    // ModHangingSignBlockEntity's doc comment for why it needs its own BlockEntity
    // subclass (unlike the regular sign, which reuses plain SignBlockEntity directly).
    // Both create(...) calls below need an explicit <SignBlockEntity> type witness - left
    // to plain inference, it infers from the lambda's own return type
    // (ModHangingSignBlockEntity, a subtype) rather than from the eventual .build(...)
    // target context, producing a Builder<ModHangingSignBlockEntity> that can't assign to
    // this field's declared BlockEntityType<SignBlockEntity> (a real, reproduced compiler
    // error, not a style preference) - a chained .build(...) call is its own separate
    // generic invocation, so javac can't see through it to use the field's type as
    // target context for create(...) itself.
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

    /** Fabric-only: registers directly. Forge/NeoForge instead use {@link #onRegister}. */
    /*? if fabric {*/
    public static void register() {
        /*? if <26.2 {*/
        Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
        Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
        /*?} else {*/
        /*Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);*/
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

    // NeoForge: split the same way ModBlocks#onRegister is split - this whole method is
    // one big disabled block comment when neoforge is inactive, so a nested Stonecutter
    // marker inside it would be invisible to the scanner.
    /*? if neoforge && <26.2 {*/
    /*
    public static void onRegister(RegisterEvent event) {
        event.register(RegistryKeys.BLOCK_ENTITY_TYPE, helper -> {
                helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
                helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
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
        });
    }
    */
    /*?}*/
}
