/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.advancements.critereon.ItemPredicate;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ 
/*    */ public final class MatchTool extends Record implements LootItemCondition {
/*    */   private final Optional<ItemPredicate> predicate;
/*    */   public static final Codec<MatchTool> CODEC;
/*    */   
/* 16 */   public MatchTool(Optional<ItemPredicate> $$0) { this.predicate = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/MatchTool;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 16 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/MatchTool; } public Optional<ItemPredicate> predicate() { return this.predicate; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/MatchTool;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/MatchTool; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/MatchTool;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/MatchTool;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } static {
/* 19 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "predicate").forGetter(MatchTool::predicate)).apply((Applicative)$$0, MatchTool::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 25 */     return LootItemConditions.MATCH_TOOL;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 30 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.TOOL);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 35 */     ItemStack $$1 = (ItemStack)$$0.getParamOrNull(LootContextParams.TOOL);
/* 36 */     return ($$1 != null && (this.predicate.isEmpty() || ((ItemPredicate)this.predicate.get()).matches($$1)));
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder toolMatches(ItemPredicate.Builder $$0) {
/* 40 */     return () -> new MatchTool(Optional.of($$0.build()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\MatchTool.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */