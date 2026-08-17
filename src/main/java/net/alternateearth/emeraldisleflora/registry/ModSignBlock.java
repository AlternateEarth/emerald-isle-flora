package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
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
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;*/
/*?}*/

/**
 * The standing sign block, with flammability wired for every loader - see
 * {@link ModPillarBlock}'s doc comment for the general "why". No axe-stripping.
 * <p>
 * Vanilla's class is public and concrete through 1.21.11, with the same constructor
 * parameter-order flip 1.20.1-&gt;1.21.1+ as Door/Trapdoor/FenceGate/PressurePlate/Button.
 * At 26.2 it became <b>abstract</b> (renamed the concrete standing-sign class to
 * {@code StandingSignBlock}, with {@code SignBlock} left as the shared base for both
 * standing and wall signs) - confirmed via javap (two abstract methods, {@code codec()}
 * and {@code getYRotationDegrees}, both already implemented by
 * {@code StandingSignBlock}), the same kind of split LeavesBlock went through, but at a
 * different version boundary (26.2 here, vs. 1.21.11 for Leaves) - checked independently
 * rather than assumed to land on the same version.
 */
/*? if <26.2 {*/
public class ModSignBlock extends SignBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModSignBlock(WoodType woodType, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
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

    // Fixes a real "Block entity ... invalid for ticking" bug (confirmed from a launch
    // log) - see ModBlockEntities' doc comment for the full "why".
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SignBlockEntity(ModBlockEntities.YEW_SIGN, pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // BlockWithEntity's helper was renamed checkType -> validateTicker starting at
        // 1.21 (confirmed via javap - still checkType at 1.20.1, already validateTicker
        // at 1.21.1 and 1.21.11) - a real, version-boundary rename this project's own
        // build caught, not assumed to land on the same boundary as other renames here.
        return /*? if <1.21 {*/ checkType /*?} else {*/ /*validateTicker*/ /*?}*/ (type, ModBlockEntities.YEW_SIGN, SignBlockEntity::tick);
    }
}
/*?} else {*/
/*
public class ModSignBlock extends StandingSignBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModSignBlock(WoodType woodType, BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
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

    // Fixes a real "Block entity ... invalid for ticking" bug (confirmed from a launch
    // log) - see ModBlockEntities' doc comment for the full "why".
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
