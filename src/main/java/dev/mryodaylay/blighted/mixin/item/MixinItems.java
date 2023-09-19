package dev.mryodaylay.blighted.mixin.item;

import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public class MixinItems {

    @ModifyConstant(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=potion",
                            ordinal = 0
                    ),
                    to = @At(
                            value = "CONSTANT",
                            args = "stringValue=glass_bottle",
                            ordinal = 0
                    )
            ),
            constant = @Constant(intValue = 1)
    )
    private static int modifyPotionStackSize(int original) {
        return 16;
    }

    @ModifyConstant(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=splash_potion",
                            ordinal = 0
                    ),
                    to = @At(
                            value = "CONSTANT",
                            args = "stringValue=spectral_arrow",
                            ordinal = 0
                    )
            ),
            constant = @Constant(intValue = 1)
    )
    private static int modifySplashPotionStackSize(int original) {
        return 16;
    }

}
