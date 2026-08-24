package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
/*? if <1.21.11 {*/
import net.minecraft.util.collection.DataPool;
/*?} else {*/
/*import net.minecraft.util.collection.WeightedPool;*/
/*?}*/
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
/*?} else {*/
/*import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
*/
/*?}*/

public class ModConfiguredFeatures {
    /*? if <26.2 {*/
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BELLS_OF_IRELAND_KEY = registerKey("patch_bells_of_ireland");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BOG_ROSEMARY_KEY = registerKey("patch_bog_rosemary");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BLENDED_BOG_ROSEMARY_KEY = registerKey("patch_blended_bog_rosemary");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY = registerKey("patch_blended_bulbous_buttercup");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BLUEBELL_KEY = registerKey("patch_bluebell");
    public static final RegistryKey<ConfiguredFeature<?, ?>> YEW_TREE_TALL_NARROW_KEY = registerKey("yew_tree_tall_narrow");
    /*?} else {*/
    /*public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BELLS_OF_IRELAND_KEY = registerKey("patch_bells_of_ireland");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BOG_ROSEMARY_KEY = registerKey("patch_bog_rosemary");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLENDED_BOG_ROSEMARY_KEY = registerKey("patch_blended_bog_rosemary");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY = registerKey("patch_blended_bulbous_buttercup");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLUEBELL_KEY = registerKey("patch_bluebell");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YEW_TREE_TALL_NARROW_KEY = registerKey("yew_tree_tall_narrow");*/
    /*?}*/

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    /*? if <26.2 {*/
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(
            context,
            PATCH_BELLS_OF_IRELAND_KEY,
            Feature.FLOWER,
            new RandomPatchFeatureConfig(
                64,
                6,
                4,
                PlacedFeatures.createEntry(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(ModBlocks.BELLS_OF_IRELAND)
                    )
                ))
        );

        register(
            context,
            PATCH_BOG_ROSEMARY_KEY,
            Feature.FLOWER,
            new RandomPatchFeatureConfig(
                64,
                6,
                4,
                PlacedFeatures.createEntry(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(ModBlocks.BOG_ROSEMARY)
                    )
                ))
        );

        register(
            context,
            PATCH_BLENDED_BOG_ROSEMARY_KEY,
            Feature.FLOWER,
            new RandomPatchFeatureConfig(
                64,
                6,
                4,
                PlacedFeatures.createEntry(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(
                        new WeightedBlockStateProvider(
                            statePool()
                                .add(ModBlocks.BOG_ROSEMARY.getDefaultState(), 4)
                                .add(ModBlocks.GROWN_BOG_ROSEMARY.getDefaultState(), 1)
                        )
                    )
                )
            )
        );

        register(
            context,
            PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY,
            Feature.FLOWER,
            new RandomPatchFeatureConfig(
                64,
                6,
                4,
                PlacedFeatures.createEntry(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(
                        new WeightedBlockStateProvider(
                            statePool()
                                .add(ModBlocks.BULBOUS_BUTTERCUP.getDefaultState(), 1)
                                .add(ModBlocks.GROWN_BULBOUS_BUTTERCUP.getDefaultState(), 2)
                        )
                    )
                ))
        );

        register(
            context,
            PATCH_BLUEBELL_KEY,
            Feature.FLOWER,
            new RandomPatchFeatureConfig(
                64,
                6,
                4,
                PlacedFeatures.createEntry(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(ModBlocks.BLUEBELL)
                    )
                ))
        );

        register(
            context,
            YEW_TREE_TALL_NARROW_KEY,
            Feature.TREE,
            new TreeFeatureConfig.Builder(
                // Base height = 7, random height 1 = 0-3, random height 2 = 0-2, total height = 7-12
                BlockStateProvider.of(ModBlocks.YEW_LOG),
                new StraightTrunkPlacer(8, 3, 2),
                BlockStateProvider.of(ModBlocks.YEW_LEAVES),
                new BlobFoliagePlacer(
                    ConstantIntProvider.create(2),
                    ConstantIntProvider.create(0),
                    5
                ),
                new TwoLayersFeatureSize(1, 0, 1)
            ).build()
        );
    }
    /*?} else {*/
    /*
    // 26.2: Feature.FLOWER/RandomPatchFeatureConfig are gone. Tries/spread moved to
    // placement modifiers, leaving just Feature.SIMPLE_BLOCK + SimpleBlockConfiguration here.
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        BlockStateProvider belowTrunkProvider = TreeConfiguration.defaultPlaceBelowTreeTrunkProvider(biomes);

        register(
            context,
            PATCH_BELLS_OF_IRELAND_KEY,
            Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(
                BlockStateProvider.simple(ModBlocks.BELLS_OF_IRELAND)
            )
        );

        register(
            context,
            PATCH_BOG_ROSEMARY_KEY,
            Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(
                BlockStateProvider.simple(ModBlocks.BOG_ROSEMARY)
            )
        );

        register(
            context,
            PATCH_BLENDED_BOG_ROSEMARY_KEY,
            Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(
                new WeightedStateProvider(
                    statePool()
                        .add(ModBlocks.BOG_ROSEMARY.defaultBlockState(), 4)
                        .add(ModBlocks.GROWN_BOG_ROSEMARY.defaultBlockState(), 1)
                )
            )
        );

        register(
            context,
            PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY,
            Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(
                new WeightedStateProvider(
                    statePool()
                        .add(ModBlocks.BULBOUS_BUTTERCUP.defaultBlockState(), 1)
                        .add(ModBlocks.GROWN_BULBOUS_BUTTERCUP.defaultBlockState(), 2)
                )
            )
        );

        register(
            context,
            PATCH_BLUEBELL_KEY,
            Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(
                BlockStateProvider.simple(ModBlocks.BLUEBELL)
            )
        );

        register(
            context,
            YEW_TREE_TALL_NARROW_KEY,
            Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                // Base height = 7, random height 1 = 0-3, random height 2 = 0-2, total height = 7-12
                BlockStateProvider.simple(ModBlocks.YEW_LOG),
                new StraightTrunkPlacer(8, 3, 2),
                BlockStateProvider.simple(ModBlocks.YEW_LEAVES),
                new BlobFoliagePlacer(
                    ConstantInt.of(2),
                    ConstantInt.of(0),
                    5
                ),
                new TwoLayersFeatureSize(1, 0, 1),
                belowTrunkProvider
            ).build()
        );
    }
    */
    /*?}*/

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    // DataPool -> WeightedPool (1.21.11) -> WeightedList (26.2), same shape each time.
    // Isolated here so call sites above don't need a version conditional each.
    /*? if <1.21.11 {*/
    private static DataPool.Builder<BlockState> statePool() {
        return DataPool.builder();
    }
    /*?}*/
    /*? if >=1.21.11 && <26.2 {*/
    /*private static WeightedPool.Builder<BlockState> statePool() {
        return WeightedPool.builder();
    }*/
    /*?}*/
    /*? if >=26.2 {*/
    /*private static WeightedList.Builder<BlockState> statePool() {
        return WeightedList.builder();
    }*/
    /*?}*/

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    /*? if <26.2 {*/
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(EmeraldIsleFlora.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
    /*?} else {*/
    /*public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }*/
    /*?}*/
}
