/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.context.ContextChain;
/*    */ import java.util.List;
/*    */ import net.minecraft.commands.ExecutionCommandSource;
/*    */ import net.minecraft.commands.execution.ChainModifiers;
/*    */ import net.minecraft.commands.execution.CustomModifierExecutor;
/*    */ import net.minecraft.commands.execution.EntryAction;
/*    */ import net.minecraft.commands.execution.ExecutionControl;
/*    */ import net.minecraft.commands.execution.tasks.BuildContexts;
/*    */ import net.minecraft.commands.execution.tasks.FallthroughTask;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ReturnFromCommandCustomModifier<T extends ExecutionCommandSource<T>>
/*    */   implements CustomModifierExecutor.ModifierAdapter<T>
/*    */ {
/*    */   public void apply(T $$0, List<T> $$1, ContextChain<T> $$2, ChainModifiers $$3, ExecutionControl<T> $$4) {
/* 67 */     if ($$1.isEmpty()) {
/*    */ 
/*    */ 
/*    */       
/* 71 */       if ($$3.isReturn()) {
/* 72 */         $$4.queueNext(FallthroughTask.instance());
/*    */       }
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 78 */     $$4.currentFrame().discard();
/*    */     
/* 80 */     ContextChain<T> $$5 = $$2.nextStage();
/* 81 */     String $$6 = $$5.getTopContext().getInput();
/*    */     
/* 83 */     $$4.queueNext((EntryAction)new BuildContexts.Continuation($$6, $$5, $$3.setReturn(), (ExecutionCommandSource)$$0, $$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ReturnCommand$ReturnFromCommandCustomModifier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */