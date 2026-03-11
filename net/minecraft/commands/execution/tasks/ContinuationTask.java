/*    */ package net.minecraft.commands.execution.tasks;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.commands.execution.CommandQueueEntry;
/*    */ import net.minecraft.commands.execution.EntryAction;
/*    */ import net.minecraft.commands.execution.ExecutionContext;
/*    */ import net.minecraft.commands.execution.Frame;
/*    */ 
/*    */ public class ContinuationTask<T, P>
/*    */   implements EntryAction<T> {
/*    */   private final TaskProvider<T, P> taskFactory;
/*    */   private final List<P> arguments;
/*    */   private final CommandQueueEntry<T> selfEntry;
/*    */   private int index;
/*    */   
/*    */   private ContinuationTask(TaskProvider<T, P> $$0, List<P> $$1, Frame $$2) {
/* 17 */     this.taskFactory = $$0;
/* 18 */     this.arguments = $$1;
/* 19 */     this.selfEntry = new CommandQueueEntry($$2, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ExecutionContext<T> $$0, Frame $$1) {
/* 24 */     P $$2 = this.arguments.get(this.index);
/* 25 */     $$0.queueNext(this.taskFactory.create($$1, $$2));
/* 26 */     if (++this.index < this.arguments.size()) {
/* 27 */       $$0.queueNext(this.selfEntry);
/*    */     }
/*    */   }
/*    */   
/*    */   public static <T, P> void schedule(ExecutionContext<T> $$0, Frame $$1, List<P> $$2, TaskProvider<T, P> $$3) {
/* 32 */     int $$4 = $$2.size();
/* 33 */     switch ($$4) { case 0: return;
/*    */       case 1:
/* 35 */         $$0.queueNext($$3.create($$1, $$2.get(0)));
/*    */ 
/*    */       
/*    */       case 2:
/* 39 */         $$0.queueNext($$3.create($$1, $$2.get(0)));
/* 40 */         $$0.queueNext($$3.create($$1, $$2.get(1))); }
/*    */     
/* 42 */     $$0.queueNext((new ContinuationTask($$3, $$2, $$1)).selfEntry);
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface TaskProvider<T, P> {
/*    */     CommandQueueEntry<T> create(Frame param1Frame, P param1P);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\tasks\ContinuationTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */