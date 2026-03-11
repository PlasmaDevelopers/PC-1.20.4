/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class PlayTagWithOtherKids
/*     */ {
/*     */   private static final int MAX_FLEE_XZ_DIST = 20;
/*     */   private static final int MAX_FLEE_Y_DIST = 8;
/*     */   private static final float FLEE_SPEED_MODIFIER = 0.6F;
/*     */   private static final float CHASE_SPEED_MODIFIER = 0.6F;
/*     */   private static final int MAX_CHASERS_PER_TARGET = 5;
/*     */   private static final int AVERAGE_WAIT_TIME_BETWEEN_RUNS = 10;
/*     */   
/*     */   public static BehaviorControl<PathfinderMob> create() {
/*  34 */     return BehaviorBuilder.create($$0 -> $$0.group((App)$$0.present(MemoryModuleType.VISIBLE_VILLAGER_BABIES), (App)$$0.absent(MemoryModuleType.WALK_TARGET), (App)$$0.registered(MemoryModuleType.LOOK_TARGET), (App)$$0.registered(MemoryModuleType.INTERACTION_TARGET)).apply((Applicative)$$0, ()));
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
/*     */   private static void chaseKid(MemoryAccessor<?, LivingEntity> $$0, MemoryAccessor<?, PositionTracker> $$1, MemoryAccessor<?, WalkTarget> $$2, LivingEntity $$3) {
/*  76 */     $$0.set($$3);
/*  77 */     $$1.set(new EntityTracker((Entity)$$3, true));
/*  78 */     $$2.set(new WalkTarget(new EntityTracker((Entity)$$3, false), 0.6F, 1));
/*     */   }
/*     */ 
/*     */   
/*     */   private static Optional<LivingEntity> findSomeoneBeingChased(List<LivingEntity> $$0) {
/*  83 */     Map<LivingEntity, Integer> $$1 = checkHowManyChasersEachFriendHas($$0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     return $$1.entrySet().stream()
/*  89 */       .sorted(Comparator.comparingInt(Map.Entry::getValue))
/*  90 */       .filter($$0 -> (((Integer)$$0.getValue()).intValue() > 0 && ((Integer)$$0.getValue()).intValue() <= 5))
/*  91 */       .map(Map.Entry::getKey)
/*  92 */       .findFirst();
/*     */   }
/*     */   
/*     */   private static Map<LivingEntity, Integer> checkHowManyChasersEachFriendHas(List<LivingEntity> $$0) {
/*  96 */     Map<LivingEntity, Integer> $$1 = Maps.newHashMap();
/*     */     
/*  98 */     $$0.stream()
/*  99 */       .filter(PlayTagWithOtherKids::isChasingSomeone)
/* 100 */       .forEach($$1 -> $$0.compute(whoAreYouChasing($$1), ()));
/*     */ 
/*     */ 
/*     */     
/* 104 */     return $$1;
/*     */   }
/*     */   
/*     */   private static LivingEntity whoAreYouChasing(LivingEntity $$0) {
/* 108 */     return $$0.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
/*     */   }
/*     */   
/*     */   private static boolean isChasingSomeone(LivingEntity $$0) {
/* 112 */     return $$0.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
/*     */   }
/*     */   
/*     */   private static boolean isFriendChasingMe(LivingEntity $$0, LivingEntity $$1) {
/* 116 */     return $$1.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET)
/* 117 */       .filter($$1 -> ($$1 == $$0))
/* 118 */       .isPresent();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\PlayTagWithOtherKids.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */