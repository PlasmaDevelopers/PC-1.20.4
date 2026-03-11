/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class CoralClawFeature
/*    */   extends CoralFeature {
/*    */   public CoralClawFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 17 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean placeFeature(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 22 */     if (!placeCoralBlock($$0, $$1, $$2, $$3)) {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     Direction $$4 = Direction.Plane.HORIZONTAL.getRandomDirection($$1);
/* 27 */     int $$5 = $$1.nextInt(2) + 2;
/*    */     
/* 29 */     List<Direction> $$6 = Util.toShuffledList(Stream.of(new Direction[] { $$4, $$4.getClockWise(), $$4.getCounterClockWise() }, ), $$1);
/* 30 */     List<Direction> $$7 = $$6.subList(0, $$5);
/*    */     
/* 32 */     for (Direction $$8 : $$7) {
/* 33 */       int $$15; Direction $$14; BlockPos.MutableBlockPos $$9 = $$2.mutable();
/* 34 */       int $$10 = $$1.nextInt(2) + 1;
/*    */ 
/*    */ 
/*    */       
/* 38 */       $$9.move($$8);
/* 39 */       if ($$8 == $$4) {
/* 40 */         Direction $$11 = $$4;
/* 41 */         int $$12 = $$1.nextInt(3) + 2;
/*    */       } else {
/* 43 */         $$9.move(Direction.UP);
/*    */ 
/*    */         
/* 46 */         Direction[] $$13 = { $$8, Direction.UP };
/* 47 */         $$14 = (Direction)Util.getRandom((Object[])$$13, $$1);
/* 48 */         $$15 = $$1.nextInt(3) + 3;
/*    */       } 
/*    */       
/* 51 */       for (int $$16 = 0; $$16 < $$10 && 
/* 52 */         placeCoralBlock($$0, $$1, (BlockPos)$$9, $$3); $$16++)
/*    */       {
/*    */         
/* 55 */         $$9.move($$14);
/*    */       }
/* 57 */       $$9.move($$14.getOpposite());
/* 58 */       $$9.move(Direction.UP);
/*    */       
/* 60 */       for (int $$17 = 0; $$17 < $$15; $$17++) {
/* 61 */         $$9.move($$4);
/* 62 */         if (!placeCoralBlock($$0, $$1, (BlockPos)$$9, $$3)) {
/*    */           break;
/*    */         }
/*    */         
/* 66 */         if ($$1.nextFloat() < 0.25F) {
/* 67 */           $$9.move(Direction.UP);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\CoralClawFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */