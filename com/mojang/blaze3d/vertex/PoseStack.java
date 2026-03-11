/*     */ package com.mojang.blaze3d.vertex;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ 
/*     */ public class PoseStack {
/*     */   public PoseStack() {
/*  13 */     this.poseStack = (Deque<Pose>)Util.make(Queues.newArrayDeque(), $$0 -> {
/*     */           Matrix4f $$1 = new Matrix4f();
/*     */           Matrix3f $$2 = new Matrix3f();
/*     */           $$0.add(new Pose($$1, $$2));
/*     */         });
/*     */   } private final Deque<Pose> poseStack;
/*     */   public void translate(double $$0, double $$1, double $$2) {
/*  20 */     translate((float)$$0, (float)$$1, (float)$$2);
/*     */   }
/*     */   
/*     */   public void translate(float $$0, float $$1, float $$2) {
/*  24 */     Pose $$3 = this.poseStack.getLast();
/*  25 */     $$3.pose.translate($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public void scale(float $$0, float $$1, float $$2) {
/*  29 */     Pose $$3 = this.poseStack.getLast();
/*  30 */     $$3.pose.scale($$0, $$1, $$2);
/*     */     
/*  32 */     if ($$0 == $$1 && $$1 == $$2) {
/*     */       
/*  34 */       if ($$0 > 0.0F) {
/*     */         return;
/*     */       }
/*  37 */       $$3.normal.scale(-1.0F);
/*     */     } 
/*     */ 
/*     */     
/*  41 */     float $$4 = 1.0F / $$0;
/*  42 */     float $$5 = 1.0F / $$1;
/*  43 */     float $$6 = 1.0F / $$2;
/*     */     
/*  45 */     float $$7 = Mth.fastInvCubeRoot($$4 * $$5 * $$6);
/*  46 */     $$3.normal.scale($$7 * $$4, $$7 * $$5, $$7 * $$6);
/*     */   }
/*     */   
/*     */   public void mulPose(Quaternionf $$0) {
/*  50 */     Pose $$1 = this.poseStack.getLast();
/*  51 */     $$1.pose.rotate((Quaternionfc)$$0);
/*  52 */     $$1.normal.rotate((Quaternionfc)$$0);
/*     */   }
/*     */   
/*     */   public void rotateAround(Quaternionf $$0, float $$1, float $$2, float $$3) {
/*  56 */     Pose $$4 = this.poseStack.getLast();
/*  57 */     $$4.pose.rotateAround((Quaternionfc)$$0, $$1, $$2, $$3);
/*  58 */     $$4.normal.rotate((Quaternionfc)$$0);
/*     */   }
/*     */   
/*     */   public void pushPose() {
/*  62 */     Pose $$0 = this.poseStack.getLast();
/*  63 */     this.poseStack.addLast(new Pose(new Matrix4f((Matrix4fc)$$0.pose), new Matrix3f((Matrix3fc)$$0.normal)));
/*     */   }
/*     */   
/*     */   public void popPose() {
/*  67 */     this.poseStack.removeLast();
/*     */   }
/*     */   
/*     */   public Pose last() {
/*  71 */     return this.poseStack.getLast();
/*     */   }
/*     */   
/*     */   public boolean clear() {
/*  75 */     return (this.poseStack.size() == 1);
/*     */   }
/*     */   
/*     */   public void setIdentity() {
/*  79 */     Pose $$0 = this.poseStack.getLast();
/*  80 */     $$0.pose.identity();
/*  81 */     $$0.normal.identity();
/*     */   }
/*     */   
/*     */   public void mulPoseMatrix(Matrix4f $$0) {
/*  85 */     ((Pose)this.poseStack.getLast()).pose.mul((Matrix4fc)$$0);
/*     */   }
/*     */   
/*     */   public static final class Pose {
/*     */     final Matrix4f pose;
/*     */     final Matrix3f normal;
/*     */     
/*     */     Pose(Matrix4f $$0, Matrix3f $$1) {
/*  93 */       this.pose = $$0;
/*  94 */       this.normal = $$1;
/*     */     }
/*     */     
/*     */     public Matrix4f pose() {
/*  98 */       return this.pose;
/*     */     }
/*     */     
/*     */     public Matrix3f normal() {
/* 102 */       return this.normal;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\PoseStack.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */