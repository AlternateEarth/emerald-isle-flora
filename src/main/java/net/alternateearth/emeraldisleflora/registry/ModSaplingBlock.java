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
/*import net.minecraft.block.SaplingGenerator;
import java.util.Optional;*/
/*?}*/
/*?} else {*/
/*import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/*? if <26.2 {*/
/*? if <1.21 {*/
public class ModSaplingBlock extends SaplingBlock {

    public ModSaplingBlock(AbstractBlock.Settings settings) {
        super(new SaplingGenerator() {
            @Override
            protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
                return ModConfiguredFeatures.YEW_TREE_TALL_NARROW_KEY;
            }
        }, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
}
/*?} else {*/
/*
public class ModSaplingBlock extends SaplingBlock {

    public ModSaplingBlock(AbstractBlock.Settings settings) {
        super(new SaplingGenerator("yew", Optional.empty(), Optional.of(ModConfiguredFeatures.YEW_TREE_TALL_NARROW_KEY), Optional.empty()), settings);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
}
*/
/*?}*/
/*?} else {*/
/*
public class ModSaplingBlock extends SaplingBlock {

    public ModSaplingBlock(BlockBehaviour.Properties settings) {
        super(new TreeGrower("yew", Optional.empty(), Optional.of(ModConfiguredFeatures.YEW_TREE_TALL_NARROW_KEY), Optional.empty()), settings);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }
}
*/
/*?}*/
