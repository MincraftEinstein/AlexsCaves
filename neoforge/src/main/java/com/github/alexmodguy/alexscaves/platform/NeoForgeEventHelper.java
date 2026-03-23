package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.platform.services.EventHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

import java.util.function.Consumer;

public class NeoForgeEventHelper implements EventHelper {

    private static IEventBus modEventBus;

    public static void init(IEventBus modEventBus) {
        NeoForgeEventHelper.modEventBus = modEventBus;
    }

    @Override
    public void addSupportedBlocks(Consumer<BlockEntityExtender> consumer) {
        modEventBus.addListener((BlockEntityTypeAddBlocksEvent event) -> consumer.accept(event::modify));
    }
}
