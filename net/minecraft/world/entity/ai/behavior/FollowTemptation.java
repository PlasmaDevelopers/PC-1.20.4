/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class FollowTemptation
/*    */   extends Behavior<PathfinderMob> {
/*    */   public static final int TEMPTATION_COOLDOWN = 100;
/*    */   public static final double DEFAULT_CLOSE_ENOUGH_DIST = 2.5D;
/*    */   public static final double BACKED_UP_CLOSE_ENOUGH_DIST = 3.5D;
/*    */   private final Function<LivingEntity, Float> speedModifier;
/*    */   private final Function<LivingEntity, Double> closeEnoughDistance;
/*    */   
/*    */   public FollowTemptation(Function<LivingEntity, Float> $$0) {
/* 28 */     this($$0, $$0 -> Double.valueOf(2.5D));
/*    */   }
/*    */   
/*    */   public FollowTemptation(Function<LivingEntity, Float> $$0, Function<LivingEntity, Double> $$1) {
/* 32 */     super((Map<MemoryModuleType<?>, MemoryStatus>)Util.make(() -> {
/*    */             ImmutableMap.Builder<MemoryModuleType<?>, MemoryStatus> $$0 = ImmutableMap.builder();
/*    */             $$0.put(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED);
/*    */             $$0.put(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED);
/*    */             $$0.put(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT);
/*    */             $$0.put(MemoryModuleType.IS_TEMPTED, MemoryStatus.REGISTERED);
/*    */             $$0.put(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_PRESENT);
/*    */             $$0.put(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT);
/*    */             $$0.put(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT);
/*    */             return $$0.build();
/*    */           }));
/* 43 */     this.speedModifier = $$0;
/* 44 */     this.closeEnoughDistance = $$1;
/*    */   }
/*    */   
/*    */   protected float getSpeedModifier(PathfinderMob $$0) {
/* 48 */     return ((Float)this.speedModifier.apply($$0)).floatValue();
/*    */   }
/*    */   
/*    */   private Optional<Player> getTemptingPlayer(PathfinderMob $$0) {
/* 52 */     return $$0.getBrain().getMemory(MemoryModuleType.TEMPTING_PLAYER);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean timedOut(long $$0) {
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/* 62 */     return (getTemptingPlayer($$1).isPresent() && !$$1.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET) && !$$1.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/* 67 */     $$1.getBrain().setMemory(MemoryModuleType.IS_TEMPTED, Boolean.valueOf(true));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/* 72 */     Brain<?> $$3 = $$1.getBrain();
/* 73 */     $$3.setMemory(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, Integer.valueOf(100));
/* 74 */     $$3.setMemory(MemoryModuleType.IS_TEMPTED, Boolean.valueOf(false));
/* 75 */     $$3.eraseMemory(MemoryModuleType.WALK_TARGET);
/* 76 */     $$3.eraseMemory(MemoryModuleType.LOOK_TARGET);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/* 81 */     Player $$3 = getTemptingPlayer($$1).get();
/* 82 */     Brain<?> $$4 = $$1.getBrain();
/* 83 */     $$4.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker((Entity)$$3, true));
/* 84 */     double $$5 = ((Double)this.closeEnoughDistance.apply($$1)).doubleValue();
/* 85 */     if ($$1.distanceToSqr((Entity)$$3) < Mth.square($$5)) {
/* 86 */       $$4.eraseMemory(MemoryModuleType.WALK_TARGET);
/*    */     } else {
/* 88 */       $$4.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker((Entity)$$3, false), getSpeedModifier($$1), 2));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\FollowTemptation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */