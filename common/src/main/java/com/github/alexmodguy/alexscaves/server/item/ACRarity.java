package com.github.alexmodguy.alexscaves.server.item;

import net.minecraft.world.item.Rarity;

public class ACRarity {
    // Custom item rarities - uses NeoForge extensible enum system
    // Defined in ACRarityEnumParams and registered via META-INF/enumextensions.json
    // Use lazy initialization via getter methods to ensure enum extensions are applied first
    private static Rarity RARITY_DEMONIC_CACHE = null;
    private static Rarity RARITY_NUCLEAR_CACHE = null;
    private static Rarity RARITY_SWEET_CACHE = null;
    private static Rarity RARITY_RAINBOW_CACHE = null;

    public static Rarity getRarityDemonic() {
        if (RARITY_DEMONIC_CACHE == null) {
            RARITY_DEMONIC_CACHE = Rarity.valueOf("ALEXSCAVES_DEMONIC");
        }
        return RARITY_DEMONIC_CACHE;
    }

    public static Rarity getRarityNuclear() {
        if (RARITY_NUCLEAR_CACHE == null) {
            RARITY_NUCLEAR_CACHE = Rarity.valueOf("ALEXSCAVES_NUCLEAR");
        }
        return RARITY_NUCLEAR_CACHE;
    }

    public static Rarity getRaritySweet() {
        if (RARITY_SWEET_CACHE == null) {
            RARITY_SWEET_CACHE = Rarity.valueOf("ALEXSCAVES_SWEET");
        }
        return RARITY_SWEET_CACHE;
    }

    public static Rarity getRarityRainbow() {
        if (RARITY_RAINBOW_CACHE == null) {
            RARITY_RAINBOW_CACHE = Rarity.valueOf("ALEXSCAVES_RAINBOW");
        }
        return RARITY_RAINBOW_CACHE;
    }
}
