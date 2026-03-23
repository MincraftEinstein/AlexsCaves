package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.platform.services.EventHelper;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class FabricEventHelper implements EventHelper {

    @Override
    public void addSupportedBlocks(Consumer<BlockEntityExtender> consumer) {
        consumer.accept((type, blocks) -> {
            for (Block block : blocks) {
                type.addSupportedBlock(block);
            }
        });
    }
}
