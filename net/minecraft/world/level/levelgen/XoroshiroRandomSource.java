/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ public class XoroshiroRandomSource implements RandomSource {
/*     */   private static final float FLOAT_UNIT = 5.9604645E-8F;
/*     */   private static final double DOUBLE_UNIT = 1.1102230246251565E-16D;
/*     */   public static final Codec<XoroshiroRandomSource> CODEC;
/*     */   private Xoroshiro128PlusPlus randomNumberGenerator;
/*     */   
/*     */   static {
/*  15 */     CODEC = Xoroshiro128PlusPlus.CODEC.xmap($$0 -> new XoroshiroRandomSource($$0), $$0 -> $$0.randomNumberGenerator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  21 */   private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);
/*     */   
/*     */   public XoroshiroRandomSource(long $$0) {
/*  24 */     this.randomNumberGenerator = new Xoroshiro128PlusPlus(RandomSupport.upgradeSeedTo128bit($$0));
/*     */   }
/*     */   
/*     */   public XoroshiroRandomSource(RandomSupport.Seed128bit $$0) {
/*  28 */     this.randomNumberGenerator = new Xoroshiro128PlusPlus($$0);
/*     */   }
/*     */   
/*     */   public XoroshiroRandomSource(long $$0, long $$1) {
/*  32 */     this.randomNumberGenerator = new Xoroshiro128PlusPlus($$0, $$1);
/*     */   }
/*     */   
/*     */   private XoroshiroRandomSource(Xoroshiro128PlusPlus $$0) {
/*  36 */     this.randomNumberGenerator = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public RandomSource fork() {
/*  41 */     return new XoroshiroRandomSource(this.randomNumberGenerator.nextLong(), this.randomNumberGenerator.nextLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public PositionalRandomFactory forkPositional() {
/*  46 */     return new XoroshiroPositionalRandomFactory(this.randomNumberGenerator.nextLong(), this.randomNumberGenerator.nextLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeed(long $$0) {
/*  51 */     this.randomNumberGenerator = new Xoroshiro128PlusPlus(RandomSupport.upgradeSeedTo128bit($$0));
/*  52 */     this.gaussianSource.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt() {
/*  57 */     return (int)this.randomNumberGenerator.nextLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt(int $$0) {
/*  62 */     if ($$0 <= 0) {
/*  63 */       throw new IllegalArgumentException("Bound must be positive");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     long $$1 = Integer.toUnsignedLong(nextInt());
/*     */ 
/*     */     
/*  72 */     long $$2 = $$1 * $$0;
/*     */     
/*  74 */     long $$3 = $$2 & 0xFFFFFFFFL;
/*     */ 
/*     */     
/*  77 */     if ($$3 < $$0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  82 */       int $$4 = Integer.remainderUnsigned(($$0 ^ 0xFFFFFFFF) + 1, $$0);
/*  83 */       while ($$3 < $$4) {
/*     */         
/*  85 */         $$1 = Integer.toUnsignedLong(nextInt());
/*  86 */         $$2 = $$1 * $$0;
/*  87 */         $$3 = $$2 & 0xFFFFFFFFL;
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     long $$5 = $$2 >> 32L;
/*     */     
/*  93 */     return (int)$$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() {
/*  98 */     return this.randomNumberGenerator.nextLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nextBoolean() {
/* 103 */     return ((this.randomNumberGenerator.nextLong() & 0x1L) != 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public float nextFloat() {
/* 108 */     return (float)nextBits(24) * 5.9604645E-8F;
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/* 113 */     return nextBits(53) * 1.1102230246251565E-16D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextGaussian() {
/* 118 */     return this.gaussianSource.nextGaussian();
/*     */   }
/*     */ 
/*     */   
/*     */   public void consumeCount(int $$0) {
/* 123 */     for (int $$1 = 0; $$1 < $$0; $$1++) {
/* 124 */       this.randomNumberGenerator.nextLong();
/*     */     }
/*     */   }
/*     */   
/*     */   private long nextBits(int $$0) {
/* 129 */     return this.randomNumberGenerator.nextLong() >>> 64 - $$0;
/*     */   }
/*     */   
/*     */   public static class XoroshiroPositionalRandomFactory implements PositionalRandomFactory {
/*     */     private final long seedLo;
/*     */     private final long seedHi;
/*     */     
/*     */     public XoroshiroPositionalRandomFactory(long $$0, long $$1) {
/* 137 */       this.seedLo = $$0;
/* 138 */       this.seedHi = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public RandomSource at(int $$0, int $$1, int $$2) {
/* 143 */       long $$3 = Mth.getSeed($$0, $$1, $$2);
/* 144 */       long $$4 = $$3 ^ this.seedLo;
/* 145 */       return new XoroshiroRandomSource($$4, this.seedHi);
/*     */     }
/*     */ 
/*     */     
/*     */     public RandomSource fromHashOf(String $$0) {
/* 150 */       RandomSupport.Seed128bit $$1 = RandomSupport.seedFromHashOf($$0);
/* 151 */       return new XoroshiroRandomSource($$1.xor(this.seedLo, this.seedHi));
/*     */     }
/*     */ 
/*     */     
/*     */     @VisibleForTesting
/*     */     public void parityConfigString(StringBuilder $$0) {
/* 157 */       $$0.append("seedLo: ").append(this.seedLo).append(", seedHi: ").append(this.seedHi);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\XoroshiroRandomSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */