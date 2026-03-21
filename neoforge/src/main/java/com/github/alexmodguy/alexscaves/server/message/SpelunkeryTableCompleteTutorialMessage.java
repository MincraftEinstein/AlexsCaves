package com.github.alexmodguy.alexscaves.server.message;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.util.ACNetUtils;
import me.fzzyhmstrs.fzzy_config.networking.api.ClientPlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public class SpelunkeryTableCompleteTutorialMessage implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SpelunkeryTableCompleteTutorialMessage> TYPE =
        new CustomPacketPayload.Type<>(AlexsCaves.id("spelunkery_table_complete_tutorial"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SpelunkeryTableCompleteTutorialMessage> CODEC =
        StreamCodec.ofMember(SpelunkeryTableCompleteTutorialMessage::write, SpelunkeryTableCompleteTutorialMessage::read);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public boolean completedTutorial;

    public SpelunkeryTableCompleteTutorialMessage(boolean completedTutorial) {
        this.completedTutorial = completedTutorial;
    }


    public SpelunkeryTableCompleteTutorialMessage() {
    }

    public static SpelunkeryTableCompleteTutorialMessage read(RegistryFriendlyByteBuf buf) {
        return new SpelunkeryTableCompleteTutorialMessage(buf.readBoolean());
    }

    public static void write(SpelunkeryTableCompleteTutorialMessage message, RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(message.completedTutorial);
    }

    public static void handle(SpelunkeryTableCompleteTutorialMessage message, ClientPlayNetworkContext context) {
        // This packet is sent from server to client
        if (ACNetUtils.isClientbound(context.networkSide())) {
            Player player = AlexsCaves.PROXY.getClientSidePlayer();
            if (player != null) {
                AlexsCaves.PROXY.setSpelunkeryTutorialComplete(message.completedTutorial);
            }
        }
    }
}
