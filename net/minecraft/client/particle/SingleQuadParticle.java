/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import org.joml.Quaternionf;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public abstract class SingleQuadParticle extends Particle {
/*    */   protected float quadSize;
/*    */   
/*    */   public static interface FacingCameraMode { public static final FacingCameraMode LOOKAT_XYZ;
/*    */     
/*    */     static {
/* 16 */       LOOKAT_XYZ = (($$0, $$1, $$2) -> $$0.set((Quaternionfc)$$1.rotation()));
/*    */ 
/*    */ 
/*    */       
/* 20 */       LOOKAT_Y = (($$0, $$1, $$2) -> $$0.set(0.0F, ($$1.rotation()).y, 0.0F, ($$1.rotation()).w));
/*    */     }
/*    */     
/*    */     public static final FacingCameraMode LOOKAT_Y;
/*    */     
/*    */     void setRotation(Quaternionf param1Quaternionf, Camera param1Camera, float param1Float); }
/* 26 */   private final Quaternionf rotation = new Quaternionf();
/*    */   
/*    */   protected SingleQuadParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/* 29 */     super($$0, $$1, $$2, $$3);
/* 30 */     this.quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;
/*    */   }
/*    */   
/*    */   protected SingleQuadParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 34 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/* 35 */     this.quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;
/*    */   }
/*    */   
/*    */   public FacingCameraMode getFacingCameraMode() {
/* 39 */     return FacingCameraMode.LOOKAT_XYZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/* 44 */     Vec3 $$3 = $$1.getPosition();
/*    */     
/* 46 */     float $$4 = (float)(Mth.lerp($$2, this.xo, this.x) - $$3.x());
/* 47 */     float $$5 = (float)(Mth.lerp($$2, this.yo, this.y) - $$3.y());
/* 48 */     float $$6 = (float)(Mth.lerp($$2, this.zo, this.z) - $$3.z());
/*    */     
/* 50 */     getFacingCameraMode().setRotation(this.rotation, $$1, $$2);
/* 51 */     if (this.roll != 0.0F) {
/* 52 */       this.rotation.rotateZ(Mth.lerp($$2, this.oRoll, this.roll));
/*    */     }
/*    */     
/* 55 */     Vector3f[] $$7 = { new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     float $$8 = getQuadSize($$2);
/*    */     
/* 64 */     for (int $$9 = 0; $$9 < 4; $$9++) {
/* 65 */       Vector3f $$10 = $$7[$$9];
/* 66 */       $$10.rotate((Quaternionfc)this.rotation);
/* 67 */       $$10.mul($$8);
/* 68 */       $$10.add($$4, $$5, $$6);
/*    */     } 
/*    */     
/* 71 */     float $$11 = getU0();
/* 72 */     float $$12 = getU1();
/* 73 */     float $$13 = getV0();
/* 74 */     float $$14 = getV1();
/*    */     
/* 76 */     int $$15 = getLightColor($$2);
/*    */     
/* 78 */     $$0.vertex($$7[0].x(), $$7[0].y(), $$7[0].z()).uv($$12, $$14).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$15).endVertex();
/* 79 */     $$0.vertex($$7[1].x(), $$7[1].y(), $$7[1].z()).uv($$12, $$13).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$15).endVertex();
/* 80 */     $$0.vertex($$7[2].x(), $$7[2].y(), $$7[2].z()).uv($$11, $$13).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$15).endVertex();
/* 81 */     $$0.vertex($$7[3].x(), $$7[3].y(), $$7[3].z()).uv($$11, $$14).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$15).endVertex();
/*    */   }
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 85 */     return this.quadSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle scale(float $$0) {
/* 90 */     this.quadSize *= $$0;
/* 91 */     return super.scale($$0);
/*    */   }
/*    */   
/*    */   protected abstract float getU0();
/*    */   
/*    */   protected abstract float getU1();
/*    */   
/*    */   protected abstract float getV0();
/*    */   
/*    */   protected abstract float getV1();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SingleQuadParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */