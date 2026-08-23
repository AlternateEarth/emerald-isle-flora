package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
/*? if forge {*/
/*import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;*/
/*?}*/
/*? if neoforge {*/
/*import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;*/
/*?}*/
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

import java.util.function.Supplier;

/**
 * A log/wood-shaped pillar block with flammability and axe-stripping wired for every loader.
 * Stripping methods use plain vanilla types with no {@code @Override}, picked up via polymorphism
 * on Forge/NeoForge and inert on Fabric; NeoForge 26.2 only gets flammability, not stripping.
 */
/*? if <26.2 {*/
public class ModPillarBlock extends PillarBlock {

    private final int burnChance;
    private final int spreadChance;
    private final Supplier<Block> strippedBlock;

    public ModPillarBlock(AbstractBlock.Settings settings, int burnChance, int spreadChance, Supplier<Block> strippedBlock) {
        super(settings);
        this.burnChance = burnChance;
        this.spreadChance = spreadChance;
        this.strippedBlock = strippedBlock;
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

    /*? if forge {*/
    /*
    @Override
    public BlockState getToolModifiedState(BlockState state, ItemUsageContext context, ToolAction toolAction, boolean simulate) {
        if (strippedBlock != null && toolAction == ToolActions.AXE_STRIP) {
            return strippedBlock.get().getStateWithProperties(state);
        }
        return null;
    }
    */
    /*?}*/
    /*? if neoforge {*/
    /*
    @Override
    public BlockState getToolModifiedState(BlockState state, ItemUsageContext context, ItemAbility itemAbility, boolean simulate) {
        if (strippedBlock != null && itemAbility == ItemAbilities.AXE_STRIP) {
            return strippedBlock.get().getStateWithProperties(state);
        }
        return null;
    }
    */
    /*?}*/
}
/*?} else {*/
/*
public class ModPillarBlock extends RotatedPillarBlock {

    private final int burnChance;
    private final int spreadChance;
    private final Supplier<Block> strippedBlock;

    public ModPillarBlock(BlockBehaviour.Properties settings, int burnChance, int spreadChance, Supplier<Block> strippedBlock) {
        super(settings);
        this.burnChance = burnChance;
        this.spreadChance = spreadChance;
        this.strippedBlock = strippedBlock;
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
