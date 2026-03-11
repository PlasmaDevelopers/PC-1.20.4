/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelWriter;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
/*    */ 
/*    */ public class DeltaFeature extends Feature<DeltaFeatureConfiguration> {
/* 16 */   private static final ImmutableList<Block> CANNOT_REPLACE = ImmutableList.of(Blocks.BEDROCK, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   private static final Direction[] DIRECTIONS = Direction.values();
/*    */   private static final double RIM_SPAWN_CHANCE = 0.9D;
/*    */   
/*    */   public DeltaFeature(Codec<DeltaFeatureConfiguration> $$0) {
/* 27 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<DeltaFeatureConfiguration> $$0) {
/* 32 */     boolean $$1 = false;
/* 33 */     RandomSource $$2 = $$0.random();
/* 34 */     WorldGenLevel $$3 = $$0.level();
/* 35 */     DeltaFeatureConfiguration $$4 = $$0.config();
/* 36 */     BlockPos $$5 = $$0.origin();
/* 37 */     boolean $$6 = ($$2.nextDouble() < 0.9D);
/* 38 */     int $$7 = $$6 ? $$4.rimSize().sample($$2) : 0;
/* 39 */     int $$8 = $$6 ? $$4.rimSize().sample($$2) : 0;
/* 40 */     boolean $$9 = ($$6 && $$7 != 0 && $$8 != 0);
/*    */     
/* 42 */     int $$10 = $$4.size().sample($$2);
/* 43 */     int $$11 = $$4.size().sample($$2);
/* 44 */     int $$12 = Math.max($$10, $$11);
/* 45 */     for (BlockPos $$13 : BlockPos.withinManhattan($$5, $$10, 0, $$11)) {
/* 46 */       if ($$13.distManhattan((Vec3i)$$5) > $$12) {
/*    */         break;
/*    */       }
/*    */       
/* 50 */       if (isClear((LevelAccessor)$$3, $$13, $$4)) {
/* 51 */         if ($$9) {
/* 52 */           $$1 = true;
/* 53 */           setBlock((LevelWriter)$$3, $$13, $$4.rim());
/*    */         } 
/*    */         
/* 56 */         BlockPos $$14 = $$13.offset($$7, 0, $$8);
/* 57 */         if (isClear((LevelAccessor)$$3, $$14, $$4)) {
/* 58 */           $$1 = true;
/* 59 */           setBlock((LevelWriter)$$3, $$14, $$4.contents());
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 64 */     return $$1;
/*    */   }
/*    */   
/*    */   private static boolean isClear(LevelAccessor $$0, BlockPos $$1, DeltaFeatureConfiguration $$2) {
/* 68 */     BlockState $$3 = $$0.getBlockState($$1);
/* 69 */     if ($$3.is($$2.contents().getBlock())) {
/* 70 */       return false;
/*    */     }
/*    */     
/* 73 */     if (CANNOT_REPLACE.contains($$3.getBlock())) {
/* 74 */       return false;
/*    */     }
/*    */     
/* 77 */     for (Direction $$4 : DIRECTIONS) {
/* 78 */       boolean $$5 = $$0.getBlockState($$1.relative($$4)).isAir();
/* 79 */       if (($$5 && $$4 != Direction.UP) || (!$$5 && $$4 == Direction.UP)) {
/* 80 */         return false;
/*    */       }
/*    */     } 
/* 83 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\DeltaFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */