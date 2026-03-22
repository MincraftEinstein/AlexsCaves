package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.render.item.ACItemRenderProperties;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class AlexsCavesClient {

    private static final ACItemRenderProperties isterProperties = new ACItemRenderProperties();

    public static void init() {
        registerExtensions(
                isterProperties,
                ACItemRegistry.CAVE_MAP,
                ACItemRegistry.GALENA_GAUNTLET,
                ACItemRegistry.RESISTOR_SHIELD,
                ACItemRegistry.PRIMITIVE_CLUB,
                ACItemRegistry.LIMESTONE_SPEAR,
                ACItemRegistry.EXTINCTION_SPEAR,
                ACBlockRegistry.SIREN_LIGHT,
                ACItemRegistry.RAYGUN,
                ACItemRegistry.SEA_STAFF,
                ACItemRegistry.ORTHOLANCE,
                ACBlockRegistry.COPPER_VALVE,
                ACBlockRegistry.BEHOLDER,
                ACItemRegistry.DREADBOW,
                ACBlockRegistry.GOBTHUMPER,
                ACItemRegistry.SHOT_GUM,
                ACItemRegistry.SUGAR_STAFF,
                ACItemRegistry.FROSTMINT_SPEAR
        );
    }

    @SafeVarargs
    @SuppressWarnings("SameParameterValue")
    private static void registerExtensions(IClientPlatformHelper.RenderExtension ext, Supplier<? extends ItemLike>... items) {
        for (var item : items) {
            Services.CLIENT_HELPER.registerExtension(item, ext);
        }
    }
}
