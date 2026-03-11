/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.enchantment.Enchantment;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ 
/*    */ public final class BonusLevelTableCondition extends Record implements LootItemCondition {
/*    */   private final Holder<Enchantment> enchantment;
/*    */   private final List<Float> values;
/*    */   public static final Codec<BonusLevelTableCondition> CODEC;
/*    */   
/* 19 */   public BonusLevelTableCondition(Holder<Enchantment> $$0, List<Float> $$1) { this.enchantment = $$0; this.values = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/BonusLevelTableCondition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 19 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/BonusLevelTableCondition; } public Holder<Enchantment> enchantment() { return this.enchantment; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/BonusLevelTableCondition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/BonusLevelTableCondition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/BonusLevelTableCondition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/BonusLevelTableCondition;
/* 19 */     //   0	8	1	$$0	Ljava/lang/Object; } public List<Float> values() { return this.values; }
/*    */ 
/*    */   
/*    */   static {
/* 23 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ENCHANTMENT.holderByNameCodec().fieldOf("enchantment").forGetter(BonusLevelTableCondition::enchantment), (App)Codec.FLOAT.listOf().fieldOf("chances").forGetter(BonusLevelTableCondition::values)).apply((Applicative)$$0, BonusLevelTableCondition::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 30 */     return LootItemConditions.TABLE_BONUS;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 35 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.TOOL);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 40 */     ItemStack $$1 = (ItemStack)$$0.getParamOrNull(LootContextParams.TOOL);
/*    */     
/* 42 */     int $$2 = ($$1 != null) ? EnchantmentHelper.getItemEnchantmentLevel((Enchantment)this.enchantment.value(), $$1) : 0;
/* 43 */     float $$3 = ((Float)this.values.get(Math.min($$2, this.values.size() - 1))).floatValue();
/* 44 */     return ($$0.getRandom().nextFloat() < $$3);
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder bonusLevelFlatChance(Enchantment $$0, float... $$1) {
/* 48 */     List<Float> $$2 = new ArrayList<>($$1.length);
/* 49 */     for (float $$3 : $$1) {
/* 50 */       $$2.add(Float.valueOf($$3));
/*    */     }
/* 52 */     return () -> new BonusLevelTableCondition((Holder<Enchantment>)$$0.builtInRegistryHolder(), $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\BonusLevelTableCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */