/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
/*    */ 
/*    */ public class ContextAwarePredicate {
/*    */   static {
/* 14 */     CODEC = LootItemConditions.CODEC.listOf().xmap(ContextAwarePredicate::new, $$0 -> $$0.conditions);
/*    */   }
/*    */   
/*    */   public static final Codec<ContextAwarePredicate> CODEC;
/*    */   
/*    */   ContextAwarePredicate(List<LootItemCondition> $$0) {
/* 20 */     this.conditions = $$0;
/* 21 */     this.compositePredicates = LootItemConditions.andConditions($$0);
/*    */   }
/*    */   private final List<LootItemCondition> conditions; private final Predicate<LootContext> compositePredicates;
/*    */   public static ContextAwarePredicate create(LootItemCondition... $$0) {
/* 25 */     return new ContextAwarePredicate(List.of($$0));
/*    */   }
/*    */   
/*    */   public boolean matches(LootContext $$0) {
/* 29 */     return this.compositePredicates.test($$0);
/*    */   }
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 33 */     for (int $$1 = 0; $$1 < this.conditions.size(); $$1++) {
/* 34 */       LootItemCondition $$2 = this.conditions.get($$1);
/* 35 */       $$2.validate($$0.forChild("[" + $$1 + "]"));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\ContextAwarePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */