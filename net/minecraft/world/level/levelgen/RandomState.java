/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.biome.Climate;
/*     */ import net.minecraft.world.level.levelgen.synth.BlendedNoise;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*     */ 
/*     */ public final class RandomState
/*     */ {
/*     */   final PositionalRandomFactory random;
/*     */   private final HolderGetter<NormalNoise.NoiseParameters> noises;
/*     */   private final NoiseRouter router;
/*     */   private final Climate.Sampler sampler;
/*     */   private final SurfaceSystem surfaceSystem;
/*     */   private final PositionalRandomFactory aquiferRandom;
/*     */   private final PositionalRandomFactory oreRandom;
/*     */   private final Map<ResourceKey<NormalNoise.NoiseParameters>, NormalNoise> noiseIntances;
/*     */   private final Map<ResourceLocation, PositionalRandomFactory> positionalRandoms;
/*     */   
/*     */   public static RandomState create(HolderGetter.Provider $$0, ResourceKey<NoiseGeneratorSettings> $$1, long $$2) {
/*  29 */     return create((NoiseGeneratorSettings)$$0.lookupOrThrow(Registries.NOISE_SETTINGS).getOrThrow($$1).value(), $$0.lookupOrThrow(Registries.NOISE), $$2);
/*     */   }
/*     */   
/*     */   public static RandomState create(NoiseGeneratorSettings $$0, HolderGetter<NormalNoise.NoiseParameters> $$1, long $$2) {
/*  33 */     return new RandomState($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private RandomState(NoiseGeneratorSettings $$0, HolderGetter<NormalNoise.NoiseParameters> $$1, final long seed) {
/*  37 */     this.random = $$0.getRandomSource().newInstance(seed).forkPositional();
/*  38 */     this.noises = $$1;
/*  39 */     this.aquiferRandom = this.random.fromHashOf(new ResourceLocation("aquifer")).forkPositional();
/*  40 */     this.oreRandom = this.random.fromHashOf(new ResourceLocation("ore")).forkPositional();
/*  41 */     this.noiseIntances = new ConcurrentHashMap<>();
/*  42 */     this.positionalRandoms = new ConcurrentHashMap<>();
/*     */     
/*  44 */     this.surfaceSystem = new SurfaceSystem(this, $$0.defaultBlock(), $$0.seaLevel(), this.random);
/*     */     
/*  46 */     final boolean useLegacyInit = $$0.useLegacyRandomSource();
/*     */     class NoiseWiringHelper implements DensityFunction.Visitor {
/*  48 */       private final Map<DensityFunction, DensityFunction> wrapped = new HashMap<>();
/*     */       
/*     */       private RandomSource newLegacyInstance(long $$0) {
/*  51 */         return new LegacyRandomSource(seed + $$0);
/*     */       }
/*     */ 
/*     */       
/*     */       public DensityFunction.NoiseHolder visitNoise(DensityFunction.NoiseHolder $$0) {
/*  56 */         Holder<NormalNoise.NoiseParameters> $$1 = $$0.noiseData();
/*  57 */         if (useLegacyInit) {
/*  58 */           if ($$1.is(Noises.TEMPERATURE)) {
/*  59 */             NormalNoise $$2 = NormalNoise.createLegacyNetherBiome(newLegacyInstance(0L), new NormalNoise.NoiseParameters(-7, 1.0D, new double[] { 1.0D }));
/*  60 */             return new DensityFunction.NoiseHolder($$1, $$2);
/*     */           } 
/*  62 */           if ($$1.is(Noises.VEGETATION)) {
/*  63 */             NormalNoise $$3 = NormalNoise.createLegacyNetherBiome(newLegacyInstance(1L), new NormalNoise.NoiseParameters(-7, 1.0D, new double[] { 1.0D }));
/*  64 */             return new DensityFunction.NoiseHolder($$1, $$3);
/*     */           } 
/*  66 */           if ($$1.is(Noises.SHIFT)) {
/*  67 */             NormalNoise $$4 = NormalNoise.create(RandomState.this.random.fromHashOf(Noises.SHIFT.location()), new NormalNoise.NoiseParameters(0, 0.0D, new double[0]));
/*  68 */             return new DensityFunction.NoiseHolder($$1, $$4);
/*     */           } 
/*     */         } 
/*  71 */         NormalNoise $$5 = RandomState.this.getOrCreateNoise($$1.unwrapKey().orElseThrow());
/*  72 */         return new DensityFunction.NoiseHolder($$1, $$5);
/*     */       }
/*     */       
/*     */       private DensityFunction wrapNew(DensityFunction $$0) {
/*  76 */         if ($$0 instanceof BlendedNoise) { BlendedNoise $$1 = (BlendedNoise)$$0;
/*  77 */           RandomSource $$2 = useLegacyInit ? newLegacyInstance(0L) : RandomState.this.random.fromHashOf(new ResourceLocation("terrain"));
/*  78 */           return (DensityFunction)$$1.withNewRandom($$2); }
/*     */         
/*  80 */         if ($$0 instanceof DensityFunctions.EndIslandDensityFunction) {
/*  81 */           return new DensityFunctions.EndIslandDensityFunction(seed);
/*     */         }
/*  83 */         return $$0;
/*     */       }
/*     */ 
/*     */       
/*     */       public DensityFunction apply(DensityFunction $$0) {
/*  88 */         return this.wrapped.computeIfAbsent($$0, this::wrapNew);
/*     */       }
/*     */     };
/*     */     
/*  92 */     this.router = $$0.noiseRouter().mapAll(new NoiseWiringHelper());
/*     */     
/*  94 */     DensityFunction.Visitor $$4 = new DensityFunction.Visitor() {
/*  95 */         private final Map<DensityFunction, DensityFunction> wrapped = new HashMap<>();
/*     */         
/*     */         private DensityFunction wrapNew(DensityFunction $$0) {
/*  98 */           if ($$0 instanceof DensityFunctions.HolderHolder) { DensityFunctions.HolderHolder $$1 = (DensityFunctions.HolderHolder)$$0;
/*  99 */             return (DensityFunction)$$1.function().value(); }
/*     */           
/* 101 */           if ($$0 instanceof DensityFunctions.Marker) { DensityFunctions.Marker $$2 = (DensityFunctions.Marker)$$0;
/* 102 */             return $$2.wrapped(); }
/*     */           
/* 104 */           return $$0;
/*     */         }
/*     */ 
/*     */         
/*     */         public DensityFunction apply(DensityFunction $$0) {
/* 109 */           return this.wrapped.computeIfAbsent($$0, this::wrapNew);
/*     */         }
/*     */       };
/*     */     
/* 113 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       .sampler = new Climate.Sampler(this.router.temperature().mapAll($$4), this.router.vegetation().mapAll($$4), this.router.continents().mapAll($$4), this.router.erosion().mapAll($$4), this.router.depth().mapAll($$4), this.router.ridges().mapAll($$4), $$0.spawnTarget());
/*     */   }
/*     */ 
/*     */   
/*     */   public NormalNoise getOrCreateNoise(ResourceKey<NormalNoise.NoiseParameters> $$0) {
/* 125 */     return this.noiseIntances.computeIfAbsent($$0, $$1 -> Noises.instantiate(this.noises, this.random, $$0));
/*     */   }
/*     */   
/*     */   public PositionalRandomFactory getOrCreateRandomFactory(ResourceLocation $$0) {
/* 129 */     return this.positionalRandoms.computeIfAbsent($$0, $$1 -> this.random.fromHashOf($$0).forkPositional());
/*     */   }
/*     */   
/*     */   public NoiseRouter router() {
/* 133 */     return this.router;
/*     */   }
/*     */   
/*     */   public Climate.Sampler sampler() {
/* 137 */     return this.sampler;
/*     */   }
/*     */   
/*     */   public SurfaceSystem surfaceSystem() {
/* 141 */     return this.surfaceSystem;
/*     */   }
/*     */   
/*     */   public PositionalRandomFactory aquiferRandom() {
/* 145 */     return this.aquiferRandom;
/*     */   }
/*     */   
/*     */   public PositionalRandomFactory oreRandom() {
/* 149 */     return this.oreRandom;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\RandomState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */