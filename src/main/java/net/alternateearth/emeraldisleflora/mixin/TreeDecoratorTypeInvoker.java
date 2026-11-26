package net.alternateearth.emeraldisleflora.mixin;

/*? if <1.21 {*/
import com.mojang.serialization.Codec;
/*?} else {*/
/*import com.mojang.serialization.MapCodec;*/
/*?}*/
/*? if <26.2 {*/
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
/*?} else {*/
/*import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;*/
/*?}*/
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** Exposes {@code TreeDecoratorType}'s private constructor. See {@link TrunkPlacerTypeInvoker}. */
@Mixin(TreeDecoratorType.class)
public interface TreeDecoratorTypeInvoker {

/*? if <1.21 {*/
    @Invoker("<init>")
    static <P extends TreeDecorator> TreeDecoratorType<P> emeraldisleflora$create(Codec<P> codec) {
        throw new AssertionError();
    }
/*?} else {*/
/*    @Invoker("<init>")
    static <P extends TreeDecorator> TreeDecoratorType<P> emeraldisleflora$create(MapCodec<P> codec) {
        throw new AssertionError();
    }*/
/*?}*/
}
