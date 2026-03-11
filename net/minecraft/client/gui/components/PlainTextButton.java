/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentUtils;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class PlainTextButton extends Button {
/*    */   private final Font font;
/*    */   private final Component message;
/*    */   private final Component underlinedMessage;
/*    */   
/*    */   public PlainTextButton(int $$0, int $$1, int $$2, int $$3, Component $$4, Button.OnPress $$5, Font $$6) {
/* 16 */     super($$0, $$1, $$2, $$3, $$4, $$5, DEFAULT_NARRATION);
/* 17 */     this.font = $$6;
/* 18 */     this.message = $$4;
/* 19 */     this.underlinedMessage = (Component)ComponentUtils.mergeStyles($$4.copy(), Style.EMPTY.withUnderlined(Boolean.valueOf(true)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 24 */     Component $$4 = isHoveredOrFocused() ? this.underlinedMessage : this.message;
/* 25 */     $$0.drawString(this.font, $$4, getX(), getY(), 0xFFFFFF | Mth.ceil(this.alpha * 255.0F) << 24);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\PlainTextButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */