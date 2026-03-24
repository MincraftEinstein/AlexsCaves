package com.github.alexmodguy.alexscaves.server.message;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.entity.util.MultipartEntity;
import me.fzzyhmstrs.fzzy_config.networking.api.ServerPlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class MultipartEntityMessage implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MultipartEntityMessage> TYPE =
        new CustomPacketPayload.Type<>(AlexsCaves.id("multipart_entity"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MultipartEntityMessage> CODEC =
        StreamCodec.ofMember(MultipartEntityMessage::write, MultipartEntityMessage::read);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public int parentId;
    public int playerId;
    public int type;

    public MultipartEntityMessage(int parentId, int playerId, int type) {
        this.parentId = parentId;
        this.playerId = playerId;
        this.type = type;
    }


    public MultipartEntityMessage() {
    }

    public static MultipartEntityMessage read(RegistryFriendlyByteBuf buf) {
        return new MultipartEntityMessage(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void write(MultipartEntityMessage message, RegistryFriendlyByteBuf buf) {
        buf.writeInt(message.parentId);
        buf.writeInt(message.playerId);
        buf.writeInt(message.type);
    }

    public static void handle(MultipartEntityMessage message, ServerPlayNetworkContext context) {
        // This packet is sent from client to server
        context.execute(() -> {
            Player player = context.player();
            if (player != null && !player.level().isClientSide) {
                Entity parent = player.level().getEntity(message.parentId);
                if (parent instanceof MultipartEntity && player.distanceTo(parent) < 16) {
                    if (message.type == 0) {
                        parent.interact(player, player.getUsedItemHand());
                    } else if (message.type == 1) {
                        // Use player's attack method to properly calculate damage with weapons, enchantments, crits, etc.
                        player.attack(parent);
                    }
                }
            }
        });
    }
}
