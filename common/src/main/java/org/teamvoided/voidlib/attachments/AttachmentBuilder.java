package org.teamvoided.voidlib.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class AttachmentBuilder<T, V> {

    public final Supplier<T> defaultValue;
    @Nullable
    public Codec<T> codec;
    @Nullable
    public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;
    @Nullable
    public BiPredicate<V, ServerPlayer> syncPredicate;
    public boolean copyOnDeath;

    public AttachmentBuilder(Supplier<T> defaultValue) {
        this.defaultValue = defaultValue;
    }

    public AttachmentBuilder<T, V> persistent(Codec<T> codec) {
        this.codec = codec;
        return this;
    }

    public AttachmentBuilder<T, V> copyOnDeath() {
        copyOnDeath = true;
        return this;
    }

    public AttachmentBuilder<T, V> synced(StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, BiPredicate<V, ServerPlayer> predicate) {
        this.streamCodec = streamCodec;
        syncPredicate = predicate;
        return this;
    }

    public AttachmentBuilder<T, V> synced(StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return synced(streamCodec, (holder, player) -> true);
    }
}
