/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.PoiTypeTags;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*     */ import net.minecraft.world.entity.ai.util.GoalUtils;
/*     */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.level.block.DoorBlock;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class MoveThroughVillageGoal
/*     */   extends Goal
/*     */ {
/*     */   protected final PathfinderMob mob;
/*     */   private final double speedModifier;
/*  33 */   private final List<BlockPos> visited = Lists.newArrayList(); @Nullable
/*     */   private Path path; private BlockPos poiPos; private final boolean onlyAtNight; private final int distanceToPoi;
/*     */   private final BooleanSupplier canDealWithDoors;
/*     */   
/*     */   public MoveThroughVillageGoal(PathfinderMob $$0, double $$1, boolean $$2, int $$3, BooleanSupplier $$4) {
/*  38 */     this.mob = $$0;
/*  39 */     this.speedModifier = $$1;
/*  40 */     this.onlyAtNight = $$2;
/*  41 */     this.distanceToPoi = $$3;
/*  42 */     this.canDealWithDoors = $$4;
/*  43 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     
/*  45 */     if (!GoalUtils.hasGroundPathNavigation((Mob)$$0)) {
/*  46 */       throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  52 */     if (!GoalUtils.hasGroundPathNavigation((Mob)this.mob)) {
/*  53 */       return false;
/*     */     }
/*  55 */     updateVisited();
/*     */     
/*  57 */     if (this.onlyAtNight && this.mob.level().isDay()) {
/*  58 */       return false;
/*     */     }
/*     */     
/*  61 */     ServerLevel $$0 = (ServerLevel)this.mob.level();
/*  62 */     BlockPos $$1 = this.mob.blockPosition();
/*     */     
/*  64 */     if (!$$0.isCloseToVillage($$1, 6)) {
/*  65 */       return false;
/*     */     }
/*     */     
/*  68 */     Vec3 $$2 = LandRandomPos.getPos(this.mob, 15, 7, $$2 -> {
/*     */           if (!$$0.isVillage($$2)) {
/*     */             return Double.NEGATIVE_INFINITY;
/*     */           }
/*     */           Optional<BlockPos> $$3 = $$0.getPoiManager().find((), this::hasNotVisited, $$2, 10, PoiManager.Occupancy.IS_OCCUPIED);
/*     */           return ((Double)$$3.<Double>map(()).orElse(Double.valueOf(Double.NEGATIVE_INFINITY))).doubleValue();
/*     */         });
/*  75 */     if ($$2 == null) {
/*  76 */       return false;
/*     */     }
/*  78 */     Optional<BlockPos> $$3 = $$0.getPoiManager().find($$0 -> $$0.is(PoiTypeTags.VILLAGE), this::hasNotVisited, BlockPos.containing((Position)$$2), 10, PoiManager.Occupancy.IS_OCCUPIED);
/*  79 */     if ($$3.isEmpty()) {
/*  80 */       return false;
/*     */     }
/*  82 */     this.poiPos = ((BlockPos)$$3.get()).immutable();
/*     */     
/*  84 */     GroundPathNavigation $$4 = (GroundPathNavigation)this.mob.getNavigation();
/*  85 */     boolean $$5 = $$4.canOpenDoors();
/*  86 */     $$4.setCanOpenDoors(this.canDealWithDoors.getAsBoolean());
/*  87 */     this.path = $$4.createPath(this.poiPos, 0);
/*  88 */     $$4.setCanOpenDoors($$5);
/*  89 */     if (this.path == null) {
/*  90 */       Vec3 $$6 = DefaultRandomPos.getPosTowards(this.mob, 10, 7, Vec3.atBottomCenterOf((Vec3i)this.poiPos), 1.5707963705062866D);
/*  91 */       if ($$6 == null) {
/*  92 */         return false;
/*     */       }
/*  94 */       $$4.setCanOpenDoors(this.canDealWithDoors.getAsBoolean());
/*  95 */       this.path = this.mob.getNavigation().createPath($$6.x, $$6.y, $$6.z, 0);
/*  96 */       $$4.setCanOpenDoors($$5);
/*  97 */       if (this.path == null) {
/*  98 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 102 */     for (int $$7 = 0; $$7 < this.path.getNodeCount(); $$7++) {
/* 103 */       Node $$8 = this.path.getNode($$7);
/* 104 */       BlockPos $$9 = new BlockPos($$8.x, $$8.y + 1, $$8.z);
/* 105 */       if (DoorBlock.isWoodenDoor(this.mob.level(), $$9)) {
/*     */         
/* 107 */         this.path = this.mob.getNavigation().createPath($$8.x, $$8.y, $$8.z, 0);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 112 */     return (this.path != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/* 117 */     if (this.mob.getNavigation().isDone()) {
/* 118 */       return false;
/*     */     }
/* 120 */     return !this.poiPos.closerToCenterThan((Position)this.mob.position(), (this.mob.getBbWidth() + this.distanceToPoi));
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 125 */     this.mob.getNavigation().moveTo(this.path, this.speedModifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 130 */     if (this.mob.getNavigation().isDone() || this.poiPos.closerToCenterThan((Position)this.mob.position(), this.distanceToPoi)) {
/* 131 */       this.visited.add(this.poiPos);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean hasNotVisited(BlockPos $$0) {
/* 136 */     for (BlockPos $$1 : this.visited) {
/* 137 */       if (Objects.equals($$0, $$1)) {
/* 138 */         return false;
/*     */       }
/*     */     } 
/* 141 */     return true;
/*     */   }
/*     */   
/*     */   private void updateVisited() {
/* 145 */     if (this.visited.size() > 15)
/* 146 */       this.visited.remove(0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\MoveThroughVillageGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */