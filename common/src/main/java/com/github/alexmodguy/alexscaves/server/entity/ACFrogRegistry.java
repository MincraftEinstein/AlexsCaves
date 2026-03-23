package com.github.alexmodguy.alexscaves.server.entity;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.world.entity.animal.FrogVariant;

public class ACFrogRegistry {

    public static final RegHolder<FrogVariant, FrogVariant> PRIMORDIAL = Services.REGISTRY_HELPER.registerFrogVariant("primordial", () -> new FrogVariant(AlexsCaves.id("textures/entity/primordial_frog.png")));

    public static void init() {
    }
}
