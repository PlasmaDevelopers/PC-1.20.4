/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class EndPortalFrameBlock extends Block {
/*  26 */   public static final MapCodec<EndPortalFrameBlock> CODEC = simpleCodec(EndPortalFrameBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<EndPortalFrameBlock> codec() {
/*  30 */     return CODEC;
/*     */   }
/*     */   
/*  33 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  34 */   public static final BooleanProperty HAS_EYE = BlockStateProperties.EYE;
/*  35 */   protected static final VoxelShape BASE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
/*  36 */   protected static final VoxelShape EYE_SHAPE = Block.box(4.0D, 13.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/*  37 */   protected static final VoxelShape FULL_SHAPE = Shapes.or(BASE_SHAPE, EYE_SHAPE);
/*     */   private static BlockPattern portalShape;
/*     */   
/*     */   public EndPortalFrameBlock(BlockBehaviour.Properties $$0) {
/*  41 */     super($$0);
/*  42 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)HAS_EYE, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  52 */     return ((Boolean)$$0.getValue((Property)HAS_EYE)).booleanValue() ? FULL_SHAPE : BASE_SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  57 */     return (BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite())).setValue((Property)HAS_EYE, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/*  67 */     if (((Boolean)$$0.getValue((Property)HAS_EYE)).booleanValue()) {
/*  68 */       return 15;
/*     */     }
/*     */     
/*  71 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  76 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/*  81 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  86 */     $$0.add(new Property[] { (Property)FACING, (Property)HAS_EYE });
/*     */   }
/*     */   
/*     */   public static BlockPattern getOrCreatePortalShape() {
/*  90 */     if (portalShape == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       portalShape = BlockPatternBuilder.start().aisle(new String[] { "?vvv?", ">???<", ">???<", ">???<", "?^^^?" }).where('?', BlockInWorld.hasState(BlockStatePredicate.ANY)).where('^', BlockInWorld.hasState((Predicate)BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME).where((Property)HAS_EYE, (Predicate)Predicates.equalTo(Boolean.valueOf(true))).where((Property)FACING, (Predicate)Predicates.equalTo(Direction.SOUTH)))).where('>', BlockInWorld.hasState((Predicate)BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME).where((Property)HAS_EYE, (Predicate)Predicates.equalTo(Boolean.valueOf(true))).where((Property)FACING, (Predicate)Predicates.equalTo(Direction.WEST)))).where('v', BlockInWorld.hasState((Predicate)BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME).where((Property)HAS_EYE, (Predicate)Predicates.equalTo(Boolean.valueOf(true))).where((Property)FACING, (Predicate)Predicates.equalTo(Direction.NORTH)))).where('<', BlockInWorld.hasState((Predicate)BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME).where((Property)HAS_EYE, (Predicate)Predicates.equalTo(Boolean.valueOf(true))).where((Property)FACING, (Predicate)Predicates.equalTo(Direction.EAST)))).build();
/*     */     }
/* 106 */     return portalShape;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\EndPortalFrameBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */