/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextAndIcon
/*    */   extends SpriteIconButton
/*    */ {
/*    */   protected TextAndIcon(int $$0, int $$1, Component $$2, int $$3, int $$4, ResourceLocation $$5, Button.OnPress $$6) {
/* 46 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 51 */     super.renderWidget($$0, $$1, $$2, $$3);
/* 52 */     int $$4 = getX() + getWidth() - this.spriteWidth - 2;
/* 53 */     int $$5 = getY() + getHeight() / 2 - this.spriteHeight / 2;
/* 54 */     $$0.blitSprite(this.sprite, $$4, $$5, this.spriteWidth, this.spriteHeight);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderString(GuiGraphics $$0, Font $$1, int $$2) {
/* 59 */     int $$3 = getX() + 2;
/* 60 */     int $$4 = getX() + getWidth() - this.spriteWidth - 4;
/* 61 */     int $$5 = getX() + getWidth() / 2;
/* 62 */     renderScrollingString($$0, $$1, getMessage(), $$5, $$3, getY(), $$4, getY() + getHeight(), $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\SpriteIconButton$TextAndIcon.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */