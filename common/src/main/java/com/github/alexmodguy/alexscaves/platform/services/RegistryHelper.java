package com.github.alexmodguy.alexscaves.platform.services;

import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface RegistryHelper {

    <T extends Item> RegHolder<Item, T> registerItem(String name, Supplier<T> type);

    default <T extends Block> RegHolder<Block, T> registerBlock(String name, Supplier<T> type) {
        RegHolder<Block, T> block = registerBlockNoItem(name, type);
        registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    <T extends Block> RegHolder<Block, T> registerBlockNoItem(String name, Supplier<T> type);

    <T extends BlockEntityType<?>> RegHolder<BlockEntityType<?>, T> registerBlockEntity(String name, Supplier<T> type);

    void addSupportedBlocks(Consumer<BlockEntityExtender> consumer);

    <T extends Entity> RegHolder<EntityType<?>, EntityType<T>> registerEntityType(String name, Supplier<EntityType<T>> entityTypeSupplier);

    @FunctionalInterface
    interface BlockEntityExtender {
        void add(BlockEntityType<?> be, Block... blocks);
    }

    void registerPotionRecipes(Consumer<PotionBrewing.Builder> consumer);

    // TODO
//    <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntitySupplier<T> supplier, Block... blocks);

    RegHolder<Potion, Potion> registerPotion(String name, Supplier<Potion> type);

    RegHolder<MobEffect, MobEffect> registerMobEffect(String name, Supplier<MobEffect> type);

    <T extends RecipeSerializer<?>> RegHolder<RecipeSerializer<?>, T> registerRecipeSerializer(String name, Supplier<T> type);

    <T extends RecipeType<?>> RegHolder<RecipeType<?>, T> registerRecipeType(String name, Supplier<T> type);

    <T extends MenuType<?>> RegHolder<MenuType<?>, T> registerMenuType(String name, Supplier<T> type);

    // TODO
//    <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuTypeSupplier<T> supplier);

    <T extends PoiType> RegHolder<PoiType, T> registerPOIType(String name, Supplier<T> type);

    <T extends CreativeModeTab> RegHolder<CreativeModeTab, T> registerCreativeModeTab(String name, Function<CreativeModeTab.Builder, T> type);

    <T extends CriterionTrigger<?>> RegHolder<CriterionTrigger<?>, T> registerTriggerType(String name, Supplier<T> type);

    <T extends LootItemFunction> RegHolder<LootItemFunctionType<?>, LootItemFunctionType<T>> registerLootFunctionType(String name, MapCodec<T> codec);

    void registerPottedPlant(RegHolder<Block, Block> plant, RegHolder<Block, Block> pottedPlant);

    <T extends ParticleType<?>> RegHolder<ParticleType<?>, T> registerParticle(String name, Supplier<T> particle);

    <T extends ParticleType<V>, V extends ParticleOptions> void registerParticleProvider(Supplier<T> particle, Function<SpriteSet, ParticleProvider<V>> provider);

    RegHolder<SoundEvent, SoundEvent> registerSound(String name, Supplier<SoundEvent> soundEvent);

    <T extends CriterionTrigger<?>> RegHolder<CriterionTrigger<?>, T> registerCriterionTrigger(String name, Supplier<T> criterion);

    <T extends DataComponentType<?>> Supplier<T> registerComponent(String name, Supplier<T> component);

    RegHolder<ArmorMaterial, ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material);
}
