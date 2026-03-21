package com.github.alexmodguy.alexscaves.server.entity;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.animal.FrogVariant;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ACFrogRegistry {

    public static final DeferredRegister<FrogVariant> DEF_REG = DeferredRegister.create(Registries.FROG_VARIANT, AlexsCaves.MOD_ID);


    public static final Holder<FrogVariant> PRIMORDIAL = DEF_REG.register("primordial", () -> new FrogVariant(AlexsCaves.id("textures/entity/primordial_frog.png")));

    public static  void init (){
    }

}
