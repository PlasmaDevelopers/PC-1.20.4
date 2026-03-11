/*    */ package net.minecraft.commands.execution;
/*    */ 
/*    */ import com.mojang.brigadier.context.ContextChain;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.ExecutionCommandSource;
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
/*    */ public abstract class WithErrorHandling<T extends ExecutionCommandSource<T>>
/*    */   implements CustomCommandExecutor<T>
/*    */ {
/*    */   public final void run(T $$0, ContextChain<T> $$1, ChainModifiers $$2, ExecutionControl<T> $$3) {
/*    */     try {
/* 25 */       runGuarded($$0, $$1, $$2, $$3);
/* 26 */     } catch (CommandSyntaxException $$4) {
/* 27 */       onError($$4, $$0, $$2, $$3.tracer());
/* 28 */       $$0.callback().onFailure();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void onError(CommandSyntaxException $$0, T $$1, ChainModifiers $$2, @Nullable TraceCallbacks $$3) {
/* 33 */     $$1.handleError($$0, $$2.isForked(), $$3);
/*    */   }
/*    */   
/*    */   protected abstract void runGuarded(T paramT, ContextChain<T> paramContextChain, ChainModifiers paramChainModifiers, ExecutionControl<T> paramExecutionControl) throws CommandSyntaxException;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\CustomCommandExecutor$WithErrorHandling.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */