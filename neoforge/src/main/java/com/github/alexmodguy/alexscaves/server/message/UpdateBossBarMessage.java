package com.github.alexmodguy.alexscaves.server.message;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.AlexsCavesNeoForge;
import me.fzzyhmstrs.fzzy_config.networking.api.ClientPlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public class UpdateBossBarMessage implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateBossBarMessage> TYPE =
        new CustomPacketPayload.Type<>(AlexsCaves.id("update_boss_bar"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBossBarMessage> CODEC =
        StreamCodec.ofMember(UpdateBossBarMessage::write, UpdateBossBarMessage::read);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    private UUID bossBar;
    private int renderType;

    public UpdateBossBarMessage(UUID bossBar, int renderType) {
        this.bossBar = bossBar;
        this.renderType = renderType;
    }


    public static UpdateBossBarMessage read(RegistryFriendlyByteBuf buf) {
        return new UpdateBossBarMessage(buf.readUUID(), buf.readInt());
    }

    public static void write(UpdateBossBarMessage message, RegistryFriendlyByteBuf buf) {
        buf.writeUUID(message.bossBar);
        buf.writeInt(message.renderType);
    }

    public static void handle(UpdateBossBarMessage message, ClientPlayNetworkContext context) {
        // This packet is sent from server to client
        if (!context.networkSide().isClientbound()) {
            return;
        }
        if (message.renderType == -1) {
            AlexsCavesNeoForge.PROXY.removeBossBarRender(message.bossBar);
        } else {
            AlexsCavesNeoForge.PROXY.setBossBarRender(message.bossBar, message.renderType);
        }
    }

}
