package com.github.alexmodguy.alexscaves.server.message;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.item.UpdatesStackTags;
import me.fzzyhmstrs.fzzy_config.networking.api.ClientPlayNetworkContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class UpdateItemTagMessage implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateItemTagMessage> TYPE =
        new CustomPacketPayload.Type<>(AlexsCaves.id("update_item_tag"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateItemTagMessage> CODEC =
        StreamCodec.ofMember(UpdateItemTagMessage::write, UpdateItemTagMessage::read);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    private int entityId;
    private ItemStack itemStackFrom;

    public UpdateItemTagMessage(int entityId, ItemStack itemStackFrom) {
        this.entityId = entityId;
        this.itemStackFrom = itemStackFrom;
    }


    public static UpdateItemTagMessage read(RegistryFriendlyByteBuf buf) {
        return new UpdateItemTagMessage(buf.readInt(), ItemStack.STREAM_CODEC.decode(buf));
    }

    public static void write(UpdateItemTagMessage message, RegistryFriendlyByteBuf buf) {
        buf.writeInt(message.entityId);
        ItemStack.STREAM_CODEC.encode(buf, message.itemStackFrom);
    }

    public static void handle(UpdateItemTagMessage message, ClientPlayNetworkContext context) {
        context.execute(() -> {
            Player playerSided = context.player();
            // For client-bound packets, use the client-side player
            if (context.networkSide().isClientbound()) {
                playerSided = AlexsCaves.PROXY.getClientSidePlayer();
            }
            if (playerSided != null) {
                Entity holder = playerSided.level().getEntity(message.entityId);

                if (holder instanceof LivingEntity living) {
                    ItemStack stackFrom = message.itemStackFrom;
                    ItemStack to = null;
                    if (living.getItemInHand(InteractionHand.MAIN_HAND).is(stackFrom.getItem())) {
                        to = living.getItemInHand(InteractionHand.MAIN_HAND);
                    } else if (living.getItemInHand(InteractionHand.OFF_HAND).is(stackFrom.getItem())) {
                        to = living.getItemInHand(InteractionHand.OFF_HAND);
                    }
                    if (to != null && to.getItem() instanceof UpdatesStackTags updatesStackTags) {
                        CompoundTag tag = stackFrom.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                        updatesStackTags.updateTagFromServer(holder, to, tag);
                    }
                }
            }
        });
    }

}
