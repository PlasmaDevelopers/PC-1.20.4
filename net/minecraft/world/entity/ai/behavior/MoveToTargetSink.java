/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MoveToTargetSink
/*     */   extends Behavior<Mob>
/*     */ {
/*     */   private static final int MAX_COOLDOWN_BEFORE_RETRYING = 40;
/*     */   private int remainingCooldown;
/*     */   @Nullable
/*     */   private Path path;
/*     */   @Nullable
/*     */   private BlockPos lastTargetPos;
/*     */   private float speedModifier;
/*     */   
/*     */   public MoveToTargetSink() {
/*  40 */     this(150, 250);
/*     */   }
/*     */   
/*     */   public MoveToTargetSink(int $$0, int $$1) {
/*  44 */     super(
/*  45 */         (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED, MemoryModuleType.PATH, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT), $$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Mob $$1) {
/*  56 */     if (this.remainingCooldown > 0) {
/*  57 */       this.remainingCooldown--;
/*  58 */       return false;
/*     */     } 
/*     */     
/*  61 */     Brain<?> $$2 = $$1.getBrain();
/*  62 */     WalkTarget $$3 = $$2.getMemory(MemoryModuleType.WALK_TARGET).get();
/*     */     
/*  64 */     boolean $$4 = reachedTarget($$1, $$3);
/*  65 */     if (!$$4 && tryComputePath($$1, $$3, $$0.getGameTime())) {
/*  66 */       this.lastTargetPos = $$3.getTarget().currentBlockPosition();
/*  67 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  72 */     $$2.eraseMemory(MemoryModuleType.WALK_TARGET);
/*  73 */     if ($$4) {
/*  74 */       $$2.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
/*     */     }
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Mob $$1, long $$2) {
/*  81 */     if (this.path == null || this.lastTargetPos == null) {
/*  82 */       return false;
/*     */     }
/*     */     
/*  85 */     Optional<WalkTarget> $$3 = $$1.getBrain().getMemory(MemoryModuleType.WALK_TARGET);
/*  86 */     boolean $$4 = ((Boolean)$$3.<Boolean>map(MoveToTargetSink::isWalkTargetSpectator).orElse(Boolean.valueOf(false))).booleanValue();
/*     */     
/*  88 */     PathNavigation $$5 = $$1.getNavigation();
/*  89 */     return (!$$5.isDone() && $$3.isPresent() && !reachedTarget($$1, $$3.get()) && !$$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Mob $$1, long $$2) {
/*  94 */     if ($$1.getBrain().hasMemoryValue(MemoryModuleType.WALK_TARGET) && !reachedTarget($$1, $$1.getBrain().getMemory(MemoryModuleType.WALK_TARGET).get()) && $$1.getNavigation().isStuck())
/*     */     {
/*  96 */       this.remainingCooldown = $$0.getRandom().nextInt(40);
/*     */     }
/*     */     
/*  99 */     $$1.getNavigation().stop();
/* 100 */     $$1.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/* 101 */     $$1.getBrain().eraseMemory(MemoryModuleType.PATH);
/* 102 */     this.path = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Mob $$1, long $$2) {
/* 107 */     $$1.getBrain().setMemory(MemoryModuleType.PATH, this.path);
/* 108 */     $$1.getNavigation().moveTo(this.path, this.speedModifier);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Mob $$1, long $$2) {
/* 113 */     Path $$3 = $$1.getNavigation().getPath();
/* 114 */     Brain<?> $$4 = $$1.getBrain();
/* 115 */     if (this.path != $$3) {
/* 116 */       this.path = $$3;
/* 117 */       $$4.setMemory(MemoryModuleType.PATH, $$3);
/*     */     } 
/*     */     
/* 120 */     if ($$3 == null || this.lastTargetPos == null) {
/*     */       return;
/*     */     }
/*     */     
/* 124 */     WalkTarget $$5 = $$4.getMemory(MemoryModuleType.WALK_TARGET).get();
/*     */     
/* 126 */     if ($$5.getTarget().currentBlockPosition().distSqr((Vec3i)this.lastTargetPos) > 4.0D && 
/* 127 */       tryComputePath($$1, $$5, $$0.getGameTime())) {
/* 128 */       this.lastTargetPos = $$5.getTarget().currentBlockPosition();
/* 129 */       start($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tryComputePath(Mob $$0, WalkTarget $$1, long $$2) {
/* 136 */     BlockPos $$3 = $$1.getTarget().currentBlockPosition();
/* 137 */     this.path = $$0.getNavigation().createPath($$3, 0);
/* 138 */     this.speedModifier = $$1.getSpeedModifier();
/*     */     
/* 140 */     Brain<?> $$4 = $$0.getBrain();
/* 141 */     if (reachedTarget($$0, $$1)) {
/* 142 */       $$4.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
/*     */     } else {
/* 144 */       boolean $$5 = (this.path != null && this.path.canReach());
/* 145 */       if ($$5) {
/* 146 */         $$4.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
/* 147 */       } else if (!$$4.hasMemoryValue(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
/* 148 */         $$4.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, Long.valueOf($$2));
/*     */       } 
/*     */       
/* 151 */       if (this.path != null) {
/* 152 */         return true;
/*     */       }
/*     */       
/* 155 */       Vec3 $$6 = DefaultRandomPos.getPosTowards((PathfinderMob)$$0, 10, 7, Vec3.atBottomCenterOf((Vec3i)$$3), 1.5707963705062866D);
/* 156 */       if ($$6 != null) {
/* 157 */         this.path = $$0.getNavigation().createPath($$6.x, $$6.y, $$6.z, 0);
/* 158 */         return (this.path != null);
/*     */       } 
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */   
/*     */   private boolean reachedTarget(Mob $$0, WalkTarget $$1) {
/* 165 */     return ($$1.getTarget().currentBlockPosition().distManhattan((Vec3i)$$0.blockPosition()) <= $$1.getCloseEnoughDist());
/*     */   }
/*     */   
/*     */   private static boolean isWalkTargetSpectator(WalkTarget $$0) {
/* 169 */     PositionTracker $$1 = $$0.getTarget();
/*     */     
/* 171 */     if ($$1 instanceof EntityTracker) { EntityTracker $$2 = (EntityTracker)$$1;
/* 172 */       return $$2.getEntity().isSpectator(); }
/*     */     
/* 174 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\MoveToTargetSink.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */