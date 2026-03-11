/*   */ package net.minecraft.commands.execution;
/*   */ 
/*   */ @FunctionalInterface
/*   */ public interface UnboundEntryAction<T> {
/*   */   void execute(T paramT, ExecutionContext<T> paramExecutionContext, Frame paramFrame);
/*   */   
/*   */   default EntryAction<T> bind(T $$0) {
/* 8 */     return ($$1, $$2) -> execute((T)$$0, $$1, $$2);
/*   */   }
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\UnboundEntryAction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */