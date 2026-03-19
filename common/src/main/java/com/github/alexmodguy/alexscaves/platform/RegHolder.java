package com.github.alexmodguy.alexscaves.platform;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface RegHolder<Reg, Type extends Reg> extends Holder<Reg>, Supplier<Type> {

    ResourceKey<Reg> key();

    ResourceLocation id();
}
