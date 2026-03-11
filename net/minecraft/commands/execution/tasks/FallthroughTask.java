/*    */ package net.minecraft.commands.execution.tasks;
/*    */ 
/*    */ import net.minecraft.commands.ExecutionCommandSource;
/*    */ import net.minecraft.commands.execution.EntryAction;
/*    */ import net.minecraft.commands.execution.ExecutionContext;
/*    */ import net.minecraft.commands.execution.Frame;
/*    */ 
/*    */ public class FallthroughTask<T extends ExecutionCommandSource<T>> implements EntryAction<T> {
/*  9 */   private static final FallthroughTask<? extends ExecutionCommandSource<?>> INSTANCE = new FallthroughTask();
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends ExecutionCommandSource<T>> EntryAction<T> instance() {
/* 14 */     return (EntryAction)INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ExecutionContext<T> $$0, Frame $$1) {
/* 19 */     $$1.returnFailure();
/*    */ 
/*    */     
/* 22 */     $$1.discard();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\tasks\FallthroughTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */