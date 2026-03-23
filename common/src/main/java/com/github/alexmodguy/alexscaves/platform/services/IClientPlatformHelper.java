package com.github.alexmodguy.alexscaves.platform.services;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IClientPlatformHelper {
    int getPixelRGBA(TextureAtlasSprite sprite, int frameIndex, int x, int y);

    void registerExtension(Supplier<? extends ItemLike> item, RenderExtension extensions);

    interface RenderExtension {
        default BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return null;
        }

        default HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
            return null;
        }
    }

    void setupEntityRotationsEvent(EntityRotEvent consumer);

    @FunctionalInterface
    interface EntityRotEvent {
        void setupRot(LivingEntity entity, float partialTicks, float bodyYRot, PoseStack poseStack);
    }

    void registerBlockEntityRenderers(Consumer<BERendererRegistry> consumer);

    @FunctionalInterface
    interface BERendererRegistry {
        <T extends BlockEntity> void register(BlockEntityType<? extends T> type, BlockEntityRendererProvider<T> renderProvider);
    }

    void registerMenuScreens(Consumer<MenuScreenRegistry> consumer);

    @FunctionalInterface
    interface MenuScreenRegistry {
        <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(MenuType<? extends M> type, MenuScreens.ScreenConstructor<M, U> factory);
    }
}