/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public abstract class GrowingPlantBlock extends Block {
/*    */   protected final Direction growthDirection;
/*    */   protected final boolean scheduleFluidTicks;
/*    */   protected final VoxelShape shape;
/*    */   
/*    */   protected GrowingPlantBlock(BlockBehaviour.Properties $$0, Direction $$1, VoxelShape $$2, boolean $$3) {
/* 24 */     super($$0);
/* 25 */     this.growthDirection = $$1;
/* 26 */     this.shape = $$2;
/* 27 */     this.scheduleFluidTicks = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends GrowingPlantBlock> codec();
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 36 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos().relative(this.growthDirection));
/* 37 */     if ($$1.is(getHeadBlock()) || $$1.is(getBodyBlock())) {
/* 38 */       return getBodyBlock().defaultBlockState();
/*    */     }
/* 40 */     return getStateForPlacement((LevelAccessor)$$0.getLevel());
/*    */   }
/*    */   
/*    */   public BlockState getStateForPlacement(LevelAccessor $$0) {
/* 44 */     return defaultBlockState();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 49 */     BlockPos $$3 = $$2.relative(this.growthDirection.getOpposite());
/* 50 */     BlockState $$4 = $$1.getBlockState($$3);
/* 51 */     if (!canAttachTo($$4)) {
/* 52 */       return false;
/*    */     }
/*    */     
/* 55 */     return ($$4.is(getHeadBlock()) || $$4.is(getBodyBlock()) || $$4.isFaceSturdy((BlockGetter)$$1, $$3, this.growthDirection));
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 60 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/* 61 */       $$1.destroyBlock($$2, true);
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean canAttachTo(BlockState $$0) {
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 71 */     return this.shape;
/*    */   }
/*    */   
/*    */   protected abstract GrowingPlantHeadBlock getHeadBlock();
/*    */   
/*    */   protected abstract Block getBodyBlock();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\GrowingPlantBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */