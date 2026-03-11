/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.LootContextUser;
/*    */ 
/*    */ public interface LootItemFunction
/*    */   extends LootContextUser, BiFunction<ItemStack, LootContext, ItemStack> {
/*    */   LootItemFunctionType getType();
/*    */   
/*    */   static Consumer<ItemStack> decorate(BiFunction<ItemStack, LootContext, ItemStack> $$0, Consumer<ItemStack> $$1, LootContext $$2) {
/* 14 */     return $$3 -> $$0.accept($$1.apply($$3, $$2));
/*    */   }
/*    */   
/*    */   public static interface Builder {
/*    */     LootItemFunction build();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\LootItemFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */