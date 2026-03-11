/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.GrowingPlantHeadBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;
/*    */ 
/*    */ public class TwistingVinesFeature extends Feature<TwistingVinesConfig> {
/*    */   public TwistingVinesFeature(Codec<TwistingVinesConfig> $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<TwistingVinesConfig> $$0) {
/* 23 */     WorldGenLevel $$1 = $$0.level();
/* 24 */     BlockPos $$2 = $$0.origin();
/* 25 */     if (isInvalidPlacementLocation((LevelAccessor)$$1, $$2)) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     RandomSource $$3 = $$0.random();
/* 30 */     TwistingVinesConfig $$4 = $$0.config();
/* 31 */     int $$5 = $$4.spreadWidth();
/* 32 */     int $$6 = $$4.spreadHeight();
/* 33 */     int $$7 = $$4.maxHeight();
/*    */     
/* 35 */     BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
/*    */     
/* 37 */     for (int $$9 = 0; $$9 < $$5 * $$5; $$9++) {
/* 38 */       $$8.set((Vec3i)$$2).move(
/* 39 */           Mth.nextInt($$3, -$$5, $$5), 
/* 40 */           Mth.nextInt($$3, -$$6, $$6), 
/* 41 */           Mth.nextInt($$3, -$$5, $$5));
/*    */ 
/*    */       
/* 44 */       if (findFirstAirBlockAboveGround((LevelAccessor)$$1, $$8))
/*    */       {
/*    */ 
/*    */         
/* 48 */         if (!isInvalidPlacementLocation((LevelAccessor)$$1, (BlockPos)$$8)) {
/*    */ 
/*    */ 
/*    */           
/* 52 */           int $$10 = Mth.nextInt($$3, 1, $$7);
/* 53 */           if ($$3.nextInt(6) == 0) {
/* 54 */             $$10 *= 2;
/*    */           }
/* 56 */           if ($$3.nextInt(5) == 0) {
/* 57 */             $$10 = 1;
/*    */           }
/*    */           
/* 60 */           int $$11 = 17;
/* 61 */           int $$12 = 25;
/* 62 */           placeWeepingVinesColumn((LevelAccessor)$$1, $$3, $$8, $$10, 17, 25);
/*    */         }  } 
/* 64 */     }  return true;
/*    */   }
/*    */   
/*    */   private static boolean findFirstAirBlockAboveGround(LevelAccessor $$0, BlockPos.MutableBlockPos $$1) {
/*    */     while (true) {
/* 69 */       $$1.move(0, -1, 0);
/* 70 */       if ($$0.isOutsideBuildHeight((BlockPos)$$1)) {
/* 71 */         return false;
/*    */       }
/* 73 */       if (!$$0.getBlockState((BlockPos)$$1).isAir()) {
/* 74 */         $$1.move(0, 1, 0);
/* 75 */         return true;
/*    */       } 
/*    */     } 
/*    */   } public static void placeWeepingVinesColumn(LevelAccessor $$0, RandomSource $$1, BlockPos.MutableBlockPos $$2, int $$3, int $$4, int $$5) {
/* 79 */     for (int $$6 = 1; $$6 <= $$3; $$6++) {
/* 80 */       if ($$0.isEmptyBlock((BlockPos)$$2)) {
/* 81 */         if ($$6 == $$3 || !$$0.isEmptyBlock($$2.above())) {
/* 82 */           $$0.setBlock((BlockPos)$$2, (BlockState)Blocks.TWISTING_VINES.defaultBlockState().setValue((Property)GrowingPlantHeadBlock.AGE, Integer.valueOf(Mth.nextInt($$1, $$4, $$5))), 2);
/*    */           break;
/*    */         } 
/* 85 */         $$0.setBlock((BlockPos)$$2, Blocks.TWISTING_VINES_PLANT.defaultBlockState(), 2);
/*    */       } 
/*    */ 
/*    */       
/* 89 */       $$2.move(Direction.UP);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static boolean isInvalidPlacementLocation(LevelAccessor $$0, BlockPos $$1) {
/* 94 */     if (!$$0.isEmptyBlock($$1)) {
/* 95 */       return true;
/*    */     }
/*    */     
/* 98 */     BlockState $$2 = $$0.getBlockState($$1.below());
/* 99 */     return (!$$2.is(Blocks.NETHERRACK) && !$$2.is(Blocks.WARPED_NYLIUM) && !$$2.is(Blocks.WARPED_WART_BLOCK));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\TwistingVinesFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */