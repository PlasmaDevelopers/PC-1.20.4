/*     */ package net.minecraft.world.level.material;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.IdMapper;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public abstract class Fluid
/*     */ {
/*  25 */   public static final IdMapper<FluidState> FLUID_STATE_REGISTRY = new IdMapper();
/*     */   
/*     */   protected final StateDefinition<Fluid, FluidState> stateDefinition;
/*     */   private FluidState defaultFluidState;
/*  29 */   private final Holder.Reference<Fluid> builtInRegistryHolder = BuiltInRegistries.FLUID.createIntrusiveHolder(this);
/*     */   
/*     */   protected Fluid() {
/*  32 */     StateDefinition.Builder<Fluid, FluidState> $$0 = new StateDefinition.Builder(this);
/*  33 */     createFluidStateDefinition($$0);
/*  34 */     this.stateDefinition = $$0.create(Fluid::defaultFluidState, FluidState::new);
/*  35 */     registerDefaultState((FluidState)this.stateDefinition.any());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> $$0) {}
/*     */   
/*     */   public StateDefinition<Fluid, FluidState> getStateDefinition() {
/*  42 */     return this.stateDefinition;
/*     */   }
/*     */   
/*     */   protected final void registerDefaultState(FluidState $$0) {
/*  46 */     this.defaultFluidState = $$0;
/*     */   }
/*     */   
/*     */   public final FluidState defaultFluidState() {
/*  50 */     return this.defaultFluidState;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Item getBucket();
/*     */ 
/*     */   
/*     */   protected void animateTick(Level $$0, BlockPos $$1, FluidState $$2, RandomSource $$3) {}
/*     */ 
/*     */   
/*     */   protected void tick(Level $$0, BlockPos $$1, FluidState $$2) {}
/*     */   
/*     */   protected void randomTick(Level $$0, BlockPos $$1, FluidState $$2, RandomSource $$3) {}
/*     */   
/*     */   @Nullable
/*     */   protected ParticleOptions getDripParticle() {
/*  66 */     return null;
/*     */   }
/*     */   
/*     */   protected abstract boolean canBeReplacedWith(FluidState paramFluidState, BlockGetter paramBlockGetter, BlockPos paramBlockPos, Fluid paramFluid, Direction paramDirection);
/*     */   
/*     */   protected abstract Vec3 getFlow(BlockGetter paramBlockGetter, BlockPos paramBlockPos, FluidState paramFluidState);
/*     */   
/*     */   public abstract int getTickDelay(LevelReader paramLevelReader);
/*     */   
/*     */   protected boolean isRandomlyTicking() {
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isEmpty() {
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract float getExplosionResistance();
/*     */   
/*     */   public abstract float getHeight(FluidState paramFluidState, BlockGetter paramBlockGetter, BlockPos paramBlockPos);
/*     */   
/*     */   public abstract float getOwnHeight(FluidState paramFluidState);
/*     */   
/*     */   protected abstract BlockState createLegacyBlock(FluidState paramFluidState);
/*     */   
/*     */   public abstract boolean isSource(FluidState paramFluidState);
/*     */   
/*     */   public abstract int getAmount(FluidState paramFluidState);
/*     */   
/*     */   public boolean isSame(Fluid $$0) {
/*  96 */     return ($$0 == this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean is(TagKey<Fluid> $$0) {
/* 104 */     return this.builtInRegistryHolder.is($$0);
/*     */   }
/*     */   
/*     */   public abstract VoxelShape getShape(FluidState paramFluidState, BlockGetter paramBlockGetter, BlockPos paramBlockPos);
/*     */   
/*     */   public Optional<SoundEvent> getPickupSound() {
/* 110 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Holder.Reference<Fluid> builtInRegistryHolder() {
/* 118 */     return this.builtInRegistryHolder;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\material\Fluid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */