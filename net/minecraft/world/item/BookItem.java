/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ public class BookItem extends Item {
/*    */   public BookItem(Item.Properties $$0) {
/*  5 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnchantable(ItemStack $$0) {
/* 10 */     return ($$0.getCount() == 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnchantmentValue() {
/* 15 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BookItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */