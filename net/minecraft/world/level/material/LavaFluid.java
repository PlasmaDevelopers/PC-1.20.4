/*     */ package net.minecraft.world.level.material;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.BaseFireBlock;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LiquidBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LavaFluid
/*     */   extends FlowingFluid
/*     */ {
/*     */   public static final float MIN_LEVEL_CUTOFF = 0.44444445F;
/*     */   
/*     */   public Fluid getFlowing() {
/*  36 */     return Fluids.FLOWING_LAVA;
/*     */   }
/*     */ 
/*     */   
/*     */   public Fluid getSource() {
/*  41 */     return Fluids.LAVA;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getBucket() {
/*  46 */     return Items.LAVA_BUCKET;
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(Level $$0, BlockPos $$1, FluidState $$2, RandomSource $$3) {
/*  51 */     BlockPos $$4 = $$1.above();
/*  52 */     if ($$0.getBlockState($$4).isAir() && !$$0.getBlockState($$4).isSolidRender((BlockGetter)$$0, $$4)) {
/*  53 */       if ($$3.nextInt(100) == 0) {
/*  54 */         double $$5 = $$1.getX() + $$3.nextDouble();
/*     */         
/*  56 */         double $$6 = $$1.getY() + 1.0D;
/*  57 */         double $$7 = $$1.getZ() + $$3.nextDouble();
/*  58 */         $$0.addParticle((ParticleOptions)ParticleTypes.LAVA, $$5, $$6, $$7, 0.0D, 0.0D, 0.0D);
/*  59 */         $$0.playLocalSound($$5, $$6, $$7, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.2F + $$3.nextFloat() * 0.2F, 0.9F + $$3.nextFloat() * 0.15F, false);
/*     */       } 
/*  61 */       if ($$3.nextInt(200) == 0) {
/*  62 */         $$0.playLocalSound($$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2F + $$3.nextFloat() * 0.2F, 0.9F + $$3.nextFloat() * 0.15F, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(Level $$0, BlockPos $$1, FluidState $$2, RandomSource $$3) {
/*  69 */     if (!$$0.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     int $$4 = $$3.nextInt(3);
/*  74 */     if ($$4 > 0) {
/*  75 */       BlockPos $$5 = $$1;
/*     */       
/*  77 */       for (int $$6 = 0; $$6 < $$4; $$6++) {
/*  78 */         $$5 = $$5.offset($$3.nextInt(3) - 1, 1, $$3.nextInt(3) - 1);
/*  79 */         if (!$$0.isLoaded($$5)) {
/*     */           return;
/*     */         }
/*  82 */         BlockState $$7 = $$0.getBlockState($$5);
/*  83 */         if ($$7.isAir()) {
/*  84 */           if (hasFlammableNeighbours((LevelReader)$$0, $$5)) {
/*  85 */             $$0.setBlockAndUpdate($$5, BaseFireBlock.getState((BlockGetter)$$0, $$5));
/*     */             return;
/*     */           } 
/*  88 */         } else if ($$7.blocksMotion()) {
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } else {
/*  93 */       for (int $$8 = 0; $$8 < 3; $$8++) {
/*  94 */         BlockPos $$9 = $$1.offset($$3.nextInt(3) - 1, 0, $$3.nextInt(3) - 1);
/*  95 */         if (!$$0.isLoaded($$9)) {
/*     */           return;
/*     */         }
/*  98 */         if ($$0.isEmptyBlock($$9.above()) && isFlammable((LevelReader)$$0, $$9)) {
/*  99 */           $$0.setBlockAndUpdate($$9.above(), BaseFireBlock.getState((BlockGetter)$$0, $$9));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasFlammableNeighbours(LevelReader $$0, BlockPos $$1) {
/* 106 */     for (Direction $$2 : Direction.values()) {
/* 107 */       if (isFlammable($$0, $$1.relative($$2))) {
/* 108 */         return true;
/*     */       }
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isFlammable(LevelReader $$0, BlockPos $$1) {
/* 115 */     if ($$1.getY() >= $$0.getMinBuildHeight() && $$1.getY() < $$0.getMaxBuildHeight() && !$$0.hasChunkAt($$1)) {
/* 116 */       return false;
/*     */     }
/* 118 */     return $$0.getBlockState($$1).ignitedByLava();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ParticleOptions getDripParticle() {
/* 124 */     return (ParticleOptions)ParticleTypes.DRIPPING_LAVA;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void beforeDestroyingBlock(LevelAccessor $$0, BlockPos $$1, BlockState $$2) {
/* 129 */     fizz($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlopeFindDistance(LevelReader $$0) {
/* 134 */     return $$0.dimensionType().ultraWarm() ? 4 : 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState createLegacyBlock(FluidState $$0) {
/* 139 */     return (BlockState)Blocks.LAVA.defaultBlockState().setValue((Property)LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSame(Fluid $$0) {
/* 144 */     return ($$0 == Fluids.LAVA || $$0 == Fluids.FLOWING_LAVA);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDropOff(LevelReader $$0) {
/* 149 */     return $$0.dimensionType().ultraWarm() ? 1 : 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplacedWith(FluidState $$0, BlockGetter $$1, BlockPos $$2, Fluid $$3, Direction $$4) {
/* 154 */     return ($$0.getHeight($$1, $$2) >= 0.44444445F && $$3.is(FluidTags.WATER));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTickDelay(LevelReader $$0) {
/* 159 */     return $$0.dimensionType().ultraWarm() ? 10 : 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpreadDelay(Level $$0, BlockPos $$1, FluidState $$2, FluidState $$3) {
/* 164 */     int $$4 = getTickDelay((LevelReader)$$0);
/*     */     
/* 166 */     if (!$$2.isEmpty() && !$$3.isEmpty() && !((Boolean)$$2.getValue((Property)FALLING)).booleanValue() && !((Boolean)$$3.getValue((Property)FALLING)).booleanValue() && $$3.getHeight((BlockGetter)$$0, $$1) > $$2.getHeight((BlockGetter)$$0, $$1) && $$0.getRandom().nextInt(4) != 0) {
/* 167 */       $$4 *= 4;
/*     */     }
/* 169 */     return $$4;
/*     */   }
/*     */   
/*     */   private void fizz(LevelAccessor $$0, BlockPos $$1) {
/* 173 */     $$0.levelEvent(1501, $$1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canConvertToSource(Level $$0) {
/* 178 */     return $$0.getGameRules().getBoolean(GameRules.RULE_LAVA_SOURCE_CONVERSION);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void spreadTo(LevelAccessor $$0, BlockPos $$1, BlockState $$2, Direction $$3, FluidState $$4) {
/* 183 */     if ($$3 == Direction.DOWN) {
/* 184 */       FluidState $$5 = $$0.getFluidState($$1);
/* 185 */       if (is(FluidTags.LAVA) && $$5.is(FluidTags.WATER)) {
/* 186 */         if ($$2.getBlock() instanceof LiquidBlock) {
/* 187 */           $$0.setBlock($$1, Blocks.STONE.defaultBlockState(), 3);
/*     */         }
/* 189 */         fizz($$0, $$1);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 194 */     super.spreadTo($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isRandomlyTicking() {
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getExplosionResistance() {
/* 204 */     return 100.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<SoundEvent> getPickupSound() {
/* 209 */     return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
/*     */   }
/*     */   
/*     */   public static class Source
/*     */     extends LavaFluid {
/*     */     public int getAmount(FluidState $$0) {
/* 215 */       return 8;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSource(FluidState $$0) {
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Flowing
/*     */     extends LavaFluid {
/*     */     protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> $$0) {
/* 227 */       super.createFluidStateDefinition($$0);
/* 228 */       $$0.add(new Property[] { (Property)LEVEL });
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAmount(FluidState $$0) {
/* 233 */       return ((Integer)$$0.getValue((Property)LEVEL)).intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSource(FluidState $$0) {
/* 238 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\material\LavaFluid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */