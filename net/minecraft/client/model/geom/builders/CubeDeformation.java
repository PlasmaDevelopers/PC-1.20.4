/*    */ package net.minecraft.client.model.geom.builders;
/*    */ 
/*    */ public class CubeDeformation {
/*  4 */   public static final CubeDeformation NONE = new CubeDeformation(0.0F);
/*    */   
/*    */   final float growX;
/*    */   final float growY;
/*    */   final float growZ;
/*    */   
/*    */   public CubeDeformation(float $$0, float $$1, float $$2) {
/* 11 */     this.growX = $$0;
/* 12 */     this.growY = $$1;
/* 13 */     this.growZ = $$2;
/*    */   }
/*    */   
/*    */   public CubeDeformation(float $$0) {
/* 17 */     this($$0, $$0, $$0);
/*    */   }
/*    */   
/*    */   public CubeDeformation extend(float $$0) {
/* 21 */     return new CubeDeformation(this.growX + $$0, this.growY + $$0, this.growZ + $$0);
/*    */   }
/*    */   
/*    */   public CubeDeformation extend(float $$0, float $$1, float $$2) {
/* 25 */     return new CubeDeformation(this.growX + $$0, this.growY + $$1, this.growZ + $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\builders\CubeDeformation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */