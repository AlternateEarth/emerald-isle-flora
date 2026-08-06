package net.alternateearth.emeraldisleflora.registry;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
/*? if <1.21 {*/
import net.minecraft.entity.effect.StatusEffect;
/*?} else {*/
/*import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
*/
/*?}*/

public class GrowableFlower extends FlowerBlock {
	/*? if <1.21 {*/
    public GrowableFlower(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }
	/*?} else {*/
	/*
    public GrowableFlower(RegistryEntry<StatusEffect> suspiciousStewEffect, float effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }
	*/
	/*?}*/

    //---------------------------Additional settings---------------------------
    @Override
    public boolean canMobSpawnInside(BlockState state) {
        return true;

    }
}