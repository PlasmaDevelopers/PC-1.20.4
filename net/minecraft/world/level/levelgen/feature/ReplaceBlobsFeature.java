/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelWriter;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
/*    */ 
/*    */ public class ReplaceBlobsFeature extends Feature<ReplaceSphereConfiguration> {
/*    */   public ReplaceBlobsFeature(Codec<ReplaceSphereConfiguration> $$0) {
/* 17 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<ReplaceSphereConfiguration> $$0) {
/* 22 */     ReplaceSphereConfiguration $$1 = $$0.config();
/* 23 */     WorldGenLevel $$2 = $$0.level();
/* 24 */     RandomSource $$3 = $$0.random();
/* 25 */     Block $$4 = $$1.targetState.getBlock();
/* 26 */     BlockPos $$5 = findTarget((LevelAccessor)$$2, $$0.origin().mutable().clamp(Direction.Axis.Y, $$2.getMinBuildHeight() + 1, $$2.getMaxBuildHeight() - 1), $$4);
/* 27 */     if ($$5 == null) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     int $$6 = $$1.radius().sample($$3);
/* 32 */     int $$7 = $$1.radius().sample($$3);
/* 33 */     int $$8 = $$1.radius().sample($$3);
/* 34 */     int $$9 = Math.max($$6, Math.max($$7, $$8));
/*    */     
/* 36 */     boolean $$10 = false;
/* 37 */     for (BlockPos $$11 : BlockPos.withinManhattan($$5, $$6, $$7, $$8)) {
/* 38 */       if ($$11.distManhattan((Vec3i)$$5) > $$9) {
/*    */         break;
/*    */       }
/*    */ 
/*    */       
/* 43 */       BlockState $$12 = $$2.getBlockState($$11);
/* 44 */       if ($$12.is($$4)) {
/* 45 */         setBlock((LevelWriter)$$2, $$11, $$1.replaceState);
/* 46 */         $$10 = true;
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     return $$10;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static BlockPos findTarget(LevelAccessor $$0, BlockPos.MutableBlockPos $$1, Block $$2) {
/* 55 */     while ($$1.getY() > $$0.getMinBuildHeight() + 1) {
/* 56 */       BlockState $$3 = $$0.getBlockState((BlockPos)$$1);
/* 57 */       if ($$3.is($$2)) {
/* 58 */         return (BlockPos)$$1;
/*    */       }
/*    */       
/* 61 */       $$1.move(Direction.DOWN);
/*    */     } 
/* 63 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\ReplaceBlobsFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */