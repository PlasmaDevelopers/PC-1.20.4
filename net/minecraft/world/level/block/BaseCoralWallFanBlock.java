/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Map;
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
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BaseCoralWallFanBlock extends BaseCoralFanBlock {
/*  23 */   public static final MapCodec<BaseCoralWallFanBlock> CODEC = simpleCodec(BaseCoralWallFanBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<? extends BaseCoralWallFanBlock> codec() {
/*  27 */     return CODEC;
/*     */   }
/*     */   
/*  30 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*     */   
/*  32 */   private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap((Map)ImmutableMap.of(Direction.NORTH, 
/*  33 */         Block.box(0.0D, 4.0D, 5.0D, 16.0D, 12.0D, 16.0D), Direction.SOUTH, 
/*  34 */         Block.box(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 11.0D), Direction.WEST, 
/*  35 */         Block.box(5.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D), Direction.EAST, 
/*  36 */         Block.box(0.0D, 4.0D, 0.0D, 11.0D, 12.0D, 16.0D)));
/*     */ 
/*     */   
/*     */   protected BaseCoralWallFanBlock(BlockBehaviour.Properties $$0) {
/*  40 */     super($$0);
/*  41 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)WATERLOGGED, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  46 */     return SHAPES.get($$0.getValue((Property)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  51 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/*  56 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  61 */     $$0.add(new Property[] { (Property)FACING, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  66 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  67 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/*  70 */     if ($$1.getOpposite() == $$0.getValue((Property)FACING) && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  71 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/*  74 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  79 */     Direction $$3 = (Direction)$$0.getValue((Property)FACING);
/*  80 */     BlockPos $$4 = $$2.relative($$3.getOpposite());
/*  81 */     BlockState $$5 = $$1.getBlockState($$4);
/*     */     
/*  83 */     return $$5.isFaceSturdy((BlockGetter)$$1, $$4, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  89 */     BlockState $$1 = super.getStateForPlacement($$0);
/*     */     
/*  91 */     Level level = $$0.getLevel();
/*  92 */     BlockPos $$3 = $$0.getClickedPos();
/*     */     
/*  94 */     Direction[] $$4 = $$0.getNearestLookingDirections();
/*  95 */     for (Direction $$5 : $$4) {
/*  96 */       if ($$5.getAxis().isHorizontal()) {
/*     */ 
/*     */ 
/*     */         
/* 100 */         $$1 = (BlockState)$$1.setValue((Property)FACING, (Comparable)$$5.getOpposite());
/* 101 */         if ($$1.canSurvive((LevelReader)level, $$3)) {
/* 102 */           return $$1;
/*     */         }
/*     */       } 
/*     */     } 
/* 106 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BaseCoralWallFanBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */