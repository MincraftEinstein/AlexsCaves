package com.github.alexmodguy.alexscaves;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlexsCaves {

    public static final String MOD_ID = "alexscaves";
    public static final String MOD_NAME = "Alex's Caves";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("Hello from Alex's Caves Multiloader Edition");
    }
}
