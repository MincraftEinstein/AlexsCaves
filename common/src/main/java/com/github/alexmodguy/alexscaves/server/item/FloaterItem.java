package com.github.alexmodguy.alexscaves.server.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FloaterItem extends Item {
    public FloaterItem() {
        super(new Item.Properties());
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isInWaterOrBubble() && !player.isShiftKeyDown()) {
            // TODO fix when entity
          /*  FloaterEntity floaterEntity = ACEntityRegistry.FLOATER.get().create(level);
            floaterEntity.copyPosition(player);
            if(!level.isClientSide){
                level.addFreshEntity(floaterEntity);
            }
            player.getRootVehicle().startRiding(floaterEntity);*/
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            return InteractionResultHolder.pass(itemstack);
        }
    }
}
