/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.lighting.LightEngine;
/*    */ 
/*    */ public abstract class SpreadingSnowyDirtBlock extends SnowyDirtBlock {
/*    */   protected SpreadingSnowyDirtBlock(BlockBehaviour.Properties $$0) {
/* 17 */     super($$0);
/*    */   }
/*    */   
/*    */   private static boolean canBeGrass(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 21 */     BlockPos $$3 = $$2.above();
/* 22 */     BlockState $$4 = $$1.getBlockState($$3);
/* 23 */     if ($$4.is(Blocks.SNOW) && ((Integer)$$4.getValue((Property)SnowLayerBlock.LAYERS)).intValue() == 1) {
/* 24 */       return true;
/*    */     }
/*    */     
/* 27 */     if ($$4.getFluidState().getAmount() == 8) {
/* 28 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 32 */     int $$5 = LightEngine.getLightBlockInto((BlockGetter)$$1, $$0, $$2, $$4, $$3, Direction.UP, $$4.getLightBlock((BlockGetter)$$1, $$3));
/*    */     
/* 34 */     return ($$5 < $$1.getMaxLightLevel());
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends SpreadingSnowyDirtBlock> codec();
/*    */   
/*    */   private static boolean canPropagate(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 41 */     BlockPos $$3 = $$2.above();
/* 42 */     return (canBeGrass($$0, $$1, $$2) && !$$1.getFluidState($$3).is(FluidTags.WATER));
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 47 */     if (!canBeGrass($$0, (LevelReader)$$1, $$2)) {
/* 48 */       $$1.setBlockAndUpdate($$2, Blocks.DIRT.defaultBlockState());
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     if ($$1.getMaxLocalRawBrightness($$2.above()) >= 9) {
/* 53 */       BlockState $$4 = defaultBlockState();
/*    */       
/* 55 */       for (int $$5 = 0; $$5 < 4; $$5++) {
/* 56 */         BlockPos $$6 = $$2.offset($$3.nextInt(3) - 1, $$3.nextInt(5) - 3, $$3.nextInt(3) - 1);
/* 57 */         if ($$1.getBlockState($$6).is(Blocks.DIRT) && canPropagate($$4, (LevelReader)$$1, $$6))
/* 58 */           $$1.setBlockAndUpdate($$6, (BlockState)$$4.setValue((Property)SNOWY, Boolean.valueOf($$1.getBlockState($$6.above()).is(Blocks.SNOW)))); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SpreadingSnowyDirtBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */