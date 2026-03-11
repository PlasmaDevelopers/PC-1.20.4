/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.InfestedBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SilverfishMergeWithStoneGoal
/*     */   extends RandomStrollGoal
/*     */ {
/*     */   @Nullable
/*     */   private Direction selectedDirection;
/*     */   private boolean doMerge;
/*     */   
/*     */   public SilverfishMergeWithStoneGoal(Silverfish $$0) {
/* 213 */     super($$0, 1.0D, 10);
/*     */     
/* 215 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 220 */     if (this.mob.getTarget() != null) {
/* 221 */       return false;
/*     */     }
/* 223 */     if (!this.mob.getNavigation().isDone()) {
/* 224 */       return false;
/*     */     }
/*     */     
/* 227 */     RandomSource $$0 = this.mob.getRandom();
/* 228 */     if (this.mob.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && $$0.nextInt(reducedTickDelay(10)) == 0) {
/* 229 */       this.selectedDirection = Direction.getRandom($$0);
/*     */       
/* 231 */       BlockPos $$1 = BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ()).relative(this.selectedDirection);
/* 232 */       BlockState $$2 = this.mob.level().getBlockState($$1);
/* 233 */       if (InfestedBlock.isCompatibleHostBlock($$2)) {
/* 234 */         this.doMerge = true;
/* 235 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 239 */     this.doMerge = false;
/* 240 */     return super.canUse();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/* 245 */     if (this.doMerge) {
/* 246 */       return false;
/*     */     }
/* 248 */     return super.canContinueToUse();
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 253 */     if (!this.doMerge) {
/* 254 */       super.start();
/*     */       
/*     */       return;
/*     */     } 
/* 258 */     Level level = this.mob.level();
/* 259 */     BlockPos $$1 = BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ()).relative(this.selectedDirection);
/* 260 */     BlockState $$2 = level.getBlockState($$1);
/*     */     
/* 262 */     if (InfestedBlock.isCompatibleHostBlock($$2)) {
/* 263 */       level.setBlock($$1, InfestedBlock.infestedStateByHost($$2), 3);
/* 264 */       this.mob.spawnAnim();
/* 265 */       this.mob.discard();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Silverfish$SilverfishMergeWithStoneGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */