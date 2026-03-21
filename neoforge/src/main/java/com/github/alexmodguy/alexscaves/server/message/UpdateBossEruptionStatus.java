package com.github.alexmodguy.alexscaves.server.message;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.util.ACNetUtils;
import me.fzzyhmstrs.fzzy_config.networking.api.ClientPlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public class UpdateBossEruptionStatus implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateBossEruptionStatus> TYPE =
        new CustomPacketPayload.Type<>(AlexsCaves.id("update_boss_eruption_status"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBossEruptionStatus> CODEC =
        StreamCodec.ofMember(UpdateBossEruptionStatus::write, UpdateBossEruptionStatus::read);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    private int entityId;
    private boolean erupting;

    public UpdateBossEruptionStatus(int entityId, boolean erupting) {
        this.entityId = entityId;
        this.erupting = erupting;
    }


    public static UpdateBossEruptionStatus read(RegistryFriendlyByteBuf buf) {
        return new UpdateBossEruptionStatus(buf.readInt(), buf.readBoolean());
    }

    public static void write(UpdateBossEruptionStatus message, RegistryFriendlyByteBuf buf) {
        buf.writeInt(message.entityId);
        buf.writeBoolean(message.erupting);
    }

    public static void handle(UpdateBossEruptionStatus message, ClientPlayNetworkContext context) {
        // This packet is sent from server to client
        if (!ACNetUtils.isClientbound(context.networkSide())) {
            return;
        }
        Player playerSided = AlexsCaves.PROXY.getClientSidePlayer();
        if (playerSided != null) {
            AlexsCaves.PROXY.setPrimordialBossActive(playerSided.level(), message.entityId, message.erupting);
        }
    }

}
