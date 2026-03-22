package com.github.alexmodguy.alexscaves.client.render.item;

import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class ACItemRenderProperties implements IClientPlatformHelper.RenderExtension {
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new ACItemstackRenderer();
    }
}
