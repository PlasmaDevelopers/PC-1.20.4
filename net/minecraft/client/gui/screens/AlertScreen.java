/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class AlertScreen extends Screen {
/*    */   private static final int LABEL_Y = 90;
/* 14 */   private MultiLineLabel message = MultiLineLabel.EMPTY;
/*    */   private final Component messageText;
/*    */   private final Runnable callback;
/*    */   private final Component okButton;
/*    */   private final boolean shouldCloseOnEsc;
/*    */   
/*    */   public AlertScreen(Runnable $$0, Component $$1, Component $$2) {
/* 21 */     this($$0, $$1, $$2, CommonComponents.GUI_BACK, true);
/*    */   }
/*    */   
/*    */   public AlertScreen(Runnable $$0, Component $$1, Component $$2, Component $$3, boolean $$4) {
/* 25 */     super($$1);
/* 26 */     this.callback = $$0;
/* 27 */     this.messageText = $$2;
/* 28 */     this.okButton = $$3;
/* 29 */     this.shouldCloseOnEsc = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 34 */     return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), this.messageText });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 39 */     super.init();
/*    */     
/* 41 */     this.message = MultiLineLabel.create(this.font, (FormattedText)this.messageText, this.width - 50);
/*    */     
/* 43 */     Objects.requireNonNull(this.font); int $$0 = this.message.getLineCount() * 9;
/* 44 */     int $$1 = Mth.clamp(90 + $$0 + 12, this.height / 6 + 96, this.height - 24);
/* 45 */     int $$2 = 150;
/*    */     
/* 47 */     addRenderableWidget(Button.builder(this.okButton, $$0 -> this.callback.run()).bounds((this.width - 150) / 2, $$1, 150, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 52 */     super.render($$0, $$1, $$2, $$3);
/* 53 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 70, 16777215);
/* 54 */     this.message.renderCentered($$0, this.width / 2, 90);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 59 */     return this.shouldCloseOnEsc;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\AlertScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */