package com.github.alexmodguy.alexscaves.server.block.poi;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.function.Supplier;

public class ACPOIRegistry {

    public static final RegHolder<PoiType, PoiType> ATTRACTING_MAGNETS = register("attracting_magnets", () -> new PoiType(getAllAttractingMagnets(), 32, 6));
    public static final RegHolder<PoiType, PoiType> REPELLING_MAGNETS = register("repelling_magnets", () -> new PoiType(getAllRepellingMagnets(), 32, 6));
    public static final RegHolder<PoiType, PoiType> NUCLEAR_SIREN = register("nuclear_siren", () -> new PoiType(getAllStatesOf(ACBlockRegistry.NUCLEAR_SIREN.get()), 0, 6));
    public static final RegHolder<PoiType, PoiType> NUCLEAR_FURNACE = register("nuclear_furnace", () -> new PoiType(getAllStatesOf(ACBlockRegistry.NUCLEAR_FURNACE.get()), 0, 6));
    public static final RegHolder<PoiType, PoiType> ABYSSAL_ALTAR = register("abyssal_altar", () -> new PoiType(getAllStatesOf(ACBlockRegistry.ABYSSAL_ALTAR.get()), 0, 6));
    public static final RegHolder<PoiType, PoiType> MOTH_BALL = register("moth_ball", () -> new PoiType(getAllStatesOf(ACBlockRegistry.MOTH_BALL.get()), 32, 6));
    public static final RegHolder<PoiType, PoiType> SUNDROP = register("sundrop", () -> new PoiType(getAllStatesOf(ACBlockRegistry.SUNDROP.get()), 32, 6));
    public static final RegHolder<PoiType, PoiType> CONVERSION_CRUCIBLE = register("conversion_crucible", () -> new PoiType(getAllStatesOf(ACBlockRegistry.CONVERSION_CRUCIBLE.get()), 0, 6));
    public static final RegHolder<PoiType, PoiType> GINGERBARREL = register("gingerbarrel", () -> new PoiType(getAllStatesOf(ACBlockRegistry.GINGERBARREL.get()), 0, 6));

    public static void init() {
    }

    private static <T extends PoiType> RegHolder<PoiType, T> register(String name, Supplier<T> poiType) {
        return Services.REGISTRY_HELPER.registerPOIType(name, poiType);
    }

    private static Set<BlockState> getAllAttractingMagnets() {
        ImmutableSet.Builder<BlockState> builder = ImmutableSet.builder();
        builder.addAll(getAllStatesOf(ACBlockRegistry.SCARLET_NEODYMIUM_NODE.get()));
        builder.addAll(getAllStatesOf(ACBlockRegistry.SCARLET_NEODYMIUM_PILLAR.get()));
        builder.addAll(getAllStatesOf(ACBlockRegistry.BLOCK_OF_SCARLET_NEODYMIUM.get()));
        return builder.build();
    }

    private static Set<BlockState> getAllRepellingMagnets() {
        ImmutableSet.Builder<BlockState> builder = ImmutableSet.builder();
        builder.addAll(getAllStatesOf(ACBlockRegistry.AZURE_NEODYMIUM_NODE.get()));
        builder.addAll(getAllStatesOf(ACBlockRegistry.AZURE_NEODYMIUM_PILLAR.get()));
        builder.addAll(getAllStatesOf(ACBlockRegistry.BLOCK_OF_AZURE_NEODYMIUM.get()));
        return builder.build();
    }

    private static Set<BlockState> getAllStatesOf(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
