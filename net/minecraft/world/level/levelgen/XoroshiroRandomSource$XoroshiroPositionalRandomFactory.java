/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.util.Mth;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XoroshiroPositionalRandomFactory
/*     */   implements PositionalRandomFactory
/*     */ {
/*     */   private final long seedLo;
/*     */   private final long seedHi;
/*     */   
/*     */   public XoroshiroPositionalRandomFactory(long $$0, long $$1) {
/* 137 */     this.seedLo = $$0;
/* 138 */     this.seedHi = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public RandomSource at(int $$0, int $$1, int $$2) {
/* 143 */     long $$3 = Mth.getSeed($$0, $$1, $$2);
/* 144 */     long $$4 = $$3 ^ this.seedLo;
/* 145 */     return new XoroshiroRandomSource($$4, this.seedHi);
/*     */   }
/*     */ 
/*     */   
/*     */   public RandomSource fromHashOf(String $$0) {
/* 150 */     RandomSupport.Seed128bit $$1 = RandomSupport.seedFromHashOf($$0);
/* 151 */     return new XoroshiroRandomSource($$1.xor(this.seedLo, this.seedHi));
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   public void parityConfigString(StringBuilder $$0) {
/* 157 */     $$0.append("seedLo: ").append(this.seedLo).append(", seedHi: ").append(this.seedHi);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\XoroshiroRandomSource$XoroshiroPositionalRandomFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */