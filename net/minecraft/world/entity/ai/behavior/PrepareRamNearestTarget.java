/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToIntFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ public class PrepareRamNearestTarget<E extends PathfinderMob>
/*     */   extends Behavior<E>
/*     */ {
/*     */   public static final int TIME_OUT_DURATION = 160;
/*     */   private final ToIntFunction<E> getCooldownOnFail;
/*     */   private final int minRamDistance;
/*     */   private final int maxRamDistance;
/*  42 */   private Optional<Long> reachedRamPositionTimestamp = Optional.empty(); private final float walkSpeed; private final TargetingConditions ramTargeting; private final int ramPrepareTime; private final Function<E, SoundEvent> getPrepareRamSound;
/*  43 */   private Optional<RamCandidate> ramCandidate = Optional.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrepareRamNearestTarget(ToIntFunction<E> $$0, int $$1, int $$2, float $$3, TargetingConditions $$4, int $$5, Function<E, SoundEvent> $$6) {
/*  54 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT, MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_ABSENT), 160);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     this.getCooldownOnFail = $$0;
/*  62 */     this.minRamDistance = $$1;
/*  63 */     this.maxRamDistance = $$2;
/*  64 */     this.walkSpeed = $$3;
/*  65 */     this.ramTargeting = $$4;
/*  66 */     this.ramPrepareTime = $$5;
/*  67 */     this.getPrepareRamSound = $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/*  72 */     Brain<?> $$3 = $$1.getBrain();
/*  73 */     $$3.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
/*  74 */       .flatMap($$1 -> $$1.findClosest(()))
/*  75 */       .ifPresent($$1 -> chooseRamPosition($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, E $$1, long $$2) {
/*  80 */     Brain<?> $$3 = $$1.getBrain();
/*  81 */     if (!$$3.hasMemoryValue(MemoryModuleType.RAM_TARGET)) {
/*  82 */       $$0.broadcastEntityEvent((Entity)$$1, (byte)59);
/*  83 */       $$3.setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, Integer.valueOf(this.getCooldownOnFail.applyAsInt($$1)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/*  89 */     return (this.ramCandidate.isPresent() && ((RamCandidate)this.ramCandidate
/*  90 */       .get()).getTarget().isAlive());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, E $$1, long $$2) {
/*  95 */     if (this.ramCandidate.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  99 */     $$1.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(((RamCandidate)this.ramCandidate.get()).getStartPosition(), this.walkSpeed, 0));
/* 100 */     $$1.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker((Entity)((RamCandidate)this.ramCandidate.get()).getTarget(), true));
/*     */     
/* 102 */     boolean $$3 = !((RamCandidate)this.ramCandidate.get()).getTarget().blockPosition().equals(((RamCandidate)this.ramCandidate.get()).getTargetPosition());
/* 103 */     if ($$3) {
/* 104 */       $$0.broadcastEntityEvent((Entity)$$1, (byte)59);
/* 105 */       $$1.getNavigation().stop();
/* 106 */       chooseRamPosition((PathfinderMob)$$1, ((RamCandidate)this.ramCandidate.get()).target);
/*     */     } else {
/* 108 */       BlockPos $$4 = $$1.blockPosition();
/* 109 */       if ($$4.equals(((RamCandidate)this.ramCandidate.get()).getStartPosition())) {
/* 110 */         $$0.broadcastEntityEvent((Entity)$$1, (byte)58);
/* 111 */         if (this.reachedRamPositionTimestamp.isEmpty()) {
/* 112 */           this.reachedRamPositionTimestamp = Optional.of(Long.valueOf($$2));
/*     */         }
/* 114 */         if ($$2 - ((Long)this.reachedRamPositionTimestamp.get()).longValue() >= this.ramPrepareTime) {
/* 115 */           $$1.getBrain().setMemory(MemoryModuleType.RAM_TARGET, getEdgeOfBlock($$4, ((RamCandidate)this.ramCandidate.get()).getTargetPosition()));
/* 116 */           $$0.playSound(null, (Entity)$$1, this.getPrepareRamSound.apply($$1), SoundSource.NEUTRAL, 1.0F, $$1.getVoicePitch());
/* 117 */           this.ramCandidate = Optional.empty();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Vec3 getEdgeOfBlock(BlockPos $$0, BlockPos $$1) {
/* 124 */     double $$2 = 0.5D;
/* 125 */     double $$3 = 0.5D * Mth.sign(($$1.getX() - $$0.getX()));
/* 126 */     double $$4 = 0.5D * Mth.sign(($$1.getZ() - $$0.getZ()));
/*     */     
/* 128 */     return Vec3.atBottomCenterOf((Vec3i)$$1).add($$3, 0.0D, $$4);
/*     */   }
/*     */   
/*     */   private Optional<BlockPos> calculateRammingStartPosition(PathfinderMob $$0, LivingEntity $$1) {
/* 132 */     BlockPos $$2 = $$1.blockPosition();
/* 133 */     if (!isWalkableBlock($$0, $$2)) {
/* 134 */       return Optional.empty();
/*     */     }
/*     */     
/* 137 */     List<BlockPos> $$3 = Lists.newArrayList();
/*     */     
/* 139 */     BlockPos.MutableBlockPos $$4 = $$2.mutable();
/* 140 */     for (Direction $$5 : Direction.Plane.HORIZONTAL) {
/* 141 */       $$4.set((Vec3i)$$2);
/* 142 */       for (int $$6 = 0; $$6 < this.maxRamDistance; $$6++) {
/* 143 */         if (!isWalkableBlock($$0, (BlockPos)$$4.move($$5))) {
/* 144 */           $$4.move($$5.getOpposite());
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 149 */       if ($$4.distManhattan((Vec3i)$$2) >= this.minRamDistance) {
/* 150 */         $$3.add($$4.immutable());
/*     */       }
/*     */     } 
/*     */     
/* 154 */     PathNavigation $$7 = $$0.getNavigation();
/*     */ 
/*     */     
/* 157 */     Objects.requireNonNull($$0.blockPosition()); return $$3.stream().sorted(Comparator.comparingDouble($$0.blockPosition()::distSqr))
/* 158 */       .filter($$1 -> {
/*     */           Path $$2 = $$0.createPath($$1, 0);
/* 160 */           return ($$2 != null && $$2.canReach());
/*     */         
/* 162 */         }).findFirst();
/*     */   }
/*     */   
/*     */   private boolean isWalkableBlock(PathfinderMob $$0, BlockPos $$1) {
/* 166 */     return ($$0.getNavigation().isStableDestination($$1) && $$0
/* 167 */       .getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic((BlockGetter)$$0.level(), $$1.mutable())) == 0.0F);
/*     */   }
/*     */   
/*     */   private void chooseRamPosition(PathfinderMob $$0, LivingEntity $$1) {
/* 171 */     this.reachedRamPositionTimestamp = Optional.empty();
/* 172 */     this
/* 173 */       .ramCandidate = calculateRammingStartPosition($$0, $$1).map($$1 -> new RamCandidate($$1, $$0.blockPosition(), $$0));
/*     */   }
/*     */   
/*     */   public static class RamCandidate {
/*     */     private final BlockPos startPosition;
/*     */     private final BlockPos targetPosition;
/*     */     final LivingEntity target;
/*     */     
/*     */     public RamCandidate(BlockPos $$0, BlockPos $$1, LivingEntity $$2) {
/* 182 */       this.startPosition = $$0;
/* 183 */       this.targetPosition = $$1;
/* 184 */       this.target = $$2;
/*     */     }
/*     */     
/*     */     public BlockPos getStartPosition() {
/* 188 */       return this.startPosition;
/*     */     }
/*     */     
/*     */     public BlockPos getTargetPosition() {
/* 192 */       return this.targetPosition;
/*     */     }
/*     */     
/*     */     public LivingEntity getTarget() {
/* 196 */       return this.target;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\PrepareRamNearestTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */