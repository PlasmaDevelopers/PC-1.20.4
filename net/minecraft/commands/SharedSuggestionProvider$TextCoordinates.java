/*    */ package net.minecraft.commands;
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
/*    */ public class TextCoordinates
/*    */ {
/* 30 */   public static final TextCoordinates DEFAULT_LOCAL = new TextCoordinates("^", "^", "^");
/*    */   
/* 32 */   public static final TextCoordinates DEFAULT_GLOBAL = new TextCoordinates("~", "~", "~");
/*    */   
/*    */   public final String x;
/*    */   
/*    */   public final String y;
/*    */   
/*    */   public final String z;
/*    */   
/*    */   public TextCoordinates(String $$0, String $$1, String $$2) {
/* 41 */     this.x = $$0;
/* 42 */     this.y = $$1;
/* 43 */     this.z = $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\SharedSuggestionProvider$TextCoordinates.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */