/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
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
/*     */ public abstract class CrossCollisionBlock extends Block implements SimpleWaterloggedBlock {
/*  23 */   public static final BooleanProperty NORTH = PipeBlock.NORTH;
/*  24 */   public static final BooleanProperty EAST = PipeBlock.EAST;
/*  25 */   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
/*  26 */   public static final BooleanProperty WEST = PipeBlock.WEST; protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION;
/*  27 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED; static {
/*  28 */     PROPERTY_BY_DIRECTION = (Map<Direction, BooleanProperty>)PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter($$0 -> ((Direction)$$0.getKey()).getAxis().isHorizontal()).collect(Util.toMap());
/*     */   }
/*     */   protected final VoxelShape[] collisionShapeByIndex;
/*     */   protected final VoxelShape[] shapeByIndex;
/*  32 */   private final Object2IntMap<BlockState> stateToIndex = (Object2IntMap<BlockState>)new Object2IntOpenHashMap();
/*     */   
/*     */   protected CrossCollisionBlock(float $$0, float $$1, float $$2, float $$3, float $$4, BlockBehaviour.Properties $$5) {
/*  35 */     super($$5);
/*     */     
/*  37 */     this.collisionShapeByIndex = makeShapes($$0, $$1, $$4, 0.0F, $$4);
/*  38 */     this.shapeByIndex = makeShapes($$0, $$1, $$2, 0.0F, $$3);
/*     */     
/*  40 */     for (UnmodifiableIterator<BlockState> unmodifiableIterator = this.stateDefinition.getPossibleStates().iterator(); unmodifiableIterator.hasNext(); ) { BlockState $$6 = unmodifiableIterator.next();
/*  41 */       getAABBIndex($$6); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VoxelShape[] makeShapes(float $$0, float $$1, float $$2, float $$3, float $$4) {
/*  49 */     float $$5 = 8.0F - $$0;
/*  50 */     float $$6 = 8.0F + $$0;
/*     */     
/*  52 */     float $$7 = 8.0F - $$1;
/*  53 */     float $$8 = 8.0F + $$1;
/*     */     
/*  55 */     VoxelShape $$9 = Block.box($$5, 0.0D, $$5, $$6, $$2, $$6);
/*  56 */     VoxelShape $$10 = Block.box($$7, $$3, 0.0D, $$8, $$4, $$8);
/*  57 */     VoxelShape $$11 = Block.box($$7, $$3, $$7, $$8, $$4, 16.0D);
/*  58 */     VoxelShape $$12 = Block.box(0.0D, $$3, $$7, $$8, $$4, $$8);
/*  59 */     VoxelShape $$13 = Block.box($$7, $$3, $$7, 16.0D, $$4, $$8);
/*     */     
/*  61 */     VoxelShape $$14 = Shapes.or($$10, $$13);
/*  62 */     VoxelShape $$15 = Shapes.or($$11, $$12);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     VoxelShape[] $$16 = { Shapes.empty(), $$11, $$12, $$15, $$10, Shapes.or($$11, $$10), Shapes.or($$12, $$10), Shapes.or($$15, $$10), $$13, Shapes.or($$11, $$13), Shapes.or($$12, $$13), Shapes.or($$15, $$13), $$14, Shapes.or($$11, $$14), Shapes.or($$12, $$14), Shapes.or($$15, $$14) };
/*     */     
/*  82 */     for (int $$17 = 0; $$17 < 16; $$17++) {
/*  83 */       $$16[$$17] = Shapes.or($$9, $$16[$$17]);
/*     */     }
/*  85 */     return $$16;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  90 */     return !((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  95 */     return this.shapeByIndex[getAABBIndex($$0)];
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 100 */     return this.collisionShapeByIndex[getAABBIndex($$0)];
/*     */   }
/*     */   
/*     */   private static int indexFor(Direction $$0) {
/* 104 */     return 1 << $$0.get2DDataValue();
/*     */   }
/*     */   
/*     */   protected int getAABBIndex(BlockState $$0) {
/* 108 */     return this.stateToIndex.computeIntIfAbsent($$0, $$0 -> {
/*     */           int $$1 = 0;
/*     */           if (((Boolean)$$0.getValue((Property)NORTH)).booleanValue()) {
/*     */             $$1 |= indexFor(Direction.NORTH);
/*     */           }
/*     */           if (((Boolean)$$0.getValue((Property)EAST)).booleanValue()) {
/*     */             $$1 |= indexFor(Direction.EAST);
/*     */           }
/*     */           if (((Boolean)$$0.getValue((Property)SOUTH)).booleanValue()) {
/*     */             $$1 |= indexFor(Direction.SOUTH);
/*     */           }
/*     */           if (((Boolean)$$0.getValue((Property)WEST)).booleanValue()) {
/*     */             $$1 |= indexFor(Direction.WEST);
/*     */           }
/*     */           return $$1;
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 128 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 129 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 131 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 141 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 143 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)SOUTH))).setValue((Property)EAST, $$0.getValue((Property)WEST))).setValue((Property)SOUTH, $$0.getValue((Property)NORTH))).setValue((Property)WEST, $$0.getValue((Property)EAST));
/*     */       case FRONT_BACK:
/* 145 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)EAST))).setValue((Property)EAST, $$0.getValue((Property)SOUTH))).setValue((Property)SOUTH, $$0.getValue((Property)WEST))).setValue((Property)WEST, $$0.getValue((Property)NORTH));
/*     */       case null:
/* 147 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)WEST))).setValue((Property)EAST, $$0.getValue((Property)NORTH))).setValue((Property)SOUTH, $$0.getValue((Property)EAST))).setValue((Property)WEST, $$0.getValue((Property)SOUTH));
/*     */     } 
/* 149 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 155 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 157 */         return (BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)SOUTH))).setValue((Property)SOUTH, $$0.getValue((Property)NORTH));
/*     */       case FRONT_BACK:
/* 159 */         return (BlockState)((BlockState)$$0.setValue((Property)EAST, $$0.getValue((Property)WEST))).setValue((Property)WEST, $$0.getValue((Property)EAST));
/*     */     } 
/*     */ 
/*     */     
/* 163 */     return super.mirror($$0, $$1);
/*     */   }
/*     */   
/*     */   protected abstract MapCodec<? extends CrossCollisionBlock> codec();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CrossCollisionBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */