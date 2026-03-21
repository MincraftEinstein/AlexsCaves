package com.github.alexmodguy.alexscaves.server.potion;


import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

import java.util.Objects;
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
        Services.REGISTRY_HELPER.registerPotionRecipes((builder) -> {
            builder.addMix(Potions.AWKWARD, ACItemRegistry.FERROUSLIME_BALL.get(), asHolder(MAGNETIZING_POTION));
            builder.addMix(asHolder(MAGNETIZING_POTION), Items.REDSTONE, asHolder(LONG_MAGNETIZING_POTION));

            builder.addMix(Potions.AWKWARD, ACItemRegistry.LANTERNFISH.get(), asHolder(DEEPSIGHT_POTION));
            builder.addMix(asHolder(DEEPSIGHT_POTION), Items.REDSTONE, asHolder(LONG_DEEPSIGHT_POTION));

            builder.addMix(Potions.AWKWARD, ACItemRegistry.BIOLUMINESSCENCE.get(), asHolder(GLOWING_POTION));
            builder.addMix(asHolder(GLOWING_POTION), Items.REDSTONE, asHolder(LONG_GLOWING_POTION));

            builder.addMix(Potions.AWKWARD, ACItemRegistry.CORRODENT_TEETH.get(), asHolder(HASTE_POTION));
            builder.addMix(asHolder(HASTE_POTION), Items.REDSTONE, asHolder(LONG_HASTE_POTION));
            builder.addMix(asHolder(HASTE_POTION), Items.GLOWSTONE_DUST, asHolder(STRONG_HASTE_POTION));

            builder.addMix(Potions.STRONG_SWIFTNESS, ACItemRegistry.SWEET_TOOTH.get(), asHolder(SUGAR_RUSH_POTION));
            builder.addMix(asHolder(SUGAR_RUSH_POTION), Items.REDSTONE, asHolder(LONG_SUGAR_RUSH_POTION));
        });
    }

    // (ender) this hack fixes the un-registered holder issue
    public static Holder<Potion> asHolder(Holder<Potion> potion) {
        return BuiltInRegistries.POTION.getHolder(Objects.requireNonNull(potion.getKey())).get();
    }

    public static ItemStack createPotion(Holder<Potion> potion) {
        ItemStack stack = new ItemStack(Items.POTION);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(asHolder(potion)));
        return stack;
    }

    public static ItemStack createSplashPotion(Holder<Potion> potion) {
        ItemStack stack = new ItemStack(Items.SPLASH_POTION);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(asHolder(potion)));
        return stack;
    }

    public static ItemStack createLingeringPotion(Holder<Potion> potion) {
        ItemStack stack = new ItemStack(Items.LINGERING_POTION);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(asHolder(potion)));
        return stack;
    }

    public static ItemStack createJellybean(Holder<Potion> potion) {
        ItemStack stack = new ItemStack(ACItemRegistry.JELLY_BEAN.get());
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(asHolder(potion)));
        return stack;
    }
}
