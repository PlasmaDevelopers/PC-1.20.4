/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.DispenserMenu;
/*    */ 
/*    */ public class DispenserScreen extends AbstractContainerScreen<DispenserMenu> {
/* 10 */   private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation("textures/gui/container/dispenser.png");
/*    */   
/*    */   public DispenserScreen(DispenserMenu $$0, Inventory $$1, Component $$2) {
/* 13 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 18 */     super.init();
/* 19 */     this.titleLabelX = (this.imageWidth - this.font.width((FormattedText)this.title)) / 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 24 */     super.render($$0, $$1, $$2, $$3);
/* 25 */     renderTooltip($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 30 */     int $$4 = (this.width - this.imageWidth) / 2;
/* 31 */     int $$5 = (this.height - this.imageHeight) / 2;
/* 32 */     $$0.blit(CONTAINER_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\DispenserScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */