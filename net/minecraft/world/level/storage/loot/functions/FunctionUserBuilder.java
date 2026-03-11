/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public interface FunctionUserBuilder<T extends FunctionUserBuilder<T>> {
/*    */   T apply(LootItemFunction.Builder paramBuilder);
/*    */   
/*    */   default <E> T apply(Iterable<E> $$0, Function<E, LootItemFunction.Builder> $$1) {
/* 10 */     T $$2 = unwrap();
/* 11 */     for (E $$3 : $$0) {
/* 12 */       $$2 = $$2.apply($$1.apply($$3));
/*    */     }
/* 14 */     return $$2;
/*    */   }
/*    */   
/*    */   default <E> T apply(E[] $$0, Function<E, LootItemFunction.Builder> $$1) {
/* 18 */     return apply(Arrays.asList($$0), $$1);
/*    */   }
/*    */   
/*    */   T unwrap();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\FunctionUserBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */