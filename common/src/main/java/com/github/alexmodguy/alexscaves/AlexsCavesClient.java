package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.gui.NuclearFurnaceScreen;
import com.github.alexmodguy.alexscaves.client.gui.SpelunkeryTableScreen;
import com.github.alexmodguy.alexscaves.client.render.blockentity.*;
import com.github.alexmodguy.alexscaves.client.render.entity.*;
import com.github.alexmodguy.alexscaves.client.render.item.ACArmorRenderProperties;
import com.github.alexmodguy.alexscaves.client.render.item.ACItemRenderProperties;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.platform.services.IClientPlatformHelper;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.blockentity.ACBlockEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.util.MagneticEntityAccessor;
import com.github.alexmodguy.alexscaves.server.inventory.ACMenuRegistry;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class AlexsCavesClient {

    private static final ACItemRenderProperties isterProperties = new ACItemRenderProperties();
    private static final ACArmorRenderProperties armorProperties = new ACArmorRenderProperties();

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

        registerExtensions(
                armorProperties,
                ACItemRegistry.PRIMORDIAL_HELMET,
                ACItemRegistry.PRIMORDIAL_TUNIC,
                ACItemRegistry.PRIMORDIAL_PANTS,
                ACItemRegistry.HAZMAT_MASK,
                ACItemRegistry.HAZMAT_CHESTPLATE,
                ACItemRegistry.HAZMAT_LEGGINGS,
                ACItemRegistry.HAZMAT_BOOTS,
                ACItemRegistry.DIVING_HELMET,
                ACItemRegistry.DIVING_CHESTPLATE,
                ACItemRegistry.DIVING_LEGGINGS,
                ACItemRegistry.DIVING_BOOTS,
                ACItemRegistry.HOOD_OF_DARKNESS,
                ACItemRegistry.CLOAK_OF_DARKNESS,
                ACItemRegistry.RAINBOUNCE_BOOTS,
                ACItemRegistry.GINGERBREAD_HELMET,
                ACItemRegistry.GINGERBREAD_CHESTPLATE,
                ACItemRegistry.GINGERBREAD_LEGGINGS,
                ACItemRegistry.GINGERBREAD_BOOTS
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

    public static void registerEntityRenderers(IClientPlatformHelper.EntityRendererRegistry event) {
        event.register(ACEntityRegistry.BOAT.get(), (context) -> new AlexsCavesBoatRenderer<>(context, false));
        event.register(ACEntityRegistry.CHEST_BOAT.get(), (context) -> new AlexsCavesBoatRenderer<>(context, true));
        event.register(ACEntityRegistry.MOVING_METAL_BLOCK.get(), MovingMetalBlockRenderer::new);
        event.register(ACEntityRegistry.TELETOR.get(), TeletorRenderer::new);
        event.register(ACEntityRegistry.MAGNETIC_WEAPON.get(), MagneticWeaponRenderer::new);
        event.register(ACEntityRegistry.MAGNETRON.get(), MagnetronRenderer::new);
        event.register(ACEntityRegistry.BOUNDROID.get(), BoundroidRenderer::new);
        event.register(ACEntityRegistry.BOUNDROID_WINCH.get(), BoundroidWinchRenderer::new);
        event.register(ACEntityRegistry.FERROUSLIME.get(), FerrouslimeRenderer::new);
        event.register(ACEntityRegistry.NOTOR.get(), NotorRenderer::new);
        event.register(ACEntityRegistry.QUARRY_SMASHER.get(), QuarrySmasherRenderer::new);
        event.register(ACEntityRegistry.SEEKING_ARROW.get(), SeekingArrowRenderer::new);
        event.register(ACEntityRegistry.SUBTERRANODON.get(), SubterranodonRenderer::new);
        event.register(ACEntityRegistry.VALLUMRAPTOR.get(), VallumraptorRenderer::new);
        event.register(ACEntityRegistry.GROTTOCERATOPS.get(), GrottoceratopsRenderer::new);
        event.register(ACEntityRegistry.TRILOCARIS.get(), TrilocarisRenderer::new);
        event.register(ACEntityRegistry.TREMORSAURUS.get(), TremorsaurusRenderer::new);
        event.register(ACEntityRegistry.RELICHEIRUS.get(), RelicheirusRenderer::new);
        event.register(ACEntityRegistry.FALLING_TREE_BLOCK.get(), FallingTreeBlockRenderer::new);
        event.register(ACEntityRegistry.CRUSHED_BLOCK.get(), CrushedBlockRenderer::new);
        event.register(ACEntityRegistry.LIMESTONE_SPEAR.get(), LimestoneSpearRenderer::new);
        event.register(ACEntityRegistry.EXTINCTION_SPEAR.get(), ExtinctionSpearRenderer::new);
        event.register(ACEntityRegistry.DINOSAUR_SPIRIT.get(), DinosaurSpiritRenderer::new);
        event.register(ACEntityRegistry.LUXTRUCTOSAURUS.get(), LuxtructosaurusRenderer::new);
        event.register(ACEntityRegistry.TEPHRA.get(), TephraRenderer::new);
        event.register(ACEntityRegistry.ATLATITAN.get(), AtlatitanRenderer::new);
        event.register(ACEntityRegistry.NUCLEAR_EXPLOSION.get(), EmptyRenderer::new);
        event.register(ACEntityRegistry.NUCLEAR_BOMB.get(), NuclearBombRenderer::new);
        event.register(ACEntityRegistry.NUCLEEPER.get(), NucleeperRenderer::new);
        event.register(ACEntityRegistry.RADGILL.get(), RadgillRenderer::new);
        event.register(ACEntityRegistry.BRAINIAC.get(), BrainiacRenderer::new);
        event.register(ACEntityRegistry.THROWN_WASTE_DRUM.get(), ThrownWasteDrumEntityRenderer::new);
        event.register(ACEntityRegistry.GAMMAROACH.get(), GammaroachRenderer::new);
        event.register(ACEntityRegistry.RAYCAT.get(), RaycatRenderer::new);
        event.register(ACEntityRegistry.CINDER_BRICK.get(), (context) -> new ThrownItemRenderer<>(context, 1.25F, false));
        event.register(ACEntityRegistry.TREMORZILLA.get(), TremorzillaRenderer::new);
        event.register(ACEntityRegistry.LANTERNFISH.get(), LanternfishRenderer::new);
        event.register(ACEntityRegistry.SEA_PIG.get(), SeaPigRenderer::new);
        event.register(ACEntityRegistry.SUBMARINE.get(), SubmarineRenderer::new);
        event.register(ACEntityRegistry.HULLBREAKER.get(), HullbreakerRenderer::new);
        event.register(ACEntityRegistry.GOSSAMER_WORM.get(), GossamerWormRenderer::new);
        event.register(ACEntityRegistry.TRIPODFISH.get(), TripodfishRenderer::new);
        event.register(ACEntityRegistry.DEEP_ONE.get(), DeepOneRenderer::new);
        event.register(ACEntityRegistry.INK_BOMB.get(), (context) -> new ThrownItemRenderer<>(context, 1.25F, false));
        event.register(ACEntityRegistry.DEEP_ONE_KNIGHT.get(), DeepOneKnightRenderer::new);
        event.register(ACEntityRegistry.DEEP_ONE_MAGE.get(), DeepOneMageRenderer::new);
        event.register(ACEntityRegistry.WATER_BOLT.get(), WaterBoltRenderer::new);
        event.register(ACEntityRegistry.WAVE.get(), WaveRenderer::new);
        event.register(ACEntityRegistry.MINE_GUARDIAN.get(), MineGuardianRenderer::new);
        event.register(ACEntityRegistry.MINE_GUARDIAN_ANCHOR.get(), MineGuardianAnchorRenderer::new);
        event.register(ACEntityRegistry.DEPTH_CHARGE.get(), (context) -> new ThrownItemRenderer<>(context, 1.75F, true));
        event.register(ACEntityRegistry.FLOATER.get(), FloaterRenderer::new);
        event.register(ACEntityRegistry.GUANO.get(), (context) -> new ThrownItemRenderer<>(context, 1.25F, false));
        event.register(ACEntityRegistry.FALLING_GUANO.get(), FallingBlockRenderer::new);
        event.register(ACEntityRegistry.GLOOMOTH.get(), GloomothRenderer::new);
        event.register(ACEntityRegistry.UNDERZEALOT.get(), UnderzealotRenderer::new);
        event.register(ACEntityRegistry.WATCHER.get(), WatcherRenderer::new);
        event.register(ACEntityRegistry.CORRODENT.get(), CorrodentRenderer::new);
        event.register(ACEntityRegistry.VESPER.get(), VesperRenderer::new);
        event.register(ACEntityRegistry.FORSAKEN.get(), ForsakenRenderer::new);
        event.register(ACEntityRegistry.BEHOLDER_EYE.get(), EmptyRenderer::new);
        event.register(ACEntityRegistry.DESOLATE_DAGGER.get(), DesolateDaggerRenderer::new);
        event.register(ACEntityRegistry.BURROWING_ARROW.get(), BurrowingArrowRenderer::new);
        event.register(ACEntityRegistry.DARK_ARROW.get(), DarkArrowRenderer::new);
        event.register(ACEntityRegistry.SWEETISH_FISH.get(), SweetishFishRenderer::new);
        event.register(ACEntityRegistry.CANIAC.get(), CaniacRenderer::new);
        event.register(ACEntityRegistry.GUMBEEPER.get(), GumbeeperRenderer::new);
        event.register(ACEntityRegistry.GUMBALL.get(), GumballRenderer::new);
        event.register(ACEntityRegistry.CANDICORN.get(), CandicornRenderer::new);
        event.register(ACEntityRegistry.GUM_WORM.get(), GumWormRenderer::new);
        event.register(ACEntityRegistry.GUM_WORM_SEGMENT.get(), GumWormSegmentRenderer::new);
        event.register(ACEntityRegistry.CARAMEL_CUBE.get(), CaramelCubeRenderer::new);
        event.register(ACEntityRegistry.MELTED_CARAMEL.get(), MeltedCaramelRenderer::new);
        event.register(ACEntityRegistry.GUMMY_BEAR.get(), GummyBearRenderer::new);
        event.register(ACEntityRegistry.LICOWITCH.get(), LicowitchRenderer::new);
        event.register(ACEntityRegistry.SPINNING_PEPPERMINT.get(), SpinningPeppermintRenderer::new);
        event.register(ACEntityRegistry.SUGAR_STAFF_HEX.get(), SugarStaffHexRenderer::new);
        event.register(ACEntityRegistry.GINGERBREAD_MAN.get(), GingerbreadManRenderer::new);
        event.register(ACEntityRegistry.FALLING_FROSTMINT.get(), FallingBlockRenderer::new);
        event.register(ACEntityRegistry.CANDY_CANE_HOOK.get(), CandyCaneHookRenderer::new);
        event.register(ACEntityRegistry.SODA_BOTTLE_ROCKET.get(), (render) -> new ThrownItemRenderer<>(render, 1.25F, true));
        event.register(ACEntityRegistry.FROSTMINT_SPEAR.get(), FrostmintSpearRenderer::new);
        event.register(ACEntityRegistry.THROWN_ICE_CREAM_SCOOP.get(), (context) -> new ThrownItemRenderer<>(context, 1.25F, false));
    }
}
