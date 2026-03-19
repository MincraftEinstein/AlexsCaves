package com.github.alexmodguy.alexscaves.server.misc;

import com.github.alexmodguy.alexscaves.platform.Services;

public class ACLoadedMods {

    private static boolean distantHorizonsLoaded;
    private static boolean entityCullingLoaded;

    public static void afterAllModsLoaded(){
        distantHorizonsLoaded = Services.PLATFORM_HELPER.isModLoaded("distanthorizons");
        entityCullingLoaded = Services.PLATFORM_HELPER.isModLoaded("entityculling");
    }

    public static boolean isDistantHorizonsLoaded() {
        return distantHorizonsLoaded;
    }

    public static boolean isEntityCullingLoaded() {
        return entityCullingLoaded;
    }
}
