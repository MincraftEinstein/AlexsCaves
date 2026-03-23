package com.github.alexmodguy.alexscaves.server.event;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.entity.util.VillagerUndergroundCabinMapTrade;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CommonEvents {

    private static boolean PLAYER_JOINED = false;

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register(CommonCommonEvents::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(CommonCommonEvents::onServerStopping);
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 2, itemListings -> {
            if (AlexsCaves.COMMON_CONFIG.cartographersSellCabinMaps.get()) {
                itemListings.add(new VillagerUndergroundCabinMapTrade(5, 10, 6));
            }
        });
        TradeOfferHelper.registerWanderingTraderOffers(0, itemListings -> {
            if (AlexsCaves.COMMON_CONFIG.wanderingTradersSellCabinMaps.get()) {
                itemListings.add(new VillagerUndergroundCabinMapTrade(8, 1, 10));
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            Level level = minecraft.level;
            Player player = minecraft.player;
            if (level != null && player != null) {
                if (!PLAYER_JOINED) {
                    CommonCommonEvents.onPlayerJoinClient(player);
                    PLAYER_JOINED = true;
                }
                return;
            }
            PLAYER_JOINED = false;
        });
    }
}
