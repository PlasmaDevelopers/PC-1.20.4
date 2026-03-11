/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.grower.TreeGrower;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class MangrovePropaguleBlock extends SaplingBlock implements SimpleWaterloggedBlock {
/*     */   static {
/*  29 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)TreeGrower.CODEC.fieldOf("tree").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, MangrovePropaguleBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<MangrovePropaguleBlock> CODEC;
/*     */   
/*     */   public MapCodec<MangrovePropaguleBlock> codec() {
/*  36 */     return CODEC;
/*     */   }
/*     */   
/*  39 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
/*     */   
/*     */   public static final int MAX_AGE = 4;
/*  42 */   private static final VoxelShape[] SHAPE_PER_AGE = new VoxelShape[] {
/*  43 */       Block.box(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D), 
/*  44 */       Block.box(7.0D, 10.0D, 7.0D, 9.0D, 16.0D, 9.0D), 
/*  45 */       Block.box(7.0D, 7.0D, 7.0D, 9.0D, 16.0D, 9.0D), 
/*  46 */       Block.box(7.0D, 3.0D, 7.0D, 9.0D, 16.0D, 9.0D), 
/*  47 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D)
/*     */     };
/*     */   
/*  50 */   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*  51 */   public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
/*     */   
/*     */   public MangrovePropaguleBlock(TreeGrower $$0, BlockBehaviour.Properties $$1) {
/*  54 */     super($$0, $$1);
/*  55 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any())
/*  56 */         .setValue((Property)STAGE, Integer.valueOf(0)))
/*  57 */         .setValue((Property)AGE, Integer.valueOf(0)))
/*  58 */         .setValue((Property)WATERLOGGED, Boolean.valueOf(false)))
/*  59 */         .setValue((Property)HANGING, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  65 */     $$0.add(new Property[] { (Property)STAGE }).add(new Property[] { (Property)AGE }).add(new Property[] { (Property)WATERLOGGED }).add(new Property[] { (Property)HANGING });
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  70 */     return (super.mayPlaceOn($$0, $$1, $$2) || $$0.is(Blocks.CLAY));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  76 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*  77 */     boolean $$2 = ($$1.getType() == Fluids.WATER);
/*  78 */     return (BlockState)((BlockState)super.getStateForPlacement($$0).setValue((Property)WATERLOGGED, Boolean.valueOf($$2))).setValue((Property)AGE, Integer.valueOf(4));
/*     */   }
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*     */     VoxelShape $$6;
/*  83 */     Vec3 $$4 = $$0.getOffset($$1, $$2);
/*     */     
/*  85 */     if (!((Boolean)$$0.getValue((Property)HANGING)).booleanValue()) {
/*  86 */       VoxelShape $$5 = SHAPE_PER_AGE[4];
/*     */     } else {
/*  88 */       $$6 = SHAPE_PER_AGE[((Integer)$$0.getValue((Property)AGE)).intValue()];
/*     */     } 
/*  90 */     return $$6.move($$4.x, $$4.y, $$4.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  95 */     if (isHanging($$0)) {
/*  96 */       return $$1.getBlockState($$2.above()).is(Blocks.MANGROVE_LEAVES);
/*     */     }
/*  98 */     return super.canSurvive($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 103 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 104 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 106 */     if ($$1 == Direction.UP && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 107 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/* 109 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 114 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 115 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 117 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 122 */     if (!isHanging($$0)) {
/*     */ 
/*     */       
/* 125 */       if ($$3.nextInt(7) == 0) {
/* 126 */         advanceTree($$1, $$2, $$0, $$3);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     if (!isFullyGrown($$0)) {
/* 132 */       $$1.setBlock($$2, (BlockState)$$0.cycle((Property)AGE), 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 138 */     return (!isHanging($$2) || !isFullyGrown($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 143 */     return isHanging($$3) ? (!isFullyGrown($$3)) : super.isBonemealSuccess($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 148 */     if (isHanging($$3) && !isFullyGrown($$3)) {
/* 149 */       $$0.setBlock($$2, (BlockState)$$3.cycle((Property)AGE), 2);
/*     */     } else {
/* 151 */       super.performBonemeal($$0, $$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isHanging(BlockState $$0) {
/* 156 */     return ((Boolean)$$0.getValue((Property)HANGING)).booleanValue();
/*     */   }
/*     */   
/*     */   private static boolean isFullyGrown(BlockState $$0) {
/* 160 */     return (((Integer)$$0.getValue((Property)AGE)).intValue() == 4);
/*     */   }
/*     */   
/*     */   public static BlockState createNewHangingPropagule() {
/* 164 */     return createNewHangingPropagule(0);
/*     */   }
/*     */   
/*     */   public static BlockState createNewHangingPropagule(int $$0) {
/* 168 */     return (BlockState)((BlockState)Blocks.MANGROVE_PROPAGULE.defaultBlockState()
/* 169 */       .setValue((Property)HANGING, Boolean.valueOf(true)))
/* 170 */       .setValue((Property)AGE, Integer.valueOf($$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MangrovePropaguleBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */