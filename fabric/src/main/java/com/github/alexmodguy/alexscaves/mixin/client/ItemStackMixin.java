package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.client.RarityTools;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.UnaryOperator;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Rarity getRarity();

    @WrapOperation(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 0))
    MutableComponent customRarityTooltipLines(MutableComponent instance, ChatFormatting format, Operation<MutableComponent> original) {
        UnaryOperator<Style> colorMod = RarityTools.getCustomRarityColor(getRarity());
        if (colorMod != null) {
            return original.call(instance, format).withStyle(colorMod);
        }
        return original.call(instance, format);
    }

    @WrapOperation(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 0))
    MutableComponent customRarityDisplayName(MutableComponent instance, ChatFormatting format, Operation<MutableComponent> original) {
        UnaryOperator<Style> colorMod = RarityTools.getCustomRarityColor(getRarity());
        if (colorMod != null) {
            return original.call(instance, format).withStyle(colorMod);
        }
        return original.call(instance, format);
    }


}
