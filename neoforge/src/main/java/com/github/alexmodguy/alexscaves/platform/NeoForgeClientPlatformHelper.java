package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class NeoForgeClientPlatformHelper implements IClientPlatformHelper {
    @Override
    public int getPixelRGBA(TextureAtlasSprite sprite, int frameIndex, int x, int y) {
        return sprite.getPixelRGBA(frameIndex, x, y);
    }
}
