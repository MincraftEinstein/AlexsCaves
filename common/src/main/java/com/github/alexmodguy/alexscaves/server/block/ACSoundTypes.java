package com.github.alexmodguy.alexscaves.server.block;

import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.google.common.base.Suppliers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

public class ACSoundTypes {

    public static final Supplier<SoundType> NEODYMIUM = register(() -> new SoundType(1, 1, ACSoundRegistry.NEODYMIUM_BREAK.get(), ACSoundRegistry.NEODYMIUM_STEP.get(), ACSoundRegistry.NEODYMIUM_PLACE.get(), ACSoundRegistry.NEODYMIUM_BREAKING.get(), ACSoundRegistry.NEODYMIUM_STEP.get()));
    public static final Supplier<SoundType> METAL_SWARF = register(() -> new SoundType(1, 1, ACSoundRegistry.METAL_SWARF_BREAK.get(), ACSoundRegistry.METAL_SWARF_STEP.get(), ACSoundRegistry.METAL_SWARF_PLACE.get(), ACSoundRegistry.METAL_SWARF_BREAKING.get(), ACSoundRegistry.METAL_SWARF_STEP.get()));
    public static final Supplier<SoundType> SCRAP_METAL = register(() -> new SoundType(1, 1, ACSoundRegistry.SCRAP_METAL_BREAK.get(), ACSoundRegistry.SCRAP_METAL_STEP.get(), ACSoundRegistry.SCRAP_METAL_PLACE.get(), ACSoundRegistry.SCRAP_METAL_BREAKING.get(), ACSoundRegistry.SCRAP_METAL_STEP.get()));
    public static final Supplier<SoundType> METAL_SCAFFOLDING = register(() -> new SoundType(1, 1, ACSoundRegistry.SCRAP_METAL_BREAK.get(), ACSoundRegistry.METAL_SCAFFOLDING_CLIMB.get(), ACSoundRegistry.SCRAP_METAL_PLACE.get(), ACSoundRegistry.SCRAP_METAL_BREAKING.get(), ACSoundRegistry.METAL_SCAFFOLDING_CLIMB.get()));
    public static final Supplier<SoundType> AMBER = register(() -> new SoundType(1, 1, ACSoundRegistry.AMBER_BREAK.get(), ACSoundRegistry.AMBER_STEP.get(), ACSoundRegistry.AMBER_PLACE.get(), ACSoundRegistry.AMBER_BREAKING.get(), ACSoundRegistry.AMBER_STEP.get()));
    public static final Supplier<SoundType> AMBER_MONOLITH = register(() -> new SoundType(1, 1, ACSoundRegistry.AMBER_BREAK.get(), ACSoundRegistry.AMBER_STEP.get(), ACSoundRegistry.AMBER_MONOLITH_PLACE.get(), ACSoundRegistry.AMBER_BREAKING.get(), ACSoundRegistry.AMBER_STEP.get()));
    public static final Supplier<SoundType> PEWEN_BRANCH = register(() -> new SoundType(1, 1, ACSoundRegistry.PEWEN_BRANCH_BREAK.get(), SoundEvents.CHERRY_WOOD_STEP, SoundEvents.CHERRY_WOOD_PLACE, SoundEvents.CHERRY_WOOD_HIT, SoundEvents.CHERRY_WOOD_FALL));
    public static final Supplier<SoundType> FLOOD_BASALT = register(() -> new SoundType(1, 1, ACSoundRegistry.FLOOD_BASALT_BREAK.get(), ACSoundRegistry.FLOOD_BASALT_STEP.get(), ACSoundRegistry.FLOOD_BASALT_PLACE.get(), ACSoundRegistry.FLOOD_BASALT_BREAKING.get(), ACSoundRegistry.FLOOD_BASALT_STEP.get()));
    //TODO breaks on neo find fix
    public static final Supplier<SoundType> RADROCK = register(() -> new SoundType(1, 1,
//            ACSoundRegistry.RADROCK_BREAK.get(), ACSoundRegistry.RADROCK_STEP.get(), ACSoundRegistry.RADROCK_PLACE.get(), ACSoundRegistry.RADROCK_BREAKING.get(), ACSoundRegistry.RADROCK_STEP.get()
            SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT
    ));
    public static final Supplier<SoundType> SULFUR = register(() -> new SoundType(1, 1, ACSoundRegistry.SULFUR_BREAK.get(), ACSoundRegistry.SULFUR_STEP.get(), ACSoundRegistry.SULFUR_PLACE.get(), ACSoundRegistry.SULFUR_BREAKING.get(), ACSoundRegistry.SULFUR_STEP.get()));
    public static final Supplier<SoundType> URANIUM = register(() -> new SoundType(1, 1, ACSoundRegistry.URANIUM_BREAK.get(), ACSoundRegistry.URANIUM_STEP.get(), ACSoundRegistry.URANIUM_PLACE.get(), ACSoundRegistry.URANIUM_BREAKING.get(), ACSoundRegistry.URANIUM_STEP.get()));
    public static final Supplier<SoundType> HAZMAT_BLOCK = register(() -> new SoundType(1, 1, ACSoundRegistry.HAZMAT_BLOCK_BREAK.get(), ACSoundRegistry.HAZMAT_BLOCK_STEP.get(), ACSoundRegistry.HAZMAT_BLOCK_PLACE.get(), ACSoundRegistry.HAZMAT_BLOCK_BREAKING.get(), ACSoundRegistry.HAZMAT_BLOCK_STEP.get()));
    //TODO breaks on neo find fix
    public static final Supplier<SoundType> CINDER_BLOCK = register(() -> new SoundType(1, 1,
//            ACSoundRegistry.CINDER_BLOCK_BREAK.get(), ACSoundRegistry.CINDER_BLOCK_STEP.get(), ACSoundRegistry.CINDER_BLOCK_PLACE.get(), ACSoundRegistry.CINDER_BLOCK_BREAKING.get(), ACSoundRegistry.CINDER_BLOCK_STEP.get()
            SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT
    ));
    public static final Supplier<SoundType> UNREFINED_WASTE = register(() -> new SoundType(1, 1, ACSoundRegistry.UNREFINED_WASTE_BREAK.get(), ACSoundRegistry.UNREFINED_WASTE_STEP.get(), ACSoundRegistry.UNREFINED_WASTE_PLACE.get(), ACSoundRegistry.UNREFINED_WASTE_BREAKING.get(), ACSoundRegistry.UNREFINED_WASTE_STEP.get()));
    public static final Supplier<SoundType> NUCLEAR_BOMB = register(() -> new SoundType(1, 1, ACSoundRegistry.NUCLEAR_BOMB_BREAK.get(), ACSoundRegistry.NUCLEAR_BOMB_STEP.get(), ACSoundRegistry.NUCLEAR_BOMB_PLACE.get(), ACSoundRegistry.NUCLEAR_BOMB_BREAKING.get(), ACSoundRegistry.NUCLEAR_BOMB_STEP.get()));
    public static final Supplier<SoundType> TUBE_WORM = register(() -> new SoundType(1, 1, ACSoundRegistry.TUBE_WORM_BREAK.get(), ACSoundRegistry.TUBE_WORM_STEP.get(), ACSoundRegistry.TUBE_WORM_PLACE.get(), ACSoundRegistry.TUBE_WORM_BREAKING.get(), ACSoundRegistry.TUBE_WORM_STEP.get()));
    public static final Supplier<SoundType> THORNWOOD_BRANCH = register(() -> new SoundType(1, 1, ACSoundRegistry.THORNWOOD_BRANCH_BREAK.get(), SoundEvents.MANGROVE_ROOTS_STEP, SoundEvents.MANGROVE_ROOTS_PLACE, SoundEvents.MANGROVE_ROOTS_HIT, SoundEvents.MANGROVE_ROOTS_FALL));
    //TODO breaks on neo find fix
    public static final Supplier<SoundType> PEERING_COPROLITH = register(() -> new SoundType(1, 1,
//            ACSoundRegistry.PEERING_COPROLITH_BREAK.get(), ACSoundRegistry.PEERING_COPROLITH_STEP.get(), ACSoundRegistry.PEERING_COPROLITH_PLACE.get(), ACSoundRegistry.PEERING_COPROLITH_BREAKING.get(), ACSoundRegistry.PEERING_COPROLITH_STEP.get()
            SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT
    ));
    public static final Supplier<SoundType> MOTH_BALL = register(() -> new SoundType(1, 1, ACSoundRegistry.MOTH_BALL_PLACE.get(), SoundEvents.WOOL_STEP, ACSoundRegistry.MOTH_BALL_PLACE.get(), SoundEvents.WOOL_HIT, SoundEvents.WOOL_STEP));
    public static final Supplier<SoundType> BEHOLDER = register(() -> new SoundType(1, 1, ACSoundRegistry.BEHOLDER_BREAK.get(), ACSoundRegistry.BEHOLDER_STEP.get(), ACSoundRegistry.BEHOLDER_PLACE.get(), ACSoundRegistry.BEHOLDER_BREAKING.get(), ACSoundRegistry.BEHOLDER_STEP.get()));

    public static final Supplier<SoundType> SOFT_CANDY = register(() -> new SoundType(1, 1, ACSoundRegistry.SOFT_CANDY_BREAK.get(), ACSoundRegistry.SOFT_CANDY_STEP.get(), ACSoundRegistry.SOFT_CANDY_PLACE.get(), ACSoundRegistry.SOFT_CANDY_BREAKING.get(), ACSoundRegistry.SOFT_CANDY_STEP.get()));
    //TODO breaks on neo find fix
    public static final Supplier<SoundType> DENSE_CANDY = register(() -> new SoundType(1, 1,
//            ACSoundRegistry.DENSE_CANDY_BREAK.get(), ACSoundRegistry.DENSE_CANDY_STEP.get(), ACSoundRegistry.DENSE_CANDY_PLACE.get(), ACSoundRegistry.DENSE_CANDY_BREAKING.get(), ACSoundRegistry.DENSE_CANDY_STEP.get()
            SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT, SoundEvents.CAT_EAT
    ));
    public static final Supplier<SoundType> HARD_CANDY = register(() -> new SoundType(1, 1, ACSoundRegistry.HARD_CANDY_BREAK.get(), ACSoundRegistry.HARD_CANDY_STEP.get(), ACSoundRegistry.HARD_CANDY_PLACE.get(), ACSoundRegistry.HARD_CANDY_BREAKING.get(), ACSoundRegistry.HARD_CANDY_STEP.get()));
    public static final Supplier<SoundType> SQUISHY_CANDY = register(() -> new SoundType(1, 1, ACSoundRegistry.SQUISHY_CANDY_BREAK.get(), ACSoundRegistry.SQUISHY_CANDY_STEP.get(), ACSoundRegistry.SQUISHY_CANDY_PLACE.get(), ACSoundRegistry.SQUISHY_CANDY_BREAKING.get(), ACSoundRegistry.SQUISHY_CANDY_STEP.get()));

    public static void init(){
    }
    public static Supplier<SoundType> register(Supplier<SoundType> soundType) {
        return Suppliers.memoize(soundType::get);
    }
}
