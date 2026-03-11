/*     */ package net.minecraft.world.entity.monster.hoglin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
/*     */ import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
/*     */ import net.minecraft.world.entity.ai.behavior.BecomePassiveIfMemoryPresent;
/*     */ import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
/*     */ import net.minecraft.world.entity.ai.behavior.DoNothing;
/*     */ import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MeleeAttack;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.StartAttacking;
/*     */ import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HoglinAi
/*     */ {
/*     */   public static final int REPELLENT_DETECTION_RANGE_HORIZONTAL = 8;
/*     */   public static final int REPELLENT_DETECTION_RANGE_VERTICAL = 4;
/*  51 */   private static final UniformInt RETREAT_DURATION = TimeUtil.rangeOfSeconds(5, 20);
/*     */   private static final int ATTACK_DURATION = 200;
/*     */   private static final int DESIRED_DISTANCE_FROM_PIGLIN_WHEN_IDLING = 8;
/*     */   private static final int DESIRED_DISTANCE_FROM_PIGLIN_WHEN_RETREATING = 15;
/*     */   private static final int ATTACK_INTERVAL = 40;
/*     */   private static final int BABY_ATTACK_INTERVAL = 15;
/*     */   private static final int REPELLENT_PACIFY_TIME = 200;
/*  58 */   private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
/*     */   
/*     */   private static final float SPEED_MULTIPLIER_WHEN_AVOIDING_REPELLENT = 1.0F;
/*     */   
/*     */   private static final float SPEED_MULTIPLIER_WHEN_RETREATING = 1.3F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 0.6F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.4F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT = 0.6F;
/*     */   
/*     */   protected static Brain<?> makeBrain(Brain<Hoglin> $$0) {
/*  68 */     initCoreActivity($$0);
/*     */     
/*  70 */     initIdleActivity($$0);
/*  71 */     initFightActivity($$0);
/*  72 */     initRetreatActivity($$0);
/*     */     
/*  74 */     $$0.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/*  75 */     $$0.setDefaultActivity(Activity.IDLE);
/*  76 */     $$0.useDefaultActivity();
/*  77 */     return $$0;
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(Brain<Hoglin> $$0) {
/*  81 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initIdleActivity(Brain<Hoglin> $$0) {
/*  88 */     $$0.addActivity(Activity.IDLE, 10, ImmutableList.of(
/*  89 */           BecomePassiveIfMemoryPresent.create(MemoryModuleType.NEAREST_REPELLENT, 200), new AnimalMakeLove(EntityType.HOGLIN, 0.6F), 
/*     */           
/*  91 */           SetWalkTargetAwayFrom.pos(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, true), 
/*  92 */           StartAttacking.create(HoglinAi::findNearestValidAttackTarget), 
/*  93 */           BehaviorBuilder.triggerIf(Hoglin::isAdult, SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, 0.4F, 8, false)), 
/*  94 */           SetEntityLookTargetSometimes.create(8.0F, UniformInt.of(30, 60)), 
/*  95 */           BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.6F), 
/*  96 */           createIdleMovementBehaviors()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initFightActivity(Brain<Hoglin> $$0) {
/* 101 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
/* 102 */           BecomePassiveIfMemoryPresent.create(MemoryModuleType.NEAREST_REPELLENT, 200), new AnimalMakeLove(EntityType.HOGLIN, 0.6F), 
/*     */           
/* 104 */           SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F), 
/* 105 */           BehaviorBuilder.triggerIf(Hoglin::isAdult, MeleeAttack.create(40)), 
/* 106 */           BehaviorBuilder.triggerIf(AgeableMob::isBaby, MeleeAttack.create(15)), 
/* 107 */           StopAttackingIfTargetInvalid.create(), 
/* 108 */           EraseMemoryIf.create(HoglinAi::isBreeding, MemoryModuleType.ATTACK_TARGET)), MemoryModuleType.ATTACK_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initRetreatActivity(Brain<Hoglin> $$0) {
/* 113 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(
/* 114 */           SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.3F, 15, false), 
/* 115 */           createIdleMovementBehaviors(), 
/* 116 */           SetEntityLookTargetSometimes.create(8.0F, UniformInt.of(30, 60)), 
/* 117 */           EraseMemoryIf.create(HoglinAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   private static RunOne<Hoglin> createIdleMovementBehaviors() {
/* 122 */     return new RunOne((List)ImmutableList.of(
/* 123 */           Pair.of(RandomStroll.stroll(0.4F), Integer.valueOf(2)), 
/* 124 */           Pair.of(SetWalkTargetFromLookTarget.create(0.4F, 3), Integer.valueOf(2)), 
/* 125 */           Pair.of(new DoNothing(30, 60), Integer.valueOf(1))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void updateActivity(Hoglin $$0) {
/* 130 */     Brain<Hoglin> $$1 = $$0.getBrain();
/*     */     
/* 132 */     Activity $$2 = $$1.getActiveNonCoreActivity().orElse(null);
/*     */ 
/*     */     
/* 135 */     $$1.setActiveActivityToFirstValid((List)ImmutableList.of(Activity.FIGHT, Activity.AVOID, Activity.IDLE));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     Activity $$3 = $$1.getActiveNonCoreActivity().orElse(null);
/* 142 */     if ($$2 != $$3) {
/*     */       
/* 144 */       Objects.requireNonNull($$0); getSoundForCurrentActivity($$0).ifPresent($$0::playSoundEvent);
/*     */     } 
/*     */ 
/*     */     
/* 148 */     $$0.setAggressive($$1.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
/*     */   }
/*     */   
/*     */   protected static void onHitTarget(Hoglin $$0, LivingEntity $$1) {
/* 152 */     if ($$0.isBaby()) {
/*     */       return;
/*     */     }
/*     */     
/* 156 */     if ($$1.getType() == EntityType.PIGLIN && piglinsOutnumberHoglins($$0)) {
/*     */       
/* 158 */       setAvoidTarget($$0, $$1);
/* 159 */       broadcastRetreat($$0, $$1);
/*     */       return;
/*     */     } 
/* 162 */     broadcastAttackTarget($$0, $$1);
/*     */   }
/*     */   
/*     */   private static void broadcastRetreat(Hoglin $$0, LivingEntity $$1) {
/* 166 */     getVisibleAdultHoglins($$0).forEach($$1 -> retreatFromNearestTarget($$1, $$0));
/*     */   }
/*     */   
/*     */   private static void retreatFromNearestTarget(Hoglin $$0, LivingEntity $$1) {
/* 170 */     LivingEntity $$2 = $$1;
/*     */     
/* 172 */     Brain<Hoglin> $$3 = $$0.getBrain();
/* 173 */     $$2 = BehaviorUtils.getNearestTarget((LivingEntity)$$0, $$3.getMemory(MemoryModuleType.AVOID_TARGET), $$2);
/* 174 */     $$2 = BehaviorUtils.getNearestTarget((LivingEntity)$$0, $$3.getMemory(MemoryModuleType.ATTACK_TARGET), $$2);
/*     */     
/* 176 */     setAvoidTarget($$0, $$2);
/*     */   }
/*     */   
/*     */   private static void setAvoidTarget(Hoglin $$0, LivingEntity $$1) {
/* 180 */     $$0.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
/* 181 */     $$0.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/* 182 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, $$1, RETREAT_DURATION.sample(($$0.level()).random));
/*     */   }
/*     */   
/*     */   private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Hoglin $$0) {
/* 186 */     if (isPacified($$0) || isBreeding($$0))
/*     */     {
/* 188 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/* 192 */     return $$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
/*     */   }
/*     */   
/*     */   static boolean isPosNearNearestRepellent(Hoglin $$0, BlockPos $$1) {
/* 196 */     Optional<BlockPos> $$2 = $$0.getBrain().getMemory(MemoryModuleType.NEAREST_REPELLENT);
/* 197 */     return ($$2.isPresent() && ((BlockPos)$$2.get()).closerThan((Vec3i)$$1, 8.0D));
/*     */   }
/*     */   
/*     */   private static boolean wantsToStopFleeing(Hoglin $$0) {
/* 201 */     return ($$0.isAdult() && !piglinsOutnumberHoglins($$0));
/*     */   }
/*     */   
/*     */   private static boolean piglinsOutnumberHoglins(Hoglin $$0) {
/* 205 */     if ($$0.isBaby()) {
/* 206 */       return false;
/*     */     }
/*     */     
/* 209 */     int $$1 = ((Integer)$$0.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT).orElse(Integer.valueOf(0))).intValue();
/* 210 */     int $$2 = ((Integer)$$0.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(Integer.valueOf(0))).intValue() + 1;
/* 211 */     return ($$1 > $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void wasHurtBy(Hoglin $$0, LivingEntity $$1) {
/* 216 */     Brain<Hoglin> $$2 = $$0.getBrain();
/* 217 */     $$2.eraseMemory(MemoryModuleType.PACIFIED);
/* 218 */     $$2.eraseMemory(MemoryModuleType.BREED_TARGET);
/*     */     
/* 220 */     if ($$0.isBaby()) {
/*     */       
/* 222 */       retreatFromNearestTarget($$0, $$1);
/*     */       
/*     */       return;
/*     */     } 
/* 226 */     maybeRetaliate($$0, $$1);
/*     */   }
/*     */   
/*     */   private static void maybeRetaliate(Hoglin $$0, LivingEntity $$1) {
/* 230 */     if ($$0.getBrain().isActive(Activity.AVOID) && $$1.getType() == EntityType.PIGLIN) {
/*     */       return;
/*     */     }
/* 233 */     if ($$1.getType() == EntityType.HOGLIN) {
/*     */       return;
/*     */     }
/* 236 */     if (BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget((LivingEntity)$$0, $$1, 4.0D)) {
/*     */       return;
/*     */     }
/*     */     
/* 240 */     if (!Sensor.isEntityAttackable((LivingEntity)$$0, $$1)) {
/*     */       return;
/*     */     }
/*     */     
/* 244 */     setAttackTarget($$0, $$1);
/* 245 */     broadcastAttackTarget($$0, $$1);
/*     */   }
/*     */   
/*     */   private static void setAttackTarget(Hoglin $$0, LivingEntity $$1) {
/* 249 */     Brain<Hoglin> $$2 = $$0.getBrain();
/* 250 */     $$2.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
/* 251 */     $$2.eraseMemory(MemoryModuleType.BREED_TARGET);
/* 252 */     $$2.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, $$1, 200L);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void broadcastAttackTarget(Hoglin $$0, LivingEntity $$1) {
/* 257 */     getVisibleAdultHoglins($$0).forEach($$1 -> setAttackTargetIfCloserThanCurrent($$1, $$0));
/*     */   }
/*     */   
/*     */   private static void setAttackTargetIfCloserThanCurrent(Hoglin $$0, LivingEntity $$1) {
/* 261 */     if (isPacified($$0)) {
/*     */       return;
/*     */     }
/*     */     
/* 265 */     Optional<LivingEntity> $$2 = $$0.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
/* 266 */     LivingEntity $$3 = BehaviorUtils.getNearestTarget((LivingEntity)$$0, $$2, $$1);
/* 267 */     setAttackTarget($$0, $$3);
/*     */   }
/*     */   
/*     */   public static Optional<SoundEvent> getSoundForCurrentActivity(Hoglin $$0) {
/* 271 */     return $$0.getBrain().getActiveNonCoreActivity().map($$1 -> getSoundForActivity($$0, $$1));
/*     */   }
/*     */   
/*     */   private static SoundEvent getSoundForActivity(Hoglin $$0, Activity $$1) {
/* 275 */     if ($$1 == Activity.AVOID || $$0.isConverting())
/* 276 */       return SoundEvents.HOGLIN_RETREAT; 
/* 277 */     if ($$1 == Activity.FIGHT)
/* 278 */       return SoundEvents.HOGLIN_ANGRY; 
/* 279 */     if (isNearRepellent($$0)) {
/* 280 */       return SoundEvents.HOGLIN_RETREAT;
/*     */     }
/* 282 */     return SoundEvents.HOGLIN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Hoglin> getVisibleAdultHoglins(Hoglin $$0) {
/* 287 */     return (List<Hoglin>)$$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS).orElse(ImmutableList.of());
/*     */   }
/*     */   
/*     */   private static boolean isNearRepellent(Hoglin $$0) {
/* 291 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_REPELLENT);
/*     */   }
/*     */   
/*     */   private static boolean isBreeding(Hoglin $$0) {
/* 295 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET);
/*     */   }
/*     */   
/*     */   protected static boolean isPacified(Hoglin $$0) {
/* 299 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.PACIFIED);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\hoglin\HoglinAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */