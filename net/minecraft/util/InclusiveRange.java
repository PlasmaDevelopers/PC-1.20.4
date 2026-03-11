/*    */ package net.minecraft.util;
/*    */ public final class InclusiveRange<T extends Comparable<T>> extends Record {
/*    */   private final T minInclusive;
/*    */   private final T maxInclusive;
/*    */   
/*  6 */   public T minInclusive() { return this.minInclusive; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/InclusiveRange;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/InclusiveRange;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/util/InclusiveRange<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/InclusiveRange;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/InclusiveRange;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*  6 */     //   0	8	0	this	Lnet/minecraft/util/InclusiveRange<TT;>; } public T maxInclusive() { return this.maxInclusive; }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends Comparable<T>> Codec<InclusiveRange<T>> codec(Codec<T> $$0) {
/* 11 */     return ExtraCodecs.intervalCodec($$0, "min_inclusive", "max_inclusive", InclusiveRange::create, InclusiveRange::minInclusive, InclusiveRange::maxInclusive);
/*    */   }
/*    */   
/*    */   public static <T extends Comparable<T>> Codec<InclusiveRange<T>> codec(Codec<T> $$0, T $$1, T $$2) {
/* 15 */     return ExtraCodecs.validate(codec($$0), $$2 -> ($$2.minInclusive().compareTo($$0) < 0) ? DataResult.error(()) : (($$2.maxInclusive().compareTo($$1) > 0) ? DataResult.error(()) : DataResult.success($$2)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends Comparable<T>> DataResult<InclusiveRange<T>> create(T $$0, T $$1) {
/* 27 */     if ($$0.compareTo($$1) <= 0) {
/* 28 */       return DataResult.success(new InclusiveRange<>($$0, $$1));
/*    */     }
/* 30 */     return DataResult.error(() -> "min_inclusive must be less than or equal to max_inclusive");
/*    */   }
/*    */   
/*    */   public InclusiveRange(T $$0, T $$1) {
/* 34 */     if ($$0.compareTo($$1) > 0)
/* 35 */       throw new IllegalArgumentException("min_inclusive must be less than or equal to max_inclusive"); 
/*    */     this.minInclusive = $$0;
/*    */     this.maxInclusive = $$1;
/*    */   }
/*    */   public InclusiveRange(T $$0) {
/* 40 */     this($$0, $$0);
/*    */   }
/*    */   
/* 43 */   public static final Codec<InclusiveRange<Integer>> INT = codec((Codec<Integer>)Codec.INT);
/*    */   
/*    */   public boolean isValueInRange(T $$0) {
/* 46 */     return ($$0.compareTo(this.minInclusive) >= 0 && $$0.compareTo(this.maxInclusive) <= 0);
/*    */   }
/*    */   
/*    */   public boolean contains(InclusiveRange<T> $$0) {
/* 50 */     return ($$0.minInclusive().compareTo(this.minInclusive) >= 0 && $$0.maxInclusive
/* 51 */       .compareTo(this.maxInclusive) <= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return "[" + this.minInclusive + ", " + this.maxInclusive + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\InclusiveRange.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */