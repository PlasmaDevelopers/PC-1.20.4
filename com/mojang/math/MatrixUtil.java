/*     */ package com.mojang.math;
/*     */ import org.apache.commons.lang3.tuple.Triple;
/*     */ import org.joml.Math;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix3fc;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class MatrixUtil {
/*  12 */   private static final float G = 3.0F + 2.0F * Math.sqrt(2.0F);
/*     */   
/*  14 */   private static final GivensParameters PI_4 = GivensParameters.fromPositiveAngle(0.7853982F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix4f mulComponentWise(Matrix4f $$0, float $$1) {
/*  20 */     return $$0.set($$0
/*  21 */         .m00() * $$1, $$0.m01() * $$1, $$0.m02() * $$1, $$0.m03() * $$1, $$0
/*  22 */         .m10() * $$1, $$0.m11() * $$1, $$0.m12() * $$1, $$0.m13() * $$1, $$0
/*  23 */         .m20() * $$1, $$0.m21() * $$1, $$0.m22() * $$1, $$0.m23() * $$1, $$0
/*  24 */         .m30() * $$1, $$0.m31() * $$1, $$0.m32() * $$1, $$0.m33() * $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static GivensParameters approxGivensQuat(float $$0, float $$1, float $$2) {
/*  30 */     float $$3 = 2.0F * ($$0 - $$2);
/*  31 */     float $$4 = $$1;
/*     */ 
/*     */     
/*  34 */     if (G * $$4 * $$4 < $$3 * $$3) {
/*  35 */       return GivensParameters.fromUnnormalized($$4, $$3);
/*     */     }
/*  37 */     return PI_4;
/*     */   }
/*     */ 
/*     */   
/*     */   private static GivensParameters qrGivensQuat(float $$0, float $$1) {
/*  42 */     float $$2 = (float)Math.hypot($$0, $$1);
/*  43 */     float $$3 = ($$2 > 1.0E-6F) ? $$1 : 0.0F;
/*  44 */     float $$4 = Math.abs($$0) + Math.max($$2, 1.0E-6F);
/*  45 */     if ($$0 < 0.0F) {
/*  46 */       float $$5 = $$3;
/*  47 */       $$3 = $$4;
/*  48 */       $$4 = $$5;
/*     */     } 
/*  50 */     return GivensParameters.fromUnnormalized($$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void similarityTransform(Matrix3f $$0, Matrix3f $$1) {
/*  55 */     $$0.mul((Matrix3fc)$$1);
/*     */     
/*  57 */     $$1.transpose();
/*     */     
/*  59 */     $$1.mul((Matrix3fc)$$0);
/*     */     
/*  61 */     $$0.set((Matrix3fc)$$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void stepJacobi(Matrix3f $$0, Matrix3f $$1, Quaternionf $$2, Quaternionf $$3) {
/*  66 */     if ($$0.m01 * $$0.m01 + $$0.m10 * $$0.m10 > 1.0E-6F) {
/*  67 */       GivensParameters $$4 = approxGivensQuat($$0.m00, 0.5F * ($$0.m01 + $$0.m10), $$0.m11);
/*     */       
/*  69 */       Quaternionf $$5 = $$4.aroundZ($$2);
/*  70 */       $$3.mul((Quaternionfc)$$5);
/*     */       
/*  72 */       $$4.aroundZ($$1);
/*  73 */       similarityTransform($$0, $$1);
/*     */     } 
/*     */     
/*  76 */     if ($$0.m02 * $$0.m02 + $$0.m20 * $$0.m20 > 1.0E-6F) {
/*     */       
/*  78 */       GivensParameters $$6 = approxGivensQuat($$0.m00, 0.5F * ($$0.m02 + $$0.m20), $$0.m22).inverse();
/*     */       
/*  80 */       Quaternionf $$7 = $$6.aroundY($$2);
/*  81 */       $$3.mul((Quaternionfc)$$7);
/*     */       
/*  83 */       $$6.aroundY($$1);
/*  84 */       similarityTransform($$0, $$1);
/*     */     } 
/*     */     
/*  87 */     if ($$0.m12 * $$0.m12 + $$0.m21 * $$0.m21 > 1.0E-6F) {
/*  88 */       GivensParameters $$8 = approxGivensQuat($$0.m11, 0.5F * ($$0.m12 + $$0.m21), $$0.m22);
/*     */       
/*  90 */       Quaternionf $$9 = $$8.aroundX($$2);
/*  91 */       $$3.mul((Quaternionfc)$$9);
/*     */       
/*  93 */       $$8.aroundX($$1);
/*  94 */       similarityTransform($$0, $$1);
/*     */     } 
/*     */   }
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
/*     */   public static Quaternionf eigenvalueJacobi(Matrix3f $$0, int $$1) {
/* 109 */     Quaternionf $$2 = new Quaternionf();
/*     */     
/* 111 */     Matrix3f $$3 = new Matrix3f();
/* 112 */     Quaternionf $$4 = new Quaternionf();
/*     */     
/* 114 */     for (int $$5 = 0; $$5 < $$1; $$5++) {
/* 115 */       stepJacobi($$0, $$3, $$4, $$2);
/*     */     }
/*     */     
/* 118 */     $$2.normalize();
/* 119 */     return $$2;
/*     */   }
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
/*     */   public static Triple<Quaternionf, Vector3f, Quaternionf> svdDecompose(Matrix3f $$0) {
/* 132 */     Matrix3f $$1 = new Matrix3f((Matrix3fc)$$0);
/* 133 */     $$1.transpose();
/* 134 */     $$1.mul((Matrix3fc)$$0);
/*     */     
/* 136 */     Quaternionf $$2 = eigenvalueJacobi($$1, 5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     float $$3 = $$1.m00;
/* 143 */     float $$4 = $$1.m11;
/*     */ 
/*     */ 
/*     */     
/* 147 */     boolean $$5 = ($$3 < 1.0E-6D);
/* 148 */     boolean $$6 = ($$4 < 1.0E-6D);
/*     */     
/* 150 */     Matrix3f $$7 = $$1;
/*     */ 
/*     */ 
/*     */     
/* 154 */     Matrix3f $$8 = $$0.rotate((Quaternionfc)$$2);
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
/* 184 */     Quaternionf $$9 = new Quaternionf();
/*     */     
/* 186 */     Quaternionf $$10 = new Quaternionf();
/*     */ 
/*     */     
/* 189 */     if ($$5) {
/* 190 */       GivensParameters $$11 = qrGivensQuat($$8.m11, -$$8.m10);
/*     */     } else {
/* 192 */       $$12 = qrGivensQuat($$8.m00, $$8.m01);
/*     */     } 
/*     */     
/* 195 */     Quaternionf $$13 = $$12.aroundZ($$10);
/* 196 */     Matrix3f $$14 = $$12.aroundZ($$7);
/*     */ 
/*     */ 
/*     */     
/* 200 */     $$9.mul((Quaternionfc)$$13);
/* 201 */     $$14.transpose().mul((Matrix3fc)$$8);
/*     */     
/* 203 */     $$7 = $$8;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     if ($$5) {
/* 209 */       $$12 = qrGivensQuat($$14.m22, -$$14.m20);
/*     */     } else {
/* 211 */       $$12 = qrGivensQuat($$14.m00, $$14.m02);
/*     */     } 
/*     */     
/* 214 */     GivensParameters $$12 = $$12.inverse();
/*     */     
/* 216 */     Quaternionf $$15 = $$12.aroundY($$10);
/*     */     
/* 218 */     Matrix3f $$16 = $$12.aroundY($$7);
/*     */ 
/*     */     
/* 221 */     $$9.mul((Quaternionfc)$$15);
/* 222 */     $$16.transpose().mul((Matrix3fc)$$14);
/*     */     
/* 224 */     $$7 = $$14;
/*     */ 
/*     */     
/* 227 */     if ($$6) {
/* 228 */       $$12 = qrGivensQuat($$16.m22, -$$16.m21);
/*     */     } else {
/* 230 */       $$12 = qrGivensQuat($$16.m11, $$16.m12);
/*     */     } 
/*     */     
/* 233 */     Quaternionf $$17 = $$12.aroundX($$10);
/*     */     
/* 235 */     Matrix3f $$18 = $$12.aroundX($$7);
/*     */ 
/*     */     
/* 238 */     $$9.mul((Quaternionfc)$$17);
/* 239 */     $$18.transpose().mul((Matrix3fc)$$16);
/*     */ 
/*     */     
/* 242 */     Vector3f $$19 = new Vector3f($$18.m00, $$18.m11, $$18.m22);
/*     */ 
/*     */     
/* 245 */     return Triple.of($$9, $$19, $$2.conjugate());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\math\MatrixUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */