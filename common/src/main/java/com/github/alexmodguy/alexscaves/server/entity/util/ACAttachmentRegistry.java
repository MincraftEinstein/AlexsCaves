package com.github.alexmodguy.alexscaves.server.entity.util;

import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.platform.services.IPlatformHelper;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.teamvoided.voidlib.attachments.AttachmentSupplier;

public class ACAttachmentRegistry {

    private static final ResourceLocation NEO_SYNC_PAYLOAD = ResourceLocation.fromNamespaceAndPath("neoforge", "sync_attachments");

    public static AttachmentSupplier<MagneticEntityData, Entity> MAGNETIC_DATA = Services.REGISTRY_HELPER.registerAttachment("magnetic_data", Entity.class,
            () -> MagneticEntityData.DEFAULT,
            builder -> builder.persistent(MagneticEntityData.CODEC)
                    .synced(MagneticEntityData.STREAM_CODEC, (entity, player) -> {
                        // Hacky fix for NeoForge not doing a "canSend" check before trying to sync the attachment
                        // causing the server to crash for early magnet ticking
                        if (Services.PLATFORM_HELPER.getPlatform() == IPlatformHelper.Platform.NEOFORGE) {
                            // noinspection ConstantConditions
                            if (player.connection != null) {
                                return ConfigApiJava.network().canSend(NEO_SYNC_PAYLOAD, player);
                            }
                            return false;
                        }
                        return true;
                    })
                    .copyOnDeath()
    );

    public static void init() {
    }
}
