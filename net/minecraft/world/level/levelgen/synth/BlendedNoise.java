/*     */ package net.minecraft.world.level.levelgen.synth;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Locale;
/*     */ import java.util.stream.IntStream;
/*     */ import net.minecraft.util.KeyDispatchDataCodec;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.levelgen.DensityFunction;
/*     */ import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlendedNoise
/*     */   implements DensityFunction.SimpleFunction
/*     */ {
/*  26 */   private static final Codec<Double> SCALE_RANGE = Codec.doubleRange(0.001D, 1000.0D);
/*     */   static {
/*  28 */     DATA_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)SCALE_RANGE.fieldOf("xz_scale").forGetter(()), (App)SCALE_RANGE.fieldOf("y_scale").forGetter(()), (App)SCALE_RANGE.fieldOf("xz_factor").forGetter(()), (App)SCALE_RANGE.fieldOf("y_factor").forGetter(()), (App)Codec.doubleRange(1.0D, 8.0D).fieldOf("smear_scale_multiplier").forGetter(())).apply((Applicative)$$0, BlendedNoise::createUnseeded));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final MapCodec<BlendedNoise> DATA_CODEC;
/*     */ 
/*     */   
/*  36 */   public static final KeyDispatchDataCodec<BlendedNoise> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);
/*     */   
/*     */   private final PerlinNoise minLimitNoise;
/*     */   
/*     */   private final PerlinNoise maxLimitNoise;
/*     */   
/*     */   private final PerlinNoise mainNoise;
/*     */   
/*     */   private final double xzMultiplier;
/*     */   private final double yMultiplier;
/*     */   private final double xzFactor;
/*     */   private final double yFactor;
/*     */   private final double smearScaleMultiplier;
/*     */   private final double maxValue;
/*     */   private final double xzScale;
/*     */   private final double yScale;
/*     */   
/*     */   public static BlendedNoise createUnseeded(double $$0, double $$1, double $$2, double $$3, double $$4) {
/*  54 */     return new BlendedNoise((RandomSource)new XoroshiroRandomSource(0L), $$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private BlendedNoise(PerlinNoise $$0, PerlinNoise $$1, PerlinNoise $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  58 */     this.minLimitNoise = $$0;
/*  59 */     this.maxLimitNoise = $$1;
/*  60 */     this.mainNoise = $$2;
/*     */     
/*  62 */     this.xzScale = $$3;
/*  63 */     this.yScale = $$4;
/*  64 */     this.xzFactor = $$5;
/*  65 */     this.yFactor = $$6;
/*  66 */     this.smearScaleMultiplier = $$7;
/*     */     
/*  68 */     this.xzMultiplier = 684.412D * this.xzScale;
/*  69 */     this.yMultiplier = 684.412D * this.yScale;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     this.maxValue = $$0.maxBrokenValue(this.yMultiplier);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public BlendedNoise(RandomSource $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/*  79 */     this(
/*  80 */         PerlinNoise.createLegacyForBlendedNoise($$0, IntStream.rangeClosed(-15, 0)), 
/*  81 */         PerlinNoise.createLegacyForBlendedNoise($$0, IntStream.rangeClosed(-15, 0)), 
/*  82 */         PerlinNoise.createLegacyForBlendedNoise($$0, IntStream.rangeClosed(-7, 0)), $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlendedNoise withNewRandom(RandomSource $$0) {
/*  88 */     return new BlendedNoise($$0, this.xzScale, this.yScale, this.xzFactor, this.yFactor, this.smearScaleMultiplier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double compute(DensityFunction.FunctionContext $$0) {
/*  99 */     double $$1 = $$0.blockX() * this.xzMultiplier;
/* 100 */     double $$2 = $$0.blockY() * this.yMultiplier;
/* 101 */     double $$3 = $$0.blockZ() * this.xzMultiplier;
/*     */     
/* 103 */     double $$4 = $$1 / this.xzFactor;
/* 104 */     double $$5 = $$2 / this.yFactor;
/* 105 */     double $$6 = $$3 / this.xzFactor;
/*     */     
/* 107 */     double $$7 = this.yMultiplier * this.smearScaleMultiplier;
/* 108 */     double $$8 = $$7 / this.yFactor;
/*     */     
/* 110 */     double $$9 = 0.0D;
/* 111 */     double $$10 = 0.0D;
/* 112 */     double $$11 = 0.0D;
/*     */     
/* 114 */     boolean $$12 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     double $$13 = 1.0D;
/*     */ 
/*     */     
/* 125 */     for (int $$14 = 0; $$14 < 8; $$14++) {
/* 126 */       ImprovedNoise $$15 = this.mainNoise.getOctaveNoise($$14);
/* 127 */       if ($$15 != null) {
/* 128 */         $$11 += $$15.noise(PerlinNoise.wrap($$4 * $$13), PerlinNoise.wrap($$5 * $$13), PerlinNoise.wrap($$6 * $$13), $$8 * $$13, $$5 * $$13) / $$13;
/*     */       }
/* 130 */       $$13 /= 2.0D;
/*     */     } 
/*     */     
/* 133 */     double $$16 = ($$11 / 10.0D + 1.0D) / 2.0D;
/*     */ 
/*     */ 
/*     */     
/* 137 */     boolean $$17 = ($$16 >= 1.0D);
/* 138 */     boolean $$18 = ($$16 <= 0.0D);
/* 139 */     $$13 = 1.0D;
/* 140 */     for (int $$19 = 0; $$19 < 16; $$19++) {
/* 141 */       double $$20 = PerlinNoise.wrap($$1 * $$13);
/* 142 */       double $$21 = PerlinNoise.wrap($$2 * $$13);
/* 143 */       double $$22 = PerlinNoise.wrap($$3 * $$13);
/* 144 */       double $$23 = $$7 * $$13;
/* 145 */       if (!$$17) {
/* 146 */         ImprovedNoise $$24 = this.minLimitNoise.getOctaveNoise($$19);
/* 147 */         if ($$24 != null) {
/* 148 */           $$9 += $$24.noise($$20, $$21, $$22, $$23, $$2 * $$13) / $$13;
/*     */         }
/*     */       } 
/* 151 */       if (!$$18) {
/* 152 */         ImprovedNoise $$25 = this.maxLimitNoise.getOctaveNoise($$19);
/* 153 */         if ($$25 != null) {
/* 154 */           $$10 += $$25.noise($$20, $$21, $$22, $$23, $$2 * $$13) / $$13;
/*     */         }
/*     */       } 
/* 157 */       $$13 /= 2.0D;
/*     */     } 
/*     */ 
/*     */     
/* 161 */     return Mth.clampedLerp($$9 / 512.0D, $$10 / 512.0D, $$16) / 128.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double minValue() {
/* 166 */     return -maxValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double maxValue() {
/* 171 */     return this.maxValue;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void parityConfigString(StringBuilder $$0) {
/* 176 */     $$0.append("BlendedNoise{minLimitNoise=");
/* 177 */     this.minLimitNoise.parityConfigString($$0);
/* 178 */     $$0.append(", maxLimitNoise=");
/* 179 */     this.maxLimitNoise.parityConfigString($$0);
/* 180 */     $$0.append(", mainNoise=");
/* 181 */     this.mainNoise.parityConfigString($$0);
/*     */     
/* 183 */     $$0.append(
/* 184 */         String.format(Locale.ROOT, ", xzScale=%.3f, yScale=%.3f, xzMainScale=%.3f, yMainScale=%.3f, cellWidth=4, cellHeight=8", new Object[] {
/* 185 */             Double.valueOf(684.412D), Double.valueOf(684.412D), Double.valueOf(8.555150000000001D), Double.valueOf(4.277575000000001D)
/* 186 */           })).append('}');
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/* 191 */     return (KeyDispatchDataCodec)CODEC;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\synth\BlendedNoise.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */