/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
/*    */ 
/*    */ public class BlockPileFeature extends Feature<BlockPileConfiguration> {
/*    */   public BlockPileFeature(Codec<BlockPileConfiguration> $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<BlockPileConfiguration> $$0) {
/* 21 */     BlockPos $$1 = $$0.origin();
/* 22 */     WorldGenLevel $$2 = $$0.level();
/* 23 */     RandomSource $$3 = $$0.random();
/* 24 */     BlockPileConfiguration $$4 = $$0.config();
/* 25 */     if ($$1.getY() < $$2.getMinBuildHeight() + 5) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     int $$5 = 2 + $$3.nextInt(2);
/* 30 */     int $$6 = 2 + $$3.nextInt(2);
/*    */     
/* 32 */     for (BlockPos $$7 : BlockPos.betweenClosed($$1.offset(-$$5, 0, -$$6), $$1.offset($$5, 1, $$6))) {
/* 33 */       int $$8 = $$1.getX() - $$7.getX();
/* 34 */       int $$9 = $$1.getZ() - $$7.getZ();
/* 35 */       if (($$8 * $$8 + $$9 * $$9) <= $$3.nextFloat() * 10.0F - $$3.nextFloat() * 6.0F) {
/* 36 */         tryPlaceBlock((LevelAccessor)$$2, $$7, $$3, $$4); continue;
/* 37 */       }  if ($$3.nextFloat() < 0.031D) {
/* 38 */         tryPlaceBlock((LevelAccessor)$$2, $$7, $$3, $$4);
/*    */       }
/*    */     } 
/*    */     
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   private boolean mayPlaceOn(LevelAccessor $$0, BlockPos $$1, RandomSource $$2) {
/* 46 */     BlockPos $$3 = $$1.below();
/* 47 */     BlockState $$4 = $$0.getBlockState($$3);
/* 48 */     if ($$4.is(Blocks.DIRT_PATH)) {
/* 49 */       return $$2.nextBoolean();
/*    */     }
/*    */     
/* 52 */     return $$4.isFaceSturdy((BlockGetter)$$0, $$3, Direction.UP);
/*    */   }
/*    */   
/*    */   private void tryPlaceBlock(LevelAccessor $$0, BlockPos $$1, RandomSource $$2, BlockPileConfiguration $$3) {
/* 56 */     if ($$0.isEmptyBlock($$1) && mayPlaceOn($$0, $$1, $$2))
/* 57 */       $$0.setBlock($$1, $$3.stateProvider.getState($$2, $$1), 4); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\BlockPileFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */