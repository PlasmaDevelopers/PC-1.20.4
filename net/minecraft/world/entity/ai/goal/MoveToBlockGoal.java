/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ 
/*     */ public abstract class MoveToBlockGoal
/*     */   extends Goal {
/*     */   private static final int GIVE_UP_TICKS = 1200;
/*     */   private static final int STAY_TICKS = 1200;
/*     */   private static final int INTERVAL_TICKS = 200;
/*     */   protected final PathfinderMob mob;
/*     */   public final double speedModifier;
/*     */   protected int nextStartTick;
/*     */   protected int tryTicks;
/*     */   private int maxStayTicks;
/*  20 */   protected BlockPos blockPos = BlockPos.ZERO;
/*     */   
/*     */   private boolean reachedTarget;
/*     */   private final int searchRange;
/*     */   private final int verticalSearchRange;
/*     */   protected int verticalSearchStart;
/*     */   
/*     */   public MoveToBlockGoal(PathfinderMob $$0, double $$1, int $$2) {
/*  28 */     this($$0, $$1, $$2, 1);
/*     */   }
/*     */   
/*     */   public MoveToBlockGoal(PathfinderMob $$0, double $$1, int $$2, int $$3) {
/*  32 */     this.mob = $$0;
/*  33 */     this.speedModifier = $$1;
/*  34 */     this.searchRange = $$2;
/*  35 */     this.verticalSearchStart = 0;
/*  36 */     this.verticalSearchRange = $$3;
/*  37 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  42 */     if (this.nextStartTick > 0) {
/*  43 */       this.nextStartTick--;
/*  44 */       return false;
/*     */     } 
/*  46 */     this.nextStartTick = nextStartTick(this.mob);
/*  47 */     return findNearestBlock();
/*     */   }
/*     */   
/*     */   protected int nextStartTick(PathfinderMob $$0) {
/*  51 */     return reducedTickDelay(200 + $$0.getRandom().nextInt(200));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  56 */     return (this.tryTicks >= -this.maxStayTicks && this.tryTicks <= 1200 && isValidTarget((LevelReader)this.mob.level(), this.blockPos));
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  61 */     moveMobToBlock();
/*  62 */     this.tryTicks = 0;
/*  63 */     this.maxStayTicks = this.mob.getRandom().nextInt(this.mob.getRandom().nextInt(1200) + 1200) + 1200;
/*     */   }
/*     */   
/*     */   protected void moveMobToBlock() {
/*  67 */     this.mob.getNavigation().moveTo(this.blockPos.getX() + 0.5D, (this.blockPos.getY() + 1), this.blockPos.getZ() + 0.5D, this.speedModifier);
/*     */   }
/*     */   
/*     */   public double acceptedDistance() {
/*  71 */     return 1.0D;
/*     */   }
/*     */   
/*     */   protected BlockPos getMoveToTarget() {
/*  75 */     return this.blockPos.above();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdateEveryTick() {
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  85 */     BlockPos $$0 = getMoveToTarget();
/*  86 */     if (!$$0.closerToCenterThan((Position)this.mob.position(), acceptedDistance())) {
/*  87 */       this.reachedTarget = false;
/*  88 */       this.tryTicks++;
/*  89 */       if (shouldRecalculatePath()) {
/*  90 */         this.mob.getNavigation().moveTo($$0.getX() + 0.5D, $$0.getY(), $$0.getZ() + 0.5D, this.speedModifier);
/*     */       }
/*     */     } else {
/*  93 */       this.reachedTarget = true;
/*  94 */       this.tryTicks--;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldRecalculatePath() {
/*  99 */     return (this.tryTicks % 40 == 0);
/*     */   }
/*     */   
/*     */   protected boolean isReachedTarget() {
/* 103 */     return this.reachedTarget;
/*     */   }
/*     */   
/*     */   protected boolean findNearestBlock() {
/* 107 */     int $$0 = this.searchRange;
/* 108 */     int $$1 = this.verticalSearchRange;
/* 109 */     BlockPos $$2 = this.mob.blockPosition();
/*     */     
/* 111 */     BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos(); int $$4;
/* 112 */     for ($$4 = this.verticalSearchStart; $$4 <= $$1; $$4 = ($$4 > 0) ? -$$4 : (1 - $$4)) {
/* 113 */       for (int $$5 = 0; $$5 < $$0; $$5++) {
/* 114 */         int $$6; for ($$6 = 0; $$6 <= $$5; $$6 = ($$6 > 0) ? -$$6 : (1 - $$6)) {
/*     */           
/* 116 */           int $$7 = ($$6 < $$5 && $$6 > -$$5) ? $$5 : 0;
/* 117 */           for (; $$7 <= $$5; $$7 = ($$7 > 0) ? -$$7 : (1 - $$7)) {
/* 118 */             $$3.setWithOffset((Vec3i)$$2, $$6, $$4 - 1, $$7);
/* 119 */             if (this.mob.isWithinRestriction((BlockPos)$$3) && isValidTarget((LevelReader)this.mob.level(), (BlockPos)$$3)) {
/* 120 */               this.blockPos = (BlockPos)$$3;
/* 121 */               return true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract boolean isValidTarget(LevelReader paramLevelReader, BlockPos paramBlockPos);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\MoveToBlockGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */