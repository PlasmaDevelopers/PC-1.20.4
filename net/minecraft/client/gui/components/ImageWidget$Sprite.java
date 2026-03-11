/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class Sprite
/*    */   extends ImageWidget
/*    */ {
/*    */   private final ResourceLocation sprite;
/*    */   
/*    */   public Sprite(int $$0, int $$1, int $$2, int $$3, ResourceLocation $$4) {
/* 49 */     super($$0, $$1, $$2, $$3);
/* 50 */     this.sprite = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 55 */     $$0.blitSprite(this.sprite, getX(), getY(), getWidth(), getHeight());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\ImageWidget$Sprite.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */