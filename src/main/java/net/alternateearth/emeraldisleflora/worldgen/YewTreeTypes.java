package net.alternateearth.emeraldisleflora.worldgen;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.alternateearth.emeraldisleflora.mixin.FoliagePlacerTypeInvoker;
import net.alternateearth.emeraldisleflora.mixin.TreeDecoratorTypeInvoker;
import net.alternateearth.emeraldisleflora.mixin.TrunkPlacerTypeInvoker;
/*? if <26.2 {*/
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
/*?} else {*/
/*import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;*/
/*?}*/

/**
 * The registered worldgen types for yew trees. The placers and the decorator return these from their type
 * hook, and the tree configs refer to them by id: {@code emeraldisleflora:yew_trunk_placer},
 * {@code emeraldisleflora:yew_foliage_placer} and {@code emeraldisleflora:yew_clearance_decorator}.
 *
 * <p>Vanilla's type constructors are private, so they are built through the invoker mixins. Fabric registers
 * directly from the mod initializer; Forge and NeoForge register from {@code RegisterEvent}. Both must run
 * before any tree can generate or any tree config is serialized.
 */
public final class YewTreeTypes {

    public static TrunkPlacerType<YewTrunkPlacer> TRUNK_PLACER;
    public static FoliagePlacerType<YewFoliagePlacer> FOLIAGE_PLACER;
    public static TreeDecoratorType<YewClearanceDecorator> CLEARANCE_DECORATOR;

    private YewTreeTypes() {}

/*? if <26.2 {*/
    private static Identifier id(String path) {
        return Identifier.of(EmeraldIsleFlora.MOD_ID, path);
    }
/*?} else {*/
/*    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, path);
    }*/
/*?}*/

    /** Builds the three types once. Safe to call from every registry callback, whichever fires first. */
    private static void createTypes() {
        if (TRUNK_PLACER != null) return;
        TRUNK_PLACER = TrunkPlacerTypeInvoker.emeraldisleflora$create(YewTrunkPlacer.CODEC);
        FOLIAGE_PLACER = FoliagePlacerTypeInvoker.emeraldisleflora$create(YewFoliagePlacer.CODEC);
        CLEARANCE_DECORATOR = TreeDecoratorTypeInvoker.emeraldisleflora$create(YewClearanceDecorator.CODEC);
    }

/*? if fabric && <26.2 {*/
    /** Fabric-only: registers directly. Forge/NeoForge instead use {@code onRegister}. */
    public static void register() {
        createTypes();
        net.minecraft.registry.Registry.register(net.minecraft.registry.Registries.TRUNK_PLACER_TYPE, id("yew_trunk_placer"), TRUNK_PLACER);
        net.minecraft.registry.Registry.register(net.minecraft.registry.Registries.FOLIAGE_PLACER_TYPE, id("yew_foliage_placer"), FOLIAGE_PLACER);
        net.minecraft.registry.Registry.register(net.minecraft.registry.Registries.TREE_DECORATOR_TYPE, id("yew_clearance_decorator"), CLEARANCE_DECORATOR);
    }
/*?}*/
/*? if fabric && >=26.2 {*/
/*    public static void register() {
        createTypes();
        net.minecraft.core.Registry.register(net.minecraft.core.registries.BuiltInRegistries.TRUNK_PLACER_TYPE, id("yew_trunk_placer"), TRUNK_PLACER);
        net.minecraft.core.Registry.register(net.minecraft.core.registries.BuiltInRegistries.FOLIAGE_PLACER_TYPE, id("yew_foliage_placer"), FOLIAGE_PLACER);
        net.minecraft.core.Registry.register(net.minecraft.core.registries.BuiltInRegistries.TREE_DECORATOR_TYPE, id("yew_clearance_decorator"), CLEARANCE_DECORATOR);
    }*/
/*?}*/
/*? if forge {*/
/*    public static void onRegister(net.minecraftforge.registries.RegisterEvent event) {
        event.register(net.minecraft.registry.RegistryKeys.TRUNK_PLACER_TYPE, helper -> {
            createTypes();
            helper.register(id("yew_trunk_placer"), TRUNK_PLACER);
        });
        event.register(net.minecraft.registry.RegistryKeys.FOLIAGE_PLACER_TYPE, helper -> {
            createTypes();
            helper.register(id("yew_foliage_placer"), FOLIAGE_PLACER);
        });
        event.register(net.minecraft.registry.RegistryKeys.TREE_DECORATOR_TYPE, helper -> {
            createTypes();
            helper.register(id("yew_clearance_decorator"), CLEARANCE_DECORATOR);
        });
    }*/
/*?}*/
/*? if neoforge && <26.2 {*/
/*    public static void onRegister(net.neoforged.neoforge.registries.RegisterEvent event) {
        event.register(net.minecraft.registry.RegistryKeys.TRUNK_PLACER_TYPE, helper -> {
            createTypes();
            helper.register(id("yew_trunk_placer"), TRUNK_PLACER);
        });
        event.register(net.minecraft.registry.RegistryKeys.FOLIAGE_PLACER_TYPE, helper -> {
            createTypes();
            helper.register(id("yew_foliage_placer"), FOLIAGE_PLACER);
        });
        event.register(net.minecraft.registry.RegistryKeys.TREE_DECORATOR_TYPE, helper -> {
            createTypes();
            helper.register(id("yew_clearance_decorator"), CLEARANCE_DECORATOR);
        });
    }*/
/*?}*/
/*? if neoforge && >=26.2 {*/
/*    public static void onRegister(net.neoforged.neoforge.registries.RegisterEvent event) {
        event.register(net.minecraft.core.registries.Registries.TRUNK_PLACER_TYPE, helper -> {
            createTypes();
            helper.register(id("yew_trunk_placer"), TRUNK_PLACER);
        });
        event.register(net.minecraft.core.registries.Registries.FOLIAGE_PLACER_TYPE, helper -> {
            createTypes();
            helper.register(id("yew_foliage_placer"), FOLIAGE_PLACER);
        });
        event.register(net.minecraft.core.registries.Registries.TREE_DECORATOR_TYPE, helper -> {
            createTypes();
            helper.register(id("yew_clearance_decorator"), CLEARANCE_DECORATOR);
        });
    }*/
/*?}*/
}
