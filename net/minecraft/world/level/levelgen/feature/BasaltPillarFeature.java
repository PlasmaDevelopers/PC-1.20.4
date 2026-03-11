/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class BasaltPillarFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public BasaltPillarFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 21 */     BlockPos $$1 = $$0.origin();
/* 22 */     WorldGenLevel $$2 = $$0.level();
/* 23 */     RandomSource $$3 = $$0.random();
/* 24 */     if (!$$2.isEmptyBlock($$1) || $$2.isEmptyBlock($$1.above())) {
/* 25 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 29 */     BlockPos.MutableBlockPos $$4 = $$1.mutable();
/* 30 */     BlockPos.MutableBlockPos $$5 = $$1.mutable();
/* 31 */     boolean $$6 = true;
/* 32 */     boolean $$7 = true;
/* 33 */     boolean $$8 = true;
/* 34 */     boolean $$9 = true;
/*    */     
/* 36 */     while ($$2.isEmptyBlock((BlockPos)$$4)) {
/* 37 */       if ($$2.isOutsideBuildHeight((BlockPos)$$4)) {
/* 38 */         return true;
/*    */       }
/*    */       
/* 41 */       $$2.setBlock((BlockPos)$$4, Blocks.BASALT.defaultBlockState(), 2);
/*    */       
/* 43 */       $$6 = ($$6 && placeHangOff((LevelAccessor)$$2, $$3, (BlockPos)$$5.setWithOffset((Vec3i)$$4, Direction.NORTH)));
/* 44 */       $$7 = ($$7 && placeHangOff((LevelAccessor)$$2, $$3, (BlockPos)$$5.setWithOffset((Vec3i)$$4, Direction.SOUTH)));
/* 45 */       $$8 = ($$8 && placeHangOff((LevelAccessor)$$2, $$3, (BlockPos)$$5.setWithOffset((Vec3i)$$4, Direction.WEST)));
/* 46 */       $$9 = ($$9 && placeHangOff((LevelAccessor)$$2, $$3, (BlockPos)$$5.setWithOffset((Vec3i)$$4, Direction.EAST)));
/*    */       
/* 48 */       $$4.move(Direction.DOWN);
/*    */     } 
/*    */ 
/*    */     
/* 52 */     $$4.move(Direction.UP);
/* 53 */     placeBaseHangOff((LevelAccessor)$$2, $$3, (BlockPos)$$5.setWithOffset((Vec3i)$$4, Direction.NORTH));
/* 54 */     placeBaseHangOff((LevelAccessor)$$2, $$3, (BlockPos)$$5.setWithOffset((Vec3i)$$4, Direction.SOUTH));
/* 55 */     placeBaseHangOff((LevelAccessor)$$2, $$3, (BlockPos)$$5.setWithOffset((Vec3i)$$4, Direction.WEST));
/* 56 */     placeBaseHangOff((LevelAccessor)$$2, $$3, (BlockPos)$$5.setWithOffset((Vec3i)$$4, Direction.EAST));
/* 57 */     $$4.move(Direction.DOWN);
/*    */     
/* 59 */     BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
/* 60 */     for (int $$11 = -3; $$11 < 4; $$11++) {
/* 61 */       for (int $$12 = -3; $$12 < 4; $$12++) {
/* 62 */         int $$13 = Mth.abs($$11) * Mth.abs($$12);
/* 63 */         if ($$3.nextInt(10) < 10 - $$13) {
/*    */ 
/*    */ 
/*    */           
/* 67 */           $$10.set((Vec3i)$$4.offset($$11, 0, $$12));
/* 68 */           int $$14 = 3;
/* 69 */           while ($$2.isEmptyBlock((BlockPos)$$5.setWithOffset((Vec3i)$$10, Direction.DOWN))) {
/* 70 */             $$10.move(Direction.DOWN);
/* 71 */             $$14--;
/* 72 */             if ($$14 <= 0) {
/*    */               break;
/*    */             }
/*    */           } 
/*    */           
/* 77 */           if (!$$2.isEmptyBlock((BlockPos)$$5.setWithOffset((Vec3i)$$10, Direction.DOWN))) {
/* 78 */             $$2.setBlock((BlockPos)$$10, Blocks.BASALT.defaultBlockState(), 2);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 83 */     return true;
/*    */   }
/*    */   
/*    */   private void placeBaseHangOff(LevelAccessor $$0, RandomSource $$1, BlockPos $$2) {
/* 87 */     if ($$1.nextBoolean()) {
/* 88 */       $$0.setBlock($$2, Blocks.BASALT.defaultBlockState(), 2);
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean placeHangOff(LevelAccessor $$0, RandomSource $$1, BlockPos $$2) {
/* 93 */     if ($$1.nextInt(10) != 0) {
/* 94 */       $$0.setBlock($$2, Blocks.BASALT.defaultBlockState(), 2);
/* 95 */       return true;
/*    */     } 
/*    */     
/* 98 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\BasaltPillarFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */