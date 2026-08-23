package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;

/*? if fabric && <1.21 {*/
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;
/*?}*/
/*? if fabric && >=1.21 && <26.2 {*/
/*import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;*/
/*?}*/
/*? if forgeLike && <26.2 {*/
/*import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;*/
/*?}*/
/*? if fabric && >=26.2 {*/
/*import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;*/
/*?}*/
/*? if neoforge && >=26.2 {*/
/*import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;*/
/*?}*/

/**
 * Registers a {@link WoodType} so a sign's 3D post model can find its wood texture - other wood blocks only 
 * need an unregistered instance for interaction sounds. Forge/NeoForge widen {@code WoodType.register} to 
 * public via their access transformers; Fabric instead needs Fabric API's own registration helper, which
 * changed shape from {@code WoodTypeRegistry} to {@code WoodTypeBuilder} at 1.21.
 */
public final class ModWoodTypes {

    private ModWoodTypes() {
    }

    /*? if fabric && <1.21 {*/
    public static WoodType register(String name) {
        return WoodTypeRegistry.register(Identifier.of(EmeraldIsleFlora.MOD_ID, name), BlockSetType.OAK);
    }
    /*?}*/
    /*? if fabric && >=1.21 && <26.2 {*/
    /*public static WoodType register(String name) {
        return WoodTypeBuilder.copyOf(WoodType.OAK).register(Identifier.of(EmeraldIsleFlora.MOD_ID, name), BlockSetType.OAK);
    }*/
    /*?}*/
    /*? if forgeLike && <26.2 {*/
    /*public static WoodType register(String name) {
        return WoodType.register(new WoodType(EmeraldIsleFlora.MOD_ID + ":" + name, BlockSetType.OAK));
    }*/
    /*?}*/
    /*? if fabric && >=26.2 {*/
    /*public static WoodType register(String name) {
        return WoodTypeBuilder.copyOf(WoodType.OAK).register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, name), BlockSetType.OAK);
    }*/
    /*?}*/
    /*? if neoforge && >=26.2 {*/
    /*public static WoodType register(String name) {
        return WoodType.register(new WoodType(EmeraldIsleFlora.MOD_ID + ":" + name, BlockSetType.OAK));
    }*/
    /*?}*/
}
