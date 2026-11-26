package net.alternateearth.emeraldisleflora.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.alternateearth.emeraldisleflora.registry.ModBlocks;
/*? if <26.2 {*/
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;*/
/*?}*/
/*? if >=1.21 {*/
/*import com.mojang.serialization.MapCodec;*/
/*?}*/

/**
 * Removes any yew leaf that sits closer than {@code clearance} blocks above the ground at its own column, so
 * a player can always walk under a yew canopy, then removes any leaf that this cut off from the trunk. The foliage placer already keeps leaves clear of the ground
 * the tree stands on; this pass is what makes the rule hold on slopes and for branch canopies. The rule
 * itself is in {@link YewClearance}.
 */
public class YewClearanceDecorator extends TreeDecorator {

/*? if <1.21 {*/
    public static final Codec<YewClearanceDecorator> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.intRange(0, 8).fieldOf("clearance").orElse(4).forGetter(d -> d.clearance)
    ).apply(i, YewClearanceDecorator::new));
/*?} else {*/
/*    public static final MapCodec<YewClearanceDecorator> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
        Codec.intRange(0, 8).fieldOf("clearance").orElse(4).forGetter(d -> d.clearance)
    ).apply(i, YewClearanceDecorator::new));*/
/*?}*/

    private final int clearance;

    public YewClearanceDecorator(int clearance) {
        this.clearance = clearance;
    }

    public int clearance() {
        return clearance;
    }

/*? if <26.2 {*/
    @Override
    protected TreeDecoratorType<?> getType() {
        return YewTreeTypes.CLEARANCE_DECORATOR;
    }

    @Override
    public void generate(TreeDecorator.Generator generator) {
        TestableWorld world = generator.getWorld();
        BlockState air = Blocks.AIR.getDefaultState();
        Map<YewFoliageLayout.Offset, BlockPos> remaining = new HashMap<>();
        boolean removedAny = false;
        for (BlockPos leaf : new ArrayList<>(generator.getLeavesPositions())) {
            boolean tooLow = YewClearance.isTooLow(leaf.getX(), leaf.getY(), leaf.getZ(), clearance,
                (x, y, z) -> world.testBlockState(new BlockPos(x, y, z), YewClearanceDecorator::letsScanContinue));
            if (tooLow) {
                generator.replace(leaf, air);
                removedAny = true;
            } else {
                remaining.put(new YewFoliageLayout.Offset(leaf.getX(), leaf.getY(), leaf.getZ()), leaf);
            }
        }
        if (!removedAny) return;
        // Removing leaves can cut off the leaves that were connected to the trunk through them. Those would
        // decay a moment later, so remove them now.
        List<YewFoliageLayout.Offset> logs = new ArrayList<>();
        for (BlockPos log : generator.getLogPositions()) logs.add(new YewFoliageLayout.Offset(log.getX(), log.getY(), log.getZ()));
        for (YewFoliageLayout.Offset lost : YewLeafDistance.unreachable(logs, remaining.keySet())) {
            generator.replace(remaining.get(lost), air);
        }
    }

    private static boolean letsScanContinue(BlockState state) {
        return state.isAir()
            || state.isReplaceable()
            || state.isIn(BlockTags.LEAVES)
            || state.isIn(BlockTags.LOGS)
            || state.isOf(ModBlocks.YEW_LEAVES)
            || state.isOf(ModBlocks.YEW_LOG)
            || state.isOf(ModBlocks.STRIPPED_YEW_LOG)
            || state.isOf(ModBlocks.YEW_WOOD);
    }
/*?} else {*/
/*    @Override
    protected TreeDecoratorType<?> type() {
        return YewTreeTypes.CLEARANCE_DECORATOR;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        WorldGenLevel level = context.level();
        BlockState air = Blocks.AIR.defaultBlockState();
        Map<YewFoliageLayout.Offset, BlockPos> remaining = new HashMap<>();
        boolean removedAny = false;
        for (BlockPos leaf : new ArrayList<>(context.leaves())) {
            boolean tooLow = YewClearance.isTooLow(leaf.getX(), leaf.getY(), leaf.getZ(), clearance,
                (x, y, z) -> level.isStateAtPosition(new BlockPos(x, y, z), YewClearanceDecorator::letsScanContinue));
            if (tooLow) {
                context.setBlock(leaf, air);
                removedAny = true;
            } else {
                remaining.put(new YewFoliageLayout.Offset(leaf.getX(), leaf.getY(), leaf.getZ()), leaf);
            }
        }
        if (!removedAny) return;
        // Removing leaves can cut off the leaves that were connected to the trunk through them. Those would
        // decay a moment later, so remove them now.
        List<YewFoliageLayout.Offset> logs = new ArrayList<>();
        for (BlockPos log : context.logs()) logs.add(new YewFoliageLayout.Offset(log.getX(), log.getY(), log.getZ()));
        for (YewFoliageLayout.Offset lost : YewLeafDistance.unreachable(logs, remaining.keySet())) {
            context.setBlock(remaining.get(lost), air);
        }
    }

    private static boolean letsScanContinue(BlockState state) {
        return state.isAir()
            || state.canBeReplaced()
            || state.is(BlockTags.LEAVES)
            || state.is(BlockTags.LOGS)
            || state.is(ModBlocks.YEW_LEAVES)
            || state.is(ModBlocks.YEW_LOG)
            || state.is(ModBlocks.STRIPPED_YEW_LOG)
            || state.is(ModBlocks.YEW_WOOD);
    }*/
/*?}*/
}
