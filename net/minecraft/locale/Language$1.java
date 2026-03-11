/*    */ package net.minecraft.locale;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ import net.minecraft.util.FormattedCharSink;
/*    */ import net.minecraft.util.StringDecomposer;
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
/*    */   extends Language
/*    */ {
/*    */   public String getOrDefault(String $$0, String $$1) {
/* 51 */     return (String)storage.getOrDefault($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean has(String $$0) {
/* 56 */     return storage.containsKey($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDefaultRightToLeft() {
/* 61 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FormattedCharSequence getVisualOrder(FormattedText $$0) {
/* 67 */     return $$1 -> $$0.visit((), Style.EMPTY).isPresent();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\locale\Language$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */