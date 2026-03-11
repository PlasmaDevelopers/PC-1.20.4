/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class ChorusPlantBlock extends PipeBlock {
/*  17 */   public static final MapCodec<ChorusPlantBlock> CODEC = simpleCodec(ChorusPlantBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<ChorusPlantBlock> codec() {
/*  21 */     return CODEC;
/*     */   }
/*     */   
/*     */   protected ChorusPlantBlock(BlockBehaviour.Properties $$0) {
/*  25 */     super(0.3125F, $$0);
/*     */     
/*  27 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)NORTH, Boolean.valueOf(false))).setValue((Property)EAST, Boolean.valueOf(false))).setValue((Property)SOUTH, Boolean.valueOf(false))).setValue((Property)WEST, Boolean.valueOf(false))).setValue((Property)UP, Boolean.valueOf(false))).setValue((Property)DOWN, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  32 */     return getStateWithConnections((BlockGetter)$$0.getLevel(), $$0.getClickedPos(), defaultBlockState());
/*     */   }
/*     */   
/*     */   public static BlockState getStateWithConnections(BlockGetter $$0, BlockPos $$1, BlockState $$2) {
/*  36 */     BlockState $$3 = $$0.getBlockState($$1.below());
/*  37 */     BlockState $$4 = $$0.getBlockState($$1.above());
/*  38 */     BlockState $$5 = $$0.getBlockState($$1.north());
/*  39 */     BlockState $$6 = $$0.getBlockState($$1.east());
/*  40 */     BlockState $$7 = $$0.getBlockState($$1.south());
/*  41 */     BlockState $$8 = $$0.getBlockState($$1.west());
/*     */     
/*  43 */     Block $$9 = $$2.getBlock();
/*  44 */     return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)$$2
/*  45 */       .trySetValue((Property)DOWN, Boolean.valueOf(($$3.is($$9) || $$3.is(Blocks.CHORUS_FLOWER) || $$3.is(Blocks.END_STONE)))))
/*  46 */       .trySetValue((Property)UP, Boolean.valueOf(($$4.is($$9) || $$4.is(Blocks.CHORUS_FLOWER)))))
/*  47 */       .trySetValue((Property)NORTH, Boolean.valueOf(($$5.is($$9) || $$5.is(Blocks.CHORUS_FLOWER)))))
/*  48 */       .trySetValue((Property)EAST, Boolean.valueOf(($$6.is($$9) || $$6.is(Blocks.CHORUS_FLOWER)))))
/*  49 */       .trySetValue((Property)SOUTH, Boolean.valueOf(($$7.is($$9) || $$7.is(Blocks.CHORUS_FLOWER)))))
/*  50 */       .trySetValue((Property)WEST, Boolean.valueOf(($$8.is($$9) || $$8.is(Blocks.CHORUS_FLOWER))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  56 */     if (!$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  57 */       $$3.scheduleTick($$4, this, 1);
/*  58 */       return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     } 
/*     */     
/*  61 */     boolean $$6 = ($$2.is(this) || $$2.is(Blocks.CHORUS_FLOWER) || ($$1 == Direction.DOWN && $$2.is(Blocks.END_STONE)));
/*     */     
/*  63 */     return (BlockState)$$0.setValue((Property)PROPERTY_BY_DIRECTION.get($$1), Boolean.valueOf($$6));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  68 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/*  69 */       $$1.destroyBlock($$2, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  78 */     BlockState $$3 = $$1.getBlockState($$2.below());
/*  79 */     boolean $$4 = (!$$1.getBlockState($$2.above()).isAir() && !$$3.isAir());
/*     */     
/*  81 */     for (Direction $$5 : Direction.Plane.HORIZONTAL) {
/*  82 */       BlockPos $$6 = $$2.relative($$5);
/*  83 */       BlockState $$7 = $$1.getBlockState($$6);
/*  84 */       if ($$7.is(this)) {
/*  85 */         if ($$4) {
/*  86 */           return false;
/*     */         }
/*  88 */         BlockState $$8 = $$1.getBlockState($$6.below());
/*  89 */         if ($$8.is(this) || $$8.is(Blocks.END_STONE)) {
/*  90 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*  94 */     return ($$3.is(this) || $$3.is(Blocks.END_STONE));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  99 */     $$0.add(new Property[] { (Property)NORTH, (Property)EAST, (Property)SOUTH, (Property)WEST, (Property)UP, (Property)DOWN });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 104 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ChorusPlantBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */