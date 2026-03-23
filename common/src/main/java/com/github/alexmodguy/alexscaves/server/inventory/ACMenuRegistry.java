package com.github.alexmodguy.alexscaves.server.inventory;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ACMenuRegistry {

    public static <T extends AbstractContainerMenu> RegHolder<MenuType<?>, MenuType<T>> register(String name, Supplier<MenuType<T>> supplier) {
        return Services.REGISTRY_HELPER.registerMenuType(name, supplier);
    }

    public static final RegHolder<MenuType<?>, MenuType<SpelunkeryTableMenu>> SPELUNKERY_TABLE_MENU = register("spelunkery_table_menu", () -> new MenuType<>(SpelunkeryTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final RegHolder<MenuType<?>, MenuType<NuclearFurnaceMenu>> NUCLEAR_FURNACE_MENU = register("nuclear_furnace_menu", () -> new MenuType<>(NuclearFurnaceMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void init() {
    }

}
