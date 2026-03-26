package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class FabricPlayerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        CommonEvents.onPlayerTick((Player) (Object) this);
    }
}
