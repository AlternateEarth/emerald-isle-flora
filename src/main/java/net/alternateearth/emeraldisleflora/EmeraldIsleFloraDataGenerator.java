package net.alternateearth.emeraldisleflora;

/*? if fabric {*/
import net.alternateearth.emeraldisleflora.data.ModRecipeProvider;
import net.alternateearth.emeraldisleflora.data.ModWorldGenerator;
import net.alternateearth.emeraldisleflora.registry.ModConfiguredFeatures;
import net.alternateearth.emeraldisleflora.registry.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
/*? if <26.2 {*/
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
/*?} else {*/
/*import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
*/
/*?}*/

/**
 * Fabric-only: hooked up via fabric.mod.json's "fabric-datagen" entrypoint. The
 * generated output (src/main/generated) is checked in and shared as plain resources
 * across every loader/version target, so only Fabric needs to be able to regenerate it.
 */
public class EmeraldIsleFloraDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModWorldGenerator::new);
        pack.addProvider(ModRecipeProvider::new);
    }

    /*? if <26.2 {*/
    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }
    /*?} else {*/
    /*
    // 26.2: Fabric API's own DataGeneratorEntrypoint#buildRegistry parameter type
    // changed from vanilla's old RegistryBuilder to vanilla's own (Mojmap)
    // RegistrySetBuilder, and its addRegistry(...) method was itself renamed to just
    // add(...) - confirmed via javap against the real Fabric API 26.2 jar's
    // fabric-data-generation-api-v1 submodule and the real 26.2 client jar.
    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }*/
    /*?}*/
}
/*?}*/