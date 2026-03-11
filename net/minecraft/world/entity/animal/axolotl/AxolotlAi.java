/*     */ package net.minecraft.world.entity.animal.axolotl;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
/*     */ import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
/*     */ import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
/*     */ import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
/*     */ import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
/*     */ import net.minecraft.world.entity.ai.behavior.FollowTemptation;
/*     */ import net.minecraft.world.entity.ai.behavior.GateBehavior;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MeleeAttack;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.PositionTracker;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.StartAttacking;
/*     */ import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
/*     */ import net.minecraft.world.entity.ai.behavior.TryFindWater;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class AxolotlAi {
/*  44 */   private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
/*     */   private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 0.2F;
/*     */   private static final float SPEED_MULTIPLIER_ON_LAND = 0.15F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_IDLING_IN_WATER = 0.5F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_CHASING_IN_WATER = 0.6F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT_IN_WATER = 0.6F;
/*     */   
/*     */   protected static Brain<?> makeBrain(Brain<Axolotl> $$0) {
/*  52 */     initCoreActivity($$0);
/*  53 */     initIdleActivity($$0);
/*  54 */     initFightActivity($$0);
/*  55 */     initPlayDeadActivity($$0);
/*     */     
/*  57 */     $$0.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/*  58 */     $$0.setDefaultActivity(Activity.IDLE);
/*  59 */     $$0.useDefaultActivity();
/*  60 */     return $$0;
/*     */   }
/*     */   
/*     */   private static void initPlayDeadActivity(Brain<Axolotl> $$0) {
/*  64 */     $$0.addActivityAndRemoveMemoriesWhenStopped(Activity.PLAY_DEAD, 
/*  65 */         ImmutableList.of(
/*  66 */           Pair.of(Integer.valueOf(0), new PlayDead()), 
/*  67 */           Pair.of(Integer.valueOf(1), EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.PLAY_DEAD_TICKS))), 
/*     */         
/*  69 */         (Set)ImmutableSet.of(Pair.of(MemoryModuleType.PLAY_DEAD_TICKS, MemoryStatus.VALUE_PRESENT)), 
/*  70 */         (Set)ImmutableSet.of(MemoryModuleType.PLAY_DEAD_TICKS));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initFightActivity(Brain<Axolotl> $$0) {
/*  75 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 0, ImmutableList.of(
/*  76 */           StopAttackingIfTargetInvalid.create(Axolotl::onStopAttacking), 
/*  77 */           SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(AxolotlAi::getSpeedModifierChasing), 
/*  78 */           MeleeAttack.create(20), 
/*  79 */           EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)), MemoryModuleType.ATTACK_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initCoreActivity(Brain<Axolotl> $$0) {
/*  84 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink(), 
/*     */ 
/*     */           
/*  87 */           ValidatePlayDead.create(), new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initIdleActivity(Brain<Axolotl> $$0) {
/*  93 */     $$0.addActivity(Activity.IDLE, ImmutableList.of(
/*  94 */           Pair.of(Integer.valueOf(0), SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))), 
/*  95 */           Pair.of(Integer.valueOf(1), new AnimalMakeLove(EntityType.AXOLOTL, 0.2F)), 
/*  96 */           Pair.of(Integer.valueOf(2), new RunOne((List)ImmutableList.of(
/*  97 */                 Pair.of(new FollowTemptation(AxolotlAi::getSpeedModifier), Integer.valueOf(1)), 
/*  98 */                 Pair.of(BabyFollowAdult.create(ADULT_FOLLOW_RANGE, AxolotlAi::getSpeedModifierFollowingAdult), Integer.valueOf(1))))), 
/*     */           
/* 100 */           Pair.of(Integer.valueOf(3), StartAttacking.create(AxolotlAi::findNearestValidAttackTarget)), 
/* 101 */           Pair.of(Integer.valueOf(3), TryFindWater.create(6, 0.15F)), 
/* 102 */           Pair.of(Integer.valueOf(4), new GateBehavior(
/* 103 */               (Map)ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), 
/*     */ 
/*     */               
/* 106 */               (Set)ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, 
/*     */ 
/*     */               
/* 109 */               (List)ImmutableList.of(
/* 110 */                 Pair.of(RandomStroll.swim(0.5F), Integer.valueOf(2)), 
/* 111 */                 Pair.of(RandomStroll.stroll(0.15F, false), Integer.valueOf(2)), 
/* 112 */                 Pair.of(SetWalkTargetFromLookTarget.create(AxolotlAi::canSetWalkTargetFromLookTarget, AxolotlAi::getSpeedModifier, 3), Integer.valueOf(3)), 
/* 113 */                 Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), Integer.valueOf(5)), 
/* 114 */                 Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), Integer.valueOf(5)))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canSetWalkTargetFromLookTarget(LivingEntity $$0) {
/* 121 */     Level $$1 = $$0.level();
/* 122 */     Optional<PositionTracker> $$2 = $$0.getBrain().getMemory(MemoryModuleType.LOOK_TARGET);
/*     */     
/* 124 */     if ($$2.isPresent()) {
/* 125 */       BlockPos $$3 = ((PositionTracker)$$2.get()).currentBlockPosition();
/* 126 */       return ($$1.isWaterAt($$3) == $$0.isInWaterOrBubble());
/*     */     } 
/*     */     
/* 129 */     return false;
/*     */   }
/*     */   
/*     */   public static void updateActivity(Axolotl $$0) {
/* 133 */     Brain<Axolotl> $$1 = $$0.getBrain();
/*     */     
/* 135 */     Activity $$2 = $$1.getActiveNonCoreActivity().orElse(null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     if ($$2 != Activity.PLAY_DEAD) {
/* 141 */       $$1.setActiveActivityToFirstValid((List)ImmutableList.of(Activity.PLAY_DEAD, Activity.FIGHT, Activity.IDLE));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       if ($$2 == Activity.FIGHT && $$1.getActiveNonCoreActivity().orElse(null) != Activity.FIGHT) {
/* 149 */         $$1.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, Boolean.valueOf(true), 2400L);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static float getSpeedModifierChasing(LivingEntity $$0) {
/* 155 */     return $$0.isInWaterOrBubble() ? 0.6F : 0.15F;
/*     */   }
/*     */   
/*     */   private static float getSpeedModifierFollowingAdult(LivingEntity $$0) {
/* 159 */     return $$0.isInWaterOrBubble() ? 0.6F : 0.15F;
/*     */   }
/*     */   
/*     */   private static float getSpeedModifier(LivingEntity $$0) {
/* 163 */     return $$0.isInWaterOrBubble() ? 0.5F : 0.15F;
/*     */   }
/*     */   
/*     */   private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Axolotl $$0) {
/* 167 */     if (BehaviorUtils.isBreeding((LivingEntity)$$0)) {
/* 168 */       return Optional.empty();
/*     */     }
/*     */     
/* 171 */     return $$0.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
/*     */   }
/*     */   
/*     */   public static Ingredient getTemptations() {
/* 175 */     return Ingredient.of(ItemTags.AXOLOTL_TEMPT_ITEMS);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\axolotl\AxolotlAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */