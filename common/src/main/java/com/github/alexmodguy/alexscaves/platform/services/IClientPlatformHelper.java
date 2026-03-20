package com.github.alexmodguy.alexscaves.platform.services;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface IClientPlatformHelper {
    int getPixelRGBA(TextureAtlasSprite sprite, int frameIndex, int x, int y);
}