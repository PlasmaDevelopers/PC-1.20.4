/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ 
/*    */ public final class Ints extends Record implements MinMaxBounds<Integer> {
/*    */   private final Optional<Integer> min;
/*    */   private final Optional<Integer> max;
/*    */   private final Optional<Long> minSq;
/*    */   private final Optional<Long> maxSq;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;
/*    */   }
/*    */   
/* 18 */   public Ints(Optional<Integer> $$0, Optional<Integer> $$1, Optional<Long> $$2, Optional<Long> $$3) { this.min = $$0; this.max = $$1; this.minSq = $$2; this.maxSq = $$3; } public Optional<Integer> min() { return this.min; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;
/* 18 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Integer> max() { return this.max; } public Optional<Long> minSq() { return this.minSq; } public Optional<Long> maxSq() { return this.maxSq; }
/* 19 */    public static final Ints ANY = new Ints(Optional.empty(), Optional.empty());
/*    */ 
/*    */ 
/*    */   
/* 23 */   public static final Codec<Ints> CODEC = MinMaxBounds.createCodec((Codec<Number>)Codec.INT, Ints::new);
/*    */   
/*    */   private static Ints create(StringReader $$0, Optional<Integer> $$1, Optional<Integer> $$2) throws CommandSyntaxException {
/* 26 */     if ($$1.isPresent() && $$2.isPresent() && ((Integer)$$1.get()).intValue() > ((Integer)$$2.get()).intValue()) {
/* 27 */       throw ERROR_SWAPPED.createWithContext($$0);
/*    */     }
/*    */     
/* 30 */     return new Ints($$1, $$2);
/*    */   }
/*    */   
/*    */   private static Optional<Long> squareOpt(Optional<Integer> $$0) {
/* 34 */     return $$0.map($$0 -> Long.valueOf($$0.longValue() * $$0.longValue()));
/*    */   }
/*    */   
/*    */   private Ints(Optional<Integer> $$0, Optional<Integer> $$1) {
/* 38 */     this($$0, $$1, $$0.map($$0 -> Long.valueOf($$0.longValue() * $$0.longValue())), squareOpt($$1));
/*    */   }
/*    */   
/*    */   public static Ints exactly(int $$0) {
/* 42 */     return new Ints(Optional.of(Integer.valueOf($$0)), Optional.of(Integer.valueOf($$0)));
/*    */   }
/*    */   
/*    */   public static Ints between(int $$0, int $$1) {
/* 46 */     return new Ints(Optional.of(Integer.valueOf($$0)), Optional.of(Integer.valueOf($$1)));
/*    */   }
/*    */   
/*    */   public static Ints atLeast(int $$0) {
/* 50 */     return new Ints(Optional.of(Integer.valueOf($$0)), Optional.empty());
/*    */   }
/*    */   
/*    */   public static Ints atMost(int $$0) {
/* 54 */     return new Ints(Optional.empty(), Optional.of(Integer.valueOf($$0)));
/*    */   }
/*    */   
/*    */   public boolean matches(int $$0) {
/* 58 */     if (this.min.isPresent() && ((Integer)this.min.get()).intValue() > $$0) {
/* 59 */       return false;
/*    */     }
/* 61 */     return (this.max.isEmpty() || ((Integer)this.max.get()).intValue() >= $$0);
/*    */   }
/*    */   
/*    */   public boolean matchesSqr(long $$0) {
/* 65 */     if (this.minSq.isPresent() && ((Long)this.minSq.get()).longValue() > $$0) {
/* 66 */       return false;
/*    */     }
/* 68 */     return (this.maxSq.isEmpty() || ((Long)this.maxSq.get()).longValue() >= $$0);
/*    */   }
/*    */   
/*    */   public static Ints fromReader(StringReader $$0) throws CommandSyntaxException {
/* 72 */     return fromReader($$0, $$0 -> $$0);
/*    */   }
/*    */   
/*    */   public static Ints fromReader(StringReader $$0, Function<Integer, Integer> $$1) throws CommandSyntaxException {
/* 76 */     Objects.requireNonNull(CommandSyntaxException.BUILT_IN_EXCEPTIONS); return MinMaxBounds.<Integer, Ints>fromReader($$0, Ints::create, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\MinMaxBounds$Ints.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */