package com.github.alexmodguy.alexscaves.server.entity;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.misc.ACMath;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;

public class ACEntityDataRegistry {

    // TODO port to Attachments
    public static final DeferredRegister<EntityDataSerializer<?>> DEF_REG = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, AlexsCaves.MOD_ID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Vec3>>> OPTIONAL_VEC_3 = DEF_REG.register("optional_vec_3", () -> EntityDataSerializer.forValueType(ACMath.OPTIONAL_VEC3_STREAM_CODEC));
}
