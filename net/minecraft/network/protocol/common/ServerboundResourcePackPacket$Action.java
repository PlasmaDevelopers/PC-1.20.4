/*    */ package net.minecraft.network.protocol.common;
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
/*    */ public enum Action
/*    */ {
/* 31 */   SUCCESSFULLY_LOADED,
/* 32 */   DECLINED,
/* 33 */   FAILED_DOWNLOAD,
/* 34 */   ACCEPTED,
/* 35 */   DOWNLOADED,
/* 36 */   INVALID_URL,
/* 37 */   FAILED_RELOAD,
/* 38 */   DISCARDED;
/*    */ 
/*    */   
/*    */   public boolean isTerminal() {
/* 42 */     return (this != ACCEPTED && this != DOWNLOADED);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ServerboundResourcePackPacket$Action.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */