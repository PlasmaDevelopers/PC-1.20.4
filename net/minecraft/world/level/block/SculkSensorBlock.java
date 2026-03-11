/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.ConstantInt;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SculkSensorBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
/*  45 */   public static final MapCodec<SculkSensorBlock> CODEC = simpleCodec(SculkSensorBlock::new); public static final int ACTIVE_TICKS = 30;
/*     */   public static final int COOLDOWN_TICKS = 10;
/*     */   
/*     */   public MapCodec<? extends SculkSensorBlock> codec() {
/*  49 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final EnumProperty<SculkSensorPhase> PHASE = BlockStateProperties.SCULK_SENSOR_PHASE;
/*  56 */   public static final IntegerProperty POWER = BlockStateProperties.POWER;
/*  57 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*  59 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D); private static final float[] RESONANCE_PITCH_BEND;
/*     */   static {
/*  61 */     RESONANCE_PITCH_BEND = (float[])Util.make(new float[16], $$0 -> {
/*     */           int[] $$1 = { 
/*     */               0, 0, 2, 4, 6, 7, 9, 10, 12, 14, 
/*     */               15, 18, 19, 21, 22, 24 };
/*     */           for (int $$2 = 0; $$2 < 16; $$2++) {
/*     */             $$0[$$2] = NoteBlock.getPitchFromNote($$1[$$2]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SculkSensorBlock(BlockBehaviour.Properties $$0) {
/*  75 */     super($$0);
/*  76 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)PHASE, (Comparable)SculkSensorPhase.INACTIVE)).setValue((Property)POWER, Integer.valueOf(0))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  82 */     BlockPos $$1 = $$0.getClickedPos();
/*  83 */     FluidState $$2 = $$0.getLevel().getFluidState($$1);
/*     */     
/*  85 */     return (BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(($$2.getType() == Fluids.WATER)));
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/*  90 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  91 */       return Fluids.WATER.getSource(false);
/*     */     }
/*  93 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  98 */     if (getPhase($$0) != SculkSensorPhase.ACTIVE) {
/*  99 */       if (getPhase($$0) == SculkSensorPhase.COOLDOWN) {
/* 100 */         $$1.setBlock($$2, (BlockState)$$0.setValue((Property)PHASE, (Comparable)SculkSensorPhase.INACTIVE), 3);
/* 101 */         if (!((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 102 */           $$1.playSound(null, $$2, SoundEvents.SCULK_CLICKING_STOP, SoundSource.BLOCKS, 1.0F, $$1.random.nextFloat() * 0.2F + 0.8F);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 109 */     deactivate((Level)$$1, $$2, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stepOn(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3) {
/* 114 */     if (!$$0.isClientSide() && canActivate($$2) && $$3.getType() != EntityType.WARDEN) {
/* 115 */       BlockEntity $$4 = $$0.getBlockEntity($$1);
/* 116 */       if ($$4 instanceof SculkSensorBlockEntity) { SculkSensorBlockEntity $$5 = (SculkSensorBlockEntity)$$4; if ($$0 instanceof ServerLevel) { ServerLevel $$6 = (ServerLevel)$$0;
/* 117 */           if ($$5.getVibrationUser().canReceiveVibration($$6, $$1, GameEvent.STEP, GameEvent.Context.of($$2)))
/* 118 */             $$5.getListener().forceScheduleVibration($$6, GameEvent.STEP, GameEvent.Context.of($$3), $$3.position());  }
/*     */          }
/*     */     
/*     */     } 
/* 122 */     super.stepOn($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 127 */     if ($$1.isClientSide() || $$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*     */     
/* 131 */     if (((Integer)$$0.getValue((Property)POWER)).intValue() > 0 && !$$1.getBlockTicks().hasScheduledTick($$2, this)) {
/* 132 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWER, Integer.valueOf(0)), 18);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 138 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*     */     
/* 142 */     if (getPhase($$0) == SculkSensorPhase.ACTIVE) {
/* 143 */       updateNeighbours($$1, $$2, $$0);
/*     */     }
/*     */     
/* 146 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 151 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 152 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 154 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private static void updateNeighbours(Level $$0, BlockPos $$1, BlockState $$2) {
/* 158 */     Block $$3 = $$2.getBlock();
/* 159 */     $$0.updateNeighborsAt($$1, $$3);
/* 160 */     $$0.updateNeighborsAt($$1.below(), $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 166 */     return (BlockEntity)new SculkSensorBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 172 */     if (!$$0.isClientSide) {
/* 173 */       return createTickerHelper($$2, BlockEntityType.SCULK_SENSOR, ($$0, $$1, $$2, $$3) -> VibrationSystem.Ticker.tick($$0, $$3.getVibrationData(), $$3.getVibrationUser()));
/*     */     }
/*     */     
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 181 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 186 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 196 */     return ((Integer)$$0.getValue((Property)POWER)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 201 */     if ($$3 == Direction.UP) {
/* 202 */       return $$0.getSignal($$1, $$2, $$3);
/*     */     }
/*     */     
/* 205 */     return 0;
/*     */   }
/*     */   
/*     */   public static SculkSensorPhase getPhase(BlockState $$0) {
/* 209 */     return (SculkSensorPhase)$$0.getValue((Property)PHASE);
/*     */   }
/*     */   
/*     */   public static boolean canActivate(BlockState $$0) {
/* 213 */     return (getPhase($$0) == SculkSensorPhase.INACTIVE);
/*     */   }
/*     */   
/*     */   public static void deactivate(Level $$0, BlockPos $$1, BlockState $$2) {
/* 217 */     $$0.setBlock($$1, (BlockState)((BlockState)$$2.setValue((Property)PHASE, (Comparable)SculkSensorPhase.COOLDOWN)).setValue((Property)POWER, Integer.valueOf(0)), 3);
/* 218 */     $$0.scheduleTick($$1, $$2.getBlock(), 10);
/* 219 */     updateNeighbours($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public int getActiveTicks() {
/* 224 */     return 30;
/*     */   }
/*     */   
/*     */   public void activate(@Nullable Entity $$0, Level $$1, BlockPos $$2, BlockState $$3, int $$4, int $$5) {
/* 228 */     $$1.setBlock($$2, (BlockState)((BlockState)$$3.setValue((Property)PHASE, (Comparable)SculkSensorPhase.ACTIVE)).setValue((Property)POWER, Integer.valueOf($$4)), 3);
/*     */     
/* 230 */     $$1.scheduleTick($$2, $$3.getBlock(), getActiveTicks());
/* 231 */     updateNeighbours($$1, $$2, $$3);
/* 232 */     tryResonateVibration($$0, $$1, $$2, $$5);
/* 233 */     $$1.gameEvent($$0, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, $$2);
/* 234 */     if (!((Boolean)$$3.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 235 */       $$1.playSound(null, $$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, SoundEvents.SCULK_CLICKING, SoundSource.BLOCKS, 1.0F, $$1.random.nextFloat() * 0.2F + 0.8F);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void tryResonateVibration(@Nullable Entity $$0, Level $$1, BlockPos $$2, int $$3) {
/* 240 */     for (Direction $$4 : Direction.values()) {
/* 241 */       BlockPos $$5 = $$2.relative($$4);
/* 242 */       BlockState $$6 = $$1.getBlockState($$5);
/* 243 */       if ($$6.is(BlockTags.VIBRATION_RESONATORS)) {
/* 244 */         $$1.gameEvent(VibrationSystem.getResonanceEventByFrequency($$3), $$5, GameEvent.Context.of($$0, $$6));
/* 245 */         float $$7 = RESONANCE_PITCH_BEND[$$3];
/* 246 */         $$1.playSound(null, $$5, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0F, $$7);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 253 */     if (getPhase($$0) != SculkSensorPhase.ACTIVE) {
/*     */       return;
/*     */     }
/*     */     
/* 257 */     Direction $$4 = Direction.getRandom($$3);
/*     */     
/* 259 */     if ($$4 == Direction.UP || $$4 == Direction.DOWN) {
/*     */       return;
/*     */     }
/*     */     
/* 263 */     double $$5 = $$2.getX() + 0.5D + (($$4.getStepX() == 0) ? (0.5D - $$3.nextDouble()) : ($$4.getStepX() * 0.6D));
/* 264 */     double $$6 = $$2.getY() + 0.25D;
/* 265 */     double $$7 = $$2.getZ() + 0.5D + (($$4.getStepZ() == 0) ? (0.5D - $$3.nextDouble()) : ($$4.getStepZ() * 0.6D));
/* 266 */     double $$8 = $$3.nextFloat() * 0.04D;
/* 267 */     $$1.addParticle((ParticleOptions)DustColorTransitionOptions.SCULK_TO_REDSTONE, $$5, $$6, $$7, 0.0D, $$8, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 272 */     $$0.add(new Property[] { (Property)PHASE, (Property)POWER, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 277 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 282 */     BlockEntity $$3 = $$1.getBlockEntity($$2);
/*     */     
/* 284 */     if ($$3 instanceof SculkSensorBlockEntity) { SculkSensorBlockEntity $$4 = (SculkSensorBlockEntity)$$3;
/* 285 */       return (getPhase($$0) == SculkSensorPhase.ACTIVE) ? $$4.getLastVibrationFrequency() : 0; }
/*     */ 
/*     */     
/* 288 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 293 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnAfterBreak(BlockState $$0, ServerLevel $$1, BlockPos $$2, ItemStack $$3, boolean $$4) {
/* 303 */     super.spawnAfterBreak($$0, $$1, $$2, $$3, $$4);
/* 304 */     if ($$4)
/* 305 */       tryDropExperience($$1, $$2, $$3, (IntProvider)ConstantInt.of(5)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SculkSensorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */