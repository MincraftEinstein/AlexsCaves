package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "die", at = @At("HEAD"))
    private void ac_onLivingDeath(DamageSource damageSource, CallbackInfo ci) {
        CommonEvents.onLivingDeath((ServerPlayer) (Object) this, damageSource);
    }
}
