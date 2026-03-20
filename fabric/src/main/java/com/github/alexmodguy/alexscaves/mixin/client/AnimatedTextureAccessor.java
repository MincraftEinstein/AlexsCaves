package com.github.alexmodguy.alexscaves.mixin.client;

import net.minecraft.client.renderer.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpriteContents.AnimatedTexture.class)
public interface AnimatedTextureAccessor {
    @Invoker("getFrameX")
    int ac_getFrameX(int frameIndex);

    @Invoker("getFrameY")
    int ac_getFrameY(int frameIndex);
}
