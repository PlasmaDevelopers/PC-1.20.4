/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class CoralTreeFeature extends CoralFeature {
/*    */   public CoralTreeFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean placeFeature(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 20 */     BlockPos.MutableBlockPos $$4 = $$2.mutable();
/*    */     
/* 22 */     int $$5 = $$1.nextInt(3) + 1;
/* 23 */     for (int $$6 = 0; $$6 < $$5; $$6++) {
/* 24 */       if (!placeCoralBlock($$0, $$1, (BlockPos)$$4, $$3)) {
/* 25 */         return true;
/*    */       }
/* 27 */       $$4.move(Direction.UP);
/*    */     } 
/* 29 */     BlockPos $$7 = $$4.immutable();
/*    */     
/* 31 */     int $$8 = $$1.nextInt(3) + 2;
/* 32 */     List<Direction> $$9 = Direction.Plane.HORIZONTAL.shuffledCopy($$1);
/* 33 */     List<Direction> $$10 = $$9.subList(0, $$8);
/*    */     
/* 35 */     for (Direction $$11 : $$10) {
/* 36 */       $$4.set((Vec3i)$$7);
/* 37 */       $$4.move($$11);
/*    */       
/* 39 */       int $$12 = $$1.nextInt(5) + 2;
/* 40 */       int $$13 = 0;
/* 41 */       for (int $$14 = 0; $$14 < $$12 && 
/* 42 */         placeCoralBlock($$0, $$1, (BlockPos)$$4, $$3); $$14++) {
/*    */ 
/*    */         
/* 45 */         $$13++;
/* 46 */         $$4.move(Direction.UP);
/*    */         
/* 48 */         if ($$14 == 0 || ($$13 >= 2 && $$1.nextFloat() < 0.25F)) {
/* 49 */           $$4.move($$11);
/* 50 */           $$13 = 0;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\CoralTreeFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */