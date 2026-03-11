/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
/*    */ 
/*    */ public class DiskFeature extends Feature<DiskConfiguration> {
/*    */   public DiskFeature(Codec<DiskConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<DiskConfiguration> $$0) {
/* 18 */     DiskConfiguration $$1 = $$0.config();
/* 19 */     BlockPos $$2 = $$0.origin();
/* 20 */     WorldGenLevel $$3 = $$0.level();
/* 21 */     RandomSource $$4 = $$0.random();
/* 22 */     boolean $$5 = false;
/*    */     
/* 24 */     int $$6 = $$2.getY();
/* 25 */     int $$7 = $$6 + $$1.halfHeight();
/* 26 */     int $$8 = $$6 - $$1.halfHeight() - 1;
/*    */     
/* 28 */     int $$9 = $$1.radius().sample($$4);
/*    */     
/* 30 */     BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
/*    */     
/* 32 */     for (BlockPos $$11 : BlockPos.betweenClosed($$2.offset(-$$9, 0, -$$9), $$2.offset($$9, 0, $$9))) {
/* 33 */       int $$12 = $$11.getX() - $$2.getX();
/* 34 */       int $$13 = $$11.getZ() - $$2.getZ();
/* 35 */       if ($$12 * $$12 + $$13 * $$13 > $$9 * $$9) {
/*    */         continue;
/*    */       }
/*    */       
/* 39 */       $$5 |= placeColumn($$1, $$3, $$4, $$7, $$8, $$10.set((Vec3i)$$11));
/*    */     } 
/*    */     
/* 42 */     return $$5;
/*    */   }
/*    */   
/*    */   protected boolean placeColumn(DiskConfiguration $$0, WorldGenLevel $$1, RandomSource $$2, int $$3, int $$4, BlockPos.MutableBlockPos $$5) {
/* 46 */     boolean $$6 = false;
/*    */     
/* 48 */     for (int $$7 = $$3; $$7 > $$4; $$7--) {
/* 49 */       $$5.setY($$7);
/* 50 */       if ($$0.target().test($$1, $$5)) {
/* 51 */         BlockState $$8 = $$0.stateProvider().getState($$1, $$2, (BlockPos)$$5);
/* 52 */         $$1.setBlock((BlockPos)$$5, $$8, 2);
/* 53 */         markAboveForPostProcessing($$1, (BlockPos)$$5);
/* 54 */         $$6 = true;
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     return $$6;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\DiskFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */