package net.alternateearth.emeraldisleflora.registry;

/*? if <26.2 {*/
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.util.math.BlockPos;
/*?} else {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * A {@link HangingSignBlockEntity} subclass that swaps in this mod's own
 * {@link BlockEntityType} via a {@code getType()} override, needed for the same reason
 * {@link ModBlockEntities} registers its own {@code BlockEntityType} for the regular sign
 * - see that class's doc comment for the general "why".
 * <p>
 * This directly extends vanilla's own {@code HangingSignBlockEntity} - unlike the
 * regular sign (which reuses plain {@code SignBlockEntity} directly, see
 * {@link ModBlockEntities}), extending vanilla's {@code HangingSignBlockEntity} isn't
 * just a nicety here, it's required: the client decides which edit screen to open
 * ({@code HangingSignEditScreen} vs. the plain {@code SignEditScreen}) via a hardcoded
 * {@code blockEntity instanceof HangingSignBlockEntity} check in
 * {@code ClientPlayerEntity.openEditSignScreen}/{@code LocalPlayer.openTextEdit}
 * (confirmed via {@code javap -c} against every real target jar, Yarn 1.20.1/1.21.1/
 * 1.21.11 and Mojmap 26.2 alike - identical bytecode shape on all four) - not by block
 * type, not by a marker interface. A first attempt at this class extended
 * {@code SignBlockEntity} directly (matching {@link ModBlockEntities}'s own doc-comment
 * reasoning for the regular sign) and failed that check at runtime, silently opening the
 * plain sign editor on a hanging sign - a real, reproduced bug on a real install, not a
 * hypothetical.
 * <p>
 * {@code HangingSignBlockEntity}'s only constructor is {@code (BlockPos, BlockState)},
 * with no way to pass a custom type in directly - it hardcodes vanilla's own static
 * {@code BlockEntityType.HANGING_SIGN}/{@code BlockEntityTypes.HANGING_SIGN}. Overriding
 * {@code getType()} (public, not final, on every target - confirmed via {@code javap})
 * after the fact fixes that: every real consumer (renderer dispatch, the
 * {@code BlockEntityType.supports(state)} validity check, this mod's own
 * {@code checkType}/{@code validateTicker} calls in {@link ModHangingSignBlock}/
 * {@link ModWallHangingSignBlock}) reads the type via this method, not the raw
 * constructor argument - the same pattern a real working reference implementation
 * ({@code bonsaistudi0s/Ghosts}) already uses for its own hanging sign. No need to
 * reimplement {@code getTextLineHeight()}/{@code getMaxTextWidth()} here either -
 * vanilla's {@code HangingSignBlockEntity} already provides those; this class only needed
 * to override them itself back when it wasn't extending vanilla's class at all.
 */
public class ModHangingSignBlockEntity extends HangingSignBlockEntity {

    public ModHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.YEW_HANGING_SIGN;
    }
}
