package com.github.alexmodguy.alexscaves.server;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.ChatFormatting;

public class AlexsCavesASM implements Runnable {

    public static final String MOB_CATEGORY_CAVE_CREATURE = "ALEXSCAVES_CAVE_CREATURE";
    public static final String MOB_CATEGORY_ALEXSCAVES_DEEP_SEA_CREATURE = "ALEXSCAVES_DEEP_SEA_CREATURE";

    public static final String RARITY_DEMONIC = "ALEXSCAVES_DEMONIC";
    public static final String RARITY_NUCLEAR = "ALEXSCAVES_NUCLEAR";
    public static final String RARITY_SWEET = "ALEXSCAVES_SWEET";
    public static final String RARITY_RAINBOW = "ALEXSCAVES_RAINBOW";

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String mobCategoryTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1311");
        ClassTinkerers.enumBuilder(mobCategoryTarget, String.class, int.class, boolean.class, boolean.class, int.class)
                .addEnum(MOB_CATEGORY_CAVE_CREATURE, "alexscaves:cave_creature", 10, true, true, 128)
                .addEnum(MOB_CATEGORY_ALEXSCAVES_DEEP_SEA_CREATURE, "alexscaves:deep_sea_creature", 20, true, false, 128)
                .build();


        String rarityTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1814");

        String intType = "I;";
        String stringType = "Ljava/lang/String;";
        String chatColorType = "L" + remapper.mapClassName("intermediary", "net.minecraft.class_124") + ";";
        ClassTinkerers.enumBuilder(rarityTarget, intType, stringType, chatColorType)
                .addEnum(RARITY_DEMONIC, -1, "alexscaves:demonic", ChatFormatting.DARK_RED)
                .addEnum(RARITY_NUCLEAR, -2, "alexscaves:nuclear", ChatFormatting.GREEN)
                .addEnum(RARITY_SWEET, -3, "alexscaves:sweet", ChatFormatting.LIGHT_PURPLE)
                .addEnum(RARITY_RAINBOW, -4, "alexscaves:rainbow", ChatFormatting.WHITE)
                .build();

    }
}
