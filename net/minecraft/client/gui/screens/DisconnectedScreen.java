/*    */ package net.minecraft.client.gui.screens;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*    */ import net.minecraft.client.gui.components.StringWidget;
/*    */ import net.minecraft.client.gui.layouts.FrameLayout;
/*    */ import net.minecraft.client.gui.layouts.LayoutElement;
/*    */ import net.minecraft.client.gui.layouts.LinearLayout;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class DisconnectedScreen extends Screen {
/* 12 */   private static final Component TO_SERVER_LIST = (Component)Component.translatable("gui.toMenu");
/* 13 */   private static final Component TO_TITLE = (Component)Component.translatable("gui.toTitle");
/*    */   
/*    */   private final Screen parent;
/*    */   
/*    */   private final Component reason;
/*    */   private final Component buttonText;
/* 19 */   private final LinearLayout layout = LinearLayout.vertical();
/*    */   
/*    */   public DisconnectedScreen(Screen $$0, Component $$1, Component $$2) {
/* 22 */     this($$0, $$1, $$2, TO_SERVER_LIST);
/*    */   }
/*    */   
/*    */   public DisconnectedScreen(Screen $$0, Component $$1, Component $$2, Component $$3) {
/* 26 */     super($$1);
/* 27 */     this.parent = $$0;
/* 28 */     this.reason = $$2;
/* 29 */     this.buttonText = $$3;
/*    */   }
/*    */   
/*    */   protected void init() {
/*    */     Button $$1;
/* 34 */     this.layout.defaultCellSetting().alignHorizontallyCenter().padding(10);
/* 35 */     this.layout.addChild((LayoutElement)new StringWidget(this.title, this.font));
/* 36 */     this.layout.addChild((LayoutElement)(new MultiLineTextWidget(this.reason, this.font)).setMaxWidth(this.width - 50).setCentered(true));
/*    */     
/* 38 */     if (this.minecraft.allowsMultiplayer()) {
/* 39 */       Button $$0 = Button.builder(this.buttonText, $$0 -> this.minecraft.setScreen(this.parent)).build();
/*    */     } else {
/* 41 */       $$1 = Button.builder(TO_TITLE, $$0 -> this.minecraft.setScreen(new TitleScreen())).build();
/*    */     } 
/* 43 */     this.layout.addChild((LayoutElement)$$1);
/* 44 */     this.layout.arrangeElements();
/* 45 */     this.layout.visitWidgets(this::addRenderableWidget);
/* 46 */     repositionElements();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void repositionElements() {
/* 51 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 56 */     return (Component)CommonComponents.joinForNarration(new Component[] { this.title, this.reason });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\DisconnectedScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */