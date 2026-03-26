package com.github.alexmodguy.alexscaves.mixin;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Holder.Reference.class)
public abstract class ReferenceHolderMixin<T> {

    @Shadow
    public abstract ResourceKey<T> key();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Holder<?> holder) {
            return holder.kind() == Holder.Kind.REFERENCE && holder.unwrapKey().orElse(null) == key();
        }

        return false;
    }
}
