/*     */ package net.minecraft.world.entity.animal.frog;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalPanic;
/*     */ import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
/*     */ import net.minecraft.world.entity.ai.behavior.Croak;
/*     */ import net.minecraft.world.entity.ai.behavior.FollowTemptation;
/*     */ import net.minecraft.world.entity.ai.behavior.GateBehavior;
/*     */ import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;
/*     */ import net.minecraft.world.entity.ai.behavior.LongJumpToPreferredBlock;
/*     */ import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.StartAttacking;
/*     */ import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
/*     */ import net.minecraft.world.entity.ai.behavior.TryFindLand;
/*     */ import net.minecraft.world.entity.ai.behavior.TryFindLandNearWater;
/*     */ import net.minecraft.world.entity.ai.behavior.TryLaySpawnOnWaterNearLand;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
/*     */ 
/*     */ public class FrogAi {
/*     */   private static final float SPEED_MULTIPLIER_WHEN_PANICKING = 2.0F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 1.0F;
/*  55 */   private static final UniformInt TIME_BETWEEN_LONG_JUMPS = UniformInt.of(100, 140); private static final float SPEED_MULTIPLIER_WHEN_IDLING = 1.0F; private static final float SPEED_MULTIPLIER_ON_LAND = 1.0F; private static final float SPEED_MULTIPLIER_IN_WATER = 0.75F;
/*     */   private static final int MAX_LONG_JUMP_HEIGHT = 2;
/*     */   private static final int MAX_LONG_JUMP_WIDTH = 4;
/*     */   private static final float MAX_JUMP_VELOCITY = 1.5F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_TEMPTED = 1.25F;
/*     */   
/*     */   protected static void initMemories(Frog $$0, RandomSource $$1) {
/*  62 */     $$0.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, Integer.valueOf(TIME_BETWEEN_LONG_JUMPS.sample($$1)));
/*     */   }
/*     */   
/*     */   protected static Brain<?> makeBrain(Brain<Frog> $$0) {
/*  66 */     initCoreActivity($$0);
/*  67 */     initIdleActivity($$0);
/*  68 */     initSwimActivity($$0);
/*  69 */     initLaySpawnActivity($$0);
/*  70 */     initTongueActivity($$0);
/*  71 */     initJumpActivity($$0);
/*     */     
/*  73 */     $$0.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/*  74 */     $$0.setDefaultActivity(Activity.IDLE);
/*  75 */     $$0.useDefaultActivity();
/*  76 */     return $$0;
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(Brain<Frog> $$0) {
/*  80 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new AnimalPanic(2.0F), new LookAtTargetSink(45, 90), new MoveToTargetSink(), new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS), new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initIdleActivity(Brain<Frog> $$0) {
/*  90 */     $$0.addActivityWithConditions(Activity.IDLE, ImmutableList.of(
/*  91 */           Pair.of(Integer.valueOf(0), SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))), 
/*  92 */           Pair.of(Integer.valueOf(0), new AnimalMakeLove(EntityType.FROG, 1.0F)), 
/*  93 */           Pair.of(Integer.valueOf(1), new FollowTemptation($$0 -> Float.valueOf(1.25F))), 
/*  94 */           Pair.of(Integer.valueOf(2), StartAttacking.create(FrogAi::canAttack, $$0 -> $$0.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))), 
/*  95 */           Pair.of(Integer.valueOf(3), TryFindLand.create(6, 1.0F)), 
/*  96 */           Pair.of(Integer.valueOf(4), new RunOne(
/*  97 */               (Map)ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), 
/*     */ 
/*     */               
/* 100 */               (List)ImmutableList.of(
/* 101 */                 Pair.of(RandomStroll.stroll(1.0F), Integer.valueOf(1)), 
/* 102 */                 Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), Integer.valueOf(1)), 
/* 103 */                 Pair.of(new Croak(), Integer.valueOf(3)), 
/* 104 */                 Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), Integer.valueOf(2)))))), 
/*     */ 
/*     */         
/* 107 */         (Set)ImmutableSet.of(
/* 108 */           Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_ABSENT), 
/* 109 */           Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initSwimActivity(Brain<Frog> $$0) {
/* 114 */     $$0.addActivityWithConditions(Activity.SWIM, ImmutableList.of(
/* 115 */           Pair.of(Integer.valueOf(0), SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))), 
/* 116 */           Pair.of(Integer.valueOf(1), new FollowTemptation($$0 -> Float.valueOf(1.25F))), 
/* 117 */           Pair.of(Integer.valueOf(2), StartAttacking.create(FrogAi::canAttack, $$0 -> $$0.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))), 
/* 118 */           Pair.of(Integer.valueOf(3), TryFindLand.create(8, 1.5F)), 
/* 119 */           Pair.of(Integer.valueOf(5), new GateBehavior(
/* 120 */               (Map)ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), 
/*     */ 
/*     */               
/* 123 */               (Set)ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, 
/*     */ 
/*     */               
/* 126 */               (List)ImmutableList.of(
/* 127 */                 Pair.of(RandomStroll.swim(0.75F), Integer.valueOf(1)), 
/* 128 */                 Pair.of(RandomStroll.stroll(1.0F, true), Integer.valueOf(1)), 
/* 129 */                 Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), Integer.valueOf(1)), 
/* 130 */                 Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), Integer.valueOf(5)))))), 
/*     */ 
/*     */         
/* 133 */         (Set)ImmutableSet.of(
/* 134 */           Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_ABSENT), 
/* 135 */           Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_PRESENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initLaySpawnActivity(Brain<Frog> $$0) {
/* 140 */     $$0.addActivityWithConditions(Activity.LAY_SPAWN, ImmutableList.of(
/* 141 */           Pair.of(Integer.valueOf(0), SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))), 
/* 142 */           Pair.of(Integer.valueOf(1), StartAttacking.create(FrogAi::canAttack, $$0 -> $$0.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))), 
/* 143 */           Pair.of(Integer.valueOf(2), TryFindLandNearWater.create(8, 1.0F)), 
/* 144 */           Pair.of(Integer.valueOf(3), TryLaySpawnOnWaterNearLand.create(Blocks.FROGSPAWN)), 
/* 145 */           Pair.of(Integer.valueOf(4), new RunOne(
/* 146 */               (List)ImmutableList.of(
/* 147 */                 Pair.of(RandomStroll.stroll(1.0F), Integer.valueOf(2)), 
/* 148 */                 Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), Integer.valueOf(1)), 
/* 149 */                 Pair.of(new Croak(), Integer.valueOf(2)), 
/* 150 */                 Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), Integer.valueOf(1)))))), 
/*     */ 
/*     */         
/* 153 */         (Set)ImmutableSet.of(
/* 154 */           Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_ABSENT), 
/* 155 */           Pair.of(MemoryModuleType.IS_PREGNANT, MemoryStatus.VALUE_PRESENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initJumpActivity(Brain<Frog> $$0) {
/* 160 */     $$0.addActivityWithConditions(Activity.LONG_JUMP, ImmutableList.of(
/* 161 */           Pair.of(Integer.valueOf(0), new LongJumpMidJump(TIME_BETWEEN_LONG_JUMPS, SoundEvents.FROG_STEP)), 
/* 162 */           Pair.of(Integer.valueOf(1), new LongJumpToPreferredBlock(TIME_BETWEEN_LONG_JUMPS, 2, 4, 1.5F, $$0 -> SoundEvents.FROG_LONG_JUMP, BlockTags.FROG_PREFER_JUMP_TO, 0.5F, FrogAi::isAcceptableLandingSpot))), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 172 */         (Set)ImmutableSet.of(
/* 173 */           Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT), 
/* 174 */           Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT), 
/* 175 */           Pair.of(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT), 
/* 176 */           Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initTongueActivity(Brain<Frog> $$0) {
/* 181 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.TONGUE, 0, ImmutableList.of(
/* 182 */           StopAttackingIfTargetInvalid.create(), new ShootTongue(SoundEvents.FROG_TONGUE, SoundEvents.FROG_EAT)), MemoryModuleType.ATTACK_TARGET);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E extends Mob> boolean isAcceptableLandingSpot(E $$0, BlockPos $$1) {
/* 188 */     Level $$2 = $$0.level();
/* 189 */     BlockPos $$3 = $$1.below();
/* 190 */     if (!$$2.getFluidState($$1).isEmpty() || 
/* 191 */       !$$2.getFluidState($$3).isEmpty() || 
/* 192 */       !$$2.getFluidState($$1.above()).isEmpty()) {
/* 193 */       return false;
/*     */     }
/* 195 */     BlockState $$4 = $$2.getBlockState($$1);
/* 196 */     BlockState $$5 = $$2.getBlockState($$3);
/* 197 */     if ($$4.is(BlockTags.FROG_PREFER_JUMP_TO) || $$5.is(BlockTags.FROG_PREFER_JUMP_TO)) {
/* 198 */       return true;
/*     */     }
/* 200 */     BlockPathTypes $$6 = WalkNodeEvaluator.getBlockPathTypeStatic((BlockGetter)$$2, $$1.mutable());
/* 201 */     BlockPathTypes $$7 = WalkNodeEvaluator.getBlockPathTypeStatic((BlockGetter)$$2, $$3.mutable());
/* 202 */     if ($$6 == BlockPathTypes.TRAPDOOR || ($$4.isAir() && $$7 == BlockPathTypes.TRAPDOOR)) {
/* 203 */       return true;
/*     */     }
/* 205 */     return LongJumpToRandomPos.defaultAcceptableLandingSpot((Mob)$$0, $$1);
/*     */   }
/*     */   
/*     */   private static boolean canAttack(Frog $$0) {
/* 209 */     return !BehaviorUtils.isBreeding((LivingEntity)$$0);
/*     */   }
/*     */   
/*     */   public static void updateActivity(Frog $$0) {
/* 213 */     $$0.getBrain().setActiveActivityToFirstValid((List)ImmutableList.of(Activity.TONGUE, Activity.LAY_SPAWN, Activity.LONG_JUMP, Activity.SWIM, Activity.IDLE));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ingredient getTemptations() {
/* 223 */     return Frog.TEMPTATION_ITEM;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\frog\FrogAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */