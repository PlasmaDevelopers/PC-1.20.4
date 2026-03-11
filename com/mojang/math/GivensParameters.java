/*     */ package com.mojang.math;
/*     */ public final class GivensParameters extends Record {
/*     */   private final float sinHalf;
/*     */   private final float cosHalf;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lcom/mojang/math/GivensParameters;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #14	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/mojang/math/GivensParameters;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/mojang/math/GivensParameters;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #14	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/mojang/math/GivensParameters;
/*     */   }
/*     */   
/*  14 */   public GivensParameters(float $$0, float $$1) { this.sinHalf = $$0; this.cosHalf = $$1; } public float sinHalf() { return this.sinHalf; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/mojang/math/GivensParameters;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #14	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/mojang/math/GivensParameters;
/*  14 */     //   0	8	1	$$0	Ljava/lang/Object; } public float cosHalf() { return this.cosHalf; }
/*     */    public static GivensParameters fromUnnormalized(float $$0, float $$1) {
/*  16 */     float $$2 = Math.invsqrt($$0 * $$0 + $$1 * $$1);
/*  17 */     return new GivensParameters($$2 * $$0, $$2 * $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GivensParameters fromPositiveAngle(float $$0) {
/*  26 */     float $$1 = Math.sin($$0 / 2.0F);
/*  27 */     float $$2 = Math.cosFromSin($$1, $$0 / 2.0F);
/*  28 */     return new GivensParameters($$1, $$2);
/*     */   }
/*     */   
/*     */   public GivensParameters inverse() {
/*  32 */     return new GivensParameters(-this.sinHalf, this.cosHalf);
/*     */   }
/*     */   
/*     */   public Quaternionf aroundX(Quaternionf $$0) {
/*  36 */     return $$0.set(this.sinHalf, 0.0F, 0.0F, this.cosHalf);
/*     */   }
/*     */   
/*     */   public Quaternionf aroundY(Quaternionf $$0) {
/*  40 */     return $$0.set(0.0F, this.sinHalf, 0.0F, this.cosHalf);
/*     */   }
/*     */   
/*     */   public Quaternionf aroundZ(Quaternionf $$0) {
/*  44 */     return $$0.set(0.0F, 0.0F, this.sinHalf, this.cosHalf);
/*     */   }
/*     */ 
/*     */   
/*     */   public float cos() {
/*  49 */     return this.cosHalf * this.cosHalf - this.sinHalf * this.sinHalf;
/*     */   }
/*     */ 
/*     */   
/*     */   public float sin() {
/*  54 */     return 2.0F * this.sinHalf * this.cosHalf;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix3f aroundX(Matrix3f $$0) {
/*  59 */     $$0.m01 = 0.0F;
/*  60 */     $$0.m02 = 0.0F;
/*  61 */     $$0.m10 = 0.0F;
/*  62 */     $$0.m20 = 0.0F;
/*     */     
/*  64 */     float $$1 = cos();
/*  65 */     float $$2 = sin();
/*     */     
/*  67 */     $$0.m11 = $$1;
/*  68 */     $$0.m22 = $$1;
/*     */     
/*  70 */     $$0.m12 = $$2;
/*  71 */     $$0.m21 = -$$2;
/*     */     
/*  73 */     $$0.m00 = 1.0F;
/*  74 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix3f aroundY(Matrix3f $$0) {
/*  79 */     $$0.m01 = 0.0F;
/*  80 */     $$0.m10 = 0.0F;
/*  81 */     $$0.m12 = 0.0F;
/*  82 */     $$0.m21 = 0.0F;
/*     */     
/*  84 */     float $$1 = cos();
/*  85 */     float $$2 = sin();
/*     */     
/*  87 */     $$0.m00 = $$1;
/*  88 */     $$0.m22 = $$1;
/*     */     
/*  90 */     $$0.m02 = -$$2;
/*  91 */     $$0.m20 = $$2;
/*     */     
/*  93 */     $$0.m11 = 1.0F;
/*  94 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix3f aroundZ(Matrix3f $$0) {
/*  99 */     $$0.m02 = 0.0F;
/* 100 */     $$0.m12 = 0.0F;
/* 101 */     $$0.m20 = 0.0F;
/* 102 */     $$0.m21 = 0.0F;
/*     */     
/* 104 */     float $$1 = cos();
/* 105 */     float $$2 = sin();
/*     */     
/* 107 */     $$0.m00 = $$1;
/* 108 */     $$0.m11 = $$1;
/*     */     
/* 110 */     $$0.m01 = $$2;
/* 111 */     $$0.m10 = -$$2;
/*     */     
/* 113 */     $$0.m22 = 1.0F;
/* 114 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\math\GivensParameters.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */