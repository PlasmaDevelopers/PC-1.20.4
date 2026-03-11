/*    */ package net.minecraft.server.rcon.thread;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.DefaultUncaughtExceptionHandlerWithName;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public abstract class GenericThread
/*    */   implements Runnable {
/* 11 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 12 */   private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
/*    */   private static final int MAX_STOP_WAIT = 5;
/*    */   protected volatile boolean running;
/*    */   protected final String name;
/*    */   @Nullable
/*    */   protected Thread thread;
/*    */   
/*    */   protected GenericThread(String $$0) {
/* 20 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   public synchronized boolean start() {
/* 24 */     if (this.running) {
/* 25 */       return true;
/*    */     }
/* 27 */     this.running = true;
/* 28 */     this.thread = new Thread(this, this.name + " #" + this.name);
/* 29 */     this.thread.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandlerWithName(LOGGER));
/* 30 */     this.thread.start();
/* 31 */     LOGGER.info("Thread {} started", this.name);
/* 32 */     return true;
/*    */   }
/*    */   
/*    */   public synchronized void stop() {
/* 36 */     this.running = false;
/* 37 */     if (null == this.thread) {
/*    */       return;
/*    */     }
/* 40 */     int $$0 = 0;
/* 41 */     while (this.thread.isAlive()) {
/*    */       
/*    */       try {
/* 44 */         this.thread.join(1000L);
/* 45 */         $$0++;
/* 46 */         if ($$0 >= 5) {
/*    */ 
/*    */ 
/*    */           
/* 50 */           LOGGER.warn("Waited {} seconds attempting force stop!", Integer.valueOf($$0)); continue;
/* 51 */         }  if (this.thread.isAlive()) {
/* 52 */           LOGGER.warn("Thread {} ({}) failed to exit after {} second(s)", new Object[] { this, this.thread.getState(), Integer.valueOf($$0), new Exception("Stack:") });
/*    */           
/* 54 */           this.thread.interrupt();
/*    */         } 
/* 56 */       } catch (InterruptedException interruptedException) {}
/*    */     } 
/*    */ 
/*    */     
/* 60 */     LOGGER.info("Thread {} stopped", this.name);
/* 61 */     this.thread = null;
/*    */   }
/*    */   
/*    */   public boolean isRunning() {
/* 65 */     return this.running;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\rcon\thread\GenericThread.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */