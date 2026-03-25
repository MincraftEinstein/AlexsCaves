package com.github.alexmodguy.alexscaves.server.misc;

import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.UUID;
import java.util.function.Supplier;

public class ACDataComponentRegistry {

    public static final Supplier<DataComponentType<ResourceKey<Biome>>> CAVE_BIOME = Services.REGISTRY_HELPER.registerComponent("cave_biome", () ->
            DataComponentType.<ResourceKey<Biome>>builder()
                    .persistent(ResourceKey.codec(Registries.BIOME))
                    .networkSynchronized(ResourceKey.streamCodec(Registries.BIOME))
                    .build()
    );

    public static final Supplier<DataComponentType<UUID>> CONTROLLED_ENTITY = Services.REGISTRY_HELPER.registerComponent("controlled_entity", () ->
            DataComponentType.<UUID>builder()
                    .persistent(UUIDUtil.CODEC)
                    .networkSynchronized(UUIDUtil.STREAM_CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<Component>> CONTROLLED_ENTITY_NAME = Services.REGISTRY_HELPER.registerComponent("controlled_entity_name", () ->
            DataComponentType.<Component>builder()
                    .persistent(ComponentSerialization.FLAT_CODEC)
                    .networkSynchronized(ComponentSerialization.STREAM_CODEC)
                    .cacheEncoding()
                    .build()
    );

    public static void init() {
    }
}
