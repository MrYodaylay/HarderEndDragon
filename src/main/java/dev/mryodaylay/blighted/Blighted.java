package dev.mryodaylay.blighted;

import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Blighted implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("blighted");

    @Override
    public void onInitialize() {

        LOGGER.info("Blighted starting Up");

        ModPotions.registerPotionRecipes();

        PolymerItemUtils.ITEM_CHECK.register((itemStack) -> itemStack.isOf(Items.POTION));
        PolymerItemUtils.ITEM_MODIFICATION_EVENT.register(
                (serverItem, clientItem, player) -> {

                    if (serverItem.isOf(Items.POTION)) {

                        NbtCompound nbt = serverItem.getNbt();
                        if (nbt != null && nbt.contains("Potion")) {

                            String potionKey = nbt.getString("Potion");
                            Identifier potionID = Identifier.tryParse(potionKey);

                            // Convert modded potion to a potion with custom effects and custom color for client
                            if (potionID != null && potionID.getNamespace().equals("blighted")) {
                                ModPotionUtil.serverPotionToClient(serverItem, clientItem);
                            }

                            // Allows for stackable potions
                            clientItem.setCount(serverItem.getCount());

                            return clientItem;

                        }

                    }

                    return serverItem;
                }
        );

    }
}
