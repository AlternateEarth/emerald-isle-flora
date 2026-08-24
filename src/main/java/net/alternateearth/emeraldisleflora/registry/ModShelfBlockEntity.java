package net.alternateearth.emeraldisleflora.registry;

/*? if >=26.2 {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * A {@link ShelfBlockEntity} subclass that swaps in this mod's own {@link BlockEntityType} via
 * {@code getType()} - same reason and pattern as {@link ModHangingSignBlockEntity}: vanilla's own
 * {@code BlockEntityTypes.SHELF} only validates against vanilla's own shelf blocks.
 */
/*? if >=26.2 {*/
/*public class ModShelfBlockEntity extends ShelfBlockEntity {

    public ModShelfBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.YEW_SHELF;
    }
}*/
/*?}*/
