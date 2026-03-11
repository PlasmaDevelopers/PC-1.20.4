/*    */ package net.minecraft.client.resources.server;
/*    */ 
/*    */ import javax.annotation.Nullable;
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
/*    */ enum RemovalReason
/*    */ {
/* 79 */   DOWNLOAD_FAILED(PackLoadFeedback.FinalResult.DOWNLOAD_FAILED),
/* 80 */   ACTIVATION_FAILED(PackLoadFeedback.FinalResult.ACTIVATION_FAILED),
/* 81 */   DECLINED(PackLoadFeedback.FinalResult.DECLINED),
/* 82 */   DISCARDED(PackLoadFeedback.FinalResult.DISCARDED),
/* 83 */   SERVER_REMOVED(null),
/* 84 */   SERVER_REPLACED(null);
/*    */   
/*    */   @Nullable
/*    */   final PackLoadFeedback.FinalResult serverResponse;
/*    */ 
/*    */   
/*    */   RemovalReason(PackLoadFeedback.FinalResult $$0) {
/* 91 */     this.serverResponse = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\ServerPackManager$RemovalReason.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */