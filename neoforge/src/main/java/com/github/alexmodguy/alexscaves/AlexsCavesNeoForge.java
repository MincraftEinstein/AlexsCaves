package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.event.ClientEvents;
import com.github.alexmodguy.alexscaves.client.event.NeoClientEvents;
import com.github.alexmodguy.alexscaves.platform.NeoForgeClientPlatformHelper;
import com.github.alexmodguy.alexscaves.platform.NeoForgeEventHelper;
import com.github.alexmodguy.alexscaves.platform.NeoForgeRegistryHelper;
import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import com.github.alexmodguy.alexscaves.server.config.BiomeGenerationConfig;
import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import com.github.alexmodguy.alexscaves.server.event.NeoCommonEvents;
import com.github.alexmodguy.alexscaves.server.misc.ACLoadedMods;
import com.github.alexmodguy.alexscaves.server.misc.ACLootModifiersRegistry;
import com.github.alexthe666.citadel.client.event.EventLivingRenderer;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@Mod(AlexsCaves.MOD_ID)
public class AlexsCavesNeoForge {

    // TODO fix when redoing chunk loading
    public static final TicketController TICKET_CONTROLLER = new TicketController(
            AlexsCaves.id("default"),
            (x, y) -> {/*ACWorldData::clearLoadedChunksCallback*/});


    public AlexsCavesNeoForge(IEventBus bus, ModContainer modContainer) {
        // (ender) This has to be before init so all events register
        NeoForgeEventHelper.init(bus);
        NeoForgeRegistryHelper.init(bus);
        AlexsCaves.init();
        modContainer.registerConfig(ModConfig.Type.COMMON, AlexsCaves.COMMON_CONFIG_SPEC, "alexscaves-general.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, AlexsCaves.CLIENT_CONFIG_SPEC, "alexscaves-client.toml");

        bus.addListener((FMLCommonSetupEvent event) -> event.enqueueWork(AlexsCaves::laterSetup));
        bus.addListener(this::loadComplete);
        bus.addListener((ModConfigEvent.Loading event) -> BiomeGenerationConfig.reloadConfig());
        bus.addListener((ModConfigEvent.Reloading event) -> BiomeGenerationConfig.reloadConfig());
        bus.addListener(this::registerTicketControllers);
        bus.addListener((EntityAttributeCreationEvent event) -> CommonEvents.initializeAttributes(event::put));
        bus.addListener(NeoCommonEvents::spawnPlacements);
        bus.addListener((EntityTickEvent event) -> CommonEvents.onEntityTick(event.getEntity()));
        NeoForge.EVENT_BUS.register(new NeoCommonEvents());

        ACFluidRegistry.FLUID_TYPE_DEF_REG.register(bus);
        ACFluidRegistry.FLUID_DEF_REG.register(bus);
        ACLootModifiersRegistry.GLOBAL_LOOT_MODIFIER_DEF_REG.register(bus);

        if (ConfigApiJava.platform().isClient()) {
            bus.addListener(this::clientSetup);
            NeoClientEvents.clientInit(bus);
            NeoForgeClientPlatformHelper.init(bus);
            AlexsCavesClient.init();
        }
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
            AlexsCavesClient.laterSetup();
            ItemBlockRenderTypes.setRenderLayer(ACFluidRegistry.ACID_FLUID_SOURCE.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(ACFluidRegistry.ACID_FLUID_FLOWING.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(ACFluidRegistry.PURPLE_SODA_FLUID_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ACFluidRegistry.PURPLE_SODA_FLUID_FLOWING.get(), RenderType.translucent());
        });
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(ACFluidRegistry::postInit);
        event.enqueueWork(ACLoadedMods::afterAllModsLoaded);
    }

}
