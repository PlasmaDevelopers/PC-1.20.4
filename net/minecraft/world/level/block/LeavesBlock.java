/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.OptionalInt;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.ParticleUtils;
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
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class LeavesBlock extends Block implements SimpleWaterloggedBlock {
/*  28 */   public static final MapCodec<LeavesBlock> CODEC = simpleCodec(LeavesBlock::new);
/*     */   public static final int DECAY_DISTANCE = 7;
/*     */   
/*     */   public MapCodec<? extends LeavesBlock> codec() {
/*  32 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;
/*  38 */   public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
/*  39 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*     */   private static final int TICK_DELAY = 1;
/*     */   
/*     */   public LeavesBlock(BlockBehaviour.Properties $$0) {
/*  44 */     super($$0);
/*  45 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)DISTANCE, Integer.valueOf(7))).setValue((Property)PERSISTENT, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  50 */     return Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/*  55 */     return (((Integer)$$0.getValue((Property)DISTANCE)).intValue() == 7 && !((Boolean)$$0.getValue((Property)PERSISTENT)).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  60 */     if (decaying($$0)) {
/*  61 */       dropResources($$0, (Level)$$1, $$2);
/*  62 */       $$1.removeBlock($$2, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean decaying(BlockState $$0) {
/*  67 */     return (!((Boolean)$$0.getValue((Property)PERSISTENT)).booleanValue() && ((Integer)$$0.getValue((Property)DISTANCE)).intValue() == 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  72 */     $$1.setBlock($$2, updateDistance($$0, (LevelAccessor)$$1, $$2), 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightBlock(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  77 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  82 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  83 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*  85 */     int $$6 = getDistanceAt($$2) + 1;
/*  86 */     if ($$6 != 1 || ((Integer)$$0.getValue((Property)DISTANCE)).intValue() != $$6) {
/*  87 */       $$3.scheduleTick($$4, this, 1);
/*     */     }
/*  89 */     return $$0;
/*     */   }
/*     */   
/*     */   private static BlockState updateDistance(BlockState $$0, LevelAccessor $$1, BlockPos $$2) {
/*  93 */     int $$3 = 7;
/*  94 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/*  95 */     for (Direction $$5 : Direction.values()) {
/*  96 */       $$4.setWithOffset((Vec3i)$$2, $$5);
/*  97 */       $$3 = Math.min($$3, getDistanceAt($$1.getBlockState((BlockPos)$$4)) + 1);
/*  98 */       if ($$3 == 1) {
/*     */         break;
/*     */       }
/*     */     } 
/* 102 */     return (BlockState)$$0.setValue((Property)DISTANCE, Integer.valueOf($$3));
/*     */   }
/*     */   
/*     */   private static int getDistanceAt(BlockState $$0) {
/* 106 */     return getOptionalDistanceAt($$0).orElse(7);
/*     */   }
/*     */   
/*     */   public static OptionalInt getOptionalDistanceAt(BlockState $$0) {
/* 110 */     if ($$0.is(BlockTags.LOGS)) {
/* 111 */       return OptionalInt.of(0);
/*     */     }
/* 113 */     if ($$0.hasProperty((Property)DISTANCE)) {
/* 114 */       return OptionalInt.of(((Integer)$$0.getValue((Property)DISTANCE)).intValue());
/*     */     }
/* 116 */     return OptionalInt.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 121 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 122 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 124 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 129 */     if (!$$1.isRainingAt($$2.above())) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     if ($$3.nextInt(15) != 1) {
/*     */       return;
/*     */     }
/*     */     
/* 137 */     BlockPos $$4 = $$2.below();
/* 138 */     BlockState $$5 = $$1.getBlockState($$4);
/* 139 */     if ($$5.canOcclude() && $$5.isFaceSturdy((BlockGetter)$$1, $$4, Direction.UP)) {
/*     */       return;
/*     */     }
/*     */     
/* 143 */     ParticleUtils.spawnParticleBelow($$1, $$2, $$3, (ParticleOptions)ParticleTypes.DRIPPING_WATER);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 148 */     $$0.add(new Property[] { (Property)DISTANCE, (Property)PERSISTENT, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 153 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/* 154 */     BlockState $$2 = (BlockState)((BlockState)defaultBlockState().setValue((Property)PERSISTENT, Boolean.valueOf(true))).setValue((Property)WATERLOGGED, Boolean.valueOf(($$1.getType() == Fluids.WATER)));
/* 155 */     return updateDistance($$2, (LevelAccessor)$$0.getLevel(), $$0.getClickedPos());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LeavesBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */