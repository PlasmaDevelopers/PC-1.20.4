/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
/*    */ 
/*    */ public class WaterloggedVegetationPatchFeature extends VegetationPatchFeature {
/*    */   public WaterloggedVegetationPatchFeature(Codec<VegetationPatchConfiguration> $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Set<BlockPos> placeGroundPatch(WorldGenLevel $$0, VegetationPatchConfiguration $$1, RandomSource $$2, BlockPos $$3, Predicate<BlockState> $$4, int $$5, int $$6) {
/* 26 */     Set<BlockPos> $$7 = super.placeGroundPatch($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/* 27 */     Set<BlockPos> $$8 = new HashSet<>();
/* 28 */     BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
/* 29 */     for (BlockPos $$10 : $$7) {
/* 30 */       if (!isExposed($$0, $$7, $$10, $$9)) {
/* 31 */         $$8.add($$10);
/*    */       }
/*    */     } 
/* 34 */     for (BlockPos $$11 : $$8) {
/* 35 */       $$0.setBlock($$11, Blocks.WATER.defaultBlockState(), 2);
/*    */     }
/* 37 */     return $$8;
/*    */   }
/*    */   
/*    */   private static boolean isExposed(WorldGenLevel $$0, Set<BlockPos> $$1, BlockPos $$2, BlockPos.MutableBlockPos $$3) {
/* 41 */     return (isExposedDirection($$0, $$2, $$3, Direction.NORTH) || 
/* 42 */       isExposedDirection($$0, $$2, $$3, Direction.EAST) || 
/* 43 */       isExposedDirection($$0, $$2, $$3, Direction.SOUTH) || 
/* 44 */       isExposedDirection($$0, $$2, $$3, Direction.WEST) || 
/* 45 */       isExposedDirection($$0, $$2, $$3, Direction.DOWN));
/*    */   }
/*    */   
/*    */   private static boolean isExposedDirection(WorldGenLevel $$0, BlockPos $$1, BlockPos.MutableBlockPos $$2, Direction $$3) {
/* 49 */     $$2.setWithOffset((Vec3i)$$1, $$3);
/* 50 */     return !$$0.getBlockState((BlockPos)$$2).isFaceSturdy((BlockGetter)$$0, (BlockPos)$$2, $$3.getOpposite());
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean placeVegetation(WorldGenLevel $$0, VegetationPatchConfiguration $$1, ChunkGenerator $$2, RandomSource $$3, BlockPos $$4) {
/* 55 */     if (super.placeVegetation($$0, $$1, $$2, $$3, $$4.below())) {
/* 56 */       BlockState $$5 = $$0.getBlockState($$4);
/* 57 */       if ($$5.hasProperty((Property)BlockStateProperties.WATERLOGGED) && !((Boolean)$$5.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue()) {
/* 58 */         $$0.setBlock($$4, (BlockState)$$5.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)), 2);
/*    */       }
/* 60 */       return true;
/*    */     } 
/* 62 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\WaterloggedVegetationPatchFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */