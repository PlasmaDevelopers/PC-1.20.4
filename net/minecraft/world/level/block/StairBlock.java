/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.stream.IntStream;
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
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Half;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.StairsShape;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class StairBlock extends Block implements SimpleWaterloggedBlock {
/*     */   public static final MapCodec<StairBlock> CODEC;
/*     */   
/*     */   static {
/*  37 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BlockState.CODEC.fieldOf("base_state").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, StairBlock::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapCodec<? extends StairBlock> codec() {
/*  44 */     return CODEC;
/*     */   }
/*     */   
/*  47 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  48 */   public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
/*  49 */   public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
/*  50 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*  52 */   protected static final VoxelShape TOP_AABB = SlabBlock.TOP_AABB;
/*  53 */   protected static final VoxelShape BOTTOM_AABB = SlabBlock.BOTTOM_AABB;
/*     */   
/*  55 */   protected static final VoxelShape OCTET_NNN = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
/*  56 */   protected static final VoxelShape OCTET_NNP = Block.box(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
/*  57 */   protected static final VoxelShape OCTET_NPN = Block.box(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
/*  58 */   protected static final VoxelShape OCTET_NPP = Block.box(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
/*  59 */   protected static final VoxelShape OCTET_PNN = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
/*  60 */   protected static final VoxelShape OCTET_PNP = Block.box(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
/*  61 */   protected static final VoxelShape OCTET_PPN = Block.box(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
/*  62 */   protected static final VoxelShape OCTET_PPP = Block.box(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
/*     */   
/*  64 */   protected static final VoxelShape[] TOP_SHAPES = makeShapes(TOP_AABB, OCTET_NNN, OCTET_PNN, OCTET_NNP, OCTET_PNP);
/*  65 */   protected static final VoxelShape[] BOTTOM_SHAPES = makeShapes(BOTTOM_AABB, OCTET_NPN, OCTET_PPN, OCTET_NPP, OCTET_PPP);
/*     */   
/*     */   private static VoxelShape[] makeShapes(VoxelShape $$0, VoxelShape $$1, VoxelShape $$2, VoxelShape $$3, VoxelShape $$4) {
/*  68 */     return (VoxelShape[])IntStream.range(0, 16).mapToObj($$5 -> makeStairShape($$5, $$0, $$1, $$2, $$3, $$4)).toArray($$0 -> new VoxelShape[$$0]);
/*     */   }
/*     */   
/*     */   private static VoxelShape makeStairShape(int $$0, VoxelShape $$1, VoxelShape $$2, VoxelShape $$3, VoxelShape $$4, VoxelShape $$5) {
/*  72 */     VoxelShape $$6 = $$1;
/*  73 */     if (($$0 & 0x1) != 0) {
/*  74 */       $$6 = Shapes.or($$6, $$2);
/*     */     }
/*  76 */     if (($$0 & 0x2) != 0) {
/*  77 */       $$6 = Shapes.or($$6, $$3);
/*     */     }
/*  79 */     if (($$0 & 0x4) != 0) {
/*  80 */       $$6 = Shapes.or($$6, $$4);
/*     */     }
/*  82 */     if (($$0 & 0x8) != 0) {
/*  83 */       $$6 = Shapes.or($$6, $$5);
/*     */     }
/*  85 */     return $$6;
/*     */   }
/*     */   
/*  88 */   private static final int[] SHAPE_BY_STATE = new int[] { 12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8 };
/*     */ 
/*     */ 
/*     */   
/*     */   private final Block base;
/*     */ 
/*     */   
/*     */   protected final BlockState baseState;
/*     */ 
/*     */ 
/*     */   
/*     */   protected StairBlock(BlockState $$0, BlockBehaviour.Properties $$1) {
/* 100 */     super($$1);
/* 101 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)HALF, (Comparable)Half.BOTTOM)).setValue((Property)SHAPE, (Comparable)StairsShape.STRAIGHT)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/* 102 */     this.base = $$0.getBlock();
/* 103 */     this.baseState = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 113 */     return (($$0.getValue((Property)HALF) == Half.TOP) ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_BY_STATE[getShapeIndex($$0)]];
/*     */   }
/*     */   
/*     */   private int getShapeIndex(BlockState $$0) {
/* 117 */     return ((StairsShape)$$0.getValue((Property)SHAPE)).ordinal() * 4 + ((Direction)$$0.getValue((Property)FACING)).get2DDataValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getExplosionResistance() {
/* 122 */     return this.base.getExplosionResistance();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 127 */     Direction $$1 = $$0.getClickedFace();
/* 128 */     BlockPos $$2 = $$0.getClickedPos();
/* 129 */     FluidState $$3 = $$0.getLevel().getFluidState($$2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     BlockState $$4 = (BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection())).setValue((Property)HALF, ($$1 == Direction.DOWN || ($$1 != Direction.UP && ($$0.getClickLocation()).y - $$2.getY() > 0.5D)) ? (Comparable)Half.TOP : (Comparable)Half.BOTTOM)).setValue((Property)WATERLOGGED, Boolean.valueOf(($$3.getType() == Fluids.WATER)));
/*     */     
/* 136 */     return (BlockState)$$4.setValue((Property)SHAPE, (Comparable)getStairsShape($$4, (BlockGetter)$$0.getLevel(), $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 141 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 142 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 144 */     if ($$1.getAxis().isHorizontal()) {
/* 145 */       return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)getStairsShape($$0, (BlockGetter)$$3, $$4));
/*     */     }
/* 147 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private static StairsShape getStairsShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 151 */     Direction $$3 = (Direction)$$0.getValue((Property)FACING);
/* 152 */     BlockState $$4 = $$1.getBlockState($$2.relative($$3));
/* 153 */     if (isStairs($$4) && $$0.getValue((Property)HALF) == $$4.getValue((Property)HALF)) {
/* 154 */       Direction $$5 = (Direction)$$4.getValue((Property)FACING);
/* 155 */       if ($$5.getAxis() != ((Direction)$$0.getValue((Property)FACING)).getAxis() && canTakeShape($$0, $$1, $$2, $$5.getOpposite())) {
/* 156 */         if ($$5 == $$3.getCounterClockWise()) {
/* 157 */           return StairsShape.OUTER_LEFT;
/*     */         }
/* 159 */         return StairsShape.OUTER_RIGHT;
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     BlockState $$6 = $$1.getBlockState($$2.relative($$3.getOpposite()));
/* 164 */     if (isStairs($$6) && $$0.getValue((Property)HALF) == $$6.getValue((Property)HALF)) {
/* 165 */       Direction $$7 = (Direction)$$6.getValue((Property)FACING);
/* 166 */       if ($$7.getAxis() != ((Direction)$$0.getValue((Property)FACING)).getAxis() && canTakeShape($$0, $$1, $$2, $$7)) {
/* 167 */         if ($$7 == $$3.getCounterClockWise()) {
/* 168 */           return StairsShape.INNER_LEFT;
/*     */         }
/* 170 */         return StairsShape.INNER_RIGHT;
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     return StairsShape.STRAIGHT;
/*     */   }
/*     */   
/*     */   private static boolean canTakeShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 178 */     BlockState $$4 = $$1.getBlockState($$2.relative($$3));
/* 179 */     return (!isStairs($$4) || $$4.getValue((Property)FACING) != $$0.getValue((Property)FACING) || $$4.getValue((Property)HALF) != $$0.getValue((Property)HALF));
/*     */   }
/*     */   
/*     */   public static boolean isStairs(BlockState $$0) {
/* 183 */     return $$0.getBlock() instanceof StairBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 188 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 193 */     Direction $$2 = (Direction)$$0.getValue((Property)FACING);
/* 194 */     StairsShape $$3 = (StairsShape)$$0.getValue((Property)SHAPE);
/* 195 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 197 */         if ($$2.getAxis() == Direction.Axis.Z) {
/* 198 */           switch ($$3) {
/*     */             case LEFT_RIGHT:
/* 200 */               return (BlockState)$$0.rotate(Rotation.CLOCKWISE_180).setValue((Property)SHAPE, (Comparable)StairsShape.INNER_RIGHT);
/*     */             case FRONT_BACK:
/* 202 */               return (BlockState)$$0.rotate(Rotation.CLOCKWISE_180).setValue((Property)SHAPE, (Comparable)StairsShape.INNER_LEFT);
/*     */             case null:
/* 204 */               return (BlockState)$$0.rotate(Rotation.CLOCKWISE_180).setValue((Property)SHAPE, (Comparable)StairsShape.OUTER_RIGHT);
/*     */             case null:
/* 206 */               return (BlockState)$$0.rotate(Rotation.CLOCKWISE_180).setValue((Property)SHAPE, (Comparable)StairsShape.OUTER_LEFT);
/*     */           } 
/* 208 */           return $$0.rotate(Rotation.CLOCKWISE_180);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case FRONT_BACK:
/* 213 */         if ($$2.getAxis() == Direction.Axis.X) {
/* 214 */           switch ($$3) {
/*     */             case LEFT_RIGHT:
/* 216 */               return (BlockState)$$0.rotate(Rotation.CLOCKWISE_180).setValue((Property)SHAPE, (Comparable)StairsShape.INNER_LEFT);
/*     */             case FRONT_BACK:
/* 218 */               return (BlockState)$$0.rotate(Rotation.CLOCKWISE_180).setValue((Property)SHAPE, (Comparable)StairsShape.INNER_RIGHT);
/*     */             case null:
/* 220 */               return (BlockState)$$0.rotate(Rotation.CLOCKWISE_180).setValue((Property)SHAPE, (Comparable)StairsShape.OUTER_RIGHT);
/*     */             case null:
/* 222 */               return (BlockState)$$0.rotate(Rotation.CLOCKWISE_180).setValue((Property)SHAPE, (Comparable)StairsShape.OUTER_LEFT);
/*     */             case null:
/* 224 */               return $$0.rotate(Rotation.CLOCKWISE_180);
/*     */           } 
/*     */         
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/* 231 */     return super.mirror($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 236 */     $$0.add(new Property[] { (Property)FACING, (Property)HALF, (Property)SHAPE, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 241 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 242 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 244 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 249 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\StairBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */