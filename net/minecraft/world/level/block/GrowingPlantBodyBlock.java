/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.BlockUtil;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public abstract class GrowingPlantBodyBlock extends GrowingPlantBlock implements BonemealableBlock {
/*    */   protected GrowingPlantBodyBlock(BlockBehaviour.Properties $$0, Direction $$1, VoxelShape $$2, boolean $$3) {
/* 23 */     super($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends GrowingPlantBodyBlock> codec();
/*    */ 
/*    */ 
/*    */   
/*    */   protected BlockState updateHeadAfterConvertedFromBody(BlockState $$0, BlockState $$1) {
/* 33 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 38 */     if ($$1 == this.growthDirection.getOpposite() && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 39 */       $$3.scheduleTick($$4, this, 1);
/*    */     }
/*    */     
/* 42 */     GrowingPlantHeadBlock $$6 = getHeadBlock();
/* 43 */     if ($$1 == this.growthDirection && 
/* 44 */       !$$2.is(this) && !$$2.is($$6))
/*    */     {
/* 46 */       return updateHeadAfterConvertedFromBody($$0, $$6.getStateForPlacement($$3));
/*    */     }
/*    */ 
/*    */     
/* 50 */     if (this.scheduleFluidTicks) {
/* 51 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/*    */     
/* 54 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 59 */     return new ItemStack(getHeadBlock());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 64 */     Optional<BlockPos> $$3 = getHeadPos((BlockGetter)$$0, $$1, $$2.getBlock());
/* 65 */     return ($$3.isPresent() && getHeadBlock().canGrowInto($$0.getBlockState(((BlockPos)$$3.get()).relative(this.growthDirection))));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 70 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 75 */     Optional<BlockPos> $$4 = getHeadPos((BlockGetter)$$0, $$2, $$3.getBlock());
/*    */     
/* 77 */     if ($$4.isPresent()) {
/* 78 */       BlockState $$5 = $$0.getBlockState($$4.get());
/* 79 */       ((GrowingPlantHeadBlock)$$5.getBlock()).performBonemeal($$0, $$1, $$4.get(), $$5);
/*    */     } 
/*    */   }
/*    */   
/*    */   private Optional<BlockPos> getHeadPos(BlockGetter $$0, BlockPos $$1, Block $$2) {
/* 84 */     return BlockUtil.getTopConnectedBlock($$0, $$1, $$2, this.growthDirection, getHeadBlock());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/* 89 */     boolean $$2 = super.canBeReplaced($$0, $$1);
/* 90 */     if ($$2 && $$1.getItemInHand().is(getHeadBlock().asItem())) {
/* 91 */       return false;
/*    */     }
/* 93 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Block getBodyBlock() {
/* 98 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\GrowingPlantBodyBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */