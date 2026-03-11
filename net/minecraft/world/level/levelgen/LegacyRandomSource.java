/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.ThreadingDetector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LegacyRandomSource
/*    */   implements BitRandomSource
/*    */ {
/*    */   private static final int MODULUS_BITS = 48;
/*    */   private static final long MODULUS_MASK = 281474976710655L;
/*    */   private static final long MULTIPLIER = 25214903917L;
/*    */   private static final long INCREMENT = 11L;
/* 19 */   private final AtomicLong seed = new AtomicLong();
/* 20 */   private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);
/*    */   
/*    */   public LegacyRandomSource(long $$0) {
/* 23 */     setSeed($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public RandomSource fork() {
/* 28 */     return new LegacyRandomSource(nextLong());
/*    */   }
/*    */ 
/*    */   
/*    */   public PositionalRandomFactory forkPositional() {
/* 33 */     return new LegacyPositionalRandomFactory(nextLong());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSeed(long $$0) {
/* 38 */     if (!this.seed.compareAndSet(this.seed.get(), ($$0 ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL)) {
/* 39 */       throw ThreadingDetector.makeThreadingException("LegacyRandomSource", null);
/*    */     }
/* 41 */     this.gaussianSource.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public int next(int $$0) {
/* 46 */     long $$1 = this.seed.get();
/* 47 */     long $$2 = $$1 * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
/* 48 */     if (!this.seed.compareAndSet($$1, $$2)) {
/* 49 */       throw ThreadingDetector.makeThreadingException("LegacyRandomSource", null);
/*    */     }
/*    */     
/* 52 */     return (int)($$2 >> 48 - $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public double nextGaussian() {
/* 57 */     return this.gaussianSource.nextGaussian();
/*    */   }
/*    */   
/*    */   public static class LegacyPositionalRandomFactory implements PositionalRandomFactory {
/*    */     private final long seed;
/*    */     
/*    */     public LegacyPositionalRandomFactory(long $$0) {
/* 64 */       this.seed = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public RandomSource at(int $$0, int $$1, int $$2) {
/* 69 */       long $$3 = Mth.getSeed($$0, $$1, $$2);
/* 70 */       long $$4 = $$3 ^ this.seed;
/* 71 */       return new LegacyRandomSource($$4);
/*    */     }
/*    */ 
/*    */     
/*    */     public RandomSource fromHashOf(String $$0) {
/* 76 */       int $$1 = $$0.hashCode();
/* 77 */       return new LegacyRandomSource($$1 ^ this.seed);
/*    */     }
/*    */ 
/*    */     
/*    */     @VisibleForTesting
/*    */     public void parityConfigString(StringBuilder $$0) {
/* 83 */       $$0.append("LegacyPositionalRandomFactory{").append(this.seed).append("}");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\LegacyRandomSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */