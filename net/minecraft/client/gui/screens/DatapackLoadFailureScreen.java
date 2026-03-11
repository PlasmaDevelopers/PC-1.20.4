/*    */ package net.minecraft.client.gui.screens;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ 
/*    */ public class DatapackLoadFailureScreen extends Screen {
/* 10 */   private MultiLineLabel message = MultiLineLabel.EMPTY;
/*    */   
/*    */   private final Runnable cancelCallback;
/*    */   private final Runnable safeModeCallback;
/*    */   
/*    */   public DatapackLoadFailureScreen(Runnable $$0, Runnable $$1) {
/* 16 */     super((Component)Component.translatable("datapackFailure.title"));
/* 17 */     this.cancelCallback = $$0;
/* 18 */     this.safeModeCallback = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 23 */     super.init();
/*    */     
/* 25 */     this.message = MultiLineLabel.create(this.font, (FormattedText)getTitle(), this.width - 50);
/* 26 */     addRenderableWidget(Button.builder((Component)Component.translatable("datapackFailure.safeMode"), $$0 -> this.safeModeCallback.run()).bounds(this.width / 2 - 155, this.height / 6 + 96, 150, 20).build());
/* 27 */     addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, $$0 -> this.cancelCallback.run()).bounds(this.width / 2 - 155 + 160, this.height / 6 + 96, 150, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 32 */     super.render($$0, $$1, $$2, $$3);
/* 33 */     this.message.renderCentered($$0, this.width / 2, 70);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\DatapackLoadFailureScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */