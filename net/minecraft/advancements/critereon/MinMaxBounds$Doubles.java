/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
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
/*     */ public final class Doubles
/*     */   extends Record
/*     */   implements MinMaxBounds<Double>
/*     */ {
/*     */   private final Optional<Double> min;
/*     */   private final Optional<Double> max;
/*     */   private final Optional<Double> minSq;
/*     */   private final Optional<Double> maxSq;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #80	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #80	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #80	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public Doubles(Optional<Double> $$0, Optional<Double> $$1, Optional<Double> $$2, Optional<Double> $$3) {
/*  80 */     this.min = $$0; this.max = $$1; this.minSq = $$2; this.maxSq = $$3; } public Optional<Double> min() { return this.min; } public Optional<Double> max() { return this.max; } public Optional<Double> minSq() { return this.minSq; } public Optional<Double> maxSq() { return this.maxSq; }
/*  81 */    public static final Doubles ANY = new Doubles(Optional.empty(), Optional.empty());
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final Codec<Doubles> CODEC = MinMaxBounds.createCodec((Codec<Number>)Codec.DOUBLE, Doubles::new);
/*     */   
/*     */   private static Doubles create(StringReader $$0, Optional<Double> $$1, Optional<Double> $$2) throws CommandSyntaxException {
/*  88 */     if ($$1.isPresent() && $$2.isPresent() && ((Double)$$1.get()).doubleValue() > ((Double)$$2.get()).doubleValue()) {
/*  89 */       throw ERROR_SWAPPED.createWithContext($$0);
/*     */     }
/*     */     
/*  92 */     return new Doubles($$1, $$2);
/*     */   }
/*     */   
/*     */   private static Optional<Double> squareOpt(Optional<Double> $$0) {
/*  96 */     return $$0.map($$0 -> Double.valueOf($$0.doubleValue() * $$0.doubleValue()));
/*     */   }
/*     */   
/*     */   private Doubles(Optional<Double> $$0, Optional<Double> $$1) {
/* 100 */     this($$0, $$1, squareOpt($$0), squareOpt($$1));
/*     */   }
/*     */   
/*     */   public static Doubles exactly(double $$0) {
/* 104 */     return new Doubles(Optional.of(Double.valueOf($$0)), Optional.of(Double.valueOf($$0)));
/*     */   }
/*     */   
/*     */   public static Doubles between(double $$0, double $$1) {
/* 108 */     return new Doubles(Optional.of(Double.valueOf($$0)), Optional.of(Double.valueOf($$1)));
/*     */   }
/*     */   
/*     */   public static Doubles atLeast(double $$0) {
/* 112 */     return new Doubles(Optional.of(Double.valueOf($$0)), Optional.empty());
/*     */   }
/*     */   
/*     */   public static Doubles atMost(double $$0) {
/* 116 */     return new Doubles(Optional.empty(), Optional.of(Double.valueOf($$0)));
/*     */   }
/*     */   
/*     */   public boolean matches(double $$0) {
/* 120 */     if (this.min.isPresent() && ((Double)this.min.get()).doubleValue() > $$0) {
/* 121 */       return false;
/*     */     }
/* 123 */     return (this.max.isEmpty() || ((Double)this.max.get()).doubleValue() >= $$0);
/*     */   }
/*     */   
/*     */   public boolean matchesSqr(double $$0) {
/* 127 */     if (this.minSq.isPresent() && ((Double)this.minSq.get()).doubleValue() > $$0) {
/* 128 */       return false;
/*     */     }
/* 130 */     return (this.maxSq.isEmpty() || ((Double)this.maxSq.get()).doubleValue() >= $$0);
/*     */   }
/*     */   
/*     */   public static Doubles fromReader(StringReader $$0) throws CommandSyntaxException {
/* 134 */     return fromReader($$0, $$0 -> $$0);
/*     */   }
/*     */   
/*     */   public static Doubles fromReader(StringReader $$0, Function<Double, Double> $$1) throws CommandSyntaxException {
/* 138 */     Objects.requireNonNull(CommandSyntaxException.BUILT_IN_EXCEPTIONS); return MinMaxBounds.<Double, Doubles>fromReader($$0, Doubles::create, Double::parseDouble, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidDouble, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\MinMaxBounds$Doubles.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */