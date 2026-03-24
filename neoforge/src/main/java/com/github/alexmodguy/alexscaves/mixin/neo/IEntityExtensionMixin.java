package com.github.alexmodguy.alexscaves.mixin.neo;

import com.github.alexmodguy.alexscaves.server.entity.util.MultipartEntity;
import net.neoforged.neoforge.common.extensions.IEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IEntityExtension.class)
public interface IEntityExtensionMixin {

    @Inject(method = "isMultipartEntity", at = @At("HEAD"), cancellable = true)
    private void modifyIsMultipartEntity(CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof MultipartEntity) {
            cir.setReturnValue(true);
        }
    }
}
