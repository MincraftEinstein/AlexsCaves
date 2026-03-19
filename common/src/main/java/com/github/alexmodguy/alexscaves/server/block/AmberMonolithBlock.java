package com.github.alexmodguy.alexscaves.server.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public class AmberMonolithBlock extends BaseEntityBlock {

    protected AmberMonolithBlock() {
        super(Properties.of().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(3F, 12.0F).sound(ACSoundTypes.AMBER_MONOLITH.get()).lightLevel(block -> 5).noOcclusion());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return MapCodec.unit(this);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

//    @Nullable
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
//        return createTickerHelper(entityType, ACBlockEntityRegistry.AMBER_MONOLITH.get(), AmberMonolithBlockEntity::tick);
//    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        // TODO
        return null; // new AmberMonolithBlockEntity(pos, state);
    }
}
