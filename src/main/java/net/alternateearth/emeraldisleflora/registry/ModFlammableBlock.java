package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * A plain block with flammability wired for every loader - see {@link ModPillarBlock}'s
 * doc comment for the full "why" (Forge/NeoForge only expose this as a per-block-instance
 * override, no registry API, unlike Fabric's FlammableBlockRegistry). No axe-stripping
 * here, unlike ModPillarBlock - this is for flammable blocks that aren't a log/wood
 * pillar (planks, etc). Since it needs no loader-specific type (unlike
 * getToolModifiedState's ToolAction/ItemAbility split), it needs no per-loader nesting
 * and works identically - including on 26.2-neoforge - across the whole matrix.
 */
/*? if <26.2 {*/
public class ModFlammableBlock extends Block {

    private final int burnChance;
    private final int spreadChance;

    public ModFlammableBlock(AbstractBlock.Settings settings, int burnChance, int spreadChance) {
        super(settings);
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
public class ModFlammableBlock extends Block {

    private final int burnChance;
    private final int spreadChance;

    public ModFlammableBlock(BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
        super(settings);
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
