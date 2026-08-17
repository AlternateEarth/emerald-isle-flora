package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * A stairs block with flammability wired for every loader - see {@link ModPillarBlock}'s
 * doc comment for the general "why". No axe-stripping (not applicable to stairs).
 * <p>
 * Vanilla's own class is {@code protected}-constructor-only (confirmed via javap - can't
 * be instantiated directly from outside its package), and was renamed
 * {@code StairsBlock} (Yarn, &lt;26.2) -&gt; {@code StairBlock} (Mojmap, 26.2, singular) -
 * confirmed, not assumed, matching this codebase's general Yarn/Mojmap divergence
 * pattern. The constructor shape itself ({@code (BlockState baseState, Settings)}) is
 * otherwise identical across all 4 versions.
 */
/*? if <26.2 {*/
public class ModStairsBlock extends StairsBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModStairsBlock(BlockState baseState, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
        super(baseState, settings);
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
public class ModStairsBlock extends StairBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModStairsBlock(BlockState baseState, BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
        super(baseState, settings);
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
