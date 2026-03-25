package com.github.alexmodguy.alexscaves.client;

import com.github.alexmodguy.alexscaves.server.item.ACRarity;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;

import java.awt.*;
import java.util.function.UnaryOperator;

public interface RarityTools {
    static UnaryOperator<Style> getCustomRarityColor(Rarity rarity) {
        if (rarity.equals(ACRarity.getRaritySweet())) {
            return style -> style.withColor(0xFF8ACD);
        }
        if (rarity.equals(ACRarity.getRarityRainbow())) {
            return style -> style.withColor(Color.HSBtoRGB((System.currentTimeMillis() % 5000) / 5000F, 1f, 1F));
        }
        return null;
    }
}
