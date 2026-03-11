/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.BuddingAmethystBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.GeodeBlockSettings;
/*     */ import net.minecraft.world.level.levelgen.GeodeCrackSettings;
/*     */ import net.minecraft.world.level.levelgen.GeodeLayerSettings;
/*     */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ 
/*     */ public class GeodeFeature extends Feature<GeodeConfiguration> {
/*  30 */   private static final Direction[] DIRECTIONS = Direction.values();
/*     */   
/*     */   public GeodeFeature(Codec<GeodeConfiguration> $$0) {
/*  33 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<GeodeConfiguration> $$0) {
/*  38 */     GeodeConfiguration $$1 = $$0.config();
/*  39 */     RandomSource $$2 = $$0.random();
/*  40 */     BlockPos $$3 = $$0.origin();
/*  41 */     WorldGenLevel $$4 = $$0.level();
/*  42 */     int $$5 = $$1.minGenOffset;
/*  43 */     int $$6 = $$1.maxGenOffset;
/*     */     
/*  45 */     List<Pair<BlockPos, Integer>> $$7 = Lists.newLinkedList();
/*  46 */     int $$8 = $$1.distributionPoints.sample($$2);
/*  47 */     WorldgenRandom $$9 = new WorldgenRandom((RandomSource)new LegacyRandomSource($$4.getSeed()));
/*  48 */     NormalNoise $$10 = NormalNoise.create((RandomSource)$$9, -4, new double[] { 1.0D });
/*  49 */     List<BlockPos> $$11 = Lists.newLinkedList();
/*  50 */     double $$12 = $$8 / $$1.outerWallDistance.getMaxValue();
/*  51 */     GeodeLayerSettings $$13 = $$1.geodeLayerSettings;
/*  52 */     GeodeBlockSettings $$14 = $$1.geodeBlockSettings;
/*  53 */     GeodeCrackSettings $$15 = $$1.geodeCrackSettings;
/*  54 */     double $$16 = 1.0D / Math.sqrt($$13.filling);
/*  55 */     double $$17 = 1.0D / Math.sqrt($$13.innerLayer + $$12);
/*  56 */     double $$18 = 1.0D / Math.sqrt($$13.middleLayer + $$12);
/*  57 */     double $$19 = 1.0D / Math.sqrt($$13.outerLayer + $$12);
/*  58 */     double $$20 = 1.0D / Math.sqrt($$15.baseCrackSize + $$2.nextDouble() / 2.0D + (($$8 > 3) ? $$12 : 0.0D));
/*  59 */     boolean $$21 = ($$2.nextFloat() < $$15.generateCrackChance);
/*     */     
/*  61 */     int $$22 = 0;
/*  62 */     for (int $$23 = 0; $$23 < $$8; $$23++) {
/*  63 */       int $$24 = $$1.outerWallDistance.sample($$2);
/*  64 */       int $$25 = $$1.outerWallDistance.sample($$2);
/*  65 */       int $$26 = $$1.outerWallDistance.sample($$2);
/*  66 */       BlockPos $$27 = $$3.offset($$24, $$25, $$26);
/*  67 */       BlockState $$28 = $$4.getBlockState($$27);
/*  68 */       if (($$28.isAir() || $$28.is(BlockTags.GEODE_INVALID_BLOCKS)) && 
/*  69 */         ++$$22 > $$1.invalidBlocksThreshold) {
/*  70 */         return false;
/*     */       }
/*     */       
/*  73 */       $$7.add(Pair.of($$27, Integer.valueOf($$1.pointOffset.sample($$2))));
/*     */     } 
/*     */     
/*  76 */     if ($$21) {
/*  77 */       int $$29 = $$2.nextInt(4);
/*     */       
/*  79 */       int $$30 = $$8 * 2 + 1;
/*  80 */       if ($$29 == 0) {
/*  81 */         $$11.add($$3.offset($$30, 7, 0));
/*  82 */         $$11.add($$3.offset($$30, 5, 0));
/*  83 */         $$11.add($$3.offset($$30, 1, 0));
/*  84 */       } else if ($$29 == 1) {
/*  85 */         $$11.add($$3.offset(0, 7, $$30));
/*  86 */         $$11.add($$3.offset(0, 5, $$30));
/*  87 */         $$11.add($$3.offset(0, 1, $$30));
/*  88 */       } else if ($$29 == 2) {
/*  89 */         $$11.add($$3.offset($$30, 7, $$30));
/*  90 */         $$11.add($$3.offset($$30, 5, $$30));
/*  91 */         $$11.add($$3.offset($$30, 1, $$30));
/*     */       } else {
/*  93 */         $$11.add($$3.offset(0, 7, 0));
/*  94 */         $$11.add($$3.offset(0, 5, 0));
/*  95 */         $$11.add($$3.offset(0, 1, 0));
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     List<BlockPos> $$31 = Lists.newArrayList();
/* 100 */     Predicate<BlockState> $$32 = isReplaceable($$1.geodeBlockSettings.cannotReplace);
/*     */     
/* 102 */     for (BlockPos $$33 : BlockPos.betweenClosed($$3.offset($$5, $$5, $$5), $$3.offset($$6, $$6, $$6))) {
/* 103 */       double $$34 = $$10.getValue($$33.getX(), $$33.getY(), $$33.getZ()) * $$1.noiseMultiplier;
/*     */       
/* 105 */       double $$35 = 0.0D;
/* 106 */       double $$36 = 0.0D;
/*     */       
/* 108 */       for (Pair<BlockPos, Integer> $$37 : $$7) {
/* 109 */         $$35 += Mth.invSqrt($$33.distSqr((Vec3i)$$37.getFirst()) + ((Integer)$$37.getSecond()).intValue()) + $$34;
/*     */       }
/*     */       
/* 112 */       for (BlockPos $$38 : $$11) {
/* 113 */         $$36 += Mth.invSqrt($$33.distSqr((Vec3i)$$38) + $$15.crackPointOffset) + $$34;
/*     */       }
/*     */ 
/*     */       
/* 117 */       if ($$35 < $$19) {
/*     */         continue;
/*     */       }
/*     */       
/* 121 */       if ($$21 && $$36 >= $$20 && $$35 < $$16) {
/*     */         
/* 123 */         safeSetBlock($$4, $$33, Blocks.AIR.defaultBlockState(), $$32);
/*     */ 
/*     */         
/* 126 */         for (Direction $$39 : DIRECTIONS) {
/* 127 */           BlockPos $$40 = $$33.relative($$39);
/* 128 */           FluidState $$41 = $$4.getFluidState($$40);
/* 129 */           if (!$$41.isEmpty())
/* 130 */             $$4.scheduleTick($$40, $$41.getType(), 0); 
/*     */         }  continue;
/*     */       } 
/* 133 */       if ($$35 >= $$16) {
/* 134 */         safeSetBlock($$4, $$33, $$14.fillingProvider.getState($$2, $$33), $$32); continue;
/* 135 */       }  if ($$35 >= $$17) {
/* 136 */         boolean $$42 = ($$2.nextFloat() < $$1.useAlternateLayer0Chance);
/* 137 */         if ($$42) {
/* 138 */           safeSetBlock($$4, $$33, $$14.alternateInnerLayerProvider.getState($$2, $$33), $$32);
/*     */         } else {
/* 140 */           safeSetBlock($$4, $$33, $$14.innerLayerProvider.getState($$2, $$33), $$32);
/*     */         } 
/*     */         
/* 143 */         if ((!$$1.placementsRequireLayer0Alternate || $$42) && $$2.nextFloat() < $$1.usePotentialPlacementsChance)
/* 144 */           $$31.add($$33.immutable());  continue;
/*     */       } 
/* 146 */       if ($$35 >= $$18) {
/* 147 */         safeSetBlock($$4, $$33, $$14.middleLayerProvider.getState($$2, $$33), $$32); continue;
/* 148 */       }  if ($$35 >= $$19) {
/* 149 */         safeSetBlock($$4, $$33, $$14.outerLayerProvider.getState($$2, $$33), $$32);
/*     */       }
/*     */     } 
/*     */     
/* 153 */     List<BlockState> $$43 = $$14.innerPlacements;
/* 154 */     for (BlockPos $$44 : $$31) {
/* 155 */       BlockState $$45 = (BlockState)Util.getRandom($$43, $$2);
/* 156 */       for (Direction $$46 : DIRECTIONS) {
/* 157 */         if ($$45.hasProperty((Property)BlockStateProperties.FACING)) {
/* 158 */           $$45 = (BlockState)$$45.setValue((Property)BlockStateProperties.FACING, (Comparable)$$46);
/*     */         }
/*     */         
/* 161 */         BlockPos $$47 = $$44.relative($$46);
/* 162 */         BlockState $$48 = $$4.getBlockState($$47);
/* 163 */         if ($$45.hasProperty((Property)BlockStateProperties.WATERLOGGED)) {
/* 164 */           $$45 = (BlockState)$$45.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf($$48.getFluidState().isSource()));
/*     */         }
/*     */         
/* 167 */         if (BuddingAmethystBlock.canClusterGrowAtState($$48)) {
/* 168 */           safeSetBlock($$4, $$47, $$45, $$32);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 174 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\GeodeFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */