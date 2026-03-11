/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.blaze3d.vertex.VertexMultiConsumer;
/*     */ import java.util.Optional;
/*     */ 
/*     */ public class OutlineBufferSource
/*     */   implements MultiBufferSource {
/*     */   private final MultiBufferSource.BufferSource bufferSource;
/*  12 */   private final MultiBufferSource.BufferSource outlineBufferSource = MultiBufferSource.immediate(new BufferBuilder(1536));
/*     */   
/*  14 */   private int teamR = 255;
/*  15 */   private int teamG = 255;
/*  16 */   private int teamB = 255;
/*  17 */   private int teamA = 255;
/*     */   
/*     */   public OutlineBufferSource(MultiBufferSource.BufferSource $$0) {
/*  20 */     this.bufferSource = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer getBuffer(RenderType $$0) {
/*  25 */     if ($$0.isOutline()) {
/*  26 */       VertexConsumer $$1 = this.outlineBufferSource.getBuffer($$0);
/*  27 */       return (VertexConsumer)new EntityOutlineGenerator($$1, this.teamR, this.teamG, this.teamB, this.teamA);
/*     */     } 
/*  29 */     VertexConsumer $$2 = this.bufferSource.getBuffer($$0);
/*  30 */     Optional<RenderType> $$3 = $$0.outline();
/*  31 */     if ($$3.isPresent()) {
/*  32 */       VertexConsumer $$4 = this.outlineBufferSource.getBuffer($$3.get());
/*  33 */       EntityOutlineGenerator $$5 = new EntityOutlineGenerator($$4, this.teamR, this.teamG, this.teamB, this.teamA);
/*  34 */       return VertexMultiConsumer.create((VertexConsumer)$$5, $$2);
/*     */     } 
/*  36 */     return $$2;
/*     */   }
/*     */   
/*     */   public void setColor(int $$0, int $$1, int $$2, int $$3) {
/*  40 */     this.teamR = $$0;
/*  41 */     this.teamG = $$1;
/*  42 */     this.teamB = $$2;
/*  43 */     this.teamA = $$3;
/*     */   }
/*     */   
/*     */   public void endOutlineBatch() {
/*  47 */     this.outlineBufferSource.endBatch();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EntityOutlineGenerator
/*     */     extends DefaultedVertexConsumer
/*     */   {
/*     */     private final VertexConsumer delegate;
/*     */     private double x;
/*     */     private double y;
/*     */     private double z;
/*     */     private float u;
/*     */     private float v;
/*     */     
/*     */     EntityOutlineGenerator(VertexConsumer $$0, int $$1, int $$2, int $$3, int $$4) {
/*  62 */       this.delegate = $$0;
/*  63 */       super.defaultColor($$1, $$2, $$3, $$4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void defaultColor(int $$0, int $$1, int $$2, int $$3) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void unsetDefaultColor() {}
/*     */ 
/*     */     
/*     */     public VertexConsumer vertex(double $$0, double $$1, double $$2) {
/*  76 */       this.x = $$0;
/*  77 */       this.y = $$1;
/*  78 */       this.z = $$2;
/*  79 */       return (VertexConsumer)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer color(int $$0, int $$1, int $$2, int $$3) {
/*  84 */       return (VertexConsumer)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer uv(float $$0, float $$1) {
/*  89 */       this.u = $$0;
/*  90 */       this.v = $$1;
/*  91 */       return (VertexConsumer)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer overlayCoords(int $$0, int $$1) {
/*  96 */       return (VertexConsumer)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer uv2(int $$0, int $$1) {
/* 101 */       return (VertexConsumer)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer normal(float $$0, float $$1, float $$2) {
/* 106 */       return (VertexConsumer)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void vertex(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, int $$9, int $$10, float $$11, float $$12, float $$13) {
/* 111 */       this.delegate.vertex($$0, $$1, $$2).color(this.defaultR, this.defaultG, this.defaultB, this.defaultA).uv($$7, $$8).endVertex();
/*     */     }
/*     */ 
/*     */     
/*     */     public void endVertex() {
/* 116 */       this.delegate.vertex(this.x, this.y, this.z).color(this.defaultR, this.defaultG, this.defaultB, this.defaultA).uv(this.u, this.v).endVertex();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\OutlineBufferSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */