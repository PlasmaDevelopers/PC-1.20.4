/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ 
/*    */ public final class InvertedLootItemCondition extends Record implements LootItemCondition {
/*    */   private final LootItemCondition term;
/*    */   public static final Codec<InvertedLootItemCondition> CODEC;
/*    */   
/* 11 */   public InvertedLootItemCondition(LootItemCondition $$0) { this.term = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition; } public LootItemCondition term() { return this.term; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/InvertedLootItemCondition;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)LootItemConditions.CODEC.fieldOf("term").forGetter(InvertedLootItemCondition::term)).apply((Applicative)$$0, InvertedLootItemCondition::new)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 18 */     return LootItemConditions.INVERTED;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 23 */     return !this.term.test($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 28 */     return this.term.getReferencedContextParams();
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 33 */     super.validate($$0);
/* 34 */     this.term.validate($$0);
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder invert(LootItemCondition.Builder $$0) {
/* 38 */     InvertedLootItemCondition $$1 = new InvertedLootItemCondition($$0.build());
/* 39 */     return () -> $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\InvertedLootItemCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */