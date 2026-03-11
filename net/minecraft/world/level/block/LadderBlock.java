/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class LadderBlock extends Block implements SimpleWaterloggedBlock {
/*  23 */   public static final MapCodec<LadderBlock> CODEC = simpleCodec(LadderBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<LadderBlock> codec() {
/*  27 */     return CODEC;
/*     */   }
/*     */   
/*  30 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  31 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   protected static final float AABB_OFFSET = 3.0F;
/*  33 */   protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
/*  34 */   protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  35 */   protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
/*  36 */   protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
/*     */   
/*     */   protected LadderBlock(BlockBehaviour.Properties $$0) {
/*  39 */     super($$0);
/*  40 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  45 */     switch ((Direction)$$0.getValue((Property)FACING)) {
/*     */       case NORTH:
/*  47 */         return NORTH_AABB;
/*     */       case SOUTH:
/*  49 */         return SOUTH_AABB;
/*     */       case WEST:
/*  51 */         return WEST_AABB;
/*     */     } 
/*     */     
/*  54 */     return EAST_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canAttachTo(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/*  59 */     BlockState $$3 = $$0.getBlockState($$1);
/*  60 */     return $$3.isFaceSturdy($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  65 */     Direction $$3 = (Direction)$$0.getValue((Property)FACING);
/*  66 */     return canAttachTo((BlockGetter)$$1, $$2.relative($$3.getOpposite()), $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  71 */     if ($$1.getOpposite() == $$0.getValue((Property)FACING) && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  72 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*  74 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  75 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/*  78 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  85 */     if (!$$0.replacingClickedOnBlock()) {
/*  86 */       BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos().relative($$0.getClickedFace().getOpposite()));
/*  87 */       if ($$1.is(this) && $$1.getValue((Property)FACING) == $$0.getClickedFace()) {
/*  88 */         return null;
/*     */       }
/*     */     } 
/*     */     
/*  92 */     BlockState $$2 = defaultBlockState();
/*     */     
/*  94 */     Level level = $$0.getLevel();
/*  95 */     BlockPos $$4 = $$0.getClickedPos();
/*  96 */     FluidState $$5 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*     */     
/*  98 */     for (Direction $$6 : $$0.getNearestLookingDirections()) {
/*  99 */       if ($$6.getAxis().isHorizontal()) {
/* 100 */         $$2 = (BlockState)$$2.setValue((Property)FACING, (Comparable)$$6.getOpposite());
/* 101 */         if ($$2.canSurvive((LevelReader)level, $$4)) {
/* 102 */           return (BlockState)$$2.setValue((Property)WATERLOGGED, Boolean.valueOf(($$5.getType() == Fluids.WATER)));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 112 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 117 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 122 */     $$0.add(new Property[] { (Property)FACING, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 127 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 128 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 130 */     return super.getFluidState($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LadderBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */