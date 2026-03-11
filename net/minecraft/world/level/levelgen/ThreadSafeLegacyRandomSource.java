/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ThreadSafeLegacyRandomSource
/*    */   implements BitRandomSource
/*    */ {
/*    */   private static final int MODULUS_BITS = 48;
/*    */   private static final long MODULUS_MASK = 281474976710655L;
/*    */   private static final long MULTIPLIER = 25214903917L;
/*    */   private static final long INCREMENT = 11L;
/* 19 */   private final AtomicLong seed = new AtomicLong();
/* 20 */   private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);
/*    */   
/*    */   public ThreadSafeLegacyRandomSource(long $$0) {
/* 23 */     setSeed($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public RandomSource fork() {
/* 28 */     return new ThreadSafeLegacyRandomSource(nextLong());
/*    */   }
/*    */ 
/*    */   
/*    */   public PositionalRandomFactory forkPositional() {
/* 33 */     return new LegacyRandomSource.LegacyPositionalRandomFactory(nextLong());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSeed(long $$0) {
/* 38 */     this.seed.set(($$0 ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int next(int $$0) {
/*    */     while (true) {
/* 46 */       long $$1 = this.seed.get();
/* 47 */       long $$2 = $$1 * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
/* 48 */       if (this.seed.compareAndSet($$1, $$2))
/* 49 */         return (int)($$2 >>> 48 - $$0); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public double nextGaussian() {
/* 54 */     return this.gaussianSource.nextGaussian();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\ThreadSafeLegacyRandomSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */