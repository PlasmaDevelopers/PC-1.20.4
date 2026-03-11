/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class CrafterSlot
/*    */   extends Slot {
/*    */   private final CrafterMenu menu;
/*    */   
/*    */   public CrafterSlot(Container $$0, int $$1, int $$2, int $$3, CrafterMenu $$4) {
/* 11 */     super($$0, $$1, $$2, $$3);
/* 12 */     this.menu = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack $$0) {
/* 17 */     return (!this.menu.isSlotDisabled(this.index) && super.mayPlace($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setChanged() {
/* 22 */     super.setChanged();
/* 23 */     this.menu.slotsChanged(this.container);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\CrafterSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */