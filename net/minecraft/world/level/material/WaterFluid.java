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
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LiquidBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public abstract class WaterFluid
/*     */   extends FlowingFluid {
/*     */   public Fluid getFlowing() {
/*  32 */     return Fluids.FLOWING_WATER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Fluid getSource() {
/*  37 */     return Fluids.WATER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getBucket() {
/*  42 */     return Items.WATER_BUCKET;
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(Level $$0, BlockPos $$1, FluidState $$2, RandomSource $$3) {
/*  47 */     if (!$$2.isSource() && !((Boolean)$$2.getValue((Property)FALLING)).booleanValue()) {
/*  48 */       if ($$3.nextInt(64) == 0) {
/*  49 */         $$0.playLocalSound($$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, $$3.nextFloat() * 0.25F + 0.75F, $$3.nextFloat() + 0.5F, false);
/*     */       }
/*  51 */     } else if ($$3.nextInt(10) == 0) {
/*  52 */       $$0.addParticle((ParticleOptions)ParticleTypes.UNDERWATER, $$1.getX() + $$3.nextDouble(), $$1.getY() + $$3.nextDouble(), $$1.getZ() + $$3.nextDouble(), 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ParticleOptions getDripParticle() {
/*  59 */     return (ParticleOptions)ParticleTypes.DRIPPING_WATER;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canConvertToSource(Level $$0) {
/*  64 */     return $$0.getGameRules().getBoolean(GameRules.RULE_WATER_SOURCE_CONVERSION);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void beforeDestroyingBlock(LevelAccessor $$0, BlockPos $$1, BlockState $$2) {
/*  69 */     BlockEntity $$3 = $$2.hasBlockEntity() ? $$0.getBlockEntity($$1) : null;
/*  70 */     Block.dropResources($$2, $$0, $$1, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlopeFindDistance(LevelReader $$0) {
/*  75 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState createLegacyBlock(FluidState $$0) {
/*  80 */     return (BlockState)Blocks.WATER.defaultBlockState().setValue((Property)LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSame(Fluid $$0) {
/*  85 */     return ($$0 == Fluids.WATER || $$0 == Fluids.FLOWING_WATER);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDropOff(LevelReader $$0) {
/*  90 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTickDelay(LevelReader $$0) {
/*  95 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplacedWith(FluidState $$0, BlockGetter $$1, BlockPos $$2, Fluid $$3, Direction $$4) {
/* 100 */     return ($$4 == Direction.DOWN && !$$3.is(FluidTags.WATER));
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getExplosionResistance() {
/* 105 */     return 100.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<SoundEvent> getPickupSound() {
/* 110 */     return Optional.of(SoundEvents.BUCKET_FILL);
/*     */   }
/*     */   
/*     */   public static class Source
/*     */     extends WaterFluid {
/*     */     public int getAmount(FluidState $$0) {
/* 116 */       return 8;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSource(FluidState $$0) {
/* 121 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Flowing
/*     */     extends WaterFluid {
/*     */     protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> $$0) {
/* 128 */       super.createFluidStateDefinition($$0);
/* 129 */       $$0.add(new Property[] { (Property)LEVEL });
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAmount(FluidState $$0) {
/* 134 */       return ((Integer)$$0.getValue((Property)LEVEL)).intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSource(FluidState $$0) {
/* 139 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\material\WaterFluid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */