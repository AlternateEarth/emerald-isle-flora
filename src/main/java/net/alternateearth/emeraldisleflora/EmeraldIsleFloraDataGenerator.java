package net.alternateearth.emeraldisleflora;

/*? if fabric {*/
import net.alternateearth.emeraldisleflora.data.ModWorldGenerator;
import net.alternateearth.emeraldisleflora.registry.ModConfiguredFeatures;
import net.alternateearth.emeraldisleflora.registry.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

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
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }
}
/*?}*/