/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.WallSide;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class WallBlock extends Block implements SimpleWaterloggedBlock {
/*  30 */   public static final MapCodec<WallBlock> CODEC = simpleCodec(WallBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<WallBlock> codec() {
/*  34 */     return CODEC;
/*     */   }
/*     */   
/*  37 */   public static final BooleanProperty UP = BlockStateProperties.UP;
/*  38 */   public static final EnumProperty<WallSide> EAST_WALL = BlockStateProperties.EAST_WALL;
/*  39 */   public static final EnumProperty<WallSide> NORTH_WALL = BlockStateProperties.NORTH_WALL;
/*  40 */   public static final EnumProperty<WallSide> SOUTH_WALL = BlockStateProperties.SOUTH_WALL;
/*  41 */   public static final EnumProperty<WallSide> WEST_WALL = BlockStateProperties.WEST_WALL;
/*  42 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*     */   private final Map<BlockState, VoxelShape> shapeByIndex;
/*     */   
/*     */   private final Map<BlockState, VoxelShape> collisionShapeByIndex;
/*     */   
/*     */   private static final int WALL_WIDTH = 3;
/*     */   
/*     */   private static final int WALL_HEIGHT = 14;
/*     */   private static final int POST_WIDTH = 4;
/*     */   private static final int POST_COVER_WIDTH = 1;
/*     */   private static final int WALL_COVER_START = 7;
/*     */   private static final int WALL_COVER_END = 9;
/*  55 */   private static final VoxelShape POST_TEST = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
/*  56 */   private static final VoxelShape NORTH_TEST = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 9.0D);
/*  57 */   private static final VoxelShape SOUTH_TEST = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 16.0D);
/*  58 */   private static final VoxelShape WEST_TEST = Block.box(0.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
/*  59 */   private static final VoxelShape EAST_TEST = Block.box(7.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);
/*     */   
/*     */   public WallBlock(BlockBehaviour.Properties $$0) {
/*  62 */     super($$0);
/*  63 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)UP, Boolean.valueOf(true))).setValue((Property)NORTH_WALL, (Comparable)WallSide.NONE)).setValue((Property)EAST_WALL, (Comparable)WallSide.NONE)).setValue((Property)SOUTH_WALL, (Comparable)WallSide.NONE)).setValue((Property)WEST_WALL, (Comparable)WallSide.NONE)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */     
/*  65 */     this.shapeByIndex = makeShapes(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
/*  66 */     this.collisionShapeByIndex = makeShapes(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
/*     */   }
/*     */   
/*     */   private static VoxelShape applyWallShape(VoxelShape $$0, WallSide $$1, VoxelShape $$2, VoxelShape $$3) {
/*  70 */     if ($$1 == WallSide.TALL) {
/*  71 */       return Shapes.or($$0, $$3);
/*     */     }
/*  73 */     if ($$1 == WallSide.LOW) {
/*  74 */       return Shapes.or($$0, $$2);
/*     */     }
/*  76 */     return $$0;
/*     */   }
/*     */   
/*     */   private Map<BlockState, VoxelShape> makeShapes(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  80 */     float $$6 = 8.0F - $$0;
/*  81 */     float $$7 = 8.0F + $$0;
/*     */     
/*  83 */     float $$8 = 8.0F - $$1;
/*  84 */     float $$9 = 8.0F + $$1;
/*     */     
/*  86 */     VoxelShape $$10 = Block.box($$6, 0.0D, $$6, $$7, $$2, $$7);
/*  87 */     VoxelShape $$11 = Block.box($$8, $$3, 0.0D, $$9, $$4, $$9);
/*  88 */     VoxelShape $$12 = Block.box($$8, $$3, $$8, $$9, $$4, 16.0D);
/*  89 */     VoxelShape $$13 = Block.box(0.0D, $$3, $$8, $$9, $$4, $$9);
/*  90 */     VoxelShape $$14 = Block.box($$8, $$3, $$8, 16.0D, $$4, $$9);
/*     */     
/*  92 */     VoxelShape $$15 = Block.box($$8, $$3, 0.0D, $$9, $$5, $$9);
/*  93 */     VoxelShape $$16 = Block.box($$8, $$3, $$8, $$9, $$5, 16.0D);
/*  94 */     VoxelShape $$17 = Block.box(0.0D, $$3, $$8, $$9, $$5, $$9);
/*  95 */     VoxelShape $$18 = Block.box($$8, $$3, $$8, 16.0D, $$5, $$9);
/*     */     
/*  97 */     ImmutableMap.Builder<BlockState, VoxelShape> $$19 = ImmutableMap.builder();
/*     */     
/*  99 */     for (Boolean $$20 : UP.getPossibleValues()) {
/* 100 */       for (WallSide $$21 : EAST_WALL.getPossibleValues()) {
/* 101 */         for (WallSide $$22 : NORTH_WALL.getPossibleValues()) {
/* 102 */           for (WallSide $$23 : WEST_WALL.getPossibleValues()) {
/* 103 */             for (WallSide $$24 : SOUTH_WALL.getPossibleValues()) {
/* 104 */               VoxelShape $$25 = Shapes.empty();
/* 105 */               $$25 = applyWallShape($$25, $$21, $$14, $$18);
/* 106 */               $$25 = applyWallShape($$25, $$23, $$13, $$17);
/* 107 */               $$25 = applyWallShape($$25, $$22, $$11, $$15);
/* 108 */               $$25 = applyWallShape($$25, $$24, $$12, $$16);
/* 109 */               if ($$20.booleanValue()) {
/* 110 */                 $$25 = Shapes.or($$25, $$10);
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 117 */               BlockState $$26 = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)UP, $$20)).setValue((Property)EAST_WALL, (Comparable)$$21)).setValue((Property)WEST_WALL, (Comparable)$$23)).setValue((Property)NORTH_WALL, (Comparable)$$22)).setValue((Property)SOUTH_WALL, (Comparable)$$24);
/*     */               
/* 119 */               $$19.put($$26.setValue((Property)WATERLOGGED, Boolean.valueOf(false)), $$25);
/* 120 */               $$19.put($$26.setValue((Property)WATERLOGGED, Boolean.valueOf(true)), $$25);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 126 */     return (Map<BlockState, VoxelShape>)$$19.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 131 */     return this.shapeByIndex.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 136 */     return this.collisionShapeByIndex.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   private boolean connectsTo(BlockState $$0, boolean $$1, Direction $$2) {
/* 145 */     Block $$3 = $$0.getBlock();
/*     */     
/* 147 */     boolean $$4 = ($$3 instanceof FenceGateBlock && FenceGateBlock.connectsToDirection($$0, $$2));
/* 148 */     return ($$0.is(BlockTags.WALLS) || (!isExceptionForConnection($$0) && $$1) || $$3 instanceof IronBarsBlock || $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 153 */     Level level = $$0.getLevel();
/* 154 */     BlockPos $$2 = $$0.getClickedPos();
/* 155 */     FluidState $$3 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*     */     
/* 157 */     BlockPos $$4 = $$2.north();
/* 158 */     BlockPos $$5 = $$2.east();
/* 159 */     BlockPos $$6 = $$2.south();
/* 160 */     BlockPos $$7 = $$2.west();
/* 161 */     BlockPos $$8 = $$2.above();
/*     */     
/* 163 */     BlockState $$9 = level.getBlockState($$4);
/* 164 */     BlockState $$10 = level.getBlockState($$5);
/* 165 */     BlockState $$11 = level.getBlockState($$6);
/* 166 */     BlockState $$12 = level.getBlockState($$7);
/* 167 */     BlockState $$13 = level.getBlockState($$8);
/*     */     
/* 169 */     boolean $$14 = connectsTo($$9, $$9.isFaceSturdy((BlockGetter)level, $$4, Direction.SOUTH), Direction.SOUTH);
/* 170 */     boolean $$15 = connectsTo($$10, $$10.isFaceSturdy((BlockGetter)level, $$5, Direction.WEST), Direction.WEST);
/* 171 */     boolean $$16 = connectsTo($$11, $$11.isFaceSturdy((BlockGetter)level, $$6, Direction.NORTH), Direction.NORTH);
/* 172 */     boolean $$17 = connectsTo($$12, $$12.isFaceSturdy((BlockGetter)level, $$7, Direction.EAST), Direction.EAST);
/*     */     
/* 174 */     BlockState $$18 = (BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(($$3.getType() == Fluids.WATER)));
/* 175 */     return updateShape((LevelReader)level, $$18, $$8, $$13, $$14, $$15, $$16, $$17);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 180 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 181 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/* 184 */     if ($$1 == Direction.DOWN) {
/* 185 */       return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */     
/* 188 */     if ($$1 == Direction.UP) {
/* 189 */       return topUpdate((LevelReader)$$3, $$0, $$5, $$2);
/*     */     }
/*     */     
/* 192 */     return sideUpdate((LevelReader)$$3, $$4, $$0, $$5, $$2, $$1);
/*     */   }
/*     */   
/*     */   private static boolean isConnected(BlockState $$0, Property<WallSide> $$1) {
/* 196 */     return ($$0.getValue($$1) != WallSide.NONE);
/*     */   }
/*     */   
/*     */   private static boolean isCovered(VoxelShape $$0, VoxelShape $$1) {
/* 200 */     return !Shapes.joinIsNotEmpty($$1, $$0, BooleanOp.ONLY_FIRST);
/*     */   }
/*     */   
/*     */   private BlockState topUpdate(LevelReader $$0, BlockState $$1, BlockPos $$2, BlockState $$3) {
/* 204 */     boolean $$4 = isConnected($$1, (Property<WallSide>)NORTH_WALL);
/* 205 */     boolean $$5 = isConnected($$1, (Property<WallSide>)EAST_WALL);
/* 206 */     boolean $$6 = isConnected($$1, (Property<WallSide>)SOUTH_WALL);
/* 207 */     boolean $$7 = isConnected($$1, (Property<WallSide>)WEST_WALL);
/*     */     
/* 209 */     return updateShape($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   private BlockState sideUpdate(LevelReader $$0, BlockPos $$1, BlockState $$2, BlockPos $$3, BlockState $$4, Direction $$5) {
/* 213 */     Direction $$6 = $$5.getOpposite();
/* 214 */     boolean $$7 = ($$5 == Direction.NORTH) ? connectsTo($$4, $$4.isFaceSturdy((BlockGetter)$$0, $$3, $$6), $$6) : isConnected($$2, (Property<WallSide>)NORTH_WALL);
/* 215 */     boolean $$8 = ($$5 == Direction.EAST) ? connectsTo($$4, $$4.isFaceSturdy((BlockGetter)$$0, $$3, $$6), $$6) : isConnected($$2, (Property<WallSide>)EAST_WALL);
/* 216 */     boolean $$9 = ($$5 == Direction.SOUTH) ? connectsTo($$4, $$4.isFaceSturdy((BlockGetter)$$0, $$3, $$6), $$6) : isConnected($$2, (Property<WallSide>)SOUTH_WALL);
/* 217 */     boolean $$10 = ($$5 == Direction.WEST) ? connectsTo($$4, $$4.isFaceSturdy((BlockGetter)$$0, $$3, $$6), $$6) : isConnected($$2, (Property<WallSide>)WEST_WALL);
/*     */     
/* 219 */     BlockPos $$11 = $$1.above();
/* 220 */     BlockState $$12 = $$0.getBlockState($$11);
/* 221 */     return updateShape($$0, $$2, $$11, $$12, $$7, $$8, $$9, $$10);
/*     */   }
/*     */   
/*     */   private BlockState updateShape(LevelReader $$0, BlockState $$1, BlockPos $$2, BlockState $$3, boolean $$4, boolean $$5, boolean $$6, boolean $$7) {
/* 225 */     VoxelShape $$8 = $$3.getCollisionShape((BlockGetter)$$0, $$2).getFaceShape(Direction.DOWN);
/* 226 */     BlockState $$9 = updateSides($$1, $$4, $$5, $$6, $$7, $$8);
/*     */     
/* 228 */     return (BlockState)$$9.setValue((Property)UP, Boolean.valueOf(shouldRaisePost($$9, $$3, $$8)));
/*     */   }
/*     */   
/*     */   private boolean shouldRaisePost(BlockState $$0, BlockState $$1, VoxelShape $$2) {
/* 232 */     boolean $$3 = ($$1.getBlock() instanceof WallBlock && ((Boolean)$$1.getValue((Property)UP)).booleanValue());
/* 233 */     if ($$3) {
/* 234 */       return true;
/*     */     }
/*     */     
/* 237 */     WallSide $$4 = (WallSide)$$0.getValue((Property)NORTH_WALL);
/* 238 */     WallSide $$5 = (WallSide)$$0.getValue((Property)SOUTH_WALL);
/* 239 */     WallSide $$6 = (WallSide)$$0.getValue((Property)EAST_WALL);
/* 240 */     WallSide $$7 = (WallSide)$$0.getValue((Property)WEST_WALL);
/*     */     
/* 242 */     boolean $$8 = ($$5 == WallSide.NONE);
/* 243 */     boolean $$9 = ($$7 == WallSide.NONE);
/* 244 */     boolean $$10 = ($$6 == WallSide.NONE);
/* 245 */     boolean $$11 = ($$4 == WallSide.NONE);
/*     */     
/* 247 */     boolean $$12 = (($$11 && $$8 && $$9 && $$10) || $$11 != $$8 || $$9 != $$10);
/*     */ 
/*     */     
/* 250 */     if ($$12) {
/* 251 */       return true;
/*     */     }
/*     */     
/* 254 */     boolean $$13 = (($$4 == WallSide.TALL && $$5 == WallSide.TALL) || ($$6 == WallSide.TALL && $$7 == WallSide.TALL));
/*     */     
/* 256 */     if ($$13) {
/* 257 */       return false;
/*     */     }
/*     */     
/* 260 */     return ($$1.is(BlockTags.WALL_POST_OVERRIDE) || isCovered($$2, POST_TEST));
/*     */   }
/*     */   
/*     */   private BlockState updateSides(BlockState $$0, boolean $$1, boolean $$2, boolean $$3, boolean $$4, VoxelShape $$5) {
/* 264 */     return (BlockState)((BlockState)((BlockState)((BlockState)$$0
/* 265 */       .setValue((Property)NORTH_WALL, (Comparable)makeWallState($$1, $$5, NORTH_TEST)))
/* 266 */       .setValue((Property)EAST_WALL, (Comparable)makeWallState($$2, $$5, EAST_TEST)))
/* 267 */       .setValue((Property)SOUTH_WALL, (Comparable)makeWallState($$3, $$5, SOUTH_TEST)))
/* 268 */       .setValue((Property)WEST_WALL, (Comparable)makeWallState($$4, $$5, WEST_TEST));
/*     */   }
/*     */   
/*     */   private WallSide makeWallState(boolean $$0, VoxelShape $$1, VoxelShape $$2) {
/* 272 */     if ($$0) {
/* 273 */       if (isCovered($$1, $$2)) {
/* 274 */         return WallSide.TALL;
/*     */       }
/* 276 */       return WallSide.LOW;
/*     */     } 
/*     */     
/* 279 */     return WallSide.NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 285 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 286 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 288 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 293 */     return !((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 298 */     $$0.add(new Property[] { (Property)UP, (Property)NORTH_WALL, (Property)EAST_WALL, (Property)WEST_WALL, (Property)SOUTH_WALL, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 303 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 305 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH_WALL, $$0.getValue((Property)SOUTH_WALL))).setValue((Property)EAST_WALL, $$0.getValue((Property)WEST_WALL))).setValue((Property)SOUTH_WALL, $$0.getValue((Property)NORTH_WALL))).setValue((Property)WEST_WALL, $$0.getValue((Property)EAST_WALL));
/*     */       case FRONT_BACK:
/* 307 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH_WALL, $$0.getValue((Property)EAST_WALL))).setValue((Property)EAST_WALL, $$0.getValue((Property)SOUTH_WALL))).setValue((Property)SOUTH_WALL, $$0.getValue((Property)WEST_WALL))).setValue((Property)WEST_WALL, $$0.getValue((Property)NORTH_WALL));
/*     */       case null:
/* 309 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH_WALL, $$0.getValue((Property)WEST_WALL))).setValue((Property)EAST_WALL, $$0.getValue((Property)NORTH_WALL))).setValue((Property)SOUTH_WALL, $$0.getValue((Property)EAST_WALL))).setValue((Property)WEST_WALL, $$0.getValue((Property)SOUTH_WALL));
/*     */     } 
/* 311 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 317 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 319 */         return (BlockState)((BlockState)$$0.setValue((Property)NORTH_WALL, $$0.getValue((Property)SOUTH_WALL))).setValue((Property)SOUTH_WALL, $$0.getValue((Property)NORTH_WALL));
/*     */       case FRONT_BACK:
/* 321 */         return (BlockState)((BlockState)$$0.setValue((Property)EAST_WALL, $$0.getValue((Property)WEST_WALL))).setValue((Property)WEST_WALL, $$0.getValue((Property)EAST_WALL));
/*     */     } 
/*     */ 
/*     */     
/* 325 */     return super.mirror($$0, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WallBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */