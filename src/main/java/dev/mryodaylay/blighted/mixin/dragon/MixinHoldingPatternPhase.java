package dev.mryodaylay.blighted.mixin.dragon;

import net.minecraft.entity.boss.dragon.phase.HoldingPatternPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HoldingPatternPhase.class)
public class MixinHoldingPatternPhase {


    @ModifyConstant(
            method = "tickInRange()V",
            constant = @Constant(doubleValue = 2.0),
            require = 1
    )
    public double modifyFireballChanceA(double constant) {
        return 1.0;
    }

    @ModifyConstant(
            method = "tickInRange()V",
            constant = @Constant(intValue = 2),
            require = 1
    )
    public int modifyFireballChanceA(int constant) {
        return 1;
    }
}
