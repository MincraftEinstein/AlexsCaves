package com.github.alexmodguy.alexscaves.server.entity.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum GummyColors implements StringRepresentable {
    RED,
    GREEN,
    YELLOW,
    BLUE,
    PINK;

    public static final StringRepresentable.EnumCodec<GummyColors> CODEC = StringRepresentable.fromEnum(GummyColors::values);
    
    public static final StreamCodec<ByteBuf, GummyColors> STREAM_CODEC = ByteBufCodecs.idMapper(
            i -> GummyColors.values()[i],
            GummyColors::ordinal
    );

    public static GummyColors fromOrdinal(int gummyColor) {
        return GummyColors.values()[Mth.clamp(gummyColor, 0, GummyColors.values().length - 1)];
    }

    public static GummyColors getRandom(RandomSource randomSource, boolean init) {
        return fromOrdinal(randomSource.nextInt(GummyColors.values().length - (init ? 0 : 1)));
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
