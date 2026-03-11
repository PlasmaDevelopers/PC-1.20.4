/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
/*    */ 
/*    */ public class ScatteredOreFeature
/*    */   extends Feature<OreConfiguration>
/*    */ {
/*    */   private static final int MAX_DIST_FROM_ORIGIN = 7;
/*    */   
/*    */   ScatteredOreFeature(Codec<OreConfiguration> $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<OreConfiguration> $$0) {
/* 26 */     WorldGenLevel $$1 = $$0.level();
/* 27 */     RandomSource $$2 = $$0.random();
/* 28 */     OreConfiguration $$3 = $$0.config();
/* 29 */     BlockPos $$4 = $$0.origin();
/* 30 */     int $$5 = $$2.nextInt($$3.size + 1);
/* 31 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/*    */     
/* 33 */     for (int $$7 = 0; $$7 < $$5; $$7++) {
/*    */       
/* 35 */       offsetTargetPos($$6, $$2, $$4, Math.min($$7, 7));
/*    */       
/* 37 */       BlockState $$8 = $$1.getBlockState((BlockPos)$$6);
/* 38 */       for (OreConfiguration.TargetBlockState $$9 : $$3.targetStates) {
/* 39 */         Objects.requireNonNull($$1); if (OreFeature.canPlaceOre($$8, $$1::getBlockState, $$2, $$3, $$9, $$6)) {
/* 40 */           $$1.setBlock((BlockPos)$$6, $$9.state, 2);
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 45 */     return true;
/*    */   }
/*    */   
/*    */   private void offsetTargetPos(BlockPos.MutableBlockPos $$0, RandomSource $$1, BlockPos $$2, int $$3) {
/* 49 */     int $$4 = getRandomPlacementInOneAxisRelativeToOrigin($$1, $$3);
/* 50 */     int $$5 = getRandomPlacementInOneAxisRelativeToOrigin($$1, $$3);
/* 51 */     int $$6 = getRandomPlacementInOneAxisRelativeToOrigin($$1, $$3);
/* 52 */     $$0.setWithOffset((Vec3i)$$2, $$4, $$5, $$6);
/*    */   }
/*    */   
/*    */   private int getRandomPlacementInOneAxisRelativeToOrigin(RandomSource $$0, int $$1) {
/* 56 */     return Math.round(($$0.nextFloat() - $$0.nextFloat()) * $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\ScatteredOreFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */