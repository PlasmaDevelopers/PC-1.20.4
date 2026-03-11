/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public class SpongeBlock extends Block {
/* 15 */   public static final MapCodec<SpongeBlock> CODEC = simpleCodec(SpongeBlock::new); public static final int MAX_DEPTH = 6;
/*    */   public static final int MAX_COUNT = 64;
/*    */   
/*    */   public MapCodec<SpongeBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   private static final Direction[] ALL_DIRECTIONS = Direction.values();
/*    */   
/*    */   protected SpongeBlock(BlockBehaviour.Properties $$0) {
/* 28 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 33 */     if ($$3.is($$0.getBlock())) {
/*    */       return;
/*    */     }
/* 36 */     tryAbsorbWater($$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 41 */     tryAbsorbWater($$1, $$2);
/* 42 */     super.neighborChanged($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   protected void tryAbsorbWater(Level $$0, BlockPos $$1) {
/* 46 */     if (removeWaterBreadthFirstSearch($$0, $$1)) {
/*    */       
/* 48 */       $$0.setBlock($$1, Blocks.WET_SPONGE.defaultBlockState(), 2);
/* 49 */       $$0.playSound(null, $$1, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean removeWaterBreadthFirstSearch(Level $$0, BlockPos $$1) {
/* 56 */     return (BlockPos.breadthFirstTraversal($$1, 6, 65, ($$0, $$1) -> { for (Direction $$2 : ALL_DIRECTIONS) $$1.accept($$0.relative($$2));  }$$2 -> { if ($$2.equals($$0)) return true;  BlockState $$3 = $$1.getBlockState($$2); FluidState $$4 = $$1.getFluidState($$2); if (!$$4.is(FluidTags.WATER)) return false;  Block $$5 = $$3.getBlock(); if ($$5 instanceof BucketPickup) { BucketPickup $$6 = (BucketPickup)$$5; if (!$$6.pickupBlock(null, (LevelAccessor)$$1, $$2, $$3).isEmpty()) return true;  }  if ($$3.getBlock() instanceof LiquidBlock) { $$1.setBlock($$2, Blocks.AIR.defaultBlockState(), 3); } else if ($$3.is(Blocks.KELP) || $$3.is(Blocks.KELP_PLANT) || $$3.is(Blocks.SEAGRASS) || $$3.is(Blocks.TALL_SEAGRASS)) { BlockEntity $$7 = $$3.hasBlockEntity() ? $$1.getBlockEntity($$2) : null; dropResources($$3, (LevelAccessor)$$1, $$2, $$7); $$1.setBlock($$2, Blocks.AIR.defaultBlockState(), 3); } else { return false; }  return true; }) > 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SpongeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */