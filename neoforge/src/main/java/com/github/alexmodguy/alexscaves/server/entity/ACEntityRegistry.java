package com.github.alexmodguy.alexscaves.server.entity;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.entity.item.*;
import com.github.alexmodguy.alexscaves.server.entity.living.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = AlexsCaves.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ACEntityRegistry {


    public static final DeferredRegister<EntityType<?>> DEF_REG = DeferredRegister.create(Registries.ENTITY_TYPE, AlexsCaves.MOD_ID);

    // Custom MobCategory values registered via enum extension in enumextensions.json
    // These preserve the 1.20 spawn behavior with custom spawn limits
    public static final MobCategory CAVE_CREATURE = MobCategory.valueOf("ALEXSCAVES_CAVE_CREATURE");
    public static final MobCategory DEEP_SEA_CREATURE = MobCategory.valueOf("ALEXSCAVES_DEEP_SEA_CREATURE");

    private static <T extends Entity> RegHolder<EntityType<?>, EntityType<T>> register(String name, Supplier<EntityType<T>> entityTypeSupplier) {
        return Services.REGISTRY_HELPER.registerEntityType(name, entityTypeSupplier);
    }

    public static final RegHolder<EntityType<?>, EntityType<AlexsCavesBoatEntity>> BOAT = register("boat", () -> (EntityType) EntityType.Builder.of(AlexsCavesBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("ac_boat"));
    public static final RegHolder<EntityType<?>, EntityType<AlexsCavesChestBoatEntity>> CHEST_BOAT = register("chest_boat", () -> (EntityType) EntityType.Builder.of(AlexsCavesChestBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("ac_chest_boat"));
    public static final RegHolder<EntityType<?>, EntityType<MovingMetalBlockEntity>> MOVING_METAL_BLOCK = register("moving_metal_block", () -> (EntityType) EntityType.Builder.of(MovingMetalBlockEntity::new, MobCategory.MISC).sized(0.99F, 0.99F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("moving_metal_block"));
    public static final RegHolder<EntityType<?>, EntityType<TeletorEntity>> TELETOR = register("teletor", () -> (EntityType) EntityType.Builder.of(TeletorEntity::new, MobCategory.MONSTER).sized(0.99F, 1.99F).build("teletor"));
    public static final RegHolder<EntityType<?>, EntityType<MagneticWeaponEntity>> MAGNETIC_WEAPON = register("magnetic_weapon", () -> (EntityType) EntityType.Builder.of(MagneticWeaponEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).clientTrackingRange(20).build("magnetic_weapon"));
    public static final RegHolder<EntityType<?>, EntityType<MagnetronEntity>> MAGNETRON = register("magnetron", () -> (EntityType) EntityType.Builder.of(MagnetronEntity::new, MobCategory.MONSTER).sized(0.8F, 2.0F).build("magnetron"));
    public static final RegHolder<EntityType<?>, EntityType<BoundroidEntity>> BOUNDROID = register("boundroid", () -> (EntityType) EntityType.Builder.of(BoundroidEntity::new, MobCategory.MONSTER).sized(1.4F, 0.75F).build("boundroid"));
    public static final RegHolder<EntityType<?>, EntityType<BoundroidWinchEntity>> BOUNDROID_WINCH = register("boundroid_winch", () -> (EntityType) EntityType.Builder.of(BoundroidWinchEntity::new, MobCategory.MONSTER).sized(0.8F, 1.4F).build("boundroid_winch"));
    public static final RegHolder<EntityType<?>, EntityType<FerrouslimeEntity>> FERROUSLIME = register("ferrouslime", () -> (EntityType) EntityType.Builder.of(FerrouslimeEntity::new, MobCategory.MONSTER).sized(0.85F, 0.85F).build("ferrouslime"));
    public static final RegHolder<EntityType<?>, EntityType<NotorEntity>> NOTOR = register("notor", () -> (EntityType) EntityType.Builder.of(NotorEntity::new, MobCategory.AMBIENT).sized(0.5F, 0.65F).build("notor"));
    public static final RegHolder<EntityType<?>, EntityType<QuarrySmasherEntity>> QUARRY_SMASHER = register("quarry_smasher", () -> (EntityType) EntityType.Builder.of(QuarrySmasherEntity::new, MobCategory.MISC).sized(0.9F, 1.2F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("quarry_smasher"));
    public static final RegHolder<EntityType<?>, EntityType<SeekingArrowEntity>> SEEKING_ARROW = register("seeking_arrow", () -> (EntityType) EntityType.Builder.of(SeekingArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("seeking_arrow"));
    public static final RegHolder<EntityType<?>, EntityType<SubterranodonEntity>> SUBTERRANODON = register("subterranodon", () -> (EntityType) EntityType.Builder.of(SubterranodonEntity::new, CAVE_CREATURE).sized(1.75F, 1.2F).setTrackingRange(12).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("subterranodon"));
    public static final RegHolder<EntityType<?>, EntityType<VallumraptorEntity>> VALLUMRAPTOR = register("vallumraptor", () -> (EntityType) EntityType.Builder.of(VallumraptorEntity::new, CAVE_CREATURE).sized(0.8F, 1.5F).setTrackingRange(8).build("vallumraptor"));
    public static final RegHolder<EntityType<?>, EntityType<GrottoceratopsEntity>> GROTTOCERATOPS = register("grottoceratops", () -> (EntityType) EntityType.Builder.of(GrottoceratopsEntity::new, CAVE_CREATURE).sized(2.3F, 2.5F).setTrackingRange(8).build("grottoceratops"));
    public static final RegHolder<EntityType<?>, EntityType<TrilocarisEntity>> TRILOCARIS = register("trilocaris", () -> (EntityType) EntityType.Builder.of(TrilocarisEntity::new, MobCategory.WATER_AMBIENT).sized(0.9F, 0.4F).build("trilocaris"));
    public static final RegHolder<EntityType<?>, EntityType<TremorsaurusEntity>> TREMORSAURUS = register("tremorsaurus", () -> (EntityType) EntityType.Builder.of(TremorsaurusEntity::new, CAVE_CREATURE).sized(2.5F, 3.85F).setTrackingRange(8).build("tremorsaurus"));
    public static final RegHolder<EntityType<?>, EntityType<RelicheirusEntity>> RELICHEIRUS = register("relicheirus", () -> (EntityType) EntityType.Builder.of(RelicheirusEntity::new, CAVE_CREATURE).sized(2.65F, 5.9F).setTrackingRange(9).build("relicheirus"));
    public static final RegHolder<EntityType<?>, EntityType<LuxtructosaurusEntity>> LUXTRUCTOSAURUS = register("luxtructosaurus", () -> (EntityType) EntityType.Builder.of(LuxtructosaurusEntity::new, MobCategory.MONSTER).sized(6.0F, 8.5F).setTrackingRange(12).fireImmune().build("luxtructosaurus"));
    public static final RegHolder<EntityType<?>, EntityType<TephraEntity>> TEPHRA = register("tephra", () -> (EntityType) EntityType.Builder.of(TephraEntity::new, MobCategory.MISC).sized(0.6F, 0.6F).fireImmune().setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("tephra"));
    public static final RegHolder<EntityType<?>, EntityType<AtlatitanEntity>> ATLATITAN = register("atlatitan", () -> (EntityType) EntityType.Builder.of(AtlatitanEntity::new, CAVE_CREATURE).sized(5.0F, 8.0F).setTrackingRange(11).build("atlatitan"));
    public static final RegHolder<EntityType<?>, EntityType<FallingTreeBlockEntity>> FALLING_TREE_BLOCK = register("falling_tree_block", () -> (EntityType) EntityType.Builder.of(FallingTreeBlockEntity::new, MobCategory.MISC).sized(0.99F, 0.99F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("falling_tree_block"));
    public static final RegHolder<EntityType<?>, EntityType<CrushedBlockEntity>> CRUSHED_BLOCK = register("crushed_block", () -> (EntityType) EntityType.Builder.of(CrushedBlockEntity::new, MobCategory.MISC).sized(0.99F, 0.99F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("crushed_block"));
    public static final RegHolder<EntityType<?>, EntityType<LimestoneSpearEntity>> LIMESTONE_SPEAR = register("limestone_spear", () -> (EntityType) EntityType.Builder.of(LimestoneSpearEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("limestone_spear"));
    public static final RegHolder<EntityType<?>, EntityType<ExtinctionSpearEntity>> EXTINCTION_SPEAR = register("extinction_spear", () -> (EntityType) EntityType.Builder.of(ExtinctionSpearEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).fireImmune().build("extinction_spear"));
    public static final RegHolder<EntityType<?>, EntityType<DinosaurSpiritEntity>> DINOSAUR_SPIRIT = register("dinosaur_spirit", () -> (EntityType) EntityType.Builder.of(DinosaurSpiritEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).fireImmune().build("dinosaur_spirit"));
    public static final RegHolder<EntityType<?>, EntityType<NuclearExplosionEntity>> NUCLEAR_EXPLOSION = register("nuclear_explosion", () -> (EntityType) EntityType.Builder.of(NuclearExplosionEntity::new, MobCategory.MISC).sized(0.99F, 0.99F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("nuclear_explosion"));
    public static final RegHolder<EntityType<?>, EntityType<NuclearBombEntity>> NUCLEAR_BOMB = register("nuclear_bomb", () -> (EntityType) EntityType.Builder.of(NuclearBombEntity::new, MobCategory.MISC).sized(0.98F, 0.98F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("nuclear_bomb"));
    public static final RegHolder<EntityType<?>, EntityType<NucleeperEntity>> NUCLEEPER = register("nucleeper", () -> (EntityType) EntityType.Builder.of(NucleeperEntity::new, MobCategory.MONSTER).sized(0.98F, 3.95F).build("nucleeper"));
    public static final RegHolder<EntityType<?>, EntityType<RadgillEntity>> RADGILL = register("radgill", () -> (EntityType) EntityType.Builder.of(RadgillEntity::new, MobCategory.WATER_AMBIENT).sized(0.9F, 0.6F).build("radgill"));
    public static final RegHolder<EntityType<?>, EntityType<BrainiacEntity>> BRAINIAC = register("brainiac", () -> (EntityType) EntityType.Builder.of(BrainiacEntity::new, MobCategory.MONSTER).sized(1.3F, 2.5F).build("brainiac"));
    public static final RegHolder<EntityType<?>, EntityType<ThrownWasteDrumEntity>> THROWN_WASTE_DRUM = register("thrown_waste_drum", () -> (EntityType) EntityType.Builder.of(ThrownWasteDrumEntity::new, MobCategory.MISC).sized(0.98F, 0.98F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("thrown_waste_drum"));
    public static final RegHolder<EntityType<?>, EntityType<GammaroachEntity>> GAMMAROACH = register("gammaroach", () -> (EntityType) EntityType.Builder.of(GammaroachEntity::new, MobCategory.AMBIENT).sized(1.25F, 0.9F).build("gammaroach"));
    public static final RegHolder<EntityType<?>, EntityType<RaycatEntity>> RAYCAT = register("raycat", () -> (EntityType) EntityType.Builder.of(RaycatEntity::new, CAVE_CREATURE).sized(0.85F, 0.6F).build("raycat"));
    public static final RegHolder<EntityType<?>, EntityType<CinderBrickEntity>> CINDER_BRICK = register("cinder_brick", () -> (EntityType) EntityType.Builder.of(CinderBrickEntity::new, MobCategory.MISC).sized(0.4F, 0.4F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("cinder_brick"));
    public static final RegHolder<EntityType<?>, EntityType<TremorzillaEntity>> TREMORZILLA = register("tremorzilla", () -> (EntityType) EntityType.Builder.of(TremorzillaEntity::new, CAVE_CREATURE).sized(4.5F, 11F).setTrackingRange(11).fireImmune().build("tremorzilla"));
    public static final RegHolder<EntityType<?>, EntityType<LanternfishEntity>> LANTERNFISH = register("lanternfish", () -> (EntityType) EntityType.Builder.of(LanternfishEntity::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.4F).build("lanternfish"));
    public static final RegHolder<EntityType<?>, EntityType<SeaPigEntity>> SEA_PIG = register("sea_pig", () -> (EntityType) EntityType.Builder.of(SeaPigEntity::new, DEEP_SEA_CREATURE).sized(0.5F, 0.65F).build("sea_pig"));
    public static final RegHolder<EntityType<?>, EntityType<SubmarineEntity>> SUBMARINE = register("submarine", () -> (EntityType) EntityType.Builder.of(SubmarineEntity::new, MobCategory.MISC).sized(3.5F, 3.3F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("submarine"));
    public static final RegHolder<EntityType<?>, EntityType<HullbreakerEntity>> HULLBREAKER = register("hullbreaker", () -> (EntityType) EntityType.Builder.of(HullbreakerEntity::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(4.65F, 4.5F).setShouldReceiveVelocityUpdates(true).clientTrackingRange(20).build("hullbreaker"));
    public static final RegHolder<EntityType<?>, EntityType<GossamerWormEntity>> GOSSAMER_WORM = register("gossamer_worm", () -> (EntityType) EntityType.Builder.of(GossamerWormEntity::new, DEEP_SEA_CREATURE).sized(1.15F, 0.5F).build("gossamer_worm"));
    public static final RegHolder<EntityType<?>, EntityType<TripodfishEntity>> TRIPODFISH = register("tripodfish", () -> (EntityType) EntityType.Builder.of(TripodfishEntity::new, DEEP_SEA_CREATURE).sized(0.95F, 0.5F).build("tripodfish"));
    public static final RegHolder<EntityType<?>, EntityType<DeepOneEntity>> DEEP_ONE = register("deep_one", () -> (EntityType) EntityType.Builder.of(DeepOneEntity::new, MobCategory.MONSTER).sized(0.9F, 2.2F).setTrackingRange(12).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("deep_one"));
    public static final RegHolder<EntityType<?>, EntityType<InkBombEntity>> INK_BOMB = register("ink_bomb", () -> (EntityType) EntityType.Builder.of(InkBombEntity::new, MobCategory.MISC).sized(0.6F, 0.6F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("ink_bomb"));
    public static final RegHolder<EntityType<?>, EntityType<DeepOneKnightEntity>> DEEP_ONE_KNIGHT = register("deep_one_knight", () -> (EntityType) EntityType.Builder.of(DeepOneKnightEntity::new, MobCategory.MONSTER).sized(1.15F, 2.5F).setTrackingRange(12).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("deep_one_knight"));
    public static final RegHolder<EntityType<?>, EntityType<DeepOneMageEntity>> DEEP_ONE_MAGE = register("deep_one_mage", () -> (EntityType) EntityType.Builder.of(DeepOneMageEntity::new, MobCategory.MONSTER).sized(1.35F, 2.5F).setTrackingRange(12).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("deep_one_mage"));
    public static final RegHolder<EntityType<?>, EntityType<WaterBoltEntity>> WATER_BOLT = register("water_bolt", () -> (EntityType) EntityType.Builder.of(WaterBoltEntity::new, MobCategory.MISC).sized(0.6F, 0.6F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("water_bolt"));
    public static final RegHolder<EntityType<?>, EntityType<WaveEntity>> WAVE = register("wave", () -> (EntityType) EntityType.Builder.of(WaveEntity::new, MobCategory.MISC).sized(0.9F, 0.9F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("wave"));
    public static final RegHolder<EntityType<?>, EntityType<MineGuardianEntity>> MINE_GUARDIAN = register("mine_guardian", () -> (EntityType) EntityType.Builder.of(MineGuardianEntity::new, MobCategory.MONSTER).sized(1.3F, 1.3F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("mine_guardian"));
    public static final RegHolder<EntityType<?>, EntityType<MineGuardianAnchorEntity>> MINE_GUARDIAN_ANCHOR = register("mine_guardian_anchor", () -> (EntityType) EntityType.Builder.of(MineGuardianAnchorEntity::new, MobCategory.MISC).sized(0.6F, 1.35F).build("mine_guardian_anchor"));
    public static final RegHolder<EntityType<?>, EntityType<DepthChargeEntity>> DEPTH_CHARGE = register("depth_charge", () -> (EntityType) EntityType.Builder.of(DepthChargeEntity::new, MobCategory.MISC).sized(0.7F, 0.7F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("depth_charge"));
    public static final RegHolder<EntityType<?>, EntityType<FloaterEntity>> FLOATER = register("floater", () -> (EntityType) EntityType.Builder.of(FloaterEntity::new, MobCategory.MISC).sized(0.98F, 0.98F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("nuclear_bomb"));
    public static final RegHolder<EntityType<?>, EntityType<GuanoEntity>> GUANO = register("guano", () -> (EntityType) EntityType.Builder.of(GuanoEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("guano"));
    public static final RegHolder<EntityType<?>, EntityType<FallingGuanoEntity>> FALLING_GUANO = register("falling_guano", () -> (EntityType) EntityType.Builder.of(FallingGuanoEntity::new, MobCategory.MISC).sized(0.8F, 0.9F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("falling_guano"));
    public static final RegHolder<EntityType<?>, EntityType<GloomothEntity>> GLOOMOTH = register("gloomoth", () -> (EntityType) EntityType.Builder.of(GloomothEntity::new, MobCategory.AMBIENT).sized(0.99F, 0.99F).setTrackingRange(12).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("gloomoth"));
    public static final RegHolder<EntityType<?>, EntityType<UnderzealotEntity>> UNDERZEALOT = register("underzealot", () -> (EntityType) EntityType.Builder.of(UnderzealotEntity::new, MobCategory.MONSTER).sized(0.8F, 1.2F).build("underzealot"));
    public static final RegHolder<EntityType<?>, EntityType<WatcherEntity>> WATCHER = register("watcher", () -> (EntityType) EntityType.Builder.of(WatcherEntity::new, MobCategory.MONSTER).sized(0.9F, 1.9F).build("watcher"));
    public static final RegHolder<EntityType<?>, EntityType<CorrodentEntity>> CORRODENT = register("corrodent", () -> (EntityType) EntityType.Builder.of(CorrodentEntity::new, MobCategory.MONSTER).sized(0.9F, 0.9F).build("corrodent"));
    public static final RegHolder<EntityType<?>, EntityType<VesperEntity>> VESPER = register("vesper", () -> (EntityType) EntityType.Builder.of(VesperEntity::new, MobCategory.MONSTER).sized(1.2F, 1.65F).setTrackingRange(12).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("vesper"));
    public static final RegHolder<EntityType<?>, EntityType<ForsakenEntity>> FORSAKEN = register("forsaken", () -> (EntityType) EntityType.Builder.of(ForsakenEntity::new, MobCategory.MONSTER).sized(3F, 3.5F).setTrackingRange(12).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("forsaken"));
    public static final RegHolder<EntityType<?>, EntityType<BeholderEyeEntity>> BEHOLDER_EYE = register("beholder_eye", () -> (EntityType) EntityType.Builder.of(BeholderEyeEntity::new, MobCategory.MISC).sized(0.3F, 0.3F).build("beholder_eye"));
    public static final RegHolder<EntityType<?>, EntityType<DesolateDaggerEntity>> DESOLATE_DAGGER = register("desolate_dagger", () -> (EntityType) EntityType.Builder.of(DesolateDaggerEntity::new, MobCategory.MISC).sized(0.6F, 0.6F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("desolate_dagger"));
    public static final RegHolder<EntityType<?>, EntityType<BurrowingArrowEntity>> BURROWING_ARROW = register("burrowing_arrow", () -> (EntityType) EntityType.Builder.of(BurrowingArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("burrowing_arrow"));
    public static final RegHolder<EntityType<?>, EntityType<DarkArrowEntity>> DARK_ARROW = register("dark_arrow", () -> (EntityType) EntityType.Builder.of(DarkArrowEntity::new, MobCategory.MISC).sized(1.1F, 0.5F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("dark_arrow"));
    public static final RegHolder<EntityType<?>, EntityType<SweetishFishEntity>> SWEETISH_FISH = register("sweetish_fish", () -> (EntityType) EntityType.Builder.of(SweetishFishEntity::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.45F).build("sweetish_fish"));
    public static final RegHolder<EntityType<?>, EntityType<CaniacEntity>> CANIAC = register("caniac", () -> (EntityType) EntityType.Builder.of(CaniacEntity::new, MobCategory.MONSTER).sized(0.9F, 2.3F).setTrackingRange(12).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("caniac"));
    public static final RegHolder<EntityType<?>, EntityType<GumbeeperEntity>> GUMBEEPER = register("gumbeeper", () -> (EntityType) EntityType.Builder.of(GumbeeperEntity::new, MobCategory.MONSTER).sized(0.8F, 1.6F).build("gumbeeper"));
    public static final RegHolder<EntityType<?>, EntityType<GumballEntity>> GUMBALL = register("gumball", () -> (EntityType) EntityType.Builder.of(GumballEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("gumball"));
    public static final RegHolder<EntityType<?>, EntityType<CandicornEntity>> CANDICORN = register("candicorn", () -> (EntityType) EntityType.Builder.of(CandicornEntity::new, CAVE_CREATURE).sized(1.7F, 2.25F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("candicorn"));
    public static final RegHolder<EntityType<?>, EntityType<GumWormEntity>> GUM_WORM = register("gum_worm", () -> (EntityType) EntityType.Builder.of(GumWormEntity::new, MobCategory.MONSTER).sized(3.5F, 2.7F).setTrackingRange(14).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).fireImmune().build("gum_worm"));
    public static final RegHolder<EntityType<?>, EntityType<GumWormSegmentEntity>> GUM_WORM_SEGMENT = register("gum_worm_segment", () -> (EntityType) EntityType.Builder.of(GumWormSegmentEntity::new, MobCategory.MISC).sized(2.0F, 2.0F).setTrackingRange(14).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).fireImmune().build("gumball"));
    public static final RegHolder<EntityType<?>, EntityType<CaramelCubeEntity>> CARAMEL_CUBE = register("caramel_cube", () -> (EntityType) EntityType.Builder.of(CaramelCubeEntity::new, MobCategory.MONSTER).sized(0.8F, 0.8F).build("caramel_cube"));
    public static final RegHolder<EntityType<?>, EntityType<MeltedCaramelEntity>> MELTED_CARAMEL = register("melted_caramel", () -> (EntityType) EntityType.Builder.of(MeltedCaramelEntity::new, MobCategory.MISC).sized(0.99F, 0.1F).build("melted_caramel"));
    public static final RegHolder<EntityType<?>, EntityType<GummyBearEntity>> GUMMY_BEAR = register("gummy_bear", () -> (EntityType) EntityType.Builder.of(GummyBearEntity::new, CAVE_CREATURE).sized(1.45F, 1.2F).build("gummy_bear"));
    public static final RegHolder<EntityType<?>, EntityType<LicowitchEntity>> LICOWITCH = register("licowitch", () -> (EntityType) EntityType.Builder.of(LicowitchEntity::new, MobCategory.MONSTER).sized(0.8F, 1.9F).build("licowitch"));
    public static final RegHolder<EntityType<?>, EntityType<SpinningPeppermintEntity>> SPINNING_PEPPERMINT = register("spinning_peppermint", () -> (EntityType) EntityType.Builder.of(SpinningPeppermintEntity::new, MobCategory.MISC).sized(0.8F, 0.4F).build("spinning_peppermint"));
    public static final RegHolder<EntityType<?>, EntityType<SugarStaffHexEntity>> SUGAR_STAFF_HEX = register("sugar_staff_hex", () -> (EntityType) EntityType.Builder.of(SugarStaffHexEntity::new, MobCategory.MISC).sized(4.0F, 0.25F).build("sugar_staff_hex"));
    public static final RegHolder<EntityType<?>, EntityType<GingerbreadManEntity>> GINGERBREAD_MAN = register("gingerbread_man", () -> (EntityType) EntityType.Builder.of(GingerbreadManEntity::new, MobCategory.MONSTER).sized(0.5F, 0.9F).build("gingerbread_man"));
    public static final RegHolder<EntityType<?>, EntityType<ThrownIceCreamScoopEntity>> THROWN_ICE_CREAM_SCOOP = register("thrown_ice_cream_scoop", () -> (EntityType) EntityType.Builder.of(ThrownIceCreamScoopEntity::new, MobCategory.MISC).sized(0.4F, 0.4F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("thrown_ice_cream_scoop"));
    public static final RegHolder<EntityType<?>, EntityType<FallingFrostmintEntity>> FALLING_FROSTMINT = register("falling_frostmint", () -> (EntityType) EntityType.Builder.of(FallingFrostmintEntity::new, MobCategory.MISC).sized(0.8F, 0.9F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("falling_frostmint"));
    public static final RegHolder<EntityType<?>, EntityType<CandyCaneHookEntity>> CANDY_CANE_HOOK = register("candy_cane_hook", () -> (EntityType) EntityType.Builder.of(CandyCaneHookEntity::new, MobCategory.MISC).sized(0.6F, 0.6F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).updateInterval(10).clientTrackingRange(20).build("candy_cane_hook"));
    public static final RegHolder<EntityType<?>, EntityType<SodaBottleRocketEntity>> SODA_BOTTLE_ROCKET = register("soda_bottle_rocket", () -> (EntityType) EntityType.Builder.of(SodaBottleRocketEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("soda_bottle_rocket"));
    public static final RegHolder<EntityType<?>, EntityType<FrostmintSpearEntity>> FROSTMINT_SPEAR = register("frostmint_spear", () -> (EntityType) EntityType.Builder.of(FrostmintSpearEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("frostmint_spear"));

    @SubscribeEvent
    public static void initializeAttributes(EntityAttributeCreationEvent event) {
        event.put(TELETOR.get(), TeletorEntity.createAttributes().build());
        event.put(MAGNETRON.get(), MagnetronEntity.createAttributes().build());
        event.put(BOUNDROID.get(), BoundroidEntity.createAttributes().build());
        event.put(BOUNDROID_WINCH.get(), BoundroidEntity.createAttributes().build());
        event.put(FERROUSLIME.get(), FerrouslimeEntity.createAttributes().build());
        event.put(NOTOR.get(), NotorEntity.createAttributes().build());
        event.put(SUBTERRANODON.get(), SubterranodonEntity.createAttributes().build());
        event.put(VALLUMRAPTOR.get(), VallumraptorEntity.createAttributes().build());
        event.put(GROTTOCERATOPS.get(), GrottoceratopsEntity.createAttributes().build());
        event.put(TRILOCARIS.get(), TrilocarisEntity.createAttributes().build());
        event.put(TREMORSAURUS.get(), TremorsaurusEntity.createAttributes().build());
        event.put(RELICHEIRUS.get(), RelicheirusEntity.createAttributes().build());
        event.put(LUXTRUCTOSAURUS.get(), LuxtructosaurusEntity.createAttributes().build());
        event.put(ATLATITAN.get(), AtlatitanEntity.createAttributes().build());
        event.put(NUCLEEPER.get(), NucleeperEntity.createAttributes().build());
        event.put(RADGILL.get(), RadgillEntity.createAttributes().build());
        event.put(BRAINIAC.get(), BrainiacEntity.createAttributes().build());
        event.put(GAMMAROACH.get(), GammaroachEntity.createAttributes().build());
        event.put(RAYCAT.get(), RaycatEntity.createAttributes().build());
        event.put(TREMORZILLA.get(), TremorzillaEntity.createAttributes().build());
        event.put(LANTERNFISH.get(), LanternfishEntity.createAttributes().build());
        event.put(SEA_PIG.get(), SeaPigEntity.createAttributes().build());
        event.put(HULLBREAKER.get(), HullbreakerEntity.createAttributes().build());
        event.put(GOSSAMER_WORM.get(), GossamerWormEntity.createAttributes().build());
        event.put(TRIPODFISH.get(), TripodfishEntity.createAttributes().build());
        event.put(DEEP_ONE.get(), DeepOneEntity.createAttributes().build());
        event.put(DEEP_ONE_KNIGHT.get(), DeepOneKnightEntity.createAttributes().build());
        event.put(DEEP_ONE_MAGE.get(), DeepOneMageEntity.createAttributes().build());
        event.put(MINE_GUARDIAN.get(), MineGuardianEntity.createAttributes().build());
        event.put(GLOOMOTH.get(), GloomothEntity.createAttributes().build());
        event.put(UNDERZEALOT.get(), UnderzealotEntity.createAttributes().build());
        event.put(WATCHER.get(), WatcherEntity.createAttributes().build());
        event.put(CORRODENT.get(), CorrodentEntity.createAttributes().build());
        event.put(VESPER.get(), VesperEntity.createAttributes().build());
        event.put(FORSAKEN.get(), ForsakenEntity.createAttributes().build());
        event.put(SWEETISH_FISH.get(), SweetishFishEntity.createAttributes().build());
        event.put(CANIAC.get(), CaniacEntity.createAttributes().build());
        event.put(GUMBEEPER.get(), GumbeeperEntity.createAttributes().build());
        event.put(CANDICORN.get(), CandicornEntity.createAttributes().build());
        event.put(GUM_WORM.get(), GumWormEntity.createAttributes().build());
        event.put(CARAMEL_CUBE.get(), CaramelCubeEntity.createAttributes().build());
        event.put(GUMMY_BEAR.get(), GummyBearEntity.createAttributes().build());
        event.put(LICOWITCH.get(), LicowitchEntity.createAttributes().build());
        event.put(GINGERBREAD_MAN.get(), GingerbreadManEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void spawnPlacements(RegisterSpawnPlacementsEvent event) {
        // Custom spawn placement types for acid and soda fluids are defined in ACSpawnPlacementTypes.
        // Magnetic Caves monsters use checkMagneticCaveSpawnRules to allow spawning in lit areas (Tesla Bulbs etc.)
        event.register(TELETOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TeletorEntity::checkMagneticCaveSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(MAGNETRON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagnetronEntity::checkMagneticCaveSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(BOUNDROID.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BoundroidEntity::checkMagneticCaveSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(FERROUSLIME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FerrouslimeEntity::checkMagneticCaveSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(NOTOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NotorEntity::checkNotorSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(SUBTERRANODON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SubterranodonEntity::checkSubterranodonSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(VALLUMRAPTOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VallumraptorEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(GROTTOCERATOPS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GrottoceratopsEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(TRILOCARIS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TrilocarisEntity::checkTrilocarisSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(TREMORSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TremorsaurusEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(RELICHEIRUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RelicheirusEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(LUXTRUCTOSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LuxtructosaurusEntity::checkPrehistoricSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ATLATITAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AtlatitanEntity::checkPrehistoricPostBossSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(NUCLEEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NucleeperEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(RADGILL.get(), ACSpawnPlacementTypes.IN_ACID, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RadgillEntity::checkRadgillSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(BRAINIAC.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BrainiacEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(GAMMAROACH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GammaroachEntity::checkGammaroachSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(RAYCAT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RaycatEntity::checkRaycatSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(LANTERNFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LanternfishEntity::checkLanternfishSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(SEA_PIG.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SeaPigEntity::checkSeaPigSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(HULLBREAKER.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HullbreakerEntity::checkHullbreakerSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(GOSSAMER_WORM.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GossamerWormEntity::checkGossamerWormSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(TRIPODFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TripodfishEntity::checkTripodfishSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(DEEP_ONE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeepOneBaseEntity::checkDeepOneSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(DEEP_ONE_KNIGHT.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeepOneBaseEntity::checkDeepOneSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(DEEP_ONE_MAGE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeepOneBaseEntity::checkDeepOneSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(MINE_GUARDIAN.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MineGuardianEntity::checkMineGuardianSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(GLOOMOTH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GloomothEntity::checkGloomothSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(UNDERZEALOT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UnderzealotEntity::checkUnderzealotSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(WATCHER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WatcherEntity::checkWatcherSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(CORRODENT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorrodentEntity::checkCorrodentSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(VESPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VesperEntity::checkVesperSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(FORSAKEN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ForsakenEntity::checkForsakenSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(SWEETISH_FISH.get(), ACSpawnPlacementTypes.IN_PURPLE_SODA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SweetishFishEntity::checkSweetishFishSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(CANIAC.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CaniacEntity::checkCaniacSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(GUMBEEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GumbeeperEntity::checkGumbeeperSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(CANDICORN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CandicornEntity::checkCandicornSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(GUM_WORM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GumWormEntity::checkGumWormSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(CARAMEL_CUBE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CaramelCubeEntity::checkCaramelCubeSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(GUMMY_BEAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GummyBearEntity::checkGummyBearSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(LICOWITCH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LicowitchEntity::checkLicowitchSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(GINGERBREAD_MAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GingerbreadManEntity::checkGingerbreadManSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
    }

    public static void init() {
    }
}

