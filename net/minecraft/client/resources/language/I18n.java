/*    */ package net.minecraft.client.resources.language;
/*    */ 
/*    */ import java.util.IllegalFormatException;
/*    */ import net.minecraft.locale.Language;
/*    */ 
/*    */ public class I18n
/*    */ {
/*  8 */   private static volatile Language language = Language.getInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void setLanguage(Language $$0) {
/* 15 */     language = $$0;
/*    */   }
/*    */   
/*    */   public static String get(String $$0, Object... $$1) {
/* 19 */     String $$2 = language.getOrDefault($$0);
/*    */     try {
/* 21 */       return String.format($$2, $$1);
/* 22 */     } catch (IllegalFormatException $$3) {
/* 23 */       return "Format error: " + $$2;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean exists(String $$0) {
/* 28 */     return language.has($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\language\I18n.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */