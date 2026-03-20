package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.platform.services.RegistryHelper;
import com.github.alexmodguy.alexscaves.server.misc.ACAdvancementTrigger;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.alexmodguy.alexscaves.AlexsCaves.MOD_ID;

public class NeoForgeRegistryHelper implements RegistryHelper {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, MOD_ID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, MOD_ID);
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredRegister<GameEvent> GAME_EVENTS = DeferredRegister.create(BuiltInRegistries.GAME_EVENT, MOD_ID);
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, MOD_ID);
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);
    public static final DeferredRegister<CriterionTrigger<?>> CRITERION_TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, MOD_ID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MOD_ID);
    public static final Map<Supplier<? extends ParticleType<?>>, Function<SpriteSet, ? extends ParticleProvider<?>>> PARTICLE_PROVIDERS = new HashMap<>();

    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        POTIONS.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        MENU_TYPES.register(modEventBus);
        POI_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        PARTICLE_TYPES.register(modEventBus);
        TRIGGER_TYPES.register(modEventBus);
        LOOT_FUNCTION_TYPES.register(modEventBus);
        CRITERION_TRIGGERS.register(modEventBus);
        DATA_COMPONENTS.register(modEventBus);
    }

    @Override
    public <T extends Item> RegHolder<Item, T> registerItem(String name, Supplier<T> type) {
        return NeoRegHolder.of(ITEMS.register(name, type));
    }

    @Override
    public <T extends Block> RegHolder<Block, T> registerBlockNoItem(String name, Supplier<T> type) {
        return NeoRegHolder.of(BLOCKS.register(name, type));
    }

    @Override
    public <T extends BlockEntityType<?>> RegHolder<BlockEntityType<?>, T> registerBlockEntity(String name, Supplier<T> type) {
        return NeoRegHolder.of(BLOCK_ENTITIES.register(name, type));
    }

//    @Override
//    public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntitySupplier<T> supplier, Block... blocks) {
//        return BlockEntityType.Builder.of(supplier::create, blocks).build(null);
//    }

    @Override
    public RegHolder<Potion, Potion> registerPotion(String name, Supplier<Potion> type) {
        return NeoRegHolder.of(POTIONS.register(name, type));
    }

    @Override
    public RegHolder<MobEffect, MobEffect> registerMobEffect(String name, Supplier<MobEffect> type) {
        return NeoRegHolder.of(MOB_EFFECTS.register(name, type));
    }

    @Override
    public <T extends RecipeSerializer<?>> RegHolder<RecipeSerializer<?>, T> registerRecipeSerializer(String name, Supplier<T> type) {
        return NeoRegHolder.of(RECIPE_SERIALIZERS.register(name, type));
    }

    @Override
    public <T extends RecipeType<?>> RegHolder<RecipeType<?>, T> registerRecipeType(String name, Supplier<T> type) {
        return NeoRegHolder.of(RECIPE_TYPES.register(name, type));
    }

    @Override
    public <T extends MenuType<?>> RegHolder<MenuType<?>, T> registerMenuType(String name, Supplier<T> type) {
        return NeoRegHolder.of(MENU_TYPES.register(name, type));
    }

//    @Override
//    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuTypeSupplier<T> supplier) {
//        return new MenuType<>(supplier::create, FeatureFlags.DEFAULT_FLAGS);
//    }

    @Override
    public <T extends PoiType> RegHolder<PoiType, T> registerPOIType(String name, Supplier<T> type) {
        return NeoRegHolder.of(POI_TYPES.register(name, type));
    }

    @Override
    public <T extends CreativeModeTab> RegHolder<CreativeModeTab, T> registerCreativeModeTab(String name, Function<CreativeModeTab.Builder, T> type) {
        return NeoRegHolder.of(CREATIVE_MODE_TABS.register(name, () -> type.apply(CreativeModeTab.builder())));
    }

    @Override
    public <T extends CriterionTrigger<?>> RegHolder<CriterionTrigger<?>, T> registerTriggerType(String name, Supplier<T> type) {
        return NeoRegHolder.of(TRIGGER_TYPES.register(name, type));
    }

    @Override
    public <T extends LootItemFunction> RegHolder<LootItemFunctionType<?>, LootItemFunctionType<T>> registerLootFunctionType(String name, MapCodec<T> codec) {
        return NeoRegHolder.of(LOOT_FUNCTION_TYPES.register(name, () -> new LootItemFunctionType<>(codec)));
    }

    @Override
    public void registerPottedPlant(RegHolder<Block, Block> plant, RegHolder<Block, Block> pottedPlant) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(plant.id(), pottedPlant);
    }

    @Override
    public <T extends ParticleType<?>> RegHolder<ParticleType<?>, T> registerParticle(String name, Supplier<T> particle) {
        return NeoRegHolder.of(PARTICLE_TYPES.register(name, particle));
    }

    @Override
    public <T extends ParticleType<V>, V extends ParticleOptions> void registerParticleProvider(Supplier<T> particle, Function<SpriteSet, ParticleProvider<V>> provider) {
        PARTICLE_PROVIDERS.put(particle, provider);
    }

    @Override
    public RegHolder<SoundEvent, SoundEvent> registerSound(String name, Supplier<SoundEvent> soundEvent) {
        return NeoRegHolder.of(SOUND_EVENTS.register(name, soundEvent));
    }

    @Override
    public <T extends CriterionTrigger<?>> RegHolder<CriterionTrigger<?>, T> registerCriterionTrigger(String name, Supplier<T> criterion) {
        return NeoRegHolder.of(CRITERION_TRIGGERS.register(name, criterion));
    }

    @Override
    public <T extends DataComponentType<?>> Supplier<T> registerComponent(String name, Supplier<T> component) {
        return NeoRegHolder.of(DATA_COMPONENTS.register(name, component));
    }
}
