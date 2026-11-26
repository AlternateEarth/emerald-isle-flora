package net.alternateearth.emeraldisleflora.mixin;

/*? if <1.21 {*/
import com.mojang.serialization.Codec;
/*?} else {*/
/*import com.mojang.serialization.MapCodec;*/
/*?}*/
/*? if <26.2 {*/
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
/*?} else {*/
/*import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;*/
/*?}*/
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** Exposes {@code FoliagePlacerType}'s private constructor. See {@link TrunkPlacerTypeInvoker}. */
@Mixin(FoliagePlacerType.class)
public interface FoliagePlacerTypeInvoker {

/*? if <1.21 {*/
    @Invoker("<init>")
    static <P extends FoliagePlacer> FoliagePlacerType<P> emeraldisleflora$create(Codec<P> codec) {
        throw new AssertionError();
    }
/*?} else {*/
/*    @Invoker("<init>")
    static <P extends FoliagePlacer> FoliagePlacerType<P> emeraldisleflora$create(MapCodec<P> codec) {
        throw new AssertionError();
    }*/
/*?}*/
}
