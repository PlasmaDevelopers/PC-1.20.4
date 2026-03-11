/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ public enum FullChunkStatus {
/*  4 */   INACCESSIBLE,
/*  5 */   FULL,
/*  6 */   BLOCK_TICKING,
/*  7 */   ENTITY_TICKING;
/*    */ 
/*    */   
/*    */   public boolean isOrAfter(FullChunkStatus $$0) {
/* 11 */     return (ordinal() >= $$0.ordinal());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\FullChunkStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */