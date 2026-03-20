package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.client.model.layered.ACModelLayers;
import com.github.alexmodguy.alexscaves.client.particle.ACParticleRegistry;
import com.github.alexmodguy.alexscaves.platform.NeoForgeRegistryHelper;
import com.github.alexmodguy.alexscaves.server.CommonProxy;
import com.github.alexmodguy.alexscaves.server.block.blockentity.ACBlockEntityRegistry;
import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import com.github.alexmodguy.alexscaves.server.config.BiomeGenerationConfig;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityDataRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACFrogRegistry;
import com.github.alexmodguy.alexscaves.server.entity.util.ACAttachmentRegistry;
import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import com.github.alexmodguy.alexscaves.server.inventory.ACMenuRegistry;
import com.github.alexmodguy.alexscaves.server.item.ACArmorMaterial;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.level.biome.ACBiomeRegistry;
import com.github.alexmodguy.alexscaves.server.level.carver.ACCarverRegistry;
import com.github.alexmodguy.alexscaves.server.level.feature.ACFeatureRegistry;
import com.github.alexmodguy.alexscaves.server.level.storage.ACWorldData;
import com.github.alexmodguy.alexscaves.server.level.structure.ACStructureRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.piece.ACStructurePieceRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.processor.ACStructureProcessorRegistry;
import com.github.alexmodguy.alexscaves.server.level.surface.ACSurfaceRuleConditionRegistry;
import com.github.alexmodguy.alexscaves.server.level.surface.ACSurfaceRules;
import com.github.alexmodguy.alexscaves.server.message.*;
import com.github.alexmodguy.alexscaves.server.misc.*;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.github.alexmodguy.alexscaves.server.recipe.ACRecipeRegistry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mod(AlexsCaves.MOD_ID)
public class AlexsCavesNeoForge {

    public static final String VERSION = "1.0.0";
    // Initialize proxy based on dist
    public static CommonProxy PROXY = FMLEnvironment.dist.isClient() ? new ClientProxy() : new CommonProxy();
    private IEventBus modEventBus; // Store for client setup
    public static final TicketController TICKET_CONTROLLER = new TicketController(
            ResourceLocation.fromNamespaceAndPath(AlexsCaves.MOD_ID, "default"),
            ACWorldData::clearLoadedChunksCallback);

    public static final List<String> MOD_GENERATION_CONFLICTS = new ArrayList<>();

    public AlexsCavesNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        AlexsCaves.init();
        NeoForgeRegistryHelper.init(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, AlexsCaves.COMMON_CONFIG_SPEC, "alexscaves-general.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, AlexsCaves.CLIENT_CONFIG_SPEC, "alexscaves-client.toml");
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(this::loadConfig);
        modEventBus.addListener(this::reloadConfig);
        modEventBus.addListener(this::registerLayerDefinitions);
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::registerTicketControllers);
        NeoForge.EVENT_BUS.register(new CommonEvents());
        NeoForge.EVENT_BUS.addListener(ACEffectRegistry::registerBrewingRecipes);
        ACBlockEntityRegistry.DEF_REG.register(modEventBus);
        ACItemRegistry.DEF_REG.register(modEventBus);
        ACArmorMaterial.ARMOR_MATERIALS.register(modEventBus);
        ACParticleRegistry.DEF_REG.register(modEventBus);
        ACEntityRegistry.DEF_REG.register(modEventBus);
        ACEntityDataRegistry.DEF_REG.register(modEventBus);
        ACAttachmentRegistry.DEF_REG.register(modEventBus);
        ACFeatureRegistry.DEF_REG.register(modEventBus);
        ACSurfaceRuleConditionRegistry.DEF_REG.register(modEventBus);
        ACCarverRegistry.DEF_REG.register(modEventBus);
        ACStructureRegistry.DEF_REG.register(modEventBus);
        ACStructurePieceRegistry.DEF_REG.register(modEventBus);
        ACStructureProcessorRegistry.DEF_REG.register(modEventBus);
        ACEffectRegistry.DEF_REG.register(modEventBus);
        ACEffectRegistry.POTION_DEF_REG.register(modEventBus);
        ACMenuRegistry.DEF_REG.register(modEventBus);
        ACRecipeRegistry.DEF_REG.register(modEventBus);
        ACRecipeRegistry.TYPE_DEF_REG.register(modEventBus);
        ACAdvancementTriggerRegistry.DEF_REG.register(modEventBus);
        ACFrogRegistry.DEF_REG.register(modEventBus);
        ACFluidRegistry.FLUID_TYPE_DEF_REG.register(modEventBus);
        ACFluidRegistry.FLUID_DEF_REG.register(modEventBus);
        ACLootTableRegistry.GLOBAL_LOOT_MODIFIER_DEF_REG.register(modEventBus);
        ACLootTableRegistry.LOOT_FUNCTION_DEF_REG.register(modEventBus);
        ACCreativeTabRegistry.DEF_REG.register(modEventBus);
        ACDataComponentRegistry.init(modEventBus);
        ACPotPatternRegistry.init(); // Pot patterns are now data-driven in 1.21
        this.modEventBus = modEventBus; // Store for later use
        PROXY.commonInit(modEventBus);
        ACBiomeRegistry.init();
    }

    private void loadConfig(final ModConfigEvent.Loading event) {
        BiomeGenerationConfig.reloadConfig();
    }

    private void reloadConfig(final ModConfigEvent.Reloading event) {
        BiomeGenerationConfig.reloadConfig();
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(AlexsCaves.MOD_ID).versioned(VERSION).optional();
        // Server-to-client messages
        registrar.playToClient(WorldEventMessage.TYPE, WorldEventMessage.CODEC, WorldEventMessage::handle);
        registrar.playToClient(UpdateCaveBiomeMapTagMessage.TYPE, UpdateCaveBiomeMapTagMessage.CODEC,
                UpdateCaveBiomeMapTagMessage::handle);
        registrar.playToClient(UpdateBossEruptionStatus.TYPE, UpdateBossEruptionStatus.CODEC,
                UpdateBossEruptionStatus::handle);
        registrar.playToClient(UpdateBossBarMessage.TYPE, UpdateBossBarMessage.CODEC, UpdateBossBarMessage::handle);
        registrar.playToClient(UpdateEffectVisualityEntityMessage.TYPE, UpdateEffectVisualityEntityMessage.CODEC,
                UpdateEffectVisualityEntityMessage::handle);
        registrar.playBidirectional(UpdateItemTagMessage.TYPE, UpdateItemTagMessage.CODEC, UpdateItemTagMessage::handle);
        registrar.playToClient(BeholderSyncMessage.TYPE, BeholderSyncMessage.CODEC, BeholderSyncMessage::handle);
        registrar.playToClient(SundropRainbowMessage.TYPE, SundropRainbowMessage.CODEC, SundropRainbowMessage::handle);
        registrar.playToClient(SpelunkeryTableCompleteTutorialMessage.TYPE,
                SpelunkeryTableCompleteTutorialMessage.CODEC, SpelunkeryTableCompleteTutorialMessage::handle);
        registrar.playToClient(UpdateMagneticDataMessage.TYPE, UpdateMagneticDataMessage.CODEC,
                UpdateMagneticDataMessage::handle);
        // Client-to-server messages
        registrar.playToServer(MultipartEntityMessage.TYPE, MultipartEntityMessage.CODEC,
                MultipartEntityMessage::handle);
        registrar.playToServer(SpelunkeryTableChangeMessage.TYPE, SpelunkeryTableChangeMessage.CODEC,
                SpelunkeryTableChangeMessage::handle);
        registrar.playToServer(PlayerJumpFromMagnetMessage.TYPE, PlayerJumpFromMagnetMessage.CODEC,
                PlayerJumpFromMagnetMessage::handle);
        registrar.playToServer(MountedEntityKeyMessage.TYPE, MountedEntityKeyMessage.CODEC,
                MountedEntityKeyMessage::handle);
        registrar.playToServer(PossessionKeyMessage.TYPE, PossessionKeyMessage.CODEC, PossessionKeyMessage::handle);
        registrar.playToServer(BeholderRotateMessage.TYPE, BeholderRotateMessage.CODEC, BeholderRotateMessage::handle);
        registrar.playToServer(ArmorKeyMessage.TYPE, ArmorKeyMessage.CODEC, ArmorKeyMessage::handle);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PROXY.initPathfinding();
        event.enqueueWork(() -> {
            ACSurfaceRules.setup();
            ACPlayerCapes.setup();
            ACEffectRegistry.setup();
            ACItemRegistry.setup();
            // ACPotPatternRegistry.expandVanillaDefinitions(); // Pot patterns are now data-driven in 1.21
            ACBlockEntityRegistry.expandVanillaDefinitions();
            // Debug: verify POI registration
            verifyPoiRegistration();
        });
        readModIncompatibilities();
    }

    private void verifyPoiRegistration() {
        try {
            var attractingMagnets = com.github.alexmodguy.alexscaves.server.block.poi.ACPOIRegistry.ATTRACTING_MAGNETS.get();
            var repellingMagnets = com.github.alexmodguy.alexscaves.server.block.poi.ACPOIRegistry.REPELLING_MAGNETS.get();
            AlexsCaves.LOGGER.info("POI Verification - Attracting Magnets: {} states registered", attractingMagnets.matchingStates().size());
            AlexsCaves.LOGGER.info("POI Verification - Repelling Magnets: {} states registered", repellingMagnets.matchingStates().size());

            // Verify that PoiTypes.hasPoi returns true for our blocks
            var scarletNode = com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry.SCARLET_NEODYMIUM_NODE.get().defaultBlockState();
            var azureNode = com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry.AZURE_NEODYMIUM_NODE.get().defaultBlockState();
            boolean scarletHasPoi = net.minecraft.world.entity.ai.village.poi.PoiTypes.hasPoi(scarletNode);
            boolean azureHasPoi = net.minecraft.world.entity.ai.village.poi.PoiTypes.hasPoi(azureNode);
            AlexsCaves.LOGGER.info("POI Verification - Scarlet Node hasPoi: {}, Azure Node hasPoi: {}", scarletHasPoi, azureHasPoi);

            if (!scarletHasPoi || !azureHasPoi) {
                AlexsCaves.LOGGER.error("POI registration failed! Neodymium blocks are not registered as POI types!");
            }
        }
        catch (Exception e) {
            AlexsCaves.LOGGER.error("Failed to verify POI registration", e);
        }
    }

    private void registerTicketControllers(RegisterTicketControllersEvent event) {
        event.register(TICKET_CONTROLLER);
        event.register(com.github.alexmodguy.alexscaves.server.entity.item.NuclearExplosionEntity.TICKET_CONTROLLER);
        event.register(com.github.alexmodguy.alexscaves.server.entity.item.BeholderEyeEntity.TICKET_CONTROLLER);
        event.register(com.github.alexmodguy.alexscaves.server.item.OccultGemItem.TICKET_CONTROLLER);
        event.register(com.github.alexmodguy.alexscaves.server.item.RemoteDetonatorItem.TICKET_CONTROLLER);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> PROXY.clientInit(this.modEventBus));
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToServer(MSG message) {
        PacketDistributor.sendToServer(message);
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(ACFluidRegistry::postInit);
        event.enqueueWork(ACLoadedMods::afterAllModsLoaded);
    }

    public static <MSG extends CustomPacketPayload> void sendNonLocal(MSG msg, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, msg);
    }

    private void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        ACModelLayers.register(event);
    }

    private void readModIncompatibilities() {
        BufferedReader urlContents = WebHelper.getURLContents(
                "https://raw.githubusercontent.com/AlexModGuy/AlexsCaves/main/src/main/resources/assets/alexscaves/warning/mod_generation_conflicts.txt",
                "assets/alexscaves/warning/mod_generation_conflicts.txt");
        if (urlContents != null) {
            try {
                String line;
                while ((line = urlContents.readLine()) != null) {
                    MOD_GENERATION_CONFLICTS.add(line);
                }
            }
            catch (IOException e) {
                AlexsCaves.LOGGER.warn("Failed to load mod conflicts");
            }
        }
        else {
            AlexsCaves.LOGGER.warn("Failed to load mod conflicts");
        }
    }

}
