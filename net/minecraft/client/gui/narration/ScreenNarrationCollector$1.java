/*    */ package net.minecraft.client.gui.narration;
/*    */ 
/*    */ import java.util.function.Consumer;
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
/*    */   implements Consumer<String>
/*    */ {
/*    */   private boolean firstEntry = true;
/*    */   
/*    */   public void accept(String $$0) {
/* 44 */     if (!this.firstEntry) {
/* 45 */       result.append(". ");
/*    */     }
/* 47 */     this.firstEntry = false;
/* 48 */     result.append($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\narration\ScreenNarrationCollector$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */