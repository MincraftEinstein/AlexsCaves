package com.github.alexmodguy.alexscaves.server.entity.util;

import com.github.alexmodguy.alexscaves.platform.Services;
import net.minecraft.world.entity.Entity;
import org.teamvoided.voidlib.attachments.AttachmentBuilder;
import org.teamvoided.voidlib.attachments.AttachmentSupplier;

public class ACAttachmentRegistry {

    public static AttachmentSupplier<MagneticEntityData, Entity> MAGNETIC_DATA = Services.REGISTRY_HELPER.registerAttachment("magnetic_data",
            () -> new AttachmentBuilder<>(() -> MagneticEntityData.DEFAULT)
                    .persistent(MagneticEntityData.CODEC)
                    .synced(MagneticEntityData.STREAM_CODEC)
                    .copyOnDeath()
    );

    public static void init() {
    }
}
