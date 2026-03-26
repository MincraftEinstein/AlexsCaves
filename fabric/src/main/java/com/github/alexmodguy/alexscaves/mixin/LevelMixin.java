package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Level.class)
public class LevelMixin {

    @Inject(method = "guardEntityTick", at = @At("TAIL"))
    private <T extends Entity> void onEntityTick(Consumer<T> consumerEntity, T entity, CallbackInfo ci) {
        CommonEvents.onEntityTick(entity);
    }
}
