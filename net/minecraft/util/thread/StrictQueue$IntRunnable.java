/*    */ package net.minecraft.util.thread;
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
/*    */ public final class IntRunnable
/*    */   implements Runnable
/*    */ {
/*    */   final int priority;
/*    */   private final Runnable task;
/*    */   
/*    */   public IntRunnable(int $$0, Runnable $$1) {
/* 54 */     this.priority = $$0;
/* 55 */     this.task = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 60 */     this.task.run();
/*    */   }
/*    */   
/*    */   public int getPriority() {
/* 64 */     return this.priority;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\StrictQueue$IntRunnable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */