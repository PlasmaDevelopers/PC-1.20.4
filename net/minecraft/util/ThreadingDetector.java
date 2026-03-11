/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.Semaphore;
/*    */ import java.util.concurrent.locks.Lock;
/*    */ import java.util.concurrent.locks.ReentrantLock;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.CrashReport;
/*    */ import net.minecraft.CrashReportCategory;
/*    */ import net.minecraft.ReportedException;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ThreadingDetector
/*    */ {
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final String name;
/*    */   
/* 23 */   private final Semaphore lock = new Semaphore(1);
/*    */   
/* 25 */   private final Lock stackTraceLock = new ReentrantLock();
/*    */   @Nullable
/*    */   private volatile Thread threadThatFailedToAcquire;
/*    */   @Nullable
/*    */   private volatile ReportedException fullException;
/*    */   
/*    */   public ThreadingDetector(String $$0) {
/* 32 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   public void checkAndLock() {
/* 36 */     boolean $$0 = false;
/*    */     try {
/* 38 */       this.stackTraceLock.lock();
/*    */ 
/*    */       
/* 41 */       if (!this.lock.tryAcquire()) {
/*    */         
/* 43 */         this.threadThatFailedToAcquire = Thread.currentThread();
/* 44 */         $$0 = true;
/* 45 */         this.stackTraceLock.unlock();
/*    */         
/*    */         try {
/* 48 */           this.lock.acquire();
/* 49 */         } catch (InterruptedException $$1) {
/* 50 */           Thread.currentThread().interrupt();
/*    */         } 
/*    */         
/* 53 */         throw this.fullException;
/*    */       } 
/*    */     } finally {
/* 56 */       if (!$$0) {
/* 57 */         this.stackTraceLock.unlock();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void checkAndUnlock() {
/*    */     try {
/* 64 */       this.stackTraceLock.lock();
/* 65 */       Thread $$0 = this.threadThatFailedToAcquire;
/* 66 */       if ($$0 != null) {
/*    */ 
/*    */         
/* 69 */         ReportedException $$1 = makeThreadingException(this.name, $$0);
/* 70 */         this.fullException = $$1;
/* 71 */         this.lock.release();
/* 72 */         throw $$1;
/*    */       } 
/* 74 */       this.lock.release();
/*    */     } finally {
/*    */       
/* 77 */       this.stackTraceLock.unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ReportedException makeThreadingException(String $$0, @Nullable Thread $$1) {
/* 82 */     String $$2 = Stream.<Thread>of(new Thread[] { Thread.currentThread(), $$1 }).filter(Objects::nonNull).map(ThreadingDetector::stackTrace).collect(Collectors.joining("\n"));
/* 83 */     String $$3 = "Accessing " + $$0 + " from multiple threads";
/* 84 */     CrashReport $$4 = new CrashReport($$3, new IllegalStateException($$3));
/* 85 */     CrashReportCategory $$5 = $$4.addCategory("Thread dumps");
/* 86 */     $$5.setDetail("Thread dumps", $$2);
/* 87 */     LOGGER.error("Thread dumps: \n" + $$2);
/* 88 */     return new ReportedException($$4);
/*    */   }
/*    */   
/*    */   private static String stackTrace(Thread $$0) {
/* 92 */     return $$0.getName() + ": \n\tat " + $$0.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ThreadingDetector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */