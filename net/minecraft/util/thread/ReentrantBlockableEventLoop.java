/*    */ package net.minecraft.util.thread;
/*    */ 
/*    */ public abstract class ReentrantBlockableEventLoop<R extends Runnable> extends BlockableEventLoop<R> {
/*    */   private int reentrantCount;
/*    */   
/*    */   public ReentrantBlockableEventLoop(String $$0) {
/*  7 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean scheduleExecutables() {
/* 12 */     return (runningTask() || super.scheduleExecutables());
/*    */   }
/*    */   
/*    */   protected boolean runningTask() {
/* 16 */     return (this.reentrantCount != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRunTask(R $$0) {
/* 21 */     this.reentrantCount++;
/*    */     try {
/* 23 */       super.doRunTask($$0);
/*    */     } finally {
/* 25 */       this.reentrantCount--;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\ReentrantBlockableEventLoop.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */