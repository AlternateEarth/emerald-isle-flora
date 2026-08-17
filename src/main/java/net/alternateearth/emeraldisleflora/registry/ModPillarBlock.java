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
 * A log/wood-shaped pillar block with flammability and axe-stripping wired for every
 * loader, not just Fabric. Fabric has registry-based APIs for both
 * (FlammableBlockRegistry/StrippableBlockRegistry, see ModBlocks.register()), but
 * Forge/NeoForge only expose these as per-block-instance overrides
 * (IForgeBlock/IBlockExtension's getFlammability/isFlammable/getFireSpreadSpeed/
 * getToolModifiedState - confirmed via javap/decompiled source against the real jars,
 * vanilla's own FireBlock/AxeItem maps backing those defaults are private/protected
 * with no public registration point), which is why a plain PillarBlock/RotatedPillarBlock
 * isn't enough on those loaders.
 * <p>
 * The flammability methods below deliberately use only plain vanilla types (no
 * IForgeBlock/IBlockExtension import, no {@code @Override}) so the exact same method
 * bodies compile - and are silently inert - on Fabric too, without needing a further
 * per-loader Stonecutter split: Forge/NeoForge's Block class implements the extension
 * interface (confirmed via javap - "Block ... implements ... IForgeBlock" / "...
 * IBlockExtension"), so a same-signature method on this subclass is picked up by normal
 * Java polymorphism regardless of whether {@code @Override} is present; on Fabric, Block
 * doesn't implement that interface, so the method is simply never called.
 * <p>
 * getToolModifiedState needs the loader's own tool-action type (ToolAction on Forge,
 * ItemAbility on NeoForge) in its signature, so it can't be written loader-agnostically
 * the same way - it's nested inside the (always real, uncommented) {@code <26.2} branch
 * below, safe per AGENTS.md's Stonecutter-nesting guidance. NeoForge on 26.2 doesn't get
 * this override (would require nesting a loader marker inside the already comment-wrapped
 * Mojmap branch, the exact pattern that has corrupted this file before elsewhere in this
 * codebase) - it still gets flammability parity, just not stripping parity; stripping
 * stays Fabric-registry-only there.
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
