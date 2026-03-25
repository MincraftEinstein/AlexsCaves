package com.github.alexmodguy.alexscaves.server.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FabricCommonEvents {

    private static boolean PLAYER_JOINED = false;

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register(CommonEvents::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(CommonEvents::onServerStopping);
        // TODO Configs can't be accessed this early
//        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 2, itemListings -> {
//            if (AlexsCaves.COMMON_CONFIG.cartographersSellCabinMaps.get()) {
//                itemListings.add(new VillagerUndergroundCabinMapTrade(5, 10, 6));
//            }
//        });
//        TradeOfferHelper.registerWanderingTraderOffers(0, itemListings -> {
//            if (AlexsCaves.COMMON_CONFIG.wanderingTradersSellCabinMaps.get()) {
//                itemListings.add(new VillagerUndergroundCabinMapTrade(8, 1, 10));
//            }
//        });
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            Level level = minecraft.level;
            Player player = minecraft.player;
            if (level != null && player != null) {
                if (!PLAYER_JOINED) {
                    CommonEvents.onPlayerJoinClient(player);
                    PLAYER_JOINED = true;
                }
                return;
            }
            PLAYER_JOINED = false;
        });
        CommonEvents.initializeAttributes(FabricDefaultAttributeRegistry::register);
    }
}
