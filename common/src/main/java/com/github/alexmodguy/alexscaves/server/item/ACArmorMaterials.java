package com.github.alexmodguy.alexscaves.server.item;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

import static com.github.alexmodguy.alexscaves.AlexsCaves.id;

// TODO add actual repair ingredients, when items
public class ACArmorMaterials {

    public static final RegHolder<ArmorMaterial, ArmorMaterial> PRIMORDIAL = register("primordial", () -> Ingredient.EMPTY /*Ingredient.of(TOUGH_HIDE.get()*/, SoundEvents.ARMOR_EQUIP_LEATHER, new int[] {3, 4, 3, 2}, 0, 25);
    public static final RegHolder<ArmorMaterial, ArmorMaterial> HAZMAT_SUIT = register("hazmat_suit", () -> Ingredient.EMPTY /*Ingredient.of(POLYMER_PLATE.get())*/, SoundEvents.ARMOR_EQUIP_IRON, new int[] {2, 4, 5, 2}, 0.5F, 25);
    public static final RegHolder<ArmorMaterial, ArmorMaterial> DIVING_SUIT = register("diving_suit", () -> Ingredient.EMPTY /*Ingredient.of(Items.COPPER_INGOT)*/, SoundEvents.ARMOR_EQUIP_IRON, new int[] {2, 6, 5, 2}, 0, 25);
    public static final RegHolder<ArmorMaterial, ArmorMaterial> DARKNESS = register("darkness", () -> Ingredient.EMPTY /*Ingredient.of(DARK_TATTERS.get())*/, SoundEvents.ARMOR_EQUIP_LEATHER, new int[] {4, 5, 1, 1}, 0.5F, 40);
    public static final RegHolder<ArmorMaterial, ArmorMaterial> RAINBOUNCE = register("rainbounce", () -> Ingredient.EMPTY, SoundEvents.ARMOR_EQUIP_GENERIC, new int[] {2, 2, 1, 2}, 0, 40);
    public static final RegHolder<ArmorMaterial, ArmorMaterial> GINGERBREAD = register("gingerbread", () -> Ingredient.EMPTY /*Ingredient.of(GINGERBREAD_CRUMBS.get()*/, SoundEvents.ARMOR_EQUIP_LEATHER, new int[] {2, 4, 5, 2}, 0, 25);

    public static void init() {
    }

    private static RegHolder<ArmorMaterial, ArmorMaterial> register(String name, Supplier<Ingredient> repairIngredient, Holder<SoundEvent> equipSound, int[] defense, float toughness, int enchantmentValue) {
        return register(name, () -> new ArmorMaterial(toDefenseMap(defense), enchantmentValue, equipSound,
                repairIngredient, List.of(new ArmorMaterial.Layer(id(name))), toughness, 0)
        );
    }

    private static RegHolder<ArmorMaterial, ArmorMaterial> register(String name, Supplier<ArmorMaterial> material) {
        return Services.REGISTRY_HELPER.registerArmorMaterial(name, material);
    }

    private static EnumMap<ArmorItem.Type, Integer> toDefenseMap(int[] defense) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.BOOTS, defense[3]);
        map.put(ArmorItem.Type.LEGGINGS, defense[2]);
        map.put(ArmorItem.Type.CHESTPLATE, defense[1]);
        map.put(ArmorItem.Type.HELMET, defense[0]);
        map.put(ArmorItem.Type.BODY, defense[1]);
        return map;
    }
}
