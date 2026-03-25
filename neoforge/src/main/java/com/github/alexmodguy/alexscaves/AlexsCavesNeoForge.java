package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.client.event.NeoClientEvents;
import com.github.alexmodguy.alexscaves.client.model.layered.ACModelLayers;
import com.github.alexmodguy.alexscaves.platform.NeoForgeClientPlatformHelper;
import com.github.alexmodguy.alexscaves.platform.NeoForgeEventHelper;
import com.github.alexmodguy.alexscaves.platform.NeoForgeRegistryHelper;
import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import com.github.alexmodguy.alexscaves.server.config.BiomeGenerationConfig;
import com.github.alexmodguy.alexscaves.server.event.NeoCommonEvents;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.level.surface.ACSurfaceRules;
import com.github.alexmodguy.alexscaves.server.misc.ACLoadedMods;
import com.github.alexmodguy.alexscaves.server.misc.ACLootModifiersRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACPlayerCapes;
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

@Mod(AlexsCaves.MOD_ID)
public class AlexsCavesNeoForge {

    private IEventBus modEventBus; // Store for client setup
    // TODO fix when redoing chunk loading
    public static final TicketController TICKET_CONTROLLER = new TicketController(
            AlexsCaves.id("default"),
            (x, y) -> {/*ACWorldData::clearLoadedChunksCallback*/});


    public AlexsCavesNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        // (ender) This has to be before init so all events register
        NeoForgeEventHelper.init(modEventBus);
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
        modEventBus.addListener(NeoCommonEvents::initializeAttributes2);
        modEventBus.addListener(NeoCommonEvents::spawnPlacements);
        NeoForge.EVENT_BUS.register(new NeoCommonEvents());
        ACFluidRegistry.FLUID_TYPE_DEF_REG.register(modEventBus);
        ACFluidRegistry.FLUID_DEF_REG.register(modEventBus);
        ACLootModifiersRegistry.GLOBAL_LOOT_MODIFIER_DEF_REG.register(modEventBus);
        this.modEventBus = modEventBus; // Store for later use
        if (AlexsCaves.PROXY instanceof ClientProxy) {
            NeoClientEvents.commonInit(modEventBus);
            NeoForgeClientPlatformHelper.init(modEventBus);
            AlexsCavesClient.init();
        }
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
        });
    }

    private void registerTicketControllers(RegisterTicketControllersEvent event) {
        event.register(TICKET_CONTROLLER);
//        event.register(com.github.alexmodguy.alexscaves.server.entity.item.NuclearExplosionEntity.TICKET_CONTROLLER);
//        event.register(com.github.alexmodguy.alexscaves.server.entity.item.BeholderEyeEntity.TICKET_CONTROLLER);
//        event.register(com.github.alexmodguy.alexscaves.server.item.OccultGemItem.TICKET_CONTROLLER);
//        event.register(com.github.alexmodguy.alexscaves.server.item.RemoteDetonatorItem.TICKET_CONTROLLER);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            if (AlexsCaves.PROXY instanceof ClientProxy) {
                NeoClientEvents.clientInit(this.modEventBus);
                AlexsCavesClient.lateInit();
            }
        });
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(ACFluidRegistry::postInit);
        event.enqueueWork(ACLoadedMods::afterAllModsLoaded);
    }

    private void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        ACModelLayers.register(event::registerLayerDefinition);
    }

}
