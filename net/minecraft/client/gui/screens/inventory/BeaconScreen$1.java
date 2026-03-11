/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.BeaconMenu;
/*    */ import net.minecraft.world.inventory.ContainerListener;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ContainerListener
/*    */ {
/*    */   public void slotChanged(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {}
/*    */   
/*    */   public void dataChanged(AbstractContainerMenu $$0, int $$1, int $$2) {
/* 59 */     BeaconScreen.this.primary = menu.getPrimaryEffect();
/* 60 */     BeaconScreen.this.secondary = menu.getSecondaryEffect();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BeaconScreen$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */