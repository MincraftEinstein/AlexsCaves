package com.github.alexmodguy.alexscaves.server.item;

import com.github.alexthe666.citadel.item.BlockItemWithSupplier;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BlockItemWithISTER extends BlockItemWithSupplier {

    public BlockItemWithISTER(Supplier<Block> blockSupplier, Properties props) {
        super(blockSupplier, props);
    }

    // TODO
//    @Override
//    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
//        consumer.accept((IClientItemExtensions) AlexsCavesNeoForge.PROXY.getISTERProperties());
//    }
}
