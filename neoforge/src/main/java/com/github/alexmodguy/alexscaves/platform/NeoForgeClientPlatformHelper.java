package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NeoForgeClientPlatformHelper implements IClientPlatformHelper {

    private static IEventBus modEventBus;

    public static void init(IEventBus modEventBus) {
        NeoForgeClientPlatformHelper.modEventBus = modEventBus;

    }

    @Override
    public int getPixelRGBA(TextureAtlasSprite sprite, int frameIndex, int x, int y) {
        return sprite.getPixelRGBA(frameIndex, x, y);
    }

    @Override
    public void registerExtension(Supplier<? extends ItemLike> item, RenderExtension extensions) {
        modEventBus.addListener((RegisterClientExtensionsEvent event) -> event.registerItem(new IClientItemExtensions() {
            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                var render = extensions.getCustomRenderer();
                return render != null ? render : IClientItemExtensions.super.getCustomRenderer();
            }

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                var render = extensions.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
                return render != null ? render : IClientItemExtensions.super.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
            }
        }, item.get().asItem()));
    }
}
