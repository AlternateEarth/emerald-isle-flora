package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
/*? if <1.21 {*/
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
/*?} else {*/
/*import net.minecraft.block.SaplingGenerator;*/
/*?}*/
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * A sapling that's deliberately inert - it plants, sits there, and doesn't grow, on
 * bonemeal or naturally. Real tree growth needs a worldgen tree feature (a
 * ConfiguredFeature/SaplingGenerator on &lt;26.2, a TreeGrower on 26.2 - renamed again,
 * confirmed via javap, not assumed) and is intentionally left for a separate
 * "World Spawn" branch rather than building tree-shape logic into this
 * blocks-and-items-only branch. randomTick is overridden to a no-op (blocks natural
 * growth) and the bonemeal-eligibility check always returns false (blocks player-forced
 * growth); the constructor still needs *some* real generator/grower instance to satisfy
 * SaplingBlock's protected constructor, so it uses an inert placeholder that's never
 * actually consulted by either overridden path.
 * <p>
 * SaplingBlock's own bonemeal API was overhauled at 26.2 too (confirmed via javap):
 * {@code Fertilizable.isFertilizable/canGrow} became
 * {@code BonemealableBlock.isValidBonemealTarget/isBonemealSuccess}, and
 * {@code SaplingGenerator} was renamed to {@code TreeGrower} in a new package. 1.20.1's
 * {@code SaplingGenerator} is abstract (subclassed directly below); 1.21.1/1.21.11's is a
 * final, pre-built-constants class instead (no subclassing possible - composed via its
 * public {@code OAK} constant), and 26.2's {@code TreeGrower} follows that same
 * final/constants shape under its new name.
 */
/*? if <26.2 {*/
/*? if <1.21 {*/
public class ModSaplingBlock extends SaplingBlock {

    public ModSaplingBlock(AbstractBlock.Settings settings) {
        super(new SaplingGenerator() {
            @Override
            protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
                throw new UnsupportedOperationException("Yew tree growth isn't implemented yet - see ModSaplingBlock");
            }
        }, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Inert for now - see this class's javadoc.
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return false;
    }
}
/*?} else {*/
/*
public class ModSaplingBlock extends SaplingBlock {

    public ModSaplingBlock(AbstractBlock.Settings settings) {
        super(SaplingGenerator.OAK, settings);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Inert for now - see this class's javadoc.
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return false;
    }
}
*/
/*?}*/
/*?} else {*/
/*
public class ModSaplingBlock extends SaplingBlock {

    public ModSaplingBlock(BlockBehaviour.Properties settings) {
        super(TreeGrower.OAK, settings);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Inert for now - see this class's javadoc.
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return false;
    }
}
*/
/*?}*/
