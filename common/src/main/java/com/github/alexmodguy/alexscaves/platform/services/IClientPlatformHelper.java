package com.github.alexmodguy.alexscaves.platform.services;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public interface IClientPlatformHelper {
    int getPixelRGBA(TextureAtlasSprite sprite, int frameIndex, int x, int y);

    void registerExtension(Supplier<? extends ItemLike> item, RenderExtension extensions);


    void setupEntityRotationsEvent(EntityRotEvent consumer);

    @FunctionalInterface
    interface EntityRotEvent {
        void setupRot(LivingEntity entity, float partialTicks, float bodyYRot, PoseStack poseStack);
    }

    interface RenderExtension {
        default BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return null;
        }

        default HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
            return null;
        }
    }
}