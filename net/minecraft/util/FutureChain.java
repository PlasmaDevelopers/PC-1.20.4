/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.concurrent.CancellationException;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionException;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.function.Consumer;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class FutureChain
/*    */   implements TaskChainer, AutoCloseable {
/* 13 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 15 */   private CompletableFuture<?> head = CompletableFuture.completedFuture(null);
/*    */   
/*    */   private final Executor executor;
/*    */   private volatile boolean closed;
/*    */   
/*    */   public FutureChain(Executor $$0) {
/* 21 */     this.executor = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> void append(CompletableFuture<T> $$0, Consumer<T> $$1) {
/* 26 */     this
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 33 */       .head = this.head.thenCombine($$0, ($$0, $$1) -> $$1).thenAcceptAsync($$1 -> { if (!this.closed) $$0.accept($$1);  }this.executor).exceptionally($$0 -> {
/*    */           if ($$0 instanceof CompletionException) {
/*    */             CompletionException $$1 = (CompletionException)$$0;
/*    */             $$0 = $$1.getCause();
/*    */           } 
/*    */           if ($$0 instanceof CancellationException) {
/*    */             CancellationException $$2 = (CancellationException)$$0;
/*    */             throw $$2;
/*    */           } 
/*    */           LOGGER.error("Chain link failed, continuing to next one", $$0);
/*    */           return null;
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 49 */     this.closed = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\FutureChain.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */