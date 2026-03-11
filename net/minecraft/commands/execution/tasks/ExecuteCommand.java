/*    */ package net.minecraft.commands.execution.tasks;
/*    */ 
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.context.ContextChain;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.ExecutionCommandSource;
/*    */ import net.minecraft.commands.execution.ChainModifiers;
/*    */ import net.minecraft.commands.execution.ExecutionContext;
/*    */ import net.minecraft.commands.execution.Frame;
/*    */ import net.minecraft.commands.execution.TraceCallbacks;
/*    */ import net.minecraft.commands.execution.UnboundEntryAction;
/*    */ 
/*    */ public class ExecuteCommand<T extends ExecutionCommandSource<T>> implements UnboundEntryAction<T> {
/*    */   private final String commandInput;
/*    */   private final ChainModifiers modifiers;
/*    */   private final CommandContext<T> executionContext;
/*    */   
/*    */   public ExecuteCommand(String $$0, ChainModifiers $$1, CommandContext<T> $$2) {
/* 19 */     this.commandInput = $$0;
/* 20 */     this.modifiers = $$1;
/* 21 */     this.executionContext = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(T $$0, ExecutionContext<T> $$1, Frame $$2) {
/* 26 */     $$1.profiler().push(() -> "execute " + this.commandInput);
/*    */     try {
/* 28 */       $$1.incrementCost();
/* 29 */       int $$3 = ContextChain.runExecutable(this.executionContext, $$0, ExecutionCommandSource.resultConsumer(), this.modifiers.isForked());
/* 30 */       TraceCallbacks $$4 = $$1.tracer();
/* 31 */       if ($$4 != null) {
/* 32 */         $$4.onReturn($$2.depth(), this.commandInput, $$3);
/*    */       }
/* 34 */     } catch (CommandSyntaxException $$5) {
/* 35 */       $$0.handleError($$5, this.modifiers.isForked(), $$1.tracer());
/*    */     } finally {
/* 37 */       $$1.profiler().pop();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\tasks\ExecuteCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */