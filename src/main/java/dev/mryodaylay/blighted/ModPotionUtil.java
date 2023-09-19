package dev.mryodaylay.blighted;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModPotionUtil {

    public static ItemStack addCustomName(ItemStack itemStack, String name) {

        NbtCompound display = new NbtCompound();
        display.putString("Name", "{\"text\":\"%s\",\"italic\":\"false\"}".formatted(name));

        NbtCompound itemNbt = itemStack.getNbt();
        if (itemNbt != null) {
            itemNbt.put("display", display);
        }

        return itemStack;
    }

    public static ItemStack addCustomColour(ItemStack itemStack, int r, int g, int b) {

        int colourCode = r * 65536 + g * 256 + b;

        NbtCompound itemNbt = itemStack.getNbt();
        if (itemNbt != null) {
            itemNbt.putInt("CustomPotionColor", colourCode);
        }

        return itemStack;

    }

    public static void serverPotionToClient(ItemStack serverItem, ItemStack clientItem) {

        NbtCompound serversideNbt = serverItem.getNbt();

        if (serversideNbt != null && serversideNbt.contains("Potion")) {

            String potionIdentifier = serversideNbt.getString("Potion");

            if (!potionIdentifier.contains("blighted")) { return; }

            Potion potion = Registries.POTION.get(Identifier.tryParse(potionIdentifier));

            PotionUtil.setCustomPotionEffects(clientItem, potion.getEffects());

            switch (potionIdentifier.substring(potionIdentifier.indexOf(":") + 1)) {

                case "resistance" -> {
                    ModPotionUtil.addCustomName(clientItem, "Ironskin Potion");
                    ModPotionUtil.addCustomColour(clientItem, 255, 255, 255);
                }

                default -> {
                    ModPotionUtil.addCustomName(clientItem, "Broken Potion");
                    ModPotionUtil.addCustomColour(clientItem, 0, 0, 0);
                }

            }

        }

    }

}
