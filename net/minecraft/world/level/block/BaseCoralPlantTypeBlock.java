/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public abstract class BaseCoralPlantTypeBlock extends Block implements SimpleWaterloggedBlock {
/* 23 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/* 24 */   private static final VoxelShape AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
/*    */   
/*    */   protected BaseCoralPlantTypeBlock(BlockBehaviour.Properties $$0) {
/* 27 */     super($$0);
/* 28 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)WATERLOGGED, Boolean.valueOf(true)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends BaseCoralPlantTypeBlock> codec();
/*    */   
/*    */   protected void tryScheduleDieTick(BlockState $$0, LevelAccessor $$1, BlockPos $$2) {
/* 35 */     if (!scanForWater($$0, (BlockGetter)$$1, $$2)) {
/* 36 */       $$1.scheduleTick($$2, this, 60 + $$1.getRandom().nextInt(40));
/*    */     }
/*    */   }
/*    */   
/*    */   protected static boolean scanForWater(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 41 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 42 */       return true;
/*    */     }
/*    */     
/* 45 */     for (Direction $$3 : Direction.values()) {
/* 46 */       if ($$1.getFluidState($$2.relative($$3)).is(FluidTags.WATER)) {
/* 47 */         return true;
/*    */       }
/*    */     } 
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 56 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*    */     
/* 58 */     return (BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(($$1.is(FluidTags.WATER) && $$1.getAmount() == 8)));
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 63 */     return AABB;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 68 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 69 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/*    */     
/* 72 */     if ($$1 == Direction.DOWN && !canSurvive($$0, (LevelReader)$$3, $$4)) {
/* 73 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/* 75 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 80 */     BlockPos $$3 = $$2.below();
/* 81 */     return $$1.getBlockState($$3).isFaceSturdy((BlockGetter)$$1, $$3, Direction.UP);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 86 */     $$0.add(new Property[] { (Property)WATERLOGGED });
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockState $$0) {
/* 91 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 92 */       return Fluids.WATER.getSource(false);
/*    */     }
/*    */     
/* 95 */     return super.getFluidState($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BaseCoralPlantTypeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */