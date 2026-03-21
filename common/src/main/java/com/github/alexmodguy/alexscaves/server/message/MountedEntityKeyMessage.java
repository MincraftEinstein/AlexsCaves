package com.github.alexmodguy.alexscaves.server.message;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.entity.util.KeybindUsingMount;
import me.fzzyhmstrs.fzzy_config.networking.api.ServerPlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class MountedEntityKeyMessage implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MountedEntityKeyMessage> TYPE =
        new CustomPacketPayload.Type<>(AlexsCaves.id("mounted_entity_key"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MountedEntityKeyMessage> CODEC =
        StreamCodec.ofMember(MountedEntityKeyMessage::write, MountedEntityKeyMessage::read);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public int mountId;
    public int playerId;
    public int type;

    public MountedEntityKeyMessage(int mountId, int playerId, int type) {
        this.mountId = mountId;
        this.playerId = playerId;
        this.type = type;
    }


    public MountedEntityKeyMessage() {
    }

    public static MountedEntityKeyMessage read(RegistryFriendlyByteBuf buf) {
        return new MountedEntityKeyMessage(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void write(MountedEntityKeyMessage message, RegistryFriendlyByteBuf buf) {
        buf.writeInt(message.mountId);
        buf.writeInt(message.playerId);
        buf.writeInt(message.type);
    }

    public static void handle(MountedEntityKeyMessage message, ServerPlayNetworkContext context) {
        // This packet is sent from client to server
        context.execute(() -> {
            Player playerSided = context.player();
            if (playerSided != null) {
                Entity parent = playerSided.level().getEntity(message.mountId);
                Entity keyPresser = playerSided.level().getEntity(message.playerId);
                if (keyPresser != null && parent instanceof KeybindUsingMount mount && keyPresser instanceof Player && keyPresser.isPassengerOfSameVehicle(parent)) {
                    mount.onKeyPacket(keyPresser, message.type);
                }
            }
        });
    }
}
