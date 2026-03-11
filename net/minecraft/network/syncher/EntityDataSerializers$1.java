/*    */ package net.minecraft.network.syncher;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements EntityDataSerializer<ItemStack>
/*    */ {
/*    */   public void write(FriendlyByteBuf $$0, ItemStack $$1) {
/* 52 */     $$0.writeItem($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack read(FriendlyByteBuf $$0) {
/* 57 */     return $$0.readItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack copy(ItemStack $$0) {
/* 62 */     return $$0.copy();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\EntityDataSerializers$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */