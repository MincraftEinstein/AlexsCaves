package com.github.alexmodguy.alexscaves.client.event;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.client.gui.ACAdvancementTabs;
import com.github.alexmodguy.alexscaves.client.gui.NuclearFurnaceScreen;
import com.github.alexmodguy.alexscaves.client.gui.SpelunkeryTableScreen;
import com.github.alexmodguy.alexscaves.client.particle.*;
import com.github.alexmodguy.alexscaves.client.render.blockentity.*;
import com.github.alexmodguy.alexscaves.client.render.entity.*;
import com.github.alexmodguy.alexscaves.client.render.item.RaygunRenderHelper;
import com.github.alexmodguy.alexscaves.mixin.client.AdvancementTabAccessor;
import com.github.alexmodguy.alexscaves.mixin.client.AdvancementWidgetAccessor;
import com.github.alexmodguy.alexscaves.mixin.client.AdvancementsScreenAccessor;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.block.blockentity.ACBlockEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.item.BeholderEyeEntity;
import com.github.alexmodguy.alexscaves.server.entity.item.NuclearBombEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.GumWormSegmentEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.SubterranodonEntity;
import com.github.alexmodguy.alexscaves.server.entity.util.HeadRotationEntityAccessor;
import com.github.alexmodguy.alexscaves.server.entity.util.MagneticEntityAccessor;
import com.github.alexmodguy.alexscaves.server.entity.util.PossessesCamera;
import com.github.alexmodguy.alexscaves.server.inventory.ACMenuRegistry;
import com.github.alexmodguy.alexscaves.server.item.*;
import com.github.alexmodguy.alexscaves.server.level.biome.ACBiomeRegistry;
import com.github.alexmodguy.alexscaves.server.level.biome.BiomeSampler;
import com.github.alexmodguy.alexscaves.server.misc.ACVanillaMapUtil;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.github.alexmodguy.alexscaves.server.potion.DarknessIncarnateEffect;
import com.github.alexthe666.citadel.client.tick.ClientTickRateTracker;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Arrays;

import static com.github.alexmodguy.alexscaves.client.ClientConstants.TRAIL_TEXTURE;
import static com.github.alexmodguy.alexscaves.client.ClientConstants.UNDERGROUND_CABIN_MAP_ICONS;
import static com.github.alexmodguy.alexscaves.client.ClientProxy.*;
import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class ClientEvents {

    private static float calculateBiomeAmbientLight(Entity player) {
        int i = Minecraft.getInstance().options.biomeBlendRadius().get();
        if (i == 0) {
            return ACBiomeRegistry.getBiomeAmbientLight(player.level().getBiome(player.blockPosition()));
        } else {
            return BiomeSampler.sampleBiomesFloat(player.level(), player.position(),
                    ACBiomeRegistry::getBiomeAmbientLight);
        }
    }

    private static Vec3 calculateBiomeLightColor(Entity player) {
        int i = Minecraft.getInstance().options.biomeBlendRadius().get();
        if (i == 0) {
            return ACBiomeRegistry.getBiomeLightColorOverride(player.level().getBiome(player.blockPosition()));
        } else {
            return BiomeSampler.sampleBiomesVec3(player.level(), player.position(),
                    ACBiomeRegistry::getBiomeLightColorOverride);
        }
    }

    private static float calculateBiomeFogNearness(Entity player) {
        int i = Minecraft.getInstance().options.biomeBlendRadius().get();
        float nearness;
        if (i == 0) {
            nearness = ACBiomeRegistry.getBiomeFogNearness(player.level().getBiome(player.blockPosition()));
        } else {
            nearness = BiomeSampler.sampleBiomesFloat(player.level(), player.position(),
                    ACBiomeRegistry::getBiomeFogNearness);
        }
        return nearness;
    }

    private static float calculateBiomeWaterFogFarness(Entity player) {
        int i = Minecraft.getInstance().options.biomeBlendRadius().get();
        float farness;
        if (i == 0) {
            farness = ACBiomeRegistry.getBiomeWaterFogFarness(player.level().getBiome(player.blockPosition()));
        } else {
            farness = BiomeSampler.sampleBiomesFloat(player.level(), player.position(),
                    ACBiomeRegistry::getBiomeWaterFogFarness);
        }
        return farness;
    }

    private static Vec3 calculateBiomeFogColor(Entity player) {
        int i = Minecraft.getInstance().options.biomeBlendRadius().get();
        Vec3 vec3;
        if (i == 0) {
            vec3 = ((ClientLevel) player.level()).effects().getBrightnessDependentFogColor(Vec3.fromRGB24(player.level()
                    .getBiomeManager().getNoiseBiomeAtPosition(player.blockPosition()).value().getFogColor()), 1.0F);
        } else {
            vec3 = ((ClientLevel) player.level()).effects()
                    .getBrightnessDependentFogColor(BiomeSampler.sampleBiomesVec3(player.level(), player.position(),
                            biomeHolder -> Vec3.fromRGB24(biomeHolder.value().getFogColor())), 1.0F);
        }
        return vec3;
    }

    private static Vec3 calculateBiomeWaterFogColor(Entity player) {
        int i = Minecraft.getInstance().options.biomeBlendRadius().get();
        Vec3 vec3;
        if (i == 0) {
            vec3 = ((ClientLevel) player.level()).effects().getBrightnessDependentFogColor(Vec3.fromRGB24(player.level()
                            .getBiomeManager().getNoiseBiomeAtPosition(player.blockPosition()).value().getWaterFogColor()),
                    1.0F);
        } else {
            vec3 = ((ClientLevel) player.level()).effects()
                    .getBrightnessDependentFogColor(BiomeSampler.sampleBiomesVec3(player.level(), player.position(),
                            biomeHolder -> Vec3.fromRGB24(biomeHolder.value().getWaterFogColor())), 1.0F);
        }
        return vec3;
    }

    public static void onClientTick(Minecraft minecraft) {
        Entity cameraEntity = minecraft.cameraEntity;
        float partialTicks = AlexsCaves.PROXY.getPartialTicks();
        // Tick down bubbled effect visual timers
        tickBubbledEffects();
        if (ClientProxy.shaderLoadAttemptCooldown > 0) {
            ClientProxy.shaderLoadAttemptCooldown--;
        }
        ClientProxy.prevPrimordialBossActiveAmount = ClientProxy.primordialBossActiveAmount;
        ClientProxy.prevNukeFlashAmount = ClientProxy.nukeFlashAmount;
        if (cameraEntity != null) {
            ClientProxy.acSkyOverrideAmount = ACBiomeRegistry.calculateBiomeSkyOverride(cameraEntity);
            if (ClientProxy.acSkyOverrideAmount > 0) {
                ClientProxy.acSkyOverrideColor = BiomeSampler.sampleBiomesVec3(minecraft.level,
                        minecraft.cameraEntity.position(),
                        biomeHolder -> Vec3.fromRGB24(biomeHolder.value().getSkyColor()));
            }
            ClientProxy.lastBiomeLightColorPrev = ClientProxy.lastBiomeLightColor;
            ClientProxy.lastBiomeLightColor = calculateBiomeLightColor(cameraEntity);
            ClientProxy.lastBiomeAmbientLightAmountPrev = ClientProxy.lastBiomeAmbientLightAmount;
            ClientProxy.lastBiomeAmbientLightAmount = calculateBiomeAmbientLight(cameraEntity);
            lastSampledFogNearness = calculateBiomeFogNearness(cameraEntity);
            lastSampledWaterFogFarness = calculateBiomeWaterFogFarness(cameraEntity);
            if (cameraEntity.level() instanceof ClientLevel) { // fixes crash with beholder
                lastSampledFogColor = calculateBiomeFogColor(cameraEntity);
                lastSampledWaterFogColor = calculateBiomeWaterFogColor(cameraEntity);
            }
        }
        if (ClientProxy.renderNukeSkyDarkFor > 0) {
            ClientProxy.renderNukeSkyDarkFor--;
        }
        if (ClientProxy.muteNonNukeSoundsFor > 0) {
            ClientProxy.muteNonNukeSoundsFor--;
            if (ClientProxy.masterVolumeNukeModifier < 1.0F) {
                ClientProxy.masterVolumeNukeModifier += 0.1F;
            }
        } else if (ClientProxy.masterVolumeNukeModifier > 0.0F) {
            ClientProxy.masterVolumeNukeModifier -= 0.1F;
        }
        if (ClientProxy.lastBossLevel != minecraft.level) {
            ClientProxy.primordialBossActive = false;
            ClientProxy.primordialBossActiveAmount = 0;
            ClientProxy.lastBossLevel = minecraft.level;
        }
        if (ClientProxy.primordialBossActive) {
            if (ClientProxy.primordialBossActiveAmount < 1.0F) {
                ClientProxy.primordialBossActiveAmount += 0.025F;
            }
        } else {
            if (ClientProxy.primordialBossActiveAmount > 0.0F) {
                ClientProxy.primordialBossActiveAmount -= 0.025F;
            }
        }
        if (ClientProxy.renderNukeFlashFor > 0) {
            if (ClientProxy.nukeFlashAmount < 1F) {
                ClientProxy.nukeFlashAmount = Math.min(ClientProxy.nukeFlashAmount + 0.4F, 1F);
            }
            ClientProxy.renderNukeFlashFor--;
        } else if (ClientProxy.nukeFlashAmount > 0F) {
            ClientProxy.nukeFlashAmount = Math.max(ClientProxy.nukeFlashAmount - 0.05F, 0F);
        }
        ClientProxy.prevPossessionStrengthAmount = ClientProxy.possessionStrengthAmount;
        if (minecraft.getCameraEntity() instanceof PossessesCamera watcherEntity) {
            if (watcherEntity.instant()) {
                ClientProxy.possessionStrengthAmount = watcherEntity.getPossessionStrength(partialTicks);
            } else {
                if (ClientProxy.possessionStrengthAmount < watcherEntity.getPossessionStrength(partialTicks)) {
                    ClientProxy.possessionStrengthAmount = Math.min(ClientProxy.possessionStrengthAmount + 0.2F,
                            watcherEntity.getPossessionStrength(partialTicks));
                } else {
                    ClientProxy.possessionStrengthAmount = Math.max(ClientProxy.possessionStrengthAmount - 0.2F,
                            watcherEntity.getPossessionStrength(partialTicks));
                }
            }
            if (watcherEntity instanceof BeholderEyeEntity beholderEye) {
                beholderEye.setOldRots();
                beholderEye.setEyeYRot(minecraft.player.getYHeadRot());
                beholderEye.setEyeXRot(minecraft.player.getXRot());
                if (AlexsCaves.PROXY.isKeyDown(4)) {
                    AlexsCaves.PROXY.resetRenderViewEntity(minecraft.player);
                }
            }
        } else if (ClientProxy.possessionStrengthAmount > 0F) {
            ClientProxy.possessionStrengthAmount = Math.max(ClientProxy.possessionStrengthAmount - 0.05F, 0F);
        }
        if (minecraft.screen instanceof AdvancementsScreen advancementsScreen) {
            AdvancementTab selectedTab = ((AdvancementsScreenAccessor) advancementsScreen).getSelectedTab();
            if (selectedTab != null) {
                AdvancementWidget rootWidget = ((AdvancementTabAccessor) selectedTab).getRootWidget();
                if (rootWidget != null) {
                    AdvancementHolder holder = ((AdvancementWidgetAccessor) rootWidget).getAdvancementNode().holder();
                    if (ACAdvancementTabs.isAlexsCavesWidget(holder)) {
                        ACAdvancementTabs.tick();
                    }
                }
            }
        }
        if (ClientProxy.primordialBossActive && minecraft.level != null
                && !minecraft.isPaused()) {
            ClientLevel level = minecraft.level;
            BlockPos cameraBlockPos = minecraft.getCameraEntity().blockPosition();
            BlockPos.MutableBlockPos trySpawnParticleBlockPos = new BlockPos.MutableBlockPos();
            int dist = 16;
            for (int particles = 0; particles < 100; ++particles) {
                int i = cameraBlockPos.getX() + level.random.nextInt(dist) - level.random.nextInt(dist);
                int j = cameraBlockPos.getY() + level.random.nextInt(dist) - level.random.nextInt(dist);
                int k = cameraBlockPos.getZ() + level.random.nextInt(dist) - level.random.nextInt(dist);
                trySpawnParticleBlockPos.set(i, j, k);
                BlockState blockstate = level.getBlockState(trySpawnParticleBlockPos);
                if (!blockstate.isCollisionShapeFullBlock(level, trySpawnParticleBlockPos)) {
                    level.addParticle(ParticleTypes.ASH,
                            (double) trySpawnParticleBlockPos.getX() + level.random.nextDouble(),
                            (double) trySpawnParticleBlockPos.getY() + level.random.nextDouble(),
                            (double) trySpawnParticleBlockPos.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    /**
     * Ticks down all bubbled effect timers. Called from ClientEvents.
     */
    public static void tickBubbledEffects() {
        if (BUBBLED_EFFECT_TICKS.isEmpty()) {
            return;
        }
        var iterator = BUBBLED_EFFECT_TICKS.int2IntEntrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            int newValue = entry.getIntValue() - 1;
            if (newValue <= 0) {
                iterator.remove();
            } else {
                entry.setValue(newValue);
            }
        }
    }

    public static void onPlayerJoinClient(Player player) {
        if (AlexsCaves.COMMON_CONFIG.warnGenerationIncompatibility.get() && !AlexsCaves.MOD_GENERATION_CONFLICTS.isEmpty()) {
            for (String modid : AlexsCaves.MOD_GENERATION_CONFLICTS) {
                if (Services.PLATFORM_HELPER.isModLoaded(modid)) {
                    player.sendSystemMessage(Component.translatable("alexscaves.startup_warning.generation_incompatible", modid).withStyle(ChatFormatting.RED));
                }
            }
        }
    }

    public static void registerItemProperties() {
        ItemProperties.register(ACItemRegistry.HOLOCODER.get(), ResourceLocation.withDefaultNamespace("bound"),
                (stack, level, living, j) -> {
                    return HolocoderItem.isBound(stack) ? 1.0F : 0.0F;
                });
        ItemProperties.register(ACItemRegistry.DINOSAUR_NUGGET.get(), ResourceLocation.withDefaultNamespace("nugget"),
                (stack, level, living, j) -> {
                    return (stack.getCount() % 4) / 4F;
                });
        ItemProperties.register(ACItemRegistry.LIMESTONE_SPEAR.get(), ResourceLocation.withDefaultNamespace("throwing"),
                (stack, level, living, j) -> {
                    return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
                });
        ItemProperties.register(ACItemRegistry.EXTINCTION_SPEAR.get(),
                ResourceLocation.withDefaultNamespace("throwing"), (stack, level, living, j) -> {
                    return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
                });
        ItemProperties.register(ACItemRegistry.REMOTE_DETONATOR.get(), ResourceLocation.withDefaultNamespace("active"),
                (stack, level, living, j) -> {
                    return RemoteDetonatorItem.isActive(stack) ? 1.0F : 0.0F;
                });
        ItemProperties.register(ACItemRegistry.MAGIC_CONCH.get(), ResourceLocation.withDefaultNamespace("tooting"),
                (stack, level, living, j) -> {
                    return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
                });
        ItemProperties.register(ACItemRegistry.ORTHOLANCE.get(), ResourceLocation.withDefaultNamespace("charging"),
                (stack, level, living, j) -> {
                    return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
                });
        ItemProperties.register(ACItemRegistry.TOTEM_OF_POSSESSION.get(),
                ResourceLocation.withDefaultNamespace("totem"), (stack, level, living, j) -> {
                    return TotemOfPossessionItem.isBound(stack)
                            ? living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.5F
                            : 0.0F;
                });
        ItemProperties.register(ACItemRegistry.CANDY_CANE_HOOK.get(), ResourceLocation.withDefaultNamespace("cast"),
                (stack, level, holder, i) -> {
                    return holder != null && CandyCaneHookItem.isActive(stack) ? 1.0F : 0.0F;
                });
        ItemProperties.register(ACItemRegistry.SACK_OF_SATING.get(), ResourceLocation.withDefaultNamespace("open"),
                (stack, level, living, j) -> {
                    return level != null && SackOfSatingItem.isChewing(stack, level.getGameTime()) ? 1.0F
                            : stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).isEmpty()
                            || living instanceof Player player && player.containerMenu != null
                            && SackOfSatingItem.calculateWholeStackHungerValue(
                            player.containerMenu.getCarried(), player) > 0 ? 0.5F : 0.0F;
                });
        ItemProperties.register(ACItemRegistry.FROSTMINT_SPEAR.get(), ResourceLocation.withDefaultNamespace("throwing"),
                (stack, level, living, j) -> {
                    return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
                });
    }

    // Moved from ClientProxy probably isn't needed
//    public static void renderVanillaMapDecoration(MapDecoration mapDecoration, int index) {
//        ClientEvents.renderVanillaMapDecoration(mapDecoration, index + 1);
//    }

    public static void renderVanillaMapDecoration(MapDecoration mapdecoration, int k) {
        // In 1.21, mapdecoration.type() returns Holder<MapDecorationType>
        if (ACVanillaMapUtil.isUndergroundCabinDecoration(mapdecoration.type())) {
            MultiBufferSource multiBufferSource = lastVanillaMapRenderBuffer == null
                    ? Minecraft.getInstance().renderBuffers().bufferSource()
                    : lastVanillaMapRenderBuffer;
            PoseStack poseStack = lastVanillaMapPoseStack == null ? new PoseStack() : lastVanillaMapPoseStack;
            poseStack.pushPose();
            poseStack.translate(0.0F + (float) mapdecoration.x() / 2.0F + 64.0F,
                    0.0F + (float) mapdecoration.y() / 2.0F + 64.0F, -0.02F);
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) (mapdecoration.rot() * 360) / 16.0F));
            poseStack.scale(4.0F, 4.0F, 3.0F);
            poseStack.translate(-0.125F, 0.125F, 0.0F);
            byte b0 = ACVanillaMapUtil.getMapIconRenderOrdinal(mapdecoration.type());
            float f1 = (float) (b0 % 16 + 0) / 16.0F;
            float f2 = (float) (b0 / 16 + 0) / 16.0F;
            float f3 = (float) (b0 % 16 + 1) / 16.0F;
            float f4 = (float) (b0 / 16 + 1) / 16.0F;
            Matrix4f matrix4f1 = poseStack.last().pose();
            float f5 = -0.001F;
            VertexConsumer vertexconsumer1 = multiBufferSource.getBuffer(UNDERGROUND_CABIN_MAP_ICONS);
            vertexconsumer1.addVertex(matrix4f1, -1.0F, 1.0F, (float) k * -0.001F).setColor(255, 255, 255, 255)
                    .setUv(f1, f2).setLight(lastVanillaMapRenderPackedLight);
            vertexconsumer1.addVertex(matrix4f1, 1.0F, 1.0F, (float) k * -0.001F).setColor(255, 255, 255, 255)
                    .setUv(f3, f2).setLight(lastVanillaMapRenderPackedLight);
            vertexconsumer1.addVertex(matrix4f1, 1.0F, -1.0F, (float) k * -0.001F).setColor(255, 255, 255, 255)
                    .setUv(f3, f4).setLight(lastVanillaMapRenderPackedLight);
            vertexconsumer1.addVertex(matrix4f1, -1.0F, -1.0F, (float) k * -0.001F).setColor(255, 255, 255, 255)
                    .setUv(f1, f4).setLight(lastVanillaMapRenderPackedLight);
            poseStack.popPose();
            // mapdecoration.name() returns Optional<Component> in 1.21
            mapdecoration.name().ifPresent(component -> {
                Font font = Minecraft.getInstance().font;
                float f6 = (float) font.width(component);
                float f7 = Mth.clamp(25.0F / f6, 0.0F, 6.0F / 9.0F);
                poseStack.pushPose();
                poseStack.translate(0.0F + (float) mapdecoration.x() / 2.0F + 64.0F - f6 * f7 / 2.0F,
                        0.0F + (float) mapdecoration.y() / 2.0F + 64.0F + 4.0F, -0.025F);
                poseStack.scale(f7, f7, 1.0F);
                poseStack.translate(0.0F, 0.0F, -0.1F);
                font.drawInBatch(component, 0.0F, 0.0F, -1, false, poseStack.last().pose(), multiBufferSource,
                        Font.DisplayMode.NORMAL, Integer.MIN_VALUE, lastVanillaMapRenderPackedLight);
                poseStack.popPose();
            });
        }
    }

    public static void onClientLivingTick(Entity entity) {
        if (!entity.level().isClientSide) {
            return;
        }

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (livingEntity.hasEffect(ACEffectRegistry.DARKNESS_INCARNATE) && livingEntity.isAlive()) {
            int trailPointer = ClientProxy.darknessTrailPointerMap.getOrDefault(livingEntity, -1);
            Vec3 latest = livingEntity.position();
            if (ClientProxy.darknessTrailPosMap.get(livingEntity) == null) {
                Vec3[] trailPositions = new Vec3[64];
                if (trailPointer == -1) {
                    Arrays.fill(trailPositions, latest);
                }
                ClientProxy.darknessTrailPosMap.put(livingEntity, trailPositions);
            }
            if (++trailPointer == ClientProxy.darknessTrailPosMap.get(livingEntity).length) {
                trailPointer = 0;
            }
            ClientProxy.darknessTrailPointerMap.put(livingEntity, trailPointer);
            Vec3[] vector3ds = ClientProxy.darknessTrailPosMap.get(livingEntity);
            vector3ds[trailPointer] = latest;
            ClientProxy.darknessTrailPosMap.put(livingEntity, vector3ds);
        } else if (ClientProxy.darknessTrailPosMap.containsKey(livingEntity)) {
            ClientProxy.darknessTrailPosMap.remove(livingEntity);
            ClientProxy.darknessTrailPointerMap.remove(livingEntity);
        }
    }

    public static void postLivingEntityRender(LivingEntity entity, float partialTick, MultiBufferSource bufferSource, PoseStack poseStack, int packedLight) {
        if (entity instanceof HeadRotationEntityAccessor magnetic) {
            // TODO make this only reset when needed
//            magnetic.resetMagnetHeadRotation();
        }
        if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            RaygunRenderHelper.renderRaysFor(entity, entity.getPosition(partialTick), poseStack,
                    bufferSource, partialTick, false, 0);
        }
        if (entity.hasEffect(ACEffectRegistry.DARKNESS_INCARNATE) && entity.isAlive()) {
            Vec3 trailOffset = new Vec3(0, entity.getBbHeight() * 0.5F, 0);
            double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
            double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
            double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());
            int samples = 0;
            int sampleSize = 60;
            float trailHeight = entity.getBbHeight() * 0.8F;
            Vec3 topAngleVec = new Vec3(0, trailHeight, 0);
            Vec3 bottomAngleVec = new Vec3(0, -trailHeight, 0);
            Vec3 drawFrom = trailOffset;
            VertexConsumer vertexconsumer = bufferSource
                    .getBuffer(RenderType.entityTranslucent(TRAIL_TEXTURE));
            float trailA = DarknessIncarnateEffect.getIntensity(entity, partialTick, 20F);
            int packedLightIn = packedLight;
            while (samples < sampleSize) {
                Vec3 sample = AlexsCaves.PROXY.getDarknessTrailPosFor(entity, samples + 5, partialTick)
                        .subtract(x, y, z).add(trailOffset);
                float u1 = samples / (float) sampleSize;
                float u2 = u1 + 1 / (float) sampleSize;

                Vec3 draw1 = drawFrom;
                Vec3 draw2 = sample;

                PoseStack.Pose posestack$pose = poseStack.last();
                Matrix4f matrix4f = posestack$pose.pose();
                Matrix3f matrix3f = posestack$pose.normal();

                vertexconsumer
                        .addVertex(matrix4f, (float) draw1.x + (float) bottomAngleVec.x,
                                (float) draw1.y + (float) bottomAngleVec.y, (float) draw1.z + (float) bottomAngleVec.z)
                        .setColor(0, 0, 0, trailA).setUv(u1, 1F).setOverlay(NO_OVERLAY).setLight(packedLightIn)
                        .setNormal(0.0F, 1.0F, 0.0F);
                vertexconsumer
                        .addVertex(matrix4f, (float) draw2.x + (float) bottomAngleVec.x,
                                (float) draw2.y + (float) bottomAngleVec.y, (float) draw2.z + (float) bottomAngleVec.z)
                        .setColor(0, 0, 0, trailA).setUv(u2, 1F).setOverlay(NO_OVERLAY).setLight(packedLightIn)
                        .setNormal(0.0F, 1.0F, 0.0F);
                vertexconsumer
                        .addVertex(matrix4f, (float) draw2.x + (float) topAngleVec.x,
                                (float) draw2.y + (float) topAngleVec.y, (float) draw2.z + (float) topAngleVec.z)
                        .setColor(0, 0, 0, trailA).setUv(u2, 0).setOverlay(NO_OVERLAY).setLight(packedLightIn)
                        .setNormal(0.0F, 1.0F, 0.0F);
                vertexconsumer
                        .addVertex(matrix4f, (float) draw1.x + (float) topAngleVec.x,
                                (float) draw1.y + (float) topAngleVec.y, (float) draw1.z + (float) topAngleVec.z)
                        .setColor(0, 0, 0, trailA).setUv(u1, 0).setOverlay(NO_OVERLAY).setLight(packedLightIn)
                        .setNormal(0.0F, 1.0F, 0.0F);
                samples++;
                drawFrom = sample;
            }
        }
    }

    public static boolean onPoseHand(LivingEntity player, HumanoidModel<?> model, boolean b, Entity entityIn) {
        float f = Minecraft.getInstance().getTimer().getRealtimeDeltaTicks();
        float rightHandResistorShieldUseProgress = 0.0F;
        float leftHandResistorShieldUseProgress = 0.0F;
        float rightHandGalenaGauntletUseProgress = 0.0F;
        float leftHandGalenaGauntletUseProgress = 0.0F;
        float rightHandSpearUseProgress = 0.0F;
        float leftHandSpearUseProgress = 0.0F;
        float rightHandRaygunUseProgress = 0.0F;
        float leftHandRaygunUseProgress = 0.0F;
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ResistorShieldItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                rightHandResistorShieldUseProgress = Math.max(rightHandResistorShieldUseProgress,
                        ResistorShieldItem.getLerpedUseTime(player.getItemInHand(InteractionHand.MAIN_HAND), f));
            } else {
                leftHandResistorShieldUseProgress = Math.max(leftHandResistorShieldUseProgress,
                        ResistorShieldItem.getLerpedUseTime(player.getItemInHand(InteractionHand.MAIN_HAND), f));
            }
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ResistorShieldItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                leftHandResistorShieldUseProgress = Math.max(leftHandResistorShieldUseProgress,
                        ResistorShieldItem.getLerpedUseTime(player.getItemInHand(InteractionHand.OFF_HAND), f));
            } else {
                rightHandResistorShieldUseProgress = Math.max(rightHandResistorShieldUseProgress,
                        ResistorShieldItem.getLerpedUseTime(player.getItemInHand(InteractionHand.OFF_HAND), f));
            }
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GalenaGauntletItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                rightHandGalenaGauntletUseProgress = Math.max(rightHandGalenaGauntletUseProgress,
                        GalenaGauntletItem.getLerpedUseTime(player.getItemInHand(InteractionHand.MAIN_HAND), f));
            } else {
                leftHandGalenaGauntletUseProgress = Math.max(leftHandGalenaGauntletUseProgress,
                        GalenaGauntletItem.getLerpedUseTime(player.getItemInHand(InteractionHand.MAIN_HAND), f));
            }
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof GalenaGauntletItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                leftHandGalenaGauntletUseProgress = Math.max(leftHandGalenaGauntletUseProgress,
                        GalenaGauntletItem.getLerpedUseTime(player.getItemInHand(InteractionHand.OFF_HAND), f));
            } else {
                rightHandGalenaGauntletUseProgress = Math.max(rightHandGalenaGauntletUseProgress,
                        GalenaGauntletItem.getLerpedUseTime(player.getItemInHand(InteractionHand.OFF_HAND), f));
            }
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SpearItem && player.isUsingItem()
                && player.getUseItemRemainingTicks() > 0) {
            float f7 = (player.getItemInHand(InteractionHand.MAIN_HAND).getUseDuration(player)
                    - ((float) player.getUseItemRemainingTicks() - f + 1.0F))
                    / 10.0F;
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                rightHandSpearUseProgress = Math.max(rightHandSpearUseProgress, f7);
            } else {
                leftHandSpearUseProgress = Math.max(leftHandSpearUseProgress, f7);
            }
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof SpearItem && player.isUsingItem()
                && player.getUseItemRemainingTicks() > 0) {
            float f7 = (player.getItemInHand(InteractionHand.OFF_HAND).getUseDuration(player)
                    - ((float) player.getUseItemRemainingTicks() - f + 1.0F))
                    / 10.0F;
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                leftHandSpearUseProgress = Math.max(leftHandSpearUseProgress, f7);
            } else {
                rightHandSpearUseProgress = Math.max(rightHandSpearUseProgress, f7);
            }
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof RaygunItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                rightHandRaygunUseProgress = Math.max(rightHandRaygunUseProgress,
                        RaygunItem.getLerpedUseTime(player.getItemInHand(InteractionHand.MAIN_HAND), f));
            } else {
                leftHandRaygunUseProgress = Math.max(leftHandRaygunUseProgress,
                        RaygunItem.getLerpedUseTime(player.getItemInHand(InteractionHand.MAIN_HAND), f));
            }
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof RaygunItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                leftHandRaygunUseProgress = Math.max(leftHandRaygunUseProgress,
                        RaygunItem.getLerpedUseTime(player.getItemInHand(InteractionHand.OFF_HAND), f));
            } else {
                rightHandRaygunUseProgress = Math.max(rightHandRaygunUseProgress,
                        RaygunItem.getLerpedUseTime(player.getItemInHand(InteractionHand.OFF_HAND), f));
            }
        }
        if (player.isPassenger() && player.getVehicle() instanceof SubterranodonEntity subterranodon) {
            float flight = subterranodon.getFlyProgress(f) - subterranodon.getHoverProgress(f);
            if (flight > 0.0F) {
                model.leftArm.xRot = -(float) Math.toRadians(180F) * flight;
                model.leftArm.zRot = (float) Math.toRadians(-10F) * flight;
                model.rightArm.xRot = -(float) Math.toRadians(180F) * flight;
                model.rightArm.zRot = (float) Math.toRadians(10F) * flight;
            }
            return true;
        }
        if (leftHandResistorShieldUseProgress > 0.0F) {
            float useProgress = Math.min(10F, leftHandResistorShieldUseProgress) / 10F;
            float useProgressTurn = Math.min(useProgress * 4F, 1F);
            float useProgressUp = (float) Math.sin(useProgress * Math.PI);
            float armTilt = model.crouching ? 120F : 80F;
            model.leftArm.xRot = -(float) Math.toRadians(armTilt)
                    - (float) Math.toRadians(80F) * useProgressUp;
            model.leftArm.yRot = (float) Math.toRadians(20F) * useProgressTurn;
            return true;
        }
        if (rightHandResistorShieldUseProgress > 0.0F) {
            float useProgress = Math.min(10F, rightHandResistorShieldUseProgress) / 10F;
            float useProgressTurn = Math.min(useProgress * 4F, 1F);
            float useProgressUp = (float) Math.sin(useProgress * Math.PI);
            float armTilt = model.crouching ? 120F : 80F;
            model.rightArm.xRot = -(float) Math.toRadians(armTilt)
                    - (float) Math.toRadians(80F) * useProgressUp;
            model.rightArm.yRot = -(float) Math.toRadians(20F) * useProgressTurn;
            return true;
        }
        if (leftHandGalenaGauntletUseProgress > 0.0F) {
            float useProgress = Math.min(5F, leftHandGalenaGauntletUseProgress) / 5F;
            model.leftArm.xRot = (model.head.xRot - (float) Math.toRadians(80F)) * useProgress;
            model.leftArm.yRot = model.head.yRot * useProgress;
            return true;
        }
        if (rightHandGalenaGauntletUseProgress > 0.0F) {
            float useProgress = Math.min(5F, rightHandGalenaGauntletUseProgress) / 5F;
            model.rightArm.xRot = (model.head.xRot - (float) Math.toRadians(80F)) * useProgress;
            model.rightArm.yRot = model.head.yRot * useProgress;
            return true;
        }
        if (leftHandSpearUseProgress > 0.0F) {
            float useProgress = Math.min(1F, leftHandSpearUseProgress);
            float useProgressMiddle = (float) Math.sin(useProgress * Math.PI);
            model.leftArm.xRot = useProgress * ((float) Math.toRadians(-180F) + model.head.xRot);
            model.leftArm.yRot = useProgressMiddle
                    * ((float) Math.toRadians(-25F) - model.head.yRot);
            model.leftArm.zRot = useProgress * (float) Math.toRadians(50F) - (float) Math.toRadians(25F);
            return true;
        }
        if (rightHandSpearUseProgress > 0.0F) {
            float useProgress = Math.min(1F, rightHandSpearUseProgress);
            float useProgressMiddle = (float) Math.sin(useProgress * Math.PI);
            model.rightArm.xRot = useProgress * ((float) Math.toRadians(-180F) + model.head.xRot);
            model.rightArm.yRot = useProgressMiddle
                    * ((float) Math.toRadians(25F) - model.head.yRot);
            model.rightArm.zRot = useProgress * -(float) Math.toRadians(50F) + (float) Math.toRadians(25F);
            return true;
        }
        if (entityIn.getVehicle() instanceof NuclearBombEntity) {
            float ageInTicks = entityIn.tickCount + f;
            model.rightArm.xRot = (float) Math.toRadians(-170F);
            model.rightArm.yRot = (float) Math.toRadians(100F)
                    + (float) Math.cos(ageInTicks * 0.35F) * (float) Math.toRadians(20F);
            model.rightArm.zRot = (float) Math.sin(ageInTicks * 0.35F) * (float) Math.toRadians(50F)
                    - (float) Math.toRadians(70F);
            model.leftArm.yRot = (float) Math.toRadians(30F);
            return true;
        }
        if (leftHandRaygunUseProgress > 0.0F) {
            float useProgress = Math.min(5F, leftHandRaygunUseProgress) / 5F;
            model.leftArm.xRot = (model.head.xRot - (float) Math.toRadians(80F)) * useProgress;
            model.leftArm.yRot = model.head.yRot * useProgress;
            model.leftArm.zRot = 0;
            return true;
        }
        if (rightHandRaygunUseProgress > 0.0F) {
            float useProgress = Math.min(5F, rightHandRaygunUseProgress) / 5F;
            model.rightArm.xRot = (model.head.xRot - (float) Math.toRadians(80F)) * useProgress;
            model.rightArm.yRot = model.head.yRot * useProgress;
            model.rightArm.zRot = 0;
            return true;
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShotGumItem
                && ShotGumItem.shouldBeHeldUpright(player.getItemInHand(InteractionHand.MAIN_HAND))) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                model.rightArm.xRot = (model.head.xRot - (float) Math.toRadians(70F));
                model.rightArm.yRot = model.head.yRot;
                model.rightArm.zRot = 0;
                model.leftArm.xRot = model.head.xRot - (float) Math.toRadians(70F);
                model.leftArm.yRot = model.head.yRot + (float) Math.toRadians(40F);
                model.leftArm.zRot = (float) Math.toRadians(20F);
            } else {
                model.leftArm.xRot = (model.head.xRot - (float) Math.toRadians(70F));
                model.leftArm.yRot = model.head.yRot;
                model.leftArm.zRot = 0;
                model.rightArm.xRot = model.head.xRot - (float) Math.toRadians(70F);
                model.rightArm.yRot = model.head.yRot + (float) Math.toRadians(-40F);
                model.rightArm.zRot = (float) Math.toRadians(-20F);
            }
            return true;
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ShotGumItem
                && ShotGumItem.shouldBeHeldUpright(player.getItemInHand(InteractionHand.OFF_HAND))) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                model.leftArm.xRot = (model.head.xRot - (float) Math.toRadians(70F));
                model.leftArm.yRot = model.head.yRot;
                model.leftArm.zRot = 0;
                model.rightArm.xRot = model.head.xRot - (float) Math.toRadians(70F);
                model.rightArm.yRot = model.head.yRot + (float) Math.toRadians(-40F);
                model.rightArm.zRot = (float) Math.toRadians(-20F);

            } else {
                model.rightArm.xRot = (model.head.xRot - (float) Math.toRadians(70F));
                model.rightArm.yRot = model.head.yRot;
                model.rightArm.zRot = 0;
                model.leftArm.xRot = model.head.xRot - (float) Math.toRadians(70F);
                model.leftArm.yRot = model.head.yRot + (float) Math.toRadians(40F);
                model.leftArm.zRot = (float) Math.toRadians(20F);
            }
            return true;
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof CandyCaneHookItem
                && CandyCaneHookItem.isActive(player.getItemInHand(InteractionHand.MAIN_HAND))
                && player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof CandyCaneHookItem
                && CandyCaneHookItem.isActive(player.getItemInHand(InteractionHand.OFF_HAND))
                && player.getVehicle() instanceof GumWormSegmentEntity) {
            float rightWiggle = -Math.min(player.xxa, 0F)
                    * (float) Math.sin(player.tickCount + AlexsCaves.PROXY.getPartialTicks()) * 25;
            float leftWiggle = Math.max(player.xxa, 0F)
                    * (float) Math.sin(player.tickCount + AlexsCaves.PROXY.getPartialTicks()) * 25;
            model.rightArm.xRot = (float) Math.toRadians(-100F + rightWiggle);
            model.leftArm.xRot = (float) Math.toRadians(-100F + leftWiggle);
            model.rightArm.yRot = (float) Math.toRadians(20F);
            model.leftArm.yRot = (float) Math.toRadians(-20F);
            model.rightLeg.xRot = (float) Math.toRadians(-20F);
            model.leftLeg.xRot = (float) Math.toRadians(20F);
            return true;
        }
        if (b && player.hasEffect(ACEffectRegistry.SUGAR_RUSH)
                && !AlexsCaves.PROXY.isFirstPersonPlayer(player)) {
            float speedModifier = 0.35F;
            if (AlexsCaves.COMMON_CONFIG.sugarRushSlowsTime.get()
                    && AlexsCaves.PROXY.isTickRateModificationActive(Minecraft.getInstance().level)) {
                float tickRate = ClientTickRateTracker.getForClient(Minecraft.getInstance()).getClientTickRate()
                        / 50.0F;
                speedModifier *= tickRate;
            }
            float deltaSpeed = 1.0F;
            float partialTicks = AlexsCaves.PROXY.getPartialTicks();
            float walkPos = player.walkAnimation.position(partialTicks);
            float walkSpeed = player.walkAnimation.speed(partialTicks);
            float headXRot = player.getViewXRot(partialTicks);
            float headYRot = Mth.lerp(partialTicks, player.yHeadRotO, player.yHeadRot)
                    - Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot);
            model.rightArm.xRot = Mth.cos(walkPos * speedModifier + (float) Math.PI * 0.5F) * 2.0F
                    * walkSpeed * 0.5F / deltaSpeed;
            model.leftArm.xRot = Mth.cos(walkPos * speedModifier) * 2.0F * walkSpeed * 0.5F / deltaSpeed;
            model.rightArm.zRot = (Mth.sin(walkPos * -speedModifier + (float) Math.PI * 0.5F) + 2.5F) * 1.5F
                    * walkSpeed * 0.5F / deltaSpeed;
            model.leftArm.zRot = (Mth.sin(walkPos * -speedModifier) - 2.5F) * 1.5F * walkSpeed * 0.5F
                    / deltaSpeed;
            model.head.xRot = headXRot * ((float) Math.PI / 180F)
                    + Mth.cos(walkPos * speedModifier + (float) Math.PI) * 1.0F * walkSpeed * 0.5F / deltaSpeed;
            model.head.yRot = headYRot * ((float) Math.PI / 180F)
                    + Mth.sin(walkPos * speedModifier + (float) Math.PI) * 1.0F * walkSpeed * 0.5F / deltaSpeed;
            model.leftLeg.xRot = Mth.cos(walkPos * speedModifier + (float) Math.PI) * 4.0F * walkSpeed * 0.5F
                    / deltaSpeed;
            model.rightLeg.xRot = Mth.cos(walkPos * speedModifier) * 4.0F * walkSpeed * 0.5F / deltaSpeed;
            return true;
        }
        return false;
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

            rotateForAngle(entity, poseStack, magnetic.getPrevMagneticAttachmentFace(), prevProg, width, height);
            rotateForAngle(entity, poseStack, magnetic.getMagneticAttachmentFace(), progress, width, height);
        }
    }

    private static void rotateForAngle(LivingEntity entity, PoseStack matrixStackIn, Direction rotate, float f, float width, float height) {
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

    public static void registerBERenderers(BERendererRegistry event) {
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

    public static void registerMenus(MenuScreenRegistry reg) {
        reg.register(ACMenuRegistry.SPELUNKERY_TABLE_MENU.get(), SpelunkeryTableScreen::new);
        reg.register(ACMenuRegistry.NUCLEAR_FURNACE_MENU.get(), NuclearFurnaceScreen::new);
    }

    public static void registerEntityRenderers(EntityRendererRegistry event) {
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void registerSpriteProviders(SpriteSetRegistry event) {
        event.register(ACParticleRegistry.GALENA_DEBRIS.get(), GalenaDebrisParticle.Factory::new);
        event.register(ACParticleRegistry.FERROUSLIME.get(), FerrouslimeParticle.Factory::new);
        event.register(ACParticleRegistry.FLY.get(), FlyParticle.Factory::new);
        event.register(ACParticleRegistry.WATER_TREMOR.get(), WaterTremorParticle.Factory::new);
        event.register(ACParticleRegistry.AMBER_MONOLITH.get(), AmberMonolithParticle.Factory::new);
        event.register(ACParticleRegistry.AMBER_EXPLOSION.get(), SmallExplosionParticle.AmberFactory::new);
        event.register(ACParticleRegistry.TEPHRA.get(), TephraParticle.Factory::new);
        event.register(ACParticleRegistry.TEPHRA_SMALL.get(), TephraParticle.SmallFactory::new);
        event.register(ACParticleRegistry.TEPHRA_FLAME.get(), TephraParticle.FlameFactory::new);
        event.register(ACParticleRegistry.LUXTRUCTOSAURUS_SPIT.get(), LuxtructosaurusSpitParticle.Factory::new);
        event.register(ACParticleRegistry.LUXTRUCTOSAURUS_ASH.get(), LuxtructosaurusAshParticle.Factory::new);
        event.register(ACParticleRegistry.HAPPINESS.get(), HappinessParticle.Factory::new);
        event.register(ACParticleRegistry.ACID_BUBBLE.get(), AcidBubbleParticle.Factory::new);
        event.register(ACParticleRegistry.BLACK_VENT_SMOKE.get(), VentSmokeParticle.BlackFactory::new);
        event.register(ACParticleRegistry.WHITE_VENT_SMOKE.get(), VentSmokeParticle.WhiteFactory::new);
        event.register(ACParticleRegistry.GREEN_VENT_SMOKE.get(), VentSmokeParticle.GreenFactory::new);
        event.register(ACParticleRegistry.RED_VENT_SMOKE.get(), VentSmokeParticle.RedFactory::new);
        event.register(ACParticleRegistry.MUSHROOM_CLOUD_SMOKE.get(), SmallExplosionParticle.NukeFactory::new);
        event.register(ACParticleRegistry.MUSHROOM_CLOUD_EXPLOSION.get(), SmallExplosionParticle.NukeFactory::new);
        event.register(ACParticleRegistry.FALLOUT.get(), FalloutParticle.Factory::new);
        event.register(ACParticleRegistry.GAMMAROACH.get(), GammaroachParticle.Factory::new);
        event.register(ACParticleRegistry.HAZMAT_BREATHE.get(), HazmatBreatheParticle.Factory::new);
        event.register(ACParticleRegistry.BLUE_HAZMAT_BREATHE.get(), HazmatBreatheParticle.BlueFactory::new);
        event.register(ACParticleRegistry.RADGILL_SPLASH.get(), RadgillSplashParticle.Factory::new);
        event.register(ACParticleRegistry.ACID_DROP.get(), AcidDropParticle.Factory::new);
        event.register(ACParticleRegistry.NUCLEAR_SIREN_SONAR.get(), NuclearSirenSonarParticle.Factory::new);
        event.register(ACParticleRegistry.RAYGUN_EXPLOSION.get(), SmallExplosionParticle.RaygunFactory::new);
        event.register(ACParticleRegistry.BLUE_RAYGUN_EXPLOSION.get(), SmallExplosionParticle.BlueRaygunFactory::new);
        event.register(ACParticleRegistry.RAYGUN_BLAST.get(), RaygunBlastParticle.Factory::new);
        event.register(ACParticleRegistry.TREMORZILLA_EXPLOSION.get(), SmallExplosionParticle.TremorzillaFactory::new);
        event.register(ACParticleRegistry.TREMORZILLA_RETRO_EXPLOSION.get(), SmallExplosionParticle.TremorzillaRetroFactory::new);
        event.register(ACParticleRegistry.TREMORZILLA_TECTONIC_EXPLOSION.get(), SmallExplosionParticle.TremorzillaTectonicFactory::new);
        event.register(ACParticleRegistry.TREMORZILLA_LIGHTNING.get(), TremorzillaLightningParticle.Factory::new);
        event.register(ACParticleRegistry.TREMORZILLA_RETRO_LIGHTNING.get(), TremorzillaLightningParticle.Factory::new);
        event.register(ACParticleRegistry.TREMORZILLA_TECTONIC_LIGHTNING.get(), TremorzillaLightningParticle.Factory::new);
        event.register(ACParticleRegistry.TREMORZILLA_BLAST.get(), RaygunBlastParticle.TremorzillaFactory::new);
        event.register(ACParticleRegistry.TREMORZILLA_STEAM.get(), TremorzillaSteamParticle.Factory::new);
        event.register(ACParticleRegistry.DEEP_ONE_MAGIC.get(), DeepOneMagicParticle.Factory::new);
        event.register(ACParticleRegistry.WATER_FOAM.get(), WaterFoamParticle.Factory::new);
        event.register(ACParticleRegistry.BIG_SPLASH_EFFECT.get(), BigSplashEffectParticle.Factory::new);
        event.register(ACParticleRegistry.MINE_EXPLOSION.get(), SmallExplosionParticle.MineFactory::new);
        event.register(ACParticleRegistry.BIO_POP.get(), BioPopParticle.Factory::new);
        event.register(ACParticleRegistry.UNDERZEALOT_MAGIC.get(), UnderzealotMagicParticle.Factory::new);
        event.register(ACParticleRegistry.UNDERZEALOT_EXPLOSION.get(), SmallExplosionParticle.UnderzealotFactory::new);
        event.register(ACParticleRegistry.FALLING_GUANO.get(), FallingGuanoParticle.Factory::new);
        event.register(ACParticleRegistry.MOTH_DUST.get(), MothDustParticle.Factory::new);
        event.register(ACParticleRegistry.FORSAKEN_SPIT.get(), ForsakenSpitParticle.Factory::new);
        event.register(ACParticleRegistry.FORSAKEN_SONAR.get(), ForsakenSonarParticle.Factory::new);
        event.register(ACParticleRegistry.FORSAKEN_SONAR_LARGE.get(), ForsakenSonarParticle.LargeFactory::new);
        event.register(ACParticleRegistry.TOTEM_EXPLOSION.get(), SmallExplosionParticle.TotemFactory::new);
        event.register(ACParticleRegistry.ICE_CREAM_DRIP.get(), IceCreamDripParticle.Factory::new);
        event.register(ACParticleRegistry.ICE_CREAM_SPLASH.get(), IceCreamSplashParticle.Factory::new);
        event.register(ACParticleRegistry.PURPLE_SODA_BUBBLE.get(), PurpleSodaBubbleParticle.Factory::new);
        event.register(ACParticleRegistry.PURPLE_SODA_BUBBLE_EMITTER.get(), PurpleSodaBubbleEmitterParticle.Factory::new);
        event.register(ACParticleRegistry.PURPLE_SODA_FIZZ.get(), PurpleSodaFizzParticle.Factory::new);
        event.register(ACParticleRegistry.SUNDROP.get(), SundropParticle.Factory::new);
        event.register(ACParticleRegistry.CANDICORN_CHARGE.get(), CandicornChargeParticle.Factory::new);
        event.register(ACParticleRegistry.BIG_BLOCK_DUST.get(), BigBlockDustParticle.Factory::new);
        event.register(ACParticleRegistry.CARAMEL_DROP.get(), CaramelDropParticle.Factory::new);
        event.register(ACParticleRegistry.SLEEP.get(), SleepParticle.Factory::new);
        event.register(ACParticleRegistry.WITCH_COOKIE.get(), WitchCookieParticle.Factory::new);
        event.register(ACParticleRegistry.PURPLE_WITCH_EXPLOSION.get(), SmallExplosionParticle.PurpleWitchFactory::new);
        event.register(ACParticleRegistry.GOBTHUMPER.get(), GobthumperParticle.Factory::new);
        event.register(ACParticleRegistry.COLORED_DUST.get(), ColoredDustParticle.Factory::new);
        event.register(ACParticleRegistry.SMALL_COLORED_DUST.get(), ColoredDustParticle.SmallFactory::new);
        event.register(ACParticleRegistry.CONVERSION_CRUCIBLE_EXPLOSION.get(), SmallExplosionParticle.ConversionCrucibleFactory::new);
        event.register(ACParticleRegistry.FROSTMINT_EXPLOSION.get(), SmallExplosionParticle.FrostmintFactory::new);
        event.register(ACParticleRegistry.SUGAR_FLAKE.get(), SugarFlakeParticle.Factory::new);
    }

    public static void registerSpecialProviders(SpecialRegistry event) {
        event.register(ACParticleRegistry.SCARLET_MAGNETIC_ORBIT.get(), new MagneticOrbitParticle.ScarletFactory());
        event.register(ACParticleRegistry.AZURE_MAGNETIC_ORBIT.get(), new MagneticOrbitParticle.AzureFactory());
        event.register(ACParticleRegistry.SCARLET_MAGNETIC_FLOW.get(), new MagneticFlowParticle.ScarletFactory());
        event.register(ACParticleRegistry.AZURE_MAGNETIC_FLOW.get(), new MagneticFlowParticle.AzureFactory());
        event.register(ACParticleRegistry.TESLA_BULB_LIGHTNING.get(), new TeslaBulbLightningParticle.Factory());
        event.register(ACParticleRegistry.MAGNET_LIGHTNING.get(), new MagnetLightningParticle.Factory());
        event.register(ACParticleRegistry.MAGNETIC_CAVES_AMBIENT.get(), new MagneticCavesAmbientParticle.Factory());
        event.register(ACParticleRegistry.QUARRY_BORDER_LIGHTING.get(), new QuarryBorderLightningParticle.Factory());
        event.register(ACParticleRegistry.AZURE_SHIELD_LIGHTNING.get(), new ResistorShieldLightningParticle.AzureFactory());
        event.register(ACParticleRegistry.SCARLET_SHIELD_LIGHTNING.get(), new ResistorShieldLightningParticle.ScarletFactory());
        event.register(ACParticleRegistry.DINOSAUR_TRANSFORMATION_AMBER.get(), new DinosaurTransformParticle.AmberFactory());
        event.register(ACParticleRegistry.DINOSAUR_TRANSFORMATION_TECTONIC.get(), new DinosaurTransformParticle.TectonicFactory());
        event.register(ACParticleRegistry.STUN_STAR.get(), new StunStarParticle.Factory());
        event.register(ACParticleRegistry.MUSHROOM_CLOUD.get(), new MushroomCloudParticle.Factory());
        event.register(ACParticleRegistry.PROTON.get(), new ProtonParticle.Factory());
        event.register(ACParticleRegistry.TREMORZILLA_PROTON.get(), new TremorzillaProtonParticle.Factory());
        event.register(ACParticleRegistry.TREMORZILLA_RETRO_PROTON.get(), new TremorzillaProtonParticle.RetroFactory());
        event.register(ACParticleRegistry.TREMORZILLA_TECTONIC_PROTON.get(), new TremorzillaProtonParticle.TectonicFactory());
        event.register(ACParticleRegistry.TUBE_WORM.get(), new TubeWormParticle.Factory());
        event.register(ACParticleRegistry.BIG_SPLASH.get(), new BigSplashParticle.Factory());
        event.register(ACParticleRegistry.WATCHER_APPEARANCE.get(), new WatcherAppearanceParticle.Factory());
        event.register(ACParticleRegistry.VOID_BEING_CLOUD.get(), new VoidBeingCloudParticle.Factory());
        event.register(ACParticleRegistry.VOID_BEING_TENDRIL.get(), new VoidBeingTendrilParticle.Factory());
        event.register(ACParticleRegistry.VOID_BEING_EYE.get(), new VoidBeingEyeParticle.Factory());
        event.register(ACParticleRegistry.RAINBOW.get(), new RainbowParticle.Factory());
        event.register(ACParticleRegistry.PLAYER_RAINBOW.get(), new PlayerRainbowParticle.Factory());
        event.register(ACParticleRegistry.JELLY_BEAN_EAT.get(), new JellyBeanEatParticle.Factory());
        event.register(ACParticleRegistry.PURPLE_WITCH_MAGIC.get(), new PurpleWitchMagicParticle.Factory());
    }

    @FunctionalInterface
    public interface SpecialRegistry {

        <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider);
    }

    @FunctionalInterface
    public interface SpriteSetRegistry<T extends ParticleOptions> {

        void register(ParticleType<T> type, ParticleEngine.SpriteParticleRegistration<T> registration);
    }

    @FunctionalInterface
    public interface BERendererRegistry {

        <T extends BlockEntity> void register(BlockEntityType<? extends T> type, BlockEntityRendererProvider<T> renderProvider);
    }

    @FunctionalInterface
    public interface EntityRendererRegistry {

        <T extends Entity> void register(EntityType<? extends T> type, EntityRendererProvider<T> renderProvider);
    }

    @FunctionalInterface
    public interface MenuScreenRegistry {

        <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(MenuType<? extends M> type, MenuScreens.ScreenConstructor<M, U> factory);
    }
}
