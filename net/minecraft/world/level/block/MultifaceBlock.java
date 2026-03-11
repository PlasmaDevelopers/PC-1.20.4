/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
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
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ public abstract class MultifaceBlock
/*     */   extends Block
/*     */ {
/*     */   private static final float AABB_OFFSET = 1.0F;
/*  41 */   private static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  42 */   private static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
/*  43 */   private static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
/*  44 */   private static final VoxelShape EAST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  45 */   private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
/*  46 */   private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
/*     */   private static final Map<Direction, VoxelShape> SHAPE_BY_DIRECTION;
/*  48 */   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;
/*     */   static {
/*  50 */     SHAPE_BY_DIRECTION = (Map<Direction, VoxelShape>)Util.make(Maps.newEnumMap(Direction.class), $$0 -> {
/*     */           $$0.put(Direction.NORTH, NORTH_AABB);
/*     */           $$0.put(Direction.EAST, EAST_AABB);
/*     */           $$0.put(Direction.SOUTH, SOUTH_AABB);
/*     */           $$0.put(Direction.WEST, WEST_AABB);
/*     */           $$0.put(Direction.UP, UP_AABB);
/*     */           $$0.put(Direction.DOWN, DOWN_AABB);
/*     */         });
/*     */   }
/*  59 */   protected static final Direction[] DIRECTIONS = Direction.values();
/*     */   
/*     */   private final ImmutableMap<BlockState, VoxelShape> shapesCache;
/*     */   
/*     */   private final boolean canRotate;
/*     */   private final boolean canMirrorX;
/*     */   private final boolean canMirrorZ;
/*     */   
/*     */   public MultifaceBlock(BlockBehaviour.Properties $$0) {
/*  68 */     super($$0);
/*  69 */     registerDefaultState(getDefaultMultifaceState(this.stateDefinition));
/*  70 */     this.shapesCache = getShapeForEachState(MultifaceBlock::calculateMultifaceShape);
/*     */     
/*  72 */     this.canRotate = Direction.Plane.HORIZONTAL.stream().allMatch(this::isFaceSupported);
/*  73 */     this.canMirrorX = (Direction.Plane.HORIZONTAL.stream().filter((Predicate)Direction.Axis.X).filter(this::isFaceSupported).count() % 2L == 0L);
/*  74 */     this.canMirrorZ = (Direction.Plane.HORIZONTAL.stream().filter((Predicate)Direction.Axis.Z).filter(this::isFaceSupported).count() % 2L == 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<Direction> availableFaces(BlockState $$0) {
/*  81 */     if (!($$0.getBlock() instanceof MultifaceBlock)) {
/*  82 */       return Set.of();
/*     */     }
/*  84 */     Set<Direction> $$1 = EnumSet.noneOf(Direction.class);
/*  85 */     for (Direction $$2 : Direction.values()) {
/*  86 */       if (hasFace($$0, $$2)) {
/*  87 */         $$1.add($$2);
/*     */       }
/*     */     } 
/*  90 */     return $$1;
/*     */   }
/*     */   
/*     */   public static Set<Direction> unpack(byte $$0) {
/*  94 */     Set<Direction> $$1 = EnumSet.noneOf(Direction.class);
/*  95 */     for (Direction $$2 : Direction.values()) {
/*  96 */       if (($$0 & (byte)(1 << $$2.ordinal())) > 0) {
/*  97 */         $$1.add($$2);
/*     */       }
/*     */     } 
/* 100 */     return $$1;
/*     */   }
/*     */   
/*     */   public static byte pack(Collection<Direction> $$0) {
/* 104 */     byte $$1 = 0;
/* 105 */     for (Direction $$2 : $$0) {
/* 106 */       $$1 = (byte)($$1 | 1 << $$2.ordinal());
/*     */     }
/* 108 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isFaceSupported(Direction $$0) {
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 118 */     for (Direction $$1 : DIRECTIONS) {
/* 119 */       if (isFaceSupported($$1)) {
/* 120 */         $$0.add(new Property[] { (Property)getFaceProperty($$1) });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 131 */     if (!hasAnyFace($$0)) {
/* 132 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 135 */     if (!hasFace($$0, $$1) || canAttachTo((BlockGetter)$$3, $$1, $$5, $$2)) {
/* 136 */       return $$0;
/*     */     }
/* 138 */     return removeFace($$0, getFaceProperty($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 143 */     return (VoxelShape)this.shapesCache.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 148 */     boolean $$3 = false;
/* 149 */     for (Direction $$4 : DIRECTIONS) {
/* 150 */       if (hasFace($$0, $$4)) {
/*     */ 
/*     */         
/* 153 */         BlockPos $$5 = $$2.relative($$4);
/* 154 */         if (!canAttachTo((BlockGetter)$$1, $$4, $$5, $$1.getBlockState($$5))) {
/* 155 */           return false;
/*     */         }
/* 157 */         $$3 = true;
/*     */       } 
/* 159 */     }  return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/* 164 */     return hasAnyVacantFace($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 170 */     Level $$1 = $$0.getLevel();
/* 171 */     BlockPos $$2 = $$0.getClickedPos();
/* 172 */     BlockState $$3 = $$1.getBlockState($$2);
/* 173 */     return Arrays.<Direction>stream($$0.getNearestLookingDirections())
/* 174 */       .map($$3 -> getStateForPlacement($$0, (BlockGetter)$$1, $$2, $$3))
/* 175 */       .filter(Objects::nonNull)
/* 176 */       .findFirst()
/* 177 */       .orElse(null);
/*     */   }
/*     */   
/*     */   public boolean isValidStateForPlacement(BlockGetter $$0, BlockState $$1, BlockPos $$2, Direction $$3) {
/* 181 */     if (!isFaceSupported($$3) || ($$1.is(this) && hasFace($$1, $$3))) {
/* 182 */       return false;
/*     */     }
/* 184 */     BlockPos $$4 = $$2.relative($$3);
/* 185 */     return canAttachTo($$0, $$3, $$4, $$0.getBlockState($$4));
/*     */   }
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*     */     BlockState $$6;
/* 190 */     if (!isValidStateForPlacement($$1, $$0, $$2, $$3)) {
/* 191 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 195 */     if ($$0.is(this)) {
/*     */       
/* 197 */       BlockState $$4 = $$0;
/* 198 */     } else if (isWaterloggable() && $$0.getFluidState().isSourceOfType((Fluid)Fluids.WATER)) {
/* 199 */       BlockState $$5 = (BlockState)defaultBlockState().setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
/*     */     } else {
/* 201 */       $$6 = defaultBlockState();
/*     */     } 
/*     */     
/* 204 */     return (BlockState)$$6.setValue((Property)getFaceProperty($$3), Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 209 */     if (!this.canRotate) {
/* 210 */       return $$0;
/*     */     }
/*     */     
/* 213 */     Objects.requireNonNull($$1); return mapDirections($$0, $$1::rotate);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 218 */     if ($$1 == Mirror.FRONT_BACK && !this.canMirrorX) {
/* 219 */       return $$0;
/*     */     }
/* 221 */     if ($$1 == Mirror.LEFT_RIGHT && !this.canMirrorZ) {
/* 222 */       return $$0;
/*     */     }
/*     */     
/* 225 */     Objects.requireNonNull($$1); return mapDirections($$0, $$1::mirror);
/*     */   }
/*     */   
/*     */   private BlockState mapDirections(BlockState $$0, Function<Direction, Direction> $$1) {
/* 229 */     BlockState $$2 = $$0;
/* 230 */     for (Direction $$3 : DIRECTIONS) {
/* 231 */       if (isFaceSupported($$3)) {
/* 232 */         $$2 = (BlockState)$$2.setValue((Property)getFaceProperty($$1.apply($$3)), $$0.getValue((Property)getFaceProperty($$3)));
/*     */       }
/*     */     } 
/* 235 */     return $$2;
/*     */   }
/*     */   
/*     */   public static boolean hasFace(BlockState $$0, Direction $$1) {
/* 239 */     BooleanProperty $$2 = getFaceProperty($$1);
/* 240 */     return ($$0.hasProperty((Property)$$2) && ((Boolean)$$0.getValue((Property)$$2)).booleanValue());
/*     */   }
/*     */   
/*     */   public static boolean canAttachTo(BlockGetter $$0, Direction $$1, BlockPos $$2, BlockState $$3) {
/* 244 */     return (Block.isFaceFull($$3.getBlockSupportShape($$0, $$2), $$1.getOpposite()) || 
/* 245 */       Block.isFaceFull($$3.getCollisionShape($$0, $$2), $$1.getOpposite()));
/*     */   }
/*     */   
/*     */   private boolean isWaterloggable() {
/* 249 */     return this.stateDefinition.getProperties().contains(BlockStateProperties.WATERLOGGED);
/*     */   }
/*     */   
/*     */   private static BlockState removeFace(BlockState $$0, BooleanProperty $$1) {
/* 253 */     BlockState $$2 = (BlockState)$$0.setValue((Property)$$1, Boolean.valueOf(false));
/* 254 */     if (hasAnyFace($$2)) {
/* 255 */       return $$2;
/*     */     }
/*     */     
/* 258 */     return Blocks.AIR.defaultBlockState();
/*     */   }
/*     */   
/*     */   public static BooleanProperty getFaceProperty(Direction $$0) {
/* 262 */     return PROPERTY_BY_DIRECTION.get($$0);
/*     */   }
/*     */   
/*     */   private static BlockState getDefaultMultifaceState(StateDefinition<Block, BlockState> $$0) {
/* 266 */     BlockState $$1 = (BlockState)$$0.any();
/* 267 */     for (BooleanProperty $$2 : PROPERTY_BY_DIRECTION.values()) {
/* 268 */       if ($$1.hasProperty((Property)$$2)) {
/* 269 */         $$1 = (BlockState)$$1.setValue((Property)$$2, Boolean.valueOf(false));
/*     */       }
/*     */     } 
/* 272 */     return $$1;
/*     */   }
/*     */   
/*     */   private static VoxelShape calculateMultifaceShape(BlockState $$0) {
/* 276 */     VoxelShape $$1 = Shapes.empty();
/* 277 */     for (Direction $$2 : DIRECTIONS) {
/* 278 */       if (hasFace($$0, $$2)) {
/* 279 */         $$1 = Shapes.or($$1, SHAPE_BY_DIRECTION.get($$2));
/*     */       }
/*     */     } 
/* 282 */     return $$1.isEmpty() ? Shapes.block() : $$1;
/*     */   }
/*     */   
/*     */   protected static boolean hasAnyFace(BlockState $$0) {
/* 286 */     return Arrays.<Direction>stream(DIRECTIONS).anyMatch($$1 -> hasFace($$0, $$1));
/*     */   }
/*     */   
/*     */   private static boolean hasAnyVacantFace(BlockState $$0) {
/* 290 */     return Arrays.<Direction>stream(DIRECTIONS).anyMatch($$1 -> !hasFace($$0, $$1));
/*     */   }
/*     */   
/*     */   protected abstract MapCodec<? extends MultifaceBlock> codec();
/*     */   
/*     */   public abstract MultifaceSpreader getSpreader();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MultifaceBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */