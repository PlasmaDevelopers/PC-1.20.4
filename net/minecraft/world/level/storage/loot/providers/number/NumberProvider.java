/*    */ package net.minecraft.world.level.storage.loot.providers.number;
/*    */ 
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.LootContextUser;
/*    */ 
/*    */ public interface NumberProvider extends LootContextUser {
/*    */   float getFloat(LootContext paramLootContext);
/*    */   
/*    */   default int getInt(LootContext $$0) {
/* 10 */     return Math.round(getFloat($$0));
/*    */   }
/*    */   
/*    */   LootNumberProviderType getType();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\number\NumberProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */