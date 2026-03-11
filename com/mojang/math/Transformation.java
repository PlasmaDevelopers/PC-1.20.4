/*     */ package com.mojang.math;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import org.apache.commons.lang3.tuple.Triple;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Transformation
/*     */ {
/*     */   private final Matrix4f matrix;
/*     */   public static final Codec<Transformation> CODEC;
/*     */   
/*     */   static {
/*  32 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.VECTOR3F.fieldOf("translation").forGetter(()), (App)ExtraCodecs.QUATERNIONF.fieldOf("left_rotation").forGetter(()), (App)ExtraCodecs.VECTOR3F.fieldOf("scale").forGetter(()), (App)ExtraCodecs.QUATERNIONF.fieldOf("right_rotation").forGetter(())).apply((Applicative)$$0, Transformation::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public static final Codec<Transformation> EXTENDED_CODEC = ExtraCodecs.withAlternative(CODEC, ExtraCodecs.MATRIX4F
/*     */       
/*  42 */       .xmap(Transformation::new, Transformation::getMatrix));
/*     */   
/*     */   private boolean decomposed;
/*     */   @Nullable
/*     */   private Vector3f translation;
/*     */   @Nullable
/*     */   private Quaternionf leftRotation;
/*     */   @Nullable
/*     */   private Vector3f scale;
/*     */   @Nullable
/*     */   private Quaternionf rightRotation;
/*     */   private static final Transformation IDENTITY;
/*     */   
/*     */   public Transformation(@Nullable Matrix4f $$0) {
/*  56 */     if ($$0 == null) {
/*  57 */       this.matrix = new Matrix4f();
/*     */     } else {
/*  59 */       this.matrix = $$0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Transformation(@Nullable Vector3f $$0, @Nullable Quaternionf $$1, @Nullable Vector3f $$2, @Nullable Quaternionf $$3) {
/*  64 */     this.matrix = compose($$0, $$1, $$2, $$3);
/*  65 */     this.translation = ($$0 != null) ? $$0 : new Vector3f();
/*  66 */     this.leftRotation = ($$1 != null) ? $$1 : new Quaternionf();
/*  67 */     this.scale = ($$2 != null) ? $$2 : new Vector3f(1.0F, 1.0F, 1.0F);
/*  68 */     this.rightRotation = ($$3 != null) ? $$3 : new Quaternionf();
/*  69 */     this.decomposed = true;
/*     */   }
/*     */   static {
/*  72 */     IDENTITY = (Transformation)Util.make(() -> {
/*     */           Transformation $$0 = new Transformation(new Matrix4f());
/*     */           $$0.translation = new Vector3f();
/*     */           $$0.leftRotation = new Quaternionf();
/*     */           $$0.scale = new Vector3f(1.0F, 1.0F, 1.0F);
/*     */           $$0.rightRotation = new Quaternionf();
/*     */           $$0.decomposed = true;
/*     */           return $$0;
/*     */         });
/*     */   }
/*     */   public static Transformation identity() {
/*  83 */     return IDENTITY;
/*     */   }
/*     */   
/*     */   public Transformation compose(Transformation $$0) {
/*  87 */     Matrix4f $$1 = getMatrix();
/*  88 */     $$1.mul((Matrix4fc)$$0.getMatrix());
/*  89 */     return new Transformation($$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Transformation inverse() {
/*  94 */     if (this == IDENTITY) {
/*  95 */       return this;
/*     */     }
/*  97 */     Matrix4f $$0 = getMatrix().invert();
/*  98 */     if ($$0.isFinite()) {
/*  99 */       return new Transformation($$0);
/*     */     }
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   private void ensureDecomposed() {
/* 105 */     if (!this.decomposed) {
/* 106 */       float $$0 = 1.0F / this.matrix.m33();
/* 107 */       Triple<Quaternionf, Vector3f, Quaternionf> $$1 = MatrixUtil.svdDecompose((new Matrix3f((Matrix4fc)this.matrix)).scale($$0));
/* 108 */       this.translation = this.matrix.getTranslation(new Vector3f()).mul($$0);
/* 109 */       this.leftRotation = new Quaternionf((Quaternionfc)$$1.getLeft());
/* 110 */       this.scale = new Vector3f((Vector3fc)$$1.getMiddle());
/* 111 */       this.rightRotation = new Quaternionf((Quaternionfc)$$1.getRight());
/* 112 */       this.decomposed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Matrix4f compose(@Nullable Vector3f $$0, @Nullable Quaternionf $$1, @Nullable Vector3f $$2, @Nullable Quaternionf $$3) {
/* 117 */     Matrix4f $$4 = new Matrix4f();
/* 118 */     if ($$0 != null) {
/* 119 */       $$4.translation((Vector3fc)$$0);
/*     */     }
/* 121 */     if ($$1 != null) {
/* 122 */       $$4.rotate((Quaternionfc)$$1);
/*     */     }
/* 124 */     if ($$2 != null) {
/* 125 */       $$4.scale((Vector3fc)$$2);
/*     */     }
/* 127 */     if ($$3 != null) {
/* 128 */       $$4.rotate((Quaternionfc)$$3);
/*     */     }
/* 130 */     return $$4;
/*     */   }
/*     */   
/*     */   public Matrix4f getMatrix() {
/* 134 */     return new Matrix4f((Matrix4fc)this.matrix);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3f getTranslation() {
/* 139 */     ensureDecomposed();
/* 140 */     return new Vector3f((Vector3fc)this.translation);
/*     */   }
/*     */ 
/*     */   
/*     */   public Quaternionf getLeftRotation() {
/* 145 */     ensureDecomposed();
/* 146 */     return new Quaternionf((Quaternionfc)this.leftRotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3f getScale() {
/* 151 */     ensureDecomposed();
/* 152 */     return new Vector3f((Vector3fc)this.scale);
/*     */   }
/*     */ 
/*     */   
/*     */   public Quaternionf getRightRotation() {
/* 157 */     ensureDecomposed();
/* 158 */     return new Quaternionf((Quaternionfc)this.rightRotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 163 */     if (this == $$0) {
/* 164 */       return true;
/*     */     }
/* 166 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 167 */       return false;
/*     */     }
/* 169 */     Transformation $$1 = (Transformation)$$0;
/* 170 */     return Objects.equals(this.matrix, $$1.matrix);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 175 */     return Objects.hash(new Object[] { this.matrix });
/*     */   }
/*     */   
/*     */   public Transformation slerp(Transformation $$0, float $$1) {
/* 179 */     Vector3f $$2 = getTranslation();
/* 180 */     Quaternionf $$3 = getLeftRotation();
/* 181 */     Vector3f $$4 = getScale();
/* 182 */     Quaternionf $$5 = getRightRotation();
/*     */     
/* 184 */     $$2.lerp((Vector3fc)$$0.getTranslation(), $$1);
/* 185 */     $$3.slerp((Quaternionfc)$$0.getLeftRotation(), $$1);
/* 186 */     $$4.lerp((Vector3fc)$$0.getScale(), $$1);
/* 187 */     $$5.slerp((Quaternionfc)$$0.getRightRotation(), $$1);
/*     */     
/* 189 */     return new Transformation($$2, $$3, $$4, $$5);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\math\Transformation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */