package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
/*? if <1.21 {*/
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * Yew sapling. A single sapling grows a 1x1-trunk tree ({@code yew_tree}: tall &amp; narrow, twisted or slim
 * wide &amp; low). Four saplings in a 2x2 square grow a 2x2-trunk tree ({@code yew_tree_large}: wide &amp; low or
 * dense), and when that tree does not fit, the sapling that triggered growth falls back to a single-sapling tree,
 * exactly as vanilla's larger trees do. Growth by random tick and by bone meal is vanilla's.
 *
 * <p>The grower class differs by version: 1.20.1 splits large trees into {@code LargeTreeSaplingGenerator};
 * from 1.21.1 {@code SaplingGenerator} takes both keys; 26.2 renames it {@code TreeGrower}.
 */
/*? if <26.2 {*/
/*? if <1.21 {*/
public class ModSaplingBlock extends SaplingBlock {

    public ModSaplingBlock(AbstractBlock.Settings settings) {
        super(new LargeTreeSaplingGenerator() {
            // Used only for the fallback below: vanilla's 1.20.1 large-tree generator stops after a 2x2 group
            // fails to grow, whereas later versions fall back to the single tree.
            private final SaplingGenerator single = new SaplingGenerator() {
                @Override
                protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
                    return ModConfiguredFeatures.YEW_TREE_KEY;
                }
            };

            @Override
            public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
                // A failed 2x2 attempt puts its saplings back, so the single tree can still grow from this one.
                if (super.generate(world, chunkGenerator, pos, state, random)) return true;
                return hasSquare(world, pos, state) && single.generate(world, chunkGenerator, pos, state, random);
            }

            @Override
            protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
                return ModConfiguredFeatures.YEW_TREE_KEY;
            }

            @Override
            protected RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
                return ModConfiguredFeatures.YEW_TREE_LARGE_KEY;
            }
        }, settings);
    }

    // Whether the sapling at pos is part of a 2x2 square of yew saplings.
    private static boolean hasSquare(BlockView world, BlockPos pos, BlockState state) {
        for (int i = 0; i >= -1; --i) {
            for (int j = 0; j >= -1; --j) {
                if (LargeTreeSaplingGenerator.canGenerateLargeTree(state, world, pos, i, j)) return true;
            }
        }
        return false;
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

    // Used only for the fallback in generate: vanilla stops after a 2x2 group fails to grow its tree.
    private static final SaplingGenerator SINGLE_ONLY = new SaplingGenerator("yew_single", Optional.empty(), Optional.of(ModConfiguredFeatures.YEW_TREE_KEY), Optional.empty());

    public ModSaplingBlock(AbstractBlock.Settings settings) {
        super(new SaplingGenerator("yew", Optional.of(ModConfiguredFeatures.YEW_TREE_LARGE_KEY), Optional.of(ModConfiguredFeatures.YEW_TREE_KEY), Optional.empty()), settings);
    }

    @Override
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.get(STAGE) == 0) {
            super.generate(world, pos, state, random);
            return;
        }
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        // A failed 2x2 attempt puts its saplings back, so the single tree can still grow from this one.
        if (!generator.generate(world, chunkGenerator, pos, state, random) && hasSquare(world, pos, state)) {
            SINGLE_ONLY.generate(world, chunkGenerator, pos, state, random);
        }
    }

    // Whether the sapling at pos is part of a 2x2 square of yew saplings.
    private static boolean hasSquare(BlockView world, BlockPos pos, BlockState state) {
        for (int i = 0; i >= -1; --i) {
            for (int j = 0; j >= -1; --j) {
                boolean all = true;
                for (int dx = 0; dx <= 1 && all; dx++) {
                    for (int dz = 0; dz <= 1 && all; dz++) {
                        all = world.getBlockState(pos.add(i + dx, 0, j + dz)).isOf(state.getBlock());
                    }
                }
                if (all) return true;
            }
        }
        return false;
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

    // Used only for the fallback in advanceTree: vanilla stops after a 2x2 group fails to grow its tree.
    private static final TreeGrower SINGLE_ONLY = new TreeGrower("yew_single", Optional.empty(), Optional.of(ModConfiguredFeatures.YEW_TREE_KEY), Optional.empty());

    public ModSaplingBlock(BlockBehaviour.Properties settings) {
        super(new TreeGrower("yew", Optional.of(ModConfiguredFeatures.YEW_TREE_LARGE_KEY), Optional.of(ModConfiguredFeatures.YEW_TREE_KEY), Optional.empty()), settings);
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(STAGE) == 0) {
            super.advanceTree(level, pos, state, random);
            return;
        }
        ChunkGenerator chunkGenerator = level.getChunkSource().getGenerator();
        // A failed 2x2 attempt puts its saplings back, so the single tree can still grow from this one.
        if (!treeGrower.growTree(level, chunkGenerator, pos, state, random) && hasSquare(level, pos, state)) {
            SINGLE_ONLY.growTree(level, chunkGenerator, pos, state, random);
        }
    }

    // Whether the sapling at pos is part of a 2x2 square of yew saplings.
    private static boolean hasSquare(BlockGetter level, BlockPos pos, BlockState state) {
        for (int i = 0; i >= -1; --i) {
            for (int j = 0; j >= -1; --j) {
                boolean all = true;
                for (int dx = 0; dx <= 1 && all; dx++) {
                    for (int dz = 0; dz <= 1 && all; dz++) {
                        all = level.getBlockState(pos.offset(i + dx, 0, j + dz)).is(state.getBlock());
                    }
                }
                if (all) return true;
            }
        }
        return false;
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
