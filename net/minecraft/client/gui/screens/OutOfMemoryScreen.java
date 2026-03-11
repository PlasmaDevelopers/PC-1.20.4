/*    */ package net.minecraft.client.gui.screens;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ 
/*    */ public class OutOfMemoryScreen extends Screen {
/* 10 */   private MultiLineLabel message = MultiLineLabel.EMPTY;
/*    */   
/*    */   public OutOfMemoryScreen() {
/* 13 */     super((Component)Component.translatable("outOfMemory.title"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 18 */     addRenderableWidget(Button.builder(CommonComponents.GUI_TO_TITLE, $$0 -> this.minecraft.setScreen(new TitleScreen())).bounds(this.width / 2 - 155, this.height / 4 + 120 + 12, 150, 20).build());
/* 19 */     addRenderableWidget(Button.builder((Component)Component.translatable("menu.quit"), $$0 -> this.minecraft.stop()).bounds(this.width / 2 - 155 + 160, this.height / 4 + 120 + 12, 150, 20).build());
/* 20 */     this.message = MultiLineLabel.create(this.font, (FormattedText)Component.translatable("outOfMemory.message"), 295);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 30 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 32 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, this.height / 4 - 60 + 20, 16777215);
/* 33 */     this.message.renderLeftAligned($$0, this.width / 2 - 145, this.height / 4, 9, 10526880);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\OutOfMemoryScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */