/*    */ package net.minecraft.realms;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class DisconnectedRealmsScreen extends RealmsScreen {
/* 13 */   private MultiLineLabel message = MultiLineLabel.EMPTY; private final Component reason;
/*    */   private final Screen parent;
/*    */   private int textHeight;
/*    */   
/*    */   public DisconnectedRealmsScreen(Screen $$0, Component $$1, Component $$2) {
/* 18 */     super($$1);
/* 19 */     this.parent = $$0;
/* 20 */     this.reason = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 25 */     this.minecraft.getDownloadedPackSource().cleanupAfterDisconnect();
/*    */     
/* 27 */     this.message = MultiLineLabel.create(this.font, (FormattedText)this.reason, this.width - 50);
/* 28 */     Objects.requireNonNull(this.font); this.textHeight = this.message.getLineCount() * 9;
/*    */ 
/*    */     
/* 31 */     Objects.requireNonNull(this.font); addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> this.minecraft.setScreen(this.parent)).bounds(this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + 9, 200, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 36 */     return (Component)Component.empty().append(this.title).append(": ").append(this.reason);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 41 */     Minecraft.getInstance().setScreen(this.parent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 46 */     super.render($$0, $$1, $$2, $$3);
/* 47 */     Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - this.textHeight / 2 - 9 * 2, 11184810);
/* 48 */     this.message.renderCentered($$0, this.width / 2, this.height / 2 - this.textHeight / 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\realms\DisconnectedRealmsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */