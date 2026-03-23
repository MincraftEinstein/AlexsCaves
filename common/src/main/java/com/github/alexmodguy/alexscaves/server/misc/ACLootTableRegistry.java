package com.github.alexmodguy.alexscaves.server.misc;

import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

import java.util.function.Supplier;

import static com.github.alexmodguy.alexscaves.AlexsCaves.id;

public class ACLootTableRegistry {

    public static final Supplier<LootItemFunctionType<GummyColorLootFunction>> GUMMY_COLORS_LOOT_FUNCTION = Services.REGISTRY_HELPER.registerLootFunctionType("gummy_colors", GummyColorLootFunction.CODEC);

    public static final ResourceKey<LootTable> ABYSSAL_RUINS_CHEST = createKey("chests/abyssal_ruins");
    public static final ResourceKey<LootTable> WITCH_HUT_CHEST = createKey("chests/witch_hut");
    public static final ResourceKey<LootTable> LICOWITCH_TOWER_CHEST = createKey("chests/licowitch_tower");
    public static final ResourceKey<LootTable> SECRET_LICOWITCH_TOWER_CHEST = createKey("chests/licowitch_tower_secret");
    public static final ResourceKey<LootTable> GINGERBREAD_TOWN_CHEST = createKey("chests/gingerbread_town");

    public static void init() {
    }

    private static ResourceKey<LootTable> createKey(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, id(path));
    }
}
