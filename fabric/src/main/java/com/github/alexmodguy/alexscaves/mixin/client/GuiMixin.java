package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.client.RarityTools;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.UnaryOperator;

@Mixin(Gui.class)
public class GuiMixin {

    @Shadow
    private ItemStack lastToolHighlight;

    @WrapOperation(method = "renderSelectedItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 0))
    MutableComponent customRarityRenderSelectedItemName(MutableComponent instance, ChatFormatting format, Operation<MutableComponent> original) {
        UnaryOperator<Style> colorMod = RarityTools.getCustomRarityColor(lastToolHighlight.getRarity());
        if (colorMod != null) {
            return original.call(instance, format).withStyle(colorMod);
        }
        return original.call(instance, format);
    }
}
