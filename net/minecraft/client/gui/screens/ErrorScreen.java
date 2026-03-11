/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class ErrorScreen extends Screen {
/*    */   private final Component message;
/*    */   
/*    */   public ErrorScreen(Component $$0, Component $$1) {
/* 12 */     super($$0);
/* 13 */     this.message = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 18 */     super.init();
/*    */     
/* 20 */     addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.minecraft.setScreen(null)).bounds(this.width / 2 - 100, 140, 200, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 25 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 27 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 90, 16777215);
/* 28 */     $$0.drawCenteredString(this.font, this.message, this.width / 2, 110, 16777215);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 33 */     $$0.fillGradient(0, 0, this.width, this.height, -12574688, -11530224);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ErrorScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */