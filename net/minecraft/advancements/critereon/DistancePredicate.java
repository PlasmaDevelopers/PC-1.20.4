/*    */ package net.minecraft.advancements.critereon;public final class DistancePredicate extends Record { private final MinMaxBounds.Doubles x;
/*    */   private final MinMaxBounds.Doubles y;
/*    */   private final MinMaxBounds.Doubles z;
/*    */   private final MinMaxBounds.Doubles horizontal;
/*    */   private final MinMaxBounds.Doubles absolute;
/*    */   public static final Codec<DistancePredicate> CODEC;
/*    */   
/*  8 */   public DistancePredicate(MinMaxBounds.Doubles $$0, MinMaxBounds.Doubles $$1, MinMaxBounds.Doubles $$2, MinMaxBounds.Doubles $$3, MinMaxBounds.Doubles $$4) { this.x = $$0; this.y = $$1; this.z = $$2; this.horizontal = $$3; this.absolute = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/DistancePredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/DistancePredicate; } public MinMaxBounds.Doubles x() { return this.x; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/DistancePredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/DistancePredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/DistancePredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/DistancePredicate;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public MinMaxBounds.Doubles y() { return this.y; } public MinMaxBounds.Doubles z() { return this.z; } public MinMaxBounds.Doubles horizontal() { return this.horizontal; } public MinMaxBounds.Doubles absolute() { return this.absolute; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "x", MinMaxBounds.Doubles.ANY).forGetter(DistancePredicate::x), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "y", MinMaxBounds.Doubles.ANY).forGetter(DistancePredicate::y), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "z", MinMaxBounds.Doubles.ANY).forGetter(DistancePredicate::z), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "horizontal", MinMaxBounds.Doubles.ANY).forGetter(DistancePredicate::horizontal), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "absolute", MinMaxBounds.Doubles.ANY).forGetter(DistancePredicate::absolute)).apply((Applicative)$$0, DistancePredicate::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DistancePredicate horizontal(MinMaxBounds.Doubles $$0) {
/* 24 */     return new DistancePredicate(MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, $$0, MinMaxBounds.Doubles.ANY);
/*    */   }
/*    */   
/*    */   public static DistancePredicate vertical(MinMaxBounds.Doubles $$0) {
/* 28 */     return new DistancePredicate(MinMaxBounds.Doubles.ANY, $$0, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY);
/*    */   }
/*    */   
/*    */   public static DistancePredicate absolute(MinMaxBounds.Doubles $$0) {
/* 32 */     return new DistancePredicate(MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, $$0);
/*    */   }
/*    */   
/*    */   public boolean matches(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/* 36 */     float $$6 = (float)($$0 - $$3);
/* 37 */     float $$7 = (float)($$1 - $$4);
/* 38 */     float $$8 = (float)($$2 - $$5);
/* 39 */     if (!this.x.matches(Mth.abs($$6)) || !this.y.matches(Mth.abs($$7)) || !this.z.matches(Mth.abs($$8))) {
/* 40 */       return false;
/*    */     }
/* 42 */     if (!this.horizontal.matchesSqr(($$6 * $$6 + $$8 * $$8))) {
/* 43 */       return false;
/*    */     }
/* 45 */     if (!this.absolute.matchesSqr(($$6 * $$6 + $$7 * $$7 + $$8 * $$8))) {
/* 46 */       return false;
/*    */     }
/* 48 */     return true;
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\DistancePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */