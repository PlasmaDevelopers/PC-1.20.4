/*    */ package net.minecraft.client.gui.font.glyphs;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.font.GlyphRenderTypes;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public class BakedGlyph {
/*    */   private final GlyphRenderTypes renderTypes;
/*    */   private final float u0;
/*    */   private final float u1;
/*    */   private final float v0;
/*    */   private final float v1;
/*    */   private final float left;
/*    */   private final float right;
/*    */   private final float up;
/*    */   private final float down;
/*    */   
/*    */   public BakedGlyph(GlyphRenderTypes $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8) {
/* 21 */     this.renderTypes = $$0;
/* 22 */     this.u0 = $$1;
/* 23 */     this.u1 = $$2;
/* 24 */     this.v0 = $$3;
/* 25 */     this.v1 = $$4;
/*    */     
/* 27 */     this.left = $$5;
/* 28 */     this.right = $$6;
/* 29 */     this.up = $$7;
/* 30 */     this.down = $$8;
/*    */   }
/*    */   
/*    */   public void render(boolean $$0, float $$1, float $$2, Matrix4f $$3, VertexConsumer $$4, float $$5, float $$6, float $$7, float $$8, int $$9) {
/* 34 */     int $$10 = 3;
/*    */     
/* 36 */     float $$11 = $$1 + this.left;
/* 37 */     float $$12 = $$1 + this.right;
/*    */     
/* 39 */     float $$13 = this.up - 3.0F;
/* 40 */     float $$14 = this.down - 3.0F;
/*    */     
/* 42 */     float $$15 = $$2 + $$13;
/* 43 */     float $$16 = $$2 + $$14;
/*    */     
/* 45 */     float $$17 = $$0 ? (1.0F - 0.25F * $$13) : 0.0F;
/* 46 */     float $$18 = $$0 ? (1.0F - 0.25F * $$14) : 0.0F;
/*    */     
/* 48 */     $$4.vertex($$3, $$11 + $$17, $$15, 0.0F).color($$5, $$6, $$7, $$8).uv(this.u0, this.v0).uv2($$9).endVertex();
/* 49 */     $$4.vertex($$3, $$11 + $$18, $$16, 0.0F).color($$5, $$6, $$7, $$8).uv(this.u0, this.v1).uv2($$9).endVertex();
/* 50 */     $$4.vertex($$3, $$12 + $$18, $$16, 0.0F).color($$5, $$6, $$7, $$8).uv(this.u1, this.v1).uv2($$9).endVertex();
/* 51 */     $$4.vertex($$3, $$12 + $$17, $$15, 0.0F).color($$5, $$6, $$7, $$8).uv(this.u1, this.v0).uv2($$9).endVertex();
/*    */   }
/*    */   
/*    */   public void renderEffect(Effect $$0, Matrix4f $$1, VertexConsumer $$2, int $$3) {
/* 55 */     $$2.vertex($$1, $$0.x0, $$0.y0, $$0.depth).color($$0.r, $$0.g, $$0.b, $$0.a).uv(this.u0, this.v0).uv2($$3).endVertex();
/* 56 */     $$2.vertex($$1, $$0.x1, $$0.y0, $$0.depth).color($$0.r, $$0.g, $$0.b, $$0.a).uv(this.u0, this.v1).uv2($$3).endVertex();
/* 57 */     $$2.vertex($$1, $$0.x1, $$0.y1, $$0.depth).color($$0.r, $$0.g, $$0.b, $$0.a).uv(this.u1, this.v1).uv2($$3).endVertex();
/* 58 */     $$2.vertex($$1, $$0.x0, $$0.y1, $$0.depth).color($$0.r, $$0.g, $$0.b, $$0.a).uv(this.u1, this.v0).uv2($$3).endVertex();
/*    */   }
/*    */   
/*    */   public RenderType renderType(Font.DisplayMode $$0) {
/* 62 */     return this.renderTypes.select($$0);
/*    */   }
/*    */   
/*    */   public static class Effect {
/*    */     protected final float x0;
/*    */     protected final float y0;
/*    */     protected final float x1;
/*    */     protected final float y1;
/*    */     protected final float depth;
/*    */     protected final float r;
/*    */     protected final float g;
/*    */     protected final float b;
/*    */     protected final float a;
/*    */     
/*    */     public Effect(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8) {
/* 77 */       this.x0 = $$0;
/* 78 */       this.y0 = $$1;
/* 79 */       this.x1 = $$2;
/* 80 */       this.y1 = $$3;
/* 81 */       this.depth = $$4;
/* 82 */       this.r = $$5;
/* 83 */       this.g = $$6;
/* 84 */       this.b = $$7;
/* 85 */       this.a = $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\glyphs\BakedGlyph.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */