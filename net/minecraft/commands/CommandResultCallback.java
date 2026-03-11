/*    */ package net.minecraft.commands;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface CommandResultCallback {
/*  5 */   public static final CommandResultCallback EMPTY = new CommandResultCallback()
/*    */     {
/*    */       public void onResult(boolean $$0, int $$1) {}
/*    */ 
/*    */ 
/*    */       
/*    */       public String toString() {
/* 12 */         return "<empty>";
/*    */       }
/*    */     };
/*    */   
/*    */   void onResult(boolean paramBoolean, int paramInt);
/*    */   
/*    */   default void onSuccess(int $$0) {
/* 19 */     onResult(true, $$0);
/*    */   }
/*    */   
/*    */   default void onFailure() {
/* 23 */     onResult(false, 0);
/*    */   }
/*    */   
/*    */   static CommandResultCallback chain(CommandResultCallback $$0, CommandResultCallback $$1) {
/* 27 */     if ($$0 == EMPTY) {
/* 28 */       return $$1;
/*    */     }
/*    */     
/* 31 */     if ($$1 == EMPTY) {
/* 32 */       return $$0;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 37 */     return ($$2, $$3) -> {
/*    */         $$0.onResult($$2, $$3);
/*    */         $$1.onResult($$2, $$3);
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\CommandResultCallback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */