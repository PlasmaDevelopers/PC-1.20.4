/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.util.GoalUtils;
/*     */ import net.minecraft.world.level.block.DoorBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ 
/*     */ public abstract class DoorInteractGoal extends Goal {
/*  14 */   protected BlockPos doorPos = BlockPos.ZERO; protected Mob mob;
/*     */   protected boolean hasDoor;
/*     */   private boolean passed;
/*     */   private float doorOpenDirX;
/*     */   private float doorOpenDirZ;
/*     */   
/*     */   public DoorInteractGoal(Mob $$0) {
/*  21 */     this.mob = $$0;
/*  22 */     if (!GoalUtils.hasGroundPathNavigation($$0)) {
/*  23 */       throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean isOpen() {
/*  28 */     if (!this.hasDoor) {
/*  29 */       return false;
/*     */     }
/*  31 */     BlockState $$0 = this.mob.level().getBlockState(this.doorPos);
/*  32 */     if (!($$0.getBlock() instanceof DoorBlock)) {
/*  33 */       this.hasDoor = false;
/*  34 */       return false;
/*     */     } 
/*  36 */     return ((Boolean)$$0.getValue((Property)DoorBlock.OPEN)).booleanValue();
/*     */   }
/*     */   
/*     */   protected void setOpen(boolean $$0) {
/*  40 */     if (this.hasDoor) {
/*  41 */       BlockState $$1 = this.mob.level().getBlockState(this.doorPos);
/*  42 */       if ($$1.getBlock() instanceof DoorBlock) {
/*  43 */         ((DoorBlock)$$1.getBlock()).setOpen((Entity)this.mob, this.mob.level(), $$1, this.doorPos, $$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  50 */     if (!GoalUtils.hasGroundPathNavigation(this.mob)) {
/*  51 */       return false;
/*     */     }
/*  53 */     if (!this.mob.horizontalCollision) {
/*  54 */       return false;
/*     */     }
/*  56 */     GroundPathNavigation $$0 = (GroundPathNavigation)this.mob.getNavigation();
/*  57 */     Path $$1 = $$0.getPath();
/*  58 */     if ($$1 == null || $$1.isDone() || !$$0.canOpenDoors()) {
/*  59 */       return false;
/*     */     }
/*     */     
/*  62 */     for (int $$2 = 0; $$2 < Math.min($$1.getNextNodeIndex() + 2, $$1.getNodeCount()); $$2++) {
/*  63 */       Node $$3 = $$1.getNode($$2);
/*  64 */       this.doorPos = new BlockPos($$3.x, $$3.y + 1, $$3.z);
/*  65 */       if (this.mob.distanceToSqr(this.doorPos.getX(), this.mob.getY(), this.doorPos.getZ()) <= 2.25D) {
/*     */ 
/*     */         
/*  68 */         this.hasDoor = DoorBlock.isWoodenDoor(this.mob.level(), this.doorPos);
/*  69 */         if (this.hasDoor) {
/*  70 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*  74 */     this.doorPos = this.mob.blockPosition().above();
/*  75 */     this.hasDoor = DoorBlock.isWoodenDoor(this.mob.level(), this.doorPos);
/*  76 */     return this.hasDoor;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  81 */     return !this.passed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  86 */     this.passed = false;
/*  87 */     this.doorOpenDirX = (float)(this.doorPos.getX() + 0.5D - this.mob.getX());
/*  88 */     this.doorOpenDirZ = (float)(this.doorPos.getZ() + 0.5D - this.mob.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdateEveryTick() {
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  98 */     float $$0 = (float)(this.doorPos.getX() + 0.5D - this.mob.getX());
/*  99 */     float $$1 = (float)(this.doorPos.getZ() + 0.5D - this.mob.getZ());
/* 100 */     float $$2 = this.doorOpenDirX * $$0 + this.doorOpenDirZ * $$1;
/* 101 */     if ($$2 < 0.0F)
/* 102 */       this.passed = true; 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\DoorInteractGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */