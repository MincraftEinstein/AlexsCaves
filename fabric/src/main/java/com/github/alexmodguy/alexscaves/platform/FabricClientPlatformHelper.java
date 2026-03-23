package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.mixin.client.AnimatedTextureAccessor;
import com.github.alexmodguy.alexscaves.mixin.client.SpriteContentsAccessor;
import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import com.github.alexthe666.citadel.refabrciated.client.ClientExtensionsManager;
import com.github.alexthe666.citadel.refabrciated.client.event.LivingRendererEvents;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;
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
        });
    }

    @Override
    public void setupEntityRotationsEvent(EntityRotEvent consumer) {
        LivingRendererEvents.SETUP_ROTATIONS.register((event) -> consumer.setupRot(event.getEntity(), event.getPartialTicks(), event.getBodyYRot(), event.getPoseStack()));
    }

    @Override
    public void registerBlockEntityRenderers(Consumer<BERendererRegistry> consumer) {
        consumer.accept(BlockEntityRenderers::register);
    }

    @Override
    public void registerMenuScreens(Consumer<MenuScreenRegistry> consumer) {
        consumer.accept(MenuScreens::register);
    }
}
