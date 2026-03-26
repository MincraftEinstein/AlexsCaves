package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Unique
    private final ServerPlayer alexscaves$me = (ServerPlayer) (Object) this;

    @Inject(method = "die", at = @At("HEAD"))
    private void ac_onLivingDeath(DamageSource damageSource, CallbackInfo ci) {
        CommonEvents.onLivingDeath(alexscaves$me, damageSource);
    }

    @Inject(method = "changeDimension", at = @At("HEAD"))
    private void changeDimension(DimensionTransition transition, CallbackInfoReturnable<Entity> cir) {
        CommonEvents.travelToDimension(alexscaves$me);
    }
}
