package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.ACSoundTypes;
import com.github.alexmodguy.alexscaves.server.block.poi.ACPOIRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlexsCaves {

    public static final String MOD_ID = "alexscaves";
    public static final String MOD_NAME = "Alex's Caves";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("Hello from Alex's Caves Multiloader Edition");
        ACSoundRegistry.init();
        ACSoundTypes.init();
        ACBlockRegistry.init();
        ACPOIRegistry.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
