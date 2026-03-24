package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.client.config.ACClientConfig;
import com.github.alexmodguy.alexscaves.client.particle.ACParticleRegistry;
import com.github.alexmodguy.alexscaves.init.NetworkRegistry;
import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.server.CommonProxy;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.ACSoundTypes;
import com.github.alexmodguy.alexscaves.server.block.blockentity.ACBlockEntityRegistry;
import com.github.alexmodguy.alexscaves.server.block.poi.ACPOIRegistry;
import com.github.alexmodguy.alexscaves.server.config.ACServerConfig;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACFrogRegistry;
import com.github.alexmodguy.alexscaves.server.entity.util.ACAttachmentRegistry;
import com.github.alexmodguy.alexscaves.server.inventory.ACMenuRegistry;
import com.github.alexmodguy.alexscaves.server.item.ACArmorMaterials;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.level.biome.ACBiomeRegistry;
import com.github.alexmodguy.alexscaves.server.level.carver.ACCarverRegistry;
import com.github.alexmodguy.alexscaves.server.level.feature.ACFeatureRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.ACStructureRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.piece.ACStructurePieceRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.processor.ACStructureProcessorRegistry;
import com.github.alexmodguy.alexscaves.server.level.surface.ACSurfaceRuleConditionRegistry;
import com.github.alexmodguy.alexscaves.server.misc.*;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.github.alexmodguy.alexscaves.server.recipe.ACRecipeRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class AlexsCaves {

    public static final String MOD_ID = "alexscaves";
    public static final String MOD_NAME = "Alex's Caves";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private static final Pair<ACServerConfig, ModConfigSpec> SERVER_PAIR = new ModConfigSpec.Builder()
            .configure(ACServerConfig::new);
    public static final ACServerConfig COMMON_CONFIG = SERVER_PAIR.getLeft();
    public static final ModConfigSpec COMMON_CONFIG_SPEC = SERVER_PAIR.getRight();
    private static final Pair<ACClientConfig, ModConfigSpec> CLIENT_PAIR = new ModConfigSpec.Builder()
            .configure(ACClientConfig::new);
    public static final ACClientConfig CLIENT_CONFIG = CLIENT_PAIR.getLeft();
    public static final ModConfigSpec CLIENT_CONFIG_SPEC = CLIENT_PAIR.getRight();
    private static final Map<Optional<Holder.Reference<Biome>>, Integer> BIOME_COLORS = new HashMap<>();
    private static final int PLAINS_FOG_COLOR = 12638463;

    // Initialize proxy based on dist
    public static CommonProxy PROXY = ConfigApiJava.platform().isClient() ? new ClientProxy() : new CommonProxy();

    public static void init() {
        LOGGER.info("Hello from Alex's Caves Multiloader Edition");
        ACSoundRegistry.init();
        ACSoundTypes.init();
        ACItemRegistry.init();
        ACBlockRegistry.init();
        ACBlockEntityRegistry.init();
        ACPOIRegistry.init();
        ACRecipeRegistry.init();
        ACMenuRegistry.init();
        ACAdvancementTriggerRegistry.init();
        ACDataComponentRegistry.init();
        ACEntityRegistry.init();
        ACParticleRegistry.init();
        ACCarverRegistry.init();
        ACArmorMaterials.init();
        ACEffectRegistry.init();
        ACAttachmentRegistry.init();
        ACCreativeTabRegistry.init();
        ACStructureProcessorRegistry.init();
        ACFeatureRegistry.init();
        ACStructurePieceRegistry.init();
        ACStructureRegistry.init();
        ACPotPatternRegistry.init(); // TODO fix pots
        ACBiomeRegistry.init();
        ACFrogRegistry.init();
        ACLootTableRegistry.init();
        NetworkRegistry.registerPayloads();
        ACSurfaceRuleConditionRegistry.init();
        readModIncompatibilities();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static int calculateBiomeColor(Optional<Holder.Reference<Biome>> holder) {
        if (BIOME_COLORS.containsKey(holder)) {
            return BIOME_COLORS.get(holder);
        } else {
            int fogColor = holder.get().value().getFogColor();
            int color;
            if (ACBiomeRegistry.getBiomeTabletColor(holder.get().key()) != -1) {
                color = ACBiomeRegistry.getBiomeTabletColor(holder.get().key());
            } else if (fogColor == PLAINS_FOG_COLOR) {
                color = holder.get().value().getGrassColor(0.0D, 0.0D);
            } else {
                fogColor = 0xff000000 | fogColor;
                float[] hsb = Color.RGBtoHSB(fogColor >> 16 & 0xFF, fogColor >> 8 & 0xFF, fogColor & 0xFF, null);
                float saturationModifier = 1.0F;
                float brightnessModifier = 3.0F;
                color = Color.HSBtoRGB(hsb[0], Mth.clamp(hsb[1] * saturationModifier, 0, 1), Mth.clamp(hsb[2] * brightnessModifier, 0, 1));
            }
            BIOME_COLORS.put(holder, color);
            return color;
        }
    }

    public static final List<String> MOD_GENERATION_CONFLICTS = new ArrayList<>();

    public static void readModIncompatibilities() {
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
