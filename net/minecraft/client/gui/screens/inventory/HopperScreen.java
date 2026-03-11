/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.HopperMenu;
/*    */ 
/*    */ public class HopperScreen extends AbstractContainerScreen<HopperMenu> {
/* 10 */   private static final ResourceLocation HOPPER_LOCATION = new ResourceLocation("textures/gui/container/hopper.png");
/*    */   
/*    */   public HopperScreen(HopperMenu $$0, Inventory $$1, Component $$2) {
/* 13 */     super($$0, $$1, $$2);
/* 14 */     this.imageHeight = 133;
/* 15 */     this.inventoryLabelY = this.imageHeight - 94;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 20 */     super.render($$0, $$1, $$2, $$3);
/* 21 */     renderTooltip($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 26 */     int $$4 = (this.width - this.imageWidth) / 2;
/* 27 */     int $$5 = (this.height - this.imageHeight) / 2;
/* 28 */     $$0.blit(HOPPER_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\HopperScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */