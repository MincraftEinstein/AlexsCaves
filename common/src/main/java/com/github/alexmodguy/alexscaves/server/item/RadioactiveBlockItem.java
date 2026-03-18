package com.github.alexmodguy.alexscaves.server.item;

import com.github.alexthe666.citadel.item.BlockItemWithSupplier;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class RadioactiveBlockItem extends BlockItemWithSupplier {

    private final float randomChanceOfRadiation;

    public RadioactiveBlockItem(Supplier<Block> blockSupplier, Properties props, float randomChanceOfRadiation) {
        super(blockSupplier, props);
        this.randomChanceOfRadiation = randomChanceOfRadiation;
    }

//    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean held) {
//        super.inventoryTick(stack, level, entity, i, held);
//        if (!level.isClientSide && entity instanceof LivingEntity living && !(living instanceof Player player && player.isCreative())) {
//            float stackChance = stack.getCount() * randomChanceOfRadiation;
//            float hazmatMultiplier = 1F - HazmatArmorItem.getWornAmount(living) / 4F;
//            if (!living.hasEffect(ACEffectRegistry.IRRADIATED) && level.random.nextFloat() < stackChance * hazmatMultiplier) {
//                MobEffectInstance instance = new MobEffectInstance(ACEffectRegistry.IRRADIATED, 1800);
//                living.addEffect(instance);
//                AlexsCavesNeoForge.sendMSGToAll(new UpdateEffectVisualityEntityMessage(entity.getId(), entity.getId(), 0, instance.getDuration()));
//            }
//        }
//    }
}
