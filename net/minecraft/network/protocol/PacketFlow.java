/*   */ package net.minecraft.network.protocol;
/*   */ 
/*   */ public enum PacketFlow {
/* 4 */   SERVERBOUND,
/* 5 */   CLIENTBOUND;
/*   */ 
/*   */   
/*   */   public PacketFlow getOpposite() {
/* 9 */     return (this == CLIENTBOUND) ? SERVERBOUND : CLIENTBOUND;
/*   */   }
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\PacketFlow.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */