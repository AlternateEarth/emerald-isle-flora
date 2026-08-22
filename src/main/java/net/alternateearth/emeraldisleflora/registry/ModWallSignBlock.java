package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;*/
/*?}*/

/**
 * The wall sign block, with flammability wired for every loader - see {@link ModPillarBlock}'s
 * doc comment for the general "why". Unlike {@link ModSignBlock}'s base class, {@code WallSignBlock}
 * stays public and concrete through 26.2. Same constructor argument-order flip as the other wood blocks.
 */
/*? if <26.2 {*/
public class ModWallSignBlock extends WallSignBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModWallSignBlock(WoodType woodType, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
        super(
                /*? if <1.21 {*/
                settings, woodType
                /*?} else {*/
                /*woodType, settings*/
                /*?}*/
        );
        this.burnChance = burnChance;
        this.spreadChance = spreadChance;
    }

    public int getFlammability(BlockState state, BlockView level, BlockPos pos, Direction direction) {
        return spreadChance;
    }

    public boolean isFlammable(BlockState state, BlockView level, BlockPos pos, Direction direction) {
        return spreadChance > 0;
    }

    public int getFireSpreadSpeed(BlockState state, BlockView level, BlockPos pos, Direction direction) {
        return burnChance;
    }

    // Fixes a "Block entity ... invalid for ticking" bug - see ModBlockEntities' doc comment.
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SignBlockEntity(ModBlockEntities.YEW_SIGN, pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // See ModSignBlock's matching override for why this isn't a plain checkType call.
        return /*? if <1.21 {*/ checkType /*?} else {*/ /*validateTicker*/ /*?}*/ (type, ModBlockEntities.YEW_SIGN, SignBlockEntity::tick);
    }
}
/*?} else {*/
/*
public class ModWallSignBlock extends WallSignBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModWallSignBlock(WoodType woodType, BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
        super(woodType, settings);
        this.burnChance = burnChance;
        this.spreadChance = spreadChance;
    }

    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return spreadChance;
    }

    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return spreadChance > 0;
    }

    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return burnChance;
    }

    // Fixes a "Block entity ... invalid for ticking" bug - see ModBlockEntities' doc comment.
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SignBlockEntity(ModBlockEntities.YEW_SIGN, pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.YEW_SIGN, SignBlockEntity::tick);
    }
}
*/
/*?}*/
