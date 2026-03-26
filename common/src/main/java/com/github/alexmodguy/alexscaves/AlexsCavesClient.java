package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.event.ClientEvents;
import com.github.alexmodguy.alexscaves.client.render.entity.layer.ACPotionEffectLayer;
import com.github.alexmodguy.alexscaves.client.render.item.ACArmorRenderProperties;
import com.github.alexmodguy.alexscaves.client.render.item.ACItemRenderProperties;
import com.github.alexmodguy.alexscaves.mixin.client.LayerAdderAccessor;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexthe666.citadel.client.shader.PostEffectRegistry;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

import static com.github.alexmodguy.alexscaves.client.ClientConstants.*;
import static com.github.alexmodguy.alexscaves.client.ClientProxy.*;

public class AlexsCavesClient {

    private static final ACItemRenderProperties isterProperties = new ACItemRenderProperties();
    private static final ACArmorRenderProperties armorProperties = new ACArmorRenderProperties();

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

        registerExtensions(
                armorProperties,
                ACItemRegistry.PRIMORDIAL_HELMET,
                ACItemRegistry.PRIMORDIAL_TUNIC,
                ACItemRegistry.PRIMORDIAL_PANTS,
                ACItemRegistry.HAZMAT_MASK,
                ACItemRegistry.HAZMAT_CHESTPLATE,
                ACItemRegistry.HAZMAT_LEGGINGS,
                ACItemRegistry.HAZMAT_BOOTS,
                ACItemRegistry.DIVING_HELMET,
                ACItemRegistry.DIVING_CHESTPLATE,
                ACItemRegistry.DIVING_LEGGINGS,
                ACItemRegistry.DIVING_BOOTS,
                ACItemRegistry.HOOD_OF_DARKNESS,
                ACItemRegistry.CLOAK_OF_DARKNESS,
                ACItemRegistry.RAINBOUNCE_BOOTS,
                ACItemRegistry.GINGERBREAD_HELMET,
                ACItemRegistry.GINGERBREAD_CHESTPLATE,
                ACItemRegistry.GINGERBREAD_LEGGINGS,
                ACItemRegistry.GINGERBREAD_BOOTS
        );
    }

    public static void laterSetup() {
        blockedParticleLocations.clear();
        hasACSplashText = RANDOM.nextInt(300) == 0;
        PostEffectRegistry.registerEffect(IRRADIATED_SHADER);
        PostEffectRegistry.registerEffect(HOLOGRAM_SHADER);
        PostEffectRegistry.registerEffect(PURPLE_WITCH_SHADER);
        ClientEvents.registerItemProperties();
    }

    @SafeVarargs
    private static void registerExtensions(IClientPlatformHelper.RenderExtension ext, Supplier<? extends ItemLike>... items) {
        for (var item : items) {
            Services.CLIENT_HELPER.registerExtension(item, ext);
        }
    }

    @SuppressWarnings("unchecked")
    public static void addLayerIfApplicable(EntityType<? extends LivingEntity> entityType, EntityRenderer<? extends LivingEntity> event) {
        // TODO replace with config
        if (entityType == EntityType.ENDER_DRAGON) {
            return;
        }

        LivingEntityRenderer<? extends LivingEntity, ?> renderer;

        try {
            renderer = (LivingEntityRenderer<? extends LivingEntity, ?>) event;
            if (renderer != null) {
                ((LayerAdderAccessor) renderer).ac_addLayer(new ACPotionEffectLayer(renderer));
            }
        }
        catch (Exception e) {
            AlexsCaves.LOGGER.warn("Could not apply radiation glow layer to {}, has custom renderer that is not LivingEntityRenderer.", BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
        }
    }
}
