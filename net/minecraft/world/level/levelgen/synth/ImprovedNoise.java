/*     */ package net.minecraft.world.level.levelgen.synth;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ 
/*     */ public final class ImprovedNoise
/*     */ {
/*     */   private static final float SHIFT_UP_EPSILON = 1.0E-7F;
/*     */   private final byte[] p;
/*     */   public final double xo;
/*     */   public final double yo;
/*     */   public final double zo;
/*     */   
/*     */   public ImprovedNoise(RandomSource $$0) {
/*  17 */     this.xo = $$0.nextDouble() * 256.0D;
/*  18 */     this.yo = $$0.nextDouble() * 256.0D;
/*  19 */     this.zo = $$0.nextDouble() * 256.0D;
/*     */     
/*  21 */     this.p = new byte[256];
/*     */     
/*  23 */     for (int $$1 = 0; $$1 < 256; $$1++) {
/*  24 */       this.p[$$1] = (byte)$$1;
/*     */     }
/*     */     
/*  27 */     for (int $$2 = 0; $$2 < 256; $$2++) {
/*  28 */       int $$3 = $$0.nextInt(256 - $$2);
/*  29 */       byte $$4 = this.p[$$2];
/*  30 */       this.p[$$2] = this.p[$$2 + $$3];
/*  31 */       this.p[$$2 + $$3] = $$4;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double noise(double $$0, double $$1, double $$2) {
/*  37 */     return noise($$0, $$1, $$2, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double noise(double $$0, double $$1, double $$2, double $$3, double $$4) {
/*  45 */     double $$17, $$5 = $$0 + this.xo;
/*  46 */     double $$6 = $$1 + this.yo;
/*  47 */     double $$7 = $$2 + this.zo;
/*     */     
/*  49 */     int $$8 = Mth.floor($$5);
/*  50 */     int $$9 = Mth.floor($$6);
/*  51 */     int $$10 = Mth.floor($$7);
/*     */ 
/*     */     
/*  54 */     double $$11 = $$5 - $$8;
/*  55 */     double $$12 = $$6 - $$9;
/*  56 */     double $$13 = $$7 - $$10;
/*     */ 
/*     */ 
/*     */     
/*  60 */     if ($$3 != 0.0D) {
/*     */       double $$15;
/*     */       
/*  63 */       if ($$4 >= 0.0D && $$4 < $$12) {
/*  64 */         double $$14 = $$4;
/*     */       } else {
/*  66 */         $$15 = $$12;
/*     */       } 
/*     */       
/*  69 */       double $$16 = Mth.floor($$15 / $$3 + 1.0000000116860974E-7D) * $$3;
/*     */     } else {
/*  71 */       $$17 = 0.0D;
/*     */     } 
/*     */ 
/*     */     
/*  75 */     return sampleAndLerp($$8, $$9, $$10, $$11, $$12 - $$17, $$13, $$12);
/*     */   }
/*     */   
/*     */   public double noiseWithDerivative(double $$0, double $$1, double $$2, double[] $$3) {
/*  79 */     double $$4 = $$0 + this.xo;
/*  80 */     double $$5 = $$1 + this.yo;
/*  81 */     double $$6 = $$2 + this.zo;
/*     */     
/*  83 */     int $$7 = Mth.floor($$4);
/*  84 */     int $$8 = Mth.floor($$5);
/*  85 */     int $$9 = Mth.floor($$6);
/*     */ 
/*     */     
/*  88 */     double $$10 = $$4 - $$7;
/*  89 */     double $$11 = $$5 - $$8;
/*  90 */     double $$12 = $$6 - $$9;
/*     */     
/*  92 */     return sampleWithDerivative($$7, $$8, $$9, $$10, $$11, $$12, $$3);
/*     */   }
/*     */   
/*     */   private static double gradDot(int $$0, double $$1, double $$2, double $$3) {
/*  96 */     return SimplexNoise.dot(SimplexNoise.GRADIENT[$$0 & 0xF], $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private int p(int $$0) {
/* 100 */     return this.p[$$0 & 0xFF] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double sampleAndLerp(int $$0, int $$1, int $$2, double $$3, double $$4, double $$5, double $$6) {
/* 106 */     int $$7 = p($$0);
/* 107 */     int $$8 = p($$0 + 1);
/*     */     
/* 109 */     int $$9 = p($$7 + $$1);
/* 110 */     int $$10 = p($$7 + $$1 + 1);
/* 111 */     int $$11 = p($$8 + $$1);
/* 112 */     int $$12 = p($$8 + $$1 + 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     double $$13 = gradDot(p($$9 + $$2), $$3, $$4, $$5);
/* 121 */     double $$14 = gradDot(p($$11 + $$2), $$3 - 1.0D, $$4, $$5);
/* 122 */     double $$15 = gradDot(p($$10 + $$2), $$3, $$4 - 1.0D, $$5);
/* 123 */     double $$16 = gradDot(p($$12 + $$2), $$3 - 1.0D, $$4 - 1.0D, $$5);
/* 124 */     double $$17 = gradDot(p($$9 + $$2 + 1), $$3, $$4, $$5 - 1.0D);
/* 125 */     double $$18 = gradDot(p($$11 + $$2 + 1), $$3 - 1.0D, $$4, $$5 - 1.0D);
/* 126 */     double $$19 = gradDot(p($$10 + $$2 + 1), $$3, $$4 - 1.0D, $$5 - 1.0D);
/* 127 */     double $$20 = gradDot(p($$12 + $$2 + 1), $$3 - 1.0D, $$4 - 1.0D, $$5 - 1.0D);
/*     */ 
/*     */     
/* 130 */     double $$21 = Mth.smoothstep($$3);
/* 131 */     double $$22 = Mth.smoothstep($$6);
/* 132 */     double $$23 = Mth.smoothstep($$5);
/*     */ 
/*     */     
/* 135 */     return Mth.lerp3($$21, $$22, $$23, $$13, $$14, $$15, $$16, $$17, $$18, $$19, $$20);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double sampleWithDerivative(int $$0, int $$1, int $$2, double $$3, double $$4, double $$5, double[] $$6) {
/* 141 */     int $$7 = p($$0);
/* 142 */     int $$8 = p($$0 + 1);
/*     */     
/* 144 */     int $$9 = p($$7 + $$1);
/* 145 */     int $$10 = p($$7 + $$1 + 1);
/* 146 */     int $$11 = p($$8 + $$1);
/* 147 */     int $$12 = p($$8 + $$1 + 1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     int $$13 = p($$9 + $$2);
/* 153 */     int $$14 = p($$11 + $$2);
/* 154 */     int $$15 = p($$10 + $$2);
/* 155 */     int $$16 = p($$12 + $$2);
/* 156 */     int $$17 = p($$9 + $$2 + 1);
/* 157 */     int $$18 = p($$11 + $$2 + 1);
/* 158 */     int $$19 = p($$10 + $$2 + 1);
/* 159 */     int $$20 = p($$12 + $$2 + 1);
/*     */     
/* 161 */     int[] $$21 = SimplexNoise.GRADIENT[$$13 & 0xF];
/* 162 */     int[] $$22 = SimplexNoise.GRADIENT[$$14 & 0xF];
/* 163 */     int[] $$23 = SimplexNoise.GRADIENT[$$15 & 0xF];
/* 164 */     int[] $$24 = SimplexNoise.GRADIENT[$$16 & 0xF];
/* 165 */     int[] $$25 = SimplexNoise.GRADIENT[$$17 & 0xF];
/* 166 */     int[] $$26 = SimplexNoise.GRADIENT[$$18 & 0xF];
/* 167 */     int[] $$27 = SimplexNoise.GRADIENT[$$19 & 0xF];
/* 168 */     int[] $$28 = SimplexNoise.GRADIENT[$$20 & 0xF];
/*     */     
/* 170 */     double $$29 = SimplexNoise.dot($$21, $$3, $$4, $$5);
/* 171 */     double $$30 = SimplexNoise.dot($$22, $$3 - 1.0D, $$4, $$5);
/* 172 */     double $$31 = SimplexNoise.dot($$23, $$3, $$4 - 1.0D, $$5);
/* 173 */     double $$32 = SimplexNoise.dot($$24, $$3 - 1.0D, $$4 - 1.0D, $$5);
/* 174 */     double $$33 = SimplexNoise.dot($$25, $$3, $$4, $$5 - 1.0D);
/* 175 */     double $$34 = SimplexNoise.dot($$26, $$3 - 1.0D, $$4, $$5 - 1.0D);
/* 176 */     double $$35 = SimplexNoise.dot($$27, $$3, $$4 - 1.0D, $$5 - 1.0D);
/* 177 */     double $$36 = SimplexNoise.dot($$28, $$3 - 1.0D, $$4 - 1.0D, $$5 - 1.0D);
/*     */     
/* 179 */     double $$37 = Mth.smoothstep($$3);
/* 180 */     double $$38 = Mth.smoothstep($$4);
/* 181 */     double $$39 = Mth.smoothstep($$5);
/*     */     
/* 183 */     double $$40 = Mth.lerp3($$37, $$38, $$39, $$21[0], $$22[0], $$23[0], $$24[0], $$25[0], $$26[0], $$27[0], $$28[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     double $$41 = Mth.lerp3($$37, $$38, $$39, $$21[1], $$22[1], $$23[1], $$24[1], $$25[1], $$26[1], $$27[1], $$28[1]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     double $$42 = Mth.lerp3($$37, $$38, $$39, $$21[2], $$22[2], $$23[2], $$24[2], $$25[2], $$26[2], $$27[2], $$28[2]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     double $$43 = Mth.lerp2($$38, $$39, $$30 - $$29, $$32 - $$31, $$34 - $$33, $$36 - $$35);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     double $$44 = Mth.lerp2($$39, $$37, $$31 - $$29, $$35 - $$33, $$32 - $$30, $$36 - $$34);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     double $$45 = Mth.lerp2($$37, $$38, $$33 - $$29, $$34 - $$30, $$35 - $$31, $$36 - $$32);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     double $$46 = Mth.smoothstepDerivative($$3);
/* 232 */     double $$47 = Mth.smoothstepDerivative($$4);
/* 233 */     double $$48 = Mth.smoothstepDerivative($$5);
/*     */     
/* 235 */     double $$49 = $$40 + $$46 * $$43;
/* 236 */     double $$50 = $$41 + $$47 * $$44;
/* 237 */     double $$51 = $$42 + $$48 * $$45;
/*     */     
/* 239 */     $$6[0] = $$6[0] + $$49;
/* 240 */     $$6[1] = $$6[1] + $$50;
/* 241 */     $$6[2] = $$6[2] + $$51;
/*     */ 
/*     */     
/* 244 */     return Mth.lerp3($$37, $$38, $$39, $$29, $$30, $$31, $$32, $$33, $$34, $$35, $$36);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void parityConfigString(StringBuilder $$0) {
/* 249 */     NoiseUtils.parityNoiseOctaveConfigString($$0, this.xo, this.yo, this.zo, this.p);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\synth\ImprovedNoise.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */