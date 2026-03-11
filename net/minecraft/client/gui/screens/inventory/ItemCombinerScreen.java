/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.ContainerListener;
/*    */ import net.minecraft.world.inventory.ItemCombinerMenu;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public abstract class ItemCombinerScreen<T extends ItemCombinerMenu> extends AbstractContainerScreen<T> implements ContainerListener {
/*    */   private final ResourceLocation menuResource;
/*    */   
/*    */   public ItemCombinerScreen(T $$0, Inventory $$1, Component $$2, ResourceLocation $$3) {
/* 16 */     super($$0, $$1, $$2);
/* 17 */     this.menuResource = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void subInit() {}
/*    */ 
/*    */   
/*    */   protected void init() {
/* 25 */     super.init();
/* 26 */     subInit();
/* 27 */     ((ItemCombinerMenu)this.menu).addSlotListener(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removed() {
/* 32 */     super.removed();
/*    */     
/* 34 */     ((ItemCombinerMenu)this.menu).removeSlotListener(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 39 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 41 */     renderFg($$0, $$1, $$2, $$3);
/*    */     
/* 43 */     renderTooltip($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderFg(GuiGraphics $$0, int $$1, int $$2, float $$3) {}
/*    */ 
/*    */   
/*    */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 51 */     $$0.blit(this.menuResource, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
/* 52 */     renderErrorIcon($$0, this.leftPos, this.topPos);
/*    */   }
/*    */   
/*    */   protected abstract void renderErrorIcon(GuiGraphics paramGuiGraphics, int paramInt1, int paramInt2);
/*    */   
/*    */   public void dataChanged(AbstractContainerMenu $$0, int $$1, int $$2) {}
/*    */   
/*    */   public void slotChanged(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\ItemCombinerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */