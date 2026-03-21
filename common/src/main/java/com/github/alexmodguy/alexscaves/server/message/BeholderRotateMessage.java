package com.github.alexmodguy.alexscaves.server.message;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import me.fzzyhmstrs.fzzy_config.networking.api.ServerPlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BeholderRotateMessage implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<BeholderRotateMessage> TYPE =
        new CustomPacketPayload.Type<>(AlexsCaves.id("beholder_rotate"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BeholderRotateMessage> CODEC =
        StreamCodec.ofMember(BeholderRotateMessage::write, BeholderRotateMessage::read);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public int beholderId;
    public float rotX;
    public float rotY;

    public BeholderRotateMessage(int beholderId, float rotX, float rotY) {
        this.beholderId = beholderId;
        this.rotX = rotX;
        this.rotY = rotY;
    }


    public BeholderRotateMessage() {
    }

    public static BeholderRotateMessage read(RegistryFriendlyByteBuf buf) {
        return new BeholderRotateMessage(buf.readInt(), buf.readFloat(), buf.readFloat());
    }

    public static void write(BeholderRotateMessage message, RegistryFriendlyByteBuf buf) {
        buf.writeInt(message.beholderId);
        buf.writeFloat(message.rotX);
        buf.writeFloat(message.rotY);
    }

    public static void handle(BeholderRotateMessage message, ServerPlayNetworkContext context) {
        // This packet is sent from client to server
        context.execute(() -> {
            Player playerSided = context.player();
            if (playerSided != null) {
                Level serverLevel = playerSided.getServer().getLevel(playerSided.level().dimension());
                Entity watcher = serverLevel.getEntity(message.beholderId);
                // TODO fix when BeholderEyeEntity if is an issue at all
//                if (watcher instanceof BeholderEyeEntity beholderEye) {
//                }
            }
        });
    }
}
