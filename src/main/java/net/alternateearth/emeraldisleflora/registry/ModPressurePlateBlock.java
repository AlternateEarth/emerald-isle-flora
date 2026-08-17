package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;*/
/*?}*/

/**
 * A pressure plate block with flammability wired for every loader - see
 * {@link ModPillarBlock}'s doc comment for the general "why". No axe-stripping.
 * <p>
 * Vanilla's constructor is protected (needs a subclass), and lost its
 * {@code ActivationRule} parameter entirely at 1.21.1+ (1.20.1:
 * {@code (ActivationRule, Settings, BlockSetType)}; 1.21.1+: {@code (BlockSetType,
 * Settings)}, no activation-rule concept in the constructor at all anymore) - confirmed
 * via javap, a real arity change (not just a reorder/rename like Door/Trapdoor/
 * FenceGate), so this needed its own independent check rather than assuming it matched
 * that pattern. Wood pressure plates always use {@code ActivationRule.EVERYTHING} in
 * vanilla (only stone-type plates use {@code MOBS}), passed explicitly on 1.20.1.
 */
/*? if <26.2 {*/
public class ModPressurePlateBlock extends PressurePlateBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModPressurePlateBlock(BlockSetType blockSetType, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
        super(
                /*? if <1.21 {*/
                ActivationRule.EVERYTHING, settings, blockSetType
                /*?} else {*/
                /*blockSetType, settings*/
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
public class ModPressurePlateBlock extends PressurePlateBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModPressurePlateBlock(BlockSetType blockSetType, BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
        super(blockSetType, settings);
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
