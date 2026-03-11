/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class VineBlock extends Block {
/*  28 */   public static final MapCodec<VineBlock> CODEC = simpleCodec(VineBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<VineBlock> codec() {
/*  32 */     return CODEC;
/*     */   }
/*     */   
/*  35 */   public static final BooleanProperty UP = PipeBlock.UP;
/*  36 */   public static final BooleanProperty NORTH = PipeBlock.NORTH;
/*  37 */   public static final BooleanProperty EAST = PipeBlock.EAST;
/*  38 */   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
/*  39 */   public static final BooleanProperty WEST = PipeBlock.WEST; public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION;
/*     */   static {
/*  41 */     PROPERTY_BY_DIRECTION = (Map<Direction, BooleanProperty>)PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter($$0 -> ($$0.getKey() != Direction.DOWN)).collect(Util.toMap());
/*     */   }
/*     */   protected static final float AABB_OFFSET = 1.0F;
/*  44 */   private static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  45 */   private static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
/*  46 */   private static final VoxelShape EAST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  47 */   private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
/*  48 */   private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
/*     */   
/*     */   private final Map<BlockState, VoxelShape> shapesCache;
/*     */   
/*     */   public VineBlock(BlockBehaviour.Properties $$0) {
/*  53 */     super($$0);
/*  54 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)UP, Boolean.valueOf(false))).setValue((Property)NORTH, Boolean.valueOf(false))).setValue((Property)EAST, Boolean.valueOf(false))).setValue((Property)SOUTH, Boolean.valueOf(false))).setValue((Property)WEST, Boolean.valueOf(false)));
/*     */     
/*  56 */     this.shapesCache = (Map<BlockState, VoxelShape>)ImmutableMap.copyOf((Map)this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), VineBlock::calculateShape)));
/*     */   }
/*     */   
/*     */   private static VoxelShape calculateShape(BlockState $$0) {
/*  60 */     VoxelShape $$1 = Shapes.empty();
/*  61 */     if (((Boolean)$$0.getValue((Property)UP)).booleanValue()) {
/*  62 */       $$1 = UP_AABB;
/*     */     }
/*  64 */     if (((Boolean)$$0.getValue((Property)NORTH)).booleanValue()) {
/*  65 */       $$1 = Shapes.or($$1, NORTH_AABB);
/*     */     }
/*  67 */     if (((Boolean)$$0.getValue((Property)SOUTH)).booleanValue()) {
/*  68 */       $$1 = Shapes.or($$1, SOUTH_AABB);
/*     */     }
/*  70 */     if (((Boolean)$$0.getValue((Property)EAST)).booleanValue()) {
/*  71 */       $$1 = Shapes.or($$1, EAST_AABB);
/*     */     }
/*  73 */     if (((Boolean)$$0.getValue((Property)WEST)).booleanValue()) {
/*  74 */       $$1 = Shapes.or($$1, WEST_AABB);
/*     */     }
/*  76 */     return $$1.isEmpty() ? Shapes.block() : $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  81 */     return this.shapesCache.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  91 */     return hasFaces(getUpdatedState($$0, (BlockGetter)$$1, $$2));
/*     */   }
/*     */   
/*     */   private boolean hasFaces(BlockState $$0) {
/*  95 */     return (countFaces($$0) > 0);
/*     */   }
/*     */   
/*     */   private int countFaces(BlockState $$0) {
/*  99 */     int $$1 = 0;
/* 100 */     for (BooleanProperty $$2 : PROPERTY_BY_DIRECTION.values()) {
/* 101 */       if (((Boolean)$$0.getValue((Property)$$2)).booleanValue()) {
/* 102 */         $$1++;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return $$1;
/*     */   }
/*     */   
/*     */   private boolean canSupportAtFace(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 110 */     if ($$2 == Direction.DOWN) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     BlockPos $$3 = $$1.relative($$2);
/* 115 */     if (isAcceptableNeighbour($$0, $$3, $$2)) {
/* 116 */       return true;
/*     */     }
/*     */     
/* 119 */     if ($$2.getAxis() != Direction.Axis.Y) {
/*     */       
/* 121 */       BooleanProperty $$4 = PROPERTY_BY_DIRECTION.get($$2);
/* 122 */       BlockState $$5 = $$0.getBlockState($$1.above());
/* 123 */       return ($$5.is(this) && ((Boolean)$$5.getValue((Property)$$4)).booleanValue());
/*     */     } 
/* 125 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isAcceptableNeighbour(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 129 */     return MultifaceBlock.canAttachTo($$0, $$2, $$1, $$0.getBlockState($$1));
/*     */   }
/*     */   
/*     */   private BlockState getUpdatedState(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 133 */     BlockPos $$3 = $$2.above();
/* 134 */     if (((Boolean)$$0.getValue((Property)UP)).booleanValue()) {
/* 135 */       $$0 = (BlockState)$$0.setValue((Property)UP, Boolean.valueOf(isAcceptableNeighbour($$1, $$3, Direction.DOWN)));
/*     */     }
/*     */ 
/*     */     
/* 139 */     BlockState $$4 = null;
/* 140 */     for (Direction $$5 : Direction.Plane.HORIZONTAL) {
/* 141 */       BooleanProperty $$6 = getPropertyForFace($$5);
/*     */       
/* 143 */       if (((Boolean)$$0.getValue((Property)$$6)).booleanValue()) {
/* 144 */         boolean $$7 = canSupportAtFace($$1, $$2, $$5);
/* 145 */         if (!$$7) {
/* 146 */           if ($$4 == null) {
/* 147 */             $$4 = $$1.getBlockState($$3);
/*     */           }
/* 149 */           $$7 = ($$4.is(this) && ((Boolean)$$4.getValue((Property)$$6)).booleanValue());
/*     */         } 
/* 151 */         $$0 = (BlockState)$$0.setValue((Property)$$6, Boolean.valueOf($$7));
/*     */       } 
/*     */     } 
/* 154 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 159 */     if ($$1 == Direction.DOWN) {
/* 160 */       return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */     
/* 163 */     BlockState $$6 = getUpdatedState($$0, (BlockGetter)$$3, $$4);
/*     */     
/* 165 */     if (!hasFaces($$6)) {
/* 166 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 169 */     return $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 174 */     if (!$$1.getGameRules().getBoolean(GameRules.RULE_DO_VINES_SPREAD)) {
/*     */       return;
/*     */     }
/*     */     
/* 178 */     if ($$3.nextInt(4) != 0) {
/*     */       return;
/*     */     }
/*     */     
/* 182 */     Direction $$4 = Direction.getRandom($$3);
/*     */     
/* 184 */     BlockPos $$5 = $$2.above();
/* 185 */     if ($$4.getAxis().isHorizontal() && !((Boolean)$$0.getValue((Property)getPropertyForFace($$4))).booleanValue()) {
/* 186 */       if (!canSpread((BlockGetter)$$1, $$2)) {
/*     */         return;
/*     */       }
/*     */       
/* 190 */       BlockPos $$6 = $$2.relative($$4);
/*     */       
/* 192 */       BlockState $$7 = $$1.getBlockState($$6);
/* 193 */       if ($$7.isAir()) {
/*     */         
/* 195 */         Direction $$8 = $$4.getClockWise();
/* 196 */         Direction $$9 = $$4.getCounterClockWise();
/*     */ 
/*     */         
/* 199 */         boolean $$10 = ((Boolean)$$0.getValue((Property)getPropertyForFace($$8))).booleanValue();
/* 200 */         boolean $$11 = ((Boolean)$$0.getValue((Property)getPropertyForFace($$9))).booleanValue();
/*     */         
/* 202 */         BlockPos $$12 = $$6.relative($$8);
/* 203 */         BlockPos $$13 = $$6.relative($$9);
/*     */         
/* 205 */         if ($$10 && isAcceptableNeighbour((BlockGetter)$$1, $$12, $$8)) {
/* 206 */           $$1.setBlock($$6, (BlockState)defaultBlockState().setValue((Property)getPropertyForFace($$8), Boolean.valueOf(true)), 2);
/* 207 */         } else if ($$11 && isAcceptableNeighbour((BlockGetter)$$1, $$13, $$9)) {
/* 208 */           $$1.setBlock($$6, (BlockState)defaultBlockState().setValue((Property)getPropertyForFace($$9), Boolean.valueOf(true)), 2);
/*     */         } else {
/*     */           
/* 211 */           Direction $$14 = $$4.getOpposite();
/* 212 */           if ($$10 && $$1.isEmptyBlock($$12) && isAcceptableNeighbour((BlockGetter)$$1, $$2.relative($$8), $$14)) {
/* 213 */             $$1.setBlock($$12, (BlockState)defaultBlockState().setValue((Property)getPropertyForFace($$14), Boolean.valueOf(true)), 2);
/* 214 */           } else if ($$11 && $$1.isEmptyBlock($$13) && isAcceptableNeighbour((BlockGetter)$$1, $$2.relative($$9), $$14)) {
/* 215 */             $$1.setBlock($$13, (BlockState)defaultBlockState().setValue((Property)getPropertyForFace($$14), Boolean.valueOf(true)), 2);
/*     */           
/*     */           }
/* 218 */           else if ($$3.nextFloat() < 0.05D && isAcceptableNeighbour((BlockGetter)$$1, $$6.above(), Direction.UP)) {
/* 219 */             $$1.setBlock($$6, (BlockState)defaultBlockState().setValue((Property)UP, Boolean.valueOf(true)), 2);
/*     */           }
/*     */         
/*     */         } 
/* 223 */       } else if (isAcceptableNeighbour((BlockGetter)$$1, $$6, $$4)) {
/*     */         
/* 225 */         $$1.setBlock($$2, (BlockState)$$0.setValue((Property)getPropertyForFace($$4), Boolean.valueOf(true)), 2);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 230 */     if ($$4 == Direction.UP && $$2.getY() < $$1.getMaxBuildHeight() - 1) {
/* 231 */       if (canSupportAtFace((BlockGetter)$$1, $$2, $$4)) {
/* 232 */         $$1.setBlock($$2, (BlockState)$$0.setValue((Property)UP, Boolean.valueOf(true)), 2);
/*     */         return;
/*     */       } 
/* 235 */       if ($$1.isEmptyBlock($$5)) {
/* 236 */         if (!canSpread((BlockGetter)$$1, $$2)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 241 */         BlockState $$15 = $$0;
/* 242 */         for (Direction $$16 : Direction.Plane.HORIZONTAL) {
/* 243 */           if ($$3.nextBoolean() || !isAcceptableNeighbour((BlockGetter)$$1, $$5.relative($$16), $$16)) {
/* 244 */             $$15 = (BlockState)$$15.setValue((Property)getPropertyForFace($$16), Boolean.valueOf(false));
/*     */           }
/*     */         } 
/* 247 */         if (hasHorizontalConnection($$15)) {
/* 248 */           $$1.setBlock($$5, $$15, 2);
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/* 253 */     if ($$2.getY() > $$1.getMinBuildHeight()) {
/*     */       
/* 255 */       BlockPos $$17 = $$2.below();
/* 256 */       BlockState $$18 = $$1.getBlockState($$17);
/*     */       
/* 258 */       if ($$18.isAir() || $$18.is(this)) {
/* 259 */         BlockState $$19 = $$18.isAir() ? defaultBlockState() : $$18;
/* 260 */         BlockState $$20 = copyRandomFaces($$0, $$19, $$3);
/* 261 */         if ($$19 != $$20 && hasHorizontalConnection($$20)) {
/* 262 */           $$1.setBlock($$17, $$20, 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private BlockState copyRandomFaces(BlockState $$0, BlockState $$1, RandomSource $$2) {
/* 269 */     for (Direction $$3 : Direction.Plane.HORIZONTAL) {
/* 270 */       if ($$2.nextBoolean()) {
/* 271 */         BooleanProperty $$4 = getPropertyForFace($$3);
/* 272 */         if (((Boolean)$$0.getValue((Property)$$4)).booleanValue()) {
/* 273 */           $$1 = (BlockState)$$1.setValue((Property)$$4, Boolean.valueOf(true));
/*     */         }
/*     */       } 
/*     */     } 
/* 277 */     return $$1;
/*     */   }
/*     */   
/*     */   private boolean hasHorizontalConnection(BlockState $$0) {
/* 281 */     return (((Boolean)$$0.getValue((Property)NORTH)).booleanValue() || ((Boolean)$$0.getValue((Property)EAST)).booleanValue() || ((Boolean)$$0.getValue((Property)SOUTH)).booleanValue() || ((Boolean)$$0.getValue((Property)WEST)).booleanValue());
/*     */   }
/*     */   
/*     */   private boolean canSpread(BlockGetter $$0, BlockPos $$1) {
/* 285 */     int $$2 = 4;
/*     */     
/* 287 */     Iterable<BlockPos> $$3 = BlockPos.betweenClosed($$1
/* 288 */         .getX() - 4, $$1.getY() - 1, $$1.getZ() - 4, $$1
/* 289 */         .getX() + 4, $$1.getY() + 1, $$1.getZ() + 4);
/*     */ 
/*     */     
/* 292 */     int $$4 = 5;
/* 293 */     for (BlockPos $$5 : $$3) {
/* 294 */       if ($$0.getBlockState($$5).is(this) && 
/* 295 */         --$$4 <= 0) {
/* 296 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 300 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/* 305 */     BlockState $$2 = $$1.getLevel().getBlockState($$1.getClickedPos());
/* 306 */     if ($$2.is(this)) {
/* 307 */       return (countFaces($$2) < PROPERTY_BY_DIRECTION.size());
/*     */     }
/*     */     
/* 310 */     return super.canBeReplaced($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 316 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos());
/* 317 */     boolean $$2 = $$1.is(this);
/* 318 */     BlockState $$3 = $$2 ? $$1 : defaultBlockState();
/*     */     
/* 320 */     for (Direction $$4 : $$0.getNearestLookingDirections()) {
/* 321 */       if ($$4 != Direction.DOWN) {
/* 322 */         BooleanProperty $$5 = getPropertyForFace($$4);
/* 323 */         boolean $$6 = ($$2 && ((Boolean)$$1.getValue((Property)$$5)).booleanValue());
/* 324 */         if (!$$6 && canSupportAtFace((BlockGetter)$$0.getLevel(), $$0.getClickedPos(), $$4)) {
/* 325 */           return (BlockState)$$3.setValue((Property)$$5, Boolean.valueOf(true));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 330 */     return $$2 ? $$3 : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 335 */     $$0.add(new Property[] { (Property)UP, (Property)NORTH, (Property)EAST, (Property)SOUTH, (Property)WEST });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 340 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 342 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)SOUTH))).setValue((Property)EAST, $$0.getValue((Property)WEST))).setValue((Property)SOUTH, $$0.getValue((Property)NORTH))).setValue((Property)WEST, $$0.getValue((Property)EAST));
/*     */       case FRONT_BACK:
/* 344 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)EAST))).setValue((Property)EAST, $$0.getValue((Property)SOUTH))).setValue((Property)SOUTH, $$0.getValue((Property)WEST))).setValue((Property)WEST, $$0.getValue((Property)NORTH));
/*     */       case null:
/* 346 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)WEST))).setValue((Property)EAST, $$0.getValue((Property)NORTH))).setValue((Property)SOUTH, $$0.getValue((Property)EAST))).setValue((Property)WEST, $$0.getValue((Property)SOUTH));
/*     */     } 
/* 348 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 354 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 356 */         return (BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)SOUTH))).setValue((Property)SOUTH, $$0.getValue((Property)NORTH));
/*     */       case FRONT_BACK:
/* 358 */         return (BlockState)((BlockState)$$0.setValue((Property)EAST, $$0.getValue((Property)WEST))).setValue((Property)WEST, $$0.getValue((Property)EAST));
/*     */     } 
/*     */ 
/*     */     
/* 362 */     return super.mirror($$0, $$1);
/*     */   }
/*     */   
/*     */   public static BooleanProperty getPropertyForFace(Direction $$0) {
/* 366 */     return PROPERTY_BY_DIRECTION.get($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\VineBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */