/*    */ package net.minecraft.network.chat.contents;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.network.chat.ComponentContents;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.network.chat.Style;
/*    */ 
/*    */ public interface PlainTextContents extends ComponentContents {
/*    */   public static final MapCodec<PlainTextContents> CODEC;
/*    */   
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("text").forGetter(PlainTextContents::text)).apply((Applicative)$$0, PlainTextContents::create));
/*    */   }
/*    */ 
/*    */   
/* 17 */   public static final ComponentContents.Type<PlainTextContents> TYPE = new ComponentContents.Type(CODEC, "text");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public static final PlainTextContents EMPTY = new PlainTextContents()
/*    */     {
/*    */       public String toString() {
/* 27 */         return "empty";
/*    */       }
/*    */ 
/*    */       
/*    */       public String text() {
/* 32 */         return "";
/*    */       }
/*    */     };
/*    */   public static final class LiteralContents extends Record implements PlainTextContents { private final String text;
/* 36 */     public LiteralContents(String $$0) { this.text = $$0; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #36	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 36 */       //   0	7	0	this	Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents; } public String text() { return this.text; } public final boolean equals(Object $$0) {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #36	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */     } public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 39 */       return $$0.accept(this.text);
/*    */     }
/*    */ 
/*    */     
/*    */     public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 44 */       return $$0.accept($$1, this.text);
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 49 */       return "literal{" + this.text + "}";
/*    */     } }
/*    */ 
/*    */   
/*    */   static PlainTextContents create(String $$0) {
/* 54 */     return $$0.isEmpty() ? EMPTY : new LiteralContents($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default ComponentContents.Type<?> type() {
/* 61 */     return TYPE;
/*    */   }
/*    */   
/*    */   String text();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\PlainTextContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */