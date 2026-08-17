package net.alternateearth.emeraldisleflora.registry;

/*? if <1.21.11 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
/*?}*/
/*? if >=1.21.11 && <26.2 {*/
/*import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.TintedParticleLeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;*/
/*?}*/
/*? if >=26.2 {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * A leaves block with flammability wired for every loader - see {@link ModPillarBlock}'s
 * doc comment for the general "why". No axe-stripping (not applicable to leaves).
 * <p>
 * LeavesBlock itself changed shape at 1.21.11 - confirmed via javap against the real
 * jars, not assumed: on 1.20.1/1.21.1 it's a concrete class taking just
 * {@code AbstractBlock.Settings}; at 1.21.11 it became <b>abstract</b> (gained a
 * {@code leafParticleChance} float constructor param and an abstract particle-spawn
 * method), with vanilla's own oak/spruce/etc. leaves now built on a new concrete
 * subclass, {@code TintedParticleLeavesBlock(float, Settings)} - carried through
 * unchanged (same shape, Mojmap names) to 26.2. Written as three full sibling
 * Stonecutter branches (not nested) for the same reason {@code ModRecipeProvider} and
 * this file's own NeoForge {@code onRegister} split are - nesting a further version
 * split inside an already loader/version-disabled comment block has corrupted files in
 * this codebase before (see AGENTS.md's Stonecutter gotchas).
 */
/*? if <1.21.11 {*/
public class ModLeavesBlock extends LeavesBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModLeavesBlock(float leafParticleChance, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
        super(settings);
        this.burnChance = burnChance;
        this.spreadChance = spreadChance;
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
}
/*?}*/
/*? if >=1.21.11 && <26.2 {*/
/*
public class ModLeavesBlock extends TintedParticleLeavesBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModLeavesBlock(float leafParticleChance, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
        super(leafParticleChance, settings);
        this.burnChance = burnChance;
        this.spreadChance = spreadChance;
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
}
*/
/*?}*/
/*? if >=26.2 {*/
/*
public class ModLeavesBlock extends TintedParticleLeavesBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModLeavesBlock(float leafParticleChance, BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
        super(leafParticleChance, settings);
        this.burnChance = burnChance;
        this.spreadChance = spreadChance;
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
