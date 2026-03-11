/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class CoralMushroomFeature extends CoralFeature {
/*    */   public CoralMushroomFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean placeFeature(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 18 */     int $$4 = $$1.nextInt(3) + 3;
/* 19 */     int $$5 = $$1.nextInt(3) + 3;
/* 20 */     int $$6 = $$1.nextInt(3) + 3;
/*    */     
/* 22 */     int $$7 = $$1.nextInt(3) + 1;
/*    */     
/* 24 */     BlockPos.MutableBlockPos $$8 = $$2.mutable();
/*    */ 
/*    */ 
/*    */     
/* 28 */     for (int $$9 = 0; $$9 <= $$5; $$9++) {
/* 29 */       for (int $$10 = 0; $$10 <= $$4; $$10++) {
/* 30 */         for (int $$11 = 0; $$11 <= $$6; $$11++) {
/* 31 */           $$8.set($$9 + $$2.getX(), $$10 + $$2.getY(), $$11 + $$2.getZ());
/* 32 */           $$8.move(Direction.DOWN, $$7);
/*    */ 
/*    */           
/* 35 */           if (($$9 != 0 && $$9 != $$5) || ($$10 != 0 && $$10 != $$4))
/*    */           {
/*    */ 
/*    */             
/* 39 */             if (($$11 != 0 && $$11 != $$6) || ($$10 != 0 && $$10 != $$4))
/*    */             {
/*    */ 
/*    */               
/* 43 */               if (($$9 != 0 && $$9 != $$5) || ($$11 != 0 && $$11 != $$6))
/*    */               {
/*    */ 
/*    */ 
/*    */                 
/* 48 */                 if ($$9 == 0 || $$9 == $$5 || $$10 == 0 || $$10 == $$4 || $$11 == 0 || $$11 == $$6)
/*    */                 {
/*    */ 
/*    */ 
/*    */                   
/* 53 */                   if ($$1.nextFloat() >= 0.1F)
/*    */                   {
/*    */ 
/*    */                     
/* 57 */                     if (!placeCoralBlock($$0, $$1, (BlockPos)$$8, $$3)); }  }  } 
/*    */             }
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 63 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\CoralMushroomFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */