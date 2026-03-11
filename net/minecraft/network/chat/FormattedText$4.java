/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Optional;
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
/*    */   implements FormattedText
/*    */ {
/*    */   public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 64 */     for (FormattedText $$1 : parts) {
/* 65 */       Optional<T> $$2 = $$1.visit($$0);
/* 66 */       if ($$2.isPresent()) {
/* 67 */         return $$2;
/*    */       }
/*    */     } 
/*    */     
/* 71 */     return Optional.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 76 */     for (FormattedText $$2 : parts) {
/* 77 */       Optional<T> $$3 = $$2.visit($$0, $$1);
/* 78 */       if ($$3.isPresent()) {
/* 79 */         return $$3;
/*    */       }
/*    */     } 
/*    */     
/* 83 */     return Optional.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\FormattedText$4.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */