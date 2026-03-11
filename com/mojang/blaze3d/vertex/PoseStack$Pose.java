/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
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
/*     */ public final class Pose
/*     */ {
/*     */   final Matrix4f pose;
/*     */   final Matrix3f normal;
/*     */   
/*     */   Pose(Matrix4f $$0, Matrix3f $$1) {
/*  93 */     this.pose = $$0;
/*  94 */     this.normal = $$1;
/*     */   }
/*     */   
/*     */   public Matrix4f pose() {
/*  98 */     return this.pose;
/*     */   }
/*     */   
/*     */   public Matrix3f normal() {
/* 102 */     return this.normal;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\PoseStack$Pose.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */