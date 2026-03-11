/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.serialization.Codec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelWriter;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
/*     */ 
/*     */ public class BasaltColumnsFeature extends Feature<ColumnFeatureConfiguration> {
/*  18 */   private static final ImmutableList<Block> CANNOT_PLACE_ON = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.SOUL_SAND, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);
/*     */ 
/*     */   
/*     */   private static final int CLUSTERED_REACH = 5;
/*     */ 
/*     */   
/*     */   private static final int CLUSTERED_SIZE = 50;
/*     */   
/*     */   private static final int UNCLUSTERED_REACH = 8;
/*     */   
/*     */   private static final int UNCLUSTERED_SIZE = 15;
/*     */ 
/*     */   
/*     */   public BasaltColumnsFeature(Codec<ColumnFeatureConfiguration> $$0) {
/*  32 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<ColumnFeatureConfiguration> $$0) {
/*  37 */     int $$1 = $$0.chunkGenerator().getSeaLevel();
/*  38 */     BlockPos $$2 = $$0.origin();
/*  39 */     WorldGenLevel $$3 = $$0.level();
/*  40 */     RandomSource $$4 = $$0.random();
/*  41 */     ColumnFeatureConfiguration $$5 = $$0.config();
/*  42 */     if (!canPlaceAt((LevelAccessor)$$3, $$1, $$2.mutable())) {
/*  43 */       return false;
/*     */     }
/*     */     
/*  46 */     int $$6 = $$5.height().sample($$4);
/*     */     
/*  48 */     boolean $$7 = ($$4.nextFloat() < 0.9F);
/*  49 */     int $$8 = Math.min($$6, $$7 ? 5 : 8);
/*  50 */     int $$9 = $$7 ? 50 : 15;
/*     */ 
/*     */     
/*  53 */     boolean $$10 = false;
/*  54 */     for (BlockPos $$11 : BlockPos.randomBetweenClosed($$4, $$9, $$2.getX() - $$8, $$2.getY(), $$2.getZ() - $$8, $$2.getX() + $$8, $$2.getY(), $$2.getZ() + $$8)) {
/*  55 */       int $$12 = $$6 - $$11.distManhattan((Vec3i)$$2);
/*  56 */       if ($$12 >= 0) {
/*  57 */         $$10 |= placeColumn((LevelAccessor)$$3, $$1, $$11, $$12, $$5.reach().sample($$4));
/*     */       }
/*     */     } 
/*     */     
/*  61 */     return $$10;
/*     */   }
/*     */   
/*     */   private boolean placeColumn(LevelAccessor $$0, int $$1, BlockPos $$2, int $$3, int $$4) {
/*  65 */     boolean $$5 = false;
/*     */     
/*  67 */     for (BlockPos $$6 : BlockPos.betweenClosed($$2.getX() - $$4, $$2.getY(), $$2.getZ() - $$4, $$2.getX() + $$4, $$2.getY(), $$2.getZ() + $$4)) {
/*  68 */       int $$7 = $$6.distManhattan((Vec3i)$$2);
/*     */ 
/*     */ 
/*     */       
/*  72 */       BlockPos $$8 = isAirOrLavaOcean($$0, $$1, $$6) ? findSurface($$0, $$1, $$6.mutable(), $$7) : findAir($$0, $$6.mutable(), $$7);
/*  73 */       if ($$8 == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  77 */       int $$9 = $$3 - $$7 / 2;
/*  78 */       BlockPos.MutableBlockPos $$10 = $$8.mutable();
/*  79 */       while ($$9 >= 0) {
/*  80 */         if (isAirOrLavaOcean($$0, $$1, (BlockPos)$$10)) {
/*  81 */           setBlock((LevelWriter)$$0, (BlockPos)$$10, Blocks.BASALT.defaultBlockState());
/*  82 */           $$10.move(Direction.UP);
/*  83 */           $$5 = true;
/*  84 */         } else if ($$0.getBlockState((BlockPos)$$10).is(Blocks.BASALT)) {
/*  85 */           $$10.move(Direction.UP);
/*     */         } else {
/*     */           break;
/*     */         } 
/*     */         
/*  90 */         $$9--;
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return $$5;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BlockPos findSurface(LevelAccessor $$0, int $$1, BlockPos.MutableBlockPos $$2, int $$3) {
/*  99 */     while ($$2.getY() > $$0.getMinBuildHeight() + 1 && $$3 > 0) {
/* 100 */       $$3--;
/* 101 */       if (canPlaceAt($$0, $$1, $$2)) {
/* 102 */         return (BlockPos)$$2;
/*     */       }
/* 104 */       $$2.move(Direction.DOWN);
/*     */     } 
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean canPlaceAt(LevelAccessor $$0, int $$1, BlockPos.MutableBlockPos $$2) {
/* 110 */     if (isAirOrLavaOcean($$0, $$1, (BlockPos)$$2)) {
/* 111 */       BlockState $$3 = $$0.getBlockState((BlockPos)$$2.move(Direction.DOWN));
/* 112 */       $$2.move(Direction.UP);
/* 113 */       return (!$$3.isAir() && !CANNOT_PLACE_ON.contains($$3.getBlock()));
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BlockPos findAir(LevelAccessor $$0, BlockPos.MutableBlockPos $$1, int $$2) {
/* 120 */     while ($$1.getY() < $$0.getMaxBuildHeight() && $$2 > 0) {
/* 121 */       $$2--;
/*     */       
/* 123 */       BlockState $$3 = $$0.getBlockState((BlockPos)$$1);
/* 124 */       if (CANNOT_PLACE_ON.contains($$3.getBlock())) {
/* 125 */         return null;
/*     */       }
/*     */       
/* 128 */       if ($$3.isAir()) {
/* 129 */         return (BlockPos)$$1;
/*     */       }
/*     */       
/* 132 */       $$1.move(Direction.UP);
/*     */     } 
/* 134 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean isAirOrLavaOcean(LevelAccessor $$0, int $$1, BlockPos $$2) {
/* 138 */     BlockState $$3 = $$0.getBlockState($$2);
/* 139 */     return ($$3.isAir() || ($$3.is(Blocks.LAVA) && $$2.getY() <= $$1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\BasaltColumnsFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */