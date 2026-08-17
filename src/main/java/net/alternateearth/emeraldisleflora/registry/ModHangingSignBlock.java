package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HangingSignBlock;
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
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;*/
/*?}*/

/**
 * The standing/"ceiling" hanging sign block, with flammability wired for every loader -
 * see {@link ModPillarBlock}'s doc comment for the general "why". No axe-stripping.
 * <p>
 * Vanilla calls this class plain {@code HangingSignBlock} on Yarn (every target through
 * 1.21.11) but {@code CeilingHangingSignBlock} on Mojmap (26.2) - confirmed via
 * {@code javap} against the real jar for each, not assumed from naming parity with the
 * regular {@link ModSignBlock}'s base class. The constructor parameter order flip
 * (Settings-first at 1.20.1, WoodType-first from 1.21.1 on) mirrors {@link ModSignBlock}'s
 * own {@code <1.21}/{@code >=1.21} split exactly - also confirmed via {@code javap}, not
 * assumed to land on the same boundary just because {@code SignBlock} did.
 * <p>
 * Returns a {@link ModHangingSignBlockEntity} from {@code createBlockEntity} rather than
 * vanilla's own {@code HangingSignBlockEntity} - see that class's doc comment for why
 * vanilla's version can't be reused here.
 */
/*? if <26.2 {*/
public class ModHangingSignBlock extends HangingSignBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModHangingSignBlock(WoodType woodType, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
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

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModHangingSignBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // See ModSignBlock's matching override for why this isn't a plain checkType call.
        return /*? if <1.21 {*/ checkType /*?} else {*/ /*validateTicker*/ /*?}*/ (type, ModBlockEntities.YEW_HANGING_SIGN, SignBlockEntity::tick);
    }
}
/*?} else {*/
/*
public class ModHangingSignBlock extends CeilingHangingSignBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModHangingSignBlock(WoodType woodType, BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
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

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ModHangingSignBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.YEW_HANGING_SIGN, SignBlockEntity::tick);
    }
}
*/
/*?}*/
