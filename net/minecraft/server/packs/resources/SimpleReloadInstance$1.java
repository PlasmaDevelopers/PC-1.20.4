/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import net.minecraft.util.Unit;
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
/*    */ class null
/*    */   implements PreparableReloadListener.PreparationBarrier
/*    */ {
/*    */   public <T> CompletableFuture<T> wait(T $$0) {
/* 48 */     mainThreadExecutor.execute(() -> {
/*    */           SimpleReloadInstance.this.preparingListeners.remove($$0);
/*    */           if (SimpleReloadInstance.this.preparingListeners.isEmpty()) {
/*    */             SimpleReloadInstance.this.allPreparations.complete(Unit.INSTANCE);
/*    */           }
/*    */         });
/* 54 */     return SimpleReloadInstance.this.allPreparations.thenCombine(previousTask, ($$1, $$2) -> $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\SimpleReloadInstance$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */