package net.alternateearth.emeraldisleflora.worldgen;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
import net.alternateearth.emeraldisleflora.worldgen.YewTrunkLayout.Params;
/*? if <26.2 {*/
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;*/
/*?}*/
/*? if >=1.21 {*/
/*import com.mojang.serialization.MapCodec;*/
/*?}*/

/**
 * Trunk placer for every yew tree shape. The shape comes from data (thickness, twist, branches, block mix),
 * so one class covers all five tree variants. It builds the trunk from {@link YewTrunkLayout}, which holds
 * the geometry and has no Minecraft dependencies; this class only maps that geometry onto the world.
 *
 * <p>The inherited base height and two random heights set the trunk height, as for vanilla trunk placers.
 * A 2x2 trunk follows vanilla's giant-trunk convention: the start position is the north-west corner and the
 * foliage node is flagged as a double trunk.
 */
public class YewTrunkPlacer extends TrunkPlacer {

    private static final Codec<Params> SHAPE_CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.intRange(1, 2).fieldOf("thickness").forGetter(Params::thickness),
        Codec.intRange(0, 4).fieldOf("twist").orElse(0).forGetter(Params::twist),
        Codec.intRange(0, 8).fieldOf("branches").orElse(0).forGetter(Params::branches),
        Codec.intRange(1, 16).fieldOf("branch_min").orElse(2).forGetter(Params::branchMin),
        Codec.intRange(1, 16).fieldOf("branch_max").orElse(3).forGetter(Params::branchMax),
        Codec.intRange(0, 100).fieldOf("stripped_percent").orElse(0).forGetter(Params::strippedPercent),
        Codec.intRange(0, 100).fieldOf("wood_percent").orElse(0).forGetter(Params::woodPercent)
    ).apply(i, Params::new));

/*? if <26.2 {*/
    private static App<RecordCodecBuilder.Mu<YewTrunkPlacer>, YewTrunkPlacer> build(
        RecordCodecBuilder.Instance<YewTrunkPlacer> instance
    ) {
        return fillTrunkPlacerFields(instance)
            .and(SHAPE_CODEC.fieldOf("shape").forGetter(p -> p.shape))
            .apply(instance, YewTrunkPlacer::new);
    }
/*?} else {*/
/*    private static App<RecordCodecBuilder.Mu<YewTrunkPlacer>, YewTrunkPlacer> build(
        RecordCodecBuilder.Instance<YewTrunkPlacer> instance
    ) {
        return trunkPlacerParts(instance)
            .and(SHAPE_CODEC.fieldOf("shape").forGetter(p -> p.shape))
            .apply(instance, YewTrunkPlacer::new);
    }*/
/*?}*/

/*? if <1.21 {*/
    public static final Codec<YewTrunkPlacer> CODEC = RecordCodecBuilder.create(YewTrunkPlacer::build);
/*?} else {*/
/*    public static final MapCodec<YewTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(YewTrunkPlacer::build);*/
/*?}*/

    private final Params shape;

    public YewTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, Params shape) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.shape = shape;
    }

    public Params shape() {
        return shape;
    }

/*? if <26.2 {*/
    @Override
    protected TrunkPlacerType<?> getType() {
        return YewTreeTypes.TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(
        TestableWorld world,
        BiConsumer<BlockPos, BlockState> replacer,
        Random random,
        int height,
        BlockPos startPos,
        TreeFeatureConfig config
    ) {
        int size = shape.thickness();
        for (int dx = 0; dx < size; dx++) {
            for (int dz = 0; dz < size; dz++) {
                setToDirt(world, replacer, random, startPos.add(dx, -1, dz), config);
            }
        }

        YewTrunkLayout.Result layout = YewTrunkLayout.generate(shape, height, random::nextInt);
        for (YewTrunkLayout.Block b : layout.blocks()) {
            BlockState state = stateFor(b);
            getAndSetState(world, replacer, random, startPos.add(b.x(), b.y(), b.z()), config, ignored -> state);
        }

        List<FoliagePlacer.TreeNode> nodes = new ArrayList<>();
        for (YewTrunkLayout.Node n : layout.nodes()) {
            nodes.add(new FoliagePlacer.TreeNode(startPos.add(n.x(), n.y(), n.z()), n.radiusOffset(), n.doubleTrunk()));
        }
        return nodes;
    }

    private static BlockState stateFor(YewTrunkLayout.Block b) {
        Block block = switch (b.kind()) {
            case LOG -> ModBlocks.YEW_LOG;
            case STRIPPED_LOG -> ModBlocks.STRIPPED_YEW_LOG;
            case WOOD -> ModBlocks.YEW_WOOD;
        };
        Direction.Axis axis = switch (b.axis()) {
            case X -> Direction.Axis.X;
            case Y -> Direction.Axis.Y;
            case Z -> Direction.Axis.Z;
        };
        return block.getDefaultState().with(PillarBlock.AXIS, axis);
    }
/*?} else {*/
/*    @Override
    protected TrunkPlacerType<?> type() {
        return YewTreeTypes.TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
        WorldGenLevel level,
        BiConsumer<BlockPos, BlockState> replacer,
        RandomSource random,
        int height,
        BlockPos origin,
        TreeConfiguration config
    ) {
        int size = shape.thickness();
        for (int dx = 0; dx < size; dx++) {
            for (int dz = 0; dz < size; dz++) {
                placeBelowTrunkBlock(level, replacer, random, origin.offset(dx, -1, dz), config);
            }
        }

        YewTrunkLayout.Result layout = YewTrunkLayout.generate(shape, height, random::nextInt);
        for (YewTrunkLayout.Block b : layout.blocks()) {
            BlockState state = stateFor(b);
            placeLog(level, replacer, random, origin.offset(b.x(), b.y(), b.z()), config, ignored -> state);
        }

        List<FoliagePlacer.FoliageAttachment> nodes = new ArrayList<>();
        for (YewTrunkLayout.Node n : layout.nodes()) {
            nodes.add(new FoliagePlacer.FoliageAttachment(origin.offset(n.x(), n.y(), n.z()), n.radiusOffset(), n.doubleTrunk()));
        }
        return nodes;
    }

    private static BlockState stateFor(YewTrunkLayout.Block b) {
        Block block = switch (b.kind()) {
            case LOG -> ModBlocks.YEW_LOG;
            case STRIPPED_LOG -> ModBlocks.STRIPPED_YEW_LOG;
            case WOOD -> ModBlocks.YEW_WOOD;
        };
        Direction.Axis axis = switch (b.axis()) {
            case X -> Direction.Axis.X;
            case Y -> Direction.Axis.Y;
            case Z -> Direction.Axis.Z;
        };
        return block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis);
    }*/
/*?}*/
}
