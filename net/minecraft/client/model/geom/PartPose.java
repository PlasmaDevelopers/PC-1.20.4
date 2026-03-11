/*    */ package net.minecraft.client.model.geom;
/*    */ 
/*    */ public class PartPose {
/*  4 */   public static final PartPose ZERO = offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*    */   
/*    */   public final float x;
/*    */   
/*    */   public final float y;
/*    */   public final float z;
/*    */   public final float xRot;
/*    */   public final float yRot;
/*    */   public final float zRot;
/*    */   
/*    */   private PartPose(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 15 */     this.x = $$0;
/* 16 */     this.y = $$1;
/* 17 */     this.z = $$2;
/* 18 */     this.xRot = $$3;
/* 19 */     this.yRot = $$4;
/* 20 */     this.zRot = $$5;
/*    */   }
/*    */   
/*    */   public static PartPose offset(float $$0, float $$1, float $$2) {
/* 24 */     return offsetAndRotation($$0, $$1, $$2, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public static PartPose rotation(float $$0, float $$1, float $$2) {
/* 28 */     return offsetAndRotation(0.0F, 0.0F, 0.0F, $$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public static PartPose offsetAndRotation(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 32 */     return new PartPose($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\PartPose.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */