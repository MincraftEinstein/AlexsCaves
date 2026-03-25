package com.github.alexmodguy.alexscaves.mixin.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntityRenderer.class)
public interface LayerAdderAccessor<T extends LivingEntity, M extends EntityModel<T>> {
	@SuppressWarnings("UnusedReturnValue")
    @Invoker("addLayer")
	boolean ac_addLayer(RenderLayer<T, M> featureRenderer);
}