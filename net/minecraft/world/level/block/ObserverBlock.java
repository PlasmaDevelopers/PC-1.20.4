/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class ObserverBlock extends DirectionalBlock {
/*  19 */   public static final MapCodec<ObserverBlock> CODEC = simpleCodec(ObserverBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<ObserverBlock> codec() {
/*  23 */     return CODEC;
/*     */   }
/*     */   
/*  26 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*     */   
/*     */   public ObserverBlock(BlockBehaviour.Properties $$0) {
/*  29 */     super($$0);
/*     */     
/*  31 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.SOUTH)).setValue((Property)POWERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  36 */     $$0.add(new Property[] { (Property)FACING, (Property)POWERED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  41 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/*  46 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  51 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*  52 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(false)), 2);
/*     */     } else {
/*  54 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(true)), 2);
/*  55 */       $$1.scheduleTick($$2, this, 2);
/*     */     } 
/*  57 */     updateNeighborsInFront((Level)$$1, $$2, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  62 */     if ($$0.getValue((Property)FACING) == $$1 && !((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*  63 */       startSignal($$3, $$4);
/*     */     }
/*     */     
/*  66 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private void startSignal(LevelAccessor $$0, BlockPos $$1) {
/*  70 */     if (!$$0.isClientSide() && !$$0.getBlockTicks().hasScheduledTick($$1, this)) {
/*  71 */       $$0.scheduleTick($$1, this, 2);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void updateNeighborsInFront(Level $$0, BlockPos $$1, BlockState $$2) {
/*  76 */     Direction $$3 = (Direction)$$2.getValue((Property)FACING);
/*  77 */     BlockPos $$4 = $$1.relative($$3.getOpposite());
/*     */     
/*  79 */     $$0.neighborChanged($$4, this, $$1);
/*  80 */     $$0.updateNeighborsAtExceptFromFacing($$4, this, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  90 */     return $$0.getSignal($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  95 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue() && $$0.getValue((Property)FACING) == $$3) {
/*  96 */       return 15;
/*     */     }
/*  98 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 103 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*     */     
/* 107 */     if (!$$1.isClientSide() && ((Boolean)$$0.getValue((Property)POWERED)).booleanValue() && !$$1.getBlockTicks().hasScheduledTick($$2, this)) {
/* 108 */       BlockState $$5 = (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(false));
/*     */       
/* 110 */       $$1.setBlock($$2, $$5, 18);
/* 111 */       updateNeighborsInFront($$1, $$2, $$5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 117 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 120 */     if (!$$1.isClientSide && ((Boolean)$$0.getValue((Property)POWERED)).booleanValue() && $$1.getBlockTicks().hasScheduledTick($$2, this))
/*     */     {
/* 122 */       updateNeighborsInFront($$1, $$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(false)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 128 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getNearestLookingDirection().getOpposite().getOpposite());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ObserverBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */