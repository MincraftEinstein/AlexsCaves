package com.github.alexmodguy.alexscaves.server.level.structure;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.function.Supplier;

public class ACStructureRegistry {

    public static <T extends Structure> RegHolder<StructureType<?>, StructureType<T>> register(String name, Supplier<StructureType<T>> supplier) {
        return Services.REGISTRY_HELPER.registerStructureType(name, supplier);
    }

    public static final RegHolder<StructureType<?>, StructureType<UndergroundCabinStructure>> UNDERGROUND_CABIN = register("underground_cabin", () -> () -> UndergroundCabinStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<FerrocaveStructure>> FERROCAVE = register("ferrocave", () -> () -> FerrocaveStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<VolcanoStructure>> VOLCANO = register("volcano", () -> () -> VolcanoStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<DinoBowlStructure>> DINO_BOWL = register("dino_bowl", () -> () -> DinoBowlStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<AcidPitStructure>> ACID_PIT = register("acid_pit", () -> () -> AcidPitStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<OceanTrenchStructure>> OCEAN_TRENCH = register("ocean_trench", () -> () -> OceanTrenchStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<AbyssalRuinsStructure>> ABYSSAL_RUINS = register("abyssal_ruins", () -> () -> AbyssalRuinsStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<ForlornCanyonStructure>> FORLORN_CANYON = register("forlorn_canyon", () -> () -> ForlornCanyonStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<ForlornBridgeStructure>> FORLORN_BRIDGE = register("forlorn_bridge", () -> () -> ForlornBridgeStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<CakeCaveStructure>> CAKE_CAVE = register("cake_cave", () -> () -> CakeCaveStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<SodaBottleStructure>> SODA_BOTTLE = register("soda_bottle", () -> () -> SodaBottleStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<DonutArchStructure>> DONUT_ARCH = register("donut_arch", () -> () -> DonutArchStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<LicowitchTowerStructure>> LICOWITCH_TOWER = register("licowitch_tower", () -> () -> LicowitchTowerStructure.CODEC);
    public static final RegHolder<StructureType<?>, StructureType<GingerbreadTownStructure>> GINGERBREAD_TOWN = register("gingerbread_town", () -> () -> GingerbreadTownStructure.CODEC);

    public static void init() {
    }
}
