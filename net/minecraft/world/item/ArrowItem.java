/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*    */ import net.minecraft.world.entity.projectile.Arrow;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ArrowItem extends Item {
/*    */   public ArrowItem(Item.Properties $$0) {
/* 10 */     super($$0);
/*    */   }
/*    */   
/*    */   public AbstractArrow createArrow(Level $$0, ItemStack $$1, LivingEntity $$2) {
/* 14 */     Arrow $$3 = new Arrow($$0, $$2, $$1.copyWithCount(1));
/* 15 */     $$3.setEffectsFromItem($$1);
/* 16 */     return (AbstractArrow)$$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ArrowItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */