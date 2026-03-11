/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.screens.inventory.MenuAccess;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.MenuType;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ interface ScreenConstructor<T extends net.minecraft.world.inventory.AbstractContainerMenu, U extends Screen & MenuAccess<T>>
/*    */ {
/*    */   default void fromPacket(Component $$0, MenuType<T> $$1, Minecraft $$2, int $$3) {
/* 63 */     U $$4 = create((T)$$1.create($$3, $$2.player.getInventory()), $$2.player.getInventory(), $$0);
/*    */     
/* 65 */     $$2.player.containerMenu = ((MenuAccess)$$4).getMenu();
/* 66 */     $$2.setScreen((Screen)$$4);
/*    */   }
/*    */   
/*    */   U create(T paramT, Inventory paramInventory, Component paramComponent);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\MenuScreens$ScreenConstructor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */