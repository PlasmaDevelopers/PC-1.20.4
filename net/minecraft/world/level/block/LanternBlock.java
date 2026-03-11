/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class LanternBlock extends Block implements SimpleWaterloggedBlock {
/*  24 */   public static final MapCodec<LanternBlock> CODEC = simpleCodec(LanternBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<LanternBlock> codec() {
/*  28 */     return CODEC;
/*     */   }
/*     */   
/*  31 */   public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
/*  32 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*  34 */   protected static final VoxelShape AABB = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.box(6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D));
/*  35 */   protected static final VoxelShape HANGING_AABB = Shapes.or(Block.box(5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.box(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D));
/*     */   
/*     */   public LanternBlock(BlockBehaviour.Properties $$0) {
/*  38 */     super($$0);
/*  39 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)HANGING, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  45 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*     */     
/*  47 */     for (Direction $$2 : $$0.getNearestLookingDirections()) {
/*     */       
/*  49 */       if ($$2.getAxis() == Direction.Axis.Y) {
/*  50 */         BlockState $$3 = (BlockState)defaultBlockState().setValue((Property)HANGING, Boolean.valueOf(($$2 == Direction.UP)));
/*     */         
/*  52 */         if ($$3.canSurvive((LevelReader)$$0.getLevel(), $$0.getClickedPos())) {
/*  53 */           return (BlockState)$$3.setValue((Property)WATERLOGGED, Boolean.valueOf(($$1.getType() == Fluids.WATER)));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  63 */     return ((Boolean)$$0.getValue((Property)HANGING)).booleanValue() ? HANGING_AABB : AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  68 */     $$0.add(new Property[] { (Property)HANGING, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  73 */     Direction $$3 = getConnectedDirection($$0).getOpposite();
/*  74 */     return Block.canSupportCenter($$1, $$2.relative($$3), $$3.getOpposite());
/*     */   }
/*     */   
/*     */   protected static Direction getConnectedDirection(BlockState $$0) {
/*  78 */     return ((Boolean)$$0.getValue((Property)HANGING)).booleanValue() ? Direction.DOWN : Direction.UP;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  83 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  84 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*  86 */     if (getConnectedDirection($$0).getOpposite() == $$1 && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  87 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*  89 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/*  94 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  95 */       return Fluids.WATER.getSource(false);
/*     */     }
/*  97 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LanternBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */