/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ public interface MinMaxBounds<T extends Number> {
/*     */   public static final class Ints extends Record implements MinMaxBounds<Integer> { private final Optional<Integer> min;
/*     */     private final Optional<Integer> max;
/*     */     private final Optional<Long> minSq;
/*     */     private final Optional<Long> maxSq;
/*     */     
/*  18 */     public Ints(Optional<Integer> $$0, Optional<Integer> $$1, Optional<Long> $$2, Optional<Long> $$3) { this.min = $$0; this.max = $$1; this.minSq = $$2; this.maxSq = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #18	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  18 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints; } public Optional<Integer> min() { return this.min; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #18	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #18	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Ints;
/*  18 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Integer> max() { return this.max; } public Optional<Long> minSq() { return this.minSq; } public Optional<Long> maxSq() { return this.maxSq; }
/*  19 */      public static final Ints ANY = new Ints(Optional.empty(), Optional.empty());
/*     */ 
/*     */ 
/*     */     
/*  23 */     public static final Codec<Ints> CODEC = MinMaxBounds.createCodec((Codec<Number>)Codec.INT, Ints::new);
/*     */     
/*     */     private static Ints create(StringReader $$0, Optional<Integer> $$1, Optional<Integer> $$2) throws CommandSyntaxException {
/*  26 */       if ($$1.isPresent() && $$2.isPresent() && ((Integer)$$1.get()).intValue() > ((Integer)$$2.get()).intValue()) {
/*  27 */         throw ERROR_SWAPPED.createWithContext($$0);
/*     */       }
/*     */       
/*  30 */       return new Ints($$1, $$2);
/*     */     }
/*     */     
/*     */     private static Optional<Long> squareOpt(Optional<Integer> $$0) {
/*  34 */       return $$0.map($$0 -> Long.valueOf($$0.longValue() * $$0.longValue()));
/*     */     }
/*     */     
/*     */     private Ints(Optional<Integer> $$0, Optional<Integer> $$1) {
/*  38 */       this($$0, $$1, $$0.map($$0 -> Long.valueOf($$0.longValue() * $$0.longValue())), squareOpt($$1));
/*     */     }
/*     */     
/*     */     public static Ints exactly(int $$0) {
/*  42 */       return new Ints(Optional.of(Integer.valueOf($$0)), Optional.of(Integer.valueOf($$0)));
/*     */     }
/*     */     
/*     */     public static Ints between(int $$0, int $$1) {
/*  46 */       return new Ints(Optional.of(Integer.valueOf($$0)), Optional.of(Integer.valueOf($$1)));
/*     */     }
/*     */     
/*     */     public static Ints atLeast(int $$0) {
/*  50 */       return new Ints(Optional.of(Integer.valueOf($$0)), Optional.empty());
/*     */     }
/*     */     
/*     */     public static Ints atMost(int $$0) {
/*  54 */       return new Ints(Optional.empty(), Optional.of(Integer.valueOf($$0)));
/*     */     }
/*     */     
/*     */     public boolean matches(int $$0) {
/*  58 */       if (this.min.isPresent() && ((Integer)this.min.get()).intValue() > $$0) {
/*  59 */         return false;
/*     */       }
/*  61 */       return (this.max.isEmpty() || ((Integer)this.max.get()).intValue() >= $$0);
/*     */     }
/*     */     
/*     */     public boolean matchesSqr(long $$0) {
/*  65 */       if (this.minSq.isPresent() && ((Long)this.minSq.get()).longValue() > $$0) {
/*  66 */         return false;
/*     */       }
/*  68 */       return (this.maxSq.isEmpty() || ((Long)this.maxSq.get()).longValue() >= $$0);
/*     */     }
/*     */     
/*     */     public static Ints fromReader(StringReader $$0) throws CommandSyntaxException {
/*  72 */       return fromReader($$0, $$0 -> $$0);
/*     */     }
/*     */     
/*     */     public static Ints fromReader(StringReader $$0, Function<Integer, Integer> $$1) throws CommandSyntaxException {
/*  76 */       Objects.requireNonNull(CommandSyntaxException.BUILT_IN_EXCEPTIONS); return MinMaxBounds.<Integer, Ints>fromReader($$0, Ints::create, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt, $$1);
/*     */     } }
/*     */   public static final class Doubles extends Record implements MinMaxBounds<Double> { private final Optional<Double> min; private final Optional<Double> max; private final Optional<Double> minSq; private final Optional<Double> maxSq;
/*     */     
/*  80 */     public Doubles(Optional<Double> $$0, Optional<Double> $$1, Optional<Double> $$2, Optional<Double> $$3) { this.min = $$0; this.max = $$1; this.minSq = $$2; this.maxSq = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #80	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #80	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #80	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;
/*  80 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Double> min() { return this.min; } public Optional<Double> max() { return this.max; } public Optional<Double> minSq() { return this.minSq; } public Optional<Double> maxSq() { return this.maxSq; }
/*  81 */      public static final Doubles ANY = new Doubles(Optional.empty(), Optional.empty());
/*     */ 
/*     */ 
/*     */     
/*  85 */     public static final Codec<Doubles> CODEC = MinMaxBounds.createCodec((Codec<Number>)Codec.DOUBLE, Doubles::new);
/*     */     
/*     */     private static Doubles create(StringReader $$0, Optional<Double> $$1, Optional<Double> $$2) throws CommandSyntaxException {
/*  88 */       if ($$1.isPresent() && $$2.isPresent() && ((Double)$$1.get()).doubleValue() > ((Double)$$2.get()).doubleValue()) {
/*  89 */         throw ERROR_SWAPPED.createWithContext($$0);
/*     */       }
/*     */       
/*  92 */       return new Doubles($$1, $$2);
/*     */     }
/*     */     
/*     */     private static Optional<Double> squareOpt(Optional<Double> $$0) {
/*  96 */       return $$0.map($$0 -> Double.valueOf($$0.doubleValue() * $$0.doubleValue()));
/*     */     }
/*     */     
/*     */     private Doubles(Optional<Double> $$0, Optional<Double> $$1) {
/* 100 */       this($$0, $$1, squareOpt($$0), squareOpt($$1));
/*     */     }
/*     */     
/*     */     public static Doubles exactly(double $$0) {
/* 104 */       return new Doubles(Optional.of(Double.valueOf($$0)), Optional.of(Double.valueOf($$0)));
/*     */     }
/*     */     
/*     */     public static Doubles between(double $$0, double $$1) {
/* 108 */       return new Doubles(Optional.of(Double.valueOf($$0)), Optional.of(Double.valueOf($$1)));
/*     */     }
/*     */     
/*     */     public static Doubles atLeast(double $$0) {
/* 112 */       return new Doubles(Optional.of(Double.valueOf($$0)), Optional.empty());
/*     */     }
/*     */     
/*     */     public static Doubles atMost(double $$0) {
/* 116 */       return new Doubles(Optional.empty(), Optional.of(Double.valueOf($$0)));
/*     */     }
/*     */     
/*     */     public boolean matches(double $$0) {
/* 120 */       if (this.min.isPresent() && ((Double)this.min.get()).doubleValue() > $$0) {
/* 121 */         return false;
/*     */       }
/* 123 */       return (this.max.isEmpty() || ((Double)this.max.get()).doubleValue() >= $$0);
/*     */     }
/*     */     
/*     */     public boolean matchesSqr(double $$0) {
/* 127 */       if (this.minSq.isPresent() && ((Double)this.minSq.get()).doubleValue() > $$0) {
/* 128 */         return false;
/*     */       }
/* 130 */       return (this.maxSq.isEmpty() || ((Double)this.maxSq.get()).doubleValue() >= $$0);
/*     */     }
/*     */     
/*     */     public static Doubles fromReader(StringReader $$0) throws CommandSyntaxException {
/* 134 */       return fromReader($$0, $$0 -> $$0);
/*     */     }
/*     */     
/*     */     public static Doubles fromReader(StringReader $$0, Function<Double, Double> $$1) throws CommandSyntaxException {
/* 138 */       Objects.requireNonNull(CommandSyntaxException.BUILT_IN_EXCEPTIONS); return MinMaxBounds.<Double, Doubles>fromReader($$0, Doubles::create, Double::parseDouble, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidDouble, $$1);
/*     */     } }
/*     */ 
/*     */   
/* 142 */   public static final SimpleCommandExceptionType ERROR_EMPTY = new SimpleCommandExceptionType((Message)Component.translatable("argument.range.empty"));
/* 143 */   public static final SimpleCommandExceptionType ERROR_SWAPPED = new SimpleCommandExceptionType((Message)Component.translatable("argument.range.swapped"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isAny() {
/* 150 */     return (min().isEmpty() && max().isEmpty());
/*     */   }
/*     */   
/*     */   default Optional<T> unwrapPoint() {
/* 154 */     Optional<T> $$0 = min();
/* 155 */     Optional<T> $$1 = max();
/* 156 */     return $$0.equals($$1) ? $$0 : Optional.<T>empty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends Number, R extends MinMaxBounds<T>> Codec<R> createCodec(Codec<T> $$0, BoundsFactory<T, R> $$1) {
/* 165 */     Codec<R> $$2 = RecordCodecBuilder.create($$2 -> {
/*     */           Objects.requireNonNull($$1);
/*     */           
/*     */           return $$2.group((App)ExtraCodecs.strictOptionalField($$0, "min").forGetter(MinMaxBounds::min), (App)ExtraCodecs.strictOptionalField($$0, "max").forGetter(MinMaxBounds::max)).apply((Applicative)$$2, $$1::create);
/*     */         });
/* 170 */     return Codec.either($$2, $$0).xmap($$1 -> (MinMaxBounds)$$1.map((), ()), $$0 -> {
/*     */           Optional<T> $$1 = $$0.unwrapPoint();
/*     */           return $$1.isPresent() ? Either.right((Number)$$1.get()) : Either.left($$0);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends Number, R extends MinMaxBounds<T>> R fromReader(StringReader $$0, BoundsFromReaderFactory<T, R> $$1, Function<String, T> $$2, Supplier<DynamicCommandExceptionType> $$3, Function<T, T> $$4) throws CommandSyntaxException {
/* 185 */     if (!$$0.canRead()) {
/* 186 */       throw ERROR_EMPTY.createWithContext($$0);
/*     */     }
/*     */     
/* 189 */     int $$5 = $$0.getCursor();
/*     */     
/*     */     try {
/* 192 */       Optional<T> $$8, $$6 = readNumber($$0, $$2, $$3).map($$4);
/*     */       
/* 194 */       if ($$0.canRead(2) && $$0.peek() == '.' && $$0.peek(1) == '.') {
/* 195 */         $$0.skip();
/* 196 */         $$0.skip();
/* 197 */         Optional<T> $$7 = readNumber($$0, $$2, $$3).map($$4);
/* 198 */         if ($$6.isEmpty() && $$7.isEmpty()) {
/* 199 */           throw ERROR_EMPTY.createWithContext($$0);
/*     */         }
/*     */       } else {
/* 202 */         $$8 = $$6;
/*     */       } 
/*     */       
/* 205 */       if ($$6.isEmpty() && $$8.isEmpty()) {
/* 206 */         throw ERROR_EMPTY.createWithContext($$0);
/*     */       }
/* 208 */       return $$1.create($$0, $$6, $$8);
/* 209 */     } catch (CommandSyntaxException $$9) {
/* 210 */       $$0.setCursor($$5);
/* 211 */       throw new CommandSyntaxException($$9.getType(), $$9.getRawMessage(), $$9.getInput(), $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static <T extends Number> Optional<T> readNumber(StringReader $$0, Function<String, T> $$1, Supplier<DynamicCommandExceptionType> $$2) throws CommandSyntaxException {
/* 216 */     int $$3 = $$0.getCursor();
/* 217 */     while ($$0.canRead() && isAllowedInputChat($$0)) {
/* 218 */       $$0.skip();
/*     */     }
/* 220 */     String $$4 = $$0.getString().substring($$3, $$0.getCursor());
/* 221 */     if ($$4.isEmpty()) {
/* 222 */       return Optional.empty();
/*     */     }
/*     */     try {
/* 225 */       return Optional.of($$1.apply($$4));
/* 226 */     } catch (NumberFormatException $$5) {
/* 227 */       throw ((DynamicCommandExceptionType)$$2.get()).createWithContext($$0, $$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isAllowedInputChat(StringReader $$0) {
/* 232 */     char $$1 = $$0.peek();
/* 233 */     if (($$1 >= '0' && $$1 <= '9') || $$1 == '-') {
/* 234 */       return true;
/*     */     }
/*     */     
/* 237 */     if ($$1 == '.') {
/* 238 */       return (!$$0.canRead(2) || $$0.peek(1) != '.');
/*     */     }
/*     */     
/* 241 */     return false;
/*     */   }
/*     */   
/*     */   Optional<T> min();
/*     */   
/*     */   Optional<T> max();
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BoundsFromReaderFactory<T extends Number, R extends MinMaxBounds<T>> {
/*     */     R create(StringReader param1StringReader, Optional<T> param1Optional1, Optional<T> param1Optional2) throws CommandSyntaxException;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BoundsFactory<T extends Number, R extends MinMaxBounds<T>> {
/*     */     R create(Optional<T> param1Optional1, Optional<T> param1Optional2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\MinMaxBounds.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */