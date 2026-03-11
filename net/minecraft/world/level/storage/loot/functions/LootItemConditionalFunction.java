/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.Products;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
/*    */ 
/*    */ public abstract class LootItemConditionalFunction implements LootItemFunction {
/*    */   protected final List<LootItemCondition> predicates;
/*    */   private final Predicate<LootContext> compositePredicates;
/*    */   
/*    */   protected LootItemConditionalFunction(List<LootItemCondition> $$0) {
/* 23 */     this.predicates = $$0;
/* 24 */     this.compositePredicates = LootItemConditions.andConditions($$0);
/*    */   }
/*    */   
/*    */   protected static <T extends LootItemConditionalFunction> Products.P1<RecordCodecBuilder.Mu<T>, List<LootItemCondition>> commonFields(RecordCodecBuilder.Instance<T> $$0) {
/* 28 */     return $$0.group(
/* 29 */         (App)ExtraCodecs.strictOptionalField(LootItemConditions.CODEC.listOf(), "conditions", List.of()).forGetter($$0 -> $$0.predicates));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final ItemStack apply(ItemStack $$0, LootContext $$1) {
/* 35 */     return this.compositePredicates.test($$1) ? run($$0, $$1) : $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 42 */     super.validate($$0);
/*    */     
/* 44 */     for (int $$1 = 0; $$1 < this.predicates.size(); $$1++)
/* 45 */       ((LootItemCondition)this.predicates.get($$1)).validate($$0.forChild(".conditions[" + $$1 + "]")); 
/*    */   }
/*    */   
/*    */   public static abstract class Builder<T extends Builder<T>>
/*    */     implements LootItemFunction.Builder, ConditionUserBuilder<T> {
/* 50 */     private final ImmutableList.Builder<LootItemCondition> conditions = ImmutableList.builder();
/*    */ 
/*    */     
/*    */     public T when(LootItemCondition.Builder $$0) {
/* 54 */       this.conditions.add($$0.build());
/* 55 */       return getThis();
/*    */     }
/*    */ 
/*    */     
/*    */     public final T unwrap() {
/* 60 */       return getThis();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     protected List<LootItemCondition> getConditions() {
/* 66 */       return (List<LootItemCondition>)this.conditions.build();
/*    */     }
/*    */     
/*    */     protected abstract T getThis();
/*    */   }
/*    */   
/*    */   private static final class DummyBuilder extends Builder<DummyBuilder> {
/*    */     public DummyBuilder(Function<List<LootItemCondition>, LootItemFunction> $$0) {
/* 74 */       this.constructor = $$0;
/*    */     }
/*    */     private final Function<List<LootItemCondition>, LootItemFunction> constructor;
/*    */     
/*    */     protected DummyBuilder getThis() {
/* 79 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public LootItemFunction build() {
/* 84 */       return this.constructor.apply(getConditions());
/*    */     }
/*    */   }
/*    */   
/*    */   protected static Builder<?> simpleBuilder(Function<List<LootItemCondition>, LootItemFunction> $$0) {
/* 89 */     return new DummyBuilder($$0);
/*    */   }
/*    */   
/*    */   protected abstract ItemStack run(ItemStack paramItemStack, LootContext paramLootContext);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\LootItemConditionalFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */