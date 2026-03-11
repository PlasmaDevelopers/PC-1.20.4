/*     */ package net.minecraft.world.entity.monster.piglin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
/*     */ import net.minecraft.world.entity.ai.behavior.DoNothing;
/*     */ import net.minecraft.world.entity.ai.behavior.InteractWith;
/*     */ import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MeleeAttack;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
/*     */ import net.minecraft.world.entity.ai.behavior.StartAttacking;
/*     */ import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
/*     */ import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
/*     */ import net.minecraft.world.entity.ai.behavior.StrollAroundPoi;
/*     */ import net.minecraft.world.entity.ai.behavior.StrollToPoi;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PiglinBruteAi
/*     */ {
/*     */   private static final int ANGER_DURATION = 600;
/*     */   private static final int MELEE_ATTACK_COOLDOWN = 20;
/*     */   private static final double ACTIVITY_SOUND_LIKELIHOOD_PER_TICK = 0.0125D;
/*     */   private static final int MAX_LOOK_DIST = 8;
/*     */   private static final int INTERACTION_RANGE = 8;
/*     */   private static final double TARGETING_RANGE = 12.0D;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.6F;
/*     */   private static final int HOME_CLOSE_ENOUGH_DISTANCE = 2;
/*     */   private static final int HOME_TOO_FAR_DISTANCE = 100;
/*     */   private static final int HOME_STROLL_AROUND_DISTANCE = 5;
/*     */   
/*     */   protected static Brain<?> makeBrain(PiglinBrute $$0, Brain<PiglinBrute> $$1) {
/*  55 */     initCoreActivity($$0, $$1);
/*     */     
/*  57 */     initIdleActivity($$0, $$1);
/*  58 */     initFightActivity($$0, $$1);
/*     */     
/*  60 */     $$1.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/*  61 */     $$1.setDefaultActivity(Activity.IDLE);
/*  62 */     $$1.useDefaultActivity();
/*     */     
/*  64 */     return $$1;
/*     */   }
/*     */   
/*     */   protected static void initMemories(PiglinBrute $$0) {
/*  68 */     GlobalPos $$1 = GlobalPos.of($$0.level().dimension(), $$0.blockPosition());
/*  69 */     $$0.getBrain().setMemory(MemoryModuleType.HOME, $$1);
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(PiglinBrute $$0, Brain<PiglinBrute> $$1) {
/*  73 */     $$1.addActivity(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink(), 
/*     */ 
/*     */           
/*  76 */           InteractWithDoor.create(), 
/*  77 */           StopBeingAngryIfTargetDead.create()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initIdleActivity(PiglinBrute $$0, Brain<PiglinBrute> $$1) {
/*  82 */     $$1.addActivity(Activity.IDLE, 10, ImmutableList.of(
/*  83 */           StartAttacking.create(PiglinBruteAi::findNearestValidAttackTarget), 
/*  84 */           createIdleLookBehaviors(), 
/*  85 */           createIdleMovementBehaviors(), 
/*  86 */           SetLookAndInteract.create(EntityType.PLAYER, 4)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initFightActivity(PiglinBrute $$0, Brain<PiglinBrute> $$1) {
/*  91 */     $$1.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
/*  92 */           StopAttackingIfTargetInvalid.create($$1 -> !isNearestValidAttackTarget($$0, $$1)), 
/*  93 */           SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F), 
/*  94 */           MeleeAttack.create(20)), MemoryModuleType.ATTACK_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   private static RunOne<PiglinBrute> createIdleLookBehaviors() {
/*  99 */     return new RunOne((List)ImmutableList.of(
/* 100 */           Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), Integer.valueOf(1)), 
/* 101 */           Pair.of(SetEntityLookTarget.create(EntityType.PIGLIN, 8.0F), Integer.valueOf(1)), 
/* 102 */           Pair.of(SetEntityLookTarget.create(EntityType.PIGLIN_BRUTE, 8.0F), Integer.valueOf(1)), 
/* 103 */           Pair.of(SetEntityLookTarget.create(8.0F), Integer.valueOf(1)), 
/* 104 */           Pair.of(new DoNothing(30, 60), Integer.valueOf(1))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static RunOne<PiglinBrute> createIdleMovementBehaviors() {
/* 109 */     return new RunOne((List)ImmutableList.of(
/* 110 */           Pair.of(RandomStroll.stroll(0.6F), Integer.valueOf(2)), 
/* 111 */           Pair.of(InteractWith.of(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), Integer.valueOf(2)), 
/* 112 */           Pair.of(InteractWith.of(EntityType.PIGLIN_BRUTE, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), Integer.valueOf(2)), 
/* 113 */           Pair.of(StrollToPoi.create(MemoryModuleType.HOME, 0.6F, 2, 100), Integer.valueOf(2)), 
/* 114 */           Pair.of(StrollAroundPoi.create(MemoryModuleType.HOME, 0.6F, 5), Integer.valueOf(2)), 
/* 115 */           Pair.of(new DoNothing(30, 60), Integer.valueOf(1))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void updateActivity(PiglinBrute $$0) {
/* 120 */     Brain<PiglinBrute> $$1 = $$0.getBrain();
/*     */ 
/*     */ 
/*     */     
/* 124 */     Activity $$2 = $$1.getActiveNonCoreActivity().orElse(null);
/*     */ 
/*     */ 
/*     */     
/* 128 */     $$1.setActiveActivityToFirstValid((List)ImmutableList.of(Activity.FIGHT, Activity.IDLE));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     Activity $$3 = $$1.getActiveNonCoreActivity().orElse(null);
/* 134 */     if ($$2 != $$3)
/*     */     {
/* 136 */       playActivitySound($$0);
/*     */     }
/*     */ 
/*     */     
/* 140 */     $$0.setAggressive($$1.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
/*     */   }
/*     */   
/*     */   private static boolean isNearestValidAttackTarget(AbstractPiglin $$0, LivingEntity $$1) {
/* 144 */     return findNearestValidAttackTarget($$0)
/* 145 */       .filter($$1 -> ($$1 == $$0))
/* 146 */       .isPresent();
/*     */   }
/*     */   
/*     */   private static Optional<? extends LivingEntity> findNearestValidAttackTarget(AbstractPiglin $$0) {
/* 150 */     Optional<LivingEntity> $$1 = BehaviorUtils.getLivingEntityFromUUIDMemory((LivingEntity)$$0, MemoryModuleType.ANGRY_AT);
/* 151 */     if ($$1.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight((LivingEntity)$$0, $$1.get())) {
/* 152 */       return $$1;
/*     */     }
/*     */     
/* 155 */     Optional<? extends LivingEntity> $$2 = getTargetIfWithinRange($$0, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
/* 156 */     if ($$2.isPresent()) {
/* 157 */       return $$2;
/*     */     }
/*     */     
/* 160 */     return $$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
/*     */   }
/*     */   
/*     */   private static Optional<? extends LivingEntity> getTargetIfWithinRange(AbstractPiglin $$0, MemoryModuleType<? extends LivingEntity> $$1) {
/* 164 */     return $$0.getBrain().getMemory($$1).filter($$1 -> $$1.closerThan((Entity)$$0, 12.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void wasHurtBy(PiglinBrute $$0, LivingEntity $$1) {
/* 169 */     if ($$1 instanceof AbstractPiglin) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     PiglinAi.maybeRetaliate($$0, $$1);
/*     */   }
/*     */   
/*     */   protected static void setAngerTarget(PiglinBrute $$0, LivingEntity $$1) {
/* 177 */     $$0.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
/* 178 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, $$1.getUUID(), 600L);
/*     */   }
/*     */   
/*     */   protected static void maybePlayActivitySound(PiglinBrute $$0) {
/* 182 */     if (($$0.level()).random.nextFloat() < 0.0125D) {
/* 183 */       playActivitySound($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void playActivitySound(PiglinBrute $$0) {
/* 189 */     $$0.getBrain().getActiveNonCoreActivity().ifPresent($$1 -> {
/*     */           if ($$1 == Activity.FIGHT)
/*     */             $$0.playAngrySound(); 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\PiglinBruteAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */