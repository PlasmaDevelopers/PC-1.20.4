/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.ContainerListener;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class CreativeInventoryListener implements ContainerListener {
/*    */   private final Minecraft minecraft;
/*    */   
/*    */   public CreativeInventoryListener(Minecraft $$0) {
/* 12 */     this.minecraft = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void slotChanged(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {
/* 17 */     this.minecraft.gameMode.handleCreativeModeItemAdd($$2, $$1);
/*    */   }
/*    */   
/*    */   public void dataChanged(AbstractContainerMenu $$0, int $$1, int $$2) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\CreativeInventoryListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */