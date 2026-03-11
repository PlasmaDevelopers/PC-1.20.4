/*    */ package net.minecraft.commands;
/*    */ 
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public interface CommandSource {
/*  6 */   public static final CommandSource NULL = new CommandSource()
/*    */     {
/*    */       public void sendSystemMessage(Component $$0) {}
/*    */ 
/*    */ 
/*    */       
/*    */       public boolean acceptsSuccess() {
/* 13 */         return false;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean acceptsFailure() {
/* 18 */         return false;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean shouldInformAdmins() {
/* 23 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   void sendSystemMessage(Component paramComponent);
/*    */   
/*    */   boolean acceptsSuccess();
/*    */   
/*    */   boolean acceptsFailure();
/*    */   
/*    */   boolean shouldInformAdmins();
/*    */   
/*    */   default boolean alwaysAccepts() {
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\CommandSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */