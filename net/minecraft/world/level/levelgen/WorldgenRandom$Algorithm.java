/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import java.util.function.LongFunction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Algorithm
/*    */ {
/* 86 */   LEGACY(LegacyRandomSource::new),
/* 87 */   XOROSHIRO(XoroshiroRandomSource::new);
/*    */   
/*    */   private final LongFunction<RandomSource> constructor;
/*    */ 
/*    */   
/*    */   Algorithm(LongFunction<RandomSource> $$0) {
/* 93 */     this.constructor = $$0;
/*    */   }
/*    */   
/*    */   public RandomSource newInstance(long $$0) {
/* 97 */     return this.constructor.apply($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\WorldgenRandom$Algorithm.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */