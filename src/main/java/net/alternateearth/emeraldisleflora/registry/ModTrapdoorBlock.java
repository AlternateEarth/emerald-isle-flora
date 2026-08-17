package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;*/
/*?}*/

/**
 * A trapdoor block with flammability wired for every loader - see
 * {@link ModPillarBlock}'s doc comment for the general "why". No axe-stripping.
 * <p>
 * Vanilla's constructor is protected (needs a subclass, like Door/Stairs/Sapling), its
 * parameter order flips between 1.20.1 and 1.21.1+ same as Door/FenceGate, and the class
 * itself is renamed {@code TrapdoorBlock} (Yarn) -&gt; {@code TrapDoorBlock} (Mojmap,
 * capital D) at 26.2 - all confirmed via javap independently, not assumed from the
 * Door/FenceGate pattern.
 */
/*? if <26.2 {*/
public class ModTrapdoorBlock extends TrapdoorBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModTrapdoorBlock(BlockSetType blockSetType, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
        super(
                /*? if <1.21 {*/
                settings, blockSetType
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
public class ModTrapdoorBlock extends TrapDoorBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModTrapdoorBlock(BlockSetType blockSetType, BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
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
