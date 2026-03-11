/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.Hash;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenCustomHashSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ 
/*    */ public class ItemStackLinkedSet
/*    */ {
/*    */   static int hashStackAndTag(@Nullable ItemStack $$0) {
/* 12 */     if ($$0 != null) {
/* 13 */       CompoundTag $$1 = $$0.getTag();
/* 14 */       int $$2 = 31 + $$0.getItem().hashCode();
/* 15 */       return 31 * $$2 + (($$1 == null) ? 0 : $$1.hashCode());
/*    */     } 
/* 17 */     return 0;
/*    */   }
/*    */   
/* 20 */   private static final Hash.Strategy<? super ItemStack> TYPE_AND_TAG = new Hash.Strategy<ItemStack>()
/*    */     {
/*    */       public int hashCode(@Nullable ItemStack $$0) {
/* 23 */         return ItemStackLinkedSet.hashStackAndTag($$0);
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean equals(@Nullable ItemStack $$0, @Nullable ItemStack $$1) {
/* 28 */         return ($$0 == $$1 || ($$0 != null && $$1 != null && $$0.isEmpty() == $$1.isEmpty() && ItemStack.isSameItemSameTags($$0, $$1)));
/*    */       }
/*    */     };
/*    */   
/*    */   public static Set<ItemStack> createTypeAndTagSet() {
/* 33 */     return (Set<ItemStack>)new ObjectLinkedOpenCustomHashSet(TYPE_AND_TAG);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ItemStackLinkedSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */