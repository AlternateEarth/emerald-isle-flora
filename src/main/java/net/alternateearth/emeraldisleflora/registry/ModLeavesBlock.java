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
import net.minecraft.block.UntintedParticleLeavesBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.TintedParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;*/
/*?}*/
/*? if >=26.2 {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * A leaves block with flammability wired for every loader. LeavesBlock became abstract at 1.21.11,
 * so vanilla leaves now build on a concrete subclass instead. We use {@code UntintedParticleLeavesBlock}
 * with a fixed particle color (like azalea) rather than {@code TintedParticleLeavesBlock}, which would
 * fall back to grey since we don't register a biome color provider for yew leaves.
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
public class ModLeavesBlock extends UntintedParticleLeavesBlock {

    // Fixed dark green so the falling-leaf particle doesn't fall back to grey when no biome tint is registered.
    private static final int LEAF_PARTICLE_COLOR = 0x3A583C;

    private final int burnChance;
    private final int spreadChance;

    public ModLeavesBlock(float leafParticleChance, AbstractBlock.Settings settings, int burnChance, int spreadChance) {
        super(leafParticleChance, TintedParticleEffect.create(ParticleTypes.TINTED_LEAVES, LEAF_PARTICLE_COLOR), settings);
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
public class ModLeavesBlock extends UntintedParticleLeavesBlock {

    // Fixed dark green so the falling-leaf particle doesn't fall back to grey when no biome tint is registered.
    private static final int LEAF_PARTICLE_COLOR = 0x3A583C;

    private final int burnChance;
    private final int spreadChance;

    public ModLeavesBlock(float leafParticleChance, BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
        super(leafParticleChance, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, LEAF_PARTICLE_COLOR), settings);
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
