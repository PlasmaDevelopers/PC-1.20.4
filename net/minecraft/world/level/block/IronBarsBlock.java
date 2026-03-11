/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class IronBarsBlock extends CrossCollisionBlock {
/* 19 */   public static final MapCodec<IronBarsBlock> CODEC = simpleCodec(IronBarsBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<? extends IronBarsBlock> codec() {
/* 23 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected IronBarsBlock(BlockBehaviour.Properties $$0) {
/* 27 */     super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, $$0);
/* 28 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)NORTH, Boolean.valueOf(false))).setValue((Property)EAST, Boolean.valueOf(false))).setValue((Property)SOUTH, Boolean.valueOf(false))).setValue((Property)WEST, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 33 */     Level level = $$0.getLevel();
/* 34 */     BlockPos $$2 = $$0.getClickedPos();
/* 35 */     FluidState $$3 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*    */     
/* 37 */     BlockPos $$4 = $$2.north();
/* 38 */     BlockPos $$5 = $$2.south();
/* 39 */     BlockPos $$6 = $$2.west();
/* 40 */     BlockPos $$7 = $$2.east();
/*    */     
/* 42 */     BlockState $$8 = level.getBlockState($$4);
/* 43 */     BlockState $$9 = level.getBlockState($$5);
/* 44 */     BlockState $$10 = level.getBlockState($$6);
/* 45 */     BlockState $$11 = level.getBlockState($$7);
/*    */     
/* 47 */     return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)defaultBlockState()
/* 48 */       .setValue((Property)NORTH, Boolean.valueOf(attachsTo($$8, $$8.isFaceSturdy((BlockGetter)level, $$4, Direction.SOUTH)))))
/* 49 */       .setValue((Property)SOUTH, Boolean.valueOf(attachsTo($$9, $$9.isFaceSturdy((BlockGetter)level, $$5, Direction.NORTH)))))
/* 50 */       .setValue((Property)WEST, Boolean.valueOf(attachsTo($$10, $$10.isFaceSturdy((BlockGetter)level, $$6, Direction.EAST)))))
/* 51 */       .setValue((Property)EAST, Boolean.valueOf(attachsTo($$11, $$11.isFaceSturdy((BlockGetter)level, $$7, Direction.WEST)))))
/* 52 */       .setValue((Property)WATERLOGGED, Boolean.valueOf(($$3.getType() == Fluids.WATER)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 58 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 59 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/* 61 */     if ($$1.getAxis().isHorizontal()) {
/* 62 */       return (BlockState)$$0.setValue((Property)PROPERTY_BY_DIRECTION.get($$1), Boolean.valueOf(attachsTo($$2, $$2.isFaceSturdy((BlockGetter)$$3, $$5, $$1.getOpposite()))));
/*    */     }
/* 64 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 69 */     return Shapes.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean skipRendering(BlockState $$0, BlockState $$1, Direction $$2) {
/* 74 */     if ($$1.is(this)) {
/* 75 */       if (!$$2.getAxis().isHorizontal()) {
/* 76 */         return true;
/*    */       }
/* 78 */       if (((Boolean)$$0.getValue((Property)PROPERTY_BY_DIRECTION.get($$2))).booleanValue() && ((Boolean)$$1.getValue((Property)PROPERTY_BY_DIRECTION.get($$2.getOpposite()))).booleanValue()) {
/* 79 */         return true;
/*    */       }
/*    */     } 
/* 82 */     return super.skipRendering($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public final boolean attachsTo(BlockState $$0, boolean $$1) {
/* 86 */     return ((!isExceptionForConnection($$0) && $$1) || $$0.getBlock() instanceof IronBarsBlock || $$0.is(BlockTags.WALLS));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 91 */     $$0.add(new Property[] { (Property)NORTH, (Property)EAST, (Property)WEST, (Property)SOUTH, (Property)WATERLOGGED });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\IronBarsBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */