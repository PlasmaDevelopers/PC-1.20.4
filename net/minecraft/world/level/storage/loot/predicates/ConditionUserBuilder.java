/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public interface ConditionUserBuilder<T extends ConditionUserBuilder<T>> {
/*    */   T when(LootItemCondition.Builder paramBuilder);
/*    */   
/*    */   default <E> T when(Iterable<E> $$0, Function<E, LootItemCondition.Builder> $$1) {
/*  9 */     T $$2 = unwrap();
/* 10 */     for (E $$3 : $$0) {
/* 11 */       $$2 = $$2.when($$1.apply($$3));
/*    */     }
/* 13 */     return $$2;
/*    */   }
/*    */   
/*    */   T unwrap();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\ConditionUserBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */