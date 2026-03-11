/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.BedBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.AABB;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CatRelaxOnOwnerGoal
/*     */   extends Goal
/*     */ {
/*     */   private final Cat cat;
/*     */   @Nullable
/*     */   private Player ownerPlayer;
/*     */   @Nullable
/*     */   private BlockPos goalPos;
/*     */   private int onBedTicks;
/*     */   
/*     */   public CatRelaxOnOwnerGoal(Cat $$0) {
/* 555 */     this.cat = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 560 */     if (!this.cat.isTame()) {
/* 561 */       return false;
/*     */     }
/*     */     
/* 564 */     if (this.cat.isOrderedToSit()) {
/* 565 */       return false;
/*     */     }
/*     */     
/* 568 */     LivingEntity $$0 = this.cat.getOwner();
/* 569 */     if ($$0 instanceof Player) {
/* 570 */       this.ownerPlayer = (Player)$$0;
/*     */       
/* 572 */       if (!$$0.isSleeping()) {
/* 573 */         return false;
/*     */       }
/*     */       
/* 576 */       if (this.cat.distanceToSqr((Entity)this.ownerPlayer) > 100.0D) {
/* 577 */         return false;
/*     */       }
/*     */       
/* 580 */       BlockPos $$1 = this.ownerPlayer.blockPosition();
/* 581 */       BlockState $$2 = this.cat.level().getBlockState($$1);
/* 582 */       if ($$2.is(BlockTags.BEDS)) {
/* 583 */         this.goalPos = $$2.getOptionalValue((Property)BedBlock.FACING).map($$1 -> $$0.relative($$1.getOpposite())).orElseGet(() -> new BlockPos((Vec3i)$$0));
/* 584 */         return !spaceIsOccupied();
/*     */       } 
/*     */     } 
/*     */     
/* 588 */     return false;
/*     */   }
/*     */   
/*     */   private boolean spaceIsOccupied() {
/* 592 */     List<Cat> $$0 = this.cat.level().getEntitiesOfClass(Cat.class, (new AABB(this.goalPos)).inflate(2.0D));
/* 593 */     for (Cat $$1 : $$0) {
/* 594 */       if ($$1 != this.cat && ($$1.isLying() || $$1.isRelaxStateOne())) {
/* 595 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 599 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/* 604 */     return (this.cat.isTame() && !this.cat.isOrderedToSit() && this.ownerPlayer != null && this.ownerPlayer.isSleeping() && this.goalPos != null && !spaceIsOccupied());
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 609 */     if (this.goalPos != null) {
/* 610 */       this.cat.setInSittingPose(false);
/* 611 */       this.cat.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.100000023841858D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 617 */     this.cat.setLying(false);
/*     */     
/* 619 */     float $$0 = this.cat.level().getTimeOfDay(1.0F);
/* 620 */     if (this.ownerPlayer.getSleepTimer() >= 100 && $$0 > 0.77D && $$0 < 0.8D && this.cat.level().getRandom().nextFloat() < 0.7D) {
/* 621 */       giveMorningGift();
/*     */     }
/*     */     
/* 624 */     this.onBedTicks = 0;
/* 625 */     this.cat.setRelaxStateOne(false);
/* 626 */     this.cat.getNavigation().stop();
/*     */   }
/*     */   
/*     */   private void giveMorningGift() {
/* 630 */     RandomSource $$0 = this.cat.getRandom();
/* 631 */     BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/* 632 */     $$1.set(this.cat.isLeashed() ? (Vec3i)this.cat.getLeashHolder().blockPosition() : (Vec3i)this.cat.blockPosition());
/* 633 */     this.cat.randomTeleport(($$1.getX() + $$0.nextInt(11) - 5), ($$1.getY() + $$0.nextInt(5) - 2), ($$1.getZ() + $$0.nextInt(11) - 5), false);
/*     */     
/* 635 */     $$1.set((Vec3i)this.cat.blockPosition());
/* 636 */     LootTable $$2 = this.cat.level().getServer().getLootData().getLootTable(BuiltInLootTables.CAT_MORNING_GIFT);
/*     */ 
/*     */ 
/*     */     
/* 640 */     LootParams $$3 = (new LootParams.Builder((ServerLevel)this.cat.level())).withParameter(LootContextParams.ORIGIN, this.cat.position()).withParameter(LootContextParams.THIS_ENTITY, this.cat).create(LootContextParamSets.GIFT);
/* 641 */     ObjectArrayList objectArrayList = $$2.getRandomItems($$3);
/* 642 */     for (ItemStack $$5 : objectArrayList) {
/* 643 */       this.cat.level().addFreshEntity((Entity)new ItemEntity(this.cat.level(), $$1.getX() - Mth.sin(this.cat.yBodyRot * 0.017453292F), $$1.getY(), $$1.getZ() + Mth.cos(this.cat.yBodyRot * 0.017453292F), $$5));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 649 */     if (this.ownerPlayer != null && this.goalPos != null) {
/* 650 */       this.cat.setInSittingPose(false);
/* 651 */       this.cat.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.100000023841858D);
/* 652 */       if (this.cat.distanceToSqr((Entity)this.ownerPlayer) < 2.5D) {
/* 653 */         this.onBedTicks++;
/* 654 */         if (this.onBedTicks > adjustedTickDelay(16)) {
/* 655 */           this.cat.setLying(true);
/* 656 */           this.cat.setRelaxStateOne(false);
/*     */         } else {
/* 658 */           this.cat.lookAt((Entity)this.ownerPlayer, 45.0F, 45.0F);
/* 659 */           this.cat.setRelaxStateOne(true);
/*     */         } 
/*     */       } else {
/* 662 */         this.cat.setLying(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Cat$CatRelaxOnOwnerGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */