package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.client.event.ClientEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class FabricClientLevelMixin {

    @Inject(method = "method_32124", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;guardEntityTick(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/Entity;)V"))
    private void ac_guardEntityTick(Entity entity, CallbackInfo ci) {
        ClientEvents.onClientLivingTick(entity);
    }
}
