/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.entity.projectile.ThrownEnderpearl;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class EnderpearlItem extends Item {
/*    */   public EnderpearlItem(Item.Properties $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 20 */     ItemStack $$3 = $$1.getItemInHand($$2);
/*    */     
/* 22 */     $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / ($$0.getRandom().nextFloat() * 0.4F + 0.8F));
/* 23 */     $$1.getCooldowns().addCooldown(this, 20);
/* 24 */     if (!$$0.isClientSide) {
/* 25 */       ThrownEnderpearl $$4 = new ThrownEnderpearl($$0, (LivingEntity)$$1);
/* 26 */       $$4.setItem($$3);
/* 27 */       $$4.shootFromRotation((Entity)$$1, $$1.getXRot(), $$1.getYRot(), 0.0F, 1.5F, 1.0F);
/* 28 */       $$0.addFreshEntity((Entity)$$4);
/*    */     } 
/* 30 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 31 */     if (!($$1.getAbilities()).instabuild) {
/* 32 */       $$3.shrink(1);
/*    */     }
/* 34 */     return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\EnderpearlItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */