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
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SnowLayerBlock extends Block {
/*  26 */   public static final MapCodec<SnowLayerBlock> CODEC = simpleCodec(SnowLayerBlock::new);
/*     */   public static final int MAX_HEIGHT = 8;
/*     */   
/*     */   public MapCodec<SnowLayerBlock> codec() {
/*  30 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  34 */   public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
/*     */   
/*  36 */   protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[] {
/*  37 */       Shapes.empty(), 
/*  38 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), 
/*  39 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), 
/*  40 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), 
/*  41 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), 
/*  42 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), 
/*  43 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), 
/*  44 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), 
/*  45 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
/*     */     };
/*     */   
/*     */   public static final int HEIGHT_IMPASSABLE = 5;
/*     */   
/*     */   protected SnowLayerBlock(BlockBehaviour.Properties $$0) {
/*  51 */     super($$0);
/*  52 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)LAYERS, Integer.valueOf(1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/*  57 */     switch ($$3) {
/*     */       case LAND:
/*  59 */         return (((Integer)$$0.getValue((Property)LAYERS)).intValue() < 5);
/*     */       case WATER:
/*  61 */         return false;
/*     */       case AIR:
/*  63 */         return false;
/*     */     } 
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  71 */     return SHAPE_BY_LAYER[((Integer)$$0.getValue((Property)LAYERS)).intValue()];
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  76 */     return SHAPE_BY_LAYER[((Integer)$$0.getValue((Property)LAYERS)).intValue() - 1];
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  81 */     return SHAPE_BY_LAYER[((Integer)$$0.getValue((Property)LAYERS)).intValue()];
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  86 */     return SHAPE_BY_LAYER[((Integer)$$0.getValue((Property)LAYERS)).intValue()];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getShadeBrightness(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  96 */     return (((Integer)$$0.getValue((Property)LAYERS)).intValue() == 8) ? 0.2F : 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 101 */     BlockState $$3 = $$1.getBlockState($$2.below());
/*     */     
/* 103 */     if ($$3.is(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)) {
/* 104 */       return false;
/*     */     }
/* 106 */     if ($$3.is(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)) {
/* 107 */       return true;
/*     */     }
/*     */     
/* 110 */     return (Block.isFaceFull($$3.getCollisionShape((BlockGetter)$$1, $$2.below()), Direction.UP) || ($$3.is(this) && ((Integer)$$3.getValue((Property)LAYERS)).intValue() == 8));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 115 */     if (!$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 116 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/* 118 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 123 */     if ($$1.getBrightness(LightLayer.BLOCK, $$2) > 11) {
/* 124 */       dropResources($$0, (Level)$$1, $$2);
/* 125 */       $$1.removeBlock($$2, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/* 131 */     int $$2 = ((Integer)$$0.getValue((Property)LAYERS)).intValue();
/*     */     
/* 133 */     if ($$1.getItemInHand().is(asItem()) && $$2 < 8) {
/* 134 */       if ($$1.replacingClickedOnBlock()) {
/* 135 */         return ($$1.getClickedFace() == Direction.UP);
/*     */       }
/* 137 */       return true;
/*     */     } 
/*     */     
/* 140 */     return ($$2 == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 146 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos());
/* 147 */     if ($$1.is(this)) {
/* 148 */       int $$2 = ((Integer)$$1.getValue((Property)LAYERS)).intValue();
/* 149 */       return (BlockState)$$1.setValue((Property)LAYERS, Integer.valueOf(Math.min(8, $$2 + 1)));
/*     */     } 
/*     */     
/* 152 */     return super.getStateForPlacement($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 157 */     $$0.add(new Property[] { (Property)LAYERS });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SnowLayerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */