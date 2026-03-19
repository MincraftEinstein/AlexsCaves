package com.github.alexmodguy.alexscaves.platform;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FabRegHolder<Reg, Type extends Reg> implements RegHolder<Reg, Type> {
    private final Reference<Reg> holder;

    public FabRegHolder(Reference<Reg> holder) {
        this.holder = holder;
    }

    public static <R, T extends R> RegHolder<R, T> of(Reference<R> holder) {
        return new FabRegHolder<>(holder);
    }


    @Override
    public ResourceKey<Reg> key() {
        return holder.key();
    }

    @Override
    public ResourceLocation id() {
        return holder.key().location();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Type get() {
        return (Type) value();
    }

    @Override
    public @NotNull Reg value() {
        return holder.value();
    }

    @Override
    public boolean isBound() {
        return holder.isBound();
    }

    @Override
    public boolean is(@NotNull ResourceLocation location) {
        return holder.is(location);
    }

    @Override
    public boolean is(@NotNull ResourceKey<Reg> resourceKey) {
        return holder.is(resourceKey);
    }

    @Override
    public boolean is(@NotNull Predicate<ResourceKey<Reg>> predicate) {
        return holder.is(predicate);
    }

    @Override
    public boolean is(@NotNull TagKey<Reg> tagKey) {
        return holder.is(tagKey);
    }

    @Deprecated
    @Override
    public boolean is(Holder<Reg> holder) {
        return holder.is(holder);
    }

    @Override
    public @NotNull Stream<TagKey<Reg>> tags() {
        return holder.tags();
    }

    @Override
    public @NotNull Either<ResourceKey<Reg>, Reg> unwrap() {
        return holder.unwrap();
    }

    @Override
    public @NotNull Optional<ResourceKey<Reg>> unwrapKey() {
        return holder.unwrapKey();
    }

    @Override
    public @NotNull Kind kind() {
        return holder.kind();
    }

    @Override
    public boolean canSerializeIn(@NotNull HolderOwner<Reg> owner) {
        return holder.canSerializeIn(owner);
    }
}
