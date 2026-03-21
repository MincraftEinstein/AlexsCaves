package com.github.alexmodguy.alexscaves.server.block.blockentity;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ACBlockEntityRegistry {

    private static <T extends BlockEntity> RegHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> beSupplier) {
        return Services.REGISTRY_HELPER.registerBlockEntity(name, beSupplier);
    }

    public static final Supplier<BlockEntityType<VolcanicCoreBlockEntity>> VOLCANIC_CORE = register("volcanic_core", () -> BlockEntityType.Builder.of(VolcanicCoreBlockEntity::new, ACBlockRegistry.VOLCANIC_CORE.get()).build(null));
    public static final Supplier<BlockEntityType<MagnetBlockEntity>> MAGNET = register("magnet", () -> BlockEntityType.Builder.of(MagnetBlockEntity::new, ACBlockRegistry.SCARLET_MAGNET.get(), ACBlockRegistry.AZURE_MAGNET.get()).build(null));
    public static final Supplier<BlockEntityType<MagnetBlockEntity>> TESLA_BULB = register("tesla_bulb", () -> BlockEntityType.Builder.of(MagnetBlockEntity::new, ACBlockRegistry.TESLA_BULB.get()).build(null));
    public static final Supplier<BlockEntityType<HologramProjectorBlockEntity>> HOLOGRAM_PROJECTOR = register("hologram_projector", () -> BlockEntityType.Builder.of(HologramProjectorBlockEntity::new, ACBlockRegistry.HOLOGRAM_PROJECTOR.get()).build(null));
    public static final Supplier<BlockEntityType<QuarryBlockEntity>> QUARRY = register("quarry", () -> BlockEntityType.Builder.of(QuarryBlockEntity::new, ACBlockRegistry.QUARRY.get()).build(null));
    public static final Supplier<BlockEntityType<AmbersolBlockEntity>> AMBERSOL = register("ambersol", () -> BlockEntityType.Builder.of(AmbersolBlockEntity::new, ACBlockRegistry.AMBERSOL.get()).build(null));
    public static final Supplier<BlockEntityType<AmberMonolithBlockEntity>> AMBER_MONOLITH = register("amber_monolith", () -> BlockEntityType.Builder.of(AmberMonolithBlockEntity::new, ACBlockRegistry.AMBER_MONOLITH.get()).build(null));
    public static final Supplier<BlockEntityType<GeothermalVentBlockEntity>> GEOTHERMAL_VENT = register("geothermal_vent", () -> BlockEntityType.Builder.of(GeothermalVentBlockEntity::new, ACBlockRegistry.GEOTHERMAL_VENT.get(), ACBlockRegistry.GEOTHERMAL_VENT_MEDIUM.get(), ACBlockRegistry.GEOTHERMAL_VENT_THIN.get()).build(null));
    public static final Supplier<BlockEntityType<NuclearFurnaceBlockEntity>> NUCLEAR_FURNACE = register("nuclear_furnace", () -> BlockEntityType.Builder.of(NuclearFurnaceBlockEntity::new, ACBlockRegistry.NUCLEAR_FURNACE.get()).build(null));
    public static final Supplier<BlockEntityType<SirenLightBlockEntity>> SIREN_LIGHT = register("siren_light", () -> BlockEntityType.Builder.of(SirenLightBlockEntity::new, ACBlockRegistry.SIREN_LIGHT.get()).build(null));
    public static final Supplier<BlockEntityType<NuclearSirenBlockEntity>> NUCLEAR_SIREN = register("nuclear_siren", () -> BlockEntityType.Builder.of(NuclearSirenBlockEntity::new, ACBlockRegistry.NUCLEAR_SIREN.get()).build(null));
    public static final Supplier<BlockEntityType<MetalBarrelBlockEntity>> METAL_BARREL = register("metal_barrel", () -> BlockEntityType.Builder.of(MetalBarrelBlockEntity::new, ACBlockRegistry.METAL_BARREL.get(), ACBlockRegistry.RUSTY_BARREL.get()).build(null));
    public static final Supplier<BlockEntityType<AbyssalAltarBlockEntity>> ABYSSAL_ALTAR = register("abyssal_altar", () -> BlockEntityType.Builder.of(AbyssalAltarBlockEntity::new, ACBlockRegistry.ABYSSAL_ALTAR.get()).build(null));
    public static final Supplier<BlockEntityType<CopperValveBlockEntity>> COPPER_VALVE = register("copper_valve", () -> BlockEntityType.Builder.of(CopperValveBlockEntity::new, ACBlockRegistry.COPPER_VALVE.get()).build(null));
    public static final Supplier<BlockEntityType<EnigmaticEngineBlockEntity>> ENIGMATIC_ENGINE = register("enigmatic_engine", () -> BlockEntityType.Builder.of(EnigmaticEngineBlockEntity::new, ACBlockRegistry.ENIGMATIC_ENGINE.get()).build(null));
    public static final Supplier<BlockEntityType<BeholderBlockEntity>> BEHOLDER = register("beholder", () -> BlockEntityType.Builder.of(BeholderBlockEntity::new, ACBlockRegistry.BEHOLDER.get()).build(null));
    public static final Supplier<BlockEntityType<GobthumperBlockEntity>> GOBTHUMPER = register("gobthumper", () -> BlockEntityType.Builder.of(GobthumperBlockEntity::new, ACBlockRegistry.GOBTHUMPER.get()).build(null));
    public static final Supplier<BlockEntityType<ConversionCrucibleBlockEntity>> CONVERSION_CRUCIBLE = register("conversion_crucible", () -> BlockEntityType.Builder.of(ConversionCrucibleBlockEntity::new, ACBlockRegistry.CONVERSION_CRUCIBLE.get()).build(null));
    public static final Supplier<BlockEntityType<GingerbarrelBlockEntity>> GINGERBARREL = register("gingerbarrel", () -> BlockEntityType.Builder.of(GingerbarrelBlockEntity::new, ACBlockRegistry.GINGERBARREL.get()).build(null));
    public static final Supplier<BlockEntityType<ConfectionOvenBlockEntity>> CONFECTION_OVEN = register("confection_oven", () -> BlockEntityType.Builder.of(ConfectionOvenBlockEntity::new, ACBlockRegistry.CONFECTION_OVEN.get()).build(null));

    public static void init() {
    }

    // Custom sign blocks are added to vanilla BlockEntityType.SIGN and BlockEntityType.HANGING_SIGN
    // using access transformers to make validBlocks accessible (public-f in accesstransformer.cfg)
    public static void expandVanillaDefinitions() {
        ImmutableSet.Builder<Block> validSignBlocks = new ImmutableSet.Builder<>();
        validSignBlocks.addAll(BlockEntityType.SIGN.validBlocks);
        validSignBlocks.add(ACBlockRegistry.PEWEN_SIGN.get());
        validSignBlocks.add(ACBlockRegistry.PEWEN_WALL_SIGN.get());
        validSignBlocks.add(ACBlockRegistry.THORNWOOD_SIGN.get());
        validSignBlocks.add(ACBlockRegistry.THORNWOOD_WALL_SIGN.get());
        BlockEntityType.SIGN.validBlocks = validSignBlocks.build();

        ImmutableSet.Builder<Block> validHangingSignBlocks = new ImmutableSet.Builder<>();
        validHangingSignBlocks.addAll(BlockEntityType.HANGING_SIGN.validBlocks);
        validHangingSignBlocks.add(ACBlockRegistry.PEWEN_HANGING_SIGN.get());
        validHangingSignBlocks.add(ACBlockRegistry.PEWEN_WALL_HANGING_SIGN.get());
        validHangingSignBlocks.add(ACBlockRegistry.THORNWOOD_HANGING_SIGN.get());
        validHangingSignBlocks.add(ACBlockRegistry.THORNWOOD_WALL_HANGING_SIGN.get());
        BlockEntityType.HANGING_SIGN.validBlocks = validHangingSignBlocks.build();
    }
}
