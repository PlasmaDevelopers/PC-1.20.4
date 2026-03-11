/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ 
/*    */ public interface ReloadInstance {
/*    */   CompletableFuture<?> done();
/*    */   
/*    */   float getActualProgress();
/*    */   
/*    */   default boolean isDone() {
/* 11 */     return done().isDone();
/*    */   }
/*    */   
/*    */   default void checkExceptions() {
/* 15 */     CompletableFuture<?> $$0 = done();
/* 16 */     if ($$0.isCompletedExceptionally())
/* 17 */       $$0.join(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ReloadInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */