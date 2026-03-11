/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.Hash;
/*    */ import javax.annotation.Nullable;
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
/*    */   implements Hash.Strategy<ItemStack>
/*    */ {
/*    */   public int hashCode(@Nullable ItemStack $$0) {
/* 23 */     return ItemStackLinkedSet.hashStackAndTag($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable ItemStack $$0, @Nullable ItemStack $$1) {
/* 28 */     return ($$0 == $$1 || ($$0 != null && $$1 != null && $$0.isEmpty() == $$1.isEmpty() && ItemStack.isSameItemSameTags($$0, $$1)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ItemStackLinkedSet$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */