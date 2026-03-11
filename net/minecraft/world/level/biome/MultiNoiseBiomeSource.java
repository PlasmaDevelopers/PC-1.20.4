/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.level.levelgen.NoiseRouterData;
/*     */ 
/*     */ public class MultiNoiseBiomeSource extends BiomeSource {
/*  20 */   private static final MapCodec<Holder<Biome>> ENTRY_CODEC = Biome.CODEC.fieldOf("biome");
/*     */ 
/*     */   
/*  23 */   public static final MapCodec<Climate.ParameterList<Holder<Biome>>> DIRECT_CODEC = Climate.ParameterList.<T>codec((MapCodec)ENTRY_CODEC).fieldOf("biomes");
/*     */   
/*  25 */   private static final MapCodec<Holder<MultiNoiseBiomeSourceParameterList>> PRESET_CODEC = MultiNoiseBiomeSourceParameterList.CODEC.fieldOf("preset").withLifecycle(Lifecycle.stable());
/*     */   
/*     */   public static final Codec<MultiNoiseBiomeSource> CODEC;
/*     */   
/*     */   private final Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> parameters;
/*     */ 
/*     */   
/*     */   static {
/*  33 */     CODEC = Codec.mapEither(DIRECT_CODEC, PRESET_CODEC).xmap(MultiNoiseBiomeSource::new, $$0 -> $$0.parameters).codec();
/*     */   }
/*     */ 
/*     */   
/*     */   private MultiNoiseBiomeSource(Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> $$0) {
/*  38 */     this.parameters = $$0;
/*     */   }
/*     */   
/*     */   public static MultiNoiseBiomeSource createFromList(Climate.ParameterList<Holder<Biome>> $$0) {
/*  42 */     return new MultiNoiseBiomeSource(Either.left($$0));
/*     */   }
/*     */   
/*     */   public static MultiNoiseBiomeSource createFromPreset(Holder<MultiNoiseBiomeSourceParameterList> $$0) {
/*  46 */     return new MultiNoiseBiomeSource(Either.right($$0));
/*     */   }
/*     */   
/*     */   private Climate.ParameterList<Holder<Biome>> parameters() {
/*  50 */     return (Climate.ParameterList<Holder<Biome>>)this.parameters.map($$0 -> $$0, $$0 -> ((MultiNoiseBiomeSourceParameterList)$$0.value()).parameters());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Stream<Holder<Biome>> collectPossibleBiomes() {
/*  58 */     return parameters().values().stream().map(Pair::getSecond);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Codec<? extends BiomeSource> codec() {
/*  63 */     return (Codec)CODEC;
/*     */   }
/*     */   
/*     */   public boolean stable(ResourceKey<MultiNoiseBiomeSourceParameterList> $$0) {
/*  67 */     Optional<Holder<MultiNoiseBiomeSourceParameterList>> $$1 = this.parameters.right();
/*  68 */     return ($$1.isPresent() && ((Holder)$$1.get()).is($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2, Climate.Sampler $$3) {
/*  73 */     return getNoiseBiome($$3.sample($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public Holder<Biome> getNoiseBiome(Climate.TargetPoint $$0) {
/*  78 */     return parameters().findValue($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDebugInfo(List<String> $$0, BlockPos $$1, Climate.Sampler $$2) {
/*  83 */     int $$3 = QuartPos.fromBlock($$1.getX());
/*  84 */     int $$4 = QuartPos.fromBlock($$1.getY());
/*  85 */     int $$5 = QuartPos.fromBlock($$1.getZ());
/*  86 */     Climate.TargetPoint $$6 = $$2.sample($$3, $$4, $$5);
/*     */     
/*  88 */     float $$7 = Climate.unquantizeCoord($$6.continentalness());
/*  89 */     float $$8 = Climate.unquantizeCoord($$6.erosion());
/*  90 */     float $$9 = Climate.unquantizeCoord($$6.temperature());
/*  91 */     float $$10 = Climate.unquantizeCoord($$6.humidity());
/*  92 */     float $$11 = Climate.unquantizeCoord($$6.weirdness());
/*     */     
/*  94 */     double $$12 = NoiseRouterData.peaksAndValleys($$11);
/*     */     
/*  96 */     OverworldBiomeBuilder $$13 = new OverworldBiomeBuilder();
/*  97 */     $$0.add("Biome builder PV: " + 
/*  98 */         OverworldBiomeBuilder.getDebugStringForPeaksAndValleys($$12) + " C: " + $$13
/*  99 */         .getDebugStringForContinentalness($$7) + " E: " + $$13
/* 100 */         .getDebugStringForErosion($$8) + " T: " + $$13
/* 101 */         .getDebugStringForTemperature($$9) + " H: " + $$13
/* 102 */         .getDebugStringForHumidity($$10));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\MultiNoiseBiomeSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */