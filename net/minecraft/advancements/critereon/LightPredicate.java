/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ 
/*    */ public final class LightPredicate extends Record {
/*    */   private final MinMaxBounds.Ints composite;
/*    */   public static final Codec<LightPredicate> CODEC;
/*    */   
/*  9 */   public LightPredicate(MinMaxBounds.Ints $$0) { this.composite = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/LightPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/LightPredicate; } public MinMaxBounds.Ints composite() { return this.composite; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/LightPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/LightPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/LightPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/LightPredicate;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "light", MinMaxBounds.Ints.ANY).forGetter(LightPredicate::composite)).apply((Applicative)$$0, LightPredicate::new)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(ServerLevel $$0, BlockPos $$1) {
/* 15 */     if (!$$0.isLoaded($$1)) {
/* 16 */       return false;
/*    */     }
/* 18 */     if (!this.composite.matches($$0.getMaxLocalRawBrightness($$1))) {
/* 19 */       return false;
/*    */     }
/* 21 */     return true;
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 25 */     private MinMaxBounds.Ints composite = MinMaxBounds.Ints.ANY;
/*    */     
/*    */     public static Builder light() {
/* 28 */       return new Builder();
/*    */     }
/*    */     
/*    */     public Builder setComposite(MinMaxBounds.Ints $$0) {
/* 32 */       this.composite = $$0;
/* 33 */       return this;
/*    */     }
/*    */     
/*    */     public LightPredicate build() {
/* 37 */       return new LightPredicate(this.composite);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\LightPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */