package com.github.alexmodguy.alexscaves.init;

import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.message.*;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.networking.api.C2SPayloadHandler;
import me.fzzyhmstrs.fzzy_config.networking.api.S2CPayloadHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class NetworkRegistry {

    public static void registerPayloads() {
        // Server-to-client messages
        registerS2C(WorldEventMessage.TYPE, WorldEventMessage.CODEC, WorldEventMessage::handle);
        registerS2C(UpdateCaveBiomeMapTagMessage.TYPE, UpdateCaveBiomeMapTagMessage.CODEC, UpdateCaveBiomeMapTagMessage::handle);
        registerS2C(UpdateBossEruptionStatus.TYPE, UpdateBossEruptionStatus.CODEC, UpdateBossEruptionStatus::handle);
        registerS2C(UpdateBossBarMessage.TYPE, UpdateBossBarMessage.CODEC, UpdateBossBarMessage::handle);
        registerS2C(UpdateEffectVisualityEntityMessage.TYPE, UpdateEffectVisualityEntityMessage.CODEC, UpdateEffectVisualityEntityMessage::handle);
        registerS2C(UpdateItemTagMessage.TYPE, UpdateItemTagMessage.CODEC, UpdateItemTagMessage::handle);
        registerS2C(BeholderSyncMessage.TYPE, BeholderSyncMessage.CODEC, BeholderSyncMessage::handle);
        registerS2C(SundropRainbowMessage.TYPE, SundropRainbowMessage.CODEC, SundropRainbowMessage::handle);
        registerS2C(SpelunkeryTableCompleteTutorialMessage.TYPE, SpelunkeryTableCompleteTutorialMessage.CODEC, SpelunkeryTableCompleteTutorialMessage::handle);

        // Client-to-server messages
        registerC2S(MultipartEntityMessage.TYPE, MultipartEntityMessage.CODEC, MultipartEntityMessage::handle);
        registerC2S(SpelunkeryTableChangeMessage.TYPE, SpelunkeryTableChangeMessage.CODEC, SpelunkeryTableChangeMessage::handle);
        registerC2S(PlayerJumpFromMagnetMessage.TYPE, PlayerJumpFromMagnetMessage.CODEC, PlayerJumpFromMagnetMessage::handle);
        registerC2S(MountedEntityKeyMessage.TYPE, MountedEntityKeyMessage.CODEC, MountedEntityKeyMessage::handle);
        registerC2S(PossessionKeyMessage.TYPE, PossessionKeyMessage.CODEC, PossessionKeyMessage::handle);
        registerC2S(BeholderRotateMessage.TYPE, BeholderRotateMessage.CODEC, BeholderRotateMessage::handle);
        registerC2S(ArmorKeyMessage.TYPE, ArmorKeyMessage.CODEC, ArmorKeyMessage::handle);
    }

    public static <T extends CustomPacketPayload> void registerS2C(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, S2CPayloadHandler<T> handler) {
        ConfigApiJava.network().registerS2C(type, codec, handler);
    }

    public static <T extends CustomPacketPayload> void registerC2S(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, C2SPayloadHandler<T> handler) {
        ConfigApiJava.network().registerC2S(type, codec, handler);
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToServer(MSG message) {
        ConfigApiJava.network().send(message, null);
    }

    public static <MSG extends CustomPacketPayload> void sendNonLocal(MSG msg, ServerPlayer player) {
        ConfigApiJava.network().send(msg, player);
    }

    // TODO some calls for this method should be changed to only send to players tracking a location
    public static <MSG extends CustomPacketPayload> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : Services.PLATFORM_HELPER.getServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }

    public static boolean isClientbound(PacketFlow packetFlow) {
        return packetFlow == PacketFlow.CLIENTBOUND;
    }

    public static boolean isServerbound(PacketFlow packetFlow) {
        return packetFlow == PacketFlow.SERVERBOUND;
    }
}
