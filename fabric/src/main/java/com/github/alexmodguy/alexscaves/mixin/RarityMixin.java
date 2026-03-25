package com.github.alexmodguy.alexscaves.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

@Mixin(value = Rarity.class, priority = 250)
public abstract class RarityMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/codec/ByteBufCodecs;idMapper(Ljava/util/function/IntFunction;Ljava/util/function/ToIntFunction;)Lnet/minecraft/network/codec/StreamCodec;"))
    private static StreamCodec<ByteBuf, Rarity> changeCodec(IntFunction<Rarity> idLookup, ToIntFunction<Rarity> idGetter, Operation<StreamCodec<ByteBuf, Rarity>> original) {
        return ByteBufCodecs.fromCodec(Rarity.CODEC);
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ByIdMap;continuous(Ljava/util/function/ToIntFunction;[Ljava/lang/Object;Lnet/minecraft/util/ByIdMap$OutOfBoundsStrategy;)Ljava/util/function/IntFunction;"))
    private static IntFunction changeById(ToIntFunction<Object> t, Object[] keyExtractor, ByIdMap.OutOfBoundsStrategy values, Operation<IntFunction<Object>> original) {
        return ByIdMap.sparse(t, keyExtractor, ByIdMap.OutOfBoundsStrategy.CLAMP);
    }
}
