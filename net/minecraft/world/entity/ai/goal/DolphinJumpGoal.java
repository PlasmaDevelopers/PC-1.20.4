/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.animal.Dolphin;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class DolphinJumpGoal extends JumpGoal {
/*  13 */   private static final int[] STEPS_TO_CHECK = new int[] { 0, 1, 4, 5, 6, 7 };
/*     */   
/*     */   private final Dolphin dolphin;
/*     */   
/*     */   private final int interval;
/*     */   
/*     */   private boolean breached;
/*     */   
/*     */   public DolphinJumpGoal(Dolphin $$0, int $$1) {
/*  22 */     this.dolphin = $$0;
/*  23 */     this.interval = reducedTickDelay($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  28 */     if (this.dolphin.getRandom().nextInt(this.interval) != 0) {
/*  29 */       return false;
/*     */     }
/*     */     
/*  32 */     Direction $$0 = this.dolphin.getMotionDirection();
/*  33 */     int $$1 = $$0.getStepX();
/*  34 */     int $$2 = $$0.getStepZ();
/*  35 */     BlockPos $$3 = this.dolphin.blockPosition();
/*     */     
/*  37 */     for (int $$4 : STEPS_TO_CHECK) {
/*  38 */       if (!waterIsClear($$3, $$1, $$2, $$4) || !surfaceIsClear($$3, $$1, $$2, $$4)) {
/*  39 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  43 */     return true;
/*     */   }
/*     */   
/*     */   private boolean waterIsClear(BlockPos $$0, int $$1, int $$2, int $$3) {
/*  47 */     BlockPos $$4 = $$0.offset($$1 * $$3, 0, $$2 * $$3);
/*     */     
/*  49 */     return (this.dolphin.level().getFluidState($$4).is(FluidTags.WATER) && !this.dolphin.level().getBlockState($$4).blocksMotion());
/*     */   }
/*     */   
/*     */   private boolean surfaceIsClear(BlockPos $$0, int $$1, int $$2, int $$3) {
/*  53 */     return (this.dolphin.level().getBlockState($$0.offset($$1 * $$3, 1, $$2 * $$3)).isAir() && this.dolphin
/*  54 */       .level().getBlockState($$0.offset($$1 * $$3, 2, $$2 * $$3)).isAir());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  59 */     double $$0 = (this.dolphin.getDeltaMovement()).y;
/*  60 */     return (($$0 * $$0 >= 0.029999999329447746D || this.dolphin.getXRot() == 0.0F || Math.abs(this.dolphin.getXRot()) >= 10.0F || !this.dolphin.isInWater()) && !this.dolphin.onGround());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInterruptable() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  71 */     Direction $$0 = this.dolphin.getMotionDirection();
/*  72 */     this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add($$0
/*  73 */           .getStepX() * 0.6D, 0.7D, $$0
/*     */           
/*  75 */           .getStepZ() * 0.6D));
/*     */ 
/*     */     
/*  78 */     this.dolphin.getNavigation().stop();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  83 */     this.dolphin.setXRot(0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  88 */     boolean $$0 = this.breached;
/*  89 */     if (!$$0) {
/*  90 */       FluidState $$1 = this.dolphin.level().getFluidState(this.dolphin.blockPosition());
/*  91 */       this.breached = $$1.is(FluidTags.WATER);
/*     */     } 
/*     */     
/*  94 */     if (this.breached && !$$0) {
/*  95 */       this.dolphin.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
/*     */     }
/*     */     
/*  98 */     Vec3 $$2 = this.dolphin.getDeltaMovement();
/*  99 */     if ($$2.y * $$2.y < 0.029999999329447746D && this.dolphin.getXRot() != 0.0F) {
/* 100 */       this.dolphin.setXRot(Mth.rotLerp(0.2F, this.dolphin.getXRot(), 0.0F));
/* 101 */     } else if ($$2.length() > 9.999999747378752E-6D) {
/* 102 */       double $$3 = $$2.horizontalDistance();
/* 103 */       double $$4 = Math.atan2(-$$2.y, $$3) * 57.2957763671875D;
/* 104 */       this.dolphin.setXRot((float)$$4);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\DolphinJumpGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */