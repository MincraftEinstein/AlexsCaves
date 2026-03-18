package com.github.alexmodguy.alexscaves.server.block;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.MapColor;

public class HazmatBlock extends RotatedPillarBlock {

    public HazmatBlock() {
        super(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(3.5F, 12.0F).sound(ACSoundTypes.HAZMAT_BLOCK.get()));
    }

    // TODO
//    @Override
//    public PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
//        return PathType.UNPASSABLE_RAIL;
//    }
//
//    @Override
//    public PathType getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, PathType originalType) {
//        return PathType.UNPASSABLE_RAIL;
//    }
}
