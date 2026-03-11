/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
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
/*    */ class ReturnValueCustomExecutor<T extends ExecutionCommandSource<T>>
/*    */   implements CustomCommandExecutor.CommandAdapter<T>
/*    */ {
/*    */   public void run(T $$0, ContextChain<T> $$1, ChainModifiers $$2, ExecutionControl<T> $$3) {
/* 46 */     int $$4 = IntegerArgumentType.getInteger($$1.getTopContext(), "value");
/* 47 */     $$0.callback().onSuccess($$4);
/* 48 */     Frame $$5 = $$3.currentFrame();
/* 49 */     $$5.returnSuccess($$4);
/* 50 */     $$5.discard();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ReturnCommand$ReturnValueCustomExecutor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */