package com.github.alexmodguy.alexscaves.mixin.neo;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Debug(export = true)
@Mixin(MobEffectInstance.class)
public class MobEffectInstanceMixin {

    @WrapOperation(method = "<init>(Lnet/minecraft/core/Holder;IIZZZLnet/minecraft/world/effect/MobEffectInstance;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Holder;value()Ljava/lang/Object;"))
    <T> T preventNeoBeingBadPart1(Holder instance, Operation<T> original) {
        return (instance.isBound()) ? original.call(instance) : null;
    }

    @WrapWithCondition(method = "<init>(Lnet/minecraft/core/Holder;IIZZZLnet/minecraft/world/effect/MobEffectInstance;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;fillEffectCures(Ljava/util/Set;Lnet/minecraft/world/effect/MobEffectInstance;)V"))
    boolean preventNeoBeingBadPart2(MobEffect instance, Set set, MobEffectInstance mobEffectInstance) {
        return (instance != null);
    }
}
