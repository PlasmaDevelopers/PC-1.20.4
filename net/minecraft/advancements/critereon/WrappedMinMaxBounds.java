/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public final class WrappedMinMaxBounds extends Record {
/*     */   @Nullable
/*     */   private final Float min;
/*     */   @Nullable
/*     */   private final Float max;
/*     */   
/*  16 */   public WrappedMinMaxBounds(@Nullable Float $$0, @Nullable Float $$1) { this.min = $$0; this.max = $$1; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/WrappedMinMaxBounds;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #16	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  16 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/WrappedMinMaxBounds; } @Nullable public Float min() { return this.min; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/WrappedMinMaxBounds;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #16	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/WrappedMinMaxBounds; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/WrappedMinMaxBounds;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #16	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/WrappedMinMaxBounds;
/*  16 */     //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public Float max() { return this.max; }
/*  17 */    public static final WrappedMinMaxBounds ANY = new WrappedMinMaxBounds(null, null);
/*     */   
/*  19 */   public static final SimpleCommandExceptionType ERROR_INTS_ONLY = new SimpleCommandExceptionType((Message)Component.translatable("argument.range.ints"));
/*     */   
/*     */   public static WrappedMinMaxBounds exactly(float $$0) {
/*  22 */     return new WrappedMinMaxBounds(Float.valueOf($$0), Float.valueOf($$0));
/*     */   }
/*     */   
/*     */   public static WrappedMinMaxBounds between(float $$0, float $$1) {
/*  26 */     return new WrappedMinMaxBounds(Float.valueOf($$0), Float.valueOf($$1));
/*     */   }
/*     */   
/*     */   public static WrappedMinMaxBounds atLeast(float $$0) {
/*  30 */     return new WrappedMinMaxBounds(Float.valueOf($$0), null);
/*     */   }
/*     */   
/*     */   public static WrappedMinMaxBounds atMost(float $$0) {
/*  34 */     return new WrappedMinMaxBounds(null, Float.valueOf($$0));
/*     */   }
/*     */   
/*     */   public boolean matches(float $$0) {
/*  38 */     if (this.min != null && this.max != null && this.min.floatValue() > this.max.floatValue() && this.min.floatValue() > $$0 && this.max.floatValue() < $$0) {
/*  39 */       return false;
/*     */     }
/*  41 */     if (this.min != null && this.min.floatValue() > $$0) {
/*  42 */       return false;
/*     */     }
/*  44 */     if (this.max != null && this.max.floatValue() < $$0) {
/*  45 */       return false;
/*     */     }
/*  47 */     return true;
/*     */   }
/*     */   
/*     */   public boolean matchesSqr(double $$0) {
/*  51 */     if (this.min != null && this.max != null && this.min.floatValue() > this.max.floatValue() && (this.min.floatValue() * this.min.floatValue()) > $$0 && (this.max.floatValue() * this.max.floatValue()) < $$0) {
/*  52 */       return false;
/*     */     }
/*  54 */     if (this.min != null && (this.min.floatValue() * this.min.floatValue()) > $$0) {
/*  55 */       return false;
/*     */     }
/*  57 */     if (this.max != null && (this.max.floatValue() * this.max.floatValue()) < $$0) {
/*  58 */       return false;
/*     */     }
/*  60 */     return true;
/*     */   }
/*     */   
/*     */   public JsonElement serializeToJson() {
/*  64 */     if (this == ANY) {
/*  65 */       return (JsonElement)JsonNull.INSTANCE;
/*     */     }
/*     */     
/*  68 */     if (this.min != null && this.max != null && this.min.equals(this.max)) {
/*  69 */       return (JsonElement)new JsonPrimitive(this.min);
/*     */     }
/*     */     
/*  72 */     JsonObject $$0 = new JsonObject();
/*  73 */     if (this.min != null) {
/*  74 */       $$0.addProperty("min", this.min);
/*     */     }
/*  76 */     if (this.max != null) {
/*  77 */       $$0.addProperty("max", this.min);
/*     */     }
/*  79 */     return (JsonElement)$$0;
/*     */   }
/*     */   
/*     */   public static WrappedMinMaxBounds fromJson(@Nullable JsonElement $$0) {
/*  83 */     if ($$0 == null || $$0.isJsonNull()) {
/*  84 */       return ANY;
/*     */     }
/*     */     
/*  87 */     if (GsonHelper.isNumberValue($$0)) {
/*  88 */       float $$1 = GsonHelper.convertToFloat($$0, "value");
/*  89 */       return new WrappedMinMaxBounds(Float.valueOf($$1), Float.valueOf($$1));
/*     */     } 
/*  91 */     JsonObject $$2 = GsonHelper.convertToJsonObject($$0, "value");
/*  92 */     Float $$3 = $$2.has("min") ? Float.valueOf(GsonHelper.getAsFloat($$2, "min")) : null;
/*  93 */     Float $$4 = $$2.has("max") ? Float.valueOf(GsonHelper.getAsFloat($$2, "max")) : null;
/*  94 */     return new WrappedMinMaxBounds($$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static WrappedMinMaxBounds fromReader(StringReader $$0, boolean $$1) throws CommandSyntaxException {
/*  99 */     return fromReader($$0, $$1, $$0 -> $$0);
/*     */   }
/*     */   public static WrappedMinMaxBounds fromReader(StringReader $$0, boolean $$1, Function<Float, Float> $$2) throws CommandSyntaxException {
/*     */     Float $$6;
/* 103 */     if (!$$0.canRead()) {
/* 104 */       throw MinMaxBounds.ERROR_EMPTY.createWithContext($$0);
/*     */     }
/* 106 */     int $$3 = $$0.getCursor();
/* 107 */     Float $$4 = optionallyFormat(readNumber($$0, $$1), $$2);
/*     */     
/* 109 */     if ($$0.canRead(2) && $$0.peek() == '.' && $$0.peek(1) == '.')
/* 110 */     { $$0.skip();
/* 111 */       $$0.skip();
/* 112 */       Float $$5 = optionallyFormat(readNumber($$0, $$1), $$2);
/* 113 */       if ($$4 == null && $$5 == null) {
/* 114 */         $$0.setCursor($$3);
/* 115 */         throw MinMaxBounds.ERROR_EMPTY.createWithContext($$0);
/*     */       }  }
/* 117 */     else { if (!$$1 && $$0.canRead() && $$0.peek() == '.') {
/* 118 */         $$0.setCursor($$3);
/* 119 */         throw ERROR_INTS_ONLY.createWithContext($$0);
/*     */       } 
/* 121 */       $$6 = $$4; }
/*     */     
/* 123 */     if ($$4 == null && $$6 == null) {
/* 124 */       $$0.setCursor($$3);
/* 125 */       throw MinMaxBounds.ERROR_EMPTY.createWithContext($$0);
/*     */     } 
/* 127 */     return new WrappedMinMaxBounds($$4, $$6);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Float readNumber(StringReader $$0, boolean $$1) throws CommandSyntaxException {
/* 132 */     int $$2 = $$0.getCursor();
/* 133 */     while ($$0.canRead() && isAllowedNumber($$0, $$1)) {
/* 134 */       $$0.skip();
/*     */     }
/* 136 */     String $$3 = $$0.getString().substring($$2, $$0.getCursor());
/* 137 */     if ($$3.isEmpty()) {
/* 138 */       return null;
/*     */     }
/*     */     try {
/* 141 */       return Float.valueOf(Float.parseFloat($$3));
/* 142 */     } catch (NumberFormatException $$4) {
/* 143 */       if ($$1) {
/* 144 */         throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext($$0, $$3);
/*     */       }
/* 146 */       throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext($$0, $$3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isAllowedNumber(StringReader $$0, boolean $$1) {
/* 152 */     char $$2 = $$0.peek();
/* 153 */     if (($$2 >= '0' && $$2 <= '9') || $$2 == '-') {
/* 154 */       return true;
/*     */     }
/*     */     
/* 157 */     if ($$1 && $$2 == '.') {
/* 158 */       return (!$$0.canRead(2) || $$0.peek(1) != '.');
/*     */     }
/*     */     
/* 161 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Float optionallyFormat(@Nullable Float $$0, Function<Float, Float> $$1) {
/* 166 */     return ($$0 == null) ? null : $$1.apply($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\WrappedMinMaxBounds.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */