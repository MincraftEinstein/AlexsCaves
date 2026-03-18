package com.github.alexmodguy.alexscaves.server.item;

import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class RadioactiveOnDestroyedBlockItem extends RadioactiveBlockItem {

    public RadioactiveOnDestroyedBlockItem(Supplier<Block> blockSupplier, Properties props, float randomChanceOfRadiation) {
        super(blockSupplier, props, randomChanceOfRadiation);
    }

    // TODO
//    @Override
//    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource){
//        super.onDestroyed(itemEntity, damageSource);
//        if(!damageSource.isCreativePlayer() && !itemEntity.isRemoved()){
//            itemEntity.discard();
//            AreaEffectCloud cloud = new AreaEffectCloud(itemEntity.level(), itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
//            cloud.setParticle(ACParticleRegistry.GAMMAROACH.get());
//            cloud.setPotionContents(new PotionContents(java.util.Optional.empty(), java.util.Optional.of(0X77D60E), java.util.List.of()));
//            cloud.addEffect(new MobEffectInstance(ACEffectRegistry.IRRADIATED, 9600, 1));
//            cloud.setRadius(2.0F);
//            cloud.setDuration(12000);
//            cloud.setWaitTime(0);
//            cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
//            itemEntity.level().addFreshEntity(cloud);
//            itemEntity.level().explode(null, itemEntity.getX(), itemEntity.getY() + 0.5F, itemEntity.getZ(), 1.8F, Level.ExplosionInteraction.BLOCK);
//        }
//    }
}
