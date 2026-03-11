/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.function.Consumer;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface TaskChainer
/*    */ {
/* 12 */   public static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   static TaskChainer immediate(final Executor executor) {
/* 15 */     return new TaskChainer()
/*    */       {
/*    */         public <T> void append(CompletableFuture<T> $$0, Consumer<T> $$1) {
/* 18 */           $$0.thenAcceptAsync($$1, executor).exceptionally($$0 -> {
/*    */                 LOGGER.error("Task failed", $$0);
/*    */                 return null;
/*    */               });
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   default void append(Runnable $$0) {
/* 27 */     append(CompletableFuture.completedFuture(null), $$1 -> $$0.run());
/*    */   }
/*    */   
/*    */   <T> void append(CompletableFuture<T> paramCompletableFuture, Consumer<T> paramConsumer);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\TaskChainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */