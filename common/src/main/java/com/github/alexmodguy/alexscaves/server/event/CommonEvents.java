package com.github.alexmodguy.alexscaves.server.event;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.event.ClientEvents;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.enchantment.ACEnchantmentRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACFrogRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACSpawnPlacementTypes;
import com.github.alexmodguy.alexscaves.server.entity.item.SubmarineEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.*;
import com.github.alexmodguy.alexscaves.server.entity.util.EntityDropChanceAccessor;
import com.github.alexmodguy.alexscaves.server.entity.util.FlyingMount;
import com.github.alexmodguy.alexscaves.server.entity.util.MagneticEntityAccessor;
import com.github.alexmodguy.alexscaves.server.entity.util.WatcherPossessionAccessor;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.item.ExtinctionSpearItem;
import com.github.alexmodguy.alexscaves.server.level.biome.ACBiomeRarity;
import com.github.alexmodguy.alexscaves.server.level.biome.ACBiomeRegistry;
import com.github.alexmodguy.alexscaves.server.level.biome.BiomeSourceAccessor;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACTagRegistry;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.github.alexmodguy.alexscaves.server.potion.DarknessIncarnateEffect;
import com.github.alexmodguy.alexscaves.server.potion.SugarRushEffect;
import com.github.alexthe666.citadel.server.tick.ServerTickRateTracker;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonEvents {

    public static final String ALEXTHE666_UUID = "71363abe-fd03-49c9-940d-aae8b8209b7c";
    public static final String NOONYEYZ_UUID = "4a463319-625c-4b86-a4e7-8b700f023a60";

    public static void init() {
    }

    // TODO implement on fabric
    public static EntityDimensions onEntityResize(Entity entity, EntityDimensions oldSize, EntityDimensions newSize) {
        // In 1.21, isAddedToWorld() is removed - use isAlive() && level() != null instead
        if (entity.isAlive() && entity.level() != null && entity instanceof MagneticEntityAccessor magnet && entity.getEntityData().isDirty()) {
            Direction dir = magnet.getMagneticAttachmentFace();
            float defaultHeight = oldSize.height();
            float defaultEyeHeight = entity.getEyeHeight();
            if (dir == Direction.DOWN && entity instanceof Player && entity.getPose() == Pose.STANDING) {
                // In 1.21, setNewSize only takes EntityDimensions - eye height is part of dimensions
                return newSize;
            } else if (dir == Direction.UP) {
                // Use withEyeHeight to create new dimensions with adjusted eye height
                return newSize.withEyeHeight(defaultHeight - defaultEyeHeight);
            } else if (dir.getAxis() != Direction.Axis.Y) {
                return newSize.withEyeHeight(0.0F);
            }
        }
        return null;
    }

    public static void onLivingDeath(LivingEntity entity, DamageSource source) {
        if (entity instanceof Player) {
            String stringUUID = entity.getUUID().toString();
            if (stringUUID.equals(ALEXTHE666_UUID)) {
                entity.spawnAtLocation(new ItemStack(ACItemRegistry.GREEN_SOYLENT.get(), 1 + entity.getRandom().nextInt(9)));
            }
            if (stringUUID.equals(NOONYEYZ_UUID)) {
                entity.spawnAtLocation(new ItemStack(ACItemRegistry.STINKY_FISH.get(), 1));
            }
        }

        if (source == null) {
            return;
        }

        if (entity.getType() == EntityType.MAGMA_CUBE && source.getEntity() instanceof Frog frog) {
            // In 1.21, getVariant() returns Holder<FrogVariant>, use is() to compare
            if (frog.getVariant().is(ACFrogRegistry.PRIMORDIAL)) {
                entity.spawnAtLocation(new ItemStack(ACBlockRegistry.CARMINE_FROGLIGHT.get()));
            }
        }

        if (!entity.level().isClientSide && entity instanceof Mob mob && source.getDirectEntity() instanceof LivingEntity directSource && directSource.getItemInHand(InteractionHand.MAIN_HAND).is(ACItemRegistry.PRIMITIVE_CLUB.get())) {
            // In 1.21, use helper method for enchantment check with data-driven enchantments
            if (hasEnchantment(directSource.getItemInHand(InteractionHand.MAIN_HAND), entity.level(), ACEnchantmentRegistry.BONKING) && entity.level().random.nextFloat() < 0.33F) {
                Creeper fakeCreeperForSkullDrop = EntityType.CREEPER.create(mob.level());
                if (fakeCreeperForSkullDrop != null) {
                    if (entity.level() instanceof ServerLevel serverLevel) {
                        LightningBolt fakeThunder = EntityType.LIGHTNING_BOLT.create(serverLevel);
                        if (fakeThunder != null) {
                            fakeThunder.setVisualOnly(true);
                            fakeCreeperForSkullDrop.thunderHit(serverLevel, fakeThunder);
                        }
                    }
                    DamageSource fakeCreeperDamage = mob.level().damageSources().mobAttack(fakeCreeperForSkullDrop);
                    HashMap<EquipmentSlot, Float> prevLootDropChances = new HashMap<>();
                    EntityDropChanceAccessor dropChanceAccessor = (EntityDropChanceAccessor) mob;
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        prevLootDropChances.put(slot, dropChanceAccessor.ac_getEquipmentDropChance(slot));
                        dropChanceAccessor.ac_setDropChance(slot, 0.0F);
                    }

                    if (mob.level() instanceof ServerLevel serverLevel2) {
                        dropChanceAccessor.ac_dropCustomDeathLoot(serverLevel2, fakeCreeperDamage, false);
                    }

                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        dropChanceAccessor.ac_setDropChance(slot, prevLootDropChances.get(slot));
                    }
                }
            }
        }
    }

    public static int onLivingHurt(LivingEntity entity, DamageSource source) {
        if (entity.isPassenger() && entity instanceof FlyingMount && (source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALL) || source.is(DamageTypes.FLY_INTO_WALL))) {
            return 0;
        }

        if (entity instanceof WatcherPossessionAccessor possessed && possessed.isPossessedByWatcher() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !(source.getEntity() instanceof WatcherEntity)) {
            return 0;
        }

        if (entity instanceof Player player && player.getUseItem().is(ACItemRegistry.EXTINCTION_SPEAR.get()) && ExtinctionSpearItem.killGrottoGhostsFor(player, true)) {
            player.playSound(SoundEvents.SHIELD_BLOCK);
            return 0;
        }

        if (entity instanceof Player player && source.is(DamageTypes.FALL) && player.getItemBySlot(EquipmentSlot.FEET).is(ACItemRegistry.RAINBOUNCE_BOOTS.get())) {
            player.fallDistance = 0.0F;
            return 0;
        }
        return -1;
    }

    /**
     * Helper method to check if an item has an enchantment (1.21 data-driven enchantments)
     */
    public static boolean hasEnchantment(ItemStack stack, Level level, ResourceKey<Enchantment> enchantmentKey) {
        var holder = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(enchantmentKey);
        // noinspection ConstantConditions
        return holder.isPresent() && stack.has(DataComponents.ENCHANTMENTS) && stack.get(DataComponents.ENCHANTMENTS).getLevel(holder.get()) > 0;
    }

    public static void onServerStopping(MinecraftServer server) {
        if (AlexsCaves.COMMON_CONFIG.sugarRushSlowsTime.get()) {
            ServerTickRateTracker tracker = ServerTickRateTracker.getForServer(server);
            tracker.tickRateModifierList.clear();
        }
    }

    public static void onServerStarting(MinecraftServer server) {
        ACBiomeRarity.init();
        //moved from citadel
        RegistryAccess registryAccess = server.registryAccess();
        Registry<Biome> allBiomes = registryAccess.registryOrThrow(Registries.BIOME);
        Registry<LevelStem> levelStems = registryAccess.registryOrThrow(Registries.LEVEL_STEM);
        Map<ResourceKey<Biome>, Holder<Biome>> biomeMap = new HashMap<>();
        for (ResourceKey<Biome> biomeResourceKey : allBiomes.registryKeySet()) {
            Optional<Holder.Reference<Biome>> holderOptional = allBiomes.getHolder(biomeResourceKey);
            holderOptional.ifPresent(biomeHolder -> biomeMap.put(biomeResourceKey, biomeHolder));
        }
        for (ResourceKey<LevelStem> levelStemResourceKey : levelStems.registryKeySet()) {
            Optional<Holder.Reference<LevelStem>> holderOptional = levelStems.getHolder(levelStemResourceKey);
            if (holderOptional.isPresent() && holderOptional.get().value().generator().getBiomeSource() instanceof BiomeSourceAccessor expandedBiomeSource) {
                expandedBiomeSource.setResourceKeyMap(biomeMap);
                if (levelStemResourceKey.equals(LevelStem.OVERWORLD)) {
                    ImmutableSet.Builder<Holder<Biome>> biomeHolders = ImmutableSet.builder();
                    for (ResourceKey<Biome> biomeResourceKey : ACBiomeRegistry.ALEXS_CAVES_BIOMES) {
                        allBiomes.getHolder(biomeResourceKey).ifPresent(biomeHolders::add);
                    }
                    expandedBiomeSource.expandBiomesWith(biomeHolders.build());
                }
            }
        }
    }

    public static void checkAndDestroyExploitItem(Player player, EquipmentSlot slot) {
        ItemStack itemInHand = player.getItemBySlot(slot);
        if (itemInHand.is(ACTagRegistry.RESTRICTED_BIOME_LOCATORS)) {
            // In 1.21, use DataComponents API instead of getTag()
            CustomData customData = itemInHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag tag = customData.copyTag();
            if (!tag.isEmpty()) {
                if (itemTagContainsAC(tag, "BiomeKey", false) || itemTagContainsAC(tag, "Structure", true) || itemTagContainsAC(tag, "structurecompass:structureName", true) || itemTagContainsAC(tag, "StructureKey", true)) {
                    itemInHand.shrink(1);
                    // In 1.21, broadcastBreakEvent is replaced with onEquippedItemBroken
                    player.onEquippedItemBroken(itemInHand.getItem(), slot);
                    player.playSound(ACSoundRegistry.DISAPPOINTMENT.get());
                    if (!player.level().isClientSide) {
                        player.displayClientMessage(Component.translatable("item.alexscaves.natures_compass_warning"), true);
                    }
                }
            }
        }
    }
    private static boolean itemTagContainsAC(CompoundTag tag, String tagID, boolean allowUndergroundCabin) {
        if (tag.contains(tagID)) {
            String resourceLocation = tag.getString(tagID);
            return resourceLocation.contains("alexscaves:") && (!allowUndergroundCabin || !resourceLocation.contains("underground_cabin"));
        }
        return false;
    }

    public static void onItemUsed(ItemStack stack, Level level, Player player, InteractionHand hand) {
        if (stack.getItem() == Items.GLASS_BOTTLE) {
            BlockHitResult raytraceresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (raytraceresult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = raytraceresult.getBlockPos();
                if (level.mayInteract(player, blockpos)) {
                    // TODO when fluids
//                    if (level.getFluidState(blockpos).getFluidType() == ACFluidRegistry.PURPLE_SODA_FLUID_TYPE.get()) {
//                        player.gameEvent(GameEvent.ITEM_INTERACT_START);
//                        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
//                        player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));
//                        if (!player.addItem(new ItemStack(ACItemRegistry.PURPLE_SODA_BOTTLE.get()))) {
//                            player.spawnAtLocation(new ItemStack(ACItemRegistry.PURPLE_SODA_BOTTLE.get()));
//                        }
//                        player.swing(hand);
//                        if (!player.isCreative()) {
//                            stack.shrink(1);
//                        }
//                    }
                }
            }
        }
    }

    // Einstein: This probably should actually just be Item.getPlayerPOVHitResult, but it's protected
    // (ender) *mmmm* accessors

    private static BlockHitResult getPlayerPOVHitResult(Level level, Player player, ClipContext.Fluid fluid) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3 = player.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        // In 1.21, use Attributes.BLOCK_INTERACTION_RANGE instead of getBlockReach()
        double d0 = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE).getValue();
        Vec3 vec31 = vec3.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, fluid, player));
    }

    public static void initializeAttributes(EntityAttributeRegistry event) {
        event.register(ACEntityRegistry.TELETOR.get(), TeletorEntity.createAttributes().build());
        event.register(ACEntityRegistry.MAGNETRON.get(), MagnetronEntity.createAttributes().build());
        event.register(ACEntityRegistry.BOUNDROID.get(), BoundroidEntity.createAttributes().build());
        event.register(ACEntityRegistry.BOUNDROID_WINCH.get(), BoundroidEntity.createAttributes().build());
        event.register(ACEntityRegistry.FERROUSLIME.get(), FerrouslimeEntity.createAttributes().build());
        event.register(ACEntityRegistry.NOTOR.get(), NotorEntity.createAttributes().build());
        event.register(ACEntityRegistry.SUBTERRANODON.get(), SubterranodonEntity.createAttributes().build());
        event.register(ACEntityRegistry.VALLUMRAPTOR.get(), VallumraptorEntity.createAttributes().build());
        event.register(ACEntityRegistry.GROTTOCERATOPS.get(), GrottoceratopsEntity.createAttributes().build());
        event.register(ACEntityRegistry.TRILOCARIS.get(), TrilocarisEntity.createAttributes().build());
        event.register(ACEntityRegistry.TREMORSAURUS.get(), TremorsaurusEntity.createAttributes().build());
        event.register(ACEntityRegistry.RELICHEIRUS.get(), RelicheirusEntity.createAttributes().build());
        event.register(ACEntityRegistry.LUXTRUCTOSAURUS.get(), LuxtructosaurusEntity.createAttributes().build());
        event.register(ACEntityRegistry.ATLATITAN.get(), AtlatitanEntity.createAttributes().build());
        event.register(ACEntityRegistry.NUCLEEPER.get(), NucleeperEntity.createAttributes().build());
        event.register(ACEntityRegistry.RADGILL.get(), RadgillEntity.createAttributes().build());
        event.register(ACEntityRegistry.BRAINIAC.get(), BrainiacEntity.createAttributes().build());
        event.register(ACEntityRegistry.GAMMAROACH.get(), GammaroachEntity.createAttributes().build());
        event.register(ACEntityRegistry.RAYCAT.get(), RaycatEntity.createAttributes().build());
        event.register(ACEntityRegistry.TREMORZILLA.get(), TremorzillaEntity.createAttributes().build());
        event.register(ACEntityRegistry.LANTERNFISH.get(), LanternfishEntity.createAttributes().build());
        event.register(ACEntityRegistry.SEA_PIG.get(), SeaPigEntity.createAttributes().build());
        event.register(ACEntityRegistry.HULLBREAKER.get(), HullbreakerEntity.createAttributes().build());
        event.register(ACEntityRegistry.GOSSAMER_WORM.get(), GossamerWormEntity.createAttributes().build());
        event.register(ACEntityRegistry.TRIPODFISH.get(), TripodfishEntity.createAttributes().build());
        event.register(ACEntityRegistry.DEEP_ONE.get(), DeepOneEntity.createAttributes().build());
        event.register(ACEntityRegistry.DEEP_ONE_KNIGHT.get(), DeepOneKnightEntity.createAttributes().build());
        event.register(ACEntityRegistry.DEEP_ONE_MAGE.get(), DeepOneMageEntity.createAttributes().build());
        event.register(ACEntityRegistry.MINE_GUARDIAN.get(), MineGuardianEntity.createAttributes().build());
        event.register(ACEntityRegistry.GLOOMOTH.get(), GloomothEntity.createAttributes().build());
        event.register(ACEntityRegistry.UNDERZEALOT.get(), UnderzealotEntity.createAttributes().build());
        event.register(ACEntityRegistry.WATCHER.get(), WatcherEntity.createAttributes().build());
        event.register(ACEntityRegistry.CORRODENT.get(), CorrodentEntity.createAttributes().build());
        event.register(ACEntityRegistry.VESPER.get(), VesperEntity.createAttributes().build());
        event.register(ACEntityRegistry.FORSAKEN.get(), ForsakenEntity.createAttributes().build());
        event.register(ACEntityRegistry.SWEETISH_FISH.get(), SweetishFishEntity.createAttributes().build());
        event.register(ACEntityRegistry.CANIAC.get(), CaniacEntity.createAttributes().build());
        event.register(ACEntityRegistry.GUMBEEPER.get(), GumbeeperEntity.createAttributes().build());
        event.register(ACEntityRegistry.CANDICORN.get(), CandicornEntity.createAttributes().build());
        event.register(ACEntityRegistry.GUM_WORM.get(), GumWormEntity.createAttributes().build());
        event.register(ACEntityRegistry.CARAMEL_CUBE.get(), CaramelCubeEntity.createAttributes().build());
        event.register(ACEntityRegistry.GUMMY_BEAR.get(), GummyBearEntity.createAttributes().build());
        event.register(ACEntityRegistry.LICOWITCH.get(), LicowitchEntity.createAttributes().build());
        event.register(ACEntityRegistry.GINGERBREAD_MAN.get(), GingerbreadManEntity.createAttributes().build());
    }

    public static void onEntityTick(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        boolean isClientSide = livingEntity.level().isClientSide;
        if (isClientSide) {
            // TODO make sure this call isn't gonna crash servers
            ClientEvents.onClientLivingTick(livingEntity);
        }

        // TODO when fluids
        if (livingEntity.hasEffect(ACEffectRegistry.BUBBLED) /*&& livingEntity.isInFluidType()*/) {
            livingEntity.removeEffect(ACEffectRegistry.BUBBLED);
        }

        if (livingEntity.hasEffect(ACEffectRegistry.DARKNESS_INCARNATE) && livingEntity.tickCount % 5 == 0 && DarknessIncarnateEffect.isInLight(livingEntity, 11)) {
            livingEntity.removeEffect(ACEffectRegistry.DARKNESS_INCARNATE);
        }

        if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(ACItemRegistry.DIVING_HELMET.get()) && (!livingEntity.isEyeInFluid(FluidTags.WATER) || livingEntity.getVehicle() instanceof SubmarineEntity)) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 810, 0, false, false, true));
        }

        if (!isClientSide && livingEntity instanceof Mob mob && mob.getTarget() instanceof VallumraptorEntity vallumraptor && vallumraptor.getHideFor() > 0) {
            mob.setTarget(null);
        }
    }

    public static void travelToDimension(Entity entity) {
        if (entity instanceof Player player && player.hasEffect(ACEffectRegistry.SUGAR_RUSH)) {
            SugarRushEffect.leaveSlowMotion(player, player.level());
        }
    }

    public static void onPlayerTick(Player player) {
        if (!player.isCreative()) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ACTagRegistry.RESTRICTED_BIOME_LOCATORS)) {
                checkAndDestroyExploitItem(player, EquipmentSlot.MAINHAND);
            }
            if (player.getItemInHand(InteractionHand.OFF_HAND).is(ACTagRegistry.RESTRICTED_BIOME_LOCATORS)) {
                checkAndDestroyExploitItem(player, EquipmentSlot.OFFHAND);
            }
        }
    }

    public static void registerSpawnPlacements(SpawnPlacementRegistry event) {
        // Custom spawn placement types for acid and soda fluids are defined in ACSpawnPlacementTypes.
        // Magnetic Caves monsters use checkMagneticCaveSpawnRules to allow spawning in lit areas (Tesla Bulbs etc.)
        event.register(ACEntityRegistry.TELETOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TeletorEntity::checkMagneticCaveSpawnRules);
        event.register(ACEntityRegistry.MAGNETRON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagnetronEntity::checkMagneticCaveSpawnRules);
        event.register(ACEntityRegistry.BOUNDROID.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BoundroidEntity::checkMagneticCaveSpawnRules);
        event.register(ACEntityRegistry.FERROUSLIME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FerrouslimeEntity::checkMagneticCaveSpawnRules);
        event.register(ACEntityRegistry.NOTOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NotorEntity::checkNotorSpawnRules);
        event.register(ACEntityRegistry.SUBTERRANODON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SubterranodonEntity::checkSubterranodonSpawnRules);
        event.register(ACEntityRegistry.VALLUMRAPTOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VallumraptorEntity::checkPrehistoricSpawnRules);
        event.register(ACEntityRegistry.GROTTOCERATOPS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GrottoceratopsEntity::checkPrehistoricSpawnRules);
        event.register(ACEntityRegistry.TRILOCARIS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TrilocarisEntity::checkTrilocarisSpawnRules);
        event.register(ACEntityRegistry.TREMORSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TremorsaurusEntity::checkPrehistoricSpawnRules);
        event.register(ACEntityRegistry.RELICHEIRUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RelicheirusEntity::checkPrehistoricSpawnRules);
        event.register(ACEntityRegistry.LUXTRUCTOSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LuxtructosaurusEntity::checkPrehistoricSpawnRules);
        event.register(ACEntityRegistry.ATLATITAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AtlatitanEntity::checkPrehistoricPostBossSpawnRules);
        event.register(ACEntityRegistry.NUCLEEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NucleeperEntity::checkMonsterSpawnRules);
        event.register(ACEntityRegistry.RADGILL.get(), ACSpawnPlacementTypes.IN_ACID, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RadgillEntity::checkRadgillSpawnRules);
        event.register(ACEntityRegistry.BRAINIAC.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BrainiacEntity::checkMonsterSpawnRules);
        event.register(ACEntityRegistry.GAMMAROACH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GammaroachEntity::checkGammaroachSpawnRules);
        event.register(ACEntityRegistry.RAYCAT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RaycatEntity::checkRaycatSpawnRules);
        event.register(ACEntityRegistry.LANTERNFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LanternfishEntity::checkLanternfishSpawnRules);
        event.register(ACEntityRegistry.SEA_PIG.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SeaPigEntity::checkSeaPigSpawnRules);
        event.register(ACEntityRegistry.HULLBREAKER.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HullbreakerEntity::checkHullbreakerSpawnRules);
        event.register(ACEntityRegistry.GOSSAMER_WORM.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GossamerWormEntity::checkGossamerWormSpawnRules);
        event.register(ACEntityRegistry.TRIPODFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TripodfishEntity::checkTripodfishSpawnRules);
        event.register(ACEntityRegistry.DEEP_ONE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeepOneBaseEntity::checkDeepOneSpawnRules);
        event.register(ACEntityRegistry.DEEP_ONE_KNIGHT.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeepOneBaseEntity::checkDeepOneSpawnRules);
        event.register(ACEntityRegistry.DEEP_ONE_MAGE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeepOneBaseEntity::checkDeepOneSpawnRules);
        event.register(ACEntityRegistry.MINE_GUARDIAN.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MineGuardianEntity::checkMineGuardianSpawnRules);
        event.register(ACEntityRegistry.GLOOMOTH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GloomothEntity::checkGloomothSpawnRules);
        event.register(ACEntityRegistry.UNDERZEALOT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UnderzealotEntity::checkUnderzealotSpawnRules);
        event.register(ACEntityRegistry.WATCHER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WatcherEntity::checkWatcherSpawnRules);
        event.register(ACEntityRegistry.CORRODENT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorrodentEntity::checkCorrodentSpawnRules);
        event.register(ACEntityRegistry.VESPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VesperEntity::checkVesperSpawnRules);
        event.register(ACEntityRegistry.FORSAKEN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ForsakenEntity::checkForsakenSpawnRules);
        event.register(ACEntityRegistry.SWEETISH_FISH.get(), ACSpawnPlacementTypes.IN_PURPLE_SODA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SweetishFishEntity::checkSweetishFishSpawnRules);
        event.register(ACEntityRegistry.CANIAC.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CaniacEntity::checkCaniacSpawnRules);
        event.register(ACEntityRegistry.GUMBEEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GumbeeperEntity::checkGumbeeperSpawnRules);
        event.register(ACEntityRegistry.CANDICORN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CandicornEntity::checkCandicornSpawnRules);
        event.register(ACEntityRegistry.GUM_WORM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GumWormEntity::checkGumWormSpawnRules);
        event.register(ACEntityRegistry.CARAMEL_CUBE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CaramelCubeEntity::checkCaramelCubeSpawnRules);
        event.register(ACEntityRegistry.GUMMY_BEAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GummyBearEntity::checkGummyBearSpawnRules);
        event.register(ACEntityRegistry.LICOWITCH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LicowitchEntity::checkLicowitchSpawnRules);
        event.register(ACEntityRegistry.GINGERBREAD_MAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GingerbreadManEntity::checkGingerbreadManSpawnRules);
    }

    public interface EntityAttributeRegistry {

        void register(EntityType<? extends LivingEntity> type, AttributeSupplier builder);
    }

    public interface SpawnPlacementRegistry {

        <T extends Mob> void register(EntityType<T> type, SpawnPlacementType placementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate);
    }
}
