/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
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
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class AmethystClusterBlock extends AmethystBlock implements SimpleWaterloggedBlock {
/*     */   static {
/*  25 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("height").forGetter(()), (App)Codec.FLOAT.fieldOf("aabb_offset").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, AmethystClusterBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<AmethystClusterBlock> CODEC;
/*     */ 
/*     */   
/*     */   public MapCodec<AmethystClusterBlock> codec() {
/*  33 */     return CODEC;
/*     */   }
/*     */   
/*  36 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*  37 */   public static final DirectionProperty FACING = BlockStateProperties.FACING;
/*     */   
/*     */   private final float height;
/*     */   
/*     */   private final float aabbOffset;
/*     */   protected final VoxelShape northAabb;
/*     */   protected final VoxelShape southAabb;
/*     */   protected final VoxelShape eastAabb;
/*     */   protected final VoxelShape westAabb;
/*     */   protected final VoxelShape upAabb;
/*     */   protected final VoxelShape downAabb;
/*     */   
/*     */   public AmethystClusterBlock(float $$0, float $$1, BlockBehaviour.Properties $$2) {
/*  50 */     super($$2);
/*  51 */     registerDefaultState((BlockState)((BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)FACING, (Comparable)Direction.UP));
/*     */     
/*  53 */     this.upAabb = Block.box($$1, 0.0D, $$1, (16.0F - $$1), $$0, (16.0F - $$1));
/*  54 */     this.downAabb = Block.box($$1, (16.0F - $$0), $$1, (16.0F - $$1), 16.0D, (16.0F - $$1));
/*  55 */     this.northAabb = Block.box($$1, $$1, (16.0F - $$0), (16.0F - $$1), (16.0F - $$1), 16.0D);
/*  56 */     this.southAabb = Block.box($$1, $$1, 0.0D, (16.0F - $$1), (16.0F - $$1), $$0);
/*  57 */     this.eastAabb = Block.box(0.0D, $$1, $$1, $$0, (16.0F - $$1), (16.0F - $$1));
/*  58 */     this.westAabb = Block.box((16.0F - $$0), $$1, $$1, 16.0D, (16.0F - $$1), (16.0F - $$1));
/*  59 */     this.height = $$0;
/*  60 */     this.aabbOffset = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  65 */     Direction $$4 = (Direction)$$0.getValue((Property)FACING);
/*  66 */     switch ($$4) {
/*     */       case NORTH:
/*  68 */         return this.northAabb;
/*     */       case SOUTH:
/*  70 */         return this.southAabb;
/*     */       case EAST:
/*  72 */         return this.eastAabb;
/*     */       case WEST:
/*  74 */         return this.westAabb;
/*     */       case DOWN:
/*  76 */         return this.downAabb;
/*     */     } 
/*     */     
/*  79 */     return this.upAabb;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  85 */     Direction $$3 = (Direction)$$0.getValue((Property)FACING);
/*  86 */     BlockPos $$4 = $$2.relative($$3.getOpposite());
/*  87 */     return $$1.getBlockState($$4).isFaceSturdy((BlockGetter)$$1, $$4, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  92 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  93 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/*  96 */     if ($$1 == ((Direction)$$0.getValue((Property)FACING)).getOpposite() && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  97 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 100 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 106 */     Level level = $$0.getLevel();
/* 107 */     BlockPos $$2 = $$0.getClickedPos();
/* 108 */     return (BlockState)((BlockState)defaultBlockState()
/* 109 */       .setValue((Property)WATERLOGGED, Boolean.valueOf((level.getFluidState($$2).getType() == Fluids.WATER))))
/* 110 */       .setValue((Property)FACING, (Comparable)$$0.getClickedFace());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 115 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 120 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 125 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 126 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 128 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 133 */     $$0.add(new Property[] { (Property)WATERLOGGED, (Property)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AmethystClusterBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */