/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ 
/*    */ public class GenericWaitingScreen
/*    */   extends Screen
/*    */ {
/*    */   private static final int TITLE_Y = 80;
/*    */   private static final int MESSAGE_Y = 120;
/*    */   private static final int MESSAGE_MAX_WIDTH = 360;
/*    */   @Nullable
/*    */   private final Component messageText;
/*    */   private final Component buttonLabel;
/*    */   private final Runnable buttonCallback;
/*    */   @Nullable
/*    */   private MultiLineLabel message;
/*    */   private Button button;
/*    */   private int disableButtonTicks;
/*    */   
/*    */   public static GenericWaitingScreen createWaiting(Component $$0, Component $$1, Runnable $$2) {
/* 29 */     return new GenericWaitingScreen($$0, null, $$1, $$2, 0);
/*    */   }
/*    */   
/*    */   public static GenericWaitingScreen createCompleted(Component $$0, Component $$1, Component $$2, Runnable $$3) {
/* 33 */     return new GenericWaitingScreen($$0, $$1, $$2, $$3, 20);
/*    */   }
/*    */   
/*    */   protected GenericWaitingScreen(Component $$0, @Nullable Component $$1, Component $$2, Runnable $$3, int $$4) {
/* 37 */     super($$0);
/* 38 */     this.messageText = $$1;
/* 39 */     this.buttonLabel = $$2;
/* 40 */     this.buttonCallback = $$3;
/* 41 */     this.disableButtonTicks = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 46 */     super.init();
/*    */     
/* 48 */     if (this.messageText != null) {
/* 49 */       this.message = MultiLineLabel.create(this.font, (FormattedText)this.messageText, 360);
/*    */     }
/*    */     
/* 52 */     int $$0 = 150;
/* 53 */     int $$1 = 20;
/*    */     
/* 55 */     int $$2 = (this.message != null) ? this.message.getLineCount() : 1;
/* 56 */     Objects.requireNonNull(this.font); int $$3 = Math.max($$2, 5) * 9;
/* 57 */     int $$4 = Math.min(120 + $$3, this.height - 40);
/*    */     
/* 59 */     this.button = addRenderableWidget(Button.builder(this.buttonLabel, $$0 -> onClose()).bounds((this.width - 150) / 2, $$4, 150, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 64 */     if (this.disableButtonTicks > 0) {
/* 65 */       this.disableButtonTicks--;
/*    */     }
/* 67 */     this.button.active = (this.disableButtonTicks == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 72 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 74 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 80, 16777215);
/*    */     
/* 76 */     if (this.message == null) {
/* 77 */       String $$4 = LoadingDotsText.get(Util.getMillis());
/* 78 */       $$0.drawCenteredString(this.font, $$4, this.width / 2, 120, 10526880);
/*    */     } else {
/* 80 */       this.message.renderCentered($$0, this.width / 2, 120);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 86 */     return (this.message != null && this.button.active);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 91 */     this.buttonCallback.run();
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 96 */     return (Component)CommonComponents.joinForNarration(new Component[] { this.title, (this.messageText != null) ? this.messageText : CommonComponents.EMPTY });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\GenericWaitingScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */