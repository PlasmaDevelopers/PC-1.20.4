/*    */ package net.minecraft.client;
/*    */ 
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.util.FormattedCharSink;
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
/*    */ 
/*    */ class WidthLimitedCharSink
/*    */   implements FormattedCharSink
/*    */ {
/*    */   private float maxWidth;
/*    */   private int position;
/*    */   
/*    */   public WidthLimitedCharSink(float $$0) {
/* 68 */     this.maxWidth = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accept(int $$0, Style $$1, int $$2) {
/* 73 */     this.maxWidth -= StringSplitter.this.widthProvider.getWidth($$2, $$1);
/* 74 */     if (this.maxWidth >= 0.0F) {
/* 75 */       this.position = $$0 + Character.charCount($$2);
/* 76 */       return true;
/*    */     } 
/* 78 */     return false;
/*    */   }
/*    */   
/*    */   public int getPosition() {
/* 82 */     return this.position;
/*    */   }
/*    */   
/*    */   public void resetPosition() {
/* 86 */     this.position = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\StringSplitter$WidthLimitedCharSink.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */