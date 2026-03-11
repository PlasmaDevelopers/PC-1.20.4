/*    */ package net.minecraft.network.protocol.status;
/*    */ 
/*    */ import net.minecraft.network.ClientPongPacketListener;
/*    */ import net.minecraft.network.ClientboundPacketListener;
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ 
/*    */ public interface ClientStatusPacketListener
/*    */   extends ClientPongPacketListener, ClientboundPacketListener {
/*    */   default ConnectionProtocol protocol() {
/* 10 */     return ConnectionProtocol.STATUS;
/*    */   }
/*    */   
/*    */   void handleStatusResponse(ClientboundStatusResponsePacket paramClientboundStatusResponsePacket);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\status\ClientStatusPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */