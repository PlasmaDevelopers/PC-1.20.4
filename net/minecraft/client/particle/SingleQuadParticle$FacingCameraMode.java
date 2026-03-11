/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Camera;
/*    */ import org.joml.Quaternionf;
/*    */ import org.joml.Quaternionfc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface FacingCameraMode
/*    */ {
/*    */   public static final FacingCameraMode LOOKAT_XYZ;
/*    */   public static final FacingCameraMode LOOKAT_Y;
/*    */   
/*    */   static {
/* 16 */     LOOKAT_XYZ = (($$0, $$1, $$2) -> $$0.set((Quaternionfc)$$1.rotation()));
/*    */ 
/*    */ 
/*    */     
/* 20 */     LOOKAT_Y = (($$0, $$1, $$2) -> $$0.set(0.0F, ($$1.rotation()).y, 0.0F, ($$1.rotation()).w));
/*    */   }
/*    */   
/*    */   void setRotation(Quaternionf paramQuaternionf, Camera paramCamera, float paramFloat);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SingleQuadParticle$FacingCameraMode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */