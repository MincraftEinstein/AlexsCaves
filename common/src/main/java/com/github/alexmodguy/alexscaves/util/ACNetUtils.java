package com.github.alexmodguy.alexscaves.util;

import com.github.alexmodguy.alexscaves.platform.Services;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.networking.api.C2SPayloadHandler;
import me.fzzyhmstrs.fzzy_config.networking.api.S2CPayloadHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public interface ACNetUtils {
    static <T extends CustomPacketPayload> void registerS2C(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, S2CPayloadHandler<T> handler) {
        ConfigApiJava.network().registerS2C(type, codec, handler);
    }

    static <T extends CustomPacketPayload> void registerC2S(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, C2SPayloadHandler<T> handler) {
        ConfigApiJava.network().registerC2S(type, codec, handler);
    }

    static <MSG extends CustomPacketPayload> void sendMSGToServer(MSG message) {
        ConfigApiJava.network().send(message, null);
    }

    static <MSG extends CustomPacketPayload> void sendNonLocal(MSG msg, ServerPlayer player) {
        ConfigApiJava.network().send(msg, player);
    }

    static <MSG extends CustomPacketPayload> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : Services.PLATFORM_HELPER.getServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }
}
