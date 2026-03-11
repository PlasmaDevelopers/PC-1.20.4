/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ 
/*    */ public abstract class CompositeLootItemCondition implements LootItemCondition {
/*    */   protected final List<LootItemCondition> terms;
/*    */   private final Predicate<LootContext> composedPredicate;
/*    */   
/*    */   protected CompositeLootItemCondition(List<LootItemCondition> $$0, Predicate<LootContext> $$1) {
/* 19 */     this.terms = $$0;
/* 20 */     this.composedPredicate = $$1;
/*    */   }
/*    */   
/*    */   protected static <T extends CompositeLootItemCondition> Codec<T> createCodec(Function<List<LootItemCondition>, T> $$0) {
/* 24 */     return RecordCodecBuilder.create($$1 -> $$1.group((App)LootItemConditions.CODEC.listOf().fieldOf("terms").forGetter(())).apply((Applicative)$$1, $$0));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static <T extends CompositeLootItemCondition> Codec<T> createInlineCodec(Function<List<LootItemCondition>, T> $$0) {
/* 30 */     return LootItemConditions.CODEC.listOf().xmap($$0, $$0 -> $$0.terms);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean test(LootContext $$0) {
/* 35 */     return this.composedPredicate.test($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 40 */     super.validate($$0);
/*    */     
/* 42 */     for (int $$1 = 0; $$1 < this.terms.size(); $$1++)
/* 43 */       ((LootItemCondition)this.terms.get($$1)).validate($$0.forChild(".term[" + $$1 + "]")); 
/*    */   }
/*    */   
/*    */   public static abstract class Builder
/*    */     implements LootItemCondition.Builder {
/* 48 */     private final ImmutableList.Builder<LootItemCondition> terms = ImmutableList.builder();
/*    */     
/*    */     protected Builder(LootItemCondition.Builder... $$0) {
/* 51 */       for (LootItemCondition.Builder $$1 : $$0) {
/* 52 */         this.terms.add($$1.build());
/*    */       }
/*    */     }
/*    */     
/*    */     public void addTerm(LootItemCondition.Builder $$0) {
/* 57 */       this.terms.add($$0.build());
/*    */     }
/*    */ 
/*    */     
/*    */     public LootItemCondition build() {
/* 62 */       return create((List<LootItemCondition>)this.terms.build());
/*    */     }
/*    */     
/*    */     protected abstract LootItemCondition create(List<LootItemCondition> param1List);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\CompositeLootItemCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */