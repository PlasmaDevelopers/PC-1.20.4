/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ 
/*     */ @VisibleForDebug
/*     */ public final class Multipoint<C, I extends ToFloatFunction<C>> extends Record implements CubicSpline<C, I> {
/*     */   private final I coordinate;
/*     */   final float[] locations;
/*     */   private final List<CubicSpline<C, I>> values;
/*     */   private final float[] derivatives;
/*     */   private final float minValue;
/*     */   private final float maxValue;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/CubicSpline$Multipoint;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #31	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/util/CubicSpline$Multipoint;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	7	0	this	Lnet/minecraft/util/CubicSpline$Multipoint<TC;TI;>;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/CubicSpline$Multipoint;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #31	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/util/CubicSpline$Multipoint;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	7	0	this	Lnet/minecraft/util/CubicSpline$Multipoint<TC;TI;>;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/CubicSpline$Multipoint;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #31	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/util/CubicSpline$Multipoint;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	0	this	Lnet/minecraft/util/CubicSpline$Multipoint<TC;TI;>;
/*     */   }
/*     */   
/*     */   public I coordinate() {
/*  31 */     return this.coordinate; } public float[] locations() { return this.locations; } public List<CubicSpline<C, I>> values() { return this.values; } public float[] derivatives() { return this.derivatives; } public float minValue() { return this.minValue; } public float maxValue() { return this.maxValue; }
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
/*     */   public Multipoint(I $$0, float[] $$1, List<CubicSpline<C, I>> $$2, float[] $$3, float $$4, float $$5) {
/*  47 */     validateSizes($$1, $$2, $$3); this.coordinate = $$0; this.locations = $$1;
/*     */     this.values = $$2;
/*     */     this.derivatives = $$3;
/*     */     this.minValue = $$4;
/*  51 */     this.maxValue = $$5; } static <C, I extends ToFloatFunction<C>> Multipoint<C, I> create(I $$0, float[] $$1, List<CubicSpline<C, I>> $$2, float[] $$3) { validateSizes($$1, $$2, $$3);
/*     */     
/*  53 */     int $$4 = $$1.length - 1;
/*     */     
/*  55 */     float $$5 = Float.POSITIVE_INFINITY;
/*  56 */     float $$6 = Float.NEGATIVE_INFINITY;
/*     */     
/*  58 */     float $$7 = $$0.minValue();
/*  59 */     float $$8 = $$0.maxValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     if ($$7 < $$1[0]) {
/*  66 */       float $$9 = linearExtend($$7, $$1, ((CubicSpline)$$2.get(0)).minValue(), $$3, 0);
/*  67 */       float $$10 = linearExtend($$7, $$1, ((CubicSpline)$$2.get(0)).maxValue(), $$3, 0);
/*     */       
/*  69 */       $$5 = Math.min($$5, Math.min($$9, $$10));
/*  70 */       $$6 = Math.max($$6, Math.max($$9, $$10));
/*     */     } 
/*     */ 
/*     */     
/*  74 */     if ($$8 > $$1[$$4]) {
/*  75 */       float $$11 = linearExtend($$8, $$1, ((CubicSpline)$$2.get($$4)).minValue(), $$3, $$4);
/*  76 */       float $$12 = linearExtend($$8, $$1, ((CubicSpline)$$2.get($$4)).maxValue(), $$3, $$4);
/*     */       
/*  78 */       $$5 = Math.min($$5, Math.min($$11, $$12));
/*  79 */       $$6 = Math.max($$6, Math.max($$11, $$12));
/*     */     } 
/*     */ 
/*     */     
/*  83 */     for (CubicSpline<C, I> $$13 : $$2) {
/*  84 */       $$5 = Math.min($$5, $$13.minValue());
/*  85 */       $$6 = Math.max($$6, $$13.maxValue());
/*     */     } 
/*     */     
/*  88 */     for (int $$14 = 0; $$14 < $$4; $$14++) {
/*  89 */       float $$15 = $$1[$$14];
/*  90 */       float $$16 = $$1[$$14 + 1];
/*  91 */       float $$17 = $$16 - $$15;
/*     */       
/*  93 */       CubicSpline<C, I> $$18 = $$2.get($$14);
/*  94 */       CubicSpline<C, I> $$19 = $$2.get($$14 + 1);
/*     */       
/*  96 */       float $$20 = $$18.minValue();
/*  97 */       float $$21 = $$18.maxValue();
/*  98 */       float $$22 = $$19.minValue();
/*  99 */       float $$23 = $$19.maxValue();
/*     */       
/* 101 */       float $$24 = $$3[$$14];
/* 102 */       float $$25 = $$3[$$14 + 1];
/*     */       
/* 104 */       if ($$24 != 0.0F || $$25 != 0.0F) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 109 */         float $$26 = $$24 * $$17;
/* 110 */         float $$27 = $$25 * $$17;
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
/* 122 */         float $$28 = Math.min($$20, $$22);
/* 123 */         float $$29 = Math.max($$21, $$23);
/*     */         
/* 125 */         float $$30 = $$26 - $$23 + $$20;
/* 126 */         float $$31 = $$26 - $$22 + $$21;
/*     */         
/* 128 */         float $$32 = -$$27 + $$22 - $$21;
/* 129 */         float $$33 = -$$27 + $$23 - $$20;
/*     */         
/* 131 */         float $$34 = Math.min($$30, $$32);
/* 132 */         float $$35 = Math.max($$31, $$33);
/*     */         
/* 134 */         $$5 = Math.min($$5, $$28 + 0.25F * $$34);
/* 135 */         $$6 = Math.max($$6, $$29 + 0.25F * $$35);
/*     */       } 
/*     */     } 
/* 138 */     return new Multipoint<>($$0, $$1, $$2, $$3, $$5, $$6); }
/*     */ 
/*     */   
/*     */   private static float linearExtend(float $$0, float[] $$1, float $$2, float[] $$3, int $$4) {
/* 142 */     float $$5 = $$3[$$4];
/* 143 */     if ($$5 == 0.0F)
/*     */     {
/* 145 */       return $$2;
/*     */     }
/* 147 */     return $$2 + $$5 * ($$0 - $$1[$$4]);
/*     */   }
/*     */   
/*     */   private static <C, I extends ToFloatFunction<C>> void validateSizes(float[] $$0, List<CubicSpline<C, I>> $$1, float[] $$2) {
/* 151 */     if ($$0.length != $$1.size() || $$0.length != $$2.length) {
/* 152 */       throw new IllegalArgumentException("All lengths must be equal, got: " + $$0.length + " " + $$1.size() + " " + $$2.length);
/*     */     }
/* 154 */     if ($$0.length == 0) {
/* 155 */       throw new IllegalArgumentException("Cannot create a multipoint spline with no points");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float apply(C $$0) {
/* 161 */     float $$1 = this.coordinate.apply($$0);
/* 162 */     int $$2 = findIntervalStart(this.locations, $$1);
/*     */     
/* 164 */     int $$3 = this.locations.length - 1;
/*     */ 
/*     */     
/* 167 */     if ($$2 < 0) {
/* 168 */       return linearExtend($$1, this.locations, ((CubicSpline)this.values.get(0)).apply($$0), this.derivatives, 0);
/*     */     }
/* 170 */     if ($$2 == $$3) {
/* 171 */       return linearExtend($$1, this.locations, ((CubicSpline)this.values.get($$3)).apply($$0), this.derivatives, $$3);
/*     */     }
/* 173 */     float $$4 = this.locations[$$2];
/* 174 */     float $$5 = this.locations[$$2 + 1];
/* 175 */     float $$6 = ($$1 - $$4) / ($$5 - $$4);
/*     */     
/* 177 */     ToFloatFunction<C> $$7 = this.values.get($$2);
/* 178 */     ToFloatFunction<C> $$8 = this.values.get($$2 + 1);
/* 179 */     float $$9 = this.derivatives[$$2];
/* 180 */     float $$10 = this.derivatives[$$2 + 1];
/*     */     
/* 182 */     float $$11 = $$7.apply($$0);
/* 183 */     float $$12 = $$8.apply($$0);
/*     */     
/* 185 */     float $$13 = $$9 * ($$5 - $$4) - $$12 - $$11;
/* 186 */     float $$14 = -$$10 * ($$5 - $$4) + $$12 - $$11;
/*     */ 
/*     */     
/* 189 */     float $$15 = Mth.lerp($$6, $$11, $$12) + $$6 * (1.0F - $$6) * Mth.lerp($$6, $$13, $$14);
/* 190 */     return $$15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int findIntervalStart(float[] $$0, float $$1) {
/* 198 */     return Mth.binarySearch(0, $$0.length, $$2 -> ($$0 < $$1[$$2])) - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   public String parityString() {
/* 204 */     return "Spline{coordinate=" + this.coordinate + ", locations=" + toString(this.locations) + ", derivatives=" + toString(this.derivatives) + ", values=" + (String)this.values.stream().map(CubicSpline::parityString).collect(Collectors.joining(", ", "[", "]")) + "}";
/*     */   }
/*     */   
/*     */   private String toString(float[] $$0) {
/* 208 */     return "[" + (String)IntStream.range(0, $$0.length).mapToDouble($$1 -> $$0[$$1]).<CharSequence>mapToObj($$0 -> String.format(Locale.ROOT, "%.3f", new Object[] { Double.valueOf($$0) })).collect(Collectors.joining(", ")) + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public CubicSpline<C, I> mapAll(CubicSpline.CoordinateVisitor<I> $$0) {
/* 213 */     return create($$0
/* 214 */         .visit(this.coordinate), this.locations, 
/*     */         
/* 216 */         values().stream().map($$1 -> $$1.mapAll($$0)).toList(), this.derivatives);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\CubicSpline$Multipoint.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */