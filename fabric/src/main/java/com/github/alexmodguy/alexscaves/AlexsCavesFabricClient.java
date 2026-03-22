package com.github.alexmodguy.alexscaves;

import net.fabricmc.api.ClientModInitializer;

public class AlexsCavesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AlexsCavesClient.init();
    }
}
