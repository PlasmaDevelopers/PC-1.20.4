/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.particles.ItemParticleOption;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class RemoveBlockGoal extends MoveToBlockGoal {
/*     */   private final Block blockToRemove;
/*     */   private final Mob removerMob;
/*     */   private int ticksSinceReachedGoal;
/*     */   private static final int WAIT_AFTER_BLOCK_FOUND = 20;
/*     */   
/*     */   public RemoveBlockGoal(Block $$0, PathfinderMob $$1, double $$2, int $$3) {
/*  33 */     super($$1, $$2, 24, $$3);
/*  34 */     this.blockToRemove = $$0;
/*  35 */     this.removerMob = (Mob)$$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  40 */     if (!this.removerMob.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/*  41 */       return false;
/*     */     }
/*     */     
/*  44 */     if (this.nextStartTick > 0) {
/*  45 */       this.nextStartTick--;
/*  46 */       return false;
/*     */     } 
/*     */     
/*  49 */     if (findNearestBlock()) {
/*     */       
/*  51 */       this.nextStartTick = reducedTickDelay(20);
/*  52 */       return true;
/*     */     } 
/*  54 */     this.nextStartTick = nextStartTick(this.mob);
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/*  61 */     super.stop();
/*  62 */     this.removerMob.fallDistance = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  67 */     super.start();
/*  68 */     this.ticksSinceReachedGoal = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playDestroyProgressSound(LevelAccessor $$0, BlockPos $$1) {}
/*     */ 
/*     */   
/*     */   public void playBreakSound(Level $$0, BlockPos $$1) {}
/*     */ 
/*     */   
/*     */   public void tick() {
/*  79 */     super.tick();
/*  80 */     Level $$0 = this.removerMob.level();
/*  81 */     BlockPos $$1 = this.removerMob.blockPosition();
/*     */     
/*  83 */     BlockPos $$2 = getPosWithBlock($$1, (BlockGetter)$$0);
/*     */     
/*  85 */     RandomSource $$3 = this.removerMob.getRandom();
/*  86 */     if (isReachedTarget() && $$2 != null) {
/*  87 */       if (this.ticksSinceReachedGoal > 0) {
/*  88 */         Vec3 $$4 = this.removerMob.getDeltaMovement();
/*  89 */         this.removerMob.setDeltaMovement($$4.x, 0.3D, $$4.z);
/*     */         
/*  91 */         if (!$$0.isClientSide) {
/*  92 */           double $$5 = 0.08D;
/*  93 */           ((ServerLevel)$$0).sendParticles((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, new ItemStack((ItemLike)Items.EGG)), $$2
/*     */               
/*  95 */               .getX() + 0.5D, $$2
/*  96 */               .getY() + 0.7D, $$2
/*  97 */               .getZ() + 0.5D, 3, ($$3
/*     */               
/*  99 */               .nextFloat() - 0.5D) * 0.08D, ($$3
/* 100 */               .nextFloat() - 0.5D) * 0.08D, ($$3
/* 101 */               .nextFloat() - 0.5D) * 0.08D, 0.15000000596046448D);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 107 */       if (this.ticksSinceReachedGoal % 2 == 0) {
/* 108 */         Vec3 $$6 = this.removerMob.getDeltaMovement();
/* 109 */         this.removerMob.setDeltaMovement($$6.x, -0.3D, $$6.z);
/*     */         
/* 111 */         if (this.ticksSinceReachedGoal % 6 == 0) {
/* 112 */           playDestroyProgressSound((LevelAccessor)$$0, this.blockPos);
/*     */         }
/*     */       } 
/*     */       
/* 116 */       if (this.ticksSinceReachedGoal > 60) {
/* 117 */         $$0.removeBlock($$2, false);
/* 118 */         if (!$$0.isClientSide) {
/* 119 */           for (int $$7 = 0; $$7 < 20; $$7++) {
/* 120 */             double $$8 = $$3.nextGaussian() * 0.02D;
/* 121 */             double $$9 = $$3.nextGaussian() * 0.02D;
/* 122 */             double $$10 = $$3.nextGaussian() * 0.02D;
/* 123 */             ((ServerLevel)$$0).sendParticles((ParticleOptions)ParticleTypes.POOF, $$2.getX() + 0.5D, $$2.getY(), $$2.getZ() + 0.5D, 1, $$8, $$9, $$10, 0.15000000596046448D);
/*     */           } 
/* 125 */           playBreakSound($$0, $$2);
/*     */         } 
/*     */       } 
/* 128 */       this.ticksSinceReachedGoal++;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockPos getPosWithBlock(BlockPos $$0, BlockGetter $$1) {
/* 134 */     if ($$1.getBlockState($$0).is(this.blockToRemove)) {
/* 135 */       return $$0;
/*     */     }
/* 137 */     BlockPos[] $$2 = { $$0.below(), $$0.west(), $$0.east(), $$0.north(), $$0.south(), $$0.below().below() };
/* 138 */     for (BlockPos $$3 : $$2) {
/* 139 */       if ($$1.getBlockState($$3).is(this.blockToRemove)) {
/* 140 */         return $$3;
/*     */       }
/*     */     } 
/* 143 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValidTarget(LevelReader $$0, BlockPos $$1) {
/* 148 */     ChunkAccess $$2 = $$0.getChunk(SectionPos.blockToSectionCoord($$1.getX()), SectionPos.blockToSectionCoord($$1.getZ()), ChunkStatus.FULL, false);
/* 149 */     if ($$2 != null) {
/* 150 */       return ($$2.getBlockState($$1).is(this.blockToRemove) && $$2.getBlockState($$1.above()).isAir() && $$2.getBlockState($$1.above(2)).isAir());
/*     */     }
/* 152 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\RemoveBlockGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */