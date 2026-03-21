package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.config.ACClientConfig;
import com.github.alexmodguy.alexscaves.client.particle.ACParticleRegistry;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.CommonProxy;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.ACSoundTypes;
import com.github.alexmodguy.alexscaves.server.block.poi.ACPOIRegistry;
import com.github.alexmodguy.alexscaves.server.config.ACServerConfig;
import com.github.alexmodguy.alexscaves.server.item.ACArmorMaterials;
import com.github.alexmodguy.alexscaves.server.level.carver.ACCarverRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACAdvancementTriggerRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACDataComponentRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.networking.api.C2SPayloadHandler;
import me.fzzyhmstrs.fzzy_config.networking.api.S2CPayloadHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlexsCaves {

    public static final String MOD_ID = "alexscaves";
    public static final String MOD_NAME = "Alex's Caves";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private static final Pair<ACServerConfig, ModConfigSpec> SERVER_PAIR = new ModConfigSpec.Builder()
            .configure(ACServerConfig::new);
    public static final ACServerConfig COMMON_CONFIG = SERVER_PAIR.getLeft();
    public static final ModConfigSpec COMMON_CONFIG_SPEC = SERVER_PAIR.getRight();
    private static final Pair<ACClientConfig, ModConfigSpec> CLIENT_PAIR = new ModConfigSpec.Builder()
            .configure(ACClientConfig::new);
    public static final ACClientConfig CLIENT_CONFIG = CLIENT_PAIR.getLeft();
    public static final ModConfigSpec CLIENT_CONFIG_SPEC = CLIENT_PAIR.getRight();

    // Initialize proxy based on dist
    public static CommonProxy PROXY = Services.PLATFORM_HELPER.getProxy();

    public static void init() {
        LOGGER.info("Hello from Alex's Caves Multiloader Edition");
        ACSoundRegistry.init();
        ACSoundTypes.init();
        ACBlockRegistry.init();
        ACPOIRegistry.init();
        ACAdvancementTriggerRegistry.init();
        ACDataComponentRegistry.init();
        ACParticleRegistry.init();
        ACCarverRegistry.init();
        ACArmorMaterials.init();
    }

    public static <T extends CustomPacketPayload> void registerS2C(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, S2CPayloadHandler<T> handler) {
        ConfigApiJava.network().registerS2C(type, codec, handler);
    }

    public static <T extends CustomPacketPayload> void registerC2S(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, C2SPayloadHandler<T> handler) {
        ConfigApiJava.network().registerC2S(type, codec, handler);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToServer(MSG message) {
        ConfigApiJava.network().send(message, null);
    }

    public static <MSG extends CustomPacketPayload> void sendNonLocal(MSG msg, ServerPlayer player) {
        ConfigApiJava.network().send(msg, player);
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : Services.PLATFORM_HELPER.getServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }
}
