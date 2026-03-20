package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.mixin.client.AnimatedTextureAccessor;
import com.github.alexmodguy.alexscaves.mixin.client.SpriteContentsAccessor;
import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

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
}
