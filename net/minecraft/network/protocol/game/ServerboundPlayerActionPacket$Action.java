/*    */ package net.minecraft.network.protocol.game;
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
/*    */ public enum Action
/*    */ {
/* 62 */   START_DESTROY_BLOCK,
/* 63 */   ABORT_DESTROY_BLOCK,
/* 64 */   STOP_DESTROY_BLOCK,
/* 65 */   DROP_ALL_ITEMS,
/* 66 */   DROP_ITEM,
/* 67 */   RELEASE_USE_ITEM,
/* 68 */   SWAP_ITEM_WITH_OFFHAND;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundPlayerActionPacket$Action.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */