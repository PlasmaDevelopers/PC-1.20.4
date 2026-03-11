/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
/*    */ 
/*    */ public class MultifaceGrowthFeature extends Feature<MultifaceGrowthConfiguration> {
/*    */   public MultifaceGrowthFeature(Codec<MultifaceGrowthConfiguration> $$0) {
/* 17 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<MultifaceGrowthConfiguration> $$0) {
/* 22 */     WorldGenLevel $$1 = $$0.level();
/* 23 */     BlockPos $$2 = $$0.origin();
/* 24 */     RandomSource $$3 = $$0.random();
/* 25 */     MultifaceGrowthConfiguration $$4 = $$0.config();
/* 26 */     if (!isAirOrWater($$1.getBlockState($$2))) {
/* 27 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 31 */     List<Direction> $$5 = $$4.getShuffledDirections($$3);
/* 32 */     if (placeGrowthIfPossible($$1, $$2, $$1.getBlockState($$2), $$4, $$3, $$5)) {
/* 33 */       return true;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 38 */     BlockPos.MutableBlockPos $$6 = $$2.mutable();
/* 39 */     for (Direction $$7 : $$5) {
/* 40 */       $$6.set((Vec3i)$$2);
/* 41 */       List<Direction> $$8 = $$4.getShuffledDirectionsExcept($$3, $$7.getOpposite());
/* 42 */       for (int $$9 = 0; $$9 < $$4.searchRange; $$9++) {
/* 43 */         $$6.setWithOffset((Vec3i)$$2, $$7);
/* 44 */         BlockState $$10 = $$1.getBlockState((BlockPos)$$6);
/* 45 */         if (!isAirOrWater($$10) && !$$10.is((Block)$$4.placeBlock)) {
/*    */           break;
/*    */         }
/*    */         
/* 49 */         if (placeGrowthIfPossible($$1, (BlockPos)$$6, $$10, $$4, $$3, $$8)) {
/* 50 */           return true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean placeGrowthIfPossible(WorldGenLevel $$0, BlockPos $$1, BlockState $$2, MultifaceGrowthConfiguration $$3, RandomSource $$4, List<Direction> $$5) {
/* 59 */     BlockPos.MutableBlockPos $$6 = $$1.mutable();
/* 60 */     for (Direction $$7 : $$5) {
/* 61 */       BlockState $$8 = $$0.getBlockState((BlockPos)$$6.setWithOffset((Vec3i)$$1, $$7));
/* 62 */       if ($$8.is($$3.canBePlacedOn)) {
/* 63 */         BlockState $$9 = $$3.placeBlock.getStateForPlacement($$2, (BlockGetter)$$0, $$1, $$7);
/* 64 */         if ($$9 == null) {
/* 65 */           return false;
/*    */         }
/* 67 */         $$0.setBlock($$1, $$9, 3);
/* 68 */         $$0.getChunk($$1).markPosForPostprocessing($$1);
/* 69 */         if ($$4.nextFloat() < $$3.chanceOfSpreading) {
/* 70 */           $$3.placeBlock.getSpreader().spreadFromFaceTowardRandomDirection($$9, (LevelAccessor)$$0, $$1, $$7, $$4, true);
/*    */         }
/* 72 */         return true;
/*    */       } 
/*    */     } 
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   private static boolean isAirOrWater(BlockState $$0) {
/* 79 */     return ($$0.isAir() || $$0.is(Blocks.WATER));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\MultifaceGrowthFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */