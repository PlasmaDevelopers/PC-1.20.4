/*    */ package net.minecraft.commands.execution;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public interface ExecutionControl<T>
/*    */ {
/*    */   void queueNext(EntryAction<T> paramEntryAction);
/*    */   
/*    */   void tracer(@Nullable TraceCallbacks paramTraceCallbacks);
/*    */   
/*    */   @Nullable
/*    */   TraceCallbacks tracer();
/*    */   
/*    */   Frame currentFrame();
/*    */   
/*    */   static <T extends net.minecraft.commands.ExecutionCommandSource<T>> ExecutionControl<T> create(final ExecutionContext<T> context, final Frame frame) {
/* 18 */     return new ExecutionControl<T>()
/*    */       {
/*    */         public void queueNext(EntryAction<T> $$0) {
/* 21 */           context.queueNext(new CommandQueueEntry<>(frame, $$0));
/*    */         }
/*    */ 
/*    */         
/*    */         public void tracer(@Nullable TraceCallbacks $$0) {
/* 26 */           context.tracer($$0);
/*    */         }
/*    */ 
/*    */         
/*    */         @Nullable
/*    */         public TraceCallbacks tracer() {
/* 32 */           return context.tracer();
/*    */         }
/*    */ 
/*    */         
/*    */         public Frame currentFrame() {
/* 37 */           return frame;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\ExecutionControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */