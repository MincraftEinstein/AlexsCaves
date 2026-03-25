package com.github.alexmodguy.alexscaves.server.entity.util;

import com.github.alexmodguy.alexscaves.platform.Services;
import com.github.alexmodguy.alexscaves.platform.services.IPlatformHelper;
import com.github.alexmodguy.alexscaves.server.misc.ACMath;
import com.mojang.serialization.Codec;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.teamvoided.voidlib.attachments.AttachmentSupplier;

import java.util.Optional;

public class ACAttachmentRegistry {

    private static final ResourceLocation NEO_SYNC_PAYLOAD = ResourceLocation.fromNamespaceAndPath("neoforge", "sync_attachments");

    public static AttachmentSupplier<MagneticEntityData, Entity> MAGNETIC_DATA = Services.REGISTRY_HELPER.registerAttachment("magnetic_data", Entity.class,
            () -> MagneticEntityData.DEFAULT,
            builder -> builder.persistent(MagneticEntityData.CODEC)
                    .synced(MagneticEntityData.STREAM_CODEC, ACAttachmentRegistry::canSendNeo)
                    .copyOnDeath()
    );

    public static AttachmentSupplier<Optional<Vec3>, Entity> OPTIONAL_VEC = Services.REGISTRY_HELPER.registerAttachment("optional_vec", Entity.class,
            Optional::empty,
            builder -> builder.persistent(Vec3.CODEC.xmap(Optional::ofNullable, (vec) -> vec.orElse(null)))
                    .synced(ACMath.OPTIONAL_VEC3_STREAM_CODEC, ACAttachmentRegistry::canSendNeo)
                    .copyOnDeath()
    );

    public static AttachmentSupplier<GummyColors, Entity> GUMMY_COLOR = Services.REGISTRY_HELPER.registerAttachment("gummy_color", Entity.class,
            () -> GummyColors.RED,
            builder -> builder.persistent(GummyColors.CODEC)
                    .synced(GummyColors.STREAM_CODEC, ACAttachmentRegistry::canSendNeo)
                    .copyOnDeath()
    );

    public static final AttachmentSupplier<Boolean, Entity> TOTEM_POSSESSED = Services.REGISTRY_HELPER.registerAttachment("totem_possessed", Entity.class,
            () -> false,
            builder -> builder.persistent(Codec.BOOL)
                    .copyOnDeath()
    );

    public static final AttachmentSupplier<Boolean, Player> SPELUNKERY_TUTORIAL_COMPLETE = Services.REGISTRY_HELPER.registerAttachment("spelunkery_tutorial_complete", Player.class,
            () -> false,
            builder -> builder.persistent(Codec.BOOL)
                    .synced(ByteBufCodecs.BOOL)
                    .copyOnDeath()
    );

    public static void init() {
    }

    // Hacky fix for NeoForge not doing a "canSend" check before trying to sync the attachment
    // causing the server to crash for early magnet ticking
    private static boolean canSendNeo(Entity entity, ServerPlayer player) {
        if (Services.PLATFORM_HELPER.getPlatform() == IPlatformHelper.Platform.NEOFORGE) {
            // noinspection ConstantConditions
            if (player.connection != null) {
                return ConfigApiJava.network().canSend(NEO_SYNC_PAYLOAD, player);
            }
            return false;
        }
        return true;
    }
}
