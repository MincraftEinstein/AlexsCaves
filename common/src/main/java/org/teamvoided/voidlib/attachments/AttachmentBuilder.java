package org.teamvoided.voidlib.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class AttachmentBuilder<T> {

    public final Supplier<T> defaultValue;
    @Nullable
    public Codec<T> codec;
    @Nullable
    public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;
    @Nullable
    public BiPredicate<Object, ServerPlayer> syncPredicate;
    public boolean copyOnDeath;

    public AttachmentBuilder(Supplier<T> defaultValue) {
        this.defaultValue = defaultValue;
    }

    public AttachmentBuilder<T> persistent(Codec<T> codec) {
        this.codec = codec;
        return this;
    }

    public AttachmentBuilder<T> copyOnDeath() {
        copyOnDeath = true;
        return this;
    }

    public AttachmentBuilder<T> synced(StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, BiPredicate<Object, ServerPlayer> predicate) {
        this.streamCodec = streamCodec;
        syncPredicate = predicate;
        return this;
    }

    public AttachmentBuilder<T> synced(StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return synced(streamCodec, (holder, player) -> true);
    }
}
