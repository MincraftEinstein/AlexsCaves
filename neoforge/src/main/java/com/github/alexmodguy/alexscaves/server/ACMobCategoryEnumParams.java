package com.github.alexmodguy.alexscaves.server;

@SuppressWarnings("unused") // Referenced by enumextensions.json
public class ACMobCategoryEnumParams {
    public static Object getCaveCreatureParameter(int idx, Class<?> type) {
        return switch (idx) {
            // name
            case 0 -> "alexscaves:cave_creature";
            // max spawn count
            case 1 -> 10;
            // isFriendly
            case 2 -> true;
            // isPersistent
            case 3 -> true;
            // despawnDistance
            case 4 -> 128;
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }

    public static Object getDeepSeaCreatureParameter(int idx, Class<?> type) {
        return switch (idx) {
            // name
            case 0 -> "alexscaves:deep_sea_creature";
            // max spawn count
            case 1 -> 20;
            // isFriendly
            case 2 -> true;
            // isPersistent (different from cave creature)
            case 3 -> false;
            // despawnDistance
            case 4 -> 128;
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
}
