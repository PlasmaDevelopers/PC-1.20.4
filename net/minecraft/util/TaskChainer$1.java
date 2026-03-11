/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements TaskChainer
/*    */ {
/*    */   public <T> void append(CompletableFuture<T> $$0, Consumer<T> $$1) {
/* 18 */     $$0.thenAcceptAsync($$1, executor).exceptionally($$0 -> {
/*    */           LOGGER.error("Task failed", $$0);
/*    */           return null;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\TaskChainer$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */