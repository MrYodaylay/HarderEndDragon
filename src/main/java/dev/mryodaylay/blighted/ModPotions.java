package dev.mryodaylay.blighted;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {

    public static final Potion RESISTANCE = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 5));
    public static final Potion RESISTANCE_EXTENDED = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 9));
    public static final Potion RESISTANCE_EXTENDED_2 = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 12));
    public static final Potion RESISTANCE_NETHER = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 4), new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20 * 60 * 4, 0));
    public static final Potion RESISTANCE_NETHER_EXTENDED = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 7), new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20 * 60 * 7, 0));
    public static final Potion RESISTANCE_2 = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 3, 1));
    public static final Potion RESISTANCE_2_EXTENDED = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 7, 1));
    public static final Potion RESISTANCE_2_EXTENDED_2 = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 10, 1));
    public static final Potion RESISTANCE_2_NETHER = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 2, 1), new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20 * 60 * 2, 1));
    public static final Potion RESISTANCE_2_NETHER_EXTENDED = new Potion(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 5, 1), new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20 * 60 * 5, 1));


    static {
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance"), RESISTANCE);
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance_extended"), RESISTANCE_EXTENDED);
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance_extended_2"), RESISTANCE_EXTENDED_2);
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance_nether"), RESISTANCE_NETHER);
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance_nether_extended"), RESISTANCE_NETHER_EXTENDED);
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance_2"), RESISTANCE_2);
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance_2_extended"), RESISTANCE_2_EXTENDED);
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance_2_extended_2"), RESISTANCE_2_EXTENDED_2);
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance_2_nether"), RESISTANCE_2_NETHER);
        Registry.register(Registries.POTION, new Identifier("blighted", "resistance_2_nether_extended"), RESISTANCE_2_NETHER_EXTENDED);
    }

    public static void registerPotionRecipes() {
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Items.RAW_IRON, RESISTANCE);

        BrewingRecipeRegistry.registerPotionRecipe(RESISTANCE, Items.GLOWSTONE_DUST, RESISTANCE_EXTENDED);
        BrewingRecipeRegistry.registerPotionRecipe(RESISTANCE_EXTENDED, Items.GLOWSTONE_DUST, RESISTANCE_EXTENDED_2);

        BrewingRecipeRegistry.registerPotionRecipe(RESISTANCE, Items.REDSTONE, RESISTANCE_2);
        BrewingRecipeRegistry.registerPotionRecipe(RESISTANCE_2, Items.GLOWSTONE_DUST, RESISTANCE_2_EXTENDED);
        BrewingRecipeRegistry.registerPotionRecipe(RESISTANCE_2_EXTENDED, Items.GLOWSTONE_DUST, RESISTANCE_2_EXTENDED_2);
    }

}
