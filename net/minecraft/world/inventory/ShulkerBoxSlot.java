/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ShulkerBoxSlot extends Slot {
/*    */   public ShulkerBoxSlot(Container $$0, int $$1, int $$2, int $$3) {
/*  8 */     super($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack $$0) {
/* 13 */     return $$0.getItem().canFitInsideContainerItems();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ShulkerBoxSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */