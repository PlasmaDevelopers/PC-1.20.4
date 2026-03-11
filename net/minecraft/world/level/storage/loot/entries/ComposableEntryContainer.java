/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ interface ComposableEntryContainer
/*    */ {
/*    */   public static final ComposableEntryContainer ALWAYS_FALSE = ($$0, $$1) -> false;
/*    */   public static final ComposableEntryContainer ALWAYS_TRUE = ($$0, $$1) -> true;
/*    */   
/*    */   default ComposableEntryContainer and(ComposableEntryContainer $$0) {
/* 16 */     Objects.requireNonNull($$0);
/* 17 */     return ($$1, $$2) -> (expand($$1, $$2) && $$0.expand($$1, $$2));
/*    */   }
/*    */   
/*    */   default ComposableEntryContainer or(ComposableEntryContainer $$0) {
/* 21 */     Objects.requireNonNull($$0);
/* 22 */     return ($$1, $$2) -> (expand($$1, $$2) || $$0.expand($$1, $$2));
/*    */   }
/*    */   
/*    */   boolean expand(LootContext paramLootContext, Consumer<LootPoolEntry> paramConsumer);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\ComposableEntryContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */