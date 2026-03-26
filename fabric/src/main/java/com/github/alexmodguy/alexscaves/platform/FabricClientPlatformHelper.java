package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.mixin.client.AnimatedTextureAccessor;
import com.github.alexmodguy.alexscaves.mixin.client.SpriteContentsAccessor;
import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import com.github.alexthe666.citadel.refabrciated.client.ClientExtensionsManager;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Supplier;

public class FabricClientPlatformHelper implements IClientPlatformHelper {
    @SuppressWarnings("resource")
    @Override
    public int getPixelRGBA(TextureAtlasSprite sprite, int frameIndex, int x, int y) {
        var contents = sprite.contents();
        var contentsAS = (SpriteContentsAccessor) contents;
        if (contentsAS.ac_animatedTexture() != null) {
            var tex = (AnimatedTextureAccessor) contentsAS.ac_animatedTexture();
            x += tex.ac_getFrameX(frameIndex) * contents.width();
            y += tex.ac_getFrameY(frameIndex) * contents.height();
        }

        return contentsAS.ac_originalImage().getPixelRGBA(x, y);
    }

    @Override
    public void registerExtension(Supplier<? extends ItemLike> item, RenderExtension extensions) {
        ClientExtensionsManager.ITEM_EXTENSIONS.put(item.get().asItem(), new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                var render = extensions.getCustomRenderer();
                return render != null ? render : IClientItemExtensions.super.getCustomRenderer();
            }

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                var render = extensions.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
                return render != null ? render : IClientItemExtensions.super.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
            }
        });
    }

    @Override
    public Model getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> _default) {
        var extensions = IClientItemExtensions.of(itemStack);
        return extensions != null ? extensions.getGenericArmorModel(entityLiving, itemStack, slot, _default) : _default;

    }
}
