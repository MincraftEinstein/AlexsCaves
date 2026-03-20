package com.github.alexmodguy.alexscaves.client.particle;

import com.github.alexmodguy.alexscaves.platform.Services;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Supplier;

public class ACParticleRegistry {
    public static final Supplier<SimpleParticleType> SCARLET_MAGNETIC_ORBIT = register("scarlet_magnetic_orbit", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> AZURE_MAGNETIC_ORBIT = register("azure_magnetic_orbit", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> SCARLET_MAGNETIC_FLOW = register("scarlet_magnetic_flow", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> AZURE_MAGNETIC_FLOW = register("azure_magnetic_flow", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> GALENA_DEBRIS = register("galena_debris", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TESLA_BULB_LIGHTNING = register("tesla_bulb_lightning", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> MAGNET_LIGHTNING = register("magnet_lightning", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> MAGNETIC_CAVES_AMBIENT = register("magnetic_caves_ambient", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FERROUSLIME = register("ferrouslime", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> QUARRY_BORDER_LIGHTING = register("quarry_border_lightning", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> SCARLET_SHIELD_LIGHTNING = register("scarlet_shield_lightning", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> AZURE_SHIELD_LIGHTNING = register("azure_shield_lightning", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FLY = register("fly", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> WATER_TREMOR = register("water_tremor", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> AMBER_MONOLITH = register("amber_monolith", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> AMBER_EXPLOSION = register("amber_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> DINOSAUR_TRANSFORMATION_AMBER = register("dinosaur_transformation_amber", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> DINOSAUR_TRANSFORMATION_TECTONIC = register("dinosaur_transformation_tectonic", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> STUN_STAR = register("stun_star", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TEPHRA = register("tephra", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TEPHRA_SMALL = register("tephra_small", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TEPHRA_FLAME = register("tephra_flame", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> LUXTRUCTOSAURUS_SPIT = register("luxtructosaurus_spit", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> LUXTRUCTOSAURUS_ASH = register("luxtructosaurus_ash", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> HAPPINESS = register("happiness", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> ACID_BUBBLE = register("acid_bubble", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> BLACK_VENT_SMOKE = register("black_vent_smoke", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> WHITE_VENT_SMOKE = register("white_vent_smoke", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> GREEN_VENT_SMOKE = register("green_vent_smoke", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> RED_VENT_SMOKE = register("red_vent_smoke", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> MUSHROOM_CLOUD = register("mushroom_cloud", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> MUSHROOM_CLOUD_SMOKE = register("mushroom_cloud_smoke", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> MUSHROOM_CLOUD_EXPLOSION = register("mushroom_cloud_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> PROTON = register("proton", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FALLOUT = register("fallout", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> GAMMAROACH = register("gammaroach", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> HAZMAT_BREATHE = register("hazmat_breathe", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> BLUE_HAZMAT_BREATHE = register("blue_hazmat_breathe", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> RADGILL_SPLASH = register("radgill_splash", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> ACID_DROP = register("acid_drop", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> NUCLEAR_SIREN_SONAR = register("nuclear_siren_sonar", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> RAYGUN_EXPLOSION = register("raygun_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> BLUE_RAYGUN_EXPLOSION = register("blue_raygun_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> RAYGUN_BLAST = register("raygun_blast", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_EXPLOSION = register("tremorzilla_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_RETRO_EXPLOSION = register("tremorzilla_retro_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_TECTONIC_EXPLOSION = register("tremorzilla_tectonic_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_PROTON = register("tremorzilla_proton", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_RETRO_PROTON = register("tremorzilla_retro_proton", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_TECTONIC_PROTON = register("tremorzilla_tectonic_proton", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_LIGHTNING = register("tremorzilla_lightning", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_RETRO_LIGHTNING = register("tremorzilla_retro_lightning", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_TECTONIC_LIGHTNING = register("tremorzilla_tectonic_lightning", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_BLAST = register("tremorzilla_blast", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TREMORZILLA_STEAM = register("tremorzilla_steam", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TUBE_WORM = register("tube_worm", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> DEEP_ONE_MAGIC = register("deep_one_magic", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> WATER_FOAM = register("water_foam", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> BIG_SPLASH = register("big_splash", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> BIG_SPLASH_EFFECT = register("big_splash_effect", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> MINE_EXPLOSION = register("mine_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> BIO_POP = register("bio_pop", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> WATCHER_APPEARANCE = register("watcher_appearance", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> VOID_BEING_CLOUD = register("void_being_cloud", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> VOID_BEING_TENDRIL = register("void_being_tendril", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> VOID_BEING_EYE = register("void_being_eye", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> UNDERZEALOT_MAGIC = register("underzealot_magic", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> UNDERZEALOT_EXPLOSION = register("underzealot_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FALLING_GUANO = register("falling_guano", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> MOTH_DUST = register("moth_dust", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FORSAKEN_SPIT = register("forsaken_spit", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FORSAKEN_SONAR = register("forsaken_sonar", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FORSAKEN_SONAR_LARGE = register("forsaken_sonar_large", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> TOTEM_EXPLOSION = register("totem_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> ICE_CREAM_DRIP = register("ice_cream_drip", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> ICE_CREAM_SPLASH = register("ice_cream_splash", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> PURPLE_SODA_BUBBLE = register("purple_soda_bubble", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> PURPLE_SODA_BUBBLE_EMITTER = register("purple_soda_bubble_emitter", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> PURPLE_SODA_FIZZ = register("purple_soda_fizz", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> SUNDROP = register("sundrop", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> RAINBOW = register("rainbow", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> PLAYER_RAINBOW = register("player_rainbow", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> CANDICORN_CHARGE = register("candicorn_charge", () -> new SimpleParticleType(false));
    public static final Supplier<ParticleType<BlockParticleOption>> BIG_BLOCK_DUST = register("big_block_dust", ACParticleRegistry::createBlockParticleType);
    public static final Supplier<SimpleParticleType> CARAMEL_DROP = register("caramel_drop", () -> new SimpleParticleType(false));
    public static final Supplier<ParticleType<ItemParticleOption>> JELLY_BEAN_EAT = register("jelly_bean_eat", ACParticleRegistry::createItemParticleType);
    public static final Supplier<SimpleParticleType> SLEEP = register("sleep", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> WITCH_COOKIE = register("witch_cookie", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> PURPLE_WITCH_MAGIC = register("purple_witch_magic", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> PURPLE_WITCH_EXPLOSION = register("purple_witch_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> GOBTHUMPER = register("gobthumper", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> COLORED_DUST = register("colored_dust", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> SMALL_COLORED_DUST = register("small_colored_dust", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> CONVERSION_CRUCIBLE_EXPLOSION = register("conversion_crucible_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FROSTMINT_EXPLOSION = register("frostmint_explosion", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> SUGAR_FLAKE = register("sugar_flake", () -> new SimpleParticleType(false));

    public static void init() {
    }

    private static ParticleType<BlockParticleOption> createBlockParticleType() {
        return new ParticleType<>(false) {
            @Override
            public MapCodec<BlockParticleOption> codec() {
                return BlockParticleOption.codec(this);
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, BlockParticleOption> streamCodec() {
                return BlockParticleOption.streamCodec(this);
            }
        };
    }

    private static ParticleType<ItemParticleOption> createItemParticleType() {
        return new ParticleType<>(false) {
            @Override
            public MapCodec<ItemParticleOption> codec() {
                return ItemParticleOption.codec(this);
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, ItemParticleOption> streamCodec() {
                return ItemParticleOption.streamCodec(this);
            }
        };
    }

    static <T extends ParticleType<?>> Supplier<T> register(String name, Supplier<T> o) {
        return Services.REGISTRY_HELPER.registerParticle(name, o);
    }
}
