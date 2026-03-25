package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Registry.class)
public interface RegistryMixin {

    @SuppressWarnings("unchecked")
    @Inject(method = "safeCastToReference", at = @At("HEAD"), cancellable = true)
    private <T> void safeCastToReference(Holder<T> value, CallbackInfoReturnable<DataResult<Holder.Reference<T>>> cir) {
        if (value instanceof RegHolder<?,?> regHolder) {
            cir.setReturnValue(regHolder.holder() instanceof Holder.Reference<?> reference
                    ? DataResult.success((Holder.Reference<T>) reference)
                    : DataResult.error(() -> "Unregistered holder in " + regHolder.key() + ": " + value));
        }
    }
}
