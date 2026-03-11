/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
/*    */ 
/*    */ public class PointedDripstoneFeature
/*    */   extends Feature<PointedDripstoneConfiguration> {
/*    */   public PointedDripstoneFeature(Codec<PointedDripstoneConfiguration> $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<PointedDripstoneConfiguration> $$0) {
/* 20 */     WorldGenLevel worldGenLevel = $$0.level();
/* 21 */     BlockPos $$2 = $$0.origin();
/* 22 */     RandomSource $$3 = $$0.random();
/* 23 */     PointedDripstoneConfiguration $$4 = $$0.config();
/* 24 */     Optional<Direction> $$5 = getTipDirection((LevelAccessor)worldGenLevel, $$2, $$3);
/*    */     
/* 26 */     if ($$5.isEmpty()) {
/* 27 */       return false;
/*    */     }
/*    */     
/* 30 */     BlockPos $$6 = $$2.relative(((Direction)$$5.get()).getOpposite());
/*    */     
/* 32 */     createPatchOfDripstoneBlocks((LevelAccessor)worldGenLevel, $$3, $$6, $$4);
/*    */     
/* 34 */     int $$7 = ($$3.nextFloat() < $$4.chanceOfTallerDripstone && DripstoneUtils.isEmptyOrWater(worldGenLevel.getBlockState($$2.relative($$5.get())))) ? 2 : 1;
/*    */     
/* 36 */     DripstoneUtils.growPointedDripstone((LevelAccessor)worldGenLevel, $$2, $$5.get(), $$7, false);
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   private static Optional<Direction> getTipDirection(LevelAccessor $$0, BlockPos $$1, RandomSource $$2) {
/* 41 */     boolean $$3 = DripstoneUtils.isDripstoneBase($$0.getBlockState($$1.above()));
/* 42 */     boolean $$4 = DripstoneUtils.isDripstoneBase($$0.getBlockState($$1.below()));
/*    */     
/* 44 */     if ($$3 && $$4) {
/* 45 */       return Optional.of($$2.nextBoolean() ? Direction.DOWN : Direction.UP);
/*    */     }
/* 47 */     if ($$3) {
/* 48 */       return Optional.of(Direction.DOWN);
/*    */     }
/* 50 */     if ($$4) {
/* 51 */       return Optional.of(Direction.UP);
/*    */     }
/* 53 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   private static void createPatchOfDripstoneBlocks(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, PointedDripstoneConfiguration $$3) {
/* 57 */     DripstoneUtils.placeDripstoneBlockIfPossible($$0, $$2);
/*    */     
/* 59 */     for (Direction $$4 : Direction.Plane.HORIZONTAL) {
/* 60 */       if ($$1.nextFloat() > $$3.chanceOfDirectionalSpread) {
/*    */         continue;
/*    */       }
/*    */       
/* 64 */       BlockPos $$5 = $$2.relative($$4);
/* 65 */       DripstoneUtils.placeDripstoneBlockIfPossible($$0, $$5);
/* 66 */       if ($$1.nextFloat() > $$3.chanceOfSpreadRadius2) {
/*    */         continue;
/*    */       }
/* 69 */       BlockPos $$6 = $$5.relative(Direction.getRandom($$1));
/* 70 */       DripstoneUtils.placeDripstoneBlockIfPossible($$0, $$6);
/* 71 */       if ($$1.nextFloat() > $$3.chanceOfSpreadRadius3) {
/*    */         continue;
/*    */       }
/* 74 */       BlockPos $$7 = $$6.relative(Direction.getRandom($$1));
/* 75 */       DripstoneUtils.placeDripstoneBlockIfPossible($$0, $$7);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\PointedDripstoneFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */