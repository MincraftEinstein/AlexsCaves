package com.github.alexmodguy.alexscaves.server.misc;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.advancements.CriterionTrigger;

import java.util.function.Supplier;

public class ACAdvancementTriggerRegistry {

    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> KILL_MOB_WITH_GALENA_GAUNTLET = register("kill_mob_with_galena_gauntlet", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> FINISHED_QUARRY = register("finished_quarry", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> DINOSAURS_MINECART = register("dinosaurs_minecart", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> CAVE_PAINTING = register("cave_painting", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> MYSTERY_CAVE_PAINTING = register("mystery_cave_painting", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> SUMMON_LUXTRUCTOSAURUS = register("summon_luxtructosaurus", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> ATLATITAN_STOMP = register("atlatitan_stomp", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> ENTER_ACID_WITH_ARMOR = register("enter_acid_with_armor", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> ACID_CREATE_RUST = register("acid_create_rust", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> REMOTE_DETONATION = register("remote_detonation", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> STOP_NUCLEAR_FURNACE_MELTDOWN = register("stop_nuclear_furnace_meltdown", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> HATCH_TREMORZILLA_EGG = register("hatch_tremorzilla_egg", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> TREMORZILLA_KILL_BEAM = register("tremorzilla_kill_beam", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> STALKED_BY_DEEP_ONE = register("stalked_by_deep_one", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> DEEP_ONE_TRADE = register("deep_one_trade", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> DEEP_ONE_NEUTRAL = register("deep_one_neutral", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> DEEP_ONE_HELPFUL = register("deep_one_helpful", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> UNDERZEALOT_SACRIFICE = register("underzealot_sacrifice", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> BEHOLDER_FAR_AWAY = register("beholder_far_away", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> EAT_DARKENED_APPLE = register("eat_darkened_apple", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> FROSTMINT_EXPLOSION = register("frostmint_explosion", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> CONVERT_BIOME = register("convert_biome", ACAdvancementTrigger::new);
    public static final RegHolder<CriterionTrigger<?>, ACAdvancementTrigger> CONVERT_NETHER_BIOME = register("convert_nether_biome", ACAdvancementTrigger::new);

    public static void init() {
    }

    private static <T extends CriterionTrigger<?>> RegHolder<CriterionTrigger<?>, T> register(String name, Supplier<T> advancement) {
        return Services.REGISTRY_HELPER.registerCriterionTrigger(name, advancement);
    }
}
