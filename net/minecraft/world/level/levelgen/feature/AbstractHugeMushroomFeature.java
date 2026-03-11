/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelWriter;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
/*    */ 
/*    */ public abstract class AbstractHugeMushroomFeature extends Feature<HugeMushroomFeatureConfiguration> {
/*    */   public AbstractHugeMushroomFeature(Codec<HugeMushroomFeatureConfiguration> $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */   
/*    */   protected void placeTrunk(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, HugeMushroomFeatureConfiguration $$3, int $$4, BlockPos.MutableBlockPos $$5) {
/* 19 */     for (int $$6 = 0; $$6 < $$4; $$6++) {
/* 20 */       $$5.set((Vec3i)$$2).move(Direction.UP, $$6);
/* 21 */       if (!$$0.getBlockState((BlockPos)$$5).isSolidRender((BlockGetter)$$0, (BlockPos)$$5)) {
/* 22 */         setBlock((LevelWriter)$$0, (BlockPos)$$5, $$3.stemProvider.getState($$1, $$2));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected int getTreeHeight(RandomSource $$0) {
/* 28 */     int $$1 = $$0.nextInt(3) + 4;
/* 29 */     if ($$0.nextInt(12) == 0) {
/* 30 */       $$1 *= 2;
/*    */     }
/* 32 */     return $$1;
/*    */   }
/*    */   
/*    */   protected boolean isValidPosition(LevelAccessor $$0, BlockPos $$1, int $$2, BlockPos.MutableBlockPos $$3, HugeMushroomFeatureConfiguration $$4) {
/* 36 */     int $$5 = $$1.getY();
/* 37 */     if ($$5 < $$0.getMinBuildHeight() + 1 || $$5 + $$2 + 1 >= $$0.getMaxBuildHeight()) {
/* 38 */       return false;
/*    */     }
/*    */     
/* 41 */     BlockState $$6 = $$0.getBlockState($$1.below());
/* 42 */     if (!isDirt($$6) && !$$6.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
/* 43 */       return false;
/*    */     }
/*    */     
/* 46 */     for (int $$7 = 0; $$7 <= $$2; $$7++) {
/* 47 */       int $$8 = getTreeRadiusForHeight(-1, -1, $$4.foliageRadius, $$7);
/* 48 */       for (int $$9 = -$$8; $$9 <= $$8; $$9++) {
/* 49 */         for (int $$10 = -$$8; $$10 <= $$8; $$10++) {
/* 50 */           BlockState $$11 = $$0.getBlockState((BlockPos)$$3.setWithOffset((Vec3i)$$1, $$9, $$7, $$10));
/* 51 */           if (!$$11.isAir() && !$$11.is(BlockTags.LEAVES)) {
/* 52 */             return false;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> $$0) {
/* 62 */     WorldGenLevel $$1 = $$0.level();
/* 63 */     BlockPos $$2 = $$0.origin();
/* 64 */     RandomSource $$3 = $$0.random();
/* 65 */     HugeMushroomFeatureConfiguration $$4 = $$0.config();
/* 66 */     int $$5 = getTreeHeight($$3);
/*    */     
/* 68 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/* 69 */     if (!isValidPosition((LevelAccessor)$$1, $$2, $$5, $$6, $$4)) {
/* 70 */       return false;
/*    */     }
/*    */     
/* 73 */     makeCap((LevelAccessor)$$1, $$3, $$2, $$5, $$6, $$4);
/* 74 */     placeTrunk((LevelAccessor)$$1, $$3, $$2, $$4, $$5, $$6);
/* 75 */     return true;
/*    */   }
/*    */   
/*    */   protected abstract int getTreeRadiusForHeight(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*    */   
/*    */   protected abstract void makeCap(LevelAccessor paramLevelAccessor, RandomSource paramRandomSource, BlockPos paramBlockPos, int paramInt, BlockPos.MutableBlockPos paramMutableBlockPos, HugeMushroomFeatureConfiguration paramHugeMushroomFeatureConfiguration);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\AbstractHugeMushroomFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */