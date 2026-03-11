/*     */ package net.minecraft.commands.arguments.coordinates;
/*     */ 
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class WorldCoordinate {
/*     */   private static final char PREFIX_RELATIVE = '~';
/*  11 */   public static final SimpleCommandExceptionType ERROR_EXPECTED_DOUBLE = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos.missing.double"));
/*  12 */   public static final SimpleCommandExceptionType ERROR_EXPECTED_INT = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos.missing.int"));
/*     */   
/*     */   private final boolean relative;
/*     */   private final double value;
/*     */   
/*     */   public WorldCoordinate(boolean $$0, double $$1) {
/*  18 */     this.relative = $$0;
/*  19 */     this.value = $$1;
/*     */   }
/*     */   
/*     */   public double get(double $$0) {
/*  23 */     if (this.relative) {
/*  24 */       return this.value + $$0;
/*     */     }
/*  26 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static WorldCoordinate parseDouble(StringReader $$0, boolean $$1) throws CommandSyntaxException {
/*  31 */     if ($$0.canRead() && $$0.peek() == '^') {
/*  32 */       throw Vec3Argument.ERROR_MIXED_TYPE.createWithContext($$0);
/*     */     }
/*     */     
/*  35 */     if (!$$0.canRead()) {
/*  36 */       throw ERROR_EXPECTED_DOUBLE.createWithContext($$0);
/*     */     }
/*     */     
/*  39 */     boolean $$2 = isRelative($$0);
/*  40 */     int $$3 = $$0.getCursor();
/*  41 */     double $$4 = ($$0.canRead() && $$0.peek() != ' ') ? $$0.readDouble() : 0.0D;
/*  42 */     String $$5 = $$0.getString().substring($$3, $$0.getCursor());
/*     */     
/*  44 */     if ($$2 && $$5.isEmpty()) {
/*  45 */       return new WorldCoordinate(true, 0.0D);
/*     */     }
/*     */     
/*  48 */     if (!$$5.contains(".") && !$$2 && $$1) {
/*  49 */       $$4 += 0.5D;
/*     */     }
/*     */     
/*  52 */     return new WorldCoordinate($$2, $$4);
/*     */   }
/*     */   public static WorldCoordinate parseInt(StringReader $$0) throws CommandSyntaxException {
/*     */     double $$3;
/*  56 */     if ($$0.canRead() && $$0.peek() == '^') {
/*  57 */       throw Vec3Argument.ERROR_MIXED_TYPE.createWithContext($$0);
/*     */     }
/*     */     
/*  60 */     if (!$$0.canRead()) {
/*  61 */       throw ERROR_EXPECTED_INT.createWithContext($$0);
/*     */     }
/*     */     
/*  64 */     boolean $$1 = isRelative($$0);
/*     */     
/*  66 */     if ($$0.canRead() && $$0.peek() != ' ') {
/*  67 */       double $$2 = $$1 ? $$0.readDouble() : $$0.readInt();
/*     */     } else {
/*  69 */       $$3 = 0.0D;
/*     */     } 
/*  71 */     return new WorldCoordinate($$1, $$3);
/*     */   }
/*     */   
/*     */   public static boolean isRelative(StringReader $$0) {
/*     */     boolean $$2;
/*  76 */     if ($$0.peek() == '~') {
/*  77 */       boolean $$1 = true;
/*  78 */       $$0.skip();
/*     */     } else {
/*  80 */       $$2 = false;
/*     */     } 
/*  82 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  87 */     if (this == $$0) {
/*  88 */       return true;
/*     */     }
/*  90 */     if (!($$0 instanceof WorldCoordinate)) {
/*  91 */       return false;
/*     */     }
/*     */     
/*  94 */     WorldCoordinate $$1 = (WorldCoordinate)$$0;
/*     */     
/*  96 */     if (this.relative != $$1.relative) {
/*  97 */       return false;
/*     */     }
/*  99 */     return (Double.compare($$1.value, this.value) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     int $$0 = this.relative ? 1 : 0;
/* 107 */     long $$1 = Double.doubleToLongBits(this.value);
/* 108 */     $$0 = 31 * $$0 + (int)($$1 ^ $$1 >>> 32L);
/* 109 */     return $$0;
/*     */   }
/*     */   
/*     */   public boolean isRelative() {
/* 113 */     return this.relative;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\WorldCoordinate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */