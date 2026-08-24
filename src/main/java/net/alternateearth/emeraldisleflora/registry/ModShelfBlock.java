package net.alternateearth.emeraldisleflora.registry;

/*? if >=26.2 {*/
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ShelfBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;*/
/*?}*/

/**
 * The shelf block introduced in 26.2 (real-world Minecraft feature - see {@code ShelfBlock}/
 * {@code ShelfBlockEntity} for the redstone-chained hotbar-swap mechanic and 3-item comparator
 * output; not something this mod invented). Vanilla's own per-wood-type shelves are all plain
 * {@code ShelfBlock} instances with no subclass - the only reason this one exists is to add
 * flammability, following every other wood block in this codebase.
 */
/*? if >=26.2 {*/
/*public class ModShelfBlock extends ShelfBlock {

    private final int burnChance;
    private final int spreadChance;

    public ModShelfBlock(BlockBehaviour.Properties settings, int burnChance, int spreadChance) {
        super(settings);
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
}*/
/*?}*/
