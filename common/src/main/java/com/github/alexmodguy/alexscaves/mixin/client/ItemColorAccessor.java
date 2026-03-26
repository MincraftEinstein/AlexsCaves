package com.github.alexmodguy.alexscaves.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface ItemColorAccessor {
    @Accessor("itemColors")
    ItemColors ac_getItemColors();
}
