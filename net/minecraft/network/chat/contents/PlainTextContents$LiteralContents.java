/*    */ package net.minecraft.network.chat.contents;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.network.chat.Style;
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
/*    */ public final class LiteralContents
/*    */   extends Record
/*    */   implements PlainTextContents
/*    */ {
/*    */   private final String text;
/*    */   
/*    */   public LiteralContents(String $$0) {
/* 36 */     this.text = $$0; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 36 */     //   0	7	0	this	Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents; } public String text() { return this.text; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   } public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 39 */     return $$0.accept(this.text);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 44 */     return $$0.accept($$1, this.text);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return "literal{" + this.text + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\PlainTextContents$LiteralContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */