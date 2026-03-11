/*    */ package net.minecraft.world.level.levelgen.synth;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSortedSet;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*    */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*    */ 
/*    */ public class PerlinSimplexNoise
/*    */ {
/*    */   private final SimplexNoise[] noiseLevels;
/*    */   private final double highestFreqValueFactor;
/*    */   private final double highestFreqInputFactor;
/*    */   
/*    */   public PerlinSimplexNoise(RandomSource $$0, List<Integer> $$1) {
/* 17 */     this($$0, (IntSortedSet)new IntRBTreeSet($$1));
/*    */   }
/*    */   
/*    */   private PerlinSimplexNoise(RandomSource $$0, IntSortedSet $$1) {
/* 21 */     if ($$1.isEmpty()) {
/* 22 */       throw new IllegalArgumentException("Need some octaves!");
/*    */     }
/*    */     
/* 25 */     int $$2 = -$$1.firstInt();
/* 26 */     int $$3 = $$1.lastInt();
/*    */     
/* 28 */     int $$4 = $$2 + $$3 + 1;
/* 29 */     if ($$4 < 1) {
/* 30 */       throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
/*    */     }
/*    */     
/* 33 */     SimplexNoise $$5 = new SimplexNoise($$0);
/* 34 */     int $$6 = $$3;
/*    */     
/* 36 */     this.noiseLevels = new SimplexNoise[$$4];
/* 37 */     if ($$6 >= 0 && $$6 < $$4 && $$1.contains(0)) {
/* 38 */       this.noiseLevels[$$6] = $$5;
/*    */     }
/*    */     
/* 41 */     for (int $$7 = $$6 + 1; $$7 < $$4; $$7++) {
/* 42 */       if ($$7 >= 0 && $$1.contains($$6 - $$7)) {
/* 43 */         this.noiseLevels[$$7] = new SimplexNoise($$0);
/*    */       } else {
/* 45 */         $$0.consumeCount(262);
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     if ($$3 > 0) {
/*    */       
/* 51 */       long $$8 = (long)($$5.getValue($$5.xo, $$5.yo, $$5.zo) * 9.223372036854776E18D);
/* 52 */       WorldgenRandom worldgenRandom = new WorldgenRandom((RandomSource)new LegacyRandomSource($$8));
/* 53 */       for (int $$10 = $$6 - 1; $$10 >= 0; $$10--) {
/* 54 */         if ($$10 < $$4 && $$1.contains($$6 - $$10)) {
/* 55 */           this.noiseLevels[$$10] = new SimplexNoise((RandomSource)worldgenRandom);
/*    */         } else {
/* 57 */           worldgenRandom.consumeCount(262);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     this.highestFreqInputFactor = Math.pow(2.0D, $$3);
/* 63 */     this.highestFreqValueFactor = 1.0D / (Math.pow(2.0D, $$4) - 1.0D);
/*    */   }
/*    */   
/*    */   public double getValue(double $$0, double $$1, boolean $$2) {
/* 67 */     double $$3 = 0.0D;
/* 68 */     double $$4 = this.highestFreqInputFactor;
/* 69 */     double $$5 = this.highestFreqValueFactor;
/*    */     
/* 71 */     for (SimplexNoise $$6 : this.noiseLevels) {
/* 72 */       if ($$6 != null) {
/* 73 */         $$3 += $$6.getValue($$0 * $$4 + ($$2 ? $$6.xo : 0.0D), $$1 * $$4 + ($$2 ? $$6.yo : 0.0D)) * $$5;
/*    */       }
/* 75 */       $$4 /= 2.0D;
/* 76 */       $$5 *= 2.0D;
/*    */     } 
/*    */     
/* 79 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\synth\PerlinSimplexNoise.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */