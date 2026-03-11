/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.ParticleUtils;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
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
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class LightningRodBlock extends RodBlock implements SimpleWaterloggedBlock {
/*  36 */   public static final MapCodec<LightningRodBlock> CODEC = simpleCodec(LightningRodBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<LightningRodBlock> codec() {
/*  40 */     return CODEC;
/*     */   }
/*     */   
/*  43 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*  44 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*     */   private static final int ACTIVATION_TICKS = 8;
/*     */   public static final int RANGE = 128;
/*     */   private static final int SPARK_CYCLE = 200;
/*     */   
/*     */   public LightningRodBlock(BlockBehaviour.Properties $$0) {
/*  50 */     super($$0);
/*  51 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.UP)).setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)POWERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  56 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*  57 */     boolean $$2 = ($$1.getType() == Fluids.WATER);
/*  58 */     return (BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getClickedFace())).setValue((Property)WATERLOGGED, Boolean.valueOf($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  63 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  64 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*  66 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/*  71 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  72 */       return Fluids.WATER.getSource(false);
/*     */     }
/*  74 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  79 */     return ((Boolean)$$0.getValue((Property)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  84 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue() && $$0.getValue((Property)FACING) == $$3) {
/*  85 */       return 15;
/*     */     }
/*  87 */     return 0;
/*     */   }
/*     */   
/*     */   public void onLightningStrike(BlockState $$0, Level $$1, BlockPos $$2) {
/*  91 */     $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(true)), 3);
/*  92 */     updateNeighbours($$0, $$1, $$2);
/*  93 */     $$1.scheduleTick($$2, this, 8);
/*     */     
/*  95 */     $$1.levelEvent(3002, $$2, ((Direction)$$0.getValue((Property)FACING)).getAxis().ordinal());
/*     */   }
/*     */   
/*     */   private void updateNeighbours(BlockState $$0, Level $$1, BlockPos $$2) {
/*  99 */     $$1.updateNeighborsAt($$2.relative(((Direction)$$0.getValue((Property)FACING)).getOpposite()), this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 104 */     $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(false)), 3);
/* 105 */     updateNeighbours($$0, (Level)$$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 110 */     if (!$$1.isThundering() || $$1.random
/* 111 */       .nextInt(200) > $$1.getGameTime() % 200L || $$2
/* 112 */       .getY() != $$1.getHeight(Heightmap.Types.WORLD_SURFACE, $$2.getX(), $$2.getZ()) - 1) {
/*     */       return;
/*     */     }
/*     */     
/* 116 */     ParticleUtils.spawnParticlesAlongAxis(((Direction)$$0.getValue((Property)FACING)).getAxis(), $$1, $$2, 0.125D, (ParticleOptions)ParticleTypes.ELECTRIC_SPARK, UniformInt.of(1, 2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 121 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 124 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 125 */       updateNeighbours($$0, $$1, $$2);
/*     */     }
/* 127 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 132 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*     */     
/* 136 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue() && !$$1.getBlockTicks().hasScheduledTick($$2, this)) {
/* 137 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(false)), 18);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 143 */     if ($$0.isThundering() && $$3 instanceof ThrownTrident && ((ThrownTrident)$$3).isChanneling()) {
/* 144 */       BlockPos $$4 = $$2.getBlockPos();
/* 145 */       if ($$0.canSeeSky($$4)) {
/* 146 */         LightningBolt $$5 = (LightningBolt)EntityType.LIGHTNING_BOLT.create($$0);
/* 147 */         if ($$5 != null) {
/* 148 */           $$5.moveTo(Vec3.atBottomCenterOf((Vec3i)$$4.above()));
/* 149 */           Entity $$6 = $$3.getOwner();
/* 150 */           $$5.setCause(($$6 instanceof ServerPlayer) ? (ServerPlayer)$$6 : null);
/* 151 */           $$0.addFreshEntity((Entity)$$5);
/*     */         } 
/* 153 */         $$0.playSound(null, $$4, SoundEvents.TRIDENT_THUNDER, SoundSource.WEATHER, 5.0F, 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 160 */     $$0.add(new Property[] { (Property)FACING, (Property)POWERED, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 165 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LightningRodBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */