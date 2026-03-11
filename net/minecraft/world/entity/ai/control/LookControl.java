/*     */ package net.minecraft.world.entity.ai.control;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class LookControl
/*     */   implements Control
/*     */ {
/*     */   protected final Mob mob;
/*     */   protected float yMaxRotSpeed;
/*     */   protected float xMaxRotAngle;
/*     */   protected int lookAtCooldown;
/*     */   protected double wantedX;
/*     */   protected double wantedY;
/*     */   protected double wantedZ;
/*     */   
/*     */   public LookControl(Mob $$0) {
/*  21 */     this.mob = $$0;
/*     */   }
/*     */   
/*     */   public void setLookAt(Vec3 $$0) {
/*  25 */     setLookAt($$0.x, $$0.y, $$0.z);
/*     */   }
/*     */   
/*     */   public void setLookAt(Entity $$0) {
/*  29 */     setLookAt($$0.getX(), getWantedY($$0), $$0.getZ());
/*     */   }
/*     */   
/*     */   public void setLookAt(Entity $$0, float $$1, float $$2) {
/*  33 */     setLookAt($$0.getX(), getWantedY($$0), $$0.getZ(), $$1, $$2);
/*     */   }
/*     */   
/*     */   public void setLookAt(double $$0, double $$1, double $$2) {
/*  37 */     setLookAt($$0, $$1, $$2, this.mob.getHeadRotSpeed(), this.mob.getMaxHeadXRot());
/*     */   }
/*     */   
/*     */   public void setLookAt(double $$0, double $$1, double $$2, float $$3, float $$4) {
/*  41 */     this.wantedX = $$0;
/*  42 */     this.wantedY = $$1;
/*  43 */     this.wantedZ = $$2;
/*  44 */     this.yMaxRotSpeed = $$3;
/*  45 */     this.xMaxRotAngle = $$4;
/*  46 */     this.lookAtCooldown = 2;
/*     */   }
/*     */   
/*     */   public void tick() {
/*  50 */     if (resetXRotOnTick()) {
/*  51 */       this.mob.setXRot(0.0F);
/*     */     }
/*     */     
/*  54 */     if (this.lookAtCooldown > 0) {
/*  55 */       this.lookAtCooldown--;
/*  56 */       getYRotD().ifPresent($$0 -> this.mob.yHeadRot = rotateTowards(this.mob.yHeadRot, $$0.floatValue(), this.yMaxRotSpeed));
/*  57 */       getXRotD().ifPresent($$0 -> this.mob.setXRot(rotateTowards(this.mob.getXRot(), $$0.floatValue(), this.xMaxRotAngle)));
/*     */     } else {
/*  59 */       this.mob.yHeadRot = rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, 10.0F);
/*     */     } 
/*     */     
/*  62 */     clampHeadRotationToBody();
/*     */   }
/*     */   
/*     */   protected void clampHeadRotationToBody() {
/*  66 */     if (!this.mob.getNavigation().isDone())
/*     */     {
/*  68 */       this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, this.mob.getMaxHeadYRot());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean resetXRotOnTick() {
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isLookingAtTarget() {
/*  78 */     return (this.lookAtCooldown > 0);
/*     */   }
/*     */   
/*     */   public double getWantedX() {
/*  82 */     return this.wantedX;
/*     */   }
/*     */   
/*     */   public double getWantedY() {
/*  86 */     return this.wantedY;
/*     */   }
/*     */   
/*     */   public double getWantedZ() {
/*  90 */     return this.wantedZ;
/*     */   }
/*     */   
/*     */   protected Optional<Float> getXRotD() {
/*  94 */     double $$0 = this.wantedX - this.mob.getX();
/*  95 */     double $$1 = this.wantedY - this.mob.getEyeY();
/*  96 */     double $$2 = this.wantedZ - this.mob.getZ();
/*  97 */     double $$3 = Math.sqrt($$0 * $$0 + $$2 * $$2);
/*  98 */     return (Math.abs($$1) > 9.999999747378752E-6D || Math.abs($$3) > 9.999999747378752E-6D) ? Optional.<Float>of(Float.valueOf((float)-(Mth.atan2($$1, $$3) * 57.2957763671875D))) : Optional.<Float>empty();
/*     */   }
/*     */   
/*     */   protected Optional<Float> getYRotD() {
/* 102 */     double $$0 = this.wantedX - this.mob.getX();
/* 103 */     double $$1 = this.wantedZ - this.mob.getZ();
/* 104 */     return (Math.abs($$1) > 9.999999747378752E-6D || Math.abs($$0) > 9.999999747378752E-6D) ? Optional.<Float>of(Float.valueOf((float)(Mth.atan2($$1, $$0) * 57.2957763671875D) - 90.0F)) : Optional.<Float>empty();
/*     */   }
/*     */   
/*     */   protected float rotateTowards(float $$0, float $$1, float $$2) {
/* 108 */     float $$3 = Mth.degreesDifference($$0, $$1);
/* 109 */     float $$4 = Mth.clamp($$3, -$$2, $$2);
/* 110 */     return $$0 + $$4;
/*     */   }
/*     */   
/*     */   private static double getWantedY(Entity $$0) {
/* 114 */     if ($$0 instanceof net.minecraft.world.entity.LivingEntity) {
/* 115 */       return $$0.getEyeY();
/*     */     }
/* 117 */     return (($$0.getBoundingBox()).minY + ($$0.getBoundingBox()).maxY) / 2.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\control\LookControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */