package com.github.alexmodguy.alexscaves.server.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BurrowingArrowItem extends ArrowItem {
    public BurrowingArrowItem() {
        super(new Properties());
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        // TODO fix when entity
        return new Arrow(level, shooter, ammo, weapon); // new BurrowingArrowEntity(level, shooter);
    }
}
