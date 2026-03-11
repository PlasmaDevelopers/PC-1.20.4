/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JumpOnBed
/*     */   extends Behavior<Mob>
/*     */ {
/*     */   private static final int MAX_TIME_TO_REACH_BED = 100;
/*     */   private static final int MIN_JUMPS = 3;
/*     */   private static final int MAX_JUMPS = 6;
/*     */   private static final int COOLDOWN_BETWEEN_JUMPS = 5;
/*     */   private final float speedModifier;
/*     */   @Nullable
/*     */   private BlockPos targetBed;
/*     */   private int remainingTimeToReachBed;
/*     */   private int remainingJumps;
/*     */   private int remainingCooldownUntilNextJump;
/*     */   
/*     */   public JumpOnBed(float $$0) {
/*  35 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.NEAREST_BED, MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
/*     */ 
/*     */ 
/*     */     
/*  39 */     this.speedModifier = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Mob $$1) {
/*  44 */     return ($$1.isBaby() && nearBed($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Mob $$1, long $$2) {
/*  49 */     super.start($$0, $$1, $$2);
/*     */     
/*  51 */     getNearestBed($$1).ifPresent($$2 -> {
/*     */           this.targetBed = $$2;
/*     */           this.remainingTimeToReachBed = 100;
/*     */           this.remainingJumps = 3 + $$0.random.nextInt(4);
/*     */           this.remainingCooldownUntilNextJump = 0;
/*     */           startWalkingTowardsBed($$1, $$2);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Mob $$1, long $$2) {
/*  62 */     super.stop($$0, $$1, $$2);
/*     */     
/*  64 */     this.targetBed = null;
/*  65 */     this.remainingTimeToReachBed = 0;
/*  66 */     this.remainingJumps = 0;
/*  67 */     this.remainingCooldownUntilNextJump = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Mob $$1, long $$2) {
/*  72 */     return ($$1.isBaby() && this.targetBed != null && 
/*     */       
/*  74 */       isBed($$0, this.targetBed) && 
/*  75 */       !tiredOfWalking($$0, $$1) && 
/*  76 */       !tiredOfJumping($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean timedOut(long $$0) {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Mob $$1, long $$2) {
/*  86 */     if (!onOrOverBed($$0, $$1)) {
/*  87 */       this.remainingTimeToReachBed--;
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     if (this.remainingCooldownUntilNextJump > 0) {
/*  92 */       this.remainingCooldownUntilNextJump--;
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     if (onBedSurface($$0, $$1)) {
/*  97 */       $$1.getJumpControl().jump();
/*  98 */       this.remainingJumps--;
/*  99 */       this.remainingCooldownUntilNextJump = 5;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startWalkingTowardsBed(Mob $$0, BlockPos $$1) {
/* 104 */     $$0.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget($$1, this.speedModifier, 0));
/*     */   }
/*     */   
/*     */   private boolean nearBed(ServerLevel $$0, Mob $$1) {
/* 108 */     return (onOrOverBed($$0, $$1) || getNearestBed($$1).isPresent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean onOrOverBed(ServerLevel $$0, Mob $$1) {
/* 115 */     BlockPos $$2 = $$1.blockPosition();
/* 116 */     BlockPos $$3 = $$2.below();
/* 117 */     return (isBed($$0, $$2) || isBed($$0, $$3));
/*     */   }
/*     */   
/*     */   private boolean onBedSurface(ServerLevel $$0, Mob $$1) {
/* 121 */     return isBed($$0, $$1.blockPosition());
/*     */   }
/*     */   
/*     */   private boolean isBed(ServerLevel $$0, BlockPos $$1) {
/* 125 */     return $$0.getBlockState($$1).is(BlockTags.BEDS);
/*     */   }
/*     */   
/*     */   private Optional<BlockPos> getNearestBed(Mob $$0) {
/* 129 */     return $$0.getBrain().getMemory(MemoryModuleType.NEAREST_BED);
/*     */   }
/*     */   
/*     */   private boolean tiredOfWalking(ServerLevel $$0, Mob $$1) {
/* 133 */     return (!onOrOverBed($$0, $$1) && this.remainingTimeToReachBed <= 0);
/*     */   }
/*     */   
/*     */   private boolean tiredOfJumping(ServerLevel $$0, Mob $$1) {
/* 137 */     return (onOrOverBed($$0, $$1) && this.remainingJumps <= 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\JumpOnBed.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */