package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.server.config.BiomeGenerationConfig;
import com.github.alexmodguy.alexscaves.server.event.FabricCommonEvents;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;

import static com.github.alexmodguy.alexscaves.AlexsCaves.MOD_ID;

public class AlexsCavesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        AlexsCaves.init();
        FabricCommonEvents.init();
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, AlexsCaves.COMMON_CONFIG_SPEC, "alexscaves-general.toml");
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, AlexsCaves.CLIENT_CONFIG_SPEC, "alexscaves-client.toml");
        NeoForgeModConfigEvents.loading(MOD_ID).register(config -> BiomeGenerationConfig.reloadConfig());
        NeoForgeModConfigEvents.reloading(MOD_ID).register(config -> BiomeGenerationConfig.reloadConfig());
        // Has to be after config
        AlexsCaves.laterSetup();
    }
}
