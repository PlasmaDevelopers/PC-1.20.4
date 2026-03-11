/*    */ package net.minecraft.commands;
/*    */ 
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class FunctionInstantiationException extends Exception {
/*    */   private final Component messageComponent;
/*    */   
/*    */   public FunctionInstantiationException(Component $$0) {
/*  9 */     super($$0.getString());
/* 10 */     this.messageComponent = $$0;
/*    */   }
/*    */   
/*    */   public Component messageComponent() {
/* 14 */     return this.messageComponent;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\FunctionInstantiationException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */