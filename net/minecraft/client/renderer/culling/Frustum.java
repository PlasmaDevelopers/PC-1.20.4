/*    */ package net.minecraft.client.renderer.culling;
/*    */ 
/*    */ import net.minecraft.world.phys.AABB;
/*    */ import org.joml.FrustumIntersection;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Matrix4fc;
/*    */ import org.joml.Vector4f;
/*    */ 
/*    */ public class Frustum {
/* 10 */   private final FrustumIntersection intersection = new FrustumIntersection(); public static final int OFFSET_STEP = 4;
/* 11 */   private final Matrix4f matrix = new Matrix4f();
/*    */   private Vector4f viewVector;
/*    */   private double camX;
/*    */   private double camY;
/*    */   private double camZ;
/*    */   
/*    */   public Frustum(Matrix4f $$0, Matrix4f $$1) {
/* 18 */     calculateFrustum($$0, $$1);
/*    */   }
/*    */   
/*    */   public Frustum(Frustum $$0) {
/* 22 */     this.intersection.set((Matrix4fc)$$0.matrix);
/* 23 */     this.matrix.set((Matrix4fc)$$0.matrix);
/* 24 */     this.camX = $$0.camX;
/* 25 */     this.camY = $$0.camY;
/* 26 */     this.camZ = $$0.camZ;
/* 27 */     this.viewVector = $$0.viewVector;
/*    */   }
/*    */   
/*    */   public Frustum offsetToFullyIncludeCameraCube(int $$0) {
/* 31 */     double $$1 = Math.floor(this.camX / $$0) * $$0;
/* 32 */     double $$2 = Math.floor(this.camY / $$0) * $$0;
/* 33 */     double $$3 = Math.floor(this.camZ / $$0) * $$0;
/* 34 */     double $$4 = Math.ceil(this.camX / $$0) * $$0;
/* 35 */     double $$5 = Math.ceil(this.camY / $$0) * $$0;
/* 36 */     double $$6 = Math.ceil(this.camZ / $$0) * $$0;
/* 37 */     while (this.intersection.intersectAab((float)($$1 - this.camX), (float)($$2 - this.camY), (float)($$3 - this.camZ), (float)($$4 - this.camX), (float)($$5 - this.camY), (float)($$6 - this.camZ)) != -2) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 45 */       this.camX -= (this.viewVector.x() * 4.0F);
/* 46 */       this.camY -= (this.viewVector.y() * 4.0F);
/* 47 */       this.camZ -= (this.viewVector.z() * 4.0F);
/*    */     } 
/* 49 */     return this;
/*    */   }
/*    */   
/*    */   public void prepare(double $$0, double $$1, double $$2) {
/* 53 */     this.camX = $$0;
/* 54 */     this.camY = $$1;
/* 55 */     this.camZ = $$2;
/*    */   }
/*    */   
/*    */   private void calculateFrustum(Matrix4f $$0, Matrix4f $$1) {
/* 59 */     $$1.mul((Matrix4fc)$$0, this.matrix);
/*    */     
/* 61 */     this.intersection.set((Matrix4fc)this.matrix);
/* 62 */     this.viewVector = this.matrix.transformTranspose(new Vector4f(0.0F, 0.0F, 1.0F, 0.0F));
/*    */   }
/*    */   
/*    */   public boolean isVisible(AABB $$0) {
/* 66 */     return cubeInFrustum($$0.minX, $$0.minY, $$0.minZ, $$0.maxX, $$0.maxY, $$0.maxZ);
/*    */   }
/*    */   
/*    */   private boolean cubeInFrustum(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/* 70 */     float $$6 = (float)($$0 - this.camX);
/* 71 */     float $$7 = (float)($$1 - this.camY);
/* 72 */     float $$8 = (float)($$2 - this.camZ);
/* 73 */     float $$9 = (float)($$3 - this.camX);
/* 74 */     float $$10 = (float)($$4 - this.camY);
/* 75 */     float $$11 = (float)($$5 - this.camZ);
/* 76 */     return this.intersection.testAab($$6, $$7, $$8, $$9, $$10, $$11);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\culling\Frustum.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */