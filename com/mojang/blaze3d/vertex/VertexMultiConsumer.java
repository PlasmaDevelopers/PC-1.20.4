/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ public class VertexMultiConsumer {
/*     */   public static VertexConsumer create() {
/*   7 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public static VertexConsumer create(VertexConsumer $$0) {
/*  11 */     return $$0;
/*     */   }
/*     */   
/*     */   public static VertexConsumer create(VertexConsumer $$0, VertexConsumer $$1) {
/*  15 */     return new Double($$0, $$1);
/*     */   }
/*     */   
/*     */   public static VertexConsumer create(VertexConsumer... $$0) {
/*  19 */     return new Multiple($$0);
/*     */   }
/*     */   
/*     */   private static class Double
/*     */     implements VertexConsumer {
/*     */     private final VertexConsumer first;
/*     */     private final VertexConsumer second;
/*     */     
/*     */     public Double(VertexConsumer $$0, VertexConsumer $$1) {
/*  28 */       if ($$0 == $$1) {
/*  29 */         throw new IllegalArgumentException("Duplicate delegates");
/*     */       }
/*  31 */       this.first = $$0;
/*  32 */       this.second = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer vertex(double $$0, double $$1, double $$2) {
/*  37 */       this.first.vertex($$0, $$1, $$2);
/*  38 */       this.second.vertex($$0, $$1, $$2);
/*  39 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer color(int $$0, int $$1, int $$2, int $$3) {
/*  44 */       this.first.color($$0, $$1, $$2, $$3);
/*  45 */       this.second.color($$0, $$1, $$2, $$3);
/*  46 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer uv(float $$0, float $$1) {
/*  51 */       this.first.uv($$0, $$1);
/*  52 */       this.second.uv($$0, $$1);
/*  53 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer overlayCoords(int $$0, int $$1) {
/*  58 */       this.first.overlayCoords($$0, $$1);
/*  59 */       this.second.overlayCoords($$0, $$1);
/*  60 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer uv2(int $$0, int $$1) {
/*  65 */       this.first.uv2($$0, $$1);
/*  66 */       this.second.uv2($$0, $$1);
/*  67 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer normal(float $$0, float $$1, float $$2) {
/*  72 */       this.first.normal($$0, $$1, $$2);
/*  73 */       this.second.normal($$0, $$1, $$2);
/*  74 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void vertex(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, int $$9, int $$10, float $$11, float $$12, float $$13) {
/*  79 */       this.first.vertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12, $$13);
/*  80 */       this.second.vertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12, $$13);
/*     */     }
/*     */ 
/*     */     
/*     */     public void endVertex() {
/*  85 */       this.first.endVertex();
/*  86 */       this.second.endVertex();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultColor(int $$0, int $$1, int $$2, int $$3) {
/*  91 */       this.first.defaultColor($$0, $$1, $$2, $$3);
/*  92 */       this.second.defaultColor($$0, $$1, $$2, $$3);
/*     */     }
/*     */ 
/*     */     
/*     */     public void unsetDefaultColor() {
/*  97 */       this.first.unsetDefaultColor();
/*  98 */       this.second.unsetDefaultColor();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Multiple implements VertexConsumer {
/*     */     private final VertexConsumer[] delegates;
/*     */     
/*     */     public Multiple(VertexConsumer[] $$0) {
/* 106 */       for (int $$1 = 0; $$1 < $$0.length; $$1++) {
/* 107 */         for (int $$2 = $$1 + 1; $$2 < $$0.length; $$2++) {
/* 108 */           if ($$0[$$1] == $$0[$$2]) {
/* 109 */             throw new IllegalArgumentException("Duplicate delegates");
/*     */           }
/*     */         } 
/*     */       } 
/* 113 */       this.delegates = $$0;
/*     */     }
/*     */     
/*     */     private void forEach(Consumer<VertexConsumer> $$0) {
/* 117 */       for (VertexConsumer $$1 : this.delegates) {
/* 118 */         $$0.accept($$1);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer vertex(double $$0, double $$1, double $$2) {
/* 124 */       forEach($$3 -> $$3.vertex($$0, $$1, $$2));
/* 125 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer color(int $$0, int $$1, int $$2, int $$3) {
/* 130 */       forEach($$4 -> $$4.color($$0, $$1, $$2, $$3));
/* 131 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer uv(float $$0, float $$1) {
/* 136 */       forEach($$2 -> $$2.uv($$0, $$1));
/* 137 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer overlayCoords(int $$0, int $$1) {
/* 142 */       forEach($$2 -> $$2.overlayCoords($$0, $$1));
/* 143 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer uv2(int $$0, int $$1) {
/* 148 */       forEach($$2 -> $$2.uv2($$0, $$1));
/* 149 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexConsumer normal(float $$0, float $$1, float $$2) {
/* 154 */       forEach($$3 -> $$3.normal($$0, $$1, $$2));
/* 155 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void vertex(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, int $$9, int $$10, float $$11, float $$12, float $$13) {
/* 160 */       forEach($$14 -> $$14.vertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12, $$13));
/*     */     }
/*     */ 
/*     */     
/*     */     public void endVertex() {
/* 165 */       forEach(VertexConsumer::endVertex);
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultColor(int $$0, int $$1, int $$2, int $$3) {
/* 170 */       forEach($$4 -> $$4.defaultColor($$0, $$1, $$2, $$3));
/*     */     }
/*     */ 
/*     */     
/*     */     public void unsetDefaultColor() {
/* 175 */       forEach(VertexConsumer::unsetDefaultColor);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\VertexMultiConsumer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */