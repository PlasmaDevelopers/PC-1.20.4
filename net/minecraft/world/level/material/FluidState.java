/*     */ package net.minecraft.world.level.material;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateHolder;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public final class FluidState
/*     */   extends StateHolder<Fluid, FluidState> {
/*  26 */   public static final Codec<FluidState> CODEC = codec(BuiltInRegistries.FLUID.byNameCodec(), Fluid::defaultFluidState).stable();
/*     */   public static final int AMOUNT_MAX = 9;
/*     */   public static final int AMOUNT_FULL = 8;
/*     */   
/*     */   public FluidState(Fluid $$0, ImmutableMap<Property<?>, Comparable<?>> $$1, MapCodec<FluidState> $$2) {
/*  31 */     super($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fluid getType() {
/*  38 */     return (Fluid)this.owner;
/*     */   }
/*     */   
/*     */   public boolean isSource() {
/*  42 */     return getType().isSource(this);
/*     */   }
/*     */   
/*     */   public boolean isSourceOfType(Fluid $$0) {
/*  46 */     return (this.owner == $$0 && ((Fluid)this.owner).isSource(this));
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  50 */     return getType().isEmpty();
/*     */   }
/*     */   
/*     */   public float getHeight(BlockGetter $$0, BlockPos $$1) {
/*  54 */     return getType().getHeight(this, $$0, $$1);
/*     */   }
/*     */   
/*     */   public float getOwnHeight() {
/*  58 */     return getType().getOwnHeight(this);
/*     */   }
/*     */   
/*     */   public int getAmount() {
/*  62 */     return getType().getAmount(this);
/*     */   }
/*     */   
/*     */   public boolean shouldRenderBackwardUpFace(BlockGetter $$0, BlockPos $$1) {
/*  66 */     for (int $$2 = -1; $$2 <= 1; $$2++) {
/*  67 */       for (int $$3 = -1; $$3 <= 1; $$3++) {
/*  68 */         BlockPos $$4 = $$1.offset($$2, 0, $$3);
/*  69 */         FluidState $$5 = $$0.getFluidState($$4);
/*  70 */         if (!$$5.getType().isSame(getType()) && !$$0.getBlockState($$4).isSolidRender($$0, $$4)) {
/*  71 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*  75 */     return false;
/*     */   }
/*     */   
/*     */   public void tick(Level $$0, BlockPos $$1) {
/*  79 */     getType().tick($$0, $$1, this);
/*     */   }
/*     */   
/*     */   public void animateTick(Level $$0, BlockPos $$1, RandomSource $$2) {
/*  83 */     getType().animateTick($$0, $$1, this, $$2);
/*     */   }
/*     */   
/*     */   public boolean isRandomlyTicking() {
/*  87 */     return getType().isRandomlyTicking();
/*     */   }
/*     */   
/*     */   public void randomTick(Level $$0, BlockPos $$1, RandomSource $$2) {
/*  91 */     getType().randomTick($$0, $$1, this, $$2);
/*     */   }
/*     */   
/*     */   public Vec3 getFlow(BlockGetter $$0, BlockPos $$1) {
/*  95 */     return getType().getFlow($$0, $$1, this);
/*     */   }
/*     */   
/*     */   public BlockState createLegacyBlock() {
/*  99 */     return getType().createLegacyBlock(this);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ParticleOptions getDripParticle() {
/* 104 */     return getType().getDripParticle();
/*     */   }
/*     */   
/*     */   public boolean is(TagKey<Fluid> $$0) {
/* 108 */     return getType().builtInRegistryHolder().is($$0);
/*     */   }
/*     */   
/*     */   public boolean is(HolderSet<Fluid> $$0) {
/* 112 */     return $$0.contains((Holder)getType().builtInRegistryHolder());
/*     */   }
/*     */   
/*     */   public boolean is(Fluid $$0) {
/* 116 */     return (getType() == $$0);
/*     */   }
/*     */   
/*     */   public float getExplosionResistance() {
/* 120 */     return getType().getExplosionResistance();
/*     */   }
/*     */   
/*     */   public boolean canBeReplacedWith(BlockGetter $$0, BlockPos $$1, Fluid $$2, Direction $$3) {
/* 124 */     return getType().canBeReplacedWith(this, $$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public VoxelShape getShape(BlockGetter $$0, BlockPos $$1) {
/* 128 */     return getType().getShape(this, $$0, $$1);
/*     */   }
/*     */   
/*     */   public Holder<Fluid> holder() {
/* 132 */     return (Holder<Fluid>)((Fluid)this.owner).builtInRegistryHolder();
/*     */   }
/*     */   
/*     */   public Stream<TagKey<Fluid>> getTags() {
/* 136 */     return ((Fluid)this.owner).builtInRegistryHolder().tags();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\material\FluidState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */