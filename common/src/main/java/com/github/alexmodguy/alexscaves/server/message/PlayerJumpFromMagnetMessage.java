package com.github.alexmodguy.alexscaves.server.message;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.entity.util.MagnetUtil;
import me.fzzyhmstrs.fzzy_config.networking.api.ServerPlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PlayerJumpFromMagnetMessage implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PlayerJumpFromMagnetMessage> TYPE =
        new CustomPacketPayload.Type<>(AlexsCaves.id("player_jump_from_magnet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerJumpFromMagnetMessage> CODEC =
        StreamCodec.ofMember(PlayerJumpFromMagnetMessage::write, PlayerJumpFromMagnetMessage::read);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    private int entityID;
    private boolean jumping;
    
    public PlayerJumpFromMagnetMessage(int entityID, boolean jumping) {
        this.entityID = entityID;
        this.jumping = jumping;
    }

    public static PlayerJumpFromMagnetMessage read(RegistryFriendlyByteBuf buf) {
        return new PlayerJumpFromMagnetMessage(buf.readInt(), buf.readBoolean());
    }

    public static void write(PlayerJumpFromMagnetMessage message, RegistryFriendlyByteBuf buf) {
        buf.writeInt(message.entityID);
        buf.writeBoolean(message.jumping);
    }

    public static void handle(PlayerJumpFromMagnetMessage message, ServerPlayNetworkContext context) {
        // Packet handling is automatic in NeoForge;
        Player player = context.player();
        if (player != null) {
            Entity entity = player.level().getEntity(message.entityID);
            if (MagnetUtil.isPulledByMagnets(entity) && entity instanceof LivingEntity living) {
                living.setJumping(message.jumping);
            }
        }
    }
}
