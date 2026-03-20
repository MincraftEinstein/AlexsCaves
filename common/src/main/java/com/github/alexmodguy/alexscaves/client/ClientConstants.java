package com.github.alexmodguy.alexscaves.client;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ClientConstants {

    public static final ResourceLocation POTION_EFFECT_HUD_OVERLAYS = ResourceLocation
            .fromNamespaceAndPath(AlexsCaves.MOD_ID, "textures/misc/potion_effect_hud_overlays.png");
    public static final ResourceLocation BOSS_BAR_HUD_OVERLAYS = ResourceLocation
            .fromNamespaceAndPath(AlexsCaves.MOD_ID, "textures/misc/boss_bar_hud_overlays.png");
    public static final ResourceLocation DINOSAUR_HUD_OVERLAYS = ResourceLocation
            .fromNamespaceAndPath(AlexsCaves.MOD_ID, "textures/misc/dinosaur_hud_overlays.png");
    public static final ResourceLocation ARMOR_HUD_OVERLAYS = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "textures/misc/armor_hud_overlays.png");
    public static final ResourceLocation SUBMARINE_SHADER = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "shaders/post/submarine_light.json");
    public static final ResourceLocation WATCHER_SHADER = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "shaders/post/watcher_perspective.json");
    public static final ResourceLocation SUGAR_RUSH_SHADER = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "shaders/post/sugar_rush.json");
    public static final ResourceLocation TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "textures/particle/teletor_trail.png");
    public static final RenderType UNDERGROUND_CABIN_MAP_ICONS = RenderType.text(
            ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID, "textures/misc/underground_cabin_map_icons.png"));
    public static final ResourceLocation BOMB_FLASH = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "textures/misc/bomb_flash.png");
    public static final ResourceLocation WATCHER_EFFECT = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "textures/misc/watcher_effect.png");
    public static final ResourceLocation IRRADIATED_SHADER = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "shaders/post/irradiated.json");
    public static final ResourceLocation HOLOGRAM_SHADER = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "shaders/post/hologram.json");
    public static final ResourceLocation PURPLE_WITCH_SHADER = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID,
            "shaders/post/purple_witch.json");
    static final List<String> FULLBRIGHTS = ImmutableList.of("alexscaves:ambersol#",
            "alexscaves:radrock_uranium_ore#", "alexscaves:acidic_radrock#", "alexscaves:uranium_rod#axis=x",
            "alexscaves:uranium_rod#axis=y", "alexscaves:uranium_rod#axis=z", "alexscaves:block_of_uranium#",
            "alexscaves:abyssal_altar#active=true", "alexscaves:abyssmarine_", "alexscaves:peering_coprolith#",
            "alexscaves:forsaken_idol#", "alexscaves:magnetic_light#", "alexscaves:tremorzilla_egg#");
}
