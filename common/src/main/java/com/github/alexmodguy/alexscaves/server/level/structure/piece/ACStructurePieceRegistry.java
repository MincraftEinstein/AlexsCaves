package com.github.alexmodguy.alexscaves.server.level.structure.piece;

import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.function.Supplier;

public class ACStructurePieceRegistry {

    public static Supplier<StructurePieceType> register(String name, Supplier<StructurePieceType> supplier) {
        return Services.REGISTRY_HELPER.registerStructurePieceType(name, supplier);
    }

    public static final Supplier<StructurePieceType> UNDERGROUND_CABIN = register("underground_cabin", () -> UndergroundCabinStructurePiece::new);
    public static final Supplier<StructurePieceType> FERROCAVE = register("ferrocave", () -> FerrocaveStructurePiece::new);
    public static final Supplier<StructurePieceType> DINO_BOWL = register("dino_bowl", () -> DinoBowlStructurePiece::new);
    public static final Supplier<StructurePieceType> VOLCANO = register("volcano", () -> VolcanoStructurePiece::new);
    public static final Supplier<StructurePieceType> ACID_PIT = register("acid_pit", () -> AcidPitStructurePiece::new);
    public static final Supplier<StructurePieceType> OCEAN_TRENCH = register("ocean_trench", () -> OceanTrenchStructurePiece::new);
    public static final Supplier<StructurePieceType> ABYSSAL_RUINS = register("abyssal_ruins", () -> AbyssalRuinsStructurePiece::new);
    public static final Supplier<StructurePieceType> FORLORN_CANYON = register("forlorn_canyon", () -> ForlornCanyonStructurePiece::new);
    public static final Supplier<StructurePieceType> FORLORN_BRIDGE = register("forlorn_bridge", () -> ForlornBridgeStructurePiece::new);
    public static final Supplier<StructurePieceType> CAKE_CAVE = register("cake_cave", () -> CakeCaveStructurePiece::new);
    public static final Supplier<StructurePieceType> SODA_BOTTLE = register("soda_bottle", () -> SodaBottleStructurePiece::new);
    public static final Supplier<StructurePieceType> DONUT_ARCH = register("donut_arch", () -> DonutArchStructurePiece::new);
    public static final Supplier<StructurePieceType> LICOWITCH_TOWER = register("licowitch_tower", () -> com.github.alexmodguy.alexscaves.server.level.structure.piece.LicowitchTowerStructurePiece::new);
    public static final Supplier<StructurePieceType> GINGERBREAD_HOUSE = register("gingerbread_house", () -> GingerbreadHousePiece::new);
    public static final Supplier<StructurePieceType> GINGERBREAD_ROAD = register("gingerbread_road", () -> GingerbreadRoadPiece::new);

    public static void init() {
    }

}
