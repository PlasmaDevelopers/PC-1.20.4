/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.SignalGetter;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import net.minecraft.world.ticks.TickPriority;
/*     */ 
/*     */ public abstract class DiodeBlock extends HorizontalDirectionalBlock {
/*  25 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
/*     */   
/*  27 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*     */   
/*     */   protected DiodeBlock(BlockBehaviour.Properties $$0) {
/*  30 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract MapCodec<? extends DiodeBlock> codec();
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  38 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  43 */     BlockPos $$3 = $$2.below();
/*  44 */     return canSurviveOn($$1, $$3, $$1.getBlockState($$3));
/*     */   }
/*     */   
/*     */   protected boolean canSurviveOn(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/*  48 */     return $$2.isFaceSturdy((BlockGetter)$$0, $$1, Direction.UP, SupportType.RIGID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  53 */     if (isLocked((LevelReader)$$1, $$2, $$0)) {
/*     */       return;
/*     */     }
/*     */     
/*  57 */     boolean $$4 = ((Boolean)$$0.getValue((Property)POWERED)).booleanValue();
/*  58 */     boolean $$5 = shouldTurnOn((Level)$$1, $$2, $$0);
/*  59 */     if ($$4 && !$$5) {
/*  60 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(false)), 2);
/*  61 */     } else if (!$$4) {
/*     */ 
/*     */       
/*  64 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(true)), 2);
/*  65 */       if (!$$5) {
/*  66 */         $$1.scheduleTick($$2, this, getDelay($$0), TickPriority.VERY_HIGH);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  73 */     return $$0.getSignal($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  78 */     if (!((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*  79 */       return 0;
/*     */     }
/*     */     
/*  82 */     if ($$0.getValue((Property)FACING) == $$3) {
/*  83 */       return getOutputSignal($$1, $$2, $$0);
/*     */     }
/*     */     
/*  86 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  91 */     if ($$0.canSurvive((LevelReader)$$1, $$2)) {
/*  92 */       checkTickOnNeighbor($$1, $$2, $$0);
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     BlockEntity $$6 = $$0.hasBlockEntity() ? $$1.getBlockEntity($$2) : null;
/*  97 */     dropResources($$0, (LevelAccessor)$$1, $$2, $$6);
/*  98 */     $$1.removeBlock($$2, false);
/*  99 */     for (Direction $$7 : Direction.values()) {
/* 100 */       $$1.updateNeighborsAt($$2.relative($$7), this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void checkTickOnNeighbor(Level $$0, BlockPos $$1, BlockState $$2) {
/* 105 */     if (isLocked((LevelReader)$$0, $$1, $$2)) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     boolean $$3 = ((Boolean)$$2.getValue((Property)POWERED)).booleanValue();
/* 110 */     boolean $$4 = shouldTurnOn($$0, $$1, $$2);
/* 111 */     if ($$3 != $$4 && !$$0.getBlockTicks().willTickThisTick($$1, this)) {
/* 112 */       TickPriority $$5 = TickPriority.HIGH;
/*     */ 
/*     */       
/* 115 */       if (shouldPrioritize((BlockGetter)$$0, $$1, $$2)) {
/* 116 */         $$5 = TickPriority.EXTREMELY_HIGH;
/* 117 */       } else if ($$3) {
/* 118 */         $$5 = TickPriority.VERY_HIGH;
/*     */       } 
/*     */       
/* 121 */       $$0.scheduleTick($$1, this, getDelay($$2), $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isLocked(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean shouldTurnOn(Level $$0, BlockPos $$1, BlockState $$2) {
/* 130 */     return (getInputSignal($$0, $$1, $$2) > 0);
/*     */   }
/*     */   
/*     */   protected int getInputSignal(Level $$0, BlockPos $$1, BlockState $$2) {
/* 134 */     Direction $$3 = (Direction)$$2.getValue((Property)FACING);
/*     */     
/* 136 */     BlockPos $$4 = $$1.relative($$3);
/* 137 */     int $$5 = $$0.getSignal($$4, $$3);
/* 138 */     if ($$5 >= 15) {
/* 139 */       return $$5;
/*     */     }
/*     */     
/* 142 */     BlockState $$6 = $$0.getBlockState($$4);
/* 143 */     return Math.max($$5, $$6.is(Blocks.REDSTONE_WIRE) ? ((Integer)$$6.getValue((Property)RedStoneWireBlock.POWER)).intValue() : 0);
/*     */   }
/*     */   
/*     */   protected int getAlternateSignal(SignalGetter $$0, BlockPos $$1, BlockState $$2) {
/* 147 */     Direction $$3 = (Direction)$$2.getValue((Property)FACING);
/* 148 */     Direction $$4 = $$3.getClockWise();
/* 149 */     Direction $$5 = $$3.getCounterClockWise();
/* 150 */     boolean $$6 = sideInputDiodesOnly();
/* 151 */     return Math.max($$0
/* 152 */         .getControlInputSignal($$1.relative($$4), $$4, $$6), $$0
/* 153 */         .getControlInputSignal($$1.relative($$5), $$5, $$6));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 164 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 169 */     if (shouldTurnOn($$0, $$1, $$2)) {
/* 170 */       $$0.scheduleTick($$1, this, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 176 */     updateNeighborsInFront($$1, $$2, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 181 */     if ($$4 || $$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*     */     
/* 185 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/* 186 */     updateNeighborsInFront($$1, $$2, $$0);
/*     */   }
/*     */   
/*     */   protected void updateNeighborsInFront(Level $$0, BlockPos $$1, BlockState $$2) {
/* 190 */     Direction $$3 = (Direction)$$2.getValue((Property)FACING);
/* 191 */     BlockPos $$4 = $$1.relative($$3.getOpposite());
/*     */     
/* 193 */     $$0.neighborChanged($$4, this, $$1);
/* 194 */     $$0.updateNeighborsAtExceptFromFacing($$4, this, $$3);
/*     */   }
/*     */   
/*     */   protected boolean sideInputDiodesOnly() {
/* 198 */     return false;
/*     */   }
/*     */   
/*     */   protected int getOutputSignal(BlockGetter $$0, BlockPos $$1, BlockState $$2) {
/* 202 */     return 15;
/*     */   }
/*     */   
/*     */   public static boolean isDiode(BlockState $$0) {
/* 206 */     return $$0.getBlock() instanceof DiodeBlock;
/*     */   }
/*     */   
/*     */   public boolean shouldPrioritize(BlockGetter $$0, BlockPos $$1, BlockState $$2) {
/* 210 */     Direction $$3 = ((Direction)$$2.getValue((Property)FACING)).getOpposite();
/* 211 */     BlockState $$4 = $$0.getBlockState($$1.relative($$3));
/*     */     
/* 213 */     return (isDiode($$4) && $$4.getValue((Property)FACING) != $$3);
/*     */   }
/*     */   
/*     */   protected abstract int getDelay(BlockState paramBlockState);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DiodeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */