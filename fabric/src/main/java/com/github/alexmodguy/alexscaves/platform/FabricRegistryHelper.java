package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.platform.services.RegistryHelper;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.inventory.MenuType;
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

import static com.github.alexmodguy.alexscaves.AlexsCaves.id;

public class FabricRegistryHelper implements RegistryHelper {

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type) {
        T item = Registry.register(BuiltInRegistries.ITEM, id(name), type.get());
        return () -> item;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlockNoItem(String name, Supplier<T> type) {
        T block = Registry.register(BuiltInRegistries.BLOCK, id(name), type.get());
        return () -> block;
    }

    @Override
    public <T extends BlockEntityType<?>> Supplier<T> registerBlockEntity(String name, Supplier<T> type) {
        T blockEntity = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id(name), type.get());
        return () -> blockEntity;
    }

//    @Override
//    public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntitySupplier<T> supplier, Block... blocks) {
//        return BlockEntityType.Builder.of(supplier::create, blocks).build(null);
//    }

    @Override
    public Supplier<Holder<Potion>> registerPotion(String name, Supplier<Potion> type) {
        Holder<Potion> potion = Registry.registerForHolder(BuiltInRegistries.POTION, id(name), type.get());
        return () -> potion;
    }

    @Override
    public Supplier<Holder<MobEffect>> registerMobEffect(String name, Supplier<MobEffect> type) {
        Holder<MobEffect> effect = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, id(name), type.get());
        return () -> effect;
    }

    @Override
    public <T extends RecipeSerializer<?>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> type) {
        T serializer = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id(name), type.get());
        return () -> serializer;
    }

    @Override
    public <T extends RecipeType<?>> Supplier<T> registerRecipeType(String name, Supplier<T> type) {
        T recipeType = Registry.register(BuiltInRegistries.RECIPE_TYPE, id(name), type.get());
        return () -> recipeType;
    }

    @Override
    public <T extends MenuType<?>> Supplier<T> registerMenuType(String name, Supplier<T> type) {
        T menuType = Registry.register(BuiltInRegistries.MENU, id(name), type.get());
        return () -> menuType;
    }

//    @Override
//    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuTypeSupplier<T> supplier) {
//        return new MenuType<>(supplier::create, FeatureFlags.DEFAULT_FLAGS);
//    }

    @Override
    public <T extends PoiType> RegHolder<PoiType, T> registerPOIType(String name, Supplier<T> type) {
        var id = id(name);
         PointOfInterestHelper.register(id, type.get().maxTickets(), type.get().validRange(), type.get().matchingStates());
        var holder = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolder(id).get();
        return FabRegHolder.of(holder);
    }

    @Override
    public <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(String name, Function<CreativeModeTab.Builder, T> type) {
        T tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id(name), type.apply(FabricItemGroup.builder()));
        return () -> tab;
    }

    @Override
    public <T extends CriterionTrigger<?>> Supplier<T> registerTriggerType(String name, Supplier<T> type) {
        T trigger = Registry.register(BuiltInRegistries.TRIGGER_TYPES, id(name), type.get());
        return () -> trigger;
    }

    @Override
    public <T extends LootItemFunction> Supplier<LootItemFunctionType<T>> registerLootFunctionType(String name, MapCodec<T> codec) {
        LootItemFunctionType<T> functionType = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, name, new LootItemFunctionType<>(codec));
        return () -> functionType;
    }

    @Override
    public void registerPottedPlant(Supplier<Block> plant, Supplier<Block> pottedPlant) {
    }

    @Override
    public <T extends ParticleType<?>> Supplier<T> registerParticle(String name, Supplier<T> particle) {
        T particleType = Registry.register(BuiltInRegistries.PARTICLE_TYPE, id(name), particle.get());
        return () -> particleType;
    }

    @Override
    public <T extends ParticleType<V>, V extends ParticleOptions> void registerParticleProvider(Supplier<T> particle, Function<SpriteSet, ParticleProvider<V>> provider) {
        ParticleFactoryRegistry.getInstance().register(particle.get(), provider::apply);
    }

    @Override
    public Supplier<SoundEvent> registerSound(String name, Supplier<SoundEvent> soundEvent) {
        SoundEvent sound = Registry.register(BuiltInRegistries.SOUND_EVENT, id(name), soundEvent.get());
        return () -> sound;
    }
}
