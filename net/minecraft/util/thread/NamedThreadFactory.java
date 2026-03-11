/*    */ package net.minecraft.util.thread;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class NamedThreadFactory
/*    */   implements ThreadFactory {
/* 10 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final ThreadGroup group;
/* 13 */   private final AtomicInteger threadNumber = new AtomicInteger(1);
/*    */   private final String namePrefix;
/*    */   
/*    */   public NamedThreadFactory(String $$0) {
/* 17 */     SecurityManager $$1 = System.getSecurityManager();
/* 18 */     this.group = ($$1 != null) ? $$1.getThreadGroup() : Thread.currentThread().getThreadGroup();
/* 19 */     this.namePrefix = $$0 + "-";
/*    */   }
/*    */ 
/*    */   
/*    */   public Thread newThread(Runnable $$0) {
/* 24 */     Thread $$1 = new Thread(this.group, $$0, this.namePrefix + this.namePrefix, 0L);
/* 25 */     $$1.setUncaughtExceptionHandler(($$1, $$2) -> {
/*    */           LOGGER.error("Caught exception in thread {} from {}", $$1, $$0);
/*    */           LOGGER.error("", $$2);
/*    */         });
/* 29 */     if ($$1.getPriority() != 5) {
/* 30 */       $$1.setPriority(5);
/*    */     }
/* 32 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\NamedThreadFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */