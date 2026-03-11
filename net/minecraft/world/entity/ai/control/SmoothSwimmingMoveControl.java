/*    */ package net.minecraft.world.entity.ai.control;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ 
/*    */ public class SmoothSwimmingMoveControl
/*    */   extends MoveControl {
/*    */   private static final float FULL_SPEED_TURN_THRESHOLD = 10.0F;
/*    */   private static final float STOP_TURN_THRESHOLD = 60.0F;
/*    */   private final int maxTurnX;
/*    */   private final int maxTurnY;
/*    */   private final float inWaterSpeedModifier;
/*    */   private final float outsideWaterSpeedModifier;
/*    */   private final boolean applyGravity;
/*    */   
/*    */   public SmoothSwimmingMoveControl(Mob $$0, int $$1, int $$2, float $$3, float $$4, boolean $$5) {
/* 18 */     super($$0);
/* 19 */     this.maxTurnX = $$1;
/* 20 */     this.maxTurnY = $$2;
/* 21 */     this.inWaterSpeedModifier = $$3;
/* 22 */     this.outsideWaterSpeedModifier = $$4;
/* 23 */     this.applyGravity = $$5;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 28 */     if (this.applyGravity && this.mob.isInWater())
/*    */     {
/* 30 */       this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
/*    */     }
/*    */     
/* 33 */     if (this.operation != MoveControl.Operation.MOVE_TO || this.mob.getNavigation().isDone()) {
/*    */       
/* 35 */       this.mob.setSpeed(0.0F);
/* 36 */       this.mob.setXxa(0.0F);
/* 37 */       this.mob.setYya(0.0F);
/* 38 */       this.mob.setZza(0.0F);
/*    */       
/*    */       return;
/*    */     } 
/* 42 */     double $$0 = this.wantedX - this.mob.getX();
/* 43 */     double $$1 = this.wantedY - this.mob.getY();
/* 44 */     double $$2 = this.wantedZ - this.mob.getZ();
/* 45 */     double $$3 = $$0 * $$0 + $$1 * $$1 + $$2 * $$2;
/*    */     
/* 47 */     if ($$3 < 2.500000277905201E-7D) {
/* 48 */       this.mob.setZza(0.0F);
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     float $$4 = (float)(Mth.atan2($$2, $$0) * 57.2957763671875D) - 90.0F;
/* 53 */     this.mob.setYRot(rotlerp(this.mob.getYRot(), $$4, this.maxTurnY));
/* 54 */     this.mob.yBodyRot = this.mob.getYRot();
/* 55 */     this.mob.yHeadRot = this.mob.getYRot();
/*    */     
/* 57 */     float $$5 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
/* 58 */     if (this.mob.isInWater()) {
/* 59 */       this.mob.setSpeed($$5 * this.inWaterSpeedModifier);
/*    */       
/* 61 */       double $$6 = Math.sqrt($$0 * $$0 + $$2 * $$2);
/* 62 */       if (Math.abs($$1) > 9.999999747378752E-6D || Math.abs($$6) > 9.999999747378752E-6D) {
/* 63 */         float $$7 = -((float)(Mth.atan2($$1, $$6) * 57.2957763671875D));
/* 64 */         $$7 = Mth.clamp(Mth.wrapDegrees($$7), -this.maxTurnX, this.maxTurnX);
/* 65 */         this.mob.setXRot(rotlerp(this.mob.getXRot(), $$7, 5.0F));
/*    */       } 
/*    */       
/* 68 */       float $$8 = Mth.cos(this.mob.getXRot() * 0.017453292F);
/* 69 */       float $$9 = Mth.sin(this.mob.getXRot() * 0.017453292F);
/* 70 */       this.mob.zza = $$8 * $$5;
/* 71 */       this.mob.yya = -$$9 * $$5;
/*    */     } else {
/* 73 */       float $$10 = Math.abs(Mth.wrapDegrees(this.mob.getYRot() - $$4));
/* 74 */       float $$11 = getTurningSpeedFactor($$10);
/*    */       
/* 76 */       this.mob.setSpeed($$5 * this.outsideWaterSpeedModifier * $$11);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static float getTurningSpeedFactor(float $$0) {
/* 81 */     return 1.0F - Mth.clamp(($$0 - 10.0F) / 50.0F, 0.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\control\SmoothSwimmingMoveControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */