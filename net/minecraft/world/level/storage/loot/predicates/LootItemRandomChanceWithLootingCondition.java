/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ 
/*    */ public final class LootItemRandomChanceWithLootingCondition extends Record implements LootItemCondition {
/*    */   private final float percent;
/*    */   private final float lootingMultiplier;
/*    */   public static final Codec<LootItemRandomChanceWithLootingCondition> CODEC;
/*    */   
/* 15 */   public LootItemRandomChanceWithLootingCondition(float $$0, float $$1) { this.percent = $$0; this.lootingMultiplier = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceWithLootingCondition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 15 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceWithLootingCondition; } public float percent() { return this.percent; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceWithLootingCondition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceWithLootingCondition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceWithLootingCondition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemRandomChanceWithLootingCondition;
/* 15 */     //   0	8	1	$$0	Ljava/lang/Object; } public float lootingMultiplier() { return this.lootingMultiplier; }
/*    */ 
/*    */   
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("chance").forGetter(LootItemRandomChanceWithLootingCondition::percent), (App)Codec.FLOAT.fieldOf("looting_multiplier").forGetter(LootItemRandomChanceWithLootingCondition::lootingMultiplier)).apply((Applicative)$$0, LootItemRandomChanceWithLootingCondition::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 26 */     return LootItemConditions.RANDOM_CHANCE_WITH_LOOTING;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 31 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.KILLER_ENTITY);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 36 */     Entity $$1 = (Entity)$$0.getParamOrNull(LootContextParams.KILLER_ENTITY);
/*    */     
/* 38 */     int $$2 = 0;
/* 39 */     if ($$1 instanceof LivingEntity) {
/* 40 */       $$2 = EnchantmentHelper.getMobLooting((LivingEntity)$$1);
/*    */     }
/* 42 */     return ($$0.getRandom().nextFloat() < this.percent + $$2 * this.lootingMultiplier);
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder randomChanceAndLootingBoost(float $$0, float $$1) {
/* 46 */     return () -> new LootItemRandomChanceWithLootingCondition($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\LootItemRandomChanceWithLootingCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */