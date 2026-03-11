/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ 
/*    */ public class SpriteCoordinateExpander implements VertexConsumer {
/*    */   private final VertexConsumer delegate;
/*    */   private final TextureAtlasSprite sprite;
/*    */   
/*    */   public SpriteCoordinateExpander(VertexConsumer $$0, TextureAtlasSprite $$1) {
/* 11 */     this.delegate = $$0;
/* 12 */     this.sprite = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer vertex(double $$0, double $$1, double $$2) {
/* 17 */     return this.delegate.vertex($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer color(int $$0, int $$1, int $$2, int $$3) {
/* 22 */     return this.delegate.color($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer uv(float $$0, float $$1) {
/* 27 */     return this.delegate.uv(this.sprite.getU($$0), this.sprite.getV($$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer overlayCoords(int $$0, int $$1) {
/* 32 */     return this.delegate.overlayCoords($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer uv2(int $$0, int $$1) {
/* 37 */     return this.delegate.uv2($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer normal(float $$0, float $$1, float $$2) {
/* 42 */     return this.delegate.normal($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void endVertex() {
/* 47 */     this.delegate.endVertex();
/*    */   }
/*    */ 
/*    */   
/*    */   public void defaultColor(int $$0, int $$1, int $$2, int $$3) {
/* 52 */     this.delegate.defaultColor($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unsetDefaultColor() {
/* 57 */     this.delegate.unsetDefaultColor();
/*    */   }
/*    */ 
/*    */   
/*    */   public void vertex(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, int $$9, int $$10, float $$11, float $$12, float $$13) {
/* 62 */     this.delegate.vertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, this.sprite.getU($$7), this.sprite.getV($$8), $$9, $$10, $$11, $$12, $$13);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\SpriteCoordinateExpander.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */