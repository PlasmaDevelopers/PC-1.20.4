/*     */ package net.minecraft.world.level.levelgen.synth;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.RegistryFileCodec;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NormalNoise
/*     */ {
/*     */   private static final double INPUT_FACTOR = 1.0181268882175227D;
/*     */   private static final double TARGET_DEVIATION = 0.3333333333333333D;
/*     */   private final double valueFactor;
/*     */   private final PerlinNoise first;
/*     */   private final PerlinNoise second;
/*     */   private final double maxValue;
/*     */   private final NoiseParameters parameters;
/*     */   
/*     */   @Deprecated
/*     */   public static NormalNoise createLegacyNetherBiome(RandomSource $$0, NoiseParameters $$1) {
/*  40 */     return new NormalNoise($$0, $$1, false);
/*     */   }
/*     */   
/*     */   public static NormalNoise create(RandomSource $$0, int $$1, double... $$2) {
/*  44 */     return create($$0, new NoiseParameters($$1, (DoubleList)new DoubleArrayList($$2)));
/*     */   }
/*     */   
/*     */   public static NormalNoise create(RandomSource $$0, NoiseParameters $$1) {
/*  48 */     return new NormalNoise($$0, $$1, true);
/*     */   }
/*     */   
/*     */   private NormalNoise(RandomSource $$0, NoiseParameters $$1, boolean $$2) {
/*  52 */     int $$3 = $$1.firstOctave;
/*  53 */     DoubleList $$4 = $$1.amplitudes;
/*     */     
/*  55 */     this.parameters = $$1;
/*     */     
/*  57 */     if ($$2) {
/*  58 */       this.first = PerlinNoise.create($$0, $$3, $$4);
/*  59 */       this.second = PerlinNoise.create($$0, $$3, $$4);
/*     */     } else {
/*  61 */       this.first = PerlinNoise.createLegacyForLegacyNetherBiome($$0, $$3, $$4);
/*  62 */       this.second = PerlinNoise.createLegacyForLegacyNetherBiome($$0, $$3, $$4);
/*     */     } 
/*     */     
/*  65 */     int $$5 = Integer.MAX_VALUE;
/*  66 */     int $$6 = Integer.MIN_VALUE;
/*     */     
/*  68 */     DoubleListIterator $$7 = $$4.iterator();
/*  69 */     while ($$7.hasNext()) {
/*  70 */       int $$8 = $$7.nextIndex();
/*  71 */       double $$9 = $$7.nextDouble();
/*  72 */       if ($$9 != 0.0D) {
/*  73 */         $$5 = Math.min($$5, $$8);
/*  74 */         $$6 = Math.max($$6, $$8);
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     this.valueFactor = 0.16666666666666666D / expectedDeviation($$6 - $$5);
/*     */     
/*  80 */     this.maxValue = (this.first.maxValue() + this.second.maxValue()) * this.valueFactor;
/*     */   }
/*     */   
/*     */   public double maxValue() {
/*  84 */     return this.maxValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double expectedDeviation(int $$0) {
/*  91 */     return 0.1D * (1.0D + 1.0D / ($$0 + 1));
/*     */   }
/*     */   
/*     */   public double getValue(double $$0, double $$1, double $$2) {
/*  95 */     double $$3 = $$0 * 1.0181268882175227D;
/*  96 */     double $$4 = $$1 * 1.0181268882175227D;
/*  97 */     double $$5 = $$2 * 1.0181268882175227D;
/*  98 */     return (this.first.getValue($$0, $$1, $$2) + this.second.getValue($$3, $$4, $$5)) * this.valueFactor;
/*     */   }
/*     */   
/*     */   public NoiseParameters parameters() {
/* 102 */     return this.parameters;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void parityConfigString(StringBuilder $$0) {
/* 107 */     $$0.append("NormalNoise {");
/* 108 */     $$0.append("first: ");
/* 109 */     this.first.parityConfigString($$0);
/* 110 */     $$0.append(", second: ");
/* 111 */     this.second.parityConfigString($$0);
/* 112 */     $$0.append("}");
/*     */   }
/*     */   public static final class NoiseParameters extends Record { final int firstOctave; final DoubleList amplitudes; public static final Codec<NoiseParameters> DIRECT_CODEC;
/* 115 */     public NoiseParameters(int $$0, DoubleList $$1) { this.firstOctave = $$0; this.amplitudes = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/synth/NormalNoise$NoiseParameters;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #115	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 115 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/synth/NormalNoise$NoiseParameters; } public int firstOctave() { return this.firstOctave; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/synth/NormalNoise$NoiseParameters;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #115	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/synth/NormalNoise$NoiseParameters; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/synth/NormalNoise$NoiseParameters;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #115	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/synth/NormalNoise$NoiseParameters;
/* 115 */       //   0	8	1	$$0	Ljava/lang/Object; } public DoubleList amplitudes() { return this.amplitudes; } static {
/* 116 */       DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("firstOctave").forGetter(NoiseParameters::firstOctave), (App)Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(NoiseParameters::amplitudes)).apply((Applicative)$$0, NoiseParameters::new));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     public static final Codec<Holder<NoiseParameters>> CODEC = (Codec<Holder<NoiseParameters>>)RegistryFileCodec.create(Registries.NOISE, DIRECT_CODEC);
/*     */     
/*     */     public NoiseParameters(int $$0, List<Double> $$1) {
/* 124 */       this($$0, (DoubleList)new DoubleArrayList($$1));
/*     */     }
/*     */     
/*     */     public NoiseParameters(int $$0, double $$1, double... $$2) {
/* 128 */       this($$0, (DoubleList)Util.make(new DoubleArrayList($$2), $$1 -> $$1.add(0, $$0)));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\synth\NormalNoise.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */