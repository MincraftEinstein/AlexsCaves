package com.github.alexmodguy.alexscaves.platform;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class NeoRegHolder<Reg, Type extends Reg> implements RegHolder<Reg, Type> {
    private final DeferredHolder<Reg, Type> deferredHolder;

    public NeoRegHolder(DeferredHolder<Reg, Type> holder) {
        deferredHolder = holder;
    }

    public static <R, T extends R> RegHolder<R, T> of(DeferredHolder<R, T> holder) {
        return new NeoRegHolder<>(holder);
    }

    @Override
    public ResourceKey<Reg> key() {
        return deferredHolder.getKey();
    }

    @Override
    public ResourceLocation id() {
        return deferredHolder.getId();
    }

    @Override
    public Holder<Reg> holder() {
        return deferredHolder.getDelegate();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return deferredHolder.equals(obj);
    }

    @Override
    public Type get() {
        return deferredHolder.get();
    }

    @Override
    public @NotNull Type value() {
        return deferredHolder.get();
    }

    @Override
    public boolean isBound() {
        return deferredHolder.isBound();
    }

    @Override
    public boolean is(ResourceLocation location) {
        return deferredHolder.is(location);
    }

    @Override
    public boolean is(ResourceKey<Reg> resourceKey) {
        return deferredHolder.is(resourceKey);
    }

    @Override
    public boolean is(Predicate<ResourceKey<Reg>> predicate) {
        return deferredHolder.is(predicate);
    }

    @Override
    public boolean is(TagKey<Reg> tagKey) {
        return deferredHolder.is(tagKey);
    }

    @Deprecated
    @Override
    public boolean is(Holder<Reg> holder) {
        return deferredHolder.is(holder);
    }

    @Override
    public @NotNull Stream<TagKey<Reg>> tags() {
        return deferredHolder.tags();
    }

    @Override
    public Either<ResourceKey<Reg>, Reg> unwrap() {
        return deferredHolder.unwrap();
    }

    @Override
    public Optional<ResourceKey<Reg>> unwrapKey() {
        return deferredHolder.unwrapKey();
    }

    @Override
    public Kind kind() {
        return deferredHolder.kind();
    }

    @Override
    public boolean canSerializeIn(HolderOwner<Reg> owner) {
        return deferredHolder.canSerializeIn(owner);
    }
}
