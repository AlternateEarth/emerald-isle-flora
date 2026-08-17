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
 * Registers a real (not just constructed) {@link WoodType} - needed so a sign's actual
 * 3D post model can find its wood texture at all (vanilla's sign renderer looks the
 * WoodType up in an internal registered-instances set at texture-atlas-stitch time, not
 * just by reading its fields). Every other wood block (fence gate, door, trapdoor,
 * pressure plate, button) only needs {@link WoodType#setType()} for interaction sounds,
 * which works fine on a plain unregistered instance - this class exists specifically so
 * signs get the real thing too.
 * <p>
 * Vanilla's own {@code WoodType.register(WoodType)} is private with no public API on
 * Fabric, but confirmed via javap against the real jars to be widened to <b>public</b> by
 * both Forge's and NeoForge's own access transformers (permanently baked into their
 * patched jars, not something this mod needs its own access widener for) - so
 * Forge/NeoForge can call it directly on every version.
 * <p>
 * Fabric has no such transformer for external mods; Fabric API instead ships its own
 * access widener scoped to its own compiled classes, exposed to the rest of us via
 * {@code WoodTypeRegistry.register(Identifier, BlockSetType)} - but that class was
 * removed starting at the 1.21.1 Fabric API build actually used by this project
 * (confirmed against the real jars: present at 0.92.x/1.20.1, absent from 0.116.x/1.21.1
 * onward including 1.21.11 and 26.2), replaced by a differently-shaped builder,
 * {@code WoodTypeBuilder}, for every Fabric target from 1.21.1 on - so the real boundary
 * here is {@code <1.21}, not the {@code <26.2}/{@code >=26.2} split most of this mod's
 * other Yarn/Mojmap API changes land on.
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
