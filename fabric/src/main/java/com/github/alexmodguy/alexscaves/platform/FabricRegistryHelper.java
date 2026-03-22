package com.github.alexmodguy.alexscaves.platform;

import com.github.alexmodguy.alexscaves.platform.services.RegistryHelper;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorMaterial;
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
import org.teamvoided.voidlib.attachments.AttachmentBuilder;
import org.teamvoided.voidlib.attachments.AttachmentSupplier;
import org.teamvoided.voidlib.attachments.FabricAttachmentSupplier;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.github.alexmodguy.alexscaves.AlexsCaves.id;

public class FabricRegistryHelper implements RegistryHelper {

    @Override
    public <T extends Item> RegHolder<Item, T> registerItem(String name, Supplier<T> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.ITEM, id(name), type.get()));
    }

    @Override
    public <T extends Block> RegHolder<Block, T> registerBlockNoItem(String name, Supplier<T> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.BLOCK, id(name), type.get()));
    }

    @Override
    public <T extends BlockEntityType<?>> RegHolder<BlockEntityType<?>, T> registerBlockEntity(String name, Supplier<T> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.BLOCK_ENTITY_TYPE, id(name), type.get()));
    }

    @Override
    public void addSupportedBlocks(Consumer<BlockEntityExtender> consumer) {
        consumer.accept((type, blocks) -> {
            for (Block block : blocks) {
                type.addSupportedBlock(block);
            }
        });

    }

    @Override
    public <T extends Entity> RegHolder<EntityType<?>, EntityType<T>> registerEntityType(String name, Supplier<EntityType<T>> entityTypeSupplier) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.ENTITY_TYPE, id(name), entityTypeSupplier.get()));
    }

    @Override
    public void registerPotionRecipes(Consumer<PotionBrewing.Builder> consumer) {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(consumer::accept);
    }

//    @Override
//    public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntitySupplier<T> supplier, Block... blocks) {
//        return BlockEntityType.Builder.of(supplier::create, blocks).build(null);
//    }

    @Override
    public RegHolder<Potion, Potion> registerPotion(String name, Supplier<Potion> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.POTION, id(name), type.get()));
    }

    @Override
    public RegHolder<MobEffect, MobEffect> registerMobEffect(String name, Supplier<MobEffect> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, id(name), type.get()));
    }

    @Override
    public <T extends RecipeSerializer<?>> RegHolder<RecipeSerializer<?>, T> registerRecipeSerializer(String name, Supplier<T> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.RECIPE_SERIALIZER, id(name), type.get()));
    }

    @Override
    public <T extends RecipeType<?>> RegHolder<RecipeType<?>, T> registerRecipeType(String name, Supplier<T> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.RECIPE_TYPE, id(name), type.get()));
    }

    @Override
    public <T extends MenuType<?>> RegHolder<MenuType<?>, T> registerMenuType(String name, Supplier<T> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.MENU, id(name), type.get()));
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
    public <T extends CreativeModeTab> RegHolder<CreativeModeTab, T> registerCreativeModeTab(String name, Function<CreativeModeTab.Builder, T> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.CREATIVE_MODE_TAB, id(name), type.apply(FabricItemGroup.builder())));
    }

    @Override
    public <T extends CriterionTrigger<?>> RegHolder<CriterionTrigger<?>, T> registerTriggerType(String name, Supplier<T> type) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.TRIGGER_TYPES, id(name), type.get()));
    }

    @Override
    public <T extends LootItemFunction> RegHolder<LootItemFunctionType<?>, LootItemFunctionType<T>> registerLootFunctionType(String name, MapCodec<T> codec) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.LOOT_FUNCTION_TYPE, id(name), new LootItemFunctionType<>(codec)));
    }

    @Override
    public void registerPottedPlant(RegHolder<Block, Block> plant, RegHolder<Block, Block> pottedPlant) {
    }

    @Override
    public <T extends ParticleType<?>> RegHolder<ParticleType<?>, T> registerParticle(String name, Supplier<T> particle) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.PARTICLE_TYPE, id(name), particle.get()));
    }

    @Override
    public <T extends ParticleType<V>, V extends ParticleOptions> void registerParticleProvider(Supplier<T> particle, Function<SpriteSet, ParticleProvider<V>> provider) {
        ParticleFactoryRegistry.getInstance().register(particle.get(), provider::apply);
    }

    @Override
    public RegHolder<SoundEvent, SoundEvent> registerSound(String name, Supplier<SoundEvent> soundEvent) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id(name), soundEvent.get()));
    }

    @Override
    public <T extends CriterionTrigger<?>> RegHolder<CriterionTrigger<?>, T> registerCriterionTrigger(String name, Supplier<T> criterion) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.TRIGGER_TYPES, id(name), criterion.get()));
    }

    @Override
    public <T extends DataComponentType<?>> Supplier<T> registerComponent(String name, Supplier<T> component) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.DATA_COMPONENT_TYPE, id(name), component.get()));
    }

    @Override
    public RegHolder<ArmorMaterial, ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return FabRegHolder.of(Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, id(name), material.get()));
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    @Override
    public <T, V> AttachmentSupplier<T, V> registerAttachment(String name, Class<V> holderClass, Supplier<T> defaultValue, UnaryOperator<AttachmentBuilder<T, V>> builderSupplier) {
        if (!AttachmentTarget.class.isAssignableFrom(holderClass)) {
            throw new IllegalArgumentException("Holder class must implement AttachmentTarget");
        }

        AttachmentType<T> type = AttachmentRegistry.create(id(name), typeBuilder -> {
            AttachmentBuilder<T, V> builder = builderSupplier.apply(new AttachmentBuilder<>(defaultValue));
            typeBuilder.initializer(builder.defaultValue);

            if (builder.codec != null) {
                typeBuilder.persistent(builder.codec);
            }

            if (builder.copyOnDeath) {
                typeBuilder.copyOnDeath();
            }

            if (builder.streamCodec != null) {
                typeBuilder.syncWith(builder.streamCodec, (attachmentTarget, player) -> {
                    if (builder.syncPredicate != null) {
                        return builder.syncPredicate.test((V) attachmentTarget, player);
                    }
                    return true;
                });
            }
        });
        return new FabricAttachmentSupplier<>(type);
    }
}
