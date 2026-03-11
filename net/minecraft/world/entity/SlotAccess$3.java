/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import java.util.function.Predicate;
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
/* 47 */     return entity.getItemBySlot(slot);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean set(ItemStack $$0) {
/* 52 */     if (!validator.test($$0)) {
/* 53 */       return false;
/*    */     }
/*    */     
/* 56 */     entity.setItemSlot(slot, $$0);
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\SlotAccess$3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */