/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class CocoaBlock extends HorizontalDirectionalBlock implements BonemealableBlock {
/*  25 */   public static final MapCodec<CocoaBlock> CODEC = simpleCodec(CocoaBlock::new);
/*     */   public static final int MAX_AGE = 2;
/*     */   
/*     */   public MapCodec<CocoaBlock> codec() {
/*  29 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  33 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
/*     */   
/*     */   protected static final int AGE_0_WIDTH = 4;
/*     */   
/*     */   protected static final int AGE_0_HEIGHT = 5;
/*     */   protected static final int AGE_0_HALFWIDTH = 2;
/*     */   protected static final int AGE_1_WIDTH = 6;
/*     */   protected static final int AGE_1_HEIGHT = 7;
/*     */   protected static final int AGE_1_HALFWIDTH = 3;
/*     */   protected static final int AGE_2_WIDTH = 8;
/*     */   protected static final int AGE_2_HEIGHT = 9;
/*     */   protected static final int AGE_2_HALFWIDTH = 4;
/*  45 */   protected static final VoxelShape[] EAST_AABB = new VoxelShape[] {
/*  46 */       Block.box(11.0D, 7.0D, 6.0D, 15.0D, 12.0D, 10.0D), 
/*  47 */       Block.box(9.0D, 5.0D, 5.0D, 15.0D, 12.0D, 11.0D), 
/*  48 */       Block.box(7.0D, 3.0D, 4.0D, 15.0D, 12.0D, 12.0D)
/*     */     };
/*     */   
/*  51 */   protected static final VoxelShape[] WEST_AABB = new VoxelShape[] {
/*  52 */       Block.box(1.0D, 7.0D, 6.0D, 5.0D, 12.0D, 10.0D), 
/*  53 */       Block.box(1.0D, 5.0D, 5.0D, 7.0D, 12.0D, 11.0D), 
/*  54 */       Block.box(1.0D, 3.0D, 4.0D, 9.0D, 12.0D, 12.0D)
/*     */     };
/*     */   
/*  57 */   protected static final VoxelShape[] NORTH_AABB = new VoxelShape[] {
/*  58 */       Block.box(6.0D, 7.0D, 1.0D, 10.0D, 12.0D, 5.0D), 
/*  59 */       Block.box(5.0D, 5.0D, 1.0D, 11.0D, 12.0D, 7.0D), 
/*  60 */       Block.box(4.0D, 3.0D, 1.0D, 12.0D, 12.0D, 9.0D)
/*     */     };
/*     */   
/*  63 */   protected static final VoxelShape[] SOUTH_AABB = new VoxelShape[] {
/*  64 */       Block.box(6.0D, 7.0D, 11.0D, 10.0D, 12.0D, 15.0D), 
/*  65 */       Block.box(5.0D, 5.0D, 9.0D, 11.0D, 12.0D, 15.0D), 
/*  66 */       Block.box(4.0D, 3.0D, 7.0D, 12.0D, 12.0D, 15.0D)
/*     */     };
/*     */   
/*     */   public CocoaBlock(BlockBehaviour.Properties $$0) {
/*  70 */     super($$0);
/*  71 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/*  76 */     return (((Integer)$$0.getValue((Property)AGE)).intValue() < 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  81 */     if ($$1.random.nextInt(5) == 0) {
/*  82 */       int $$4 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/*  83 */       if ($$4 < 2) {
/*  84 */         $$1.setBlock($$2, (BlockState)$$0.setValue((Property)AGE, Integer.valueOf($$4 + 1)), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  91 */     BlockState $$3 = $$1.getBlockState($$2.relative((Direction)$$0.getValue((Property)FACING)));
/*  92 */     return $$3.is(BlockTags.JUNGLE_LOGS);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  97 */     int $$4 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/*  98 */     switch ((Direction)$$0.getValue((Property)FACING))
/*     */     { case SOUTH:
/* 100 */         return SOUTH_AABB[$$4];
/*     */       
/*     */       default:
/* 103 */         return NORTH_AABB[$$4];
/*     */       case WEST:
/* 105 */         return WEST_AABB[$$4];
/*     */       case EAST:
/* 107 */         break; }  return EAST_AABB[$$4];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 114 */     BlockState $$1 = defaultBlockState();
/*     */     
/* 116 */     Level level = $$0.getLevel();
/* 117 */     BlockPos $$3 = $$0.getClickedPos();
/*     */     
/* 119 */     for (Direction $$4 : $$0.getNearestLookingDirections()) {
/* 120 */       if ($$4.getAxis().isHorizontal()) {
/* 121 */         $$1 = (BlockState)$$1.setValue((Property)FACING, (Comparable)$$4);
/* 122 */         if ($$1.canSurvive((LevelReader)level, $$3)) {
/* 123 */           return $$1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 133 */     if ($$1 == $$0.getValue((Property)FACING) && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 134 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 137 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 142 */     return (((Integer)$$2.getValue((Property)AGE)).intValue() < 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 152 */     $$0.setBlock($$2, (BlockState)$$3.setValue((Property)AGE, Integer.valueOf(((Integer)$$3.getValue((Property)AGE)).intValue() + 1)), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 157 */     $$0.add(new Property[] { (Property)FACING, (Property)AGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 162 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CocoaBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */