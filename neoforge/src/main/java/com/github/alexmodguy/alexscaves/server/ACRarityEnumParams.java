package com.github.alexmodguy.alexscaves.server;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;

import java.awt.*;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")  // Referenced by enumextensions.json
public class ACRarityEnumParams {

    public static Object getDemonicRarityParameter(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            // id
            case 0 -> -1;
            // name
            case 1 -> "alexscaves:demonic";
            // color
            case 2 -> (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.DARK_RED);
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object getNuclearRarityParameter(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            // id
            case 0 -> -1;
            // name
            case 1 -> "alexscaves:nuclear";
            // color
            case 2 -> (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.GREEN);
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object getSweetRarityParameter(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            // id
            case 0 -> -1;
            // name
            case 1 -> "alexscaves:sweet";
            // color
            case 2 -> (UnaryOperator<Style>) style -> style.withColor(0xFF8ACD);
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object getRainbowRarityParameter(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            // id
            case 0 -> -1;
            // name
            case 1 -> "alexscaves:rainbow";
            // color
            case 2 ->
                    (UnaryOperator<Style>) style -> style.withColor(Color.HSBtoRGB((System.currentTimeMillis() % 5000) / 5000F, 1f, 1F));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }
}
