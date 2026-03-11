/*     */ package net.minecraft.world.level.block;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public abstract class GrowingPlantHeadBlock extends GrowingPlantBlock implements BonemealableBlock {
/*  19 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_25;
/*     */   
/*     */   public static final int MAX_AGE = 25;
/*     */   private final double growPerTickProbability;
/*     */   
/*     */   protected GrowingPlantHeadBlock(BlockBehaviour.Properties $$0, Direction $$1, VoxelShape $$2, boolean $$3, double $$4) {
/*  25 */     super($$0, $$1, $$2, $$3);
/*  26 */     this.growPerTickProbability = $$4;
/*  27 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract MapCodec<? extends GrowingPlantHeadBlock> codec();
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(LevelAccessor $$0) {
/*  35 */     return (BlockState)defaultBlockState().setValue((Property)AGE, Integer.valueOf($$0.getRandom().nextInt(25)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/*  40 */     return (((Integer)$$0.getValue((Property)AGE)).intValue() < 25);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  45 */     if (((Integer)$$0.getValue((Property)AGE)).intValue() < 25 && $$3.nextDouble() < this.growPerTickProbability) {
/*  46 */       BlockPos $$4 = $$2.relative(this.growthDirection);
/*  47 */       if (canGrowInto($$1.getBlockState($$4))) {
/*  48 */         $$1.setBlockAndUpdate($$4, getGrowIntoState($$0, $$1.random));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected BlockState getGrowIntoState(BlockState $$0, RandomSource $$1) {
/*  54 */     return (BlockState)$$0.cycle((Property)AGE);
/*     */   }
/*     */   
/*     */   public BlockState getMaxAgeState(BlockState $$0) {
/*  58 */     return (BlockState)$$0.setValue((Property)AGE, Integer.valueOf(25));
/*     */   }
/*     */   
/*     */   public boolean isMaxAge(BlockState $$0) {
/*  62 */     return (((Integer)$$0.getValue((Property)AGE)).intValue() == 25);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockState updateBodyAfterConvertedFromHead(BlockState $$0, BlockState $$1) {
/*  69 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  74 */     if ($$1 == this.growthDirection.getOpposite() && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  75 */       $$3.scheduleTick($$4, this, 1);
/*     */     }
/*  77 */     if ($$1 == this.growthDirection && ($$2.is(this) || $$2.is(getBodyBlock())))
/*     */     {
/*  79 */       return updateBodyAfterConvertedFromHead($$0, getBodyBlock().defaultBlockState());
/*     */     }
/*  81 */     if (this.scheduleFluidTicks) {
/*  82 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/*  85 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  90 */     $$0.add(new Property[] { (Property)AGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/*  95 */     return canGrowInto($$0.getBlockState($$1.relative(this.growthDirection)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 105 */     BlockPos $$4 = $$2.relative(this.growthDirection);
/* 106 */     int $$5 = Math.min(((Integer)$$3.getValue((Property)AGE)).intValue() + 1, 25);
/*     */     
/* 108 */     int $$6 = getBlocksToGrowWhenBonemealed($$1);
/* 109 */     for (int $$7 = 0; $$7 < $$6 && 
/* 110 */       canGrowInto($$0.getBlockState($$4)); $$7++) {
/*     */ 
/*     */       
/* 113 */       $$0.setBlockAndUpdate($$4, (BlockState)$$3.setValue((Property)AGE, Integer.valueOf($$5)));
/*     */       
/* 115 */       $$4 = $$4.relative(this.growthDirection);
/* 116 */       $$5 = Math.min($$5 + 1, 25);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract int getBlocksToGrowWhenBonemealed(RandomSource paramRandomSource);
/*     */   
/*     */   protected abstract boolean canGrowInto(BlockState paramBlockState);
/*     */   
/*     */   protected GrowingPlantHeadBlock getHeadBlock() {
/* 126 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\GrowingPlantHeadBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */