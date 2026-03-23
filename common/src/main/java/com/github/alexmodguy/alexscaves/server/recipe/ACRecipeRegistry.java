package com.github.alexmodguy.alexscaves.server.recipe;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import java.util.function.Supplier;

public class ACRecipeRegistry {
    public static final RegHolder<RecipeType<?>, RecipeType<NuclearFurnaceRecipe>> NUCLEAR_FURNACE_TYPE = Services.REGISTRY_HELPER.registerRecipeType("nuclear_furnace", () -> new RecipeType<>() {
    });

    public static final Supplier<RecipeSerializer<?>> CAVE_MAP = Services.REGISTRY_HELPER.registerRecipeSerializer("cave_map", () -> new SimpleCraftingRecipeSerializer<>(RecipeCaveMap::new));
    public static final Supplier<RecipeSerializer<?>> NUCLEAR_FURNACE = Services.REGISTRY_HELPER.registerRecipeSerializer("nuclear_furnace", () -> new SimpleCookingSerializer<>(NuclearFurnaceRecipe::new, 100));

    public static void init() {
    }
}
