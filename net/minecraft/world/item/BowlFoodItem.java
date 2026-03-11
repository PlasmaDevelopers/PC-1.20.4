/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class BowlFoodItem extends Item {
/*    */   public BowlFoodItem(Item.Properties $$0) {
/*  9 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack finishUsingItem(ItemStack $$0, Level $$1, LivingEntity $$2) {
/* 14 */     ItemStack $$3 = super.finishUsingItem($$0, $$1, $$2);
/* 15 */     if ($$2 instanceof Player && (((Player)$$2).getAbilities()).instabuild) {
/* 16 */       return $$3;
/*    */     }
/* 18 */     return new ItemStack(Items.BOWL);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BowlFoodItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */