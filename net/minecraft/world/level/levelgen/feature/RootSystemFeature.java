/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;
/*     */ 
/*     */ public class RootSystemFeature extends Feature<RootSystemConfiguration> {
/*     */   public RootSystemFeature(Codec<RootSystemConfiguration> $$0) {
/*  18 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<RootSystemConfiguration> $$0) {
/*  23 */     WorldGenLevel $$1 = $$0.level();
/*  24 */     BlockPos $$2 = $$0.origin();
/*  25 */     if (!$$1.getBlockState($$2).isAir()) {
/*  26 */       return false;
/*     */     }
/*     */     
/*  29 */     RandomSource $$3 = $$0.random();
/*  30 */     BlockPos $$4 = $$0.origin();
/*  31 */     RootSystemConfiguration $$5 = $$0.config();
/*  32 */     BlockPos.MutableBlockPos $$6 = $$4.mutable();
/*  33 */     if (placeDirtAndTree($$1, $$0.chunkGenerator(), $$5, $$3, $$6, $$4)) {
/*  34 */       placeRoots($$1, $$5, $$3, $$4, $$6);
/*     */     }
/*     */     
/*  37 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean spaceForTree(WorldGenLevel $$0, RootSystemConfiguration $$1, BlockPos $$2) {
/*  41 */     BlockPos.MutableBlockPos $$3 = $$2.mutable();
/*  42 */     for (int $$4 = 1; $$4 <= $$1.requiredVerticalSpaceForTree; $$4++) {
/*  43 */       $$3.move(Direction.UP);
/*  44 */       BlockState $$5 = $$0.getBlockState((BlockPos)$$3);
/*  45 */       if (!isAllowedTreeSpace($$5, $$4, $$1.allowedVerticalWaterForTree)) {
/*  46 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  50 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean isAllowedTreeSpace(BlockState $$0, int $$1, int $$2) {
/*  54 */     if ($$0.isAir()) {
/*  55 */       return true;
/*     */     }
/*  57 */     int $$3 = $$1 + 1;
/*  58 */     return ($$3 <= $$2 && $$0.getFluidState().is(FluidTags.WATER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean placeDirtAndTree(WorldGenLevel $$0, ChunkGenerator $$1, RootSystemConfiguration $$2, RandomSource $$3, BlockPos.MutableBlockPos $$4, BlockPos $$5) {
/*  65 */     for (int $$6 = 0; $$6 < $$2.rootColumnMaxHeight; $$6++) {
/*  66 */       $$4.move(Direction.UP);
/*     */       
/*  68 */       if ($$2.allowedTreePosition.test($$0, $$4) && 
/*  69 */         spaceForTree($$0, $$2, (BlockPos)$$4)) {
/*  70 */         BlockPos $$7 = $$4.below();
/*  71 */         if ($$0.getFluidState($$7).is(FluidTags.LAVA) || !$$0.getBlockState($$7).isSolid()) {
/*  72 */           return false;
/*     */         }
/*     */         
/*  75 */         if (((PlacedFeature)$$2.treeFeature.value()).place($$0, $$1, $$3, (BlockPos)$$4)) {
/*  76 */           placeDirt($$5, $$5.getY() + $$6, $$0, $$2, $$3);
/*  77 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   private static void placeDirt(BlockPos $$0, int $$1, WorldGenLevel $$2, RootSystemConfiguration $$3, RandomSource $$4) {
/*  86 */     int $$5 = $$0.getX();
/*  87 */     int $$6 = $$0.getZ();
/*  88 */     BlockPos.MutableBlockPos $$7 = $$0.mutable();
/*  89 */     for (int $$8 = $$0.getY(); $$8 < $$1; $$8++) {
/*  90 */       placeRootedDirt($$2, $$3, $$4, $$5, $$6, $$7.set($$5, $$8, $$6));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void placeRootedDirt(WorldGenLevel $$0, RootSystemConfiguration $$1, RandomSource $$2, int $$3, int $$4, BlockPos.MutableBlockPos $$5) {
/*  95 */     int $$6 = $$1.rootRadius;
/*  96 */     Predicate<BlockState> $$7 = $$1 -> $$1.is($$0.rootReplaceable);
/*  97 */     for (int $$8 = 0; $$8 < $$1.rootPlacementAttempts; $$8++) {
/*  98 */       $$5.setWithOffset((Vec3i)$$5, $$2.nextInt($$6) - $$2.nextInt($$6), 0, $$2.nextInt($$6) - $$2.nextInt($$6));
/*  99 */       if ($$7.test($$0.getBlockState((BlockPos)$$5))) {
/* 100 */         $$0.setBlock((BlockPos)$$5, $$1.rootStateProvider.getState($$2, (BlockPos)$$5), 2);
/*     */       }
/*     */       
/* 103 */       $$5.setX($$3);
/* 104 */       $$5.setZ($$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void placeRoots(WorldGenLevel $$0, RootSystemConfiguration $$1, RandomSource $$2, BlockPos $$3, BlockPos.MutableBlockPos $$4) {
/* 112 */     int $$5 = $$1.hangingRootRadius;
/* 113 */     int $$6 = $$1.hangingRootsVerticalSpan;
/* 114 */     for (int $$7 = 0; $$7 < $$1.hangingRootPlacementAttempts; $$7++) {
/* 115 */       $$4.setWithOffset((Vec3i)$$3, $$2.nextInt($$5) - $$2.nextInt($$5), $$2.nextInt($$6) - $$2.nextInt($$6), $$2.nextInt($$5) - $$2.nextInt($$5));
/* 116 */       if ($$0.isEmptyBlock((BlockPos)$$4)) {
/* 117 */         BlockState $$8 = $$1.hangingRootStateProvider.getState($$2, (BlockPos)$$4);
/* 118 */         if ($$8.canSurvive((LevelReader)$$0, (BlockPos)$$4) && $$0.getBlockState($$4.above()).isFaceSturdy((BlockGetter)$$0, (BlockPos)$$4, Direction.DOWN))
/* 119 */           $$0.setBlock((BlockPos)$$4, $$8, 2); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\RootSystemFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */