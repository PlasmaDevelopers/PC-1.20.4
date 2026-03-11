/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.entity.projectile.Snowball;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class SnowballItem extends Item {
/*    */   public SnowballItem(Item.Properties $$0) {
/* 14 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 19 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 20 */     $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / ($$0.getRandom().nextFloat() * 0.4F + 0.8F));
/* 21 */     if (!$$0.isClientSide) {
/* 22 */       Snowball $$4 = new Snowball($$0, (LivingEntity)$$1);
/* 23 */       $$4.setItem($$3);
/* 24 */       $$4.shootFromRotation((Entity)$$1, $$1.getXRot(), $$1.getYRot(), 0.0F, 1.5F, 1.0F);
/* 25 */       $$0.addFreshEntity((Entity)$$4);
/*    */     } 
/* 27 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 28 */     if (!($$1.getAbilities()).instabuild) {
/* 29 */       $$3.shrink(1);
/*    */     }
/* 31 */     return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SnowballItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */