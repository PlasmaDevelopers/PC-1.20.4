/*    */ package net.minecraft.world.level.storage.loot.providers.number;
/*    */ 
/*    */ 
/*    */ public final class ConstantValue extends Record implements NumberProvider {
/*    */   private final float value;
/*    */   
/*  7 */   public ConstantValue(float $$0) { this.value = $$0; } public static final Codec<ConstantValue> CODEC; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/providers/number/ConstantValue;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/ConstantValue; } public float value() { return this.value; } static {
/*  8 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("value").forGetter(ConstantValue::value)).apply((Applicative)$$0, ConstantValue::new));
/*    */   }
/*    */ 
/*    */   
/* 12 */   public static final Codec<ConstantValue> INLINE_CODEC = Codec.FLOAT.xmap(ConstantValue::new, ConstantValue::value);
/*    */ 
/*    */   
/*    */   public LootNumberProviderType getType() {
/* 16 */     return NumberProviders.CONSTANT;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat(LootContext $$0) {
/* 21 */     return this.value;
/*    */   }
/*    */   
/*    */   public static ConstantValue exactly(float $$0) {
/* 25 */     return new ConstantValue($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 31 */     if (this == $$0) {
/* 32 */       return true;
/*    */     }
/* 34 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 35 */       return false;
/*    */     }
/*    */     
/* 38 */     return (Float.compare(((ConstantValue)$$0).value, this.value) == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     return (this.value != 0.0F) ? Float.floatToIntBits(this.value) : 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\number\ConstantValue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */