/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ExperienceBottleItem extends Item {
/*    */   public ExperienceBottleItem(Item.Properties $$0) {
/* 14 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFoil(ItemStack $$0) {
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 24 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 25 */     $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / ($$0.getRandom().nextFloat() * 0.4F + 0.8F));
/* 26 */     if (!$$0.isClientSide) {
/* 27 */       ThrownExperienceBottle $$4 = new ThrownExperienceBottle($$0, (LivingEntity)$$1);
/* 28 */       $$4.setItem($$3);
/* 29 */       $$4.shootFromRotation((Entity)$$1, $$1.getXRot(), $$1.getYRot(), -20.0F, 0.7F, 1.0F);
/* 30 */       $$0.addFreshEntity((Entity)$$4);
/*    */     } 
/* 32 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 33 */     if (!($$1.getAbilities()).instabuild) {
/* 34 */       $$3.shrink(1);
/*    */     }
/* 36 */     return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ExperienceBottleItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */