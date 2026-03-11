/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ public final class LootItemRandomChanceCondition extends Record implements LootItemCondition {
/*    */   private final float probability;
/*    */   public static final Codec<LootItemRandomChanceCondition> CODEC;
/*    */   
/*  7 */   public LootItemRandomChanceCondition(float $$0) { this.probability = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition; } public float probability() { return this.probability; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceCondition;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("chance").forGetter(LootItemRandomChanceCondition::probability)).apply((Applicative)$$0, LootItemRandomChanceCondition::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 16 */     return LootItemConditions.RANDOM_CHANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 21 */     return ($$0.getRandom().nextFloat() < this.probability);
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder randomChance(float $$0) {
/* 25 */     return () -> new LootItemRandomChanceCondition($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\LootItemRandomChanceCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */