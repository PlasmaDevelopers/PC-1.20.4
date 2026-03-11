/*     */ package net.minecraft.world.entity.animal.camel;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalPanic;
/*     */ import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
/*     */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*     */ import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
/*     */ import net.minecraft.world.entity.ai.behavior.DoNothing;
/*     */ import net.minecraft.world.entity.ai.behavior.FollowTemptation;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomLookAround;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.Swim;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ 
/*     */ public class CamelAi {
/*     */   private static final float SPEED_MULTIPLIER_WHEN_PANICKING = 4.0F;
/*  45 */   private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16); private static final float SPEED_MULTIPLIER_WHEN_IDLING = 2.0F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_TEMPTED = 2.5F;
/*  47 */   private static final ImmutableList<SensorType<? extends Sensor<? super Camel>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.CAMEL_TEMPTATIONS, SensorType.NEAREST_ADULT);
/*     */ 
/*     */   
/*     */   private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT = 2.5F;
/*     */   
/*     */   private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 1.0F;
/*     */   
/*  54 */   private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.IS_PANICKING, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.GAZE_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, (Object[])new MemoryModuleType[] { MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_VISIBLE_ADULT });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void initMemories(Camel $$0, RandomSource $$1) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Brain.Provider<Camel> brainProvider() {
/*  75 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */   
/*     */   protected static Brain<?> makeBrain(Brain<Camel> $$0) {
/*  79 */     initCoreActivity($$0);
/*  80 */     initIdleActivity($$0);
/*     */     
/*  82 */     $$0.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/*  83 */     $$0.setDefaultActivity(Activity.IDLE);
/*  84 */     $$0.useDefaultActivity();
/*  85 */     return $$0;
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(Brain<Camel> $$0) {
/*  89 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new CamelPanic(4.0F), new LookAtTargetSink(45, 90), new MoveToTargetSink(), new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS), new CountDownCooldownTicks(MemoryModuleType.GAZE_COOLDOWN_TICKS)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initIdleActivity(Brain<Camel> $$0) {
/* 100 */     $$0.addActivity(Activity.IDLE, ImmutableList.of(
/* 101 */           Pair.of(Integer.valueOf(0), SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))), 
/* 102 */           Pair.of(Integer.valueOf(1), new AnimalMakeLove(EntityType.CAMEL, 1.0F)), 
/* 103 */           Pair.of(Integer.valueOf(2), new RunOne((List)ImmutableList.of(
/* 104 */                 Pair.of(new FollowTemptation($$0 -> Float.valueOf(2.5F), $$0 -> Double.valueOf($$0.isBaby() ? 2.5D : 3.5D)), Integer.valueOf(1)), 
/* 105 */                 Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Camel::refuseToMove), BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 2.5F)), Integer.valueOf(1))))), 
/*     */           
/* 107 */           Pair.of(Integer.valueOf(3), new RandomLookAround((IntProvider)UniformInt.of(150, 250), 30.0F, 0.0F, 0.0F)), 
/* 108 */           Pair.of(Integer.valueOf(4), new RunOne(
/* 109 */               (Map)ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), 
/*     */ 
/*     */               
/* 112 */               (List)ImmutableList.of(
/* 113 */                 Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Camel::refuseToMove), RandomStroll.stroll(2.0F)), Integer.valueOf(1)), 
/* 114 */                 Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Camel::refuseToMove), SetWalkTargetFromLookTarget.create(2.0F, 3)), Integer.valueOf(1)), 
/* 115 */                 Pair.of(new RandomSitting(20), Integer.valueOf(1)), 
/* 116 */                 Pair.of(new DoNothing(30, 60), Integer.valueOf(1)))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateActivity(Camel $$0) {
/* 123 */     $$0.getBrain().setActiveActivityToFirstValid((List)ImmutableList.of(Activity.IDLE));
/*     */   }
/*     */   
/*     */   public static class CamelPanic
/*     */     extends AnimalPanic
/*     */   {
/*     */     public CamelPanic(float $$0) {
/* 130 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void start(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/* 135 */       if ($$1 instanceof Camel) { Camel $$3 = (Camel)$$1;
/* 136 */         $$3.standUpInstantly(); }
/*     */       
/* 138 */       super.start($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RandomSitting extends Behavior<Camel> {
/*     */     private final int minimalPoseTicks;
/*     */     
/*     */     public RandomSitting(int $$0) {
/* 146 */       super((Map)ImmutableMap.of());
/* 147 */       this.minimalPoseTicks = $$0 * 20;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean checkExtraStartConditions(ServerLevel $$0, Camel $$1) {
/* 152 */       return (!$$1.isInWater() && $$1
/* 153 */         .getPoseTime() >= this.minimalPoseTicks && 
/* 154 */         !$$1.isLeashed() && $$1
/* 155 */         .onGround() && 
/* 156 */         !$$1.hasControllingPassenger() && $$1
/* 157 */         .canCamelChangePose());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void start(ServerLevel $$0, Camel $$1, long $$2) {
/* 162 */       if ($$1.isCamelSitting()) {
/* 163 */         $$1.standUp();
/* 164 */       } else if (!$$1.isPanicking()) {
/* 165 */         $$1.sitDown();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static Ingredient getTemptations() {
/* 171 */     return Camel.TEMPTATION_ITEM;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\camel\CamelAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */