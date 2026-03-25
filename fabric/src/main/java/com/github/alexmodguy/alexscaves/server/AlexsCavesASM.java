package com.github.alexmodguy.alexscaves.server;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class AlexsCavesASM implements Runnable {
    public static final String RARITY_DEMONIC = "ALEXSCAVES_DEMONIC";
    public static final String RARITY_NUCLEAR = "ALEXSCAVES_NUCLEAR";
    public static final String RARITY_SWEET = "ALEXSCAVES_SWEET";
    public static final String RARITY_RAINBOW = "ALEXSCAVES_RAINBOW";

    public static final String MOB_CATEGORY_CAVE_CREATURE = "ALEXSCAVES_CAVE_CREATURE";
    public static final String MOB_CATEGORY_ALEXSCAVES_DEEP_SEA_CREATURE = "ALEXSCAVES_DEEP_SEA_CREATURE";

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String mobCategoryTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1311");
        ClassTinkerers.enumBuilder(mobCategoryTarget, String.class, int.class, boolean.class, boolean.class, int.class)
                .addEnum(MOB_CATEGORY_CAVE_CREATURE, "alexscaves:cave_creature", 10, true, true, 128)
                .addEnum(MOB_CATEGORY_ALEXSCAVES_DEEP_SEA_CREATURE, "alexscaves:deep_sea_creature", 20, true, false, 128)
                .build();

    }
}
