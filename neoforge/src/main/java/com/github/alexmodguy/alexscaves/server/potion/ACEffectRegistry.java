package com.github.alexmodguy.alexscaves.server.potion;


import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class ACEffectRegistry {

    static RegHolder<MobEffect, MobEffect> registerEffect(String name, Supplier<MobEffect> effectSupplier) {
        return Services.REGISTRY_HELPER.registerMobEffect(name, effectSupplier);
    }

    static RegHolder<Potion, Potion> registerPotion(String name, Supplier<Potion> potionSupplier) {
        return Services.REGISTRY_HELPER.registerPotion(name, potionSupplier);
    }

    public static final Holder<MobEffect> MAGNETIZING = registerEffect("magnetizing", MagnetizedEffect::new);
    public static final Holder<MobEffect> STUNNED = registerEffect("stunned", StunnedEffect::new);
    public static final Holder<MobEffect> RAGE = registerEffect("rage", RageEffect::new);
    public static final Holder<MobEffect> IRRADIATED = registerEffect("irradiated", IrradiatedEffect::new);
    public static final Holder<MobEffect> BUBBLED = registerEffect("bubbled", BubbledEffect::new);
    public static final Holder<MobEffect> DEEPSIGHT = registerEffect("deepsight", DeepsightEffect::new);
    public static final Holder<MobEffect> DARKNESS_INCARNATE = registerEffect("darkness_incarnate", DarknessIncarnateEffect::new);
    public static final Holder<MobEffect> SUGAR_RUSH = registerEffect("sugar_rush", SugarRushEffect::new);

    public static final Holder<Potion> MAGNETIZING_POTION = registerPotion("magnetizing", () -> new Potion(new MobEffectInstance(MAGNETIZING, 3600)));
    public static final Holder<Potion> LONG_MAGNETIZING_POTION = registerPotion("long_magnetizing", () -> new Potion(new MobEffectInstance(MAGNETIZING, 9600)));
    public static final Holder<Potion> DEEPSIGHT_POTION = registerPotion("deepsight", () -> new Potion(new MobEffectInstance(DEEPSIGHT, 3600)));
    public static final Holder<Potion> LONG_DEEPSIGHT_POTION = registerPotion("long_deepsight", () -> new Potion(new MobEffectInstance(DEEPSIGHT, 9600)));
    public static final Holder<Potion> GLOWING_POTION = registerPotion("glowing", () -> new Potion(new MobEffectInstance(MobEffects.GLOWING, 3600)));
    public static final Holder<Potion> LONG_GLOWING_POTION = registerPotion("long_glowing", () -> new Potion(new MobEffectInstance(MobEffects.GLOWING, 9600)));
    public static final Holder<Potion> HASTE_POTION = registerPotion("haste", () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 3600)));
    public static final Holder<Potion> LONG_HASTE_POTION = registerPotion("long_haste", () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 9600)));
    public static final Holder<Potion> STRONG_HASTE_POTION = registerPotion("strong_haste", () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 1)));
    public static final Holder<Potion> STRONG_HUNGER_POTION = registerPotion("strong_hunger", () -> new Potion(new MobEffectInstance(MobEffects.HUNGER, 1800, 4)));
    public static final Holder<Potion> SUGAR_RUSH_POTION = registerPotion("sugar_rush", () -> new Potion(new MobEffectInstance(SUGAR_RUSH, 1800)));
    public static final Holder<Potion> LONG_SUGAR_RUSH_POTION = registerPotion("long_sugar_rush", () -> new Potion(new MobEffectInstance(SUGAR_RUSH, 3600)));

    public static void init() {
    }

    public static void registerBrewingRecipes(net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent event) {
        var builder = event.getBuilder();
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(ACItemRegistry.FERROUSLIME_BALL.get()), createPotion(MAGNETIZING_POTION)));
        builder.addMix(MAGNETIZING_POTION, Items.REDSTONE, LONG_MAGNETIZING_POTION);
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(ACItemRegistry.LANTERNFISH.get()), createPotion(DEEPSIGHT_POTION)));
        builder.addMix(DEEPSIGHT_POTION, Items.REDSTONE, LONG_DEEPSIGHT_POTION);
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(ACItemRegistry.BIOLUMINESSCENCE.get()), createPotion(GLOWING_POTION)));
        builder.addMix(GLOWING_POTION, Items.REDSTONE, LONG_GLOWING_POTION);
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(ACItemRegistry.CORRODENT_TEETH.get()), createPotion(HASTE_POTION)));
        builder.addMix(HASTE_POTION, Items.REDSTONE, LONG_HASTE_POTION);
        builder.addMix(HASTE_POTION, Items.GLOWSTONE_DUST, STRONG_HASTE_POTION);
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.STRONG_SWIFTNESS)), Ingredient.of(ACItemRegistry.SWEET_TOOTH.get()), createPotion(SUGAR_RUSH_POTION)));
        builder.addMix(SUGAR_RUSH_POTION, Items.REDSTONE, LONG_SUGAR_RUSH_POTION);
    }


    public static ItemStack createPotion(Holder<Potion> potion) {
        ItemStack stack = new ItemStack(Items.POTION);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
        return stack;
    }

    public static ItemStack createPotion(Potion potion) {
        // For Potion instances, we need to create a direct holder
        ItemStack stack = new ItemStack(Items.POTION);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Holder.direct(potion)));
        return stack;
    }

    public static ItemStack createSplashPotion(Holder<Potion> potion) {
        ItemStack stack = new ItemStack(Items.SPLASH_POTION);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
        return stack;
    }

    public static ItemStack createLingeringPotion(Holder<Potion> potion) {
        ItemStack stack = new ItemStack(Items.LINGERING_POTION);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
        return stack;
    }

    public static ItemStack createJellybean(Potion potion) {
        return createJellybean(Holder.direct(potion));
    }

    public static ItemStack createJellybean(Holder<Potion> potion) {
        ItemStack stack = new ItemStack(ACItemRegistry.JELLY_BEAN.get());
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
        return stack;
    }
}
