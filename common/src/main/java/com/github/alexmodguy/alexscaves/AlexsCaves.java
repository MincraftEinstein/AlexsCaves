package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.config.ACClientConfig;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.ACSoundTypes;
import com.github.alexmodguy.alexscaves.server.block.poi.ACPOIRegistry;
import com.github.alexmodguy.alexscaves.server.config.ACServerConfig;
import com.github.alexmodguy.alexscaves.server.misc.ACAdvancementTriggerRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import net.minecraft.resources.ResourceLocation;
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

    public static void init() {
        LOGGER.info("Hello from Alex's Caves Multiloader Edition");
        ACSoundRegistry.init();
        ACSoundTypes.init();
        ACBlockRegistry.init();
        ACPOIRegistry.init();
        ACAdvancementTriggerRegistry.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
