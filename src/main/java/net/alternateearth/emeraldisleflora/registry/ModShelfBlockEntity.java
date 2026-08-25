package net.alternateearth.emeraldisleflora.registry;

/*? if >=1.21.11 && <26.2 {*/
/*import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShelfBlockEntity;
import net.minecraft.util.math.BlockPos;*/
/*?}*/
/*? if >=26.2 {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/*? if >=1.21.11 && <26.2 {*/
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
/*? if >=26.2 {*/
/*public class ModShelfBlockEntity extends ShelfBlockEntity {

    public ModShelfBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.YEW_SHELF;
    }

    @Override
    public boolean isValidBlockState(BlockState state) {
        return this.getType().isValid(state);
    }
}*/
/*?}*/
