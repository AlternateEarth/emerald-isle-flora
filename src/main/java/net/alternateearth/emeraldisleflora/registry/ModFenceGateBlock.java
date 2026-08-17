package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;*/
/*?}*/

/**
 * A fence gate block with flammability wired for every loader - see
 * {@link ModPillarBlock}'s doc comment for the general "why". No axe-stripping.
 * <p>
 * Vanilla's constructor keeps the same {@code (WoodType, Settings)} parameter *types*
 * across every version, but the parameter *order* flipped between 1.20.1
 * ({@code (Settings, WoodType)}) and 1.21.1+ ({@code (WoodType, Settings)}) - confirmed
 * via javap, not assumed; a bare positional swap like this is exactly the kind of thing
 * that compiles fine but silently passes the wrong argument if copied from a nearby
 * version without checking. Handled with a nested {@code <1.21}/{@code >=1.21} split
 * inside the always-real {@code <26.2} branch (safe per AGENTS.md's Stonecutter nesting
 * guidance), since both sides only differ in argument order, not in imports or types.
 */
/*? if <26.2 {*/
public class ModFenceGateBlock extends FenceGateBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModFenceGateBlock(WoodType woodType, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
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
}
/*?} else {*/
/*
public class ModFenceGateBlock extends FenceGateBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModFenceGateBlock(WoodType woodType, BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
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
}
*/
/*?}*/
