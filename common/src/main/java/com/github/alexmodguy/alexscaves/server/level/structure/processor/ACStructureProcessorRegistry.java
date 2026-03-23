package com.github.alexmodguy.alexscaves.server.level.structure.processor;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.function.Supplier;

public class ACStructureProcessorRegistry {

    public static <T extends StructureProcessor> RegHolder<StructureProcessorType<?>, StructureProcessorType<T>> register(String name, Supplier<StructureProcessorType<T>> supplier) {
        return Services.REGISTRY_HELPER.registerStructureProcessor(name, supplier);
    }

    public static final RegHolder<StructureProcessorType<?>, StructureProcessorType<UndergroundCabinProcessor>> UNDERGROUND_CABIN = register("underground_cabin", () -> () -> UndergroundCabinProcessor.CODEC);
    public static final RegHolder<StructureProcessorType<?>, StructureProcessorType<WhalefallProcessor>> WHALEFALL = register("whalefall", () -> () -> WhalefallProcessor.CODEC);
    public static final RegHolder<StructureProcessorType<?>, StructureProcessorType<WhalefallProcessor>> WHALEFALL_SKULL = register("whalefall_skull", () -> () -> WhalefallProcessor.CODEC_SKULL);
    public static final RegHolder<StructureProcessorType<?>, StructureProcessorType<LollipopProcessor>> LOLLIPOP = register("lollipop", () -> () -> LollipopProcessor.CODEC);
    public static final RegHolder<StructureProcessorType<?>, StructureProcessorType<SodaBottleProcessor>> SODA_BOTTLE = register("soda_bottle", () -> () -> SodaBottleProcessor.CODEC);

    public static void init(){

    }
}
