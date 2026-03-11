/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.entity.projectile.ThrownPotion;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ThrowablePotionItem extends PotionItem {
/*    */   public ThrowablePotionItem(Item.Properties $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 17 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 18 */     if (!$$0.isClientSide) {
/* 19 */       ThrownPotion $$4 = new ThrownPotion($$0, (LivingEntity)$$1);
/* 20 */       $$4.setItem($$3);
/* 21 */       $$4.shootFromRotation((Entity)$$1, $$1.getXRot(), $$1.getYRot(), -20.0F, 0.5F, 1.0F);
/* 22 */       $$0.addFreshEntity((Entity)$$4);
/*    */     } 
/* 24 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 25 */     if (!($$1.getAbilities()).instabuild) {
/* 26 */       $$3.shrink(1);
/*    */     }
/* 28 */     return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ThrowablePotionItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */