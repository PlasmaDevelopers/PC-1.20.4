/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.ClampedNormalFloat;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.Column;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DripstoneClusterFeature
/*     */   extends Feature<DripstoneClusterConfiguration>
/*     */ {
/*     */   public DripstoneClusterFeature(Codec<DripstoneClusterConfiguration> $$0) {
/*  30 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<DripstoneClusterConfiguration> $$0) {
/*  35 */     WorldGenLevel $$1 = $$0.level();
/*  36 */     BlockPos $$2 = $$0.origin();
/*  37 */     DripstoneClusterConfiguration $$3 = $$0.config();
/*  38 */     RandomSource $$4 = $$0.random();
/*     */     
/*  40 */     if (!DripstoneUtils.isEmptyOrWater((LevelAccessor)$$1, $$2)) {
/*  41 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  45 */     int $$5 = $$3.height.sample($$4);
/*     */     
/*  47 */     float $$6 = $$3.wetness.sample($$4);
/*  48 */     float $$7 = $$3.density.sample($$4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     int $$8 = $$3.radius.sample($$4);
/*  54 */     int $$9 = $$3.radius.sample($$4);
/*  55 */     for (int $$10 = -$$8; $$10 <= $$8; $$10++) {
/*  56 */       for (int $$11 = -$$9; $$11 <= $$9; $$11++) {
/*  57 */         double $$12 = getChanceOfStalagmiteOrStalactite($$8, $$9, $$10, $$11, $$3);
/*  58 */         BlockPos $$13 = $$2.offset($$10, 0, $$11);
/*  59 */         placeColumn($$1, $$4, $$13, $$10, $$11, $$6, $$12, $$5, $$7, $$3);
/*     */       } 
/*     */     } 
/*  62 */     return true;
/*     */   }
/*     */   private void placeColumn(WorldGenLevel $$0, RandomSource $$1, BlockPos $$2, int $$3, int $$4, float $$5, double $$6, int $$7, float $$8, DripstoneClusterConfiguration $$9) {
/*     */     Column $$16;
/*     */     int $$23, $$28, $$37, $$38;
/*  67 */     Optional<Column> $$10 = Column.scan((LevelSimulatedReader)$$0, $$2, $$9.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isNeitherEmptyNorWater);
/*  68 */     if ($$10.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     OptionalInt $$11 = ((Column)$$10.get()).getCeiling();
/*  73 */     OptionalInt $$12 = ((Column)$$10.get()).getFloor();
/*     */     
/*  75 */     if ($$11.isEmpty() && $$12.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  81 */     boolean $$13 = ($$1.nextFloat() < $$5);
/*     */     
/*  83 */     if ($$13 && $$12.isPresent() && canPlacePool($$0, $$2.atY($$12.getAsInt()))) {
/*     */       
/*  85 */       int $$14 = $$12.getAsInt();
/*  86 */       Column $$15 = ((Column)$$10.get()).withFloor(OptionalInt.of($$14 - 1));
/*  87 */       $$0.setBlock($$2.atY($$14), Blocks.WATER.defaultBlockState(), 2);
/*     */     } else {
/*  89 */       $$16 = $$10.get();
/*     */     } 
/*     */     
/*  92 */     OptionalInt $$17 = $$16.getFloor();
/*     */ 
/*     */ 
/*     */     
/*  96 */     boolean $$18 = ($$1.nextDouble() < $$6);
/*  97 */     if ($$11.isPresent() && $$18 && !isLava((LevelReader)$$0, $$2.atY($$11.getAsInt()))) {
/*  98 */       int $$21, $$19 = $$9.dripstoneBlockLayerThickness.sample($$1);
/*  99 */       replaceBlocksWithDripstoneBlocks($$0, $$2.atY($$11.getAsInt()), $$19, Direction.UP);
/*     */       
/* 101 */       if ($$17.isPresent()) {
/* 102 */         int $$20 = Math.min($$7, $$11.getAsInt() - $$17.getAsInt());
/*     */       } else {
/* 104 */         $$21 = $$7;
/*     */       } 
/* 106 */       int $$22 = getDripstoneHeight($$1, $$3, $$4, $$8, $$21, $$9);
/*     */     } else {
/* 108 */       $$23 = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 113 */     boolean $$24 = ($$1.nextDouble() < $$6);
/* 114 */     if ($$17.isPresent() && $$24 && !isLava((LevelReader)$$0, $$2.atY($$17.getAsInt()))) {
/* 115 */       int $$25 = $$9.dripstoneBlockLayerThickness.sample($$1);
/* 116 */       replaceBlocksWithDripstoneBlocks($$0, $$2.atY($$17.getAsInt()), $$25, Direction.DOWN);
/*     */       
/* 118 */       if ($$11.isPresent()) {
/* 119 */         int $$26 = Math.max(0, $$23 + Mth.randomBetweenInclusive($$1, -$$9.maxStalagmiteStalactiteHeightDiff, $$9.maxStalagmiteStalactiteHeightDiff));
/*     */       } else {
/*     */         
/* 122 */         int $$27 = getDripstoneHeight($$1, $$3, $$4, $$8, $$7, $$9);
/*     */       } 
/*     */     } else {
/* 125 */       $$28 = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     if ($$11.isPresent() && $$17.isPresent() && $$11.getAsInt() - $$23 <= $$17.getAsInt() + $$28) {
/*     */ 
/*     */       
/* 134 */       int $$29 = $$17.getAsInt();
/* 135 */       int $$30 = $$11.getAsInt();
/* 136 */       int $$31 = Math.max($$30 - $$23, $$29 + 1);
/* 137 */       int $$32 = Math.min($$29 + $$28, $$30 - 1);
/* 138 */       int $$33 = Mth.randomBetweenInclusive($$1, $$31, $$32 + 1);
/* 139 */       int $$34 = $$33 - 1;
/* 140 */       int $$35 = $$30 - $$33;
/* 141 */       int $$36 = $$34 - $$29;
/*     */     } else {
/* 143 */       $$37 = $$23;
/* 144 */       $$38 = $$28;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     boolean $$39 = ($$1.nextBoolean() && $$37 > 0 && $$38 > 0 && $$16.getHeight().isPresent() && $$37 + $$38 == $$16.getHeight().getAsInt());
/*     */     
/* 153 */     if ($$11.isPresent()) {
/* 154 */       DripstoneUtils.growPointedDripstone((LevelAccessor)$$0, $$2.atY($$11.getAsInt() - 1), Direction.DOWN, $$37, $$39);
/*     */     }
/* 156 */     if ($$17.isPresent()) {
/* 157 */       DripstoneUtils.growPointedDripstone((LevelAccessor)$$0, $$2.atY($$17.getAsInt() + 1), Direction.UP, $$38, $$39);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isLava(LevelReader $$0, BlockPos $$1) {
/* 162 */     return $$0.getBlockState($$1).is(Blocks.LAVA);
/*     */   }
/*     */   
/*     */   private int getDripstoneHeight(RandomSource $$0, int $$1, int $$2, float $$3, int $$4, DripstoneClusterConfiguration $$5) {
/* 166 */     if ($$0.nextFloat() > $$3) {
/* 167 */       return 0;
/*     */     }
/*     */     
/* 170 */     int $$6 = Math.abs($$1) + Math.abs($$2);
/*     */ 
/*     */     
/* 173 */     float $$7 = (float)Mth.clampedMap($$6, 0.0D, $$5.maxDistanceFromCenterAffectingHeightBias, $$4 / 2.0D, 0.0D);
/* 174 */     return (int)randomBetweenBiased($$0, 0.0F, $$4, $$7, $$5.heightDeviation);
/*     */   }
/*     */   
/*     */   private boolean canPlacePool(WorldGenLevel $$0, BlockPos $$1) {
/* 178 */     BlockState $$2 = $$0.getBlockState($$1);
/* 179 */     if ($$2.is(Blocks.WATER) || $$2.is(Blocks.DRIPSTONE_BLOCK) || $$2.is(Blocks.POINTED_DRIPSTONE)) {
/* 180 */       return false;
/*     */     }
/* 182 */     if ($$0.getBlockState($$1.above()).getFluidState().is(FluidTags.WATER)) {
/* 183 */       return false;
/*     */     }
/*     */     
/* 186 */     for (Direction $$3 : Direction.Plane.HORIZONTAL) {
/* 187 */       if (!canBeAdjacentToWater((LevelAccessor)$$0, $$1.relative($$3))) {
/* 188 */         return false;
/*     */       }
/*     */     } 
/* 191 */     return canBeAdjacentToWater((LevelAccessor)$$0, $$1.below());
/*     */   }
/*     */   
/*     */   private boolean canBeAdjacentToWater(LevelAccessor $$0, BlockPos $$1) {
/* 195 */     BlockState $$2 = $$0.getBlockState($$1);
/* 196 */     return ($$2.is(BlockTags.BASE_STONE_OVERWORLD) || $$2.getFluidState().is(FluidTags.WATER));
/*     */   }
/*     */   
/*     */   private void replaceBlocksWithDripstoneBlocks(WorldGenLevel $$0, BlockPos $$1, int $$2, Direction $$3) {
/* 200 */     BlockPos.MutableBlockPos $$4 = $$1.mutable();
/* 201 */     for (int $$5 = 0; $$5 < $$2; $$5++) {
/* 202 */       if (!DripstoneUtils.placeDripstoneBlockIfPossible((LevelAccessor)$$0, (BlockPos)$$4)) {
/*     */         return;
/*     */       }
/* 205 */       $$4.move($$3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getChanceOfStalagmiteOrStalactite(int $$0, int $$1, int $$2, int $$3, DripstoneClusterConfiguration $$4) {
/* 213 */     int $$5 = $$0 - Math.abs($$2);
/* 214 */     int $$6 = $$1 - Math.abs($$3);
/* 215 */     int $$7 = Math.min($$5, $$6);
/*     */     
/* 217 */     return Mth.clampedMap($$7, 0.0F, $$4.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn, $$4.chanceOfDripstoneColumnAtMaxDistanceFromCenter, 1.0F);
/*     */   }
/*     */   
/*     */   private static float randomBetweenBiased(RandomSource $$0, float $$1, float $$2, float $$3, float $$4) {
/* 221 */     return ClampedNormalFloat.sample($$0, $$3, $$4, $$1, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\DripstoneClusterFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */