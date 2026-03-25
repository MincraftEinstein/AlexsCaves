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
import org.teamvoided.voidlib.attachments.AttachmentBuilder;
import org.teamvoided.voidlib.attachments.AttachmentSupplier;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ACAttachmentRegistry {

    private static final ResourceLocation NEO_SYNC_PAYLOAD = ResourceLocation.fromNamespaceAndPath("neoforge", "sync_attachments");

    public static AttachmentSupplier<MagneticEntityData, Entity> MAGNETIC_DATA = register("magnetic_data", Entity.class,
            () -> MagneticEntityData.DEFAULT,
            builder -> builder.persistent(MagneticEntityData.CODEC)
                    .synced(MagneticEntityData.STREAM_CODEC, ACAttachmentRegistry::canSendNeo)
                    .copyOnDeath()
    );

    public static AttachmentSupplier<Optional<Vec3>, Entity> OPTIONAL_VEC = register("optional_vec", Entity.class,
            Optional::empty,
            builder -> builder.persistent(Vec3.CODEC.xmap(Optional::ofNullable, (vec) -> vec.orElse(null)))
                    .synced(ACMath.OPTIONAL_VEC3_STREAM_CODEC, ACAttachmentRegistry::canSendNeo)
                    .copyOnDeath()
    );

    public static AttachmentSupplier<GummyColors, Entity> GUMMY_COLOR = register("gummy_color", Entity.class,
            () -> GummyColors.RED,
            builder -> builder.persistent(GummyColors.CODEC)
                    .synced(GummyColors.STREAM_CODEC, ACAttachmentRegistry::canSendNeo)
                    .copyOnDeath()
    );

    public static final AttachmentSupplier<Boolean, Entity> TOTEM_POSSESSED = register("totem_possessed", Entity.class,
            () -> false,
            builder -> builder.persistent(Codec.BOOL)
                    .copyOnDeath()
    );

    public static final AttachmentSupplier<Boolean, Player> SPELUNKERY_TUTORIAL_COMPLETE = register("spelunkery_tutorial_complete", Player.class,
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

    private static <T, V> AttachmentSupplier<T, V> register(String name, Class<V> holderClass, Supplier<T> defaultSupplier, UnaryOperator<AttachmentBuilder<T, V>> builderSupplier) {
        return Services.REGISTRY_HELPER.registerAttachment(name, holderClass, defaultSupplier, builderSupplier);
    }
}
