/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.gui.ComponentPath;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*    */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*    */ import net.minecraft.client.gui.screens.LoadingDotsText;
/*    */ import net.minecraft.client.sounds.SoundManager;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ 
/*    */ public class LoadingDotsWidget extends AbstractWidget {
/*    */   private final Font font;
/*    */   
/*    */   public LoadingDotsWidget(Font $$0, Component $$1) {
/* 20 */     super(0, 0, $$0.width((FormattedText)$$1), 9 * 3, $$1);
/* 21 */     this.font = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 26 */     int $$4 = getX() + getWidth() / 2;
/* 27 */     int $$5 = getY() + getHeight() / 2;
/*    */     
/* 29 */     Component $$6 = getMessage();
/* 30 */     Objects.requireNonNull(this.font); $$0.drawString(this.font, $$6, $$4 - this.font.width((FormattedText)$$6) / 2, $$5 - 9, -1, false);
/*    */     
/* 32 */     String $$7 = LoadingDotsText.get(Util.getMillis());
/* 33 */     Objects.requireNonNull(this.font); $$0.drawString(this.font, $$7, $$4 - this.font.width($$7) / 2, $$5 + 9, -8355712, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateWidgetNarration(NarrationElementOutput $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void playDownSound(SoundManager $$0) {}
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\LoadingDotsWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */