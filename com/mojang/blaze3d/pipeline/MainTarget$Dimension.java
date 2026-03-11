/*     */ package com.mojang.blaze3d.pipeline;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ class Dimension
/*     */ {
/*     */   public final int width;
/*     */   public final int height;
/*     */   
/*     */   Dimension(int $$0, int $$1) {
/* 127 */     this.width = $$0;
/* 128 */     this.height = $$1;
/*     */   }
/*     */   
/*     */   static List<Dimension> listWithFallback(int $$0, int $$1) {
/* 132 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 134 */     int $$2 = RenderSystem.maxSupportedTextureSize();
/* 135 */     if ($$0 <= 0 || $$0 > $$2 || $$1 <= 0 || $$1 > $$2) {
/* 136 */       return (List<Dimension>)ImmutableList.of(MainTarget.DEFAULT_DIMENSIONS);
/*     */     }
/* 138 */     return (List<Dimension>)ImmutableList.of(new Dimension($$0, $$1), MainTarget.DEFAULT_DIMENSIONS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 147 */     if (this == $$0) {
/* 148 */       return true;
/*     */     }
/* 150 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 151 */       return false;
/*     */     }
/* 153 */     Dimension $$1 = (Dimension)$$0;
/* 154 */     return (this.width == $$1.width && this.height == $$1.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 159 */     return Objects.hash(new Object[] { Integer.valueOf(this.width), Integer.valueOf(this.height) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 164 */     return "" + this.width + "x" + this.width;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\pipeline\MainTarget$Dimension.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */