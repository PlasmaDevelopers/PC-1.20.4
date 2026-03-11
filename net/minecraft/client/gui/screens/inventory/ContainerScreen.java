/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.ChestMenu;
/*    */ 
/*    */ public class ContainerScreen extends AbstractContainerScreen<ChestMenu> implements MenuAccess<ChestMenu> {
/* 10 */   private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");
/*    */   
/*    */   private final int containerRows;
/*    */   
/*    */   public ContainerScreen(ChestMenu $$0, Inventory $$1, Component $$2) {
/* 15 */     super($$0, $$1, $$2);
/*    */     
/* 17 */     int $$3 = 222;
/* 18 */     int $$4 = 114;
/* 19 */     this.containerRows = $$0.getRowCount();
/*    */     
/* 21 */     this.imageHeight = 114 + this.containerRows * 18;
/* 22 */     this.inventoryLabelY = this.imageHeight - 94;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 27 */     super.render($$0, $$1, $$2, $$3);
/* 28 */     renderTooltip($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 33 */     int $$4 = (this.width - this.imageWidth) / 2;
/* 34 */     int $$5 = (this.height - this.imageHeight) / 2;
/* 35 */     $$0.blit(CONTAINER_BACKGROUND, $$4, $$5, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
/* 36 */     $$0.blit(CONTAINER_BACKGROUND, $$4, $$5 + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\ContainerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */