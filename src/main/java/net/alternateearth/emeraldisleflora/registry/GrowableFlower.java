package net.alternateearth.emeraldisleflora.registry;

/*? if <1.21 {*/
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
/*?} else if <26.2 {*/
/*import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
*/
/*?} else {*/
/*import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
*/
/*?}*/

public class GrowableFlower extends FlowerBlock {
	/*? if <1.21 {*/
    public GrowableFlower(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }
	/*?} else if <26.2 {*/
	/*
    public GrowableFlower(RegistryEntry<StatusEffect> suspiciousStewEffect, float effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }
	*/
	/*?} else {*/
	/*
    public GrowableFlower(Holder<MobEffect> suspiciousStewEffect, float effectDuration, Properties settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }
	*/
	/*?}*/

    //---------------------------Additional settings---------------------------
    /*? if <26.2 {*/
    @Override
    public boolean canMobSpawnInside(BlockState state) {
        return true;
    }
    /*?} else {*/
    /*
    // 26.2: Yarn's canMobSpawnInside(BlockState) was renamed and moved to Block itself as
    // isPossibleToRespawnInThis(BlockState).
    @Override
    public boolean isPossibleToRespawnInThis(BlockState state) {
        return true;
    }
    */
    /*?}*/
}
