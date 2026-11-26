package net.alternateearth.emeraldisleflora.worldgen;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.alternateearth.emeraldisleflora.worldgen.YewFoliageLayout.Params;
/*? if <26.2 {*/
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;*/
/*?}*/
/*? if >=1.21 {*/
/*import com.mojang.serialization.MapCodec;*/
/*?}*/

/**
 * Foliage placer for every yew tree shape. The shape comes from data (canopy size, lobes, droop, flat top),
 * so one class covers all five tree variants. The geometry lives in {@link YewFoliageLayout}, which has no
 * Minecraft dependencies; this class only maps it onto the world.
 *
 * <p>{@code min_clearance} is the lowest a leaf may sit above the tree's base. It is measured from the node,
 * so it is exact for the main node (which sits {@code trunkHeight} above the base) and a little lenient for
 * branch nodes, which sit lower. The clearance decorator is what guarantees the rule on slopes.
 *
 * <p>Leaves that vanilla would decay right after growth (more than six steps from a log) are not placed.
 */
public class YewFoliagePlacer extends FoliagePlacer {

    private static final Codec<Params> SHAPE_CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.intRange(1, 8).fieldOf("canopy_radius").forGetter(Params::radius),
        Codec.intRange(1, 6).fieldOf("canopy_height").forGetter(Params::height),
        Codec.intRange(-6, 4).fieldOf("center_y").orElse(0).forGetter(Params::centerY),
        Codec.intRange(0, 8).fieldOf("lobes").orElse(0).forGetter(Params::lobes),
        Codec.intRange(0, 6).fieldOf("lobe_spread").orElse(0).forGetter(Params::lobeSpread),
        Codec.intRange(1, 6).fieldOf("lobe_radius").orElse(2).forGetter(Params::lobeRadius),
        Codec.intRange(0, 3).fieldOf("droop").orElse(0).forGetter(Params::droop),
        Codec.BOOL.fieldOf("flat_top").orElse(false).forGetter(Params::flatTop)
    ).apply(i, Params::new));

/*? if <26.2 {*/
    private static App<RecordCodecBuilder.Mu<YewFoliagePlacer>, YewFoliagePlacer> build(
        RecordCodecBuilder.Instance<YewFoliagePlacer> instance
    ) {
        return fillFoliagePlacerFields(instance)
            .and(SHAPE_CODEC.fieldOf("shape").forGetter(p -> p.shape))
            .and(Codec.intRange(0, 8).fieldOf("min_clearance").orElse(4).forGetter(p -> p.minClearance))
            .apply(instance, YewFoliagePlacer::new);
    }
/*?} else {*/
/*    private static App<RecordCodecBuilder.Mu<YewFoliagePlacer>, YewFoliagePlacer> build(
        RecordCodecBuilder.Instance<YewFoliagePlacer> instance
    ) {
        return foliagePlacerParts(instance)
            .and(SHAPE_CODEC.fieldOf("shape").forGetter(p -> p.shape))
            .and(Codec.intRange(0, 8).fieldOf("min_clearance").orElse(4).forGetter(p -> p.minClearance))
            .apply(instance, YewFoliagePlacer::new);
    }*/
/*?}*/

/*? if <1.21 {*/
    public static final Codec<YewFoliagePlacer> CODEC = RecordCodecBuilder.create(YewFoliagePlacer::build);
/*?} else {*/
/*    public static final MapCodec<YewFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(YewFoliagePlacer::build);*/
/*?}*/

    private final Params shape;
    private final int minClearance;

    public YewFoliagePlacer(IntProvider radius, IntProvider offset, Params shape, int minClearance) {
        super(radius, offset);
        this.shape = shape;
        this.minClearance = minClearance;
    }

/*? if <26.2 {*/
    public YewFoliagePlacer(Params shape, int minClearance) {
        this(ConstantIntProvider.create(0), ConstantIntProvider.create(0), shape, minClearance);
    }

    public Params shape() {
        return shape;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return YewTreeTypes.FOLIAGE_PLACER;
    }

    @Override
    protected void generate(
        TestableWorld world,
        FoliagePlacer.BlockPlacer placer,
        Random random,
        TreeFeatureConfig config,
        int trunkHeight,
        FoliagePlacer.TreeNode node,
        int foliageHeight,
        int radius,
        int offset
    ) {
        BlockPos center = node.getCenter();
        int minDy = minClearance - trunkHeight;
        for (YewFoliageLayout.Offset o : YewFoliageLayout.generate(
            shape,
            node.isGiantTrunk(),
            minDy,
            random::nextInt,
            (dx, dy, dz) -> world.testBlockState(center.add(dx, dy, dz), YewFoliagePlacer::isYewTrunk)
        )) {
            placeFoliageBlock(world, placer, random, config, center.add(o.dx(), o.dy(), o.dz()));
        }
    }

    private static boolean isYewTrunk(BlockState state) {
        return state.isOf(ModBlocks.YEW_LOG) || state.isOf(ModBlocks.STRIPPED_YEW_LOG) || state.isOf(ModBlocks.YEW_WOOD);
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return shape.height() * 2 + 1;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
/*?} else {*/
/*    public YewFoliagePlacer(Params shape, int minClearance) {
        this(ConstantInt.of(0), ConstantInt.of(0), shape, minClearance);
    }

    public Params shape() {
        return shape;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return YewTreeTypes.FOLIAGE_PLACER;
    }

    @Override
    protected void createFoliage(
        WorldGenLevel level,
        FoliagePlacer.FoliageSetter setter,
        RandomSource random,
        TreeConfiguration config,
        int trunkHeight,
        FoliagePlacer.FoliageAttachment node,
        int foliageHeight,
        int radius,
        int offset
    ) {
        BlockPos center = node.pos();
        int minDy = minClearance - trunkHeight;
        for (YewFoliageLayout.Offset o : YewFoliageLayout.generate(
            shape,
            node.doubleTrunk(),
            minDy,
            random::nextInt,
            (dx, dy, dz) -> level.isStateAtPosition(center.offset(dx, dy, dz), YewFoliagePlacer::isYewTrunk)
        )) {
            tryPlaceLeaf(level, setter, random, config, center.offset(o.dx(), o.dy(), o.dz()));
        }
    }

    private static boolean isYewTrunk(BlockState state) {
        return state.is(ModBlocks.YEW_LOG) || state.is(ModBlocks.STRIPPED_YEW_LOG) || state.is(ModBlocks.YEW_WOOD);
    }

    @Override
    public int foliageHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
        return shape.height() * 2 + 1;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }*/
/*?}*/
}
