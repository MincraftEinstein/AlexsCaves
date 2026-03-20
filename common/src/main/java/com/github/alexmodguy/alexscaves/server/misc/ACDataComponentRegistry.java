package com.github.alexmodguy.alexscaves.server.misc;

import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

public class ACDataComponentRegistry {

    public static final Supplier<DataComponentType<ResourceKey<Biome>>> CAVE_BIOME = Services.REGISTRY_HELPER.registerComponent("cave_biome", () ->
            DataComponentType.<ResourceKey<Biome>>builder()
                    .persistent(ResourceKey.codec(Registries.BIOME))
                    .networkSynchronized(ResourceKey.streamCodec(Registries.BIOME))
                    .build()
    );

    public static void init() {
    }
}
