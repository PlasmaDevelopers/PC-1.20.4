/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements SlotAccess
/*    */ {
/*    */   public ItemStack get() {
/* 25 */     return inventory.getItem(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean set(ItemStack $$0) {
/* 30 */     if (!validator.test($$0)) {
/* 31 */       return false;
/*    */     }
/* 33 */     inventory.setItem(id, $$0);
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\SlotAccess$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */