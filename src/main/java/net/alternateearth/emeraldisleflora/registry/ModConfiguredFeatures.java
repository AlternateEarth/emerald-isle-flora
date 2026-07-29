package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BELLS_OF_IRELAND_KEY = registerKey("patch_bells_of_ireland");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BOG_ROSEMARY_KEY = registerKey("patch_bog_rosemary");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BLENDED_BOG_ROSEMARY_KEY = registerKey("patch_blended_bog_rosemary");

    //--------------------------------------------------------------------------------------------------------------------------------------------------

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
                            DataPool.<BlockState>builder()
                                .add(ModBlocks.BOG_ROSEMARY.getDefaultState(), 4)
                                .add(ModBlocks.GROWN_BOG_ROSEMARY.getDefaultState(), 1)
                        )
                    )
                )
            )
        );
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(EmeraldIsleFlora.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
