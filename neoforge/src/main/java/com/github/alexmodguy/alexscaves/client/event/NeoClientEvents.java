package com.github.alexmodguy.alexscaves.client.event;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.AlexsCavesClient;
import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.client.model.baked.BakedModelShadeLayerFullbright;
import com.github.alexmodguy.alexscaves.client.model.layered.ACModelLayers;
import com.github.alexmodguy.alexscaves.client.render.ACInternalShaders;
import com.github.alexmodguy.alexscaves.client.render.blockentity.AmbersolBlockRenderer;
import com.github.alexmodguy.alexscaves.client.render.blockentity.HologramProjectorBlockRenderer;
import com.github.alexmodguy.alexscaves.client.render.entity.CorrodentRenderer;
import com.github.alexmodguy.alexscaves.client.render.entity.LicowitchRenderer;
import com.github.alexmodguy.alexscaves.client.render.entity.SubmarineRenderer;
import com.github.alexmodguy.alexscaves.client.render.item.RaygunRenderHelper;
import com.github.alexmodguy.alexscaves.client.render.item.tooltip.ClientSackOfSatingTooltip;
import com.github.alexmodguy.alexscaves.mixin.client.CameraAccessor;
import com.github.alexmodguy.alexscaves.mixin.client.GameRendererAccessor;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.FrostedChocolateBlock;
import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import com.github.alexmodguy.alexscaves.server.entity.item.SubmarineEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.*;
import com.github.alexmodguy.alexscaves.server.entity.util.HeadRotationEntityAccessor;
import com.github.alexmodguy.alexscaves.server.entity.util.MagnetUtil;
import com.github.alexmodguy.alexscaves.server.entity.util.PossessesCamera;
import com.github.alexmodguy.alexscaves.server.entity.util.RidingMeterMount;
import com.github.alexmodguy.alexscaves.server.item.*;
import com.github.alexmodguy.alexscaves.server.item.tooltip.SackOfSatingTooltip;
import com.github.alexmodguy.alexscaves.server.misc.ACKeybindRegistry;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.github.alexmodguy.alexscaves.server.potion.DeepsightEffect;
import com.github.alexthe666.citadel.client.event.EventGetOutlineColor;
import com.github.alexthe666.citadel.client.event.EventPosePlayerHand;
import com.github.alexthe666.citadel.client.event.EventRenderSplashText;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.io.IOException;
import java.util.UUID;

import static com.github.alexmodguy.alexscaves.client.ClientConstants.*;
import static com.github.alexmodguy.alexscaves.client.ClientProxy.*;

public class NeoClientEvents {

    public static void addLayersEvent(EntityRenderersEvent.AddLayers event) {
        BuiltInRegistries.ENTITY_TYPE.stream()
                .filter(DefaultAttributes::hasSupplier)
                .map(entityType -> (EntityType<? extends LivingEntity>) entityType)
                .forEach(entityType -> {
                    AlexsCavesClient.addLayerIfApplicable(entityType, event.getRenderer(entityType));
                });


        for (PlayerSkin.Model modelType : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getSkin(modelType);
            if (renderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
                AlexsCavesClient.addLayerIfApplicable(EntityType.PLAYER, livingRenderer);
            }
        }
    }

    @SubscribeEvent
    public void preRenderLiving(RenderLivingEvent.Pre event) {
        if (event.getEntity() instanceof HeadRotationEntityAccessor magnetic) {
            magnetic.setMagnetHeadRotation();
        }

        if (ClientProxy.blockedEntityRenders.contains(event.getEntity().getUUID())) {
            if (!AlexsCaves.PROXY.isFirstPersonPlayer(event.getEntity())) {
                NeoForge.EVENT_BUS
                        .post(new RenderLivingEvent.Post(event.getEntity(), event.getRenderer(), event.getPartialTick(),
                                event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()));
                event.setCanceled(true);
            }
            ClientProxy.blockedEntityRenders.remove(event.getEntity().getUUID());
        }
    }

    @SubscribeEvent
    public void postRenderLiving(RenderLivingEvent.Post<?, ?> event) {
        ClientEvents.postLivingEntityRender(event.getEntity(), event.getPartialTick(), event.getMultiBufferSource(), event.getPoseStack(), event.getPackedLight());
    }

    private static void attemptLoadShader(ResourceLocation resourceLocation) {
        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        if (ClientProxy.shaderLoadAttemptCooldown <= 0) {
            renderer.loadEffect(resourceLocation);
            if (!((GameRendererAccessor) renderer).isEffectActive()) {
                ClientProxy.shaderLoadAttemptCooldown = 12000;
                AlexsCaves.LOGGER.warn(
                        "Alex's Caves could not load the shader {}, will attempt to load shader in 30 seconds",
                        resourceLocation);
            }
        }
    }

    @SubscribeEvent
    public void postRenderStage(RenderLevelStageEvent event) {
        Entity player = Minecraft.getInstance().getCameraEntity();
        boolean firstPerson = Minecraft.getInstance().options.getCameraType().isFirstPerson();
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            if (firstPerson && player instanceof LivingEntity living) {
                MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers()
                        .bufferSource();
                Vec3 cameraPos = event.getCamera().getPosition();
                RaygunRenderHelper.renderRaysFor(living, cameraPos, event.getPoseStack(),
                        multibuffersource$buffersource, event.getPartialTick().getGameTimeDeltaPartialTick(false), true,
                        2);
            }
            GameRenderer renderer = Minecraft.getInstance().gameRenderer;
            if (firstPerson && player.isPassenger() && player.getVehicle() instanceof SubmarineEntity submarine
                    && SubmarineRenderer.isFirstPersonFloodlightsMode(submarine)) {
                if (renderer.currentEffect() == null
                        || !SUBMARINE_SHADER.toString().equals(renderer.currentEffect().getName())) {
                    attemptLoadShader(SUBMARINE_SHADER);
                }
            } else if (renderer.currentEffect() != null
                    && SUBMARINE_SHADER.toString().equals(renderer.currentEffect().getName())) {
                renderer.checkEntityPostEffect(null);
            } else if (firstPerson && player instanceof PossessesCamera || player instanceof LivingEntity afflicted
                    && afflicted.hasEffect(ACEffectRegistry.DARKNESS_INCARNATE)) {
                if (renderer.currentEffect() == null
                        || !WATCHER_SHADER.toString().equals(renderer.currentEffect().getName())) {
                    attemptLoadShader(WATCHER_SHADER);
                }
            } else if (renderer.currentEffect() != null
                    && WATCHER_SHADER.toString().equals(renderer.currentEffect().getName())) {
                renderer.checkEntityPostEffect(null);
            } else if (player instanceof LivingEntity afflicted && afflicted.hasEffect(ACEffectRegistry.SUGAR_RUSH)
                    && AlexsCaves.CLIENT_CONFIG.sugarRushSaturationEffect.get()) {
                if (renderer.currentEffect() == null
                        || !SUGAR_RUSH_SHADER.toString().equals(renderer.currentEffect().getName())) {
                    attemptLoadShader(SUGAR_RUSH_SHADER);
                }
            } else if (renderer.currentEffect() != null
                    && SUGAR_RUSH_SHADER.toString().equals(renderer.currentEffect().getName())) {
                renderer.checkEntityPostEffect(null);
            }
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            if (firstPerson && player instanceof LivingEntity living) {
                MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers()
                        .bufferSource();
                Vec3 cameraPos = event.getCamera().getPosition();
                RaygunRenderHelper.renderRaysFor(living, cameraPos, event.getPoseStack(),
                        multibuffersource$buffersource, event.getPartialTick().getGameTimeDeltaPartialTick(false), true,
                        1);
            }
            RenderSystem.runAsFancy(() -> HologramProjectorBlockRenderer.renderEntireBatch(event.getLevelRenderer(),
                    event.getPoseStack(), event.getRenderTick(), event.getCamera(),
                    event.getPartialTick().getGameTimeDeltaPartialTick(false)));
            RenderSystem.runAsFancy(() -> CorrodentRenderer.renderEntireBatch(event.getLevelRenderer(),
                    event.getPoseStack(), event.getRenderTick(), event.getCamera(),
                    event.getPartialTick().getGameTimeDeltaPartialTick(false)));
            RenderSystem.runAsFancy(() -> LicowitchRenderer.renderEntireBatch(event.getLevelRenderer(),
                    event.getPoseStack(), event.getRenderTick(), event.getCamera(),
                    event.getPartialTick().getGameTimeDeltaPartialTick(false)));
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS
                && AlexsCaves.CLIENT_CONFIG.ambersolShines.get()) {
            RenderSystem.runAsFancy(() -> AmbersolBlockRenderer.renderEntireBatch(event.getLevelRenderer(),
                    event.getPoseStack(), event.getRenderTick(), event.getCamera(),
                    event.getPartialTick().getGameTimeDeltaPartialTick(false)));
        }
    }

    @SubscribeEvent
    public void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Entity player = Minecraft.getInstance().getCameraEntity();
        float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
        if (player instanceof PossessesCamera watcherEntity) {
            Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
        }
        // Screen shake logic has been moved to CameraMixin because ComputeCameraAngles
        // event fires BEFORE setPosition() in Camera.setup(), causing move() effects to be overwritten
        if (player != null && player.isPassenger() && player.getVehicle() instanceof SubmarineEntity
                && event.getCamera().isDetached()) {
            CameraAccessor cameraAccessor = (CameraAccessor) event.getCamera();
            cameraAccessor.invokeMove(-cameraAccessor.invokeGetMaxZoom(4.0F), 0, 0);
        }
        if (player != null && player.isPassenger() && player.getVehicle() instanceof TremorsaurusEntity
                && event.getCamera().isDetached()) {
            CameraAccessor cameraAccessor = (CameraAccessor) event.getCamera();
            cameraAccessor.invokeMove(-cameraAccessor.invokeGetMaxZoom(2.0F), 0, 0);
        }
        if (player != null && player.isPassenger() && player.getVehicle() instanceof AtlatitanEntity
                && event.getCamera().isDetached()) {
            CameraAccessor cameraAccessor = (CameraAccessor) event.getCamera();
            cameraAccessor.invokeMove(-cameraAccessor.invokeGetMaxZoom(4.0F), 0, 0);
        }
        if (player != null && player.isPassenger() && player.getVehicle() instanceof TremorzillaEntity
                && event.getCamera().isDetached()) {
            CameraAccessor cameraAccessor = (CameraAccessor) event.getCamera();
            cameraAccessor.invokeMove(-cameraAccessor.invokeGetMaxZoom(10.0F), 0, 0);
        }
        if (player != null && player.isPassenger() && player.getVehicle() instanceof GumWormSegmentEntity
                && event.getCamera().isDetached()) {
            CameraAccessor cameraAccessor = (CameraAccessor) event.getCamera();
            cameraAccessor.invokeMove(-cameraAccessor.invokeGetMaxZoom(12.0F), 0, 0);
        }
        if (player != null && player instanceof LivingEntity livingEntity
                && livingEntity.hasEffect(ACEffectRegistry.STUNNED)) {
            event.setRoll((float) (Math.sin((player.tickCount + partialTick) * 0.2F) * 10F));
        }
        Direction dir = MagnetUtil.getEntityMagneticDirection(player);

    }

    @SubscribeEvent
    public void clientLivingTick(EntityTickEvent.Post event) {
        ClientEvents.onClientLivingTick(event.getEntity());
    }

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        if (Minecraft.getInstance().getCameraEntity() instanceof PossessesCamera) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPreRenderGuiOverlay(RenderGuiLayerEvent.Pre event) {
        Entity player = Minecraft.getInstance().getCameraEntity();
        if (player instanceof PossessesCamera && (event.getName().equals(VanillaGuiLayers.CROSSHAIR)
                || event.getName().equals(VanillaGuiLayers.EXPERIENCE_BAR)
                || event.getName().equals(VanillaGuiLayers.JUMP_METER)
                || event.getName().equals(VanillaGuiLayers.SELECTED_ITEM_NAME))) {
            event.setCanceled(true);
        }
        if (player != null && player.getVehicle() instanceof RidingMeterMount dinosaur && dinosaur.hasRidingMeter()
                && event.getName().equals(VanillaGuiLayers.EXPERIENCE_BAR)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPoseHand(EventPosePlayerHand event) {
        if (ClientEvents.onPoseHand((LivingEntity) event.getEntityIn(), event.getModel(), event.getResult() != TriState.TRUE, event.getEntityIn())) {
            event.setResult(TriState.TRUE);
        }
    }

    @SubscribeEvent
    public void onPostRenderGuiOverlay(RenderGuiLayerEvent.Post event) {
        Player player = AlexsCaves.PROXY.getClientSidePlayer();
        int hudY = 0;
        if (event.getName().equals(VanillaGuiLayers.HOTBAR) && player.getVehicle() instanceof RidingMeterMount mount
                && mount.hasRidingMeter()) {
            int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            Gui gui = Minecraft.getInstance().gui;
            int forgeGuiY = Math.max(gui.leftHeight, gui.rightHeight);
            if (player.getArmorValue() > 0 && mount instanceof SubterranodonEntity) {
                forgeGuiY += 25;
            }
            if (forgeGuiY < 53) {
                forgeGuiY = 53;
            }
            int j = screenWidth / 2 - AlexsCaves.CLIENT_CONFIG.subterranodonIndicatorX.get();
            int k = screenHeight - forgeGuiY - AlexsCaves.CLIENT_CONFIG.subterranodonIndicatorY.get();
            float f = mount.getMeterAmount();
            float invProgress = 1 - f;
            int uOffset = 0;
            int vOffset = 0;
            int dinoHeight = 31;
            if (mount instanceof TremorsaurusEntity) {
                vOffset = 63;
                k += 5;
                hudY = 20;
            } else if (mount instanceof AtlatitanEntity) {
                vOffset = 126;
                dinoHeight = 32;
                k += 3;
                hudY = 40;
            } else if (mount instanceof TremorzillaEntity tremorzilla) {
                vOffset = 193;
                if (tremorzilla.isPowered() && !tremorzilla.isFiring() && tremorzilla.getSpikesDownAmount() > 0) {
                    if (tremorzilla.tickCount / 2 % 2 == 1) {
                        vOffset = 251;
                    }
                    invProgress = 1F;
                }
                dinoHeight = 29;
                k += 5;
                hudY = 20;
            } else if (mount instanceof CandicornEntity) {
                vOffset = 280;
                dinoHeight = 25;
                hudY = 40;
                k += 4;
            } else {
                hudY = 40;
            }
            event.getGuiGraphics().pose().pushPose();
            event.getGuiGraphics().blit(DINOSAUR_HUD_OVERLAYS, j, k, 50, uOffset, vOffset + dinoHeight, 43, dinoHeight,
                    128, 512);
            event.getGuiGraphics().blit(DINOSAUR_HUD_OVERLAYS, j, k, 50, uOffset, vOffset, 43,
                    (int) Math.floor(dinoHeight * invProgress), 128, 512);
            event.getGuiGraphics().pose().popPose();
        }
        if (event.getName().equals(VanillaGuiLayers.HOTBAR) && DarknessArmorItem.hasMeter(player)) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            int forgeGuiY = Math.max(Minecraft.getInstance().gui.leftHeight, Minecraft.getInstance().gui.rightHeight);
            if (forgeGuiY < 53) {
                forgeGuiY = 53;
            }
            int j = screenWidth / 2 - AlexsCaves.CLIENT_CONFIG.subterranodonIndicatorX.get() + 13;
            int k = screenHeight - forgeGuiY - AlexsCaves.CLIENT_CONFIG.subterranodonIndicatorY.get() + 9 - hudY;
            float f = DarknessArmorItem.getMeterProgress(stack);
            float invProgress = 1 - f;
            int uvOffset = DarknessArmorItem.canChargeUp(stack) && f >= 1.0F ? 0 : 18;
            event.getGuiGraphics().pose().pushPose();
            event.getGuiGraphics().blit(ARMOR_HUD_OVERLAYS, j, k, 50, uvOffset, 19, 18, 19, 128, 128);
            event.getGuiGraphics().blit(ARMOR_HUD_OVERLAYS, j, k, 50, 0, 0, 18, (int) Math.floor(19 * invProgress), 128,
                    128);
            event.getGuiGraphics().pose().popPose();
        }
        if (event.getName().equals(VanillaGuiLayers.PLAYER_HEALTH) && Minecraft.getInstance().gameMode.canHurtPlayer()
                && Minecraft.getInstance().getCameraEntity() instanceof Player
                && player.hasEffect(ACEffectRegistry.IRRADIATED)) {
            int leftHeight = 39;
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            int health = Mth.ceil(player.getHealth());
            int forgeGuiTick = Minecraft.getInstance().gui.getGuiTicks();
            AttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            float healthMax = (float) attrMaxHealth.getValue();
            float absorb = Mth.ceil(player.getAbsorptionAmount());

            int healthRows = Mth.ceil((healthMax + absorb) / 2.0F / 10.0F);
            int rowHeight = Math.max(10 - (healthRows - 2), 3);

            ClientProxy.RANDOM.setSeed(forgeGuiTick * 312871L);
            int left = width / 2 - 91;
            int top = height - leftHeight;
            int regen = -1;
            if (player.hasEffect(MobEffects.REGENERATION)) {
                regen = forgeGuiTick % Mth.ceil(healthMax + 5.0F);
            }
            final int heartV = player.level().getLevelData().isHardcore() ? 9 : 0;
            int heartU = 0;
            float absorbRemaining = absorb;
            event.getGuiGraphics().pose().pushPose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, POTION_EFFECT_HUD_OVERLAYS);
            for (int i = Mth.ceil((healthMax + absorb) / 2.0F) - 1; i >= 0; --i) {
                int row = Mth.ceil((float) (i + 1) / 10.0F) - 1;
                int x = left + i % 10 * 8;
                int y = top - row * rowHeight;
                if (health <= 4) {
                    y += ClientProxy.RANDOM.nextInt(2);
                }
                if (i == regen) {
                    y -= 2;
                }
                event.getGuiGraphics().blit(POTION_EFFECT_HUD_OVERLAYS, x, y, 50, heartU, heartV + 18, 9, 9, 32, 32);
                if (absorbRemaining > 0.0F) {
                    if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
                        event.getGuiGraphics().blit(POTION_EFFECT_HUD_OVERLAYS, x, y, 50, heartU + 9, heartV, 9, 9, 32,
                                32);
                        absorbRemaining -= 1.0F;
                    } else {
                        event.getGuiGraphics().blit(POTION_EFFECT_HUD_OVERLAYS, x, y, 50, heartU, heartV, 9, 9, 32, 32);
                        absorbRemaining -= 2.0F;
                    }
                } else {
                    if (i * 2 + 1 < health) {
                        event.getGuiGraphics().blit(POTION_EFFECT_HUD_OVERLAYS, x, y, 50, heartU, heartV, 9, 9, 32, 32);
                    } else if (i * 2 + 1 == health) {
                        event.getGuiGraphics().blit(POTION_EFFECT_HUD_OVERLAYS, x, y, 50, heartU + 9, heartV, 9, 9, 32,
                                32);
                    }
                }
            }
            event.getGuiGraphics().pose().popPose();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderBossOverlay(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (ClientProxy.bossBarRenderTypes.containsKey(event.getBossEvent().getId())) {
            int renderTypeFor = ClientProxy.bossBarRenderTypes.get(event.getBossEvent().getId());
            int i = event.getGuiGraphics().guiWidth();
            int j = event.getY();
            Component component = event.getBossEvent().getName();
            if (renderTypeFor == 0) {
                event.setCanceled(true);
                event.getGuiGraphics().blit(BOSS_BAR_HUD_OVERLAYS, event.getX(), event.getY(), 0, 0, 182, 15);
                int progressScaled = (int) (event.getBossEvent().getProgress() * 183.0F);
                event.getGuiGraphics().blit(BOSS_BAR_HUD_OVERLAYS, event.getX(), event.getY(), 0, 15, progressScaled,
                        15);
                int l = Minecraft.getInstance().font.width(component);
                int i1 = i / 2 - l / 2;
                int j1 = j - 9;
                PoseStack poseStack = event.getGuiGraphics().pose();
                poseStack.pushPose();
                poseStack.translate(i1, j1, 0);
                Minecraft.getInstance().font.drawInBatch8xOutline(component.getVisualOrderText(), 0.0F, 0.0F, 0XFF5100,
                        0X361515, poseStack.last().pose(), event.getGuiGraphics().bufferSource(), 240);
                poseStack.popPose();
                event.setIncrement(event.getIncrement() + 7);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void fogRender(ViewportEvent.RenderFog event) {
        if (event.isCanceled()) {
            // another mod has cancelled fog rendering.
            return;
        }
        // some mods incorrectly set the RenderSystem fog start and end directly, so
        // this will have to do as a band-aid...
        float defaultFarPlaneDistance = RenderSystem.getShaderFogEnd();
        float defaultNearPlaneDistance = RenderSystem.getShaderFogStart();

        Entity player = Minecraft.getInstance().getCameraEntity();
        FluidState fluidstate = player.level().getFluidState(event.getCamera().getBlockPosition());
        BlockState blockState = player.level().getBlockState(event.getCamera().getBlockPosition());
        if (!fluidstate.isEmpty()
                && fluidstate.getType().getFluidType().equals(ACFluidRegistry.ACID_FLUID_TYPE.get())) {
            event.setCanceled(true);
            float farness = 10.0F;
            if (Minecraft.getInstance().player.hasEffect(ACEffectRegistry.DEEPSIGHT)) {
                farness *= 1.0F + 1.5F
                        * DeepsightEffect.getIntensity(Minecraft.getInstance().player, (float) event.getPartialTick());
            }
            event.setFarPlaneDistance(farness);
            event.setNearPlaneDistance(0.0F);
            return;
        }
        if (!fluidstate.isEmpty()
                && fluidstate.getType().getFluidType().equals(ACFluidRegistry.PURPLE_SODA_FLUID_TYPE.get())) {
            event.setCanceled(true);
            float farness = 20.0F;
            float nearness = -8.0F;
            if (Minecraft.getInstance().player.hasEffect(ACEffectRegistry.DEEPSIGHT)) {
                float f = DeepsightEffect.getIntensity(Minecraft.getInstance().player, (float) event.getPartialTick());
                farness *= 1.0F + 1.5F * f;
                nearness *= 1.0F - f;
            }
            event.setFarPlaneDistance(farness);
            event.setNearPlaneDistance(nearness);
            return;
        }
        if (blockState.is(ACBlockRegistry.PRIMAL_MAGMA.get())
                || blockState.is(ACBlockRegistry.FISSURE_PRIMAL_MAGMA.get())) {
            event.setCanceled(true);
            float farness = 2.0F;
            if (Minecraft.getInstance().player.hasEffect(ACEffectRegistry.DEEPSIGHT)) {
                farness *= 1.0F + 1.5F
                        * DeepsightEffect.getIntensity(Minecraft.getInstance().player, (float) event.getPartialTick());
            }
            event.setFarPlaneDistance(farness);
            event.setNearPlaneDistance(0.0F);
            return;
        }
        if (event.getCamera().getFluidInCamera() == FogType.WATER
                && AlexsCaves.CLIENT_CONFIG.biomeWaterFogOverrides.get()) {
            float farness = lastSampledWaterFogFarness;
            if (Minecraft.getInstance().player.hasEffect(ACEffectRegistry.DEEPSIGHT)) {
                farness *= 1.0F + 1.5F
                        * DeepsightEffect.getIntensity(Minecraft.getInstance().player, (float) event.getPartialTick());
            }
            if (farness != 1.0F) {
                event.setCanceled(true);
                event.setFarPlaneDistance(defaultFarPlaneDistance * farness);
            }
        } else if (event.getMode() == FogRenderer.FogMode.FOG_TERRAIN
                && AlexsCaves.CLIENT_CONFIG.biomeSkyFogOverrides.get()) {
            float nearness = lastSampledFogNearness;
            float primordialBossAmount = AlexsCaves.PROXY.getPrimordialBossActiveAmount((float) event.getPartialTick());
            boolean flag = Math.abs(nearness) - 1.0F < 0.01F;
            if (primordialBossAmount > 0.0F) {
                flag = true;
                nearness *= (1.0F - primordialBossAmount * 0.75F);
            }
            if (flag) {
                event.setCanceled(true);
                event.setNearPlaneDistance(defaultNearPlaneDistance * nearness);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void fogColor(ViewportEvent.ComputeFogColor event) {
        Entity player = Minecraft.getInstance().player;
        BlockState blockState = player.level().getBlockState(event.getCamera().getBlockPosition());
        if (blockState.is(ACBlockRegistry.PRIMAL_MAGMA.get())
                || blockState.is(ACBlockRegistry.FISSURE_PRIMAL_MAGMA.get())) {
            event.setRed(1F);
            event.setGreen(0.4F);
            event.setBlue((float) (0));
        } else if (player.getEyeInFluidType() != null
                && player.getEyeInFluidType().equals(ACFluidRegistry.ACID_FLUID_TYPE.get())) {
            event.setRed((float) (0));
            event.setGreen((float) (1));
            event.setBlue((float) (0));
        } else if (player.getEyeInFluidType() != null
                && player.getEyeInFluidType().equals(ACFluidRegistry.PURPLE_SODA_FLUID_TYPE.get())) {
            event.setRed(0.6F);
            event.setGreen(0.1F);
            event.setBlue(0.85F);
        } else if (event.getCamera().getFluidInCamera() == FogType.NONE
                && AlexsCaves.CLIENT_CONFIG.biomeSkyFogOverrides.get()) {
            float override = ClientProxy.acSkyOverrideAmount;
            float setR = event.getRed();
            float setG = event.getGreen();
            float setB = event.getBlue();

            boolean flag = false;
            if (override != 0.0F) {
                flag = true;
                Vec3 vec3 = lastSampledFogColor;
                setR = (float) (vec3.x - setR) * override + setR;
                setG = (float) (vec3.y - setG) * override + setG;
                setB = (float) (vec3.z - setB) * override + setB;
            }
            float primordialBossAmount = AlexsCaves.PROXY.getPrimordialBossActiveAmount((float) event.getPartialTick());
            if (primordialBossAmount > 0.0F) {
                flag = true;
                setR = (0.8F - setR) * primordialBossAmount + setR;
                setG = (0.2F - setG) * primordialBossAmount + setG;
                setB = (0.15F - setB) * primordialBossAmount + setB;
            }
            if (flag) {
                event.setRed(setR);
                event.setGreen(setG);
                event.setBlue(setB);
            }
        } else if (event.getCamera().getFluidInCamera() == FogType.WATER
                && AlexsCaves.CLIENT_CONFIG.biomeWaterFogOverrides.get()) {
            int i = Minecraft.getInstance().options.biomeBlendRadius().get();
            float override = ClientProxy.acSkyOverrideAmount;
            if (override != 0) {
                Vec3 vec3 = lastSampledWaterFogColor;
                event.setRed((float) (event.getRed() + (vec3.x - event.getRed()) * override));
                event.setGreen((float) (event.getGreen() + (vec3.y - event.getGreen()) * override));
                event.setBlue((float) (event.getBlue() + (vec3.z - event.getBlue()) * override));
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        ClientEvents.onClientTick(Minecraft.getInstance());
    }

    @SubscribeEvent
    public void onRenderBlockScreenEffect(RenderBlockScreenEffectEvent event) {
        Player player = event.getPlayer();
        if (player.isPassenger() && player.getVehicle() instanceof SubmarineEntity
                && event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.WATER) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onComputeFOV(ViewportEvent.ComputeFov event) {
        if (event.getCamera().getEntity() instanceof PossessesCamera) {
            event.setFOV(90);
        }
        Player player = Minecraft.getInstance().player;
        FogType fogtype = event.getCamera().getFluidInCamera();
        if (player != null && player.isPassenger() && player.getVehicle() instanceof SubmarineEntity
                && fogtype == FogType.WATER) {
            float f = (float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0D, 0.85714287F);
            event.setFOV(event.getFOV() / f);
        }
    }

    @SubscribeEvent
    public void onComputeFOVModifier(ComputeFovModifierEvent event) {
        ItemStack itemstack = event.getPlayer().getUseItem();
        if (event.getPlayer().isUsingItem()) {
            if (itemstack.is(ACItemRegistry.DREADBOW.get())) {
                int i = event.getPlayer().getTicksUsingItem();
                float f1 = (float) i / 20.0F;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                } else {
                    f1 *= f1;
                }
                event.setNewFovModifier(event.getFovModifier() * (1.0F - f1 * 0.15F));
            }
        }
    }

    @SubscribeEvent
    public void onSplashTextRender(EventRenderSplashText.Pre event) {
        if (hasACSplashText) {
            event.setResult(TriState.TRUE);
            event.setSplashText("30k downloads max");
            event.setSplashTextColor(0X00B6D5);
        }
    }

    @SubscribeEvent
    public void outlineColor(EventGetOutlineColor event) {
        if (Minecraft.getInstance().player.getUseItem() != null
                && Minecraft.getInstance().player.getUseItem().is(ACItemRegistry.TOTEM_OF_POSSESSION.get())) {
            ItemStack stack = Minecraft.getInstance().player.getUseItem();
            UUID boundUUID = TotemOfPossessionItem.getBoundEntityUUID(stack);
            if (boundUUID != null && boundUUID.equals(event.getEntityIn().getUUID())) {
                event.setResult(TriState.TRUE);
                event.setColor(0xFF0000);
            }
        }
        if (event.getEntityIn() instanceof ItemEntity item) {
            if (item.getItem().is(ACItemRegistry.TECTONIC_SHARD.get())) {
                event.setResult(TriState.TRUE);
                event.setColor(0XFFDB00);
            }
            if (item.getItem().is(ACItemRegistry.SWEET_TOOTH.get())) {
                event.setResult(TriState.TRUE);
                event.setColor(0XFF8ACD);
            }
        }
    }

    public static void clientInit(IEventBus bus) {
        NeoForge.EVENT_BUS.register(new NeoClientEvents());
        bus.addListener(NeoClientEvents::addLayersEvent);
        bus.addListener(NeoClientEvents::bakeModels);
        bus.addListener(NeoClientEvents::registerShaders);
        bus.addListener(NeoClientEvents::onItemColors);
        bus.addListener(NeoClientEvents::onBlockColors);
        bus.addListener((EntityRenderersEvent.RegisterRenderers event) -> AlexsCavesClient.registerEntityRenderers(event::registerEntityRenderer));
        bus.addListener((RegisterKeyMappingsEvent event) -> event.register(ACKeybindRegistry.KEY_SPECIAL_ABILITY));
        bus.addListener((EntityRenderersEvent.RegisterLayerDefinitions event) -> ACModelLayers.register(event::registerLayerDefinition));
        bus.addListener((RegisterClientTooltipComponentFactoriesEvent event) -> event.register(SackOfSatingTooltip.class, ClientSackOfSatingTooltip::new));
        bus.addListener((RegisterParticleProvidersEvent event) -> {
            AlexsCavesClient.registerSpecialProviders(event::registerSpecial);
            AlexsCavesClient.registerSpriteProviders(event::registerSpriteSet);
        });
    }

    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, colorIn) -> colorIn != 1 ? -1
                        : CaveInfoItem.getBiomeColorOf(Minecraft.getInstance().level, stack, false) | 0xFF000000,
                ACItemRegistry.CAVE_TABLET.get(), ACItemRegistry.CAVE_CODEX.get());
        event.register((stack, colorIn) -> colorIn != 0 ? -1 : GazingPearlItem.getPearlColor(stack),
                ACItemRegistry.GAZING_PEARL.get());
        event.register((stack, colorIn) -> colorIn != 0 ? -1 : JellyBeanItem.getBeanColor(stack),
                ACItemRegistry.JELLY_BEAN.get());
        event.register(
                (stack, colorIn) -> colorIn != 1 ? -1
                        : BiomeTreatItem.getBiomeTreatColorOf(Minecraft.getInstance().level, stack) | 0xFF000000,
                ACItemRegistry.BIOME_TREAT.get());
    }

    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                (blockState, blockAndTintGetter, blockPos, colorIn) -> colorIn != 0 ? -1 : FrostedChocolateBlock.calculateFrostingColor(blockPos),
                ACBlockRegistry.BLOCK_OF_FROSTED_CHOCOLATE.get(), ACBlockRegistry.BLOCK_OF_FROSTING.get()
        );
    }


    private static void bakeModels(final ModelEvent.ModifyBakingResult e) {
        if (AlexsCaves.CLIENT_CONFIG.emissiveBlockModels.get()) {
            long time = System.currentTimeMillis();
            for (ModelResourceLocation id : e.getModels().keySet()) {
                if (FULLBRIGHTS.stream().anyMatch(str -> id.toString().startsWith(str))) {
                    e.getModels().put(id, new BakedModelShadeLayerFullbright(e.getModels().get(id)));
                }
            }
            AlexsCaves.LOGGER.info("Loaded emissive block models in {} ms", System.currentTimeMillis() - time);

        }
    }

    // TODO change this to be more common (it can very easily)
    private static void registerShaders(final RegisterShadersEvent e) {
        try {
            e.registerShader(new ShaderInstance(e.getResourceProvider(),
                    ACInternalShaders.RENDERTYPE_FERROUSLIME_GEL,
                    DefaultVertexFormat.NEW_ENTITY), ACInternalShaders::setRenderTypeFerrouslimeGelShader);
            e.registerShader(new ShaderInstance(e.getResourceProvider(),
                    ACInternalShaders.RENDERTYPE_HOLOGRAM,
                    DefaultVertexFormat.POSITION_COLOR), ACInternalShaders::setRenderTypeHologramShader);
            e.registerShader(
                    new ShaderInstance(e.getResourceProvider(),
                            ACInternalShaders.RENDERTYPE_IRRADIATED,
                            DefaultVertexFormat.NEW_ENTITY),
                    ACInternalShaders::setRenderTypeIrradiatedShader);
            e.registerShader(
                    new ShaderInstance(e.getResourceProvider(),
                            ACInternalShaders.RENDERTYPE_BLUE_IRRADIATED,
                            DefaultVertexFormat.NEW_ENTITY),
                    ACInternalShaders::setRenderTypeBlueIrradiatedShader);
            e.registerShader(new ShaderInstance(e.getResourceProvider(),
                    ACInternalShaders.RENDERTYPE_BUBBLED,
                    DefaultVertexFormat.NEW_ENTITY), ACInternalShaders::setRenderTypeBubbledShader);
            e.registerShader(new ShaderInstance(e.getResourceProvider(),
                    ACInternalShaders.RENDERTYPE_SEPIA,
                    DefaultVertexFormat.NEW_ENTITY), ACInternalShaders::setRenderTypeSepiaShader);
            e.registerShader(new ShaderInstance(e.getResourceProvider(),
                    ACInternalShaders.RENDERTYPE_RED_GHOST,
                    DefaultVertexFormat.NEW_ENTITY), ACInternalShaders::setRenderTypeRedGhostShader);
            e.registerShader(new ShaderInstance(e.getResourceProvider(),
                    ACInternalShaders.RENDERTYPE_PURPLE_WITCH,
                    DefaultVertexFormat.NEW_ENTITY), ACInternalShaders::setRenderTypePurpleWitchShader);
            AlexsCaves.LOGGER.info("registered internal shaders");
        } catch (IOException exception) {
            AlexsCaves.LOGGER.error("could not register internal shaders");
            exception.printStackTrace();
        }
    }

}
