/*    */ package net.minecraft.util;
/*    */ 
/*    */ import io.netty.util.internal.ThreadLocalRandom;
/*    */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*    */ import net.minecraft.world.level.levelgen.PositionalRandomFactory;
/*    */ import net.minecraft.world.level.levelgen.RandomSupport;
/*    */ import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
/*    */ import net.minecraft.world.level.levelgen.ThreadSafeLegacyRandomSource;
/*    */ 
/*    */ public interface RandomSource {
/*    */   static RandomSource create() {
/* 12 */     return create(RandomSupport.generateUniqueSeed());
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static final double GAUSSIAN_SPREAD_FACTOR = 2.297D;
/*    */   
/*    */   @Deprecated
/*    */   static RandomSource createThreadSafe() {
/* 20 */     return (RandomSource)new ThreadSafeLegacyRandomSource(RandomSupport.generateUniqueSeed());
/*    */   }
/*    */   
/*    */   static RandomSource create(long $$0) {
/* 24 */     return (RandomSource)new LegacyRandomSource($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static RandomSource createNewThreadLocalInstance() {
/* 32 */     return (RandomSource)new SingleThreadedRandomSource(ThreadLocalRandom.current().nextLong());
/*    */   }
/*    */ 
/*    */   
/*    */   RandomSource fork();
/*    */ 
/*    */   
/*    */   PositionalRandomFactory forkPositional();
/*    */ 
/*    */   
/*    */   void setSeed(long paramLong);
/*    */ 
/*    */   
/*    */   int nextInt();
/*    */ 
/*    */   
/*    */   int nextInt(int paramInt);
/*    */ 
/*    */   
/*    */   default int nextIntBetweenInclusive(int $$0, int $$1) {
/* 52 */     return nextInt($$1 - $$0 + 1) + $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   long nextLong();
/*    */ 
/*    */ 
/*    */   
/*    */   boolean nextBoolean();
/*    */ 
/*    */ 
/*    */   
/*    */   float nextFloat();
/*    */ 
/*    */   
/*    */   double nextDouble();
/*    */ 
/*    */   
/*    */   double nextGaussian();
/*    */ 
/*    */   
/*    */   default double triangle(double $$0, double $$1) {
/* 75 */     return $$0 + $$1 * (nextDouble() - nextDouble());
/*    */   }
/*    */   
/*    */   default void consumeCount(int $$0) {
/* 79 */     for (int $$1 = 0; $$1 < $$0; $$1++) {
/* 80 */       nextInt();
/*    */     }
/*    */   }
/*    */   
/*    */   default int nextInt(int $$0, int $$1) {
/* 85 */     if ($$0 >= $$1) {
/* 86 */       throw new IllegalArgumentException("bound - origin is non positive");
/*    */     }
/* 88 */     return $$0 + nextInt($$1 - $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\RandomSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */