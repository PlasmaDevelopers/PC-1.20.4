/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ 
/*    */ public class ConfirmLinkScreen extends ConfirmScreen {
/* 13 */   private static final Component COPY_BUTTON_TEXT = (Component)Component.translatable("chat.copy");
/* 14 */   private static final Component WARNING_TEXT = (Component)Component.translatable("chat.link.warning");
/*    */   private final String url;
/*    */   private final boolean showWarning;
/*    */   
/*    */   public ConfirmLinkScreen(BooleanConsumer $$0, String $$1, boolean $$2) {
/* 19 */     this($$0, (Component)confirmMessage($$2), (Component)Component.literal($$1), $$1, $$2 ? CommonComponents.GUI_CANCEL : CommonComponents.GUI_NO, $$2);
/*    */   }
/*    */   
/*    */   public ConfirmLinkScreen(BooleanConsumer $$0, Component $$1, String $$2, boolean $$3) {
/* 23 */     this($$0, $$1, (Component)confirmMessage($$3, $$2), $$2, $$3 ? CommonComponents.GUI_CANCEL : CommonComponents.GUI_NO, $$3);
/*    */   }
/*    */   
/*    */   public ConfirmLinkScreen(BooleanConsumer $$0, Component $$1, Component $$2, String $$3, Component $$4, boolean $$5) {
/* 27 */     super($$0, $$1, $$2);
/*    */     
/* 29 */     this.yesButton = $$5 ? (Component)Component.translatable("chat.link.open") : CommonComponents.GUI_YES;
/* 30 */     this.noButton = $$4;
/* 31 */     this.showWarning = !$$5;
/* 32 */     this.url = $$3;
/*    */   }
/*    */   
/*    */   protected static MutableComponent confirmMessage(boolean $$0, String $$1) {
/* 36 */     return confirmMessage($$0).append(CommonComponents.SPACE).append((Component)Component.literal($$1));
/*    */   }
/*    */   
/*    */   protected static MutableComponent confirmMessage(boolean $$0) {
/* 40 */     return Component.translatable($$0 ? "chat.link.confirmTrusted" : "chat.link.confirm");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void addButtons(int $$0) {
/* 46 */     addRenderableWidget(Button.builder(this.yesButton, $$0 -> this.callback.accept(true)).bounds(this.width / 2 - 50 - 105, $$0, 100, 20).build());
/* 47 */     addRenderableWidget(Button.builder(COPY_BUTTON_TEXT, $$0 -> {
/*    */             copyToClipboard();
/*    */             this.callback.accept(false);
/* 50 */           }).bounds(this.width / 2 - 50, $$0, 100, 20).build());
/* 51 */     addRenderableWidget(Button.builder(this.noButton, $$0 -> this.callback.accept(false)).bounds(this.width / 2 - 50 + 105, $$0, 100, 20).build());
/*    */   }
/*    */   
/*    */   public void copyToClipboard() {
/* 55 */     this.minecraft.keyboardHandler.setClipboard(this.url);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 60 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 62 */     if (this.showWarning) {
/* 63 */       $$0.drawCenteredString(this.font, WARNING_TEXT, this.width / 2, 110, 16764108);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void confirmLinkNow(Screen $$0, String $$1) {
/* 68 */     Minecraft $$2 = Minecraft.getInstance();
/* 69 */     $$2.setScreen(new ConfirmLinkScreen($$3 -> { if ($$3) Util.getPlatform().openUri($$0);  $$1.setScreen($$2); }$$1, true));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Button.OnPress confirmLink(Screen $$0, String $$1) {
/* 80 */     return $$2 -> confirmLinkNow($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ConfirmLinkScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */