/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.synth.BlendedNoise;
/*    */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NoiseWiringHelper
/*    */   implements DensityFunction.Visitor
/*    */ {
/* 48 */   private final Map<DensityFunction, DensityFunction> wrapped = new HashMap<>();
/*    */   
/*    */   private RandomSource newLegacyInstance(long $$0) {
/* 51 */     return new LegacyRandomSource(seed + $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public DensityFunction.NoiseHolder visitNoise(DensityFunction.NoiseHolder $$0) {
/* 56 */     Holder<NormalNoise.NoiseParameters> $$1 = $$0.noiseData();
/* 57 */     if (useLegacyInit) {
/* 58 */       if ($$1.is(Noises.TEMPERATURE)) {
/* 59 */         NormalNoise $$2 = NormalNoise.createLegacyNetherBiome(newLegacyInstance(0L), new NormalNoise.NoiseParameters(-7, 1.0D, new double[] { 1.0D }));
/* 60 */         return new DensityFunction.NoiseHolder($$1, $$2);
/*    */       } 
/* 62 */       if ($$1.is(Noises.VEGETATION)) {
/* 63 */         NormalNoise $$3 = NormalNoise.createLegacyNetherBiome(newLegacyInstance(1L), new NormalNoise.NoiseParameters(-7, 1.0D, new double[] { 1.0D }));
/* 64 */         return new DensityFunction.NoiseHolder($$1, $$3);
/*    */       } 
/* 66 */       if ($$1.is(Noises.SHIFT)) {
/* 67 */         NormalNoise $$4 = NormalNoise.create(RandomState.this.random.fromHashOf(Noises.SHIFT.location()), new NormalNoise.NoiseParameters(0, 0.0D, new double[0]));
/* 68 */         return new DensityFunction.NoiseHolder($$1, $$4);
/*    */       } 
/*    */     } 
/* 71 */     NormalNoise $$5 = RandomState.this.getOrCreateNoise($$1.unwrapKey().orElseThrow());
/* 72 */     return new DensityFunction.NoiseHolder($$1, $$5);
/*    */   }
/*    */   
/*    */   private DensityFunction wrapNew(DensityFunction $$0) {
/* 76 */     if ($$0 instanceof BlendedNoise) { BlendedNoise $$1 = (BlendedNoise)$$0;
/* 77 */       RandomSource $$2 = useLegacyInit ? newLegacyInstance(0L) : RandomState.this.random.fromHashOf(new ResourceLocation("terrain"));
/* 78 */       return (DensityFunction)$$1.withNewRandom($$2); }
/*    */     
/* 80 */     if ($$0 instanceof DensityFunctions.EndIslandDensityFunction) {
/* 81 */       return new DensityFunctions.EndIslandDensityFunction(seed);
/*    */     }
/* 83 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public DensityFunction apply(DensityFunction $$0) {
/* 88 */     return this.wrapped.computeIfAbsent($$0, this::wrapNew);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\RandomState$1NoiseWiringHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */