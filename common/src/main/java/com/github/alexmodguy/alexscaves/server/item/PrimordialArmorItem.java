package com.github.alexmodguy.alexscaves.server.item;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class PrimordialArmorItem extends ArmorItem {

    public PrimordialArmorItem(RegHolder<ArmorMaterial, ArmorMaterial> armorMaterial, Type slot) {
        super(armorMaterial, slot, new Item.Properties().durability(slot.getDurability(20)));
    }

    public static int getExtraSaturationFromArmor(LivingEntity entity) {
        int i = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ACItemRegistry.PRIMORDIAL_HELMET.get())) {
            i++;
        }
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ACItemRegistry.PRIMORDIAL_TUNIC.get())) {
            i++;
        }
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ACItemRegistry.PRIMORDIAL_PANTS.get())) {
            i++;
        }
        return i;
    }
}
