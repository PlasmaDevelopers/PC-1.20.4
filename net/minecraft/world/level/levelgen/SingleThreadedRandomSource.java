/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleThreadedRandomSource
/*    */   implements BitRandomSource
/*    */ {
/*    */   private static final int MODULUS_BITS = 48;
/*    */   private static final long MODULUS_MASK = 281474976710655L;
/*    */   private static final long MULTIPLIER = 25214903917L;
/*    */   private static final long INCREMENT = 11L;
/*    */   private long seed;
/* 15 */   private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);
/*    */   
/*    */   public SingleThreadedRandomSource(long $$0) {
/* 18 */     setSeed($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public RandomSource fork() {
/* 23 */     return new SingleThreadedRandomSource(nextLong());
/*    */   }
/*    */ 
/*    */   
/*    */   public PositionalRandomFactory forkPositional() {
/* 28 */     return new LegacyRandomSource.LegacyPositionalRandomFactory(nextLong());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSeed(long $$0) {
/* 33 */     this.seed = ($$0 ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
/* 34 */     this.gaussianSource.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public int next(int $$0) {
/* 39 */     long $$1 = this.seed * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
/* 40 */     this.seed = $$1;
/* 41 */     return (int)($$1 >> 48 - $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public double nextGaussian() {
/* 46 */     return this.gaussianSource.nextGaussian();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\SingleThreadedRandomSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */