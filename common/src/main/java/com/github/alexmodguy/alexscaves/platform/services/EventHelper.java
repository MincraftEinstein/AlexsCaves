package com.github.alexmodguy.alexscaves.platform.services;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Consumer;

public interface EventHelper {

    void addSupportedBlocks(Consumer<BlockEntityExtender> consumer);

    @FunctionalInterface
    interface BlockEntityExtender {

        void add(BlockEntityType<?> blockEntityType, Block... blocks);
    }
}
