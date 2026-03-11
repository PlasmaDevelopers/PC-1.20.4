/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
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
/*    */ public interface BookAccess
/*    */ {
/*    */   int getPageCount();
/*    */   
/*    */   FormattedText getPageRaw(int paramInt);
/*    */   
/*    */   default FormattedText getPage(int $$0) {
/* 44 */     if ($$0 >= 0 && $$0 < getPageCount()) {
/* 45 */       return getPageRaw($$0);
/*    */     }
/* 47 */     return FormattedText.EMPTY;
/*    */   }
/*    */   
/*    */   static BookAccess fromItem(ItemStack $$0) {
/* 51 */     if ($$0.is(Items.WRITTEN_BOOK))
/* 52 */       return new BookViewScreen.WrittenBookAccess($$0); 
/* 53 */     if ($$0.is(Items.WRITABLE_BOOK)) {
/* 54 */       return new BookViewScreen.WritableBookAccess($$0);
/*    */     }
/* 56 */     return BookViewScreen.EMPTY_ACCESS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BookViewScreen$BookAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */