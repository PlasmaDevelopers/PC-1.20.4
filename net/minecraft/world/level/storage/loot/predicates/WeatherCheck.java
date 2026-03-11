/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public final class WeatherCheck extends Record implements LootItemCondition {
/*    */   private final Optional<Boolean> isRaining;
/*    */   private final Optional<Boolean> isThundering;
/*    */   public static final Codec<WeatherCheck> CODEC;
/*    */   
/* 11 */   public WeatherCheck(Optional<Boolean> $$0, Optional<Boolean> $$1) { this.isRaining = $$0; this.isThundering = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/WeatherCheck;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/WeatherCheck; } public Optional<Boolean> isRaining() { return this.isRaining; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/WeatherCheck;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/WeatherCheck; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/WeatherCheck;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/WeatherCheck;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Boolean> isThundering() { return this.isThundering; }
/*    */ 
/*    */   
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "raining").forGetter(WeatherCheck::isRaining), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "thundering").forGetter(WeatherCheck::isThundering)).apply((Applicative)$$0, WeatherCheck::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 22 */     return LootItemConditions.WEATHER_CHECK;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 27 */     ServerLevel $$1 = $$0.getLevel();
/*    */     
/* 29 */     if (this.isRaining.isPresent() && ((Boolean)this.isRaining.get()).booleanValue() != $$1.isRaining()) {
/* 30 */       return false;
/*    */     }
/*    */     
/* 33 */     if (this.isThundering.isPresent() && ((Boolean)this.isThundering.get()).booleanValue() != $$1.isThundering()) {
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   public static class Builder implements LootItemCondition.Builder {
/* 41 */     private Optional<Boolean> isRaining = Optional.empty();
/* 42 */     private Optional<Boolean> isThundering = Optional.empty();
/*    */     
/*    */     public Builder setRaining(boolean $$0) {
/* 45 */       this.isRaining = Optional.of(Boolean.valueOf($$0));
/* 46 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setThundering(boolean $$0) {
/* 50 */       this.isThundering = Optional.of(Boolean.valueOf($$0));
/* 51 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public WeatherCheck build() {
/* 56 */       return new WeatherCheck(this.isRaining, this.isThundering);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder weather() {
/* 61 */     return new Builder();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\WeatherCheck.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */