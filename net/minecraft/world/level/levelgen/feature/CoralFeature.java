/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.BaseCoralWallFanBlock;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.SeaPickleBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public abstract class CoralFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public CoralFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 23 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 28 */     RandomSource $$1 = $$0.random();
/* 29 */     WorldGenLevel $$2 = $$0.level();
/* 30 */     BlockPos $$3 = $$0.origin();
/* 31 */     Optional<Block> $$4 = BuiltInRegistries.BLOCK.getTag(BlockTags.CORAL_BLOCKS).flatMap($$1 -> $$1.getRandomElement($$0)).map(Holder::value);
/* 32 */     if ($$4.isEmpty()) {
/* 33 */       return false;
/*    */     }
/* 35 */     return placeFeature((LevelAccessor)$$2, $$1, $$3, ((Block)$$4.get()).defaultBlockState());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean placeCoralBlock(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 41 */     BlockPos $$4 = $$2.above();
/* 42 */     BlockState $$5 = $$0.getBlockState($$2);
/*    */     
/* 44 */     if ((!$$5.is(Blocks.WATER) && !$$5.is(BlockTags.CORALS)) || !$$0.getBlockState($$4).is(Blocks.WATER)) {
/* 45 */       return false;
/*    */     }
/*    */     
/* 48 */     $$0.setBlock($$2, $$3, 3);
/* 49 */     if ($$1.nextFloat() < 0.25F) {
/* 50 */       BuiltInRegistries.BLOCK.getTag(BlockTags.CORALS).flatMap($$1 -> $$1.getRandomElement($$0)).map(Holder::value).ifPresent($$2 -> $$0.setBlock($$1, $$2.defaultBlockState(), 2));
/*    */     
/*    */     }
/* 53 */     else if ($$1.nextFloat() < 0.05F) {
/* 54 */       $$0.setBlock($$4, (BlockState)Blocks.SEA_PICKLE.defaultBlockState().setValue((Property)SeaPickleBlock.PICKLES, Integer.valueOf($$1.nextInt(4) + 1)), 2);
/*    */     } 
/*    */     
/* 57 */     for (Direction $$6 : Direction.Plane.HORIZONTAL) {
/* 58 */       if ($$1.nextFloat() < 0.2F) {
/* 59 */         BlockPos $$7 = $$2.relative($$6);
/* 60 */         if ($$0.getBlockState($$7).is(Blocks.WATER)) {
/* 61 */           BuiltInRegistries.BLOCK.getTag(BlockTags.WALL_CORALS).flatMap($$1 -> $$1.getRandomElement($$0)).map(Holder::value).ifPresent($$3 -> {
/*    */                 BlockState $$4 = $$3.defaultBlockState();
/*    */                 
/*    */                 if ($$4.hasProperty((Property)BaseCoralWallFanBlock.FACING)) {
/*    */                   $$4 = (BlockState)$$4.setValue((Property)BaseCoralWallFanBlock.FACING, (Comparable)$$0);
/*    */                 }
/*    */                 $$1.setBlock($$2, $$4, 2);
/*    */               });
/*    */         }
/*    */       } 
/*    */     } 
/* 72 */     return true;
/*    */   }
/*    */   
/*    */   protected abstract boolean placeFeature(LevelAccessor paramLevelAccessor, RandomSource paramRandomSource, BlockPos paramBlockPos, BlockState paramBlockState);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\CoralFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */