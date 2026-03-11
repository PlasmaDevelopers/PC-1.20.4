/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Arrays;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import net.minecraft.world.level.storage.loot.predicates.MatchTool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TriggerInstance
/*    */   extends Record
/*    */   implements SimpleCriterionTrigger.SimpleInstance
/*    */ {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<ContextAwarePredicate> location;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/ItemUsedOnLocationTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #46	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/ItemUsedOnLocationTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/ItemUsedOnLocationTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #46	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/ItemUsedOnLocationTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/ItemUsedOnLocationTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #46	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/ItemUsedOnLocationTrigger$TriggerInstance;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ContextAwarePredicate> $$1) {
/* 46 */     this.player = $$0; this.location = $$1; } public Optional<ContextAwarePredicate> player() { return this.player; } public Optional<ContextAwarePredicate> location() { return this.location; }
/*    */ 
/*    */   
/*    */   static {
/* 50 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(ContextAwarePredicate.CODEC, "location").forGetter(TriggerInstance::location)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> placedBlock(Block $$0) {
/* 56 */     ContextAwarePredicate $$1 = ContextAwarePredicate.create(new LootItemCondition[] { LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).build() });
/* 57 */     return CriteriaTriggers.PLACED_BLOCK.createCriterion(new TriggerInstance(Optional.empty(), Optional.of($$1)));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> placedBlock(LootItemCondition.Builder... $$0) {
/* 61 */     ContextAwarePredicate $$1 = ContextAwarePredicate.create((LootItemCondition[])Arrays.<LootItemCondition.Builder>stream($$0).map(LootItemCondition.Builder::build).toArray($$0 -> new LootItemCondition[$$0]));
/* 62 */     return CriteriaTriggers.PLACED_BLOCK.createCriterion(new TriggerInstance(Optional.empty(), Optional.of($$1)));
/*    */   }
/*    */   
/*    */   private static TriggerInstance itemUsedOnLocation(LocationPredicate.Builder $$0, ItemPredicate.Builder $$1) {
/* 66 */     ContextAwarePredicate $$2 = ContextAwarePredicate.create(new LootItemCondition[] {
/* 67 */           LocationCheck.checkLocation($$0).build(), 
/* 68 */           MatchTool.toolMatches($$1).build()
/*    */         });
/*    */     
/* 71 */     return new TriggerInstance(Optional.empty(), Optional.of($$2));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> itemUsedOnBlock(LocationPredicate.Builder $$0, ItemPredicate.Builder $$1) {
/* 75 */     return CriteriaTriggers.ITEM_USED_ON_BLOCK.createCriterion(itemUsedOnLocation($$0, $$1));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> allayDropItemOnBlock(LocationPredicate.Builder $$0, ItemPredicate.Builder $$1) {
/* 79 */     return CriteriaTriggers.ALLAY_DROP_ITEM_ON_BLOCK.createCriterion(itemUsedOnLocation($$0, $$1));
/*    */   }
/*    */   
/*    */   public boolean matches(LootContext $$0) {
/* 83 */     return (this.location.isEmpty() || ((ContextAwarePredicate)this.location.get()).matches($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(CriterionValidator $$0) {
/* 88 */     super.validate($$0);
/* 89 */     this.location.ifPresent($$1 -> $$0.validate($$1, LootContextParamSets.ADVANCEMENT_LOCATION, ".location"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\ItemUsedOnLocationTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */