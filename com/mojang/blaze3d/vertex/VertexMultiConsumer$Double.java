/*    */ package com.mojang.blaze3d.vertex;
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
/*    */ class Double
/*    */   implements VertexConsumer
/*    */ {
/*    */   private final VertexConsumer first;
/*    */   private final VertexConsumer second;
/*    */   
/*    */   public Double(VertexConsumer $$0, VertexConsumer $$1) {
/* 28 */     if ($$0 == $$1) {
/* 29 */       throw new IllegalArgumentException("Duplicate delegates");
/*    */     }
/* 31 */     this.first = $$0;
/* 32 */     this.second = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer vertex(double $$0, double $$1, double $$2) {
/* 37 */     this.first.vertex($$0, $$1, $$2);
/* 38 */     this.second.vertex($$0, $$1, $$2);
/* 39 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer color(int $$0, int $$1, int $$2, int $$3) {
/* 44 */     this.first.color($$0, $$1, $$2, $$3);
/* 45 */     this.second.color($$0, $$1, $$2, $$3);
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer uv(float $$0, float $$1) {
/* 51 */     this.first.uv($$0, $$1);
/* 52 */     this.second.uv($$0, $$1);
/* 53 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer overlayCoords(int $$0, int $$1) {
/* 58 */     this.first.overlayCoords($$0, $$1);
/* 59 */     this.second.overlayCoords($$0, $$1);
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer uv2(int $$0, int $$1) {
/* 65 */     this.first.uv2($$0, $$1);
/* 66 */     this.second.uv2($$0, $$1);
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexConsumer normal(float $$0, float $$1, float $$2) {
/* 72 */     this.first.normal($$0, $$1, $$2);
/* 73 */     this.second.normal($$0, $$1, $$2);
/* 74 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void vertex(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, int $$9, int $$10, float $$11, float $$12, float $$13) {
/* 79 */     this.first.vertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12, $$13);
/* 80 */     this.second.vertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12, $$13);
/*    */   }
/*    */ 
/*    */   
/*    */   public void endVertex() {
/* 85 */     this.first.endVertex();
/* 86 */     this.second.endVertex();
/*    */   }
/*    */ 
/*    */   
/*    */   public void defaultColor(int $$0, int $$1, int $$2, int $$3) {
/* 91 */     this.first.defaultColor($$0, $$1, $$2, $$3);
/* 92 */     this.second.defaultColor($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unsetDefaultColor() {
/* 97 */     this.first.unsetDefaultColor();
/* 98 */     this.second.unsetDefaultColor();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\VertexMultiConsumer$Double.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */