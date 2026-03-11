/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.animal.Cat;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ 
/*    */ public class CatLieOnBedGoal
/*    */   extends MoveToBlockGoal {
/*    */   private final Cat cat;
/*    */   
/*    */   public CatLieOnBedGoal(Cat $$0, double $$1, int $$2) {
/* 15 */     super((PathfinderMob)$$0, $$1, $$2, 6);
/* 16 */     this.cat = $$0;
/* 17 */     this.verticalSearchStart = -2;
/* 18 */     setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 23 */     return (this.cat.isTame() && !this.cat.isOrderedToSit() && !this.cat.isLying() && super.canUse());
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 28 */     super.start();
/* 29 */     this.cat.setInSittingPose(false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int nextStartTick(PathfinderMob $$0) {
/* 34 */     return 40;
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 39 */     super.stop();
/* 40 */     this.cat.setLying(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 45 */     super.tick();
/*    */     
/* 47 */     this.cat.setInSittingPose(false);
/* 48 */     if (!isReachedTarget()) {
/* 49 */       this.cat.setLying(false);
/* 50 */     } else if (!this.cat.isLying()) {
/* 51 */       this.cat.setLying(true);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isValidTarget(LevelReader $$0, BlockPos $$1) {
/* 57 */     return ($$0.isEmptyBlock($$1.above()) && $$0.getBlockState($$1).is(BlockTags.BEDS));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\CatLieOnBedGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */