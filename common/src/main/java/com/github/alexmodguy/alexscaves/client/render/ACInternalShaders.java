package com.github.alexmodguy.alexscaves.client.render;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class ACInternalShaders {

    public static final ResourceLocation RENDERTYPE_FERROUSLIME_GEL = AlexsCaves.id("rendertype_ferrouslime_gel");
    public static final ResourceLocation RENDERTYPE_HOLOGRAM = AlexsCaves.id("rendertype_hologram");
    public static final ResourceLocation RENDERTYPE_IRRADIATED = AlexsCaves.id("rendertype_irradiated");
    public static final ResourceLocation RENDERTYPE_BLUE_IRRADIATED = AlexsCaves.id("rendertype_blue_irradiated");
    public static final ResourceLocation RENDERTYPE_BUBBLED = AlexsCaves.id("rendertype_bubbled");
    public static final ResourceLocation RENDERTYPE_SEPIA = AlexsCaves.id("rendertype_sepia");
    public static final ResourceLocation RENDERTYPE_RED_GHOST = AlexsCaves.id("rendertype_red_ghost");
    public static final ResourceLocation RENDERTYPE_PURPLE_WITCH = AlexsCaves.id("rendertype_purple_witch");

    private static ShaderInstance renderTypeFerrouslimeGelShader;
    private static ShaderInstance renderTypeHologramShader;
    private static ShaderInstance renderTypeIrradiatedShader;
    private static ShaderInstance renderTypeBlueIrradiatedShader;
    private static ShaderInstance renderTypeBubbledShader;
    private static ShaderInstance renderTypeSepiaShader;
    private static ShaderInstance renderTypeSepiaOutlineShader;
    private static ShaderInstance renderTypeRedGhostShader;
    private static ShaderInstance renderTypePurpleWitchShader;

    @Nullable
    public static ShaderInstance getRenderTypeFerrouslimeGelShader() {
        return renderTypeFerrouslimeGelShader;
    }

    public static void setRenderTypeFerrouslimeGelShader(ShaderInstance instance) {
        renderTypeFerrouslimeGelShader = instance;
    }

    public static void setRenderTypeHologramShader(ShaderInstance instance) {
        renderTypeHologramShader = instance;
    }

    @Nullable
    public static ShaderInstance getRenderTypeHologramShader() {
        return renderTypeHologramShader;
    }

    @Nullable
    public static ShaderInstance getRenderTypeIrradiatedShader() {
        return renderTypeIrradiatedShader;
    }

    public static void setRenderTypeIrradiatedShader(ShaderInstance instance) {
        renderTypeIrradiatedShader = instance;
    }

    @Nullable
    public static ShaderInstance getRenderTypeBlueIrradiatedShader() {
        return renderTypeBlueIrradiatedShader;
    }

    public static void setRenderTypeBlueIrradiatedShader(ShaderInstance instance) {
        renderTypeBlueIrradiatedShader = instance;
    }

    @Nullable
    public static ShaderInstance getRenderTypeBubbledShader() {
        return renderTypeBubbledShader;
    }

    public static void setRenderTypeBubbledShader(ShaderInstance instance) {
        renderTypeBubbledShader = instance;
    }

    @Nullable
    public static ShaderInstance getRenderTypeSepiaShader() {
        return renderTypeSepiaShader;
    }

    public static void setRenderTypeSepiaShader(ShaderInstance instance) {
        renderTypeSepiaShader = instance;
    }

    @Nullable
    public static ShaderInstance getRenderTypeRedGhostShader() {
        return renderTypeRedGhostShader;
    }

    public static void setRenderTypeRedGhostShader(ShaderInstance instance) {
        renderTypeRedGhostShader = instance;
    }

    @Nullable
    public static ShaderInstance getRenderTypePurpleWitchShader() {
        return renderTypePurpleWitchShader;
    }

    public static void setRenderTypePurpleWitchShader(ShaderInstance instance) {
        renderTypePurpleWitchShader = instance;
    }

}
