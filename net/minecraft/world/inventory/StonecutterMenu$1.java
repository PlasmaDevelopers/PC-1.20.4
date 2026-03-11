/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.SimpleContainer;
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
/*    */   extends SimpleContainer
/*    */ {
/*    */   null(int $$1) {
/* 41 */     super($$1);
/*    */   }
/*    */   public void setChanged() {
/* 44 */     super.setChanged();
/* 45 */     StonecutterMenu.this.slotsChanged((Container)this);
/* 46 */     StonecutterMenu.this.slotUpdateListener.run();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\StonecutterMenu$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */