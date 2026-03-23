package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.platform.services.EventHelper;
import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import com.github.alexmodguy.alexscaves.platform.services.IPlatformHelper;
import com.github.alexmodguy.alexscaves.platform.services.RegistryHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM_HELPER = load(IPlatformHelper.class);
    public static final IClientPlatformHelper CLIENT_HELPER = load(IClientPlatformHelper.class);
    public static final RegistryHelper REGISTRY_HELPER = load(RegistryHelper.class);
    public static final EventHelper EVENTS = load(EventHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        AlexsCaves.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
