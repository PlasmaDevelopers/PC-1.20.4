/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import java.util.function.LongFunction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldgenRandom
/*    */   extends LegacyRandomSource
/*    */ {
/*    */   private final RandomSource randomSource;
/*    */   private int count;
/*    */   
/*    */   public WorldgenRandom(RandomSource $$0) {
/* 17 */     super(0L);
/* 18 */     this.randomSource = $$0;
/*    */   }
/*    */   
/*    */   public int getCount() {
/* 22 */     return this.count;
/*    */   }
/*    */ 
/*    */   
/*    */   public RandomSource fork() {
/* 27 */     return this.randomSource.fork();
/*    */   }
/*    */ 
/*    */   
/*    */   public PositionalRandomFactory forkPositional() {
/* 32 */     return this.randomSource.forkPositional();
/*    */   }
/*    */ 
/*    */   
/*    */   public int next(int $$0) {
/* 37 */     this.count++;
/* 38 */     RandomSource randomSource = this.randomSource; if (randomSource instanceof LegacyRandomSource) { LegacyRandomSource $$1 = (LegacyRandomSource)randomSource;
/* 39 */       return $$1.next($$0); }
/*    */     
/* 41 */     return (int)(this.randomSource.nextLong() >>> 64 - $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void setSeed(long $$0) {
/* 47 */     if (this.randomSource == null) {
/*    */       return;
/*    */     }
/* 50 */     this.randomSource.setSeed($$0);
/*    */   }
/*    */   
/*    */   public long setDecorationSeed(long $$0, int $$1, int $$2) {
/* 54 */     setSeed($$0);
/*    */     
/* 56 */     long $$3 = nextLong() | 0x1L;
/* 57 */     long $$4 = nextLong() | 0x1L;
/* 58 */     long $$5 = $$1 * $$3 + $$2 * $$4 ^ $$0;
/* 59 */     setSeed($$5);
/* 60 */     return $$5;
/*    */   }
/*    */   
/*    */   public void setFeatureSeed(long $$0, int $$1, int $$2) {
/* 64 */     long $$3 = $$0 + $$1 + (10000 * $$2);
/* 65 */     setSeed($$3);
/*    */   }
/*    */   
/*    */   public void setLargeFeatureSeed(long $$0, int $$1, int $$2) {
/* 69 */     setSeed($$0);
/* 70 */     long $$3 = nextLong();
/* 71 */     long $$4 = nextLong();
/* 72 */     long $$5 = $$1 * $$3 ^ $$2 * $$4 ^ $$0;
/* 73 */     setSeed($$5);
/*    */   }
/*    */   
/*    */   public void setLargeFeatureWithSalt(long $$0, int $$1, int $$2, int $$3) {
/* 77 */     long $$4 = $$1 * 341873128712L + $$2 * 132897987541L + $$0 + $$3;
/* 78 */     setSeed($$4);
/*    */   }
/*    */   
/*    */   public static RandomSource seedSlimeChunk(int $$0, int $$1, long $$2, long $$3) {
/* 82 */     return RandomSource.create($$2 + ($$0 * $$0 * 4987142) + ($$0 * 5947611) + ($$1 * $$1) * 4392871L + ($$1 * 389711) ^ $$3);
/*    */   }
/*    */   
/*    */   public enum Algorithm {
/* 86 */     LEGACY((String)LegacyRandomSource::new),
/* 87 */     XOROSHIRO((String)XoroshiroRandomSource::new);
/*    */     
/*    */     private final LongFunction<RandomSource> constructor;
/*    */ 
/*    */     
/*    */     Algorithm(LongFunction<RandomSource> $$0) {
/* 93 */       this.constructor = $$0;
/*    */     }
/*    */     
/*    */     public RandomSource newInstance(long $$0) {
/* 97 */       return this.constructor.apply($$0);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\WorldgenRandom.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */