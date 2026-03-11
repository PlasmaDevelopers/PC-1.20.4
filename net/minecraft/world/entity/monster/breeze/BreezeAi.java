/*     */ package net.minecraft.world.entity.monster.breeze;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.DoNothing;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.StartAttacking;
/*     */ import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
/*     */ import net.minecraft.world.entity.ai.behavior.Swim;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BreezeAi
/*     */ {
/*     */   public static final float SPEED_MULTIPLIER_WHEN_SLIDING = 0.6F;
/*     */   public static final float JUMP_CIRCLE_INNER_RADIUS = 4.0F;
/*     */   public static final float JUMP_CIRCLE_MIDDLE_RADIUS = 8.0F;
/*     */   public static final float JUMP_CIRCLE_OUTER_RADIUS = 20.0F;
/*  37 */   static final List<SensorType<? extends Sensor<? super Breeze>>> SENSOR_TYPES = (List<SensorType<? extends Sensor<? super Breeze>>>)ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS, SensorType.BREEZE_ATTACK_ENTITY_SENSOR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   static final List<MemoryModuleType<?>> MEMORY_TYPES = (List<MemoryModuleType<?>>)ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.BREEZE_JUMP_COOLDOWN, MemoryModuleType.BREEZE_JUMP_INHALING, MemoryModuleType.BREEZE_SHOOT, MemoryModuleType.BREEZE_SHOOT_CHARGING, MemoryModuleType.BREEZE_SHOOT_RECOVERING, MemoryModuleType.BREEZE_SHOOT_COOLDOWN, (Object[])new MemoryModuleType[] { MemoryModuleType.BREEZE_JUMP_TARGET, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.PATH });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Brain<?> makeBrain(Brain<Breeze> $$0) {
/*  64 */     initCoreActivity($$0);
/*  65 */     initFightActivity($$0);
/*     */     
/*  67 */     $$0.setCoreActivities(Set.of(Activity.CORE));
/*  68 */     $$0.setDefaultActivity(Activity.FIGHT);
/*  69 */     $$0.useDefaultActivity();
/*  70 */     return $$0;
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(Brain<Breeze> $$0) {
/*  74 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new LookAtTargetSink(45, 90), new SlideToTargetSink(20, 100)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initFightActivity(Brain<Breeze> $$0) {
/*  82 */     $$0.addActivityWithConditions(Activity.FIGHT, ImmutableList.of(
/*  83 */           Pair.of(Integer.valueOf(0), StartAttacking.create($$0 -> $$0.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))), 
/*  84 */           Pair.of(Integer.valueOf(1), StopAttackingIfTargetInvalid.create()), 
/*  85 */           Pair.of(Integer.valueOf(2), new Shoot()), 
/*  86 */           Pair.of(Integer.valueOf(3), new ShootWhenStuck()), 
/*  87 */           Pair.of(Integer.valueOf(4), new LongJump()), 
/*  88 */           Pair.of(Integer.valueOf(5), new Slide()), 
/*  89 */           Pair.of(Integer.valueOf(6), new RunOne((List)ImmutableList.of(
/*  90 */                 Pair.of(new DoNothing(20, 100), Integer.valueOf(1)), 
/*  91 */                 Pair.of(RandomStroll.stroll(0.6F), Integer.valueOf(2)))))), 
/*     */         
/*  93 */         Set.of());
/*     */   }
/*     */   
/*     */   public static class SlideToTargetSink
/*     */     extends MoveToTargetSink
/*     */   {
/*     */     @VisibleForTesting
/*     */     public SlideToTargetSink(int $$0, int $$1) {
/* 101 */       super($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void start(ServerLevel $$0, Mob $$1, long $$2) {
/* 106 */       super.start($$0, $$1, $$2);
/* 107 */       $$1.playSound(SoundEvents.BREEZE_SLIDE);
/* 108 */       $$1.setPose(Pose.SLIDING);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void stop(ServerLevel $$0, Mob $$1, long $$2) {
/* 113 */       super.stop($$0, $$1, $$2);
/* 114 */       $$1.setPose(Pose.STANDING);
/*     */ 
/*     */       
/* 117 */       if ($$1.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET))
/* 118 */         $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 60L); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\breeze\BreezeAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */