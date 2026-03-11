/*    */ package net.minecraft.commands.execution.tasks;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.commands.CommandResultCallback;
/*    */ import net.minecraft.commands.ExecutionCommandSource;
/*    */ import net.minecraft.commands.execution.CommandQueueEntry;
/*    */ import net.minecraft.commands.execution.ExecutionContext;
/*    */ import net.minecraft.commands.execution.Frame;
/*    */ import net.minecraft.commands.execution.TraceCallbacks;
/*    */ import net.minecraft.commands.execution.UnboundEntryAction;
/*    */ import net.minecraft.commands.functions.InstantiatedFunction;
/*    */ 
/*    */ public class CallFunction<T extends ExecutionCommandSource<T>>
/*    */   implements UnboundEntryAction<T> {
/*    */   private final InstantiatedFunction<T> function;
/*    */   private final CommandResultCallback resultCallback;
/*    */   private final boolean returnParentFrame;
/*    */   
/*    */   public CallFunction(InstantiatedFunction<T> $$0, CommandResultCallback $$1, boolean $$2) {
/* 20 */     this.function = $$0;
/* 21 */     this.resultCallback = $$1;
/* 22 */     this.returnParentFrame = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(T $$0, ExecutionContext<T> $$1, Frame $$2) {
/* 27 */     $$1.incrementCost();
/*    */     
/* 29 */     List<UnboundEntryAction<T>> $$3 = this.function.entries();
/* 30 */     TraceCallbacks $$4 = $$1.tracer();
/* 31 */     if ($$4 != null) {
/* 32 */       $$4.onCall($$2.depth(), this.function.id(), this.function.entries().size());
/*    */     }
/*    */     
/* 35 */     int $$5 = $$2.depth() + 1;
/*    */ 
/*    */     
/* 38 */     Frame.FrameControl $$6 = this.returnParentFrame ? $$2.frameControl() : $$1.frameControlForDepth($$5);
/* 39 */     Frame $$7 = new Frame($$5, this.resultCallback, $$6);
/* 40 */     ContinuationTask.schedule($$1, $$7, $$3, ($$1, $$2) -> new CommandQueueEntry($$1, $$2.bind($$0)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\tasks\CallFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */