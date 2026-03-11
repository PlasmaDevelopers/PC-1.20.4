/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*    */ import net.minecraft.world.entity.projectile.SpectralArrow;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class SpectralArrowItem extends ArrowItem {
/*    */   public SpectralArrowItem(Item.Properties $$0) {
/* 10 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractArrow createArrow(Level $$0, ItemStack $$1, LivingEntity $$2) {
/* 15 */     return (AbstractArrow)new SpectralArrow($$0, $$2, $$1.copyWithCount(1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SpectralArrowItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */