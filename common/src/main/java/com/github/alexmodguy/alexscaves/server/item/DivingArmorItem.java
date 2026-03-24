package com.github.alexmodguy.alexscaves.server.item;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.platform.RegHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class DivingArmorItem extends ArmorItem {

    public DivingArmorItem(RegHolder<ArmorMaterial, ArmorMaterial> armorMaterial, Type slot) {
        super(armorMaterial, slot, new Item.Properties().durability(slot.getDurability(20)).attributes(createDivingAttributes(armorMaterial.get(), slot)));
    }

    private static ItemAttributeModifiers createDivingAttributes(ArmorMaterial armorMaterial, Type type) {
        ResourceLocation id = AlexsCaves.id("armor_diving_" + type.getName());
        EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.ARMOR, new AttributeModifier(id, armorMaterial.getDefense(type), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        if (type == Type.LEGGINGS) {
            // TODO replace with custom swim speed
            builder.add(Attributes.WATER_MOVEMENT_EFFICIENCY, new AttributeModifier(id, 0.5D, AttributeModifier.Operation.ADD_VALUE), slotGroup);
        } else if (type == Type.CHESTPLATE) {
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(id, armorMaterial.toughness(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        }
        float knockbackResist = armorMaterial.knockbackResistance();
        if (knockbackResist > 0) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(id, knockbackResist, AttributeModifier.Operation.ADD_VALUE), slotGroup);
        }
        return builder.build();
    }

}
