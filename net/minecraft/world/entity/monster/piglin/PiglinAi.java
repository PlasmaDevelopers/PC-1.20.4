/*     */ package net.minecraft.world.entity.monster.piglin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.BackUpIfTooClose;
/*     */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*     */ import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
/*     */ import net.minecraft.world.entity.ai.behavior.CopyMemoryWithExpiry;
/*     */ import net.minecraft.world.entity.ai.behavior.CrossbowAttack;
/*     */ import net.minecraft.world.entity.ai.behavior.DismountOrSkipMounting;
/*     */ import net.minecraft.world.entity.ai.behavior.DoNothing;
/*     */ import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
/*     */ import net.minecraft.world.entity.ai.behavior.GoToTargetLocation;
/*     */ import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
/*     */ import net.minecraft.world.entity.ai.behavior.InteractWith;
/*     */ import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MeleeAttack;
/*     */ import net.minecraft.world.entity.ai.behavior.Mount;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.OneShot;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
/*     */ import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.StartAttacking;
/*     */ import net.minecraft.world.entity.ai.behavior.StartCelebratingIfTargetDead;
/*     */ import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
/*     */ import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
/*     */ import net.minecraft.world.entity.ai.behavior.TriggerGate;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.monster.hoglin.Hoglin;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ import net.minecraft.world.item.ArmorItem;
/*     */ import net.minecraft.world.item.ArmorMaterials;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PiglinAi
/*     */ {
/*     */   public static final int REPELLENT_DETECTION_RANGE_HORIZONTAL = 8;
/*     */   public static final int REPELLENT_DETECTION_RANGE_VERTICAL = 4;
/*  85 */   public static final Item BARTERING_ITEM = Items.GOLD_INGOT;
/*     */   
/*     */   private static final int PLAYER_ANGER_RANGE = 16;
/*     */   private static final int ANGER_DURATION = 600;
/*     */   private static final int ADMIRE_DURATION = 119;
/*     */   private static final int MAX_DISTANCE_TO_WALK_TO_ITEM = 9;
/*     */   private static final int MAX_TIME_TO_WALK_TO_ITEM = 200;
/*     */   private static final int HOW_LONG_TIME_TO_DISABLE_ADMIRE_WALKING_IF_CANT_REACH_ITEM = 200;
/*     */   private static final int CELEBRATION_TIME = 300;
/*  94 */   protected static final UniformInt TIME_BETWEEN_HUNTS = TimeUtil.rangeOfSeconds(30, 120);
/*     */   private static final int BABY_FLEE_DURATION_AFTER_GETTING_HIT = 100;
/*     */   private static final int HIT_BY_PLAYER_MEMORY_TIMEOUT = 400;
/*     */   private static final int MAX_WALK_DISTANCE_TO_START_RIDING = 8;
/*  98 */   private static final UniformInt RIDE_START_INTERVAL = TimeUtil.rangeOfSeconds(10, 40);
/*  99 */   private static final UniformInt RIDE_DURATION = TimeUtil.rangeOfSeconds(10, 30);
/* 100 */   private static final UniformInt RETREAT_DURATION = TimeUtil.rangeOfSeconds(5, 20);
/*     */   private static final int MELEE_ATTACK_COOLDOWN = 20;
/*     */   private static final int EAT_COOLDOWN = 200;
/*     */   private static final int DESIRED_DISTANCE_FROM_ENTITY_WHEN_AVOIDING = 12;
/*     */   private static final int MAX_LOOK_DIST = 8;
/*     */   private static final int MAX_LOOK_DIST_FOR_PLAYER_HOLDING_LOVED_ITEM = 14;
/*     */   private static final int INTERACTION_RANGE = 8;
/*     */   private static final int MIN_DESIRED_DIST_FROM_TARGET_WHEN_HOLDING_CROSSBOW = 5;
/*     */   private static final float SPEED_WHEN_STRAFING_BACK_FROM_TARGET = 0.75F;
/*     */   private static final int DESIRED_DISTANCE_FROM_ZOMBIFIED = 6;
/* 110 */   private static final UniformInt AVOID_ZOMBIFIED_DURATION = TimeUtil.rangeOfSeconds(5, 7);
/* 111 */   private static final UniformInt BABY_AVOID_NEMESIS_DURATION = TimeUtil.rangeOfSeconds(5, 7);
/*     */   
/*     */   private static final float PROBABILITY_OF_CELEBRATION_DANCE = 0.1F;
/*     */   
/*     */   private static final float SPEED_MULTIPLIER_WHEN_AVOIDING = 1.0F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_RETREATING = 1.0F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_MOUNTING = 0.8F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_GOING_TO_WANTED_ITEM = 1.0F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_GOING_TO_CELEBRATE_LOCATION = 1.0F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_DANCING = 0.6F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.6F;
/*     */   
/*     */   protected static Brain<?> makeBrain(Piglin $$0, Brain<Piglin> $$1) {
/* 124 */     initCoreActivity($$1);
/*     */     
/* 126 */     initIdleActivity($$1);
/*     */     
/* 128 */     initAdmireItemActivity($$1);
/*     */     
/* 130 */     initFightActivity($$0, $$1);
/* 131 */     initCelebrateActivity($$1);
/*     */     
/* 133 */     initRetreatActivity($$1);
/* 134 */     initRideHoglinActivity($$1);
/*     */     
/* 136 */     $$1.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/* 137 */     $$1.setDefaultActivity(Activity.IDLE);
/* 138 */     $$1.useDefaultActivity();
/*     */     
/* 140 */     return $$1;
/*     */   }
/*     */   
/*     */   protected static void initMemories(Piglin $$0, RandomSource $$1) {
/* 144 */     int $$2 = TIME_BETWEEN_HUNTS.sample($$1);
/* 145 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.HUNTED_RECENTLY, Boolean.valueOf(true), $$2);
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(Brain<Piglin> $$0) {
/* 149 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink(), 
/*     */ 
/*     */           
/* 152 */           InteractWithDoor.create(), 
/* 153 */           babyAvoidNemesis(), 
/* 154 */           avoidZombified(), 
/* 155 */           StopHoldingItemIfNoLongerAdmiring.create(), 
/* 156 */           StartAdmiringItemIfSeen.create(119), 
/* 157 */           StartCelebratingIfTargetDead.create(300, PiglinAi::wantsToDance), 
/* 158 */           StopBeingAngryIfTargetDead.create()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initIdleActivity(Brain<Piglin> $$0) {
/* 163 */     $$0.addActivity(Activity.IDLE, 10, ImmutableList.of(
/* 164 */           SetEntityLookTarget.create(PiglinAi::isPlayerHoldingLovedItem, 14.0F), 
/* 165 */           StartAttacking.create(AbstractPiglin::isAdult, PiglinAi::findNearestValidAttackTarget), 
/* 166 */           BehaviorBuilder.triggerIf(Piglin::canHunt, StartHuntingHoglin.create()), 
/* 167 */           avoidRepellent(), 
/* 168 */           babySometimesRideBabyHoglin(), 
/* 169 */           createIdleLookBehaviors(), 
/* 170 */           createIdleMovementBehaviors(), 
/* 171 */           SetLookAndInteract.create(EntityType.PLAYER, 4)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initFightActivity(Piglin $$0, Brain<Piglin> $$1) {
/* 176 */     $$1.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
/* 177 */           StopAttackingIfTargetInvalid.create($$1 -> !isNearestValidAttackTarget($$0, $$1)), 
/* 178 */           BehaviorBuilder.triggerIf(PiglinAi::hasCrossbow, BackUpIfTooClose.create(5, 0.75F)), 
/* 179 */           SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F), 
/* 180 */           MeleeAttack.create(20), new CrossbowAttack(), 
/*     */           
/* 182 */           RememberIfHoglinWasKilled.create(), 
/* 183 */           EraseMemoryIf.create(PiglinAi::isNearZombified, MemoryModuleType.ATTACK_TARGET)), MemoryModuleType.ATTACK_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initCelebrateActivity(Brain<Piglin> $$0) {
/* 188 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.CELEBRATE, 10, ImmutableList.of(
/* 189 */           avoidRepellent(), 
/* 190 */           SetEntityLookTarget.create(PiglinAi::isPlayerHoldingLovedItem, 14.0F), 
/* 191 */           StartAttacking.create(AbstractPiglin::isAdult, PiglinAi::findNearestValidAttackTarget), 
/* 192 */           BehaviorBuilder.triggerIf($$0 -> !$$0.isDancing(), GoToTargetLocation.create(MemoryModuleType.CELEBRATE_LOCATION, 2, 1.0F)), 
/* 193 */           BehaviorBuilder.triggerIf(Piglin::isDancing, GoToTargetLocation.create(MemoryModuleType.CELEBRATE_LOCATION, 4, 0.6F)), new RunOne(
/* 194 */             (List)ImmutableList.of(
/* 195 */               Pair.of(SetEntityLookTarget.create(EntityType.PIGLIN, 8.0F), Integer.valueOf(1)), 
/* 196 */               Pair.of(RandomStroll.stroll(0.6F, 2, 1), Integer.valueOf(1)), 
/* 197 */               Pair.of(new DoNothing(10, 20), Integer.valueOf(1))))), MemoryModuleType.CELEBRATE_LOCATION);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initAdmireItemActivity(Brain<Piglin> $$0) {
/* 203 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.ADMIRE_ITEM, 10, ImmutableList.of(
/* 204 */           GoToWantedItem.create(PiglinAi::isNotHoldingLovedItemInOffHand, 1.0F, true, 9), 
/* 205 */           StopAdmiringIfItemTooFarAway.create(9), 
/* 206 */           StopAdmiringIfTiredOfTryingToReachItem.create(200, 200)), MemoryModuleType.ADMIRING_ITEM);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initRetreatActivity(Brain<Piglin> $$0) {
/* 211 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(
/* 212 */           SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.0F, 12, true), 
/* 213 */           createIdleLookBehaviors(), 
/* 214 */           createIdleMovementBehaviors(), 
/* 215 */           EraseMemoryIf.create(PiglinAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initRideHoglinActivity(Brain<Piglin> $$0) {
/* 220 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.RIDE, 10, ImmutableList.of(
/* 221 */           Mount.create(0.8F), 
/* 222 */           SetEntityLookTarget.create(PiglinAi::isPlayerHoldingLovedItem, 8.0F), 
/* 223 */           BehaviorBuilder.sequence(
/* 224 */             (Trigger)BehaviorBuilder.triggerIf(Entity::isPassenger), 
/* 225 */             (Trigger)TriggerGate.triggerOneShuffled(
/* 226 */               (List)ImmutableList.builder()
/* 227 */               .addAll((Iterable)createLookBehaviors())
/* 228 */               .add(Pair.of(BehaviorBuilder.triggerIf($$0 -> true), Integer.valueOf(1)))
/* 229 */               .build())), 
/*     */ 
/*     */           
/* 232 */           DismountOrSkipMounting.create(8, PiglinAi::wantsToStopRiding)), MemoryModuleType.RIDE_TARGET);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ImmutableList<Pair<OneShot<LivingEntity>, Integer>> createLookBehaviors() {
/* 238 */     return ImmutableList.of(
/* 239 */         Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), Integer.valueOf(1)), 
/* 240 */         Pair.of(SetEntityLookTarget.create(EntityType.PIGLIN, 8.0F), Integer.valueOf(1)), 
/* 241 */         Pair.of(SetEntityLookTarget.create(8.0F), Integer.valueOf(1)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static RunOne<LivingEntity> createIdleLookBehaviors() {
/* 246 */     return new RunOne(
/* 247 */         (List)ImmutableList.builder()
/* 248 */         .addAll((Iterable)createLookBehaviors())
/* 249 */         .add(Pair.of(new DoNothing(30, 60), Integer.valueOf(1)))
/* 250 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static RunOne<Piglin> createIdleMovementBehaviors() {
/* 255 */     return new RunOne((List)ImmutableList.of(
/* 256 */           Pair.of(RandomStroll.stroll(0.6F), Integer.valueOf(2)), 
/*     */           
/* 258 */           Pair.of(InteractWith.of(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), Integer.valueOf(2)), 
/* 259 */           Pair.of(BehaviorBuilder.triggerIf(PiglinAi::doesntSeeAnyPlayerHoldingLovedItem, SetWalkTargetFromLookTarget.create(0.6F, 3)), Integer.valueOf(2)), 
/* 260 */           Pair.of(new DoNothing(30, 60), Integer.valueOf(1))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static BehaviorControl<PathfinderMob> avoidRepellent() {
/* 265 */     return SetWalkTargetAwayFrom.pos(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, false);
/*     */   }
/*     */   
/*     */   private static BehaviorControl<Piglin> babyAvoidNemesis() {
/* 269 */     return CopyMemoryWithExpiry.create(Piglin::isBaby, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.AVOID_TARGET, BABY_AVOID_NEMESIS_DURATION);
/*     */   }
/*     */   
/*     */   private static BehaviorControl<Piglin> avoidZombified() {
/* 273 */     return CopyMemoryWithExpiry.create(PiglinAi::isNearZombified, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MemoryModuleType.AVOID_TARGET, AVOID_ZOMBIFIED_DURATION);
/*     */   }
/*     */   
/*     */   protected static void updateActivity(Piglin $$0) {
/* 277 */     Brain<Piglin> $$1 = $$0.getBrain();
/*     */     
/* 279 */     Activity $$2 = $$1.getActiveNonCoreActivity().orElse(null);
/*     */ 
/*     */ 
/*     */     
/* 283 */     $$1.setActiveActivityToFirstValid((List)ImmutableList.of(Activity.ADMIRE_ITEM, Activity.FIGHT, Activity.AVOID, Activity.CELEBRATE, Activity.RIDE, Activity.IDLE));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 292 */     Activity $$3 = $$1.getActiveNonCoreActivity().orElse(null);
/* 293 */     if ($$2 != $$3) {
/*     */       
/* 295 */       Objects.requireNonNull($$0); getSoundForCurrentActivity($$0).ifPresent($$0::playSoundEvent);
/*     */     } 
/*     */ 
/*     */     
/* 299 */     $$0.setAggressive($$1.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
/*     */     
/* 301 */     if (!$$1.hasMemoryValue(MemoryModuleType.RIDE_TARGET) && isBabyRidingBaby($$0))
/*     */     {
/*     */ 
/*     */       
/* 305 */       $$0.stopRiding();
/*     */     }
/*     */     
/* 308 */     if (!$$1.hasMemoryValue(MemoryModuleType.CELEBRATE_LOCATION))
/*     */     {
/*     */       
/* 311 */       $$1.eraseMemory(MemoryModuleType.DANCING);
/*     */     }
/* 313 */     $$0.setDancing($$1.hasMemoryValue(MemoryModuleType.DANCING));
/*     */   }
/*     */   
/*     */   private static boolean isBabyRidingBaby(Piglin $$0) {
/* 317 */     if (!$$0.isBaby()) {
/* 318 */       return false;
/*     */     }
/* 320 */     Entity $$1 = $$0.getVehicle();
/* 321 */     return (($$1 instanceof Piglin && ((Piglin)$$1).isBaby()) || ($$1 instanceof Hoglin && ((Hoglin)$$1)
/* 322 */       .isBaby()));
/*     */   }
/*     */   protected static void pickUpItem(Piglin $$0, ItemEntity $$1) {
/*     */     ItemStack $$3;
/* 326 */     stopWalking($$0);
/*     */ 
/*     */ 
/*     */     
/* 330 */     if ($$1.getItem().is(Items.GOLD_NUGGET)) {
/*     */ 
/*     */       
/* 333 */       $$0.take((Entity)$$1, $$1.getItem().getCount());
/* 334 */       ItemStack $$2 = $$1.getItem();
/* 335 */       $$1.discard();
/*     */     } else {
/* 337 */       $$0.take((Entity)$$1, 1);
/* 338 */       $$3 = removeOneItemFromItemEntity($$1);
/*     */     } 
/*     */     
/* 341 */     if (isLovedItem($$3)) {
/* 342 */       $$0.getBrain().eraseMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
/* 343 */       holdInOffhand($$0, $$3);
/* 344 */       admireGoldItem((LivingEntity)$$0);
/*     */       
/*     */       return;
/*     */     } 
/* 348 */     if (isFood($$3) && !hasEatenRecently($$0)) {
/* 349 */       eat($$0);
/*     */       
/*     */       return;
/*     */     } 
/* 353 */     boolean $$4 = !$$0.equipItemIfPossible($$3).equals(ItemStack.EMPTY);
/* 354 */     if ($$4) {
/*     */       return;
/*     */     }
/*     */     
/* 358 */     putInInventory($$0, $$3);
/*     */   }
/*     */   
/*     */   private static void holdInOffhand(Piglin $$0, ItemStack $$1) {
/* 362 */     if (isHoldingItemInOffHand($$0)) {
/* 363 */       $$0.spawnAtLocation($$0.getItemInHand(InteractionHand.OFF_HAND));
/*     */     }
/* 365 */     $$0.holdInOffHand($$1);
/*     */   }
/*     */   
/*     */   private static ItemStack removeOneItemFromItemEntity(ItemEntity $$0) {
/* 369 */     ItemStack $$1 = $$0.getItem();
/* 370 */     ItemStack $$2 = $$1.split(1);
/* 371 */     if ($$1.isEmpty()) {
/* 372 */       $$0.discard();
/*     */     } else {
/* 374 */       $$0.setItem($$1);
/*     */     } 
/* 376 */     return $$2;
/*     */   }
/*     */   
/*     */   protected static void stopHoldingOffHandItem(Piglin $$0, boolean $$1) {
/* 380 */     ItemStack $$2 = $$0.getItemInHand(InteractionHand.OFF_HAND);
/* 381 */     $$0.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
/*     */     
/* 383 */     if ($$0.isAdult()) {
/* 384 */       boolean $$3 = isBarterCurrency($$2);
/* 385 */       if ($$1 && $$3) {
/* 386 */         throwItems($$0, getBarterResponseItems($$0));
/* 387 */       } else if (!$$3) {
/* 388 */         boolean $$4 = !$$0.equipItemIfPossible($$2).isEmpty();
/* 389 */         if (!$$4) {
/* 390 */           putInInventory($$0, $$2);
/*     */         }
/*     */       } 
/*     */     } else {
/* 394 */       boolean $$5 = !$$0.equipItemIfPossible($$2).isEmpty();
/* 395 */       if (!$$5) {
/*     */ 
/*     */ 
/*     */         
/* 399 */         ItemStack $$6 = $$0.getMainHandItem();
/* 400 */         if (isLovedItem($$6)) {
/* 401 */           putInInventory($$0, $$6);
/*     */         } else {
/* 403 */           throwItems($$0, Collections.singletonList($$6));
/*     */         } 
/* 405 */         $$0.holdInMainHand($$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void cancelAdmiring(Piglin $$0) {
/* 411 */     if (isAdmiringItem($$0) && !$$0.getOffhandItem().isEmpty()) {
/* 412 */       $$0.spawnAtLocation($$0.getOffhandItem());
/* 413 */       $$0.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void putInInventory(Piglin $$0, ItemStack $$1) {
/* 418 */     ItemStack $$2 = $$0.addToInventory($$1);
/* 419 */     throwItemsTowardRandomPos($$0, Collections.singletonList($$2));
/*     */   }
/*     */   
/*     */   private static void throwItems(Piglin $$0, List<ItemStack> $$1) {
/* 423 */     Optional<Player> $$2 = $$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
/* 424 */     if ($$2.isPresent()) {
/* 425 */       throwItemsTowardPlayer($$0, $$2.get(), $$1);
/*     */     } else {
/* 427 */       throwItemsTowardRandomPos($$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void throwItemsTowardRandomPos(Piglin $$0, List<ItemStack> $$1) {
/* 432 */     throwItemsTowardPos($$0, $$1, getRandomNearbyPos($$0));
/*     */   }
/*     */   
/*     */   private static void throwItemsTowardPlayer(Piglin $$0, Player $$1, List<ItemStack> $$2) {
/* 436 */     throwItemsTowardPos($$0, $$2, $$1.position());
/*     */   }
/*     */   
/*     */   private static void throwItemsTowardPos(Piglin $$0, List<ItemStack> $$1, Vec3 $$2) {
/* 440 */     if (!$$1.isEmpty()) {
/* 441 */       $$0.swing(InteractionHand.OFF_HAND);
/* 442 */       for (ItemStack $$3 : $$1) {
/* 443 */         BehaviorUtils.throwItem((LivingEntity)$$0, $$3, $$2.add(0.0D, 1.0D, 0.0D));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<ItemStack> getBarterResponseItems(Piglin $$0) {
/* 449 */     LootTable $$1 = $$0.level().getServer().getLootData().getLootTable(BuiltInLootTables.PIGLIN_BARTERING);
/* 450 */     return (List<ItemStack>)$$1.getRandomItems((new LootParams.Builder((ServerLevel)$$0.level()))
/* 451 */         .withParameter(LootContextParams.THIS_ENTITY, $$0)
/* 452 */         .create(LootContextParamSets.PIGLIN_BARTER));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean wantsToDance(LivingEntity $$0, LivingEntity $$1) {
/* 457 */     if ($$1.getType() != EntityType.HOGLIN) {
/* 458 */       return false;
/*     */     }
/*     */     
/* 461 */     return (RandomSource.create($$0.level().getGameTime()).nextFloat() < 0.1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean wantsToPickup(Piglin $$0, ItemStack $$1) {
/* 470 */     if ($$0.isBaby() && $$1.is(ItemTags.IGNORED_BY_PIGLIN_BABIES)) {
/* 471 */       return false;
/*     */     }
/*     */     
/* 474 */     if ($$1.is(ItemTags.PIGLIN_REPELLENTS)) {
/* 475 */       return false;
/*     */     }
/* 477 */     if (isAdmiringDisabled($$0) && $$0.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
/* 478 */       return false;
/*     */     }
/* 480 */     if (isBarterCurrency($$1)) {
/* 481 */       return isNotHoldingLovedItemInOffHand($$0);
/*     */     }
/*     */     
/* 484 */     boolean $$2 = $$0.canAddToInventory($$1);
/* 485 */     if ($$1.is(Items.GOLD_NUGGET)) {
/* 486 */       return $$2;
/*     */     }
/* 488 */     if (isFood($$1)) {
/* 489 */       return (!hasEatenRecently($$0) && $$2);
/*     */     }
/* 491 */     if (isLovedItem($$1)) {
/* 492 */       return (isNotHoldingLovedItemInOffHand($$0) && $$2);
/*     */     }
/* 494 */     return $$0.canReplaceCurrentItem($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isLovedItem(ItemStack $$0) {
/* 499 */     return $$0.is(ItemTags.PIGLIN_LOVED);
/*     */   }
/*     */   
/*     */   private static boolean wantsToStopRiding(Piglin $$0, Entity $$1) {
/* 503 */     if ($$1 instanceof Mob) { Mob $$2 = (Mob)$$1;
/* 504 */       return (!$$2.isBaby() || 
/* 505 */         !$$2.isAlive() || 
/* 506 */         wasHurtRecently((LivingEntity)$$0) || 
/* 507 */         wasHurtRecently((LivingEntity)$$2) || ($$2 instanceof Piglin && $$2
/* 508 */         .getVehicle() == null)); }
/*     */     
/* 510 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isNearestValidAttackTarget(Piglin $$0, LivingEntity $$1) {
/* 514 */     return findNearestValidAttackTarget($$0)
/* 515 */       .filter($$1 -> ($$1 == $$0))
/* 516 */       .isPresent();
/*     */   }
/*     */   
/*     */   private static boolean isNearZombified(Piglin $$0) {
/* 520 */     Brain<Piglin> $$1 = $$0.getBrain();
/* 521 */     if ($$1.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED)) {
/* 522 */       LivingEntity $$2 = $$1.getMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED).get();
/* 523 */       return $$0.closerThan((Entity)$$2, 6.0D);
/*     */     } 
/* 525 */     return false;
/*     */   }
/*     */   
/*     */   private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Piglin $$0) {
/* 529 */     Brain<Piglin> $$1 = $$0.getBrain();
/*     */     
/* 531 */     if (isNearZombified($$0)) {
/* 532 */       return Optional.empty();
/*     */     }
/*     */     
/* 535 */     Optional<LivingEntity> $$2 = BehaviorUtils.getLivingEntityFromUUIDMemory((LivingEntity)$$0, MemoryModuleType.ANGRY_AT);
/* 536 */     if ($$2.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight((LivingEntity)$$0, $$2.get())) {
/* 537 */       return $$2;
/*     */     }
/*     */     
/* 540 */     if ($$1.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
/* 541 */       Optional<Player> $$3 = $$1.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
/* 542 */       if ($$3.isPresent()) {
/* 543 */         return (Optional)$$3;
/*     */       }
/*     */     } 
/*     */     
/* 547 */     Optional<Mob> $$4 = $$1.getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
/* 548 */     if ($$4.isPresent()) {
/* 549 */       return (Optional)$$4;
/*     */     }
/*     */     
/* 552 */     Optional<Player> $$5 = $$1.getMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
/* 553 */     if ($$5.isPresent() && Sensor.isEntityAttackable((LivingEntity)$$0, (LivingEntity)$$5.get())) {
/* 554 */       return (Optional)$$5;
/*     */     }
/*     */     
/* 557 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public static void angerNearbyPiglins(Player $$0, boolean $$1) {
/* 561 */     List<Piglin> $$2 = $$0.level().getEntitiesOfClass(Piglin.class, $$0.getBoundingBox().inflate(16.0D));
/* 562 */     $$2.stream()
/* 563 */       .filter(PiglinAi::isIdle)
/* 564 */       .filter($$2 -> (!$$0 || BehaviorUtils.canSee((LivingEntity)$$2, (LivingEntity)$$1)))
/* 565 */       .forEach($$1 -> {
/*     */           if ($$1.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
/*     */             setAngerTargetToNearestTargetablePlayerIfFound($$1, (LivingEntity)$$0);
/*     */           } else {
/*     */             setAngerTarget($$1, (LivingEntity)$$0);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public static InteractionResult mobInteract(Piglin $$0, Player $$1, InteractionHand $$2) {
/* 575 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 576 */     if (canAdmire($$0, $$3)) {
/* 577 */       ItemStack $$4 = $$3.split(1);
/* 578 */       holdInOffhand($$0, $$4);
/* 579 */       admireGoldItem((LivingEntity)$$0);
/* 580 */       stopWalking($$0);
/*     */       
/* 582 */       return InteractionResult.CONSUME;
/*     */     } 
/* 584 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   protected static boolean canAdmire(Piglin $$0, ItemStack $$1) {
/* 588 */     return (!isAdmiringDisabled($$0) && !isAdmiringItem($$0) && $$0.isAdult() && isBarterCurrency($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void wasHurtBy(Piglin $$0, LivingEntity $$1) {
/* 593 */     if ($$1 instanceof Piglin) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 598 */     if (isHoldingItemInOffHand($$0)) {
/* 599 */       stopHoldingOffHandItem($$0, false);
/*     */     }
/* 601 */     Brain<Piglin> $$2 = $$0.getBrain();
/* 602 */     $$2.eraseMemory(MemoryModuleType.CELEBRATE_LOCATION);
/* 603 */     $$2.eraseMemory(MemoryModuleType.DANCING);
/* 604 */     $$2.eraseMemory(MemoryModuleType.ADMIRING_ITEM);
/*     */     
/* 606 */     if ($$1 instanceof Player)
/*     */     {
/* 608 */       $$2.setMemoryWithExpiry(MemoryModuleType.ADMIRING_DISABLED, Boolean.valueOf(true), 400L);
/*     */     }
/*     */     
/* 611 */     getAvoidTarget($$0).ifPresent($$2 -> {
/*     */           if ($$2.getType() != $$0.getType()) {
/*     */             $$1.eraseMemory(MemoryModuleType.AVOID_TARGET);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 618 */     if ($$0.isBaby()) {
/*     */       
/* 620 */       $$2.setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, $$1, 100L);
/* 621 */       if (Sensor.isEntityAttackableIgnoringLineOfSight((LivingEntity)$$0, $$1)) {
/* 622 */         broadcastAngerTarget($$0, $$1);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 627 */     if ($$1.getType() == EntityType.HOGLIN && hoglinsOutnumberPiglins($$0)) {
/*     */       
/* 629 */       setAvoidTargetAndDontHuntForAWhile($$0, $$1);
/* 630 */       broadcastRetreat($$0, $$1);
/*     */       
/*     */       return;
/*     */     } 
/* 634 */     maybeRetaliate($$0, $$1);
/*     */   }
/*     */   
/*     */   protected static void maybeRetaliate(AbstractPiglin $$0, LivingEntity $$1) {
/* 638 */     if ($$0.getBrain().isActive(Activity.AVOID)) {
/*     */       return;
/*     */     }
/* 641 */     if (!Sensor.isEntityAttackableIgnoringLineOfSight((LivingEntity)$$0, $$1)) {
/*     */       return;
/*     */     }
/* 644 */     if (BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget((LivingEntity)$$0, $$1, 4.0D)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 650 */     if ($$1.getType() == EntityType.PLAYER && $$0.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
/*     */ 
/*     */       
/* 653 */       setAngerTargetToNearestTargetablePlayerIfFound($$0, $$1);
/* 654 */       broadcastUniversalAnger($$0);
/*     */     } else {
/* 656 */       setAngerTarget($$0, $$1);
/* 657 */       broadcastAngerTarget($$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Optional<SoundEvent> getSoundForCurrentActivity(Piglin $$0) {
/* 662 */     return $$0.getBrain().getActiveNonCoreActivity().map($$1 -> getSoundForActivity($$0, $$1));
/*     */   }
/*     */   
/*     */   private static SoundEvent getSoundForActivity(Piglin $$0, Activity $$1) {
/* 666 */     if ($$1 == Activity.FIGHT)
/* 667 */       return SoundEvents.PIGLIN_ANGRY; 
/* 668 */     if ($$0.isConverting())
/* 669 */       return SoundEvents.PIGLIN_RETREAT; 
/* 670 */     if ($$1 == Activity.AVOID && isNearAvoidTarget($$0))
/* 671 */       return SoundEvents.PIGLIN_RETREAT; 
/* 672 */     if ($$1 == Activity.ADMIRE_ITEM)
/* 673 */       return SoundEvents.PIGLIN_ADMIRING_ITEM; 
/* 674 */     if ($$1 == Activity.CELEBRATE)
/* 675 */       return SoundEvents.PIGLIN_CELEBRATE; 
/* 676 */     if (seesPlayerHoldingLovedItem((LivingEntity)$$0))
/* 677 */       return SoundEvents.PIGLIN_JEALOUS; 
/* 678 */     if (isNearRepellent($$0)) {
/* 679 */       return SoundEvents.PIGLIN_RETREAT;
/*     */     }
/* 681 */     return SoundEvents.PIGLIN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isNearAvoidTarget(Piglin $$0) {
/* 686 */     Brain<Piglin> $$1 = $$0.getBrain();
/* 687 */     if (!$$1.hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
/* 688 */       return false;
/*     */     }
/* 690 */     return ((LivingEntity)$$1.getMemory(MemoryModuleType.AVOID_TARGET).get()).closerThan((Entity)$$0, 12.0D);
/*     */   }
/*     */   
/*     */   protected static List<AbstractPiglin> getVisibleAdultPiglins(Piglin $$0) {
/* 694 */     return (List<AbstractPiglin>)$$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS).orElse(ImmutableList.of());
/*     */   }
/*     */   
/*     */   private static List<AbstractPiglin> getAdultPiglins(AbstractPiglin $$0) {
/* 698 */     return (List<AbstractPiglin>)$$0.getBrain().getMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS).orElse(ImmutableList.of());
/*     */   }
/*     */   
/*     */   public static boolean isWearingGold(LivingEntity $$0) {
/* 702 */     Iterable<ItemStack> $$1 = $$0.getArmorSlots();
/* 703 */     for (ItemStack $$2 : $$1) {
/* 704 */       Item $$3 = $$2.getItem();
/* 705 */       if ($$3 instanceof ArmorItem && ((ArmorItem)$$3).getMaterial() == ArmorMaterials.GOLD) {
/* 706 */         return true;
/*     */       }
/*     */     } 
/* 709 */     return false;
/*     */   }
/*     */   
/*     */   private static void stopWalking(Piglin $$0) {
/* 713 */     $$0.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/* 714 */     $$0.getNavigation().stop();
/*     */   }
/*     */   
/*     */   private static BehaviorControl<LivingEntity> babySometimesRideBabyHoglin() {
/* 718 */     SetEntityLookTargetSometimes.Ticker $$0 = new SetEntityLookTargetSometimes.Ticker(RIDE_START_INTERVAL);
/* 719 */     return CopyMemoryWithExpiry.create($$1 -> ($$1.isBaby() && $$0.tickDownAndCheck(($$1.level()).random)), MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.RIDE_TARGET, RIDE_DURATION);
/*     */   }
/*     */   
/*     */   protected static void broadcastAngerTarget(AbstractPiglin $$0, LivingEntity $$1) {
/* 723 */     getAdultPiglins($$0).forEach($$1 -> {
/*     */           if ($$0.getType() == EntityType.HOGLIN && (!$$1.canHunt() || !((Hoglin)$$0).canBeHunted())) {
/*     */             return;
/*     */           }
/*     */           setAngerTargetIfCloserThanCurrent($$1, $$0);
/*     */         });
/*     */   }
/*     */   
/*     */   protected static void broadcastUniversalAnger(AbstractPiglin $$0) {
/* 732 */     getAdultPiglins($$0).forEach($$0 -> getNearestVisibleTargetablePlayer($$0).ifPresent(()));
/*     */   }
/*     */   
/*     */   protected static void setAngerTarget(AbstractPiglin $$0, LivingEntity $$1) {
/* 736 */     if (!Sensor.isEntityAttackableIgnoringLineOfSight((LivingEntity)$$0, $$1)) {
/*     */       return;
/*     */     }
/*     */     
/* 740 */     $$0.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
/* 741 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, $$1.getUUID(), 600L);
/* 742 */     if ($$1.getType() == EntityType.HOGLIN && $$0.canHunt()) {
/* 743 */       dontKillAnyMoreHoglinsForAWhile($$0);
/*     */     }
/* 745 */     if ($$1.getType() == EntityType.PLAYER && $$0.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
/* 746 */       $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, Boolean.valueOf(true), 600L);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void setAngerTargetToNearestTargetablePlayerIfFound(AbstractPiglin $$0, LivingEntity $$1) {
/* 751 */     Optional<Player> $$2 = getNearestVisibleTargetablePlayer($$0);
/* 752 */     if ($$2.isPresent()) {
/* 753 */       setAngerTarget($$0, (LivingEntity)$$2.get());
/*     */     } else {
/* 755 */       setAngerTarget($$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setAngerTargetIfCloserThanCurrent(AbstractPiglin $$0, LivingEntity $$1) {
/* 760 */     Optional<LivingEntity> $$2 = getAngerTarget($$0);
/* 761 */     LivingEntity $$3 = BehaviorUtils.getNearestTarget((LivingEntity)$$0, $$2, $$1);
/* 762 */     if ($$2.isPresent() && $$2.get() == $$3) {
/*     */       return;
/*     */     }
/* 765 */     setAngerTarget($$0, $$3);
/*     */   }
/*     */   
/*     */   private static Optional<LivingEntity> getAngerTarget(AbstractPiglin $$0) {
/* 769 */     return BehaviorUtils.getLivingEntityFromUUIDMemory((LivingEntity)$$0, MemoryModuleType.ANGRY_AT);
/*     */   }
/*     */   
/*     */   public static Optional<LivingEntity> getAvoidTarget(Piglin $$0) {
/* 773 */     if ($$0.getBrain().hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
/* 774 */       return $$0.getBrain().getMemory(MemoryModuleType.AVOID_TARGET);
/*     */     }
/* 776 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public static Optional<Player> getNearestVisibleTargetablePlayer(AbstractPiglin $$0) {
/* 780 */     if ($$0.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)) {
/* 781 */       return $$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
/*     */     }
/* 783 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   private static void broadcastRetreat(Piglin $$0, LivingEntity $$1) {
/* 787 */     getVisibleAdultPiglins($$0).stream()
/* 788 */       .filter($$0 -> $$0 instanceof Piglin)
/* 789 */       .forEach($$1 -> retreatFromNearestTarget((Piglin)$$1, $$0));
/*     */   }
/*     */   
/*     */   private static void retreatFromNearestTarget(Piglin $$0, LivingEntity $$1) {
/* 793 */     Brain<Piglin> $$2 = $$0.getBrain();
/* 794 */     LivingEntity $$3 = $$1;
/* 795 */     $$3 = BehaviorUtils.getNearestTarget((LivingEntity)$$0, $$2.getMemory(MemoryModuleType.AVOID_TARGET), $$3);
/* 796 */     $$3 = BehaviorUtils.getNearestTarget((LivingEntity)$$0, $$2.getMemory(MemoryModuleType.ATTACK_TARGET), $$3);
/* 797 */     setAvoidTargetAndDontHuntForAWhile($$0, $$3);
/*     */   }
/*     */   
/*     */   private static boolean wantsToStopFleeing(Piglin $$0) {
/* 801 */     Brain<Piglin> $$1 = $$0.getBrain();
/* 802 */     if (!$$1.hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
/* 803 */       return true;
/*     */     }
/* 805 */     LivingEntity $$2 = $$1.getMemory(MemoryModuleType.AVOID_TARGET).get();
/* 806 */     EntityType<?> $$3 = $$2.getType();
/*     */     
/* 808 */     if ($$3 == EntityType.HOGLIN) {
/* 809 */       return piglinsEqualOrOutnumberHoglins($$0);
/*     */     }
/* 811 */     if (isZombified($$3)) {
/* 812 */       return !$$1.isMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, $$2);
/*     */     }
/* 814 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean piglinsEqualOrOutnumberHoglins(Piglin $$0) {
/* 818 */     return !hoglinsOutnumberPiglins($$0);
/*     */   }
/*     */   
/*     */   private static boolean hoglinsOutnumberPiglins(Piglin $$0) {
/* 822 */     int $$1 = ((Integer)$$0.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT).orElse(Integer.valueOf(0))).intValue() + 1;
/* 823 */     int $$2 = ((Integer)$$0.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(Integer.valueOf(0))).intValue();
/* 824 */     return ($$2 > $$1);
/*     */   }
/*     */   
/*     */   private static void setAvoidTargetAndDontHuntForAWhile(Piglin $$0, LivingEntity $$1) {
/* 828 */     $$0.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
/* 829 */     $$0.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
/* 830 */     $$0.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/* 831 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, $$1, RETREAT_DURATION.sample(($$0.level()).random));
/* 832 */     dontKillAnyMoreHoglinsForAWhile($$0);
/*     */   }
/*     */   
/*     */   protected static void dontKillAnyMoreHoglinsForAWhile(AbstractPiglin $$0) {
/* 836 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.HUNTED_RECENTLY, Boolean.valueOf(true), TIME_BETWEEN_HUNTS.sample(($$0.level()).random));
/*     */   }
/*     */   
/*     */   private static void eat(Piglin $$0) {
/* 840 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.ATE_RECENTLY, Boolean.valueOf(true), 200L);
/*     */   }
/*     */   
/*     */   private static Vec3 getRandomNearbyPos(Piglin $$0) {
/* 844 */     Vec3 $$1 = LandRandomPos.getPos((PathfinderMob)$$0, 4, 2);
/* 845 */     return ($$1 == null) ? $$0.position() : $$1;
/*     */   }
/*     */   
/*     */   private static boolean hasEatenRecently(Piglin $$0) {
/* 849 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.ATE_RECENTLY);
/*     */   }
/*     */   
/*     */   protected static boolean isIdle(AbstractPiglin $$0) {
/* 853 */     return $$0.getBrain().isActive(Activity.IDLE);
/*     */   }
/*     */   
/*     */   private static boolean hasCrossbow(LivingEntity $$0) {
/* 857 */     return $$0.isHolding(Items.CROSSBOW);
/*     */   }
/*     */   
/*     */   private static void admireGoldItem(LivingEntity $$0) {
/* 861 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.ADMIRING_ITEM, Boolean.valueOf(true), 119L);
/*     */   }
/*     */   
/*     */   private static boolean isAdmiringItem(Piglin $$0) {
/* 865 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_ITEM);
/*     */   }
/*     */   
/*     */   private static boolean isBarterCurrency(ItemStack $$0) {
/* 869 */     return $$0.is(BARTERING_ITEM);
/*     */   }
/*     */   
/*     */   private static boolean isFood(ItemStack $$0) {
/* 873 */     return $$0.is(ItemTags.PIGLIN_FOOD);
/*     */   }
/*     */   
/*     */   private static boolean isNearRepellent(Piglin $$0) {
/* 877 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_REPELLENT);
/*     */   }
/*     */   
/*     */   private static boolean seesPlayerHoldingLovedItem(LivingEntity $$0) {
/* 881 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
/*     */   }
/*     */   
/*     */   private static boolean doesntSeeAnyPlayerHoldingLovedItem(LivingEntity $$0) {
/* 885 */     return !seesPlayerHoldingLovedItem($$0);
/*     */   }
/*     */   
/*     */   public static boolean isPlayerHoldingLovedItem(LivingEntity $$0) {
/* 889 */     return ($$0.getType() == EntityType.PLAYER && $$0.isHolding(PiglinAi::isLovedItem));
/*     */   }
/*     */   
/*     */   private static boolean isAdmiringDisabled(Piglin $$0) {
/* 893 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_DISABLED);
/*     */   }
/*     */   
/*     */   private static boolean wasHurtRecently(LivingEntity $$0) {
/* 897 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
/*     */   }
/*     */   
/*     */   private static boolean isHoldingItemInOffHand(Piglin $$0) {
/* 901 */     return !$$0.getOffhandItem().isEmpty();
/*     */   }
/*     */   
/*     */   private static boolean isNotHoldingLovedItemInOffHand(Piglin $$0) {
/* 905 */     return ($$0.getOffhandItem().isEmpty() || !isLovedItem($$0.getOffhandItem()));
/*     */   }
/*     */   
/*     */   public static boolean isZombified(EntityType<?> $$0) {
/* 909 */     return ($$0 == EntityType.ZOMBIFIED_PIGLIN || $$0 == EntityType.ZOGLIN);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\PiglinAi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */