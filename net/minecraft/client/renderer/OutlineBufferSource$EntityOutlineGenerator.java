/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EntityOutlineGenerator
/*     */   extends DefaultedVertexConsumer
/*     */ {
/*     */   private final VertexConsumer delegate;
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   private float u;
/*     */   private float v;
/*     */   
/*     */   EntityOutlineGenerator(VertexConsumer $$0, int $$1, int $$2, int $$3, int $$4) {
/*  62 */     this.delegate = $$0;
/*  63 */     super.defaultColor($$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void defaultColor(int $$0, int $$1, int $$2, int $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetDefaultColor() {}
/*     */ 
/*     */   
/*     */   public VertexConsumer vertex(double $$0, double $$1, double $$2) {
/*  76 */     this.x = $$0;
/*  77 */     this.y = $$1;
/*  78 */     this.z = $$2;
/*  79 */     return (VertexConsumer)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer color(int $$0, int $$1, int $$2, int $$3) {
/*  84 */     return (VertexConsumer)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer uv(float $$0, float $$1) {
/*  89 */     this.u = $$0;
/*  90 */     this.v = $$1;
/*  91 */     return (VertexConsumer)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer overlayCoords(int $$0, int $$1) {
/*  96 */     return (VertexConsumer)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer uv2(int $$0, int $$1) {
/* 101 */     return (VertexConsumer)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer normal(float $$0, float $$1, float $$2) {
/* 106 */     return (VertexConsumer)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void vertex(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, int $$9, int $$10, float $$11, float $$12, float $$13) {
/* 111 */     this.delegate.vertex($$0, $$1, $$2).color(this.defaultR, this.defaultG, this.defaultB, this.defaultA).uv($$7, $$8).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endVertex() {
/* 116 */     this.delegate.vertex(this.x, this.y, this.z).color(this.defaultR, this.defaultG, this.defaultB, this.defaultA).uv(this.u, this.v).endVertex();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\OutlineBufferSource$EntityOutlineGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */