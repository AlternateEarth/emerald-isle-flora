package net.alternateearth.emeraldisleflora.data;

/*? if fabric {*/
import java.util.concurrent.CompletableFuture;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

// Fabric-only: see EmeraldIsleFloraDataGenerator for why.
public class ModWorldGenerator extends FabricDynamicRegistryProvider {

    public ModWorldGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        /*? if <1.21.11 {*/
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
        /*?} else {*/
        /*entries.addAll(registries.getOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(registries.getOrThrow(RegistryKeys.PLACED_FEATURE));*/
        /*?}*/
    }

    @Override
    public String getName() {
        return EmeraldIsleFlora.MOD_ID;
    }
}
/*?}*/
