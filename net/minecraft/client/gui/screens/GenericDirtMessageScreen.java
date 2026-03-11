/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class GenericDirtMessageScreen extends Screen {
/*    */   public GenericDirtMessageScreen(Component $$0) {
/*  8 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 13 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldNarrateNavigation() {
/* 18 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 23 */     super.render($$0, $$1, $$2, $$3);
/* 24 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 70, 16777215);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 29 */     renderDirtBackground($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\GenericDirtMessageScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */