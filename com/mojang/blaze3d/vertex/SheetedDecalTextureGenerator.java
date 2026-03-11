/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import net.minecraft.core.Direction;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix3fc;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SheetedDecalTextureGenerator
/*     */   extends DefaultedVertexConsumer
/*     */ {
/*     */   private final VertexConsumer delegate;
/*     */   private final Matrix4f cameraInversePose;
/*     */   private final Matrix3f normalInversePose;
/*     */   private final float textureScale;
/*     */   private float x;
/*     */   private float y;
/*     */   private float z;
/*     */   private int overlayU;
/*     */   private int overlayV;
/*     */   private int lightCoords;
/*     */   private float nx;
/*     */   private float ny;
/*     */   private float nz;
/*     */   
/*     */   public SheetedDecalTextureGenerator(VertexConsumer $$0, Matrix4f $$1, Matrix3f $$2, float $$3) {
/*  32 */     this.delegate = $$0;
/*  33 */     this.cameraInversePose = (new Matrix4f((Matrix4fc)$$1)).invert();
/*  34 */     this.normalInversePose = (new Matrix3f((Matrix3fc)$$2)).invert();
/*  35 */     this.textureScale = $$3;
/*  36 */     resetState();
/*     */   }
/*     */   
/*     */   private void resetState() {
/*  40 */     this.x = 0.0F;
/*  41 */     this.y = 0.0F;
/*  42 */     this.z = 0.0F;
/*  43 */     this.overlayU = 0;
/*  44 */     this.overlayV = 10;
/*  45 */     this.lightCoords = 15728880;
/*  46 */     this.nx = 0.0F;
/*  47 */     this.ny = 1.0F;
/*  48 */     this.nz = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endVertex() {
/*  53 */     Vector3f $$0 = this.normalInversePose.transform(new Vector3f(this.nx, this.ny, this.nz));
/*  54 */     Direction $$1 = Direction.getNearest($$0.x(), $$0.y(), $$0.z());
/*     */     
/*  56 */     Vector4f $$2 = this.cameraInversePose.transform(new Vector4f(this.x, this.y, this.z, 1.0F));
/*     */ 
/*     */     
/*  59 */     $$2.rotateY(3.1415927F);
/*  60 */     $$2.rotateX(-1.5707964F);
/*  61 */     $$2.rotate((Quaternionfc)$$1.getRotation());
/*     */     
/*  63 */     float $$3 = -$$2.x() * this.textureScale;
/*  64 */     float $$4 = -$$2.y() * this.textureScale;
/*     */     
/*  66 */     this.delegate.vertex(this.x, this.y, this.z).color(1.0F, 1.0F, 1.0F, 1.0F).uv($$3, $$4).overlayCoords(this.overlayU, this.overlayV).uv2(this.lightCoords).normal(this.nx, this.ny, this.nz).endVertex();
/*  67 */     resetState();
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer vertex(double $$0, double $$1, double $$2) {
/*  72 */     this.x = (float)$$0;
/*  73 */     this.y = (float)$$1;
/*  74 */     this.z = (float)$$2;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexConsumer color(int $$0, int $$1, int $$2, int $$3) {
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexConsumer uv(float $$0, float $$1) {
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer overlayCoords(int $$0, int $$1) {
/*  92 */     this.overlayU = $$0;
/*  93 */     this.overlayV = $$1;
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer uv2(int $$0, int $$1) {
/*  99 */     this.lightCoords = $$0 | $$1 << 16;
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer normal(float $$0, float $$1, float $$2) {
/* 105 */     this.nx = $$0;
/* 106 */     this.ny = $$1;
/* 107 */     this.nz = $$2;
/* 108 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\SheetedDecalTextureGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */