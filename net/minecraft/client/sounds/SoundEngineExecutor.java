/*    */ package net.minecraft.client.sounds;
/*    */ 
/*    */ import java.util.concurrent.locks.LockSupport;
/*    */ import net.minecraft.util.thread.BlockableEventLoop;
/*    */ 
/*    */ public class SoundEngineExecutor
/*    */   extends BlockableEventLoop<Runnable>
/*    */ {
/*    */   private Thread thread;
/*    */   private volatile boolean shutdown;
/*    */   
/*    */   public SoundEngineExecutor() {
/* 13 */     super("Sound executor");
/* 14 */     this.thread = createThread();
/*    */   }
/*    */   
/*    */   private Thread createThread() {
/* 18 */     Thread $$0 = new Thread(this::run);
/* 19 */     $$0.setDaemon(true);
/* 20 */     $$0.setName("Sound engine");
/* 21 */     $$0.start();
/* 22 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Runnable wrapRunnable(Runnable $$0) {
/* 27 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldRun(Runnable $$0) {
/* 32 */     return !this.shutdown;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Thread getRunningThread() {
/* 37 */     return this.thread;
/*    */   }
/*    */   
/*    */   private void run() {
/* 41 */     while (!this.shutdown) {
/* 42 */       managedBlock(() -> this.shutdown);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void waitForTasks() {
/* 49 */     LockSupport.park("waiting for tasks");
/*    */   }
/*    */   
/*    */   public void flush() {
/* 53 */     this.shutdown = true;
/* 54 */     this.thread.interrupt();
/*    */     try {
/* 56 */       this.thread.join();
/* 57 */     } catch (InterruptedException $$0) {
/* 58 */       Thread.currentThread().interrupt();
/*    */     } 
/* 60 */     dropAllTasks();
/* 61 */     this.shutdown = false;
/* 62 */     this.thread = createThread();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\SoundEngineExecutor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */