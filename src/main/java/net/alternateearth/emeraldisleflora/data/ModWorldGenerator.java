package net.alternateearth.emeraldisleflora.data;

/*? if fabric {*/
import java.util.concurrent.CompletableFuture;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
/*?} else {*/
/*import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
*/
/*?}*/

// Fabric-only: see EmeraldIsleFloraDataGenerator for why.
public class ModWorldGenerator extends FabricDynamicRegistryProvider {

    /*? if <26.2 {*/
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
    /*?} else {*/
    /*
    // 26.2: Fabric API's own FabricDataOutput was renamed FabricPackOutput (per-version
    // rename, not just a repackage - confirmed via javap against the real Fabric API
    // 26.2 jar's fabric-data-generation-api-v1 submodule); RegistryWrapper.WrapperLookup
    // doesn't exist in Mojmap at all - it's vanilla's own core.HolderLookup.Provider,
    // and the entries.addAll(...) call now needs a HolderLookup.RegistryLookup<T>
    // (obtained via registries.lookupOrThrow(ResourceKey), not the Provider itself)
    // rather than the Provider directly.
    public ModWorldGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
    }*/
    /*?}*/

    @Override
    public String getName() {
        return EmeraldIsleFlora.MOD_ID;
    }
}
/*?}*/
