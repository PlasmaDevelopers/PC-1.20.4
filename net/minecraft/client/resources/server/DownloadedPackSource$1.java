/*    */ package net.minecraft.client.resources.server;
/*    */ 
/*    */ import java.util.UUID;
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
/*    */   implements PackLoadFeedback
/*    */ {
/*    */   public void reportUpdate(UUID $$0, PackLoadFeedback.Update $$1) {
/* 56 */     DownloadedPackSource.LOGGER.debug("Downloaded pack {} changed state to {}", $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reportFinalResult(UUID $$0, PackLoadFeedback.FinalResult $$1) {
/* 61 */     DownloadedPackSource.LOGGER.debug("Downloaded pack {} finished with state {}", $$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\DownloadedPackSource$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */