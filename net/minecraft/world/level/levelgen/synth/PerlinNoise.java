/*     */ package net.minecraft.world.level.levelgen.synth;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*     */ import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSortedSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.levelgen.PositionalRandomFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PerlinNoise
/*     */ {
/*     */   private static final int ROUND_OFF = 33554432;
/*     */   private final ImprovedNoise[] noiseLevels;
/*     */   private final int firstOctave;
/*     */   private final DoubleList amplitudes;
/*     */   private final double lowestFreqValueFactor;
/*     */   private final double lowestFreqInputFactor;
/*     */   private final double maxValue;
/*     */   
/*     */   @Deprecated
/*     */   public static PerlinNoise createLegacyForBlendedNoise(RandomSource $$0, IntStream $$1) {
/*  35 */     return new PerlinNoise($$0, makeAmplitudes((IntSortedSet)new IntRBTreeSet($$1.boxed().collect(ImmutableList.toImmutableList()))), false);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static PerlinNoise createLegacyForLegacyNetherBiome(RandomSource $$0, int $$1, DoubleList $$2) {
/*  40 */     return new PerlinNoise($$0, Pair.of(Integer.valueOf($$1), $$2), false);
/*     */   }
/*     */   
/*     */   public static PerlinNoise create(RandomSource $$0, IntStream $$1) {
/*  44 */     return create($$0, $$1.boxed().collect(ImmutableList.toImmutableList()));
/*     */   }
/*     */   
/*     */   public static PerlinNoise create(RandomSource $$0, List<Integer> $$1) {
/*  48 */     return new PerlinNoise($$0, makeAmplitudes((IntSortedSet)new IntRBTreeSet($$1)), true);
/*     */   }
/*     */   
/*     */   public static PerlinNoise create(RandomSource $$0, int $$1, double $$2, double... $$3) {
/*  52 */     DoubleArrayList $$4 = new DoubleArrayList($$3);
/*  53 */     $$4.add(0, $$2);
/*  54 */     return new PerlinNoise($$0, Pair.of(Integer.valueOf($$1), $$4), true);
/*     */   }
/*     */   
/*     */   public static PerlinNoise create(RandomSource $$0, int $$1, DoubleList $$2) {
/*  58 */     return new PerlinNoise($$0, Pair.of(Integer.valueOf($$1), $$2), true);
/*     */   }
/*     */   
/*     */   private static Pair<Integer, DoubleList> makeAmplitudes(IntSortedSet $$0) {
/*  62 */     if ($$0.isEmpty()) {
/*  63 */       throw new IllegalArgumentException("Need some octaves!");
/*     */     }
/*     */     
/*  66 */     int $$1 = -$$0.firstInt();
/*  67 */     int $$2 = $$0.lastInt();
/*     */     
/*  69 */     int $$3 = $$1 + $$2 + 1;
/*  70 */     if ($$3 < 1) {
/*  71 */       throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
/*     */     }
/*     */     
/*  74 */     DoubleArrayList doubleArrayList = new DoubleArrayList(new double[$$3]);
/*  75 */     IntBidirectionalIterator $$5 = $$0.iterator();
/*  76 */     while ($$5.hasNext()) {
/*  77 */       int $$6 = $$5.nextInt();
/*  78 */       doubleArrayList.set($$6 + $$1, 1.0D);
/*     */     } 
/*     */     
/*  81 */     return Pair.of(Integer.valueOf(-$$1), doubleArrayList);
/*     */   }
/*     */   
/*     */   protected PerlinNoise(RandomSource $$0, Pair<Integer, DoubleList> $$1, boolean $$2) {
/*  85 */     this.firstOctave = ((Integer)$$1.getFirst()).intValue();
/*  86 */     this.amplitudes = (DoubleList)$$1.getSecond();
/*  87 */     int $$3 = this.amplitudes.size();
/*  88 */     int $$4 = -this.firstOctave;
/*     */     
/*  90 */     this.noiseLevels = new ImprovedNoise[$$3];
/*     */     
/*  92 */     if ($$2) {
/*  93 */       PositionalRandomFactory $$5 = $$0.forkPositional();
/*  94 */       for (int $$6 = 0; $$6 < $$3; $$6++) {
/*  95 */         if (this.amplitudes.getDouble($$6) != 0.0D) {
/*  96 */           int $$7 = this.firstOctave + $$6;
/*  97 */           this.noiseLevels[$$6] = new ImprovedNoise($$5.fromHashOf("octave_" + $$7));
/*     */         } 
/*     */       } 
/*     */     } else {
/* 101 */       ImprovedNoise $$8 = new ImprovedNoise($$0);
/* 102 */       if ($$4 >= 0 && $$4 < $$3) {
/* 103 */         double $$9 = this.amplitudes.getDouble($$4);
/* 104 */         if ($$9 != 0.0D) {
/* 105 */           this.noiseLevels[$$4] = $$8;
/*     */         }
/*     */       } 
/*     */       
/* 109 */       for (int $$10 = $$4 - 1; $$10 >= 0; $$10--) {
/* 110 */         if ($$10 < $$3) {
/* 111 */           double $$11 = this.amplitudes.getDouble($$10);
/* 112 */           if ($$11 != 0.0D) {
/* 113 */             this.noiseLevels[$$10] = new ImprovedNoise($$0);
/*     */           } else {
/* 115 */             skipOctave($$0);
/*     */           } 
/*     */         } else {
/* 118 */           skipOctave($$0);
/*     */         } 
/*     */       } 
/*     */       
/* 122 */       if (Arrays.<ImprovedNoise>stream(this.noiseLevels).filter(Objects::nonNull).count() != this.amplitudes.stream().filter($$0 -> ($$0.doubleValue() != 0.0D)).count()) {
/* 123 */         throw new IllegalStateException("Failed to create correct number of noise levels for given non-zero amplitudes");
/*     */       }
/*     */       
/* 126 */       if ($$4 < $$3 - 1)
/*     */       {
/* 128 */         throw new IllegalArgumentException("Positive octaves are temporarily disabled");
/*     */       }
/*     */     } 
/*     */     
/* 132 */     this.lowestFreqInputFactor = Math.pow(2.0D, -$$4);
/* 133 */     this.lowestFreqValueFactor = Math.pow(2.0D, ($$3 - 1)) / (Math.pow(2.0D, $$3) - 1.0D);
/*     */ 
/*     */     
/* 136 */     this.maxValue = edgeValue(2.0D);
/*     */   }
/*     */   
/*     */   protected double maxValue() {
/* 140 */     return this.maxValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void skipOctave(RandomSource $$0) {
/* 146 */     $$0.consumeCount(262);
/*     */   }
/*     */   
/*     */   public double getValue(double $$0, double $$1, double $$2) {
/* 150 */     return getValue($$0, $$1, $$2, 0.0D, 0.0D, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double getValue(double $$0, double $$1, double $$2, double $$3, double $$4, boolean $$5) {
/* 158 */     double $$6 = 0.0D;
/* 159 */     double $$7 = this.lowestFreqInputFactor;
/* 160 */     double $$8 = this.lowestFreqValueFactor;
/*     */     
/* 162 */     for (int $$9 = 0; $$9 < this.noiseLevels.length; $$9++) {
/* 163 */       ImprovedNoise $$10 = this.noiseLevels[$$9];
/* 164 */       if ($$10 != null) {
/* 165 */         double $$11 = $$10.noise(wrap($$0 * $$7), $$5 ? -$$10.yo : wrap($$1 * $$7), wrap($$2 * $$7), $$3 * $$7, $$4 * $$7);
/* 166 */         $$6 += this.amplitudes.getDouble($$9) * $$11 * $$8;
/*     */       } 
/* 168 */       $$7 *= 2.0D;
/* 169 */       $$8 /= 2.0D;
/*     */     } 
/*     */     
/* 172 */     return $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public double maxBrokenValue(double $$0) {
/* 177 */     return edgeValue($$0 + 2.0D);
/*     */   }
/*     */   
/*     */   private double edgeValue(double $$0) {
/* 181 */     double $$1 = 0.0D;
/* 182 */     double $$2 = this.lowestFreqValueFactor;
/*     */     
/* 184 */     for (int $$3 = 0; $$3 < this.noiseLevels.length; $$3++) {
/* 185 */       ImprovedNoise $$4 = this.noiseLevels[$$3];
/* 186 */       if ($$4 != null) {
/* 187 */         $$1 += this.amplitudes.getDouble($$3) * $$0 * $$2;
/*     */       }
/* 189 */       $$2 /= 2.0D;
/*     */     } 
/*     */     
/* 192 */     return $$1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ImprovedNoise getOctaveNoise(int $$0) {
/* 197 */     return this.noiseLevels[this.noiseLevels.length - 1 - $$0];
/*     */   }
/*     */   
/*     */   public static double wrap(double $$0) {
/* 201 */     return $$0 - Mth.lfloor($$0 / 3.3554432E7D + 0.5D) * 3.3554432E7D;
/*     */   }
/*     */   
/*     */   protected int firstOctave() {
/* 205 */     return this.firstOctave;
/*     */   }
/*     */   
/*     */   protected DoubleList amplitudes() {
/* 209 */     return this.amplitudes;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void parityConfigString(StringBuilder $$0) {
/* 214 */     $$0.append("PerlinNoise{");
/* 215 */     List<String> $$1 = this.amplitudes.stream().map($$0 -> String.format(Locale.ROOT, "%.2f", new Object[] { $$0 })).toList();
/* 216 */     $$0.append("first octave: ").append(this.firstOctave).append(", amplitudes: ").append($$1)
/* 217 */       .append(", noise levels: [");
/*     */     
/* 219 */     for (int $$2 = 0; $$2 < this.noiseLevels.length; $$2++) {
/* 220 */       $$0.append($$2).append(": ");
/* 221 */       ImprovedNoise $$3 = this.noiseLevels[$$2];
/* 222 */       if ($$3 == null) {
/* 223 */         $$0.append("null");
/*     */       } else {
/* 225 */         $$3.parityConfigString($$0);
/*     */       } 
/* 227 */       $$0.append(", ");
/*     */     } 
/*     */     
/* 230 */     $$0.append("]");
/* 231 */     $$0.append("}");
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\synth\PerlinNoise.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */