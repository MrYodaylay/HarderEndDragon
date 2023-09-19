package dev.mryodaylay.blighted.mixin.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BrewingRecipeRegistry.class)
public class MixinBrewingRecipeRegistry {

    @Redirect(
            method = "registerDefaults",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/item/Items;GHAST_TEAR:Lnet/minecraft/item/Item;"
            )
    )
    private static Item makeUncraftable() {
        return Items.KNOWLEDGE_BOOK;
    }

}
