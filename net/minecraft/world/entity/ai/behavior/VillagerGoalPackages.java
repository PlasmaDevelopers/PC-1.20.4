/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.entity.npc.VillagerProfession;
/*     */ import net.minecraft.world.entity.raid.Raid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VillagerGoalPackages
/*     */ {
/*     */   private static final float STROLL_SPEED_MODIFIER = 0.4F;
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getCorePackage(VillagerProfession $$0, float $$1) {
/*  37 */     return ImmutableList.of(
/*  38 */         Pair.of(Integer.valueOf(0), new Swim(0.8F)), 
/*  39 */         Pair.of(Integer.valueOf(0), InteractWithDoor.create()), 
/*  40 */         Pair.of(Integer.valueOf(0), new LookAtTargetSink(45, 90)), 
/*  41 */         Pair.of(Integer.valueOf(0), new VillagerPanicTrigger()), 
/*  42 */         Pair.of(Integer.valueOf(0), WakeUp.create()), 
/*  43 */         Pair.of(Integer.valueOf(0), ReactToBell.create()), 
/*  44 */         Pair.of(Integer.valueOf(0), SetRaidStatus.create()), 
/*  45 */         Pair.of(Integer.valueOf(0), ValidateNearbyPoi.create($$0.heldJobSite(), MemoryModuleType.JOB_SITE)), 
/*  46 */         Pair.of(Integer.valueOf(0), ValidateNearbyPoi.create($$0.acquirableJobSite(), MemoryModuleType.POTENTIAL_JOB_SITE)), 
/*  47 */         Pair.of(Integer.valueOf(1), new MoveToTargetSink()), 
/*  48 */         Pair.of(Integer.valueOf(2), PoiCompetitorScan.create()), 
/*  49 */         Pair.of(Integer.valueOf(3), new LookAndFollowTradingPlayerSink($$1)), (Object[])new Pair[] {
/*  50 */           Pair.of(Integer.valueOf(5), GoToWantedItem.create($$1, false, 4)), 
/*     */           
/*  52 */           Pair.of(Integer.valueOf(6), AcquirePoi.create($$0.acquirableJobSite(), MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, true, Optional.empty())), 
/*  53 */           Pair.of(Integer.valueOf(7), new GoToPotentialJobSite($$1)), 
/*  54 */           Pair.of(Integer.valueOf(8), YieldJobSite.create($$1)), 
/*  55 */           Pair.of(Integer.valueOf(10), AcquirePoi.create($$0 -> $$0.is(PoiTypes.HOME), MemoryModuleType.HOME, false, Optional.of(Byte.valueOf((byte)14)))), 
/*  56 */           Pair.of(Integer.valueOf(10), AcquirePoi.create($$0 -> $$0.is(PoiTypes.MEETING), MemoryModuleType.MEETING_POINT, true, Optional.of(Byte.valueOf((byte)14)))), 
/*  57 */           Pair.of(Integer.valueOf(10), AssignProfessionFromJobSite.create()), 
/*  58 */           Pair.of(Integer.valueOf(10), ResetProfession.create())
/*     */         });
/*     */   }
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getWorkPackage(VillagerProfession $$0, float $$1) {
/*     */     WorkAtPoi $$3;
/*  64 */     if ($$0 == VillagerProfession.FARMER) {
/*  65 */       WorkAtPoi $$2 = new WorkAtComposter();
/*     */     } else {
/*  67 */       $$3 = new WorkAtPoi();
/*     */     } 
/*     */     
/*  70 */     return ImmutableList.of(
/*  71 */         getMinimalLookBehavior(), 
/*  72 */         Pair.of(Integer.valueOf(5), new RunOne<>((List<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>)ImmutableList.of(
/*  73 */               Pair.of($$3, Integer.valueOf(7)), 
/*  74 */               Pair.of(StrollAroundPoi.create(MemoryModuleType.JOB_SITE, 0.4F, 4), Integer.valueOf(2)), 
/*  75 */               Pair.of(StrollToPoi.create(MemoryModuleType.JOB_SITE, 0.4F, 1, 10), Integer.valueOf(5)), 
/*  76 */               Pair.of(StrollToPoiList.create(MemoryModuleType.SECONDARY_JOB_SITE, $$1, 1, 6, MemoryModuleType.JOB_SITE), Integer.valueOf(5)), 
/*  77 */               Pair.of(new HarvestFarmland(), Integer.valueOf(($$0 == VillagerProfession.FARMER) ? 2 : 5)), 
/*  78 */               Pair.of(new UseBonemeal(), Integer.valueOf(($$0 == VillagerProfession.FARMER) ? 4 : 7))))), 
/*     */         
/*  80 */         Pair.of(Integer.valueOf(10), new ShowTradesToPlayer(400, 1600)), 
/*  81 */         Pair.of(Integer.valueOf(10), SetLookAndInteract.create(EntityType.PLAYER, 4)), 
/*  82 */         Pair.of(Integer.valueOf(2), SetWalkTargetFromBlockMemory.create(MemoryModuleType.JOB_SITE, $$1, 9, 100, 1200)), 
/*  83 */         Pair.of(Integer.valueOf(3), new GiveGiftToHero(100)), 
/*  84 */         Pair.of(Integer.valueOf(99), UpdateActivityFromSchedule.create()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getPlayPackage(float $$0) {
/*  89 */     return ImmutableList.of(
/*  90 */         Pair.of(Integer.valueOf(0), new MoveToTargetSink(80, 120)), 
/*  91 */         getFullLookBehavior(), 
/*  92 */         Pair.of(Integer.valueOf(5), PlayTagWithOtherKids.create()), 
/*  93 */         Pair.of(Integer.valueOf(5), new RunOne<>(
/*  94 */             (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryStatus.VALUE_ABSENT), 
/*     */ 
/*     */ 
/*     */             
/*  98 */             (List<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>)ImmutableList.of(
/*  99 */               Pair.of(InteractWith.of(EntityType.VILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, $$0, 2), Integer.valueOf(2)), 
/* 100 */               Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, $$0, 2), Integer.valueOf(1)), 
/* 101 */               Pair.of(VillageBoundRandomStroll.create($$0), Integer.valueOf(1)), 
/* 102 */               Pair.of(SetWalkTargetFromLookTarget.create($$0, 2), Integer.valueOf(1)), 
/* 103 */               Pair.of(new JumpOnBed($$0), Integer.valueOf(2)), 
/* 104 */               Pair.of(new DoNothing(20, 40), Integer.valueOf(2))))), 
/*     */ 
/*     */         
/* 107 */         Pair.of(Integer.valueOf(99), UpdateActivityFromSchedule.create()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getRestPackage(VillagerProfession $$0, float $$1) {
/* 112 */     return ImmutableList.of(
/* 113 */         Pair.of(Integer.valueOf(2), SetWalkTargetFromBlockMemory.create(MemoryModuleType.HOME, $$1, 1, 150, 1200)), 
/* 114 */         Pair.of(Integer.valueOf(3), ValidateNearbyPoi.create($$0 -> $$0.is(PoiTypes.HOME), MemoryModuleType.HOME)), 
/* 115 */         Pair.of(Integer.valueOf(3), new SleepInBed()), 
/* 116 */         Pair.of(Integer.valueOf(5), new RunOne<>(
/* 117 */             (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT), 
/*     */ 
/*     */ 
/*     */             
/* 121 */             (List<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 122 */               Pair.of(SetClosestHomeAsWalkTarget.create($$1), Integer.valueOf(1)), 
/* 123 */               Pair.of(InsideBrownianWalk.create($$1), Integer.valueOf(4)), 
/* 124 */               Pair.of(GoToClosestVillage.create($$1, 4), Integer.valueOf(2)), 
/* 125 */               Pair.of(new DoNothing(20, 40), Integer.valueOf(2))))), 
/*     */ 
/*     */         
/* 128 */         getMinimalLookBehavior(), 
/* 129 */         Pair.of(Integer.valueOf(99), UpdateActivityFromSchedule.create()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getMeetPackage(VillagerProfession $$0, float $$1) {
/* 134 */     return ImmutableList.of(
/* 135 */         Pair.of(Integer.valueOf(2), TriggerGate.triggerOneShuffled((List<Pair<? extends Trigger<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 136 */               Pair.of(StrollAroundPoi.create(MemoryModuleType.MEETING_POINT, 0.4F, 40), Integer.valueOf(2)), 
/* 137 */               Pair.of(SocializeAtBell.create(), Integer.valueOf(2))))), 
/*     */         
/* 139 */         Pair.of(Integer.valueOf(10), new ShowTradesToPlayer(400, 1600)), 
/* 140 */         Pair.of(Integer.valueOf(10), SetLookAndInteract.create(EntityType.PLAYER, 4)), 
/* 141 */         Pair.of(Integer.valueOf(2), SetWalkTargetFromBlockMemory.create(MemoryModuleType.MEETING_POINT, $$1, 6, 100, 200)), 
/* 142 */         Pair.of(Integer.valueOf(3), new GiveGiftToHero(100)), 
/* 143 */         Pair.of(Integer.valueOf(3), ValidateNearbyPoi.create($$0 -> $$0.is(PoiTypes.MEETING), MemoryModuleType.MEETING_POINT)), 
/* 144 */         Pair.of(Integer.valueOf(3), new GateBehavior<>(
/* 145 */             (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), 
/* 146 */             (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, 
/*     */ 
/*     */             
/* 149 */             (List<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 150 */               Pair.of(new TradeWithVillager(), Integer.valueOf(1))))), 
/*     */ 
/*     */         
/* 153 */         getFullLookBehavior(), 
/* 154 */         Pair.of(Integer.valueOf(99), UpdateActivityFromSchedule.create()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getIdlePackage(VillagerProfession $$0, float $$1) {
/* 159 */     return ImmutableList.of(
/* 160 */         Pair.of(Integer.valueOf(2), new RunOne<>((List<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 161 */               Pair.of(InteractWith.of(EntityType.VILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, $$1, 2), Integer.valueOf(2)), 
/* 162 */               Pair.of(InteractWith.of(EntityType.VILLAGER, 8, AgeableMob::canBreed, AgeableMob::canBreed, MemoryModuleType.BREED_TARGET, $$1, 2), Integer.valueOf(1)), 
/* 163 */               Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, $$1, 2), Integer.valueOf(1)), 
/* 164 */               Pair.of(VillageBoundRandomStroll.create($$1), Integer.valueOf(1)), 
/* 165 */               Pair.of(SetWalkTargetFromLookTarget.create($$1, 2), Integer.valueOf(1)), 
/* 166 */               Pair.of(new JumpOnBed($$1), Integer.valueOf(1)), 
/* 167 */               Pair.of(new DoNothing(30, 60), Integer.valueOf(1))))), 
/*     */         
/* 169 */         Pair.of(Integer.valueOf(3), new GiveGiftToHero(100)), 
/* 170 */         Pair.of(Integer.valueOf(3), SetLookAndInteract.create(EntityType.PLAYER, 4)), 
/* 171 */         Pair.of(Integer.valueOf(3), new ShowTradesToPlayer(400, 1600)), 
/* 172 */         Pair.of(Integer.valueOf(3), new GateBehavior<>(
/* 173 */             (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), 
/* 174 */             (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, 
/*     */ 
/*     */             
/* 177 */             (List<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 178 */               Pair.of(new TradeWithVillager(), Integer.valueOf(1))))), 
/*     */ 
/*     */         
/* 181 */         Pair.of(Integer.valueOf(3), new GateBehavior<>(
/* 182 */             (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), 
/* 183 */             (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.BREED_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, 
/*     */ 
/*     */             
/* 186 */             (List<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 187 */               Pair.of(new VillagerMakeLove(), Integer.valueOf(1))))), 
/*     */ 
/*     */         
/* 190 */         getFullLookBehavior(), 
/* 191 */         Pair.of(Integer.valueOf(99), UpdateActivityFromSchedule.create()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getPanicPackage(VillagerProfession $$0, float $$1) {
/* 196 */     float $$2 = $$1 * 1.5F;
/*     */     
/* 198 */     return ImmutableList.of(
/* 199 */         Pair.of(Integer.valueOf(0), VillagerCalmDown.create()), 
/* 200 */         Pair.of(Integer.valueOf(1), SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_HOSTILE, $$2, 6, false)), 
/* 201 */         Pair.of(Integer.valueOf(1), SetWalkTargetAwayFrom.entity(MemoryModuleType.HURT_BY_ENTITY, $$2, 6, false)), 
/* 202 */         Pair.of(Integer.valueOf(3), VillageBoundRandomStroll.create($$2, 2, 2)), 
/* 203 */         getMinimalLookBehavior());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getPreRaidPackage(VillagerProfession $$0, float $$1) {
/* 208 */     return ImmutableList.of(
/* 209 */         Pair.of(Integer.valueOf(0), RingBell.create()), 
/* 210 */         Pair.of(Integer.valueOf(0), TriggerGate.triggerOneShuffled((List<Pair<? extends Trigger<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 211 */               Pair.of(SetWalkTargetFromBlockMemory.create(MemoryModuleType.MEETING_POINT, $$1 * 1.5F, 2, 150, 200), Integer.valueOf(6)), 
/* 212 */               Pair.of(VillageBoundRandomStroll.create($$1 * 1.5F), Integer.valueOf(2))))), 
/*     */         
/* 214 */         getMinimalLookBehavior(), 
/* 215 */         Pair.of(Integer.valueOf(99), ResetRaidStatus.create()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getRaidPackage(VillagerProfession $$0, float $$1) {
/* 220 */     return ImmutableList.of(
/* 221 */         Pair.of(Integer.valueOf(0), BehaviorBuilder.sequence(
/* 222 */             BehaviorBuilder.triggerIf(VillagerGoalPackages::raidExistsAndNotVictory), 
/* 223 */             TriggerGate.triggerOneShuffled((List<Pair<? extends Trigger<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 224 */                 Pair.of(MoveToSkySeeingSpot.create($$1), Integer.valueOf(5)), 
/* 225 */                 Pair.of(VillageBoundRandomStroll.create($$1 * 1.1F), Integer.valueOf(2)))))), 
/*     */ 
/*     */         
/* 228 */         Pair.of(Integer.valueOf(0), new CelebrateVillagersSurvivedRaid(600, 600)), 
/* 229 */         Pair.of(Integer.valueOf(2), BehaviorBuilder.sequence(
/* 230 */             BehaviorBuilder.triggerIf(VillagerGoalPackages::raidExistsAndActive), 
/* 231 */             LocateHidingPlace.create(24, $$1 * 1.4F, 1))), 
/*     */         
/* 233 */         getMinimalLookBehavior(), 
/* 234 */         Pair.of(Integer.valueOf(99), ResetRaidStatus.create()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getHidePackage(VillagerProfession $$0, float $$1) {
/* 239 */     int $$2 = 2;
/* 240 */     return ImmutableList.of(
/* 241 */         Pair.of(Integer.valueOf(0), SetHiddenState.create(15, 3)), 
/* 242 */         Pair.of(Integer.valueOf(1), LocateHidingPlace.create(32, $$1 * 1.25F, 2)), 
/* 243 */         getMinimalLookBehavior());
/*     */   }
/*     */ 
/*     */   
/*     */   private static Pair<Integer, BehaviorControl<LivingEntity>> getFullLookBehavior() {
/* 248 */     return Pair.of(Integer.valueOf(5), new RunOne<>((List<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 249 */             Pair.of(SetEntityLookTarget.create(EntityType.CAT, 8.0F), Integer.valueOf(8)), 
/* 250 */             Pair.of(SetEntityLookTarget.create(EntityType.VILLAGER, 8.0F), Integer.valueOf(2)), 
/* 251 */             Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), Integer.valueOf(2)), 
/* 252 */             Pair.of(SetEntityLookTarget.create(MobCategory.CREATURE, 8.0F), Integer.valueOf(1)), 
/* 253 */             Pair.of(SetEntityLookTarget.create(MobCategory.WATER_CREATURE, 8.0F), Integer.valueOf(1)), 
/*     */             
/* 255 */             Pair.of(SetEntityLookTarget.create(MobCategory.AXOLOTLS, 8.0F), Integer.valueOf(1)), 
/* 256 */             Pair.of(SetEntityLookTarget.create(MobCategory.UNDERGROUND_WATER_CREATURE, 8.0F), Integer.valueOf(1)), 
/* 257 */             Pair.of(SetEntityLookTarget.create(MobCategory.WATER_AMBIENT, 8.0F), Integer.valueOf(1)), 
/* 258 */             Pair.of(SetEntityLookTarget.create(MobCategory.MONSTER, 8.0F), Integer.valueOf(1)), 
/* 259 */             Pair.of(new DoNothing(30, 60), Integer.valueOf(2)))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static Pair<Integer, BehaviorControl<LivingEntity>> getMinimalLookBehavior() {
/* 264 */     return Pair.of(Integer.valueOf(5), new RunOne<>((List<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>)ImmutableList.of(
/* 265 */             Pair.of(SetEntityLookTarget.create(EntityType.VILLAGER, 8.0F), Integer.valueOf(2)), 
/* 266 */             Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), Integer.valueOf(2)), 
/* 267 */             Pair.of(new DoNothing(30, 60), Integer.valueOf(8)))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean raidExistsAndActive(ServerLevel $$0, LivingEntity $$1) {
/* 272 */     Raid $$2 = $$0.getRaidAt($$1.blockPosition());
/* 273 */     return ($$2 != null && $$2.isActive() && !$$2.isVictory() && !$$2.isLoss());
/*     */   }
/*     */   
/*     */   private static boolean raidExistsAndNotVictory(ServerLevel $$0, LivingEntity $$1) {
/* 277 */     Raid $$2 = $$0.getRaidAt($$1.blockPosition());
/* 278 */     return ($$2 != null && $$2.isVictory());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\VillagerGoalPackages.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */