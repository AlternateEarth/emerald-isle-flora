package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.util.math.BlockPos;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * A {@link HangingSignBlockEntity} subclass that swaps in this mod's own
 * {@link BlockEntityType} via {@code getType()}. Must extend vanilla's
 * {@code HangingSignBlockEntity} (not {@code SignBlockEntity}) because the client picks
 * the edit screen via an {@code instanceof HangingSignBlockEntity} check.
 */
public class ModHangingSignBlockEntity extends HangingSignBlockEntity {

    public ModHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.YEW_HANGING_SIGN;
    }

    /*? if >=26.2 {*/
    /*@Override
    public boolean isValidBlockState(BlockState state) {
        return this.getType().isValid(state);
    }*/
    /*?}*/
}
