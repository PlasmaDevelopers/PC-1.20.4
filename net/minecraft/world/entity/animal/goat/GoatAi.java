/*     */ package net.minecraft.world.entity.animal.goat;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalPanic;
/*     */ import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
/*     */ import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
/*     */ import net.minecraft.world.entity.ai.behavior.FollowTemptation;
/*     */ import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;
/*     */ import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.PrepareRamNearestTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.Swim;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ public class GoatAi {
/*     */   public static final int RAM_PREPARE_TIME = 20;
/*  39 */   private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16); public static final int RAM_MAX_DISTANCE = 7;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 1.0F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 1.0F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT = 1.25F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_TEMPTED = 1.25F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_PANICKING = 2.0F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_PREPARING_TO_RAM = 1.25F;
/*  46 */   private static final UniformInt TIME_BETWEEN_LONG_JUMPS = UniformInt.of(600, 1200);
/*     */   public static final int MAX_LONG_JUMP_HEIGHT = 5;
/*     */   public static final int MAX_LONG_JUMP_WIDTH = 5;
/*     */   public static final float MAX_JUMP_VELOCITY = 1.5F;
/*  50 */   private static final UniformInt TIME_BETWEEN_RAMS = UniformInt.of(600, 6000);
/*  51 */   private static final UniformInt TIME_BETWEEN_RAMS_SCREAMER = UniformInt.of(100, 300); private static final TargetingConditions RAM_TARGET_CONDITIONS; private static final float SPEED_MULTIPLIER_WHEN_RAMMING = 3.0F; static {
/*  52 */     RAM_TARGET_CONDITIONS = TargetingConditions.forCombat().selector($$0 -> 
/*  53 */         (!$$0.getType().equals(EntityType.GOAT) && $$0.level().getWorldBorder().isWithinBounds($$0.getBoundingBox())));
/*     */   }
/*     */   
/*     */   public static final int RAM_MIN_DISTANCE = 4;
/*     */   public static final float ADULT_RAM_KNOCKBACK_FORCE = 2.5F;
/*     */   public static final float BABY_RAM_KNOCKBACK_FORCE = 1.0F;
/*     */   
/*     */   protected static void initMemories(Goat $$0, RandomSource $$1) {
/*  61 */     $$0.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, Integer.valueOf(TIME_BETWEEN_LONG_JUMPS.sample($$1)));
/*  62 */     $$0.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, Integer.valueOf(TIME_BETWEEN_RAMS.sample($$1)));
/*     */   }
/*     */   
/*     */   protected static Brain<?> makeBrain(Brain<Goat> $$0) {
/*  66 */     initCoreActivity($$0);
/*  67 */     initIdleActivity($$0);
/*  68 */     initLongJumpActivity($$0);
/*  69 */     initRamActivity($$0);
/*     */     
/*  71 */     $$0.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/*  72 */     $$0.setDefaultActivity(Activity.IDLE);
/*  73 */     $$0.useDefaultActivity();
/*  74 */     return $$0;
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(Brain<Goat> $$0) {
/*  78 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new AnimalPanic(2.0F), new LookAtTargetSink(45, 90), new MoveToTargetSink(), new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS), new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS), new CountDownCooldownTicks(MemoryModuleType.RAM_COOLDOWN_TICKS)));
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
/*     */   private static void initIdleActivity(Brain<Goat> $$0) {
/*  90 */     $$0.addActivityWithConditions(Activity.IDLE, ImmutableList.of(
/*  91 */           Pair.of(Integer.valueOf(0), SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))), 
/*  92 */           Pair.of(Integer.valueOf(0), new AnimalMakeLove(EntityType.GOAT, 1.0F)), 
/*  93 */           Pair.of(Integer.valueOf(1), new FollowTemptation($$0 -> Float.valueOf(1.25F))), 
/*  94 */           Pair.of(Integer.valueOf(2), BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 1.25F)), 
/*  95 */           Pair.of(Integer.valueOf(3), new RunOne((List)ImmutableList.of(
/*  96 */                 Pair.of(RandomStroll.stroll(1.0F), Integer.valueOf(2)), 
/*  97 */                 Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), Integer.valueOf(2)), 
/*  98 */                 Pair.of(new DoNothing(30, 60), Integer.valueOf(1)))))), 
/*     */         
/* 100 */         (Set)ImmutableSet.of(
/* 101 */           Pair.of(MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_ABSENT), 
/* 102 */           Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_ABSENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initLongJumpActivity(Brain<Goat> $$0) {
/* 107 */     $$0.addActivityWithConditions(Activity.LONG_JUMP, ImmutableList.of(
/* 108 */           Pair.of(Integer.valueOf(0), new LongJumpMidJump(TIME_BETWEEN_LONG_JUMPS, SoundEvents.GOAT_STEP)), 
/* 109 */           Pair.of(Integer.valueOf(1), new LongJumpToRandomPos(TIME_BETWEEN_LONG_JUMPS, 5, 5, 1.5F, $$0 -> $$0.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_LONG_JUMP : SoundEvents.GOAT_LONG_JUMP))), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 116 */         (Set)ImmutableSet.of(
/* 117 */           Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT), 
/* 118 */           Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT), 
/* 119 */           Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), 
/* 120 */           Pair.of(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initRamActivity(Brain<Goat> $$0) {
/* 125 */     $$0.addActivityWithConditions(Activity.RAM, ImmutableList.of(
/* 126 */           Pair.of(Integer.valueOf(0), new RamTarget($$0 -> $$0.isScreamingGoat() ? TIME_BETWEEN_RAMS_SCREAMER : TIME_BETWEEN_RAMS, RAM_TARGET_CONDITIONS, 3.0F, $$0 -> $$0.isBaby() ? 1.0D : 2.5D, $$0 -> $$0.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_RAM_IMPACT : SoundEvents.GOAT_RAM_IMPACT, $$0 -> $$0.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_HORN_BREAK : SoundEvents.GOAT_HORN_BREAK)), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 133 */           Pair.of(Integer.valueOf(1), new PrepareRamNearestTarget($$0 -> $$0.isScreamingGoat() ? TIME_BETWEEN_RAMS_SCREAMER.getMinValue() : TIME_BETWEEN_RAMS.getMinValue(), 4, 7, 1.25F, RAM_TARGET_CONDITIONS, 20, $$0 -> $$0.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_PREPARE_RAM : SoundEvents.GOAT_PREPARE_RAM))), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 141 */         (Set)ImmutableSet.of(
/* 142 */           Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT), 
/* 143 */           Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT), 
/* 144 */           Pair.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateActivity(Goat $$0) {
/* 149 */     $$0.getBrain().setActiveActivityToFirstValid((List)ImmutableList.of(Activity.RAM, Activity.LONG_JUMP, Activity.IDLE));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ingredient getTemptations() {
/* 157 */     return Ingredient.of(new ItemLike[] { (ItemLike)Items.WHEAT });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\goat\GoatAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */