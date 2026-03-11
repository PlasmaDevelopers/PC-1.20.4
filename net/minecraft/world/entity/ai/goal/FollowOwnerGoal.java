/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.TamableAnimal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FollowOwnerGoal
/*     */   extends Goal
/*     */ {
/*     */   public static final int TELEPORT_WHEN_DISTANCE_IS = 12;
/*     */   private static final int MIN_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 2;
/*     */   private static final int MAX_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 3;
/*     */   private static final int MAX_VERTICAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 1;
/*     */   private final TamableAnimal tamable;
/*     */   private LivingEntity owner;
/*     */   private final LevelReader level;
/*     */   private final double speedModifier;
/*     */   private final PathNavigation navigation;
/*     */   private int timeToRecalcPath;
/*     */   private final float stopDistance;
/*     */   private final float startDistance;
/*     */   private float oldWaterCost;
/*     */   private final boolean canFly;
/*     */   
/*     */   public FollowOwnerGoal(TamableAnimal $$0, double $$1, float $$2, float $$3, boolean $$4) {
/*  37 */     this.tamable = $$0;
/*  38 */     this.level = (LevelReader)$$0.level();
/*  39 */     this.speedModifier = $$1;
/*  40 */     this.navigation = $$0.getNavigation();
/*  41 */     this.startDistance = $$2;
/*  42 */     this.stopDistance = $$3;
/*  43 */     this.canFly = $$4;
/*  44 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     
/*  46 */     if (!($$0.getNavigation() instanceof net.minecraft.world.entity.ai.navigation.GroundPathNavigation) && !($$0.getNavigation() instanceof net.minecraft.world.entity.ai.navigation.FlyingPathNavigation)) {
/*  47 */       throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  53 */     LivingEntity $$0 = this.tamable.getOwner();
/*  54 */     if ($$0 == null) {
/*  55 */       return false;
/*     */     }
/*  57 */     if ($$0.isSpectator()) {
/*  58 */       return false;
/*     */     }
/*  60 */     if (unableToMove()) {
/*  61 */       return false;
/*     */     }
/*  63 */     if (this.tamable.distanceToSqr((Entity)$$0) < (this.startDistance * this.startDistance)) {
/*  64 */       return false;
/*     */     }
/*  66 */     this.owner = $$0;
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  72 */     if (this.navigation.isDone()) {
/*  73 */       return false;
/*     */     }
/*  75 */     if (unableToMove()) {
/*  76 */       return false;
/*     */     }
/*  78 */     if (this.tamable.distanceToSqr((Entity)this.owner) <= (this.stopDistance * this.stopDistance)) {
/*  79 */       return false;
/*     */     }
/*  81 */     return true;
/*     */   }
/*     */   
/*     */   private boolean unableToMove() {
/*  85 */     return (this.tamable.isOrderedToSit() || this.tamable.isPassenger() || this.tamable.isLeashed());
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  90 */     this.timeToRecalcPath = 0;
/*  91 */     this.oldWaterCost = this.tamable.getPathfindingMalus(BlockPathTypes.WATER);
/*  92 */     this.tamable.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  97 */     this.owner = null;
/*  98 */     this.navigation.stop();
/*  99 */     this.tamable.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 104 */     this.tamable.getLookControl().setLookAt((Entity)this.owner, 10.0F, this.tamable.getMaxHeadXRot());
/*     */     
/* 106 */     if (--this.timeToRecalcPath > 0) {
/*     */       return;
/*     */     }
/* 109 */     this.timeToRecalcPath = adjustedTickDelay(10);
/*     */     
/* 111 */     if (this.tamable.distanceToSqr((Entity)this.owner) >= 144.0D) {
/*     */       
/* 113 */       teleportToOwner();
/*     */     } else {
/* 115 */       this.navigation.moveTo((Entity)this.owner, this.speedModifier);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void teleportToOwner() {
/* 120 */     BlockPos $$0 = this.owner.blockPosition();
/*     */     
/* 122 */     for (int $$1 = 0; $$1 < 10; $$1++) {
/* 123 */       int $$2 = randomIntInclusive(-3, 3);
/* 124 */       int $$3 = randomIntInclusive(-1, 1);
/* 125 */       int $$4 = randomIntInclusive(-3, 3);
/* 126 */       boolean $$5 = maybeTeleportTo($$0.getX() + $$2, $$0.getY() + $$3, $$0.getZ() + $$4);
/* 127 */       if ($$5) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean maybeTeleportTo(int $$0, int $$1, int $$2) {
/* 134 */     if (Math.abs($$0 - this.owner.getX()) < 2.0D && Math.abs($$2 - this.owner.getZ()) < 2.0D)
/*     */     {
/* 136 */       return false;
/*     */     }
/* 138 */     if (!canTeleportTo(new BlockPos($$0, $$1, $$2))) {
/* 139 */       return false;
/*     */     }
/* 141 */     this.tamable.moveTo($$0 + 0.5D, $$1, $$2 + 0.5D, this.tamable.getYRot(), this.tamable.getXRot());
/* 142 */     this.navigation.stop();
/* 143 */     return true;
/*     */   }
/*     */   
/*     */   private boolean canTeleportTo(BlockPos $$0) {
/* 147 */     BlockPathTypes $$1 = WalkNodeEvaluator.getBlockPathTypeStatic((BlockGetter)this.level, $$0.mutable());
/*     */     
/* 149 */     if ($$1 != BlockPathTypes.WALKABLE) {
/* 150 */       return false;
/*     */     }
/*     */     
/* 153 */     BlockState $$2 = this.level.getBlockState($$0.below());
/* 154 */     if (!this.canFly && $$2.getBlock() instanceof net.minecraft.world.level.block.LeavesBlock)
/*     */     {
/* 156 */       return false;
/*     */     }
/*     */     
/* 159 */     BlockPos $$3 = $$0.subtract((Vec3i)this.tamable.blockPosition());
/* 160 */     if (!this.level.noCollision((Entity)this.tamable, this.tamable.getBoundingBox().move($$3)))
/*     */     {
/* 162 */       return false;
/*     */     }
/*     */     
/* 165 */     return true;
/*     */   }
/*     */   
/*     */   private int randomIntInclusive(int $$0, int $$1) {
/* 169 */     return this.tamable.getRandom().nextInt($$1 - $$0 + 1) + $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\FollowOwnerGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */