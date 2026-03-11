/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.animal.Cat;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.BedBlock;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BedPart;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class CatSitOnBlockGoal extends MoveToBlockGoal {
/*    */   public CatSitOnBlockGoal(Cat $$0, double $$1) {
/* 18 */     super((PathfinderMob)$$0, $$1, 8);
/* 19 */     this.cat = $$0;
/*    */   }
/*    */   private final Cat cat;
/*    */   
/*    */   public boolean canUse() {
/* 24 */     return (this.cat.isTame() && !this.cat.isOrderedToSit() && super.canUse());
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 29 */     super.start();
/* 30 */     this.cat.setInSittingPose(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 35 */     super.stop();
/* 36 */     this.cat.setInSittingPose(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 41 */     super.tick();
/*    */     
/* 43 */     this.cat.setInSittingPose(isReachedTarget());
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isValidTarget(LevelReader $$0, BlockPos $$1) {
/* 48 */     if (!$$0.isEmptyBlock($$1.above())) {
/* 49 */       return false;
/*    */     }
/*    */     
/* 52 */     BlockState $$2 = $$0.getBlockState($$1);
/*    */ 
/*    */     
/* 55 */     if ($$2.is(Blocks.CHEST))
/* 56 */       return (ChestBlockEntity.getOpenCount((BlockGetter)$$0, $$1) < 1); 
/* 57 */     if ($$2.is(Blocks.FURNACE) && ((Boolean)$$2.getValue((Property)FurnaceBlock.LIT)).booleanValue()) {
/* 58 */       return true;
/*    */     }
/* 60 */     return $$2.is(BlockTags.BEDS, $$0 -> ((Boolean)$$0.getOptionalValue((Property)BedBlock.PART).map(()).orElse(Boolean.valueOf(true))).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\CatSitOnBlockGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */