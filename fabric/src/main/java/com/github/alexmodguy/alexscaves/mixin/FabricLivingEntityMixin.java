package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import com.github.alexmodguy.alexscaves.server.misc.ACTagRegistry;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class FabricLivingEntityMixin {

    @Unique
    private final LivingEntity alexscaves$me = (LivingEntity) (Object) this;

    @Inject(method = "die", at = @At("HEAD"))
    private void ac_onDeath(DamageSource source, CallbackInfo ci) {
        CommonEvents.onLivingDeath((LivingEntity) (Object) this, source);
    }

    @ModifyVariable(method = "actuallyHurt", argsOnly = true, at = @At(value = "STORE", ordinal = 0))
    private float ac_modifyDamage(float damage, @Local(argsOnly = true) DamageSource source) {
        int i = CommonEvents.onLivingHurt((LivingEntity) (Object) this, source);
        if (i == -1) {
            return damage;
        }
        return i;
    }

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    private void onHeal(float healAmount, CallbackInfo ci) {
        if (alexscaves$me.hasEffect(ACEffectRegistry.IRRADIATED) && !alexscaves$me.getType().is(ACTagRegistry.RESISTS_RADIATION)) {
            ci.cancel();
        }
    }
}
