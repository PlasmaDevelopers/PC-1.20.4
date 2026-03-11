/*     */ package net.minecraft.world.entity.animal.sniffer;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
/*     */ import net.minecraft.world.entity.ai.behavior.AnimalPanic;
/*     */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*     */ import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
/*     */ import net.minecraft.world.entity.ai.behavior.DoNothing;
/*     */ import net.minecraft.world.entity.ai.behavior.FollowTemptation;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.PositionTracker;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.Swim;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SnifferAi {
/*  45 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int MAX_LOOK_DISTANCE = 6;
/*     */   
/*  49 */   static final List<SensorType<? extends Sensor<? super Sniffer>>> SENSOR_TYPES = (List<SensorType<? extends Sensor<? super Sniffer>>>)ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS, SensorType.SNIFFER_TEMPTATIONS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   static final List<MemoryModuleType<?>> MEMORY_TYPES = (List<MemoryModuleType<?>>)ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.IS_PANICKING, MemoryModuleType.SNIFFER_SNIFFING_TARGET, MemoryModuleType.SNIFFER_DIGGING, MemoryModuleType.SNIFFER_HAPPY, MemoryModuleType.SNIFF_COOLDOWN, MemoryModuleType.SNIFFER_EXPLORED_POSITIONS, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.BREED_TARGET, (Object[])new MemoryModuleType[] { MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED });
/*     */ 
/*     */   
/*     */   private static final int SNIFFING_COOLDOWN_TICKS = 9600;
/*     */ 
/*     */   
/*     */   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 1.0F;
/*     */ 
/*     */   
/*     */   private static final float SPEED_MULTIPLIER_WHEN_PANICKING = 2.0F;
/*     */ 
/*     */   
/*     */   private static final float SPEED_MULTIPLIER_WHEN_SNIFFING = 1.25F;
/*     */ 
/*     */   
/*     */   private static final float SPEED_MULTIPLIER_WHEN_TEMPTED = 1.25F;
/*     */ 
/*     */   
/*     */   public static Ingredient getTemptations() {
/*  75 */     return Ingredient.of(new ItemLike[] { (ItemLike)Items.TORCHFLOWER_SEEDS });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Brain<?> makeBrain(Brain<Sniffer> $$0) {
/*  86 */     initCoreActivity($$0);
/*  87 */     initIdleActivity($$0);
/*  88 */     initSniffingActivity($$0);
/*  89 */     initDigActivity($$0);
/*     */     
/*  91 */     $$0.setCoreActivities(Set.of(Activity.CORE));
/*  92 */     $$0.setDefaultActivity(Activity.IDLE);
/*  93 */     $$0.useDefaultActivity();
/*  94 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   static Sniffer resetSniffing(Sniffer $$0) {
/*  99 */     $$0.getBrain().eraseMemory(MemoryModuleType.SNIFFER_DIGGING);
/* 100 */     $$0.getBrain().eraseMemory(MemoryModuleType.SNIFFER_SNIFFING_TARGET);
/*     */     
/* 102 */     return $$0.transitionTo(Sniffer.State.IDLING);
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(Brain<Sniffer> $$0) {
/* 106 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new AnimalPanic(2.0F)
/*     */           {
/*     */             
/*     */             protected void start(ServerLevel $$0, PathfinderMob $$1, long $$2)
/*     */             {
/* 111 */               SnifferAi.resetSniffing((Sniffer)$$1);
/* 112 */               super.start($$0, $$1, $$2);
/*     */             }
/*     */           }new MoveToTargetSink(500, 700), new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initSniffingActivity(Brain<Sniffer> $$0) {
/* 121 */     $$0.addActivityWithConditions(Activity.SNIFF, 
/* 122 */         ImmutableList.of(
/* 123 */           Pair.of(Integer.valueOf(0), new Searching())), 
/* 124 */         Set.of(
/* 125 */           Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT), 
/* 126 */           Pair.of(MemoryModuleType.SNIFFER_SNIFFING_TARGET, MemoryStatus.VALUE_PRESENT), 
/* 127 */           Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initDigActivity(Brain<Sniffer> $$0) {
/* 132 */     $$0.addActivityWithConditions(Activity.DIG, 
/* 133 */         ImmutableList.of(
/* 134 */           Pair.of(Integer.valueOf(0), new Digging(160, 180)), 
/* 135 */           Pair.of(Integer.valueOf(0), new FinishedDigging(40))), 
/*     */         
/* 137 */         Set.of(
/* 138 */           Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT), 
/* 139 */           Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), 
/* 140 */           Pair.of(MemoryModuleType.SNIFFER_DIGGING, MemoryStatus.VALUE_PRESENT)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initIdleActivity(Brain<Sniffer> $$0) {
/* 146 */     $$0.addActivityWithConditions(Activity.IDLE, ImmutableList.of(
/* 147 */           Pair.of(Integer.valueOf(0), new AnimalMakeLove(EntityType.SNIFFER, 1.0F)
/*     */             {
/*     */               protected void start(ServerLevel $$0, Animal $$1, long $$2) {
/* 150 */                 SnifferAi.resetSniffing((Sniffer)$$1);
/* 151 */                 super.start($$0, $$1, $$2);
/*     */               }
/* 154 */             }), Pair.of(Integer.valueOf(1), new FollowTemptation($$0 -> Float.valueOf(1.25F), $$0 -> Double.valueOf($$0.isBaby() ? 2.5D : 3.5D))
/*     */             {
/*     */               protected void start(ServerLevel $$0, PathfinderMob $$1, long $$2)
/*     */               {
/* 158 */                 SnifferAi.resetSniffing((Sniffer)$$1);
/* 159 */                 super.start($$0, $$1, $$2);
/*     */               }
/* 162 */             }), Pair.of(Integer.valueOf(2), new LookAtTargetSink(45, 90)), 
/* 163 */           Pair.of(Integer.valueOf(3), new FeelingHappy(40, 100)), 
/* 164 */           Pair.of(Integer.valueOf(4), new RunOne((List)ImmutableList.of(
/* 165 */                 Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), Integer.valueOf(2)), 
/* 166 */                 Pair.of(new Scenting(40, 80), Integer.valueOf(1)), 
/* 167 */                 Pair.of(new Sniffing(40, 80), Integer.valueOf(1)), 
/* 168 */                 Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 6.0F), Integer.valueOf(1)), 
/* 169 */                 Pair.of(RandomStroll.stroll(1.0F), Integer.valueOf(1)), 
/* 170 */                 Pair.of(new DoNothing(5, 20), Integer.valueOf(2)))))), 
/*     */ 
/*     */         
/* 173 */         Set.of(
/* 174 */           Pair.of(MemoryModuleType.SNIFFER_DIGGING, MemoryStatus.VALUE_ABSENT)));
/*     */   }
/*     */ 
/*     */   
/*     */   static void updateActivity(Sniffer $$0) {
/* 179 */     $$0.getBrain().setActiveActivityToFirstValid((List)ImmutableList.of(Activity.DIG, Activity.SNIFF, Activity.IDLE));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Sniffing
/*     */     extends Behavior<Sniffer>
/*     */   {
/*     */     Sniffing(int $$0, int $$1) {
/* 188 */       super(Map.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SNIFFER_SNIFFING_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SNIFF_COOLDOWN, MemoryStatus.VALUE_ABSENT), $$0, $$1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean checkExtraStartConditions(ServerLevel $$0, Sniffer $$1) {
/* 198 */       return (!$$1.isBaby() && $$1.canSniff());
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean canStillUse(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 203 */       return $$1.canSniff();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void start(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 208 */       $$1.transitionTo(Sniffer.State.SNIFFING);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void stop(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 213 */       boolean $$3 = timedOut($$2);
/* 214 */       $$1.transitionTo(Sniffer.State.IDLING);
/*     */       
/* 216 */       if ($$3)
/* 217 */         $$1.calculateDigPosition().ifPresent($$1 -> {
/*     */               $$0.getBrain().setMemory(MemoryModuleType.SNIFFER_SNIFFING_TARGET, $$1);
/*     */               $$0.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget($$1, 1.25F, 0));
/*     */             }); 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Searching
/*     */     extends Behavior<Sniffer> {
/*     */     Searching() {
/* 227 */       super(Map.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SNIFFER_SNIFFING_TARGET, MemoryStatus.VALUE_PRESENT), 600);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean checkExtraStartConditions(ServerLevel $$0, Sniffer $$1) {
/* 238 */       return $$1.canSniff();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean canStillUse(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 243 */       if (!$$1.canSniff()) {
/* 244 */         $$1.transitionTo(Sniffer.State.IDLING);
/* 245 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 251 */       Optional<BlockPos> $$3 = $$1.getBrain().getMemory(MemoryModuleType.WALK_TARGET).map(WalkTarget::getTarget).map(PositionTracker::currentBlockPosition);
/*     */       
/* 253 */       Optional<BlockPos> $$4 = $$1.getBrain().getMemory(MemoryModuleType.SNIFFER_SNIFFING_TARGET);
/*     */       
/* 255 */       if ($$3.isEmpty() || $$4.isEmpty()) {
/* 256 */         return false;
/*     */       }
/*     */       
/* 259 */       return ((BlockPos)$$4.get()).equals($$3.get());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void start(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 264 */       $$1.transitionTo(Sniffer.State.SEARCHING);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void stop(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 270 */       if ($$1.canDig() && $$1.canSniff()) {
/* 271 */         $$1.getBrain().setMemory(MemoryModuleType.SNIFFER_DIGGING, Boolean.valueOf(true));
/*     */       }
/*     */ 
/*     */       
/* 275 */       $$1.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/* 276 */       $$1.getBrain().eraseMemory(MemoryModuleType.SNIFFER_SNIFFING_TARGET);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Digging extends Behavior<Sniffer> {
/*     */     Digging(int $$0, int $$1) {
/* 282 */       super(Map.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SNIFFER_DIGGING, MemoryStatus.VALUE_PRESENT, MemoryModuleType.SNIFF_COOLDOWN, MemoryStatus.VALUE_ABSENT), $$0, $$1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean checkExtraStartConditions(ServerLevel $$0, Sniffer $$1) {
/* 292 */       return $$1.canSniff();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean canStillUse(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 297 */       return ($$1.getBrain().getMemory(MemoryModuleType.SNIFFER_DIGGING).isPresent() && $$1.canDig() && !$$1.isInLove());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void start(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 302 */       $$1.transitionTo(Sniffer.State.DIGGING);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void stop(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 307 */       boolean $$3 = timedOut($$2);
/*     */       
/* 309 */       if ($$3) {
/* 310 */         $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, 9600L);
/*     */       } else {
/* 312 */         SnifferAi.resetSniffing($$1);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FinishedDigging extends Behavior<Sniffer> {
/*     */     FinishedDigging(int $$0) {
/* 319 */       super(Map.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SNIFFER_DIGGING, MemoryStatus.VALUE_PRESENT, MemoryModuleType.SNIFF_COOLDOWN, MemoryStatus.VALUE_PRESENT), $$0, $$0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean checkExtraStartConditions(ServerLevel $$0, Sniffer $$1) {
/* 329 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean canStillUse(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 334 */       return $$1.getBrain().getMemory(MemoryModuleType.SNIFFER_DIGGING).isPresent();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void start(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 339 */       $$1.transitionTo(Sniffer.State.RISING);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void stop(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 344 */       boolean $$3 = timedOut($$2);
/*     */       
/* 346 */       $$1
/* 347 */         .transitionTo(Sniffer.State.IDLING)
/* 348 */         .onDiggingComplete($$3);
/*     */ 
/*     */       
/* 351 */       $$1.getBrain().eraseMemory(MemoryModuleType.SNIFFER_DIGGING);
/* 352 */       $$1.getBrain().setMemory(MemoryModuleType.SNIFFER_HAPPY, Boolean.valueOf(true));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FeelingHappy extends Behavior<Sniffer> {
/*     */     FeelingHappy(int $$0, int $$1) {
/* 358 */       super(Map.of(MemoryModuleType.SNIFFER_HAPPY, MemoryStatus.VALUE_PRESENT), $$0, $$1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean canStillUse(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 365 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void start(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 370 */       $$1.transitionTo(Sniffer.State.FEELING_HAPPY);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void stop(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 375 */       $$1.transitionTo(Sniffer.State.IDLING);
/* 376 */       $$1.getBrain().eraseMemory(MemoryModuleType.SNIFFER_HAPPY);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Scenting extends Behavior<Sniffer> {
/*     */     Scenting(int $$0, int $$1) {
/* 382 */       super(Map.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SNIFFER_DIGGING, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SNIFFER_SNIFFING_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SNIFFER_HAPPY, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT), $$0, $$1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean checkExtraStartConditions(ServerLevel $$0, Sniffer $$1) {
/* 394 */       return !$$1.isTempted();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean canStillUse(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 399 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void start(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 404 */       $$1.transitionTo(Sniffer.State.SCENTING);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void stop(ServerLevel $$0, Sniffer $$1, long $$2) {
/* 409 */       $$1.transitionTo(Sniffer.State.IDLING);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\sniffer\SnifferAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */