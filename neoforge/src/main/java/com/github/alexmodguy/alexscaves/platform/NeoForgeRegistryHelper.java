package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.platform.services.RegistryHelper;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.teamvoided.voidlib.attachments.AttachmentBuilder;
import org.teamvoided.voidlib.attachments.AttachmentSupplier;
import org.teamvoided.voidlib.attachments.NeoForgeAttachmentSupplier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.github.alexmodguy.alexscaves.AlexsCaves.MOD_ID;

public class NeoForgeRegistryHelper implements RegistryHelper {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, MOD_ID);
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PROCESSOR, MOD_ID);
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE, MOD_ID);
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PIECE, MOD_ID);
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
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, MOD_ID);
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);
    public static final DeferredRegister<FrogVariant> FROG_VARIANTS = DeferredRegister.create(BuiltInRegistries.FROG_VARIANT, MOD_ID);
    public static final DeferredRegister<MapCodec<? extends SurfaceRules.ConditionSource>> SURFACE_RULE_CONDITIONS = DeferredRegister.create(BuiltInRegistries.MATERIAL_CONDITION, MOD_ID);
    public static final Map<Supplier<? extends ParticleType<?>>, Function<SpriteSet, ? extends ParticleProvider<?>>> PARTICLE_PROVIDERS = new HashMap<>();

    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        FEATURES.register(modEventBus);
        STRUCTURE_PROCESSORS.register(modEventBus);
        STRUCTURE_TYPES.register(modEventBus);
        STRUCTURE_PIECES.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        POTIONS.register(modEventBus);
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
        ARMOR_MATERIALS.register(modEventBus);
        ATTACHMENTS.register(modEventBus);
        FROG_VARIANTS.register(modEventBus);
        SURFACE_RULE_CONDITIONS.register(modEventBus);
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

    @Override
    public <T extends Entity> RegHolder<EntityType<?>, EntityType<T>> registerEntityType(String name, Supplier<EntityType<T>> type) {
        return NeoRegHolder.of(ENTITY_TYPES.register(name, type));
    }

    @Override
    public <T extends Feature<?>> RegHolder<Feature<?>, T> registerFeature(String name, Supplier<T> supplier) {
        return NeoRegHolder.of(FEATURES.register(name, supplier));
    }

    @Override
    public <T extends StructureProcessor> RegHolder<StructureProcessorType<?>, StructureProcessorType<T>> registerStructureProcessor(String name, Supplier<StructureProcessorType<T>> supplier) {
        return NeoRegHolder.of(STRUCTURE_PROCESSORS.register(name, supplier));
    }

    @Override
    public <T extends Structure> RegHolder<StructureType<?>, StructureType<T>> registerStructureType(String name, Supplier<StructureType<T>> supplier) {
        return NeoRegHolder.of(STRUCTURE_TYPES.register(name, supplier));
    }

    @Override
    public RegHolder<StructurePieceType, StructurePieceType> registerStructurePieceType(String name, Supplier<StructurePieceType> supplier) {
        return NeoRegHolder.of(STRUCTURE_PIECES.register(name, supplier));
    }


    @Override
    public void registerPotionRecipes(Consumer<PotionBrewing.Builder> consumer) {
        NeoForge.EVENT_BUS.addListener((RegisterBrewingRecipesEvent event) -> consumer.accept(event.getBuilder()));
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

    @Override
    public RegHolder<ArmorMaterial, ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return NeoRegHolder.of(ARMOR_MATERIALS.register(name, material));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, V> AttachmentSupplier<T, V> registerAttachment(String name, Class<V> holderClass, Supplier<T> defaultValue, UnaryOperator<AttachmentBuilder<T, V>> builderSupplier) {
        if (!IAttachmentHolder.class.isAssignableFrom(holderClass)) {
            throw new IllegalArgumentException("Holder class must implement IAttachmentHolder");
        }

        DeferredHolder<AttachmentType<?>, AttachmentType<T>> registered = ATTACHMENTS.register(name, () -> {
            AttachmentBuilder<T, V> builder = builderSupplier.apply(new AttachmentBuilder<>(defaultValue));
            AttachmentType.Builder<T> typeBuilder = AttachmentType.builder(builder.defaultValue);
            if (builder.codec != null) {
                typeBuilder.serialize(builder.codec);
            }

            if (builder.copyOnDeath) {
                typeBuilder.copyOnDeath();
            }

            if (builder.streamCodec != null) {
                typeBuilder.sync((holder, player) -> {
                    if (builder.syncPredicate != null) {
                        return builder.syncPredicate.test((V) holder, player);
                    }
                    return true;
                }, builder.streamCodec);
            }
            return typeBuilder.build();
        });
        return new NeoForgeAttachmentSupplier<>(registered);
    }

    @Override
    public RegHolder<FrogVariant, FrogVariant> registerFrogVariant(String name, Supplier<FrogVariant> variant) {
        return NeoRegHolder.of(FROG_VARIANTS.register(name, variant));
    }

    @Override
    public <T extends SurfaceRules.ConditionSource> Supplier<MapCodec<T>> registerSurfaceRuleCondition(String name, Supplier<MapCodec<T>> codec) {
        return NeoRegHolder.of(SURFACE_RULE_CONDITIONS.register(name, codec));
    }
}
