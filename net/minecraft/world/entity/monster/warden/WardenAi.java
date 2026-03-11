/*     */ package net.minecraft.world.entity.monster.warden;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*     */ import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
/*     */ import net.minecraft.world.entity.ai.behavior.DoNothing;
/*     */ import net.minecraft.world.entity.ai.behavior.GoToTargetLocation;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MeleeAttack;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
/*     */ import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
/*     */ import net.minecraft.world.entity.ai.behavior.Swim;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.Digging;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.Emerging;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.ForceUnmount;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.Roar;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.SetRoarTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.SetWardenLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.Sniffing;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.TryToSniff;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WardenAi
/*     */ {
/*     */   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.5F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_INVESTIGATING = 0.7F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_FIGHTING = 1.2F;
/*     */   private static final int MELEE_ATTACK_COOLDOWN = 18;
/*  60 */   private static final int DIGGING_DURATION = Mth.ceil(100.0F);
/*  61 */   public static final int EMERGE_DURATION = Mth.ceil(133.59999F);
/*  62 */   public static final int ROAR_DURATION = Mth.ceil(84.0F);
/*  63 */   private static final int SNIFFING_DURATION = Mth.ceil(83.2F);
/*     */   
/*     */   public static final int DIGGING_COOLDOWN = 1200;
/*     */   
/*     */   private static final int DISTURBANCE_LOCATION_EXPIRY_TIME = 100;
/*     */   
/*  69 */   private static final List<SensorType<? extends Sensor<? super Warden>>> SENSOR_TYPES = List.of(SensorType.NEAREST_PLAYERS, SensorType.WARDEN_ENTITY_SENSOR);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of((MemoryModuleType<?>[])new MemoryModuleType[] { MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.ROAR_TARGET, MemoryModuleType.DISTURBANCE_LOCATION, MemoryModuleType.RECENT_PROJECTILE, MemoryModuleType.IS_SNIFFING, MemoryModuleType.IS_EMERGING, MemoryModuleType.ROAR_SOUND_DELAY, MemoryModuleType.DIG_COOLDOWN, MemoryModuleType.ROAR_SOUND_COOLDOWN, MemoryModuleType.SNIFF_COOLDOWN, MemoryModuleType.TOUCH_COOLDOWN, MemoryModuleType.VIBRATION_COOLDOWN, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, MemoryModuleType.SONIC_BOOM_SOUND_DELAY });
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
/*     */   private static final BehaviorControl<Warden> DIG_COOLDOWN_SETTER;
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
/*     */   static {
/* 104 */     DIG_COOLDOWN_SETTER = (BehaviorControl<Warden>)BehaviorBuilder.create($$0 -> $$0.group((App)$$0.registered(MemoryModuleType.DIG_COOLDOWN)).apply((Applicative)$$0, ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateActivity(Warden $$0) {
/* 114 */     $$0.getBrain().setActiveActivityToFirstValid((List)ImmutableList.of(Activity.EMERGE, Activity.DIG, Activity.ROAR, Activity.FIGHT, Activity.INVESTIGATE, Activity.SNIFF, Activity.IDLE));
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
/*     */   protected static Brain<?> makeBrain(Warden $$0, Dynamic<?> $$1) {
/* 126 */     Brain.Provider<Warden> $$2 = Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
/* 127 */     Brain<Warden> $$3 = $$2.makeBrain($$1);
/*     */     
/* 129 */     initCoreActivity($$3);
/* 130 */     initEmergeActivity($$3);
/* 131 */     initDiggingActivity($$3);
/* 132 */     initIdleActivity($$3);
/* 133 */     initRoarActivity($$3);
/* 134 */     initFightActivity($$0, $$3);
/* 135 */     initInvestigateActivity($$3);
/* 136 */     initSniffingActivity($$3);
/*     */     
/* 138 */     $$3.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/* 139 */     $$3.setDefaultActivity(Activity.IDLE);
/* 140 */     $$3.useDefaultActivity();
/*     */     
/* 142 */     return $$3;
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(Brain<Warden> $$0) {
/* 146 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), 
/*     */           
/* 148 */           SetWardenLookTarget.create(), new LookAtTargetSink(45, 90), new MoveToTargetSink()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initEmergeActivity(Brain<Warden> $$0) {
/* 155 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.EMERGE, 5, ImmutableList.of(new Emerging(EMERGE_DURATION)), MemoryModuleType.IS_EMERGING);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initDiggingActivity(Brain<Warden> $$0) {
/* 161 */     $$0.addActivityWithConditions(Activity.DIG, ImmutableList.of(
/* 162 */           Pair.of(Integer.valueOf(0), new ForceUnmount()), 
/* 163 */           Pair.of(Integer.valueOf(1), new Digging(DIGGING_DURATION))), 
/* 164 */         (Set)ImmutableSet.of(
/* 165 */           Pair.of(MemoryModuleType.ROAR_TARGET, MemoryStatus.VALUE_ABSENT), 
/* 166 */           Pair.of(MemoryModuleType.DIG_COOLDOWN, MemoryStatus.VALUE_ABSENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initIdleActivity(Brain<Warden> $$0) {
/* 171 */     $$0.addActivity(Activity.IDLE, 10, ImmutableList.of(
/* 172 */           SetRoarTarget.create(Warden::getEntityAngryAt), 
/* 173 */           TryToSniff.create(), new RunOne(
/* 174 */             (Map)ImmutableMap.of(MemoryModuleType.IS_SNIFFING, MemoryStatus.VALUE_ABSENT), 
/*     */             
/* 176 */             (List)ImmutableList.of(
/* 177 */               Pair.of(RandomStroll.stroll(0.5F), Integer.valueOf(2)), 
/* 178 */               Pair.of(new DoNothing(30, 60), Integer.valueOf(1))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initInvestigateActivity(Brain<Warden> $$0) {
/* 184 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.INVESTIGATE, 5, ImmutableList.of(
/* 185 */           SetRoarTarget.create(Warden::getEntityAngryAt), 
/* 186 */           GoToTargetLocation.create(MemoryModuleType.DISTURBANCE_LOCATION, 2, 0.7F)), MemoryModuleType.DISTURBANCE_LOCATION);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initSniffingActivity(Brain<Warden> $$0) {
/* 191 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.SNIFF, 5, ImmutableList.of(
/* 192 */           SetRoarTarget.create(Warden::getEntityAngryAt), new Sniffing(SNIFFING_DURATION)), MemoryModuleType.IS_SNIFFING);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initRoarActivity(Brain<Warden> $$0) {
/* 198 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.ROAR, 10, ImmutableList.of(new Roar()), MemoryModuleType.ROAR_TARGET);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initFightActivity(Warden $$0, Brain<Warden> $$1) {
/* 204 */     $$1.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(DIG_COOLDOWN_SETTER, 
/*     */           
/* 206 */           StopAttackingIfTargetInvalid.create($$1 -> (!$$0.getAngerLevel().isAngry() || !$$0.canTargetEntity((Entity)$$1)), WardenAi::onTargetInvalid, false), 
/* 207 */           SetEntityLookTarget.create($$1 -> isTarget($$0, $$1), (float)$$0.getAttributeValue(Attributes.FOLLOW_RANGE)), 
/* 208 */           SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.2F), new SonicBoom(), 
/*     */           
/* 210 */           MeleeAttack.create(18)), MemoryModuleType.ATTACK_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isTarget(Warden $$0, LivingEntity $$1) {
/* 215 */     return $$0.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter($$1 -> ($$1 == $$0)).isPresent();
/*     */   }
/*     */   
/*     */   private static void onTargetInvalid(Warden $$0, LivingEntity $$1) {
/* 219 */     if (!$$0.canTargetEntity((Entity)$$1)) {
/* 220 */       $$0.clearAnger((Entity)$$1);
/*     */     }
/*     */ 
/*     */     
/* 224 */     setDigCooldown((LivingEntity)$$0);
/*     */   }
/*     */   
/*     */   public static void setDigCooldown(LivingEntity $$0) {
/* 228 */     if ($$0.getBrain().hasMemoryValue(MemoryModuleType.DIG_COOLDOWN)) {
/* 229 */       $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 1200L);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setDisturbanceLocation(Warden $$0, BlockPos $$1) {
/* 234 */     if (!$$0.level().getWorldBorder().isWithinBounds($$1) || $$0
/* 235 */       .getEntityAngryAt().isPresent() || $$0
/* 236 */       .getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent()) {
/*     */       return;
/*     */     }
/*     */     
/* 240 */     setDigCooldown((LivingEntity)$$0);
/* 241 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, 100L);
/* 242 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.LOOK_TARGET, new BlockPosTracker($$1), 100L);
/* 243 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.DISTURBANCE_LOCATION, $$1, 100L);
/* 244 */     $$0.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\warden\WardenAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */