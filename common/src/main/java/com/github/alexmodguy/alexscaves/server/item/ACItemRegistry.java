package com.github.alexmodguy.alexscaves.server.item;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.platform.RegHolder;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.entity.util.AlexsCavesBoat;
import com.github.alexmodguy.alexscaves.server.entity.util.GummyColors;
import com.github.alexmodguy.alexscaves.server.item.dispenser.FluidContainerDispenseItemBehavior;
import com.github.alexthe666.citadel.server.block.LecternBooks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ACItemRegistry {

    private static Map<Supplier<Item>, ResourceKey<Biome>> creativeTabSpawnEggMap = new LinkedHashMap<>();

    static RegHolder<Item, Item> register(String name, Supplier<Item> itemSupplier) {
        return Services.REGISTRY_HELPER.registerItem(name, itemSupplier);
    }

    //    public static final ACArmorMaterial PRIMORDIAL_ARMOR_MATERIAL = new ACArmorMaterial("primordial", 20, new int[]{3, 4, 3, 2}, 25, SoundEvents.ARMOR_EQUIP_LEATHER, 0F);
//    public static final ACArmorMaterial HAZMAT_SUIT_ARMOR_MATERIAL = new ACArmorMaterial("hazmat_suit", 20, new int[]{2, 4, 5, 2}, 25, SoundEvents.ARMOR_EQUIP_IRON, 0.5F);
//    public static final ACArmorMaterial DIVING_SUIT_ARMOR_MATERIAL = new ACArmorMaterial("diving_suit", 20, new int[]{2, 6, 5, 2}, 25, SoundEvents.ARMOR_EQUIP_IRON, 0.0F);
//    public static final ACArmorMaterial DARKNESS_ARMOR_MATERIAL = new ACArmorMaterial("darkness", 15, new int[]{4, 5, 1, 1}, 40, SoundEvents.ARMOR_EQUIP_LEATHER, 0.5F);
//    public static final ACArmorMaterial RAINBOUNCE_ARMOR_MATERIAL = new ACArmorMaterial("rainbounce", 6, new int[]{2, 2, 1, 2}, 40, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F);
//    public static final ACArmorMaterial GINGERBREAD_ARMOR_MATERIAL = new ACArmorMaterial("gingerbread", 10, new int[]{2, 4, 5, 2}, 25, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F);


    public static final Supplier<Item> ADVANCEMENT_TAB_ICON = register("advancement_tab_icon", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> CAVE_TABLET = register("cave_tablet", () -> new CaveInfoItem(new Item.Properties(), true));
    public static final Supplier<Item> CAVE_CODEX = register("cave_codex", () -> new CaveInfoItem(new Item.Properties(), false));
    public static final RegHolder<Item, Item> CAVE_BOOK = register("cave_book", () -> new CaveBookItem());
    public static final Supplier<Item> CAVE_MAP = register("cave_map", () -> new CaveMapItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CAVE_MAP_SPRITE = register("cave_map_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CAVE_MAP_LOADING_SPRITE = register("cave_map_loading", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CAVE_MAP_FILLED_SPRITE = register("cave_map_filled", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> RAW_SCARLET_NEODYMIUM = register("raw_scarlet_neodymium", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> RAW_AZURE_NEODYMIUM = register("raw_azure_neodymium", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SCARLET_NEODYMIUM_INGOT = register("scarlet_neodymium_ingot", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> AZURE_NEODYMIUM_INGOT = register("azure_neodymium_ingot", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> TELECORE = register("telecore", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> NOTOR_COMPONENT = register("notor_gizmo", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HEAVYWEIGHT = register("heavyweight", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FERROUSLIME_BALL = register("ferrouslime_ball", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> QUARRY_SMASHER = register("quarry_smasher", () -> new QuarrySmasherItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> HOLOCODER = register("holocoder", () -> new HolocoderItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SEEKING_ARROW = register("seeking_arrow", () -> new SeekingArrowItem());
    public static final Supplier<Item> GALENA_GAUNTLET = register("galena_gauntlet", () -> new GalenaGauntletItem());
    public static final Supplier<Item> RESISTOR_SHIELD = register("resistor_shield", () -> new ResistorShieldItem());
    public static final Supplier<Item> POLARITY_ARMOR_TRIM_SMITHING_TEMPLATE = register("polarity_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(AlexsCaves.id("polarity")));
    public static final Supplier<Item> PEWEN_DOOR = register("pewen_door", () -> new DoubleHighBlockItem(ACBlockRegistry.PEWEN_DOOR.get(), (new Item.Properties())));
    public static final Supplier<Item> PEWEN_SIGN = register("pewen_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), ACBlockRegistry.PEWEN_SIGN.get(), ACBlockRegistry.PEWEN_WALL_SIGN.get()));
    public static final Supplier<Item> PEWEN_HANGING_SIGN = register("pewen_hanging_sign", () -> new HangingSignItem(ACBlockRegistry.PEWEN_HANGING_SIGN.get(), ACBlockRegistry.PEWEN_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));
    public static final Supplier<Item> PEWEN_BOAT = register("pewen_boat", () -> new CaveBoatItem(false, AlexsCavesBoat.Type.PEWEN, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PEWEN_CHEST_BOAT = register("pewen_chest_boat", () -> new CaveBoatItem(true, AlexsCavesBoat.Type.PEWEN, new Item.Properties().stacksTo(1)));
    // TODO fix when fluid
//    public static final Supplier<Item> TRILOCARIS_BUCKET = register("trilocaris_bucket", () -> new ModFishBucketItem(ACEntityRegistry.TRILOCARIS, () -> Fluids.WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> TRILOCARIS_TAIL = register("trilocaris_tail", () -> new Item(new Item.Properties().food(ACFoods.TRILOCARIS_TAIL)));
    public static final Supplier<Item> COOKED_TRILOCARIS_TAIL = register("cooked_trilocaris_tail", () -> new Item(new Item.Properties().food(ACFoods.TRILOCARIS_TAIL_COOKED)));
    public static final Supplier<Item> PINE_NUTS = register("pine_nuts", () -> new Item(new Item.Properties().food(ACFoods.PINE_NUTS)));
    public static final Supplier<Item> PEWEN_SAP = register("pewen_sap", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> AMBER_CURIOSITY = register("amber_curiosity", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DINOSAUR_NUGGET = register("dinosaur_nugget", () -> new Item(new Item.Properties().food(ACFoods.DINOSAUR_NUGGETS)));
    public static final Supplier<Item> SERENE_SALAD = register("serene_salad", () -> new PrehistoricMixtureItem(new Item.Properties().stacksTo(1).food(ACFoods.SERENE_SALAD)));
    public static final Supplier<Item> SEETHING_STEW = register("seething_stew", () -> new PrehistoricMixtureItem(new Item.Properties().stacksTo(1).food(ACFoods.SEETHING_STEW)));
    public static final Supplier<Item> PRIMORDIAL_SOUP = register("primordial_soup", () -> new PrehistoricMixtureItem(new Item.Properties().stacksTo(1).food(ACFoods.PRIMORDIAL_SOUP)));
    public static final Supplier<Item> TOUGH_HIDE = register("tough_hide", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HEAVY_BONE = register("heavy_bone", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final Supplier<Item> PRIMITIVE_CLUB = register("primitive_club", () -> new PrimitiveClubItem(new Item.Properties().durability(120)));
    public static final Supplier<Item> PRIMITIVE_CLUB_SPRITE = register("primitive_club_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> PRIMORDIAL_HELMET = register("primordial_helmet", () -> new PrimordialArmorItem(ACArmorMaterials.PRIMORDIAL, ArmorItem.Type.HELMET));
    public static final Supplier<Item> PRIMORDIAL_TUNIC = register("primordial_tunic", () -> new PrimordialArmorItem(ACArmorMaterials.PRIMORDIAL, ArmorItem.Type.CHESTPLATE));
    public static final Supplier<Item> PRIMORDIAL_PANTS = register("primordial_pants", () -> new PrimordialArmorItem(ACArmorMaterials.PRIMORDIAL, ArmorItem.Type.LEGGINGS));
    public static final Supplier<Item> LIMESTONE_SPEAR = register("limestone_spear", () -> new LimestoneSpearItem(new Item.Properties().stacksTo(16)));
    public static final Supplier<Item> LIMESTONE_SPEAR_SPRITE = register("limestone_spear_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> OMINOUS_CATALYST = register("ominous_catalyst", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final Supplier<Item> TECTONIC_SHARD = register("tectonic_shard", () -> new Item(new Item.Properties().rarity(ACRarity.getRarityDemonic()).fireResistant()));
    public static final Supplier<Item> EXTINCTION_SPEAR = register("extinction_spear", () -> new ExtinctionSpearItem(new Item.Properties().durability(1300).rarity(ACRarity.getRarityDemonic()).fireResistant()));
    public static final Supplier<Item> EXTINCTION_SPEAR_SPRITE = register("extinction_spear_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DINOSAUR_POTTERY_SHERD = register("dinosaur_pottery_sherd", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FOOTPRINT_POTTERY_SHERD = register("footprint_pottery_sherd", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DINOSAUR_TRAIN = register("dinosaur_train", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    // TODO fix when fluid
//    public static final Supplier<Item> ACID_BUCKET = register("acid_bucket", () -> new BucketItem(ACFluidRegistry.ACID_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
//    public static final Supplier<Item> RADGILL_BUCKET = register("radgill_bucket", () -> new ModFishBucketItem(ACEntityRegistry.RADGILL, ACFluidRegistry.ACID_FLUID_SOURCE::get, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> RADGILL = register("radgill", () -> new Item(new Item.Properties().food(ACFoods.RADGILL)));
    public static final Supplier<Item> COOKED_RADGILL = register("cooked_radgill", () -> new Item(new Item.Properties().food(ACFoods.RADGILL_COOKED)));
    public static final Supplier<Item> URANIUM = register("uranium", () -> new RadioactiveItem(new Item.Properties(), 0.001F));
    public static final Supplier<Item> URANIUM_SHARD = register("uranium_shard", () -> new RadioactiveItem(new Item.Properties(), 0.001F));
    public static final Supplier<Item> SULFUR_DUST = register("sulfur_dust", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> RADON_BOTTLE = register("radon_bottle", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    // TODO
//    public static final Supplier<Item> CINDER_BRICK = register("cinder_brick", () -> new ThrownProjectileItem(new Item.Properties(), player -> new CinderBrickEntity(player.level(), player), -20.0F, 0.65F, 0.9F));
    public static final Supplier<Item> SPELUNKIE = register("spelunkie", () -> new RadiationRemovingFoodItem(new Item.Properties().food(ACFoods.SPELUNKIE)));
    public static final Supplier<Item> SLAM = register("slam", () -> new RadiationRemovingFoodItem(new Item.Properties().food(ACFoods.SLAM)));
    public static final Supplier<Item> GREEN_SOYLENT = register("green_soylent", () -> new RadiationRemovingFoodItem(new Item.Properties().food(ACFoods.SOYLENT_GREEN)));
    public static final Supplier<Item> TOXIC_PASTE = register("toxic_paste", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> POLYMER_PLATE = register("polymer_plate", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HAZMAT_MASK = register("hazmat_mask", () -> new HazmatArmorItem(ACArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.HELMET));
    public static final Supplier<Item> HAZMAT_CHESTPLATE = register("hazmat_chestplate", () -> new HazmatArmorItem(ACArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.CHESTPLATE));
    public static final Supplier<Item> HAZMAT_LEGGINGS = register("hazmat_leggings", () -> new HazmatArmorItem(ACArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.LEGGINGS));
    public static final Supplier<Item> HAZMAT_BOOTS = register("hazmat_boots", () -> new HazmatArmorItem(ACArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.BOOTS));
    public static final Supplier<Item> FISSILE_CORE = register("fissile_core", () -> new RadioactiveItem(new Item.Properties().rarity(Rarity.UNCOMMON), 0.001F));
    public static final Supplier<Item> CHARRED_REMNANT = register("charred_remnant", () -> new RadioactiveItem(new Item.Properties(), 0.0005F));
    public static final Supplier<Item> REMOTE_DETONATOR = register("remote_detonator", () -> new RemoteDetonatorItem());
    public static final Supplier<Item> RAYGUN = register("raygun", () -> new RaygunItem());
    public static final Supplier<Item> MUSIC_DISC_FUSION_FRAGMENT = register("disc_fragment_fusion", () -> new DiscFragmentItem(new Item.Properties()));
    // Music discs - in 1.21+, use jukeboxPlayable component in item properties
    // The jukebox songs are data-driven and defined in data/alexscaves/jukebox_song/
    public static final ResourceKey<JukeboxSong> JUKEBOX_SONG_FUSION = ResourceKey.create(Registries.JUKEBOX_SONG, AlexsCaves.id("fusion"));
    public static final Supplier<Item> MUSIC_DISC_FUSION = register("music_disc_fusion", () -> new Item(new Item.Properties().stacksTo(1).rarity(ACRarity.getRarityNuclear()).jukeboxPlayable(JUKEBOX_SONG_FUSION)));
    // TODO fix when fluid
//    public static final Supplier<Item> LANTERNFISH_BUCKET = register("lanternfish_bucket", () -> new ModFishBucketItem(ACEntityRegistry.LANTERNFISH, () -> Fluids.WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> LANTERNFISH = register("lanternfish", () -> new Item(new Item.Properties().food(ACFoods.LANTERNFISH)));
    public static final Supplier<Item> COOKED_LANTERNFISH = register("cooked_lanternfish", () -> new Item(new Item.Properties().food(ACFoods.LANTERNFISH_COOKED)));
    // TODO fix when fluid
//    public static final Supplier<Item> TRIPODFISH_BUCKET = register("tripodfish_bucket", () -> new ModFishBucketItem(ACEntityRegistry.TRIPODFISH, () -> Fluids.WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> TRIPODFISH = register("tripodfish", () -> new Item(new Item.Properties().food(ACFoods.TRIPODFISH)));
    public static final Supplier<Item> COOKED_TRIPODFISH = register("cooked_tripodfish", () -> new Item(new Item.Properties().food(ACFoods.TRIPODFISH_COOKED)));
    // TODO fix when fluid
//    public static final Supplier<Item> SEA_PIG_BUCKET = register("sea_pig_bucket", () -> new ModFishBucketItem(ACEntityRegistry.SEA_PIG, () -> Fluids.WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> SEA_PIG = register("sea_pig", () -> new Item(new Item.Properties().food(ACFoods.SEA_PIG)));
    public static final Supplier<Item> MARINE_SNOW = register("marine_snow", () -> new MarineSnowItem());
    // TODO fix when fluid
//    public static final Supplier<Item> GOSSAMER_WORM_BUCKET = register("gossamer_worm_bucket", () -> new ModFishBucketItem(ACEntityRegistry.GOSSAMER_WORM, () -> Fluids.WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> BIOLUMINESSCENCE = register("bioluminesscence", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> PEARL = register("pearl", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> COOKED_MUSSEL = register("cooked_mussel", () -> new Item(new Item.Properties().food(ACFoods.MUSSEL_COOKED)));
    public static final Supplier<Item> DEEP_SEA_SUSHI_ROLL = register("deep_sea_sushi_roll", () -> new Item(new Item.Properties().food(ACFoods.DEEP_SEA_SUSHI_ROLL)));
    public static final Supplier<Item> SEA_GLASS_SHARDS = register("sea_glass_shards", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SUBMARINE = register("submarine", () -> new SubmarineItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> DIVING_HELMET = register("diving_helmet", () -> new DivingArmorItem(ACArmorMaterials.DIVING_SUIT, ArmorItem.Type.HELMET));
    public static final Supplier<Item> DIVING_CHESTPLATE = register("diving_chestplate", () -> new DivingArmorItem(ACArmorMaterials.DIVING_SUIT, ArmorItem.Type.CHESTPLATE));
    public static final Supplier<Item> DIVING_LEGGINGS = register("diving_leggings", () -> new DivingArmorItem(ACArmorMaterials.DIVING_SUIT, ArmorItem.Type.LEGGINGS));
    public static final Supplier<Item> DIVING_BOOTS = register("diving_boots", () -> new DivingArmorItem(ACArmorMaterials.DIVING_SUIT, ArmorItem.Type.BOOTS));
    public static final Supplier<Item> FLOATER = register("floater", () -> new FloaterItem());
    public static final Supplier<Item> GAZING_PEARL = register("gazing_pearl", () -> new GazingPearlItem());
    public static final Supplier<Item> INK_BOMB = register("ink_bomb", () -> new InkBombItem(new Item.Properties(), false));
    public static final Supplier<Item> GLOW_INK_BOMB = register("glow_ink_bomb", () -> new InkBombItem(new Item.Properties(), true));
    public static final Supplier<Item> MAGIC_CONCH = register("magic_conch", () -> new MagicConchItem(new Item.Properties().durability(5).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> SEA_STAFF = register("sea_staff", () -> new SeaStaffItem(new Item.Properties().durability(850).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> SEA_STAFF_SPRITE = register("sea_staff_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> ORTHOLANCE = register("ortholance", () -> new OrtholanceItem(new Item.Properties().durability(340).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> ORTHOLANCE_SPRITE = register("ortholance_inventory", () -> new Item(new Item.Properties()));
    // TODO
//    public static final Supplier<Item> DEPTH_CHARGE = register("depth_charge", () -> new ThrownProjectileItem(new Item.Properties(), player -> new DepthChargeEntity(player.level(), player), -10.0F, 0.65F, 1.5F));
    public static final Supplier<Item> GUARDIAN_POTTERY_SHERD = register("guardian_pottery_sherd", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HERO_POTTERY_SHERD = register("hero_pottery_sherd", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BIOLUMINESCENT_TORCH = register("bioluminescent_torch", () -> new StandingAndWallBlockItem(ACBlockRegistry.BIOLUMINESCENT_TORCH.get(), ACBlockRegistry.BIOLUMINESCENT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final Supplier<Item> GAME_CONTROLLER = register("game_controller", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Supplier<Item> STINKY_FISH = register("stinky_fish", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ACFoods.STINKY_FISH)));
    public static final Supplier<Item> IMMORTAL_EMBRYO = register("immortal_embryo", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    //TODO
//    public static final Supplier<Item> GUANO = register("guano", () -> new ThrownProjectileItem(new Item.Properties(), player -> new GuanoEntity(player.level(), player), 0.0F, 1.0F, 1.0F));
    public static final Supplier<Item> MOTH_DUST = register("moth_dust", () -> new MothDustItem());
    public static final Supplier<Item> FERTILIZER = register("fertilizer", () -> new FertilizerItem());
    public static final Supplier<Item> DARK_TATTERS = register("dark_tatters", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> OCCULT_GEM = register("occult_gem", () -> new OccultGemItem());
    public static final Supplier<Item> TOTEM_OF_POSSESSION = register("totem_of_possession", () -> new TotemOfPossessionItem());
    public static final Supplier<Item> DESOLATE_DAGGER = register("desolate_dagger", () -> new DesolateDaggerItem());
    public static final Supplier<Item> CORRODENT_TEETH = register("corrodent_teeth", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BURROWING_ARROW = register("burrowing_arrow", () -> new BurrowingArrowItem());
    public static final Supplier<Item> VESPER_WING = register("vesper_wing", () -> new Item(new Item.Properties().food(ACFoods.VESPER_WING)));
    public static final Supplier<Item> VESPER_STEW = register("vesper_stew", () -> new Item(new Item.Properties().food(ACFoods.VESPER_SOUP).stacksTo(1)));
    public static final Supplier<Item> PURE_DARKNESS = register("pure_darkness", () -> new Item(new Item.Properties().rarity(ACRarity.getRarityDemonic())));
    public static final Supplier<Item> SHADOW_SILK = register("shadow_silk", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HOOD_OF_DARKNESS = register("hood_of_darkness", () -> new DarknessArmorItem(ACArmorMaterials.DARKNESS, ArmorItem.Type.HELMET));
    public static final Supplier<Item> CLOAK_OF_DARKNESS = register("cloak_of_darkness", () -> new DarknessArmorItem(ACArmorMaterials.DARKNESS, ArmorItem.Type.CHESTPLATE));
    public static final Supplier<Item> DARKENED_APPLE = register("darkened_apple", () -> new DarkenedAppleItem());
    public static final Supplier<Item> DREADBOW = register("dreadbow", () -> new DreadbowItem());
    public static final Supplier<Item> DREADBOW_SPRITE = register("dreadbow_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DREADBOW_PULLING_0_SPRITE = register("dreadbow_pulling_0_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DREADBOW_PULLING_1_SPRITE = register("dreadbow_pulling_1_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DREADBOW_PULLING_2_SPRITE = register("dreadbow_pulling_2_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> THORNWOOD_DOOR = register("thornwood_door", () -> new DoubleHighBlockItem(ACBlockRegistry.THORNWOOD_DOOR.get(), (new Item.Properties())));
    public static final Supplier<Item> THORNWOOD_SIGN = register("thornwood_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), ACBlockRegistry.THORNWOOD_SIGN.get(), ACBlockRegistry.THORNWOOD_WALL_SIGN.get()));
    public static final Supplier<Item> THORNWOOD_HANGING_SIGN = register("thornwood_hanging_sign", () -> new HangingSignItem(ACBlockRegistry.THORNWOOD_HANGING_SIGN.get(), ACBlockRegistry.THORNWOOD_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));
    public static final Supplier<Item> THORNWOOD_BOAT = register("thornwood_boat", () -> new CaveBoatItem(false, AlexsCavesBoat.Type.THORNWOOD, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> THORNWOOD_CHEST_BOAT = register("thornwood_chest_boat", () -> new CaveBoatItem(true, AlexsCavesBoat.Type.THORNWOOD, new Item.Properties().stacksTo(1)));
    // TODO fix when fluid
//    public static final Supplier<Item> PURPLE_SODA_BUCKET = register("purple_soda_bucket", () -> new BucketItem(ACFluidRegistry.PURPLE_SODA_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> PURPLE_SODA_BOTTLE = register("purple_soda_bottle", () -> new DrinkableBottledItem(new Item.Properties().stacksTo(16).food(ACFoods.PURPLE_SODA_BOTTLE)));
//    public static final Supplier<Item> SWEETISH_FISH_RED_BUCKET = register("sweetish_fish_red_bucket", () -> new SweetishFishBucketItem(GummyColors.RED));
//    public static final Supplier<Item> SWEETISH_FISH_GREEN_BUCKET = register("sweetish_fish_green_bucket", () -> new SweetishFishBucketItem(GummyColors.GREEN));
//    public static final Supplier<Item> SWEETISH_FISH_BLUE_BUCKET = register("sweetish_fish_blue_bucket", () -> new SweetishFishBucketItem(GummyColors.BLUE));
//    public static final Supplier<Item> SWEETISH_FISH_YELLOW_BUCKET = register("sweetish_fish_yellow_bucket", () -> new SweetishFishBucketItem(GummyColors.YELLOW));
//    public static final Supplier<Item> SWEETISH_FISH_PINK_BUCKET = register("sweetish_fish_pink_bucket", () -> new SweetishFishBucketItem(GummyColors.PINK));
    public static final Supplier<Item> SWEETISH_FISH_RED = register("sweetish_fish_red", () -> new Item(new Item.Properties().food(ACFoods.SWEETISH_FISH)));
    public static final Supplier<Item> SWEETISH_FISH_GREEN = register("sweetish_fish_green", () -> new Item(new Item.Properties().food(ACFoods.SWEETISH_FISH)));
    public static final Supplier<Item> SWEETISH_FISH_BLUE = register("sweetish_fish_blue", () -> new Item(new Item.Properties().food(ACFoods.SWEETISH_FISH)));
    public static final Supplier<Item> SWEETISH_FISH_YELLOW = register("sweetish_fish_yellow", () -> new Item(new Item.Properties().food(ACFoods.SWEETISH_FISH)));
    public static final Supplier<Item> SWEETISH_FISH_PINK = register("sweetish_fish_pink", () -> new Item(new Item.Properties().food(ACFoods.SWEETISH_FISH)));
    public static final Supplier<Item> GELATIN_RED = register("gelatin_red", () -> new Item(new Item.Properties().food(ACFoods.GELATIN)));
    public static final Supplier<Item> GELATIN_GREEN = register("gelatin_green", () -> new Item(new Item.Properties().food(ACFoods.GELATIN)));
    public static final Supplier<Item> GELATIN_BLUE = register("gelatin_blue", () -> new Item(new Item.Properties().food(ACFoods.GELATIN)));
    public static final Supplier<Item> GELATIN_YELLOW = register("gelatin_yellow", () -> new Item(new Item.Properties().food(ACFoods.GELATIN)));
    public static final Supplier<Item> GELATIN_PINK = register("gelatin_pink", () -> new Item(new Item.Properties().food(ACFoods.GELATIN)));
    public static final Supplier<Item> HOT_CHOCOLATE_BOTTLE = register("hot_chocolate_bottle", () -> new HotChocolateBottleItem());
    // TODO fix when entity
//    public static final Supplier<Item> VANILLA_ICE_CREAM_SCOOP = register("vanilla_ice_cream_scoop", () -> new ThrownProjectileItem(new Item.Properties(), player -> new ThrownIceCreamScoopEntity(player.level(), player), -10.0F, 1.0F, 0.2F));
//    public static final Supplier<Item> CHOCOLATE_ICE_CREAM_SCOOP = register("chocolate_ice_cream_scoop", () -> new ThrownProjectileItem(new Item.Properties(), player -> new ThrownIceCreamScoopEntity(player.level(), player), -10.0F, 1.0F, 0.2F));
//    public static final Supplier<Item> SWEETBERRY_ICE_CREAM_SCOOP = register("sweetberry_ice_cream_scoop", () -> new ThrownProjectileItem(new Item.Properties(), player -> new ThrownIceCreamScoopEntity(player.level(), player), -10.0F, 1.0F, 0.2F));
    public static final Supplier<Item> SUNDAE = register("sundae", () -> new Item(new Item.Properties().food(ACFoods.SUNDAE).rarity(ACRarity.getRaritySweet()).stacksTo(1)));
    public static final Supplier<Item> SHARPENED_CANDY_CANE = register("sharpened_candy_cane", () -> new SharpenedCandyCaneItem(new Item.Properties().food(ACFoods.CANDY_CANE)));
    public static final Supplier<Item> PEPPERMINT_POWDER = register("peppermint_powder", () -> new Item(new Item.Properties().food(ACFoods.PEPPERMINT_POWDER)));
    public static final Supplier<Item> RAINBOUNCE_BOOTS = register("rainbounce_boots", () -> new RainbounceBootsItem(ACArmorMaterials.RAINBOUNCE));
    public static final Supplier<Item> GUMBALL_PILE = register("gumball_pile", () -> new Item(new Item.Properties().food(ACFoods.GUMBALL_PILE)));
    public static final Supplier<Item> SHOT_GUM = register("shot_gum", () -> new ShotGumItem());
    public static final Supplier<Item> CARAMEL = register("caramel", () -> new Item(new Item.Properties().food(ACFoods.CARAMEL)));
    public static final Supplier<Item> CARAMEL_APPLE = register("caramel_apple", () -> new Item(new Item.Properties().food(ACFoods.CARAMEL_APPLE)));
    public static final Supplier<Item> CANDY_CANE_HOOK = register("candy_cane_hook", () -> new CandyCaneHookItem());
    public static final Supplier<Item> SWEET_TOOTH = register("sweet_tooth", () -> new Item(new Item.Properties().rarity(ACRarity.getRaritySweet())));
    public static final Supplier<Item> RADIANT_ESSENCE = register("radiant_essence", () -> new RadiantEssenceItem());
    public static final Supplier<Item> LICOWITCH_RADIANT_ESSENCE = register("licowitch_radiant_essence", () -> new RadiantEssenceItem());
    public static final Supplier<Item> SACK_OF_SATING = register("sack_of_sating", () -> new SackOfSatingItem());
    public static final Supplier<Item> SUGAR_STAFF = register("sugar_staff", () -> new SugarStaffItem(new Item.Properties().durability(100).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> SUGAR_STAFF_SPRITE = register("sugar_staff_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GINGERBREAD_CRUMBS = register("gingerbread_crumbs", () -> new Item(new Item.Properties().food(ACFoods.GINGERBREAD_CRUMBS)));
    public static final Supplier<Item> GINGERBREAD_HELMET = register("gingerbread_helmet", () -> new GingerbreadArmorItem(ACArmorMaterials.GINGERBREAD, ArmorItem.Type.HELMET));
    public static final Supplier<Item> GINGERBREAD_CHESTPLATE = register("gingerbread_chestplate", () -> new GingerbreadArmorItem(ACArmorMaterials.GINGERBREAD, ArmorItem.Type.CHESTPLATE));
    public static final Supplier<Item> GINGERBREAD_LEGGINGS = register("gingerbread_leggings", () -> new GingerbreadArmorItem(ACArmorMaterials.GINGERBREAD, ArmorItem.Type.LEGGINGS));
    public static final Supplier<Item> GINGERBREAD_BOOTS = register("gingerbread_boots", () -> new GingerbreadArmorItem(ACArmorMaterials.GINGERBREAD, ArmorItem.Type.BOOTS));
    public static final Supplier<Item> PURPLE_SODA_BOTTLE_ROCKET = register("purple_soda_bottle_rocket", () -> new SodaBottleRocketItem());
    public static final Supplier<Item> FROSTMINT_SPEAR = register("frostmint_spear", () -> new FrostmintSpearItem(new Item.Properties().stacksTo(16)));
    public static final Supplier<Item> FROSTMINT_SPEAR_SPRITE = register("frostmint_spear_inventory", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MUSIC_DISC_TASTY_FRAGMENT = register("disc_fragment_tasty", () -> new DiscFragmentItem(new Item.Properties()));
    public static final ResourceKey<JukeboxSong> JUKEBOX_SONG_TASTY = ResourceKey.create(Registries.JUKEBOX_SONG, AlexsCaves.id("tasty"));
    public static final Supplier<Item> MUSIC_DISC_TASTY = register("music_disc_tasty", () -> new Item(new Item.Properties().stacksTo(1).rarity(ACRarity.getRaritySweet()).jukeboxPlayable(JUKEBOX_SONG_TASTY)));
    public static final Supplier<Item> ALEX_MEAL = register("alex_meal", () -> new AlexMealItem());
    public static final Supplier<Item> BIOME_TREAT = register("biome_treat", () -> new BiomeTreatItem());
    public static final Supplier<Item> JELLY_BEAN = register("jelly_bean", () -> new JellyBeanItem());

    static {
        // TODO when entity
      /*  spawnEgg("teletor", ACEntityRegistry.TELETOR, 0X433B4A, 0X0060EF, ACBiomeRegistry.MAGNETIC_CAVES);
        spawnEgg("magnetron", ACEntityRegistry.MAGNETRON, 0XFF002A, 0X203070, ACBiomeRegistry.MAGNETIC_CAVES);
        spawnEgg("boundroid", ACEntityRegistry.BOUNDROID, 0XBB1919, 0XFFFFFF, ACBiomeRegistry.MAGNETIC_CAVES);
        spawnEgg("ferrouslime", ACEntityRegistry.FERROUSLIME, 0X26272D, 0X53556C, ACBiomeRegistry.MAGNETIC_CAVES);
        spawnEgg("notor", ACEntityRegistry.NOTOR, 0X5F5369, 0XC6C6C6, ACBiomeRegistry.MAGNETIC_CAVES);
        spawnEgg("subterranodon", ACEntityRegistry.SUBTERRANODON, 0X00B1B2, 0XFFF11C, ACBiomeRegistry.PRIMORDIAL_CAVES);
        spawnEgg("vallumraptor", ACEntityRegistry.VALLUMRAPTOR, 0X22389A, 0XEEE5AB, ACBiomeRegistry.PRIMORDIAL_CAVES);
        spawnEgg("grottoceratops", ACEntityRegistry.GROTTOCERATOPS, 0XAC3B03, 0XD39B4E, ACBiomeRegistry.PRIMORDIAL_CAVES);
        spawnEgg("trilocaris", ACEntityRegistry.TRILOCARIS, 0X713E0D, 0X8B2010, ACBiomeRegistry.PRIMORDIAL_CAVES);
        spawnEgg("tremorsaurus", ACEntityRegistry.TREMORSAURUS, 0X53780E, 0XDFA211, ACBiomeRegistry.PRIMORDIAL_CAVES);
        spawnEgg("relicheirus", ACEntityRegistry.RELICHEIRUS, 0X6AE4F9, 0X5B2152, ACBiomeRegistry.PRIMORDIAL_CAVES);
        spawnEgg("luxtructosaurus", ACEntityRegistry.LUXTRUCTOSAURUS, 0X1F0E15, 0XB30C03, ACBiomeRegistry.PRIMORDIAL_CAVES);
        spawnEgg("atlatitan", ACEntityRegistry.ATLATITAN, 0XB67000, 0XBFBAA4, ACBiomeRegistry.PRIMORDIAL_CAVES);
        spawnEgg("nucleeper", ACEntityRegistry.NUCLEEPER, 0X95A1A5, 0X00FF00, ACBiomeRegistry.TOXIC_CAVES);
        spawnEgg("radgill", ACEntityRegistry.RADGILL, 0X43302C, 0XE8E400, ACBiomeRegistry.TOXIC_CAVES);
        spawnEgg("brainiac", ACEntityRegistry.BRAINIAC, 0X3E5136, 0XE87C9E, ACBiomeRegistry.TOXIC_CAVES);
        spawnEgg("gammaroach", ACEntityRegistry.GAMMAROACH, 0X56682A, 0X2A2B19, ACBiomeRegistry.TOXIC_CAVES);
        spawnEgg("raycat", ACEntityRegistry.RAYCAT, 0X67FF00, 0X030A00, ACBiomeRegistry.TOXIC_CAVES);
        spawnEgg("tremorzilla", ACEntityRegistry.TREMORZILLA, 0X574D2F, 0X8CFF08, ACBiomeRegistry.TOXIC_CAVES);
        spawnEgg("lanternfish", ACEntityRegistry.LANTERNFISH, 0X182538, 0XECA500, ACBiomeRegistry.ABYSSAL_CHASM);
        spawnEgg("sea_pig", ACEntityRegistry.SEA_PIG, 0XFFA3B9, 0XF88672, ACBiomeRegistry.ABYSSAL_CHASM);
        spawnEgg("hullbreaker", ACEntityRegistry.HULLBREAKER, 0X182538, 0X76FFFD, ACBiomeRegistry.ABYSSAL_CHASM);
        spawnEgg("gossamer_worm", ACEntityRegistry.GOSSAMER_WORM, 0XC8F1FF, 0X96DEF6, ACBiomeRegistry.ABYSSAL_CHASM);
        spawnEgg("tripodfish", ACEntityRegistry.TRIPODFISH, 0X34529D, 0X81A1CF, ACBiomeRegistry.ABYSSAL_CHASM);
        spawnEgg("deep_one", ACEntityRegistry.DEEP_ONE, 0X0D2547, 0X0A843B, ACBiomeRegistry.ABYSSAL_CHASM);
        spawnEgg("deep_one_knight", ACEntityRegistry.DEEP_ONE_KNIGHT, 0X472C3B, 0XD4CCC3, ACBiomeRegistry.ABYSSAL_CHASM);
        spawnEgg("deep_one_mage", ACEntityRegistry.DEEP_ONE_MAGE, 0X96DEF6, 0XD1FF00, ACBiomeRegistry.ABYSSAL_CHASM);
        spawnEgg("mine_guardian", ACEntityRegistry.MINE_GUARDIAN, 0X404253, 0XE62008, ACBiomeRegistry.ABYSSAL_CHASM);
        spawnEgg("gloomoth", ACEntityRegistry.GLOOMOTH, 0X5E463D, 0XEBD3BE, ACBiomeRegistry.FORLORN_HOLLOWS);
        spawnEgg("underzealot", ACEntityRegistry.UNDERZEALOT, 0X291C17, 0XF27C68, ACBiomeRegistry.FORLORN_HOLLOWS);
        spawnEgg("watcher", ACEntityRegistry.WATCHER, 0X291C17, 0XEC1900, ACBiomeRegistry.FORLORN_HOLLOWS);
        spawnEgg("corrodent", ACEntityRegistry.CORRODENT, 0X351A14, 0X593B33, ACBiomeRegistry.FORLORN_HOLLOWS);
        spawnEgg("vesper", ACEntityRegistry.VESPER, 0X884E2A, 0XA54A6B, ACBiomeRegistry.FORLORN_HOLLOWS);
        spawnEgg("forsaken", ACEntityRegistry.FORSAKEN, 0X000000, 0X110909, ACBiomeRegistry.FORLORN_HOLLOWS);
        spawnEgg("sweetish_fish", ACEntityRegistry.SWEETISH_FISH, 0XE9132C, 0XFF364D, ACBiomeRegistry.CANDY_CAVITY);
        spawnEgg("caniac", ACEntityRegistry.CANIAC, 0XF9F0FF, 0XFF3F56, ACBiomeRegistry.CANDY_CAVITY);
        spawnEgg("gumbeeper", ACEntityRegistry.GUMBEEPER, 0XFF2B44, 0XE7BAFF, ACBiomeRegistry.CANDY_CAVITY);
        spawnEgg("candicorn", ACEntityRegistry.CANDICORN, 0XE86B00, 0XFFEF57, ACBiomeRegistry.CANDY_CAVITY);
        spawnEgg("gum_worm", ACEntityRegistry.GUM_WORM, 0X92FFD9, 0XFFA1DC, ACBiomeRegistry.CANDY_CAVITY);
        spawnEgg("caramel_cube", ACEntityRegistry.CARAMEL_CUBE, 0XCC8015, 0XB86A0D, ACBiomeRegistry.CANDY_CAVITY);
        spawnEgg("gummy_bear", ACEntityRegistry.GUMMY_BEAR, 0XFF463F, 0XFDA09E, ACBiomeRegistry.CANDY_CAVITY);
        spawnEgg("licowitch", ACEntityRegistry.LICOWITCH, 0X681182, 0XFF6CD7, ACBiomeRegistry.CANDY_CAVITY);
        spawnEgg("gingerbread_man", ACEntityRegistry.GINGERBREAD_MAN, 0XBB581D, 0XFFFFFF, ACBiomeRegistry.CANDY_CAVITY);
  */
    }

    public static void init() {
        LecternBooks.BOOKS.put(CAVE_BOOK.id(), new LecternBooks.BookData(0X81301C, 0XFDF8EC));
        // Register compostable on fabric here:
    }

    private static void spawnEgg(String entityName, Supplier<? extends EntityType<? extends Mob>> type, int color1, int color2, ResourceKey<Biome> biomeTab) {
        Supplier<Item> item = register("spawn_egg_" + entityName, () -> new SpawnEggItem(type.get(), color1, color2, new Item.Properties()));
        creativeTabSpawnEggMap.put(item, biomeTab);
    }

    public static void registerDispenserBehavior() {
        // TODO when entity
      /*  DispenserBlock.registerBehavior(SEEKING_ARROW.get(), new ACProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                AbstractArrow abstractarrow = new SeekingArrowEntity(level, position.x(), position.y(), position.z());
                abstractarrow.pickup = AbstractArrow.Pickup.ALLOWED;
                return abstractarrow;
            }
        });*/
        DispenserBlock.registerBehavior(GALENA_GAUNTLET.get(), ArmorItem.DISPENSE_ITEM_BEHAVIOR);
//        DispenserBlock.registerBehavior(TRILOCARIS_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(ACID_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(RADGILL_BUCKET.get(), new FluidContainerDispenseItemBehavior());
      /*  DispenserBlock.registerBehavior(CINDER_BRICK.get(), new ACProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                return new CinderBrickEntity(level, position.x(), position.y(), position.z());
            }
        });*/
//        DispenserBlock.registerBehavior(LANTERNFISH_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(TRIPODFISH_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(SEA_PIG_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(GOSSAMER_WORM_BUCKET.get(), new FluidContainerDispenseItemBehavior());
      /*  DispenserBlock.registerBehavior(INK_BOMB.get(), new ACProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                return new InkBombEntity(level, position.x(), position.y(), position.z());
            }
        });
        DispenserBlock.registerBehavior(GLOW_INK_BOMB.get(), new ACProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                InkBombEntity inkBombEntity = new InkBombEntity(level, position.x(), position.y(), position.z());
                inkBombEntity.setGlowingBomb(true);
                inkBombEntity.setItem(itemStack);
                return inkBombEntity;
            }
        });
        DispenserBlock.registerBehavior(GUANO.get(), new ACProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                return new GuanoEntity(level, position.x(), position.y(), position.z());
            }
        });
        DispenserBlock.registerBehavior(BURROWING_ARROW.get(), new ACProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                AbstractArrow abstractarrow = new BurrowingArrowEntity(level, position.x(), position.y(), position.z());
                abstractarrow.pickup = AbstractArrow.Pickup.ALLOWED;
                return abstractarrow;
            }
        });*/
       /* DispenserBlock.registerBehavior(ACBlockRegistry.NUCLEAR_BOMB.get(), new DefaultDispenseItemBehavior() {
            protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
                Level level = blockSource.level();
                BlockPos blockpos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
                NuclearBombEntity nuclearBomb = new NuclearBombEntity(level, (double) blockpos.getX() + 0.5D, (double) blockpos.getY(), (double) blockpos.getZ() + 0.5D);
                level.addFreshEntity(nuclearBomb);
                level.playSound((Player) null, nuclearBomb.getX(), nuclearBomb.getY(), nuclearBomb.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent((Entity) null, GameEvent.ENTITY_PLACE, blockpos);
                itemStack.shrink(1);
                return itemStack;
            }
        });*/
//        DispenserBlock.registerBehavior(PURPLE_SODA_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(SWEETISH_FISH_RED_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(SWEETISH_FISH_GREEN_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(SWEETISH_FISH_BLUE_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(SWEETISH_FISH_YELLOW_BUCKET.get(), new FluidContainerDispenseItemBehavior());
//        DispenserBlock.registerBehavior(SWEETISH_FISH_PINK_BUCKET.get(), new FluidContainerDispenseItemBehavior());
    }

    public static List<RegHolder<Item, Item>> getSpawnEggsForTab(ResourceKey<Biome> tabName) {
        List<RegHolder<Item, Item>> list = new ArrayList<>();
        for (Map.Entry<Supplier<Item>, ResourceKey<Biome>> entry : creativeTabSpawnEggMap.entrySet()) {
            if (entry.getValue().equals(tabName)) {
                list.add((RegHolder<Item, Item>) entry.getKey());
            }
        }
        return list;
    }

    public static Item getSpawnEggFor(EntityType type) {
        for (Map.Entry<Supplier<Item>, ResourceKey<Biome>> entry : creativeTabSpawnEggMap.entrySet()) {
//            if (entry.getKey().get() instanceof DeferredSpawnEggItem forgeSpawnEggItem && forgeSpawnEggItem.getType(null) == type) {
//                return forgeSpawnEggItem;
//            }
        }
        return Items.AIR;
    }
}
