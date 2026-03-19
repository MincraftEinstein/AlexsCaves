package com.github.alexmodguy.alexscaves.platform.services;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

import java.util.function.Function;
import java.util.function.Supplier;

public interface RegistryHelper {

    <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type);

    default <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> type) {
        Supplier<T> block = registerBlockNoItem(name, type);
        registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    <T extends Block> Supplier<T> registerBlockNoItem(String name, Supplier<T> type);

    <T extends BlockEntityType<?>> Supplier<T> registerBlockEntity(String name, Supplier<T> type);

    // TODO
//    <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntitySupplier<T> supplier, Block... blocks);

    Supplier<Holder<Potion>> registerPotion(String name, Supplier<Potion> type);

    Supplier<Holder<MobEffect>> registerMobEffect(String name, Supplier<MobEffect> type);

    <T extends RecipeSerializer<?>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> type);

    <T extends RecipeType<?>> Supplier<T> registerRecipeType(String name, Supplier<T> type);

    <T extends MenuType<?>> Supplier<T> registerMenuType(String name, Supplier<T> type);

    // TODO
//    <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuTypeSupplier<T> supplier);

    <T extends PoiType> RegHolder<PoiType, T> registerPOIType(String name, Supplier<T> type);

    <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(String name, Function<CreativeModeTab.Builder, T> type);

    <T extends CriterionTrigger<?>> Supplier<T> registerTriggerType(String name, Supplier<T> type);

    <T extends LootItemFunction> Supplier<LootItemFunctionType<T>> registerLootFunctionType(String name, MapCodec<T> codec);

    void registerPottedPlant(Supplier<Block> plant, Supplier<Block> pottedPlant);

    <T extends ParticleType<?>> Supplier<T> registerParticle(String name, Supplier<T> particle);

    <T extends ParticleType<V>, V extends ParticleOptions> void registerParticleProvider(Supplier<T> particle, Function<SpriteSet, ParticleProvider<V>> provider);

    Supplier<SoundEvent> registerSound(String name, Supplier<SoundEvent> soundEvent);
}
