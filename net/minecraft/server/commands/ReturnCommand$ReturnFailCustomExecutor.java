/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.context.ContextChain;
/*    */ import net.minecraft.commands.ExecutionCommandSource;
/*    */ import net.minecraft.commands.execution.ChainModifiers;
/*    */ import net.minecraft.commands.execution.CustomCommandExecutor;
/*    */ import net.minecraft.commands.execution.ExecutionControl;
/*    */ import net.minecraft.commands.execution.Frame;
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
/*    */ class ReturnFailCustomExecutor<T extends ExecutionCommandSource<T>>
/*    */   implements CustomCommandExecutor.CommandAdapter<T>
/*    */ {
/*    */   public void run(T $$0, ContextChain<T> $$1, ChainModifiers $$2, ExecutionControl<T> $$3) {
/* 57 */     $$0.callback().onFailure();
/* 58 */     Frame $$4 = $$3.currentFrame();
/* 59 */     $$4.returnFailure();
/* 60 */     $$4.discard();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ReturnCommand$ReturnFailCustomExecutor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */