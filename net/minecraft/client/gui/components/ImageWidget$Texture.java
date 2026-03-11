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
/*    */ class Texture
/*    */   extends ImageWidget
/*    */ {
/*    */   private final ResourceLocation texture;
/*    */   private final int textureWidth;
/*    */   private final int textureHeight;
/*    */   
/*    */   public Texture(int $$0, int $$1, int $$2, int $$3, ResourceLocation $$4, int $$5, int $$6) {
/* 65 */     super($$0, $$1, $$2, $$3);
/* 66 */     this.texture = $$4;
/* 67 */     this.textureWidth = $$5;
/* 68 */     this.textureHeight = $$6;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 73 */     $$0.blit(this.texture, getX(), getY(), getWidth(), getHeight(), 0.0F, 0.0F, getWidth(), getHeight(), this.textureWidth, this.textureHeight);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\ImageWidget$Texture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */