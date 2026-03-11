/*     */ package net.minecraft.world.entity.monster.breeze;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.projectile.WindCharge;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Shoot
/*     */   extends Behavior<Breeze> {
/*     */   private static final int ATTACK_RANGE_MIN_SQRT = 4;
/*     */   private static final int ATTACK_RANGE_MAX_SQRT = 256;
/*     */   private static final int UNCERTAINTY_BASE = 5;
/*     */   private static final int UNCERTAINTY_MULTIPLIER = 4;
/*     */   private static final float PROJECTILE_MOVEMENT_SCALE = 0.7F;
/*  29 */   private static final int SHOOT_INITIAL_DELAY_TICKS = Math.round(15.0F);
/*  30 */   private static final int SHOOT_RECOVER_DELAY_TICKS = Math.round(4.0F);
/*  31 */   private static final int SHOOT_COOLDOWN_TICKS = Math.round(10.0F);
/*     */   
/*     */   @VisibleForTesting
/*     */   public Shoot() {
/*  35 */     super((Map)ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.BREEZE_SHOOT_COOLDOWN, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREEZE_SHOOT_CHARGING, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREEZE_SHOOT_RECOVERING, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREEZE_SHOOT, MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREEZE_JUMP_TARGET, MemoryStatus.VALUE_ABSENT), SHOOT_INITIAL_DELAY_TICKS + 1 + SHOOT_RECOVER_DELAY_TICKS);
/*     */   }
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
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Breeze $$1) {
/*  48 */     if ($$1.getPose() != Pose.STANDING) {
/*  49 */       return false;
/*     */     }
/*  51 */     return ((Boolean)$$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET)
/*  52 */       .map($$1 -> Boolean.valueOf(isTargetWithinRange($$0, $$1)))
/*  53 */       .map($$1 -> {
/*     */           if (!$$1.booleanValue()) {
/*     */             $$0.getBrain().eraseMemory(MemoryModuleType.BREEZE_SHOOT);
/*     */           }
/*     */           return $$1;
/*  58 */         }).orElse(Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Breeze $$1, long $$2) {
/*  63 */     return ($$1.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && $$1.getBrain().hasMemoryValue(MemoryModuleType.BREEZE_SHOOT));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Breeze $$1, long $$2) {
/*  68 */     $$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent($$1 -> $$0.setPose(Pose.SHOOTING));
/*  69 */     $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_CHARGING, Unit.INSTANCE, SHOOT_INITIAL_DELAY_TICKS);
/*  70 */     $$1.playSound(SoundEvents.BREEZE_INHALE, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Breeze $$1, long $$2) {
/*  75 */     if ($$1.getPose() == Pose.SHOOTING) {
/*  76 */       $$1.setPose(Pose.STANDING);
/*     */     }
/*  78 */     $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_COOLDOWN, Unit.INSTANCE, SHOOT_COOLDOWN_TICKS);
/*  79 */     $$1.getBrain().eraseMemory(MemoryModuleType.BREEZE_SHOOT);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Breeze $$1, long $$2) {
/*  84 */     Brain<Breeze> $$3 = $$1.getBrain();
/*  85 */     LivingEntity $$4 = $$3.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
/*  86 */     if ($$4 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  91 */     $$1.lookAt(EntityAnchorArgument.Anchor.EYES, $$4.position());
/*     */     
/*  93 */     if ($$3.getMemory(MemoryModuleType.BREEZE_SHOOT_CHARGING).isPresent() || $$3.getMemory(MemoryModuleType.BREEZE_SHOOT_RECOVERING).isPresent()) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     $$3.setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_RECOVERING, Unit.INSTANCE, SHOOT_RECOVER_DELAY_TICKS);
/*     */     
/*  99 */     if (isFacingTarget($$1, $$4)) {
/* 100 */       double $$5 = $$4.getX() - $$1.getX();
/* 101 */       double $$6 = $$4.getY(0.3D) - $$1.getY(0.5D);
/* 102 */       double $$7 = $$4.getZ() - $$1.getZ();
/*     */       
/* 104 */       WindCharge $$8 = new WindCharge(EntityType.WIND_CHARGE, $$1, (Level)$$0);
/* 105 */       $$1.playSound(SoundEvents.BREEZE_SHOOT, 1.5F, 1.0F);
/*     */       
/* 107 */       $$8.shoot($$5, $$6, $$7, 0.7F, (5 - $$0.getDifficulty().getId() * 4));
/* 108 */       $$0.addFreshEntity((Entity)$$8);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   public static boolean isFacingTarget(Breeze $$0, LivingEntity $$1) {
/* 116 */     Vec3 $$2 = $$0.getViewVector(1.0F);
/* 117 */     Vec3 $$3 = $$1.position().subtract($$0.position()).normalize();
/* 118 */     return ($$2.dot($$3) > 0.5D);
/*     */   }
/*     */   
/*     */   private static boolean isTargetWithinRange(Breeze $$0, LivingEntity $$1) {
/* 122 */     double $$2 = $$0.position().distanceToSqr($$1.position());
/* 123 */     return ($$2 > 4.0D && $$2 < 256.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\breeze\Shoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */