package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.gui.NuclearFurnaceScreen;
import com.github.alexmodguy.alexscaves.client.gui.SpelunkeryTableScreen;
import com.github.alexmodguy.alexscaves.client.render.blockentity.*;
import com.github.alexmodguy.alexscaves.client.render.item.ACItemRenderProperties;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.blockentity.ACBlockEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.util.MagneticEntityAccessor;
import com.github.alexmodguy.alexscaves.server.inventory.ACMenuRegistry;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class AlexsCavesClient {

    private static final ACItemRenderProperties isterProperties = new ACItemRenderProperties();

    public static void init() {
        registerExtensions(
                isterProperties,
                ACItemRegistry.CAVE_MAP,
                ACItemRegistry.GALENA_GAUNTLET,
                ACItemRegistry.RESISTOR_SHIELD,
                ACItemRegistry.PRIMITIVE_CLUB,
                ACItemRegistry.LIMESTONE_SPEAR,
                ACItemRegistry.EXTINCTION_SPEAR,
                ACBlockRegistry.SIREN_LIGHT,
                ACItemRegistry.RAYGUN,
                ACItemRegistry.SEA_STAFF,
                ACItemRegistry.ORTHOLANCE,
                ACBlockRegistry.COPPER_VALVE,
                ACBlockRegistry.BEHOLDER,
                ACItemRegistry.DREADBOW,
                ACBlockRegistry.GOBTHUMPER,
                ACItemRegistry.SHOT_GUM,
                ACItemRegistry.SUGAR_STAFF,
                ACItemRegistry.FROSTMINT_SPEAR
        );

        Services.CLIENT_HELPER.setupEntityRotationsEvent(AlexsCavesClient::renderMagnetised);
        Services.CLIENT_HELPER.registerBlockEntityRenderers(AlexsCavesClient::registerBERenderers);
        Services.CLIENT_HELPER.registerMenuScreens(AlexsCavesClient::registerMenus);
    }

    // (ender) a neo special
    public static void lateInit() {
    }

    @SafeVarargs
    @SuppressWarnings("SameParameterValue")
    private static void registerExtensions(IClientPlatformHelper.RenderExtension ext, Supplier<? extends ItemLike>... items) {
        for (var item : items) {
            Services.CLIENT_HELPER.registerExtension(item, ext);
        }
    }

    public static void renderMagnetised(LivingEntity entity, float partialTicks, float bodyYRot, PoseStack poseStack) {
        if (entity instanceof MagneticEntityAccessor magnetic) {
            float width = entity.getBbWidth();
            float height = entity.getBbHeight();
            float progress = magnetic.getAttachmentProgress(partialTicks);
            float prevProg = 1F - progress;
            float bodyRot = 180.0F - bodyYRot;
            if (magnetic.getMagneticAttachmentFace().getAxis() != Direction.Axis.Y) {
                poseStack.mulPose(Axis.YN.rotationDegrees(bodyRot));
            }
            rotateForAngle(entity, poseStack, magnetic.getPrevMagneticAttachmentFace(), prevProg,
                    width, height);
            rotateForAngle(entity, poseStack, magnetic.getMagneticAttachmentFace(), progress,
                    width, height);
        }
    }

    public static void rotateForAngle(LivingEntity entity, PoseStack matrixStackIn, Direction rotate, float f, float width,
                                      float height) {
        boolean down = entity.zza < 0.0F;
        switch (rotate) {
            case DOWN:
                break;
            case UP:
                matrixStackIn.translate(0.0D, height * f, 0.0D);
                matrixStackIn.mulPose(Axis.XP.rotationDegrees(-180.0F * f));
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(-180.0F * f));
                break;
            case NORTH:
                matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F * f));
                matrixStackIn.translate(0.0D, -0.25f * f, 0.0D);
                if (down) {
                    matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F * f));
                }
                break;
            case SOUTH:
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(180 * f));
                matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F * f));
                matrixStackIn.translate(0.0D, -0.25f * f, 0.0D);
                if (down) {
                    matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F * f));
                }
                break;
            case WEST:
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(90 * f));
                matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F * f));
                matrixStackIn.translate(0.0D, -0.25f * f, 0.0D);
                if (down) {
                    matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F * f));
                }
                break;
            case EAST:
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(-90 * f));
                matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F * f));
                matrixStackIn.translate(0.0D, -0.25f * f, 0.0D);
                if (down) {
                    matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F * f));
                }
                break;
        }
    }

    public static void registerBERenderers(IClientPlatformHelper.BERendererRegistry event) {
        event.register(ACBlockEntityRegistry.MAGNET.get(), MagnetBlockRenderer::new);
        event.register(ACBlockEntityRegistry.TESLA_BULB.get(), TelsaBulbBlockRenderer::new);
        event.register(ACBlockEntityRegistry.HOLOGRAM_PROJECTOR.get(), HologramProjectorBlockRenderer::new);
        event.register(ACBlockEntityRegistry.QUARRY.get(), QuarryBlockRenderer::new);
        event.register(ACBlockEntityRegistry.AMBERSOL.get(), AmbersolBlockRenderer::new);
        event.register(ACBlockEntityRegistry.AMBER_MONOLITH.get(), AmberMonolithBlockRenderer::new);
        event.register(ACBlockEntityRegistry.NUCLEAR_FURNACE.get(), NuclearFurnaceBlockRenderer::new);
        event.register(ACBlockEntityRegistry.SIREN_LIGHT.get(), SirenLightBlockRenderer::new);
        event.register(ACBlockEntityRegistry.ABYSSAL_ALTAR.get(), AbyssalAltarBlockRenderer::new);
        event.register(ACBlockEntityRegistry.COPPER_VALVE.get(), CopperValveBlockRenderer::new);
        event.register(ACBlockEntityRegistry.BEHOLDER.get(), BeholderBlockRenderer::new);
        event.register(ACBlockEntityRegistry.GOBTHUMPER.get(), GobthumperBlockRenderer::new);
        event.register(ACBlockEntityRegistry.CONVERSION_CRUCIBLE.get(), ConversionCrucibleBlockRenderer::new);
    }

    public static void registerMenus(IClientPlatformHelper.MenuScreenRegistry reg) {
       reg.register(ACMenuRegistry.SPELUNKERY_TABLE_MENU.get(), SpelunkeryTableScreen::new);
       reg.register(ACMenuRegistry.NUCLEAR_FURNACE_MENU.get(), NuclearFurnaceScreen::new);
   }

}
