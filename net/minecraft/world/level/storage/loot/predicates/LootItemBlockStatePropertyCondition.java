/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.critereon.StatePropertiesPredicate;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public final class LootItemBlockStatePropertyCondition extends Record implements LootItemCondition {
/*    */   private final Holder<Block> block;
/*    */   private final Optional<StatePropertiesPredicate> properties;
/*    */   public static final Codec<LootItemBlockStatePropertyCondition> CODEC;
/*    */   
/* 19 */   public LootItemBlockStatePropertyCondition(Holder<Block> $$0, Optional<StatePropertiesPredicate> $$1) { this.block = $$0; this.properties = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemBlockStatePropertyCondition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 19 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemBlockStatePropertyCondition; } public Holder<Block> block() { return this.block; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemBlockStatePropertyCondition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemBlockStatePropertyCondition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemBlockStatePropertyCondition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemBlockStatePropertyCondition;
/* 19 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<StatePropertiesPredicate> properties() { return this.properties; }
/*    */ 
/*    */   
/*    */   static {
/* 23 */     CODEC = ExtraCodecs.validate(RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("block").forGetter(LootItemBlockStatePropertyCondition::block), (App)ExtraCodecs.strictOptionalField(StatePropertiesPredicate.CODEC, "properties").forGetter(LootItemBlockStatePropertyCondition::properties)).apply((Applicative)$$0, LootItemBlockStatePropertyCondition::new)), LootItemBlockStatePropertyCondition::validate);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static DataResult<LootItemBlockStatePropertyCondition> validate(LootItemBlockStatePropertyCondition $$0) {
/* 29 */     return $$0.properties()
/* 30 */       .flatMap($$1 -> $$1.checkState(((Block)$$0.block().value()).getStateDefinition()))
/* 31 */       .map($$1 -> DataResult.error(()))
/* 32 */       .orElse(DataResult.success($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 37 */     return LootItemConditions.BLOCK_STATE_PROPERTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 42 */     return Set.of(LootContextParams.BLOCK_STATE);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 47 */     BlockState $$1 = (BlockState)$$0.getParamOrNull(LootContextParams.BLOCK_STATE);
/* 48 */     return ($$1 != null && $$1.is(this.block) && (this.properties.isEmpty() || ((StatePropertiesPredicate)this.properties.get()).matches($$1)));
/*    */   }
/*    */   
/*    */   public static class Builder implements LootItemCondition.Builder {
/*    */     private final Holder<Block> block;
/* 53 */     private Optional<StatePropertiesPredicate> properties = Optional.empty();
/*    */     
/*    */     public Builder(Block $$0) {
/* 56 */       this.block = (Holder<Block>)$$0.builtInRegistryHolder();
/*    */     }
/*    */     
/*    */     public Builder setProperties(StatePropertiesPredicate.Builder $$0) {
/* 60 */       this.properties = $$0.build();
/* 61 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public LootItemCondition build() {
/* 66 */       return new LootItemBlockStatePropertyCondition(this.block, this.properties);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder hasBlockStateProperties(Block $$0) {
/* 71 */     return new Builder($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\LootItemBlockStatePropertyCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */