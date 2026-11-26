package net.alternateearth.emeraldisleflora.mixin;

/*? if <1.21 {*/
import com.mojang.serialization.Codec;
/*?} else {*/
/*import com.mojang.serialization.MapCodec;*/
/*?}*/
/*? if <26.2 {*/
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
/*?} else {*/
/*import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;*/
/*?}*/
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Exposes {@code TrunkPlacerType}'s private constructor so the mod can register its own trunk placer type.
 * Vanilla only builds its types through a private static {@code register} that always uses the
 * {@code minecraft} namespace.
 */
@Mixin(TrunkPlacerType.class)
public interface TrunkPlacerTypeInvoker {

/*? if <1.21 {*/
    @Invoker("<init>")
    static <P extends TrunkPlacer> TrunkPlacerType<P> emeraldisleflora$create(Codec<P> codec) {
        throw new AssertionError();
    }
/*?} else {*/
/*    @Invoker("<init>")
    static <P extends TrunkPlacer> TrunkPlacerType<P> emeraldisleflora$create(MapCodec<P> codec) {
        throw new AssertionError();
    }*/
/*?}*/
}
