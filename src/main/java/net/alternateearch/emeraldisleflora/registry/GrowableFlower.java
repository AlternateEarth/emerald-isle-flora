package net.alternateearch.emeraldisleflora.registry;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;

public class GrowableFlower extends FlowerBlock {
    public GrowableFlower(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }

    //---------------------------Additional settings---------------------------
    @Override
    public boolean canMobSpawnInside(BlockState state) {
        return true;

    }
}