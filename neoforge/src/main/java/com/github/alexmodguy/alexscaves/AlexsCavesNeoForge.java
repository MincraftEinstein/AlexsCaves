package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.client.model.layered.ACModelLayers;
import com.github.alexmodguy.alexscaves.platform.NeoForgeClientPlatformHelper;
import com.github.alexmodguy.alexscaves.platform.NeoForgeRegistryHelper;
import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import com.github.alexmodguy.alexscaves.server.config.BiomeGenerationConfig;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityDataRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACFrogRegistry;
import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.level.biome.ACBiomeRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.ACStructureRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.piece.ACStructurePieceRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.processor.ACStructureProcessorRegistry;
import com.github.alexmodguy.alexscaves.server.level.surface.ACSurfaceRuleConditionRegistry;
import com.github.alexmodguy.alexscaves.server.level.surface.ACSurfaceRules;
import com.github.alexmodguy.alexscaves.server.misc.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mod(AlexsCaves.MOD_ID)
public class AlexsCavesNeoForge {

    private IEventBus modEventBus; // Store for client setup
    // TODO fix when redoing chunk loading
    public static final TicketController TICKET_CONTROLLER = new TicketController(
            AlexsCaves.id("default"),
            (x, y) -> {/*ACWorldData::clearLoadedChunksCallback*/});

    public static final List<String> MOD_GENERATION_CONFLICTS = new ArrayList<>();

    public AlexsCavesNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        // (ender) This has to be before init so all events register
        NeoForgeRegistryHelper.init(modEventBus);
        AlexsCaves.init();
        modContainer.registerConfig(ModConfig.Type.COMMON, AlexsCaves.COMMON_CONFIG_SPEC, "alexscaves-general.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, AlexsCaves.CLIENT_CONFIG_SPEC, "alexscaves-client.toml");
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(this::loadConfig);
        modEventBus.addListener(this::reloadConfig);
        modEventBus.addListener(this::registerLayerDefinitions);
        modEventBus.addListener(this::registerTicketControllers);
        NeoForge.EVENT_BUS.register(new CommonEvents());
        // TODO move to common
        ACEntityRegistry.init();
        ACEntityDataRegistry.DEF_REG.register(modEventBus);
        ACSurfaceRuleConditionRegistry.DEF_REG.register(modEventBus);
        ACStructureRegistry.DEF_REG.register(modEventBus);
        ACStructurePieceRegistry.DEF_REG.register(modEventBus);
        ACStructureProcessorRegistry.DEF_REG.register(modEventBus);
        // TODO move to common
        ACFrogRegistry.DEF_REG.register(modEventBus);
        ACFluidRegistry.FLUID_TYPE_DEF_REG.register(modEventBus);
        ACFluidRegistry.FLUID_DEF_REG.register(modEventBus);
        ACLootTableRegistry.GLOBAL_LOOT_MODIFIER_DEF_REG.register(modEventBus);
        ACLootTableRegistry.LOOT_FUNCTION_DEF_REG.register(modEventBus);
        ACPotPatternRegistry.init(); // Pot patterns are now data-driven in 1.21
        this.modEventBus = modEventBus; // Store for later use
        if (AlexsCaves.PROXY instanceof ClientProxy cProxy) {
            cProxy.commonInit(modEventBus);
            NeoForgeClientPlatformHelper.init(modEventBus);
            AlexsCavesClient.init();
        }
        ACBiomeRegistry.init();
    }

    private void loadConfig(final ModConfigEvent.Loading event) {
        BiomeGenerationConfig.reloadConfig();
    }

    private void reloadConfig(final ModConfigEvent.Reloading event) {
        BiomeGenerationConfig.reloadConfig();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        AlexsCaves.PROXY.initPathfinding();
        event.enqueueWork(() -> {
            ACSurfaceRules.setup();
            ACPlayerCapes.setup();
            ACItemRegistry.registerDispenserBehavior();
            // ACPotPatternRegistry.expandVanillaDefinitions(); // Pot patterns are now data-driven in 1.21
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
        } catch (Exception e) {
            AlexsCaves.LOGGER.error("Failed to verify POI registration", e);
        }
    }

    private void registerTicketControllers(RegisterTicketControllersEvent event) {
        event.register(TICKET_CONTROLLER);
        event.register(com.github.alexmodguy.alexscaves.server.entity.item.NuclearExplosionEntity.TICKET_CONTROLLER);
        event.register(com.github.alexmodguy.alexscaves.server.entity.item.BeholderEyeEntity.TICKET_CONTROLLER);
//        event.register(com.github.alexmodguy.alexscaves.server.item.OccultGemItem.TICKET_CONTROLLER);
//        event.register(com.github.alexmodguy.alexscaves.server.item.RemoteDetonatorItem.TICKET_CONTROLLER);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            if (AlexsCaves.PROXY instanceof ClientProxy cProxy) {
                cProxy.clientInit(this.modEventBus);
                AlexsCavesClient.lateInit();
            }
        });
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(ACFluidRegistry::postInit);
        event.enqueueWork(ACLoadedMods::afterAllModsLoaded);
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
            } catch (IOException e) {
                AlexsCaves.LOGGER.warn("Failed to load mod conflicts");
            }
        } else {
            AlexsCaves.LOGGER.warn("Failed to load mod conflicts");
        }
    }

}
