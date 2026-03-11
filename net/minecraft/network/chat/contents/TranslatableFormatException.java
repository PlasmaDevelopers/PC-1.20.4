/*    */ package net.minecraft.network.chat.contents;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class TranslatableFormatException extends IllegalArgumentException {
/*    */   public TranslatableFormatException(TranslatableContents $$0, String $$1) {
/*  7 */     super(String.format(Locale.ROOT, "Error parsing: %s: %s", new Object[] { $$0, $$1 }));
/*    */   }
/*    */   
/*    */   public TranslatableFormatException(TranslatableContents $$0, int $$1) {
/* 11 */     super(String.format(Locale.ROOT, "Invalid index %d requested for %s", new Object[] { Integer.valueOf($$1), $$0 }));
/*    */   }
/*    */   
/*    */   public TranslatableFormatException(TranslatableContents $$0, Throwable $$1) {
/* 15 */     super(String.format(Locale.ROOT, "Error while parsing: %s", new Object[] { $$0 }), $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\TranslatableFormatException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */