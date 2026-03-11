/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Optional;
/*    */ import java.util.OptionalInt;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.Column;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnderwaterMagmaFeature
/*    */   extends Feature<UnderwaterMagmaConfiguration>
/*    */ {
/*    */   public UnderwaterMagmaFeature(Codec<UnderwaterMagmaConfiguration> $$0) {
/* 28 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<UnderwaterMagmaConfiguration> $$0) {
/* 33 */     WorldGenLevel $$1 = $$0.level();
/* 34 */     BlockPos $$2 = $$0.origin();
/* 35 */     UnderwaterMagmaConfiguration $$3 = $$0.config();
/* 36 */     RandomSource $$4 = $$0.random();
/*    */     
/* 38 */     OptionalInt $$5 = getFloorY($$1, $$2, $$3);
/* 39 */     if ($$5.isEmpty()) {
/* 40 */       return false;
/*    */     }
/* 42 */     BlockPos $$6 = $$2.atY($$5.getAsInt());
/*    */     
/* 44 */     Vec3i $$7 = new Vec3i($$3.placementRadiusAroundFloor, $$3.placementRadiusAroundFloor, $$3.placementRadiusAroundFloor);
/* 45 */     BoundingBox $$8 = BoundingBox.fromCorners((Vec3i)$$6.subtract($$7), (Vec3i)$$6.offset($$7));
/* 46 */     return 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 52 */       (BlockPos.betweenClosedStream($$8).filter($$2 -> ($$0.nextFloat() < $$1.placementProbabilityPerValidPosition)).filter($$1 -> isValidPlacement($$0, $$1)).mapToInt($$1 -> { $$0.setBlock($$1, Blocks.MAGMA_BLOCK.defaultBlockState(), 2); return 1; }).sum() > 0);
/*    */   }
/*    */   
/*    */   private static OptionalInt getFloorY(WorldGenLevel $$0, BlockPos $$1, UnderwaterMagmaConfiguration $$2) {
/* 56 */     Predicate<BlockState> $$3 = $$0 -> $$0.is(Blocks.WATER);
/* 57 */     Predicate<BlockState> $$4 = $$0 -> !$$0.is(Blocks.WATER);
/* 58 */     Optional<Column> $$5 = Column.scan((LevelSimulatedReader)$$0, $$1, $$2.floorSearchRange, $$3, $$4);
/* 59 */     return $$5.<OptionalInt>map(Column::getFloor).orElseGet(OptionalInt::empty);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isValidPlacement(WorldGenLevel $$0, BlockPos $$1) {
/* 66 */     if (isWaterOrAir((LevelAccessor)$$0, $$1) || isWaterOrAir((LevelAccessor)$$0, $$1.below())) {
/* 67 */       return false;
/*    */     }
/* 69 */     for (Direction $$2 : Direction.Plane.HORIZONTAL) {
/* 70 */       if (isWaterOrAir((LevelAccessor)$$0, $$1.relative($$2))) {
/* 71 */         return false;
/*    */       }
/*    */     } 
/* 74 */     return true;
/*    */   }
/*    */   
/*    */   private boolean isWaterOrAir(LevelAccessor $$0, BlockPos $$1) {
/* 78 */     BlockState $$2 = $$0.getBlockState($$1);
/* 79 */     return ($$2.is(Blocks.WATER) || $$2.isAir());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\UnderwaterMagmaFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */