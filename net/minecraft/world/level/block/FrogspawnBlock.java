/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.animal.frog.Tadpole;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class FrogspawnBlock extends Block {
/*  27 */   public static final MapCodec<FrogspawnBlock> CODEC = simpleCodec(FrogspawnBlock::new); private static final int MIN_TADPOLES_SPAWN = 2;
/*     */   private static final int MAX_TADPOLES_SPAWN = 5;
/*     */   
/*     */   public MapCodec<FrogspawnBlock> codec() {
/*  31 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_MIN_HATCH_TICK_DELAY = 3600;
/*     */   
/*     */   private static final int DEFAULT_MAX_HATCH_TICK_DELAY = 12000;
/*     */   
/*  39 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);
/*     */   
/*  41 */   private static int minHatchTickDelay = 3600;
/*  42 */   private static int maxHatchTickDelay = 12000;
/*     */   
/*     */   public FrogspawnBlock(BlockBehaviour.Properties $$0) {
/*  45 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  50 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  55 */     return mayPlaceOn((BlockGetter)$$1, $$2.below());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  60 */     $$1.scheduleTick($$2, this, getFrogspawnHatchDelay($$1.getRandom()));
/*     */   }
/*     */   
/*     */   private static int getFrogspawnHatchDelay(RandomSource $$0) {
/*  64 */     return $$0.nextInt(minHatchTickDelay, maxHatchTickDelay);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  69 */     if (!canSurvive($$0, (LevelReader)$$3, $$4)) {
/*  70 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*  72 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  77 */     if (!canSurvive($$0, (LevelReader)$$1, $$2)) {
/*  78 */       destroyBlock((Level)$$1, $$2);
/*     */       return;
/*     */     } 
/*  81 */     hatchFrogspawn($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/*  86 */     if ($$3.getType().equals(EntityType.FALLING_BLOCK)) {
/*  87 */       destroyBlock($$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean mayPlaceOn(BlockGetter $$0, BlockPos $$1) {
/*  92 */     FluidState $$2 = $$0.getFluidState($$1);
/*  93 */     FluidState $$3 = $$0.getFluidState($$1.above());
/*  94 */     return ($$2.getType() == Fluids.WATER && $$3.getType() == Fluids.EMPTY);
/*     */   }
/*     */   
/*     */   private void hatchFrogspawn(ServerLevel $$0, BlockPos $$1, RandomSource $$2) {
/*  98 */     destroyBlock((Level)$$0, $$1);
/*  99 */     $$0.playSound(null, $$1, SoundEvents.FROGSPAWN_HATCH, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 100 */     spawnTadpoles($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void destroyBlock(Level $$0, BlockPos $$1) {
/* 104 */     $$0.destroyBlock($$1, false);
/*     */   }
/*     */   
/*     */   private void spawnTadpoles(ServerLevel $$0, BlockPos $$1, RandomSource $$2) {
/* 108 */     int $$3 = $$2.nextInt(2, 6);
/* 109 */     for (int $$4 = 1; $$4 <= $$3; $$4++) {
/* 110 */       Tadpole $$5 = (Tadpole)EntityType.TADPOLE.create((Level)$$0);
/* 111 */       if ($$5 != null) {
/* 112 */         double $$6 = $$1.getX() + getRandomTadpolePositionOffset($$2);
/* 113 */         double $$7 = $$1.getZ() + getRandomTadpolePositionOffset($$2);
/* 114 */         int $$8 = $$2.nextInt(1, 361);
/* 115 */         $$5.moveTo($$6, $$1.getY() - 0.5D, $$7, $$8, 0.0F);
/* 116 */         $$5.setPersistenceRequired();
/* 117 */         $$0.addFreshEntity((Entity)$$5);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private double getRandomTadpolePositionOffset(RandomSource $$0) {
/* 123 */     double $$1 = (Tadpole.HITBOX_WIDTH / 2.0F);
/* 124 */     return Mth.clamp($$0.nextDouble(), $$1, 1.0D - $$1);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public static void setHatchDelay(int $$0, int $$1) {
/* 129 */     minHatchTickDelay = $$0;
/* 130 */     maxHatchTickDelay = $$1;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public static void setDefaultHatchDelay() {
/* 135 */     minHatchTickDelay = 3600;
/* 136 */     maxHatchTickDelay = 12000;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FrogspawnBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */