/*    */ package net.minecraft.client.resources.server;public interface PackLoadFeedback {
/*    */   void reportUpdate(UUID paramUUID, Update paramUpdate);
/*    */   
/*    */   void reportFinalResult(UUID paramUUID, FinalResult paramFinalResult);
/*    */   
/*    */   public enum Update {
/*  7 */     ACCEPTED,
/*  8 */     DOWNLOADED;
/*    */   }
/*    */   
/*    */   public enum FinalResult
/*    */   {
/* 13 */     DECLINED,
/*    */ 
/*    */     
/* 16 */     APPLIED,
/*    */ 
/*    */     
/* 19 */     DISCARDED,
/* 20 */     DOWNLOAD_FAILED,
/* 21 */     ACTIVATION_FAILED;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\PackLoadFeedback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */