package com.github.alexmodguy.alexscaves.server.event;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.enchantment.ACEnchantmentRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACSpawnPlacementTypes;
import com.github.alexmodguy.alexscaves.server.entity.item.SeekingArrowEntity;
import com.github.alexmodguy.alexscaves.server.entity.item.SubmarineEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.*;
import com.github.alexmodguy.alexscaves.server.entity.util.*;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.item.AlwaysCombinableOnAnvil;
import com.github.alexmodguy.alexscaves.server.level.biome.ACBiomeRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACTagRegistry;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.github.alexmodguy.alexscaves.server.potion.DarknessIncarnateEffect;
import com.github.alexmodguy.alexscaves.server.potion.SugarRushEffect;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

public class CommonEvents {

    @SubscribeEvent
    public static void initializeAttributes2(EntityAttributeCreationEvent event) {
        CommonCommonEvents.initializeAttributes(event::put);
    }

    @SubscribeEvent
    public static void spawnPlacements(RegisterSpawnPlacementsEvent event) {
        // Custom spawn placement types for acid and soda fluids are defined in ACSpawnPlacementTypes.
        // Magnetic Caves monsters use checkMagneticCaveSpawnRules to allow spawning in lit areas (Tesla Bulbs etc.)
        event.register(ACEntityRegistry.TELETOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TeletorEntity::checkMagneticCaveSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.MAGNETRON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagnetronEntity::checkMagneticCaveSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.BOUNDROID.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BoundroidEntity::checkMagneticCaveSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.FERROUSLIME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FerrouslimeEntity::checkMagneticCaveSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.NOTOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NotorEntity::checkNotorSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.SUBTERRANODON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SubterranodonEntity::checkSubterranodonSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.VALLUMRAPTOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VallumraptorEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.GROTTOCERATOPS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GrottoceratopsEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.TRILOCARIS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TrilocarisEntity::checkTrilocarisSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.TREMORSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TremorsaurusEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.RELICHEIRUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RelicheirusEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.LUXTRUCTOSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LuxtructosaurusEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.ATLATITAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AtlatitanEntity::checkPrehistoricPostBossSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.NUCLEEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NucleeperEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.RADGILL.get(), ACSpawnPlacementTypes.IN_ACID, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RadgillEntity::checkRadgillSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.BRAINIAC.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BrainiacEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.GAMMAROACH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GammaroachEntity::checkGammaroachSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.RAYCAT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RaycatEntity::checkRaycatSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.LANTERNFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LanternfishEntity::checkLanternfishSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.SEA_PIG.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SeaPigEntity::checkSeaPigSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.HULLBREAKER.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HullbreakerEntity::checkHullbreakerSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.GOSSAMER_WORM.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GossamerWormEntity::checkGossamerWormSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.TRIPODFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TripodfishEntity::checkTripodfishSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.DEEP_ONE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeepOneBaseEntity::checkDeepOneSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.DEEP_ONE_KNIGHT.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeepOneBaseEntity::checkDeepOneSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.DEEP_ONE_MAGE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeepOneBaseEntity::checkDeepOneSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.MINE_GUARDIAN.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MineGuardianEntity::checkMineGuardianSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.GLOOMOTH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GloomothEntity::checkGloomothSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.UNDERZEALOT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UnderzealotEntity::checkUnderzealotSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.WATCHER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WatcherEntity::checkWatcherSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.CORRODENT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorrodentEntity::checkCorrodentSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.VESPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VesperEntity::checkVesperSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.FORSAKEN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ForsakenEntity::checkForsakenSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.SWEETISH_FISH.get(), ACSpawnPlacementTypes.IN_PURPLE_SODA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SweetishFishEntity::checkSweetishFishSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.CANIAC.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CaniacEntity::checkCaniacSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.GUMBEEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GumbeeperEntity::checkGumbeeperSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.CANDICORN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CandicornEntity::checkCandicornSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.GUM_WORM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GumWormEntity::checkGumWormSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.CARAMEL_CUBE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CaramelCubeEntity::checkCaramelCubeSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.GUMMY_BEAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GummyBearEntity::checkGummyBearSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.LICOWITCH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LicowitchEntity::checkLicowitchSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ACEntityRegistry.GINGERBREAD_MAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GingerbreadManEntity::checkGingerbreadManSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
    }

    @SubscribeEvent
    public void resizeEntity(EntityEvent.Size event) {
        EntityDimensions dimensions = CommonCommonEvents.onEntityResize(event.getEntity(), event.getOldSize(), event.getNewSize());
        if (dimensions != null) {
            event.setNewSize(dimensions);
        }
    }

    @SubscribeEvent
    public void livingDie(LivingDeathEvent event) {
        CommonCommonEvents.onLivingDeath(event.getEntity(), event.getSource());
    }

    // TODO implement on fabric
    @SubscribeEvent
    public void livingHeal(LivingHealEvent event) {
        if (event.getEntity().hasEffect(ACEffectRegistry.IRRADIATED) && !event.getEntity().getType().is(ACTagRegistry.RESISTS_RADIATION)) {
            event.setCanceled(true);
        }
    }

    // TODO implement on fabric and replace stuff with attachments
    @SubscribeEvent
    public void playerEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(ACItemRegistry.HOLOCODER.get()) && event.getTarget() instanceof LivingEntity && !(event.getTarget() instanceof ArmorStand) && event.getTarget().isAlive()) {
            // In 1.21, use DataComponents API instead of NBT tags
            CompoundTag tag = new CompoundTag();
            tag.putUUID("BoundEntityUUID", event.getTarget().getUUID());
            // In 1.21, serializeNBT requires RegistryAccess provider
            CompoundTag entityTag = event.getTarget() instanceof Player ? new CompoundTag() : event.getTarget().serializeNBT(event.getLevel().registryAccess());
            entityTag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(event.getTarget().getType()).toString());
            if (event.getTarget() instanceof Player) {
                entityTag.putUUID("UUID", event.getTarget().getUUID());
            }
            tag.put("BoundEntityTag", entityTag);
            ItemStack stackReplacement = new ItemStack(ACItemRegistry.HOLOCODER.get());
            stack.shrink(1);
            // In 1.21, use DataComponents.CUSTOM_DATA instead of setTag
            stackReplacement.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            event.getEntity().swing(event.getHand());
            if (!event.getEntity().addItem(stackReplacement)) {
                ItemEntity itementity = event.getEntity().drop(stackReplacement, false);
                if (itementity != null) {
                    itementity.setNoPickUpDelay();
                    // In 1.21, setThrower takes Entity, not UUID
                    itementity.setThrower(event.getEntity());
                }
            }
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    // TODO implement on fabric
    // Einstein: can this just be implemented in a custom goal?
    @SubscribeEvent
    public void livingFindTarget(LivingChangeTargetEvent event) {
        // In 1.21, use getNewAboutToBeSetTarget() instead of getNewTarget()
        if (event.getEntity() instanceof Mob mob && event.getNewAboutToBeSetTarget() instanceof VallumraptorEntity vallumraptor && vallumraptor.getHideFor() > 0) {
            mob.setTarget(null);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void livingHurt(LivingDamageEvent.Pre event) {
        // In 1.21, use LivingDamageEvent.Pre and setNewDamage(0) instead of setCanceled()
        int damageAmount = CommonCommonEvents.onLivingHurt(event.getEntity(), event.getSource());
        if (damageAmount != -1) {
            event.setNewDamage(damageAmount);
        }
    }

    @SubscribeEvent
    public void livingAttack(LivingIncomingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow && event.getEntity().isBlocking() && event.getEntity().getUseItem().is(ACItemRegistry.RESISTOR_SHIELD.get())) {
            ItemStack shield = event.getEntity().getUseItem();
            // Check for arrow inducting enchantment using 1.21 data-driven system
            if (CommonCommonEvents.hasEnchantment(shield, event.getEntity().level(), ACEnchantmentRegistry.ARROW_INDUCTING) && arrow.getType() != ACEntityRegistry.SEEKING_ARROW.get()) {
                SeekingArrowEntity seekingArrowEntity = new SeekingArrowEntity(event.getEntity().level(), event.getEntity());
                seekingArrowEntity.copyPosition(arrow);
                seekingArrowEntity.setDeltaMovement(arrow.getDeltaMovement().scale(-0.4D));
                seekingArrowEntity.setYRot(arrow.getYRot() + 180.0F);
                event.getEntity().level().addFreshEntity(seekingArrowEntity);
                arrow.discard();
            }
        }

        if (event.getSource() != null && event.getSource().getDirectEntity() instanceof LivingEntity directSource && directSource.hasEffect(ACEffectRegistry.STUNNED)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void playerAttack(AttackEntityEvent event) {
        if (event.getTarget() instanceof DinosaurEntity && event.getEntity().isPassengerOfSameVehicle(event.getTarget())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void livingTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) {
            return;
        }
        if (livingEntity.hasEffect(ACEffectRegistry.BUBBLED) && livingEntity.isInFluidType()) {
            livingEntity.removeEffect(ACEffectRegistry.BUBBLED);
        }
        if (livingEntity.hasEffect(ACEffectRegistry.DARKNESS_INCARNATE) && livingEntity.tickCount % 5 == 0 && DarknessIncarnateEffect.isInLight(livingEntity, 11)) {
            livingEntity.removeEffect(ACEffectRegistry.DARKNESS_INCARNATE);
        }
        if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(ACItemRegistry.DIVING_HELMET.get()) && (!livingEntity.isEyeInFluid(FluidTags.WATER) || livingEntity.getVehicle() instanceof SubmarineEntity)) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 810, 0, false, false, true));
        }
        if (!livingEntity.level().isClientSide && livingEntity instanceof Mob mob && mob.getTarget() instanceof VallumraptorEntity vallumraptor && vallumraptor.getHideFor() > 0) {
            mob.setTarget(null);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(FinalizeSpawnEvent event) {
        try {
            if (event.getEntity() instanceof Creeper creeper) {
                creeper.targetSelector.addGoal(3, new AvoidEntityGoal<>(creeper, RaycatEntity.class, 10.0F, 1.0D, 1.2D));
            }
            if (event.getEntity() instanceof Drowned drowned && drowned.level().getBiome(drowned.blockPosition()).is(ACBiomeRegistry.ABYSSAL_CHASM)) {
                if (drowned.getItemBySlot(EquipmentSlot.FEET).isEmpty() && drowned.getItemBySlot(EquipmentSlot.LEGS).isEmpty() && drowned.getItemBySlot(EquipmentSlot.CHEST).isEmpty() && drowned.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                    if (drowned.getRandom().nextFloat() < AlexsCaves.COMMON_CONFIG.drownedDivingGearSpawnChance.get()) {
                        drowned.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ACItemRegistry.DIVING_HELMET.get()));
                        drowned.setDropChance(EquipmentSlot.HEAD, 0.5F);
                    }
                    if (drowned.getRandom().nextFloat() < AlexsCaves.COMMON_CONFIG.drownedDivingGearSpawnChance.get()) {
                        drowned.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ACItemRegistry.DIVING_CHESTPLATE.get()));
                        drowned.setDropChance(EquipmentSlot.CHEST, 0.5F);
                    }
                    if (drowned.getRandom().nextFloat() < AlexsCaves.COMMON_CONFIG.drownedDivingGearSpawnChance.get()) {
                        drowned.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ACItemRegistry.DIVING_LEGGINGS.get()));
                        drowned.setDropChance(EquipmentSlot.LEGS, 0.5F);
                    }
                    if (drowned.getRandom().nextFloat() < AlexsCaves.COMMON_CONFIG.drownedDivingGearSpawnChance.get()) {
                        drowned.setItemSlot(EquipmentSlot.FEET, new ItemStack(ACItemRegistry.DIVING_BOOTS.get()));
                        drowned.setDropChance(EquipmentSlot.FEET, 0.5F);
                    }
                }
            }
            if (event.getEntity() instanceof Fox fox) {
                fox.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(fox, GingerbreadManEntity.class, 40, false, false, null));
            }
        } catch (Exception e) {
            AlexsCaves.LOGGER.warn("Tried to add unique behaviors to vanilla mobs and encountered an error");
        }
    }

    @SubscribeEvent
    public void livingRemoveEffect(MobEffectEvent.Remove event) {
        if (event.getEffect().value() instanceof DarknessIncarnateEffect darknessIncarnateEffect) {
            darknessIncarnateEffect.toggleFlight(event.getEntity(), false);
            event.getEntity().level().playSound(null, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), ACSoundRegistry.DARKNESS_INCARNATE_EXIT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        if (event.getEffect().value() instanceof SugarRushEffect) {
            event.getEntity().level().playSound(null, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), ACSoundRegistry.SUGAR_RUSH_EXIT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    @SubscribeEvent
    public void livingAddEffect(MobEffectEvent.Added event) {
        if (event.getEffectInstance().getEffect().value() instanceof DarknessIncarnateEffect) {
            event.getEntity().level().playSound(null, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), ACSoundRegistry.DARKNESS_INCARNATE_ENTER.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        if (event.getEffectInstance().getEffect().value() instanceof SugarRushEffect) {
            event.getEntity().level().playSound(null, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), ACSoundRegistry.SUGAR_RUSH_ENTER.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        // In 1.21, isAddedToWorld() is removed - use isAlive() && level() != null
        if (event.getEntity() instanceof Player player && player.isAlive() && player.level() != null && event.getEffectInstance().getEffect().value() instanceof SugarRushEffect && AlexsCaves.COMMON_CONFIG.sugarRushSlowsTime.get()) {
            float timeBetweenTicksIncrease = 2F;
            SugarRushEffect.enterSlowMotion(player, player.level(), Mth.ceil(event.getEffectInstance().getDuration() * timeBetweenTicksIncrease), timeBetweenTicksIncrease);
        }
    }

    @SubscribeEvent
    public void livingExpireEffect(MobEffectEvent.Expired event) {
        if (event.getEffectInstance().getEffect().value() instanceof DarknessIncarnateEffect darknessIncarnateEffect) {
            darknessIncarnateEffect.toggleFlight(event.getEntity(), false);
            event.getEntity().playSound(ACSoundRegistry.DARKNESS_INCARNATE_EXIT.get());
        }
        if (event.getEntity() instanceof Player player && event.getEffectInstance().getEffect().value() instanceof SugarRushEffect && AlexsCaves.COMMON_CONFIG.sugarRushSlowsTime.get()) {
            SugarRushEffect.leaveSlowMotion(player, player.level());
        }
    }

    @SubscribeEvent
    public void travelToDimension(EntityTravelToDimensionEvent event) {
        if (event.getEntity() instanceof Player player && player.hasEffect(ACEffectRegistry.SUGAR_RUSH)) {
            SugarRushEffect.leaveSlowMotion(player, player.level());
        }
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        CommonCommonEvents.onServerStopping(event.getServer());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        CommonCommonEvents.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public void playerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.isCreative()) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ACTagRegistry.RESTRICTED_BIOME_LOCATORS)) {
                CommonCommonEvents.checkAndDestroyExploitItem(player, EquipmentSlot.MAINHAND);
            }
            if (player.getItemInHand(InteractionHand.OFF_HAND).is(ACTagRegistry.RESTRICTED_BIOME_LOCATORS)) {
                CommonCommonEvents.checkAndDestroyExploitItem(player, EquipmentSlot.OFFHAND);
            }
        }
    }

    @SubscribeEvent
    public void onVillagerTradeSetup(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.CARTOGRAPHER && AlexsCaves.COMMON_CONFIG.cartographersSellCabinMaps.get()) {
            int level = 2;
            List<VillagerTrades.ItemListing> list = event.getTrades().get(level);
            list.add(new VillagerUndergroundCabinMapTrade(5, 10, 6));
            event.getTrades().put(level, list);
        }
    }

    @SubscribeEvent
    public void onWanderingTradeSetup(WandererTradesEvent event) {
        if (AlexsCaves.COMMON_CONFIG.wanderingTradersSellCabinMaps.get()) {
            event.getGenericTrades().add(new VillagerUndergroundCabinMapTrade(8, 1, 10));
        }
    }

    @SubscribeEvent
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player entity = event.getEntity();
        if (entity.level().isClientSide) {
            CommonCommonEvents.onPlayerJoinClient(entity);
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        CommonCommonEvents.onItemUsed(event.getItemStack(), event.getLevel(), event.getEntity(), event.getHand());
    }

    @SubscribeEvent
    public void onUpdateAnvil(AnvilUpdateEvent event) {
        // In 1.21, enchantments are data-driven - use ItemEnchantments API
        ItemEnchantments leftEnchants = event.getLeft().getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments rightEnchants = event.getRight().getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        
        if (event.getLeft().getItem() instanceof AlwaysCombinableOnAnvil && event.getLeft().getItem() == event.getRight().getItem() && !leftEnchants.isEmpty() && !rightEnchants.isEmpty()) {
            ItemEnchantments.Mutable mutableEnchants = new ItemEnchantments.Mutable(leftEnchants);
            int cost = 0;
            
            for (var entry : rightEnchants.entrySet()) {
                Holder<Enchantment> enchantHolder = entry.getKey();
                int rightLevel = entry.getIntValue();
                int leftLevel = leftEnchants.getLevel(enchantHolder);
                int newLevel = leftLevel == rightLevel ? rightLevel + 1 : Math.max(rightLevel, leftLevel);
                
                // Check compatibility with other enchantments
                boolean canCombine = true;
                for (var existingEntry : leftEnchants.entrySet()) {
                    if (!existingEntry.getKey().equals(enchantHolder) && !Enchantment.areCompatible(enchantHolder, existingEntry.getKey())) {
                        canCombine = false;
                        cost++;
                    }
                }
                
                if (canCombine) {
                    Enchantment enchantment = enchantHolder.value();
                    if (newLevel > enchantment.getMaxLevel()) {
                        newLevel = enchantment.getMaxLevel();
                    }
                    mutableEnchants.set(enchantHolder, newLevel);
                    // Use anvil cost from enchantment definition (default to 1 if not available)
                    int enchantCost = enchantment.getAnvilCost();
                    cost += enchantCost * newLevel;
                }
            }
            
            event.setCost(cost);
            ItemStack copy = event.getLeft().copy();
            copy.set(DataComponents.ENCHANTMENTS, mutableEnchants.toImmutable());
            event.setOutput(copy);
        }
    }
}
