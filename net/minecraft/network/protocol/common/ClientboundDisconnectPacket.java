/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundDisconnectPacket
/*    */   implements Packet<ClientCommonPacketListener> {
/*    */   public ClientboundDisconnectPacket(Component $$0) {
/* 11 */     this.reason = $$0;
/*    */   }
/*    */   private final Component reason;
/*    */   public ClientboundDisconnectPacket(FriendlyByteBuf $$0) {
/* 15 */     this.reason = $$0.readComponentTrusted();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeComponent(this.reason);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientCommonPacketListener $$0) {
/* 25 */     $$0.handleDisconnect(this);
/*    */   }
/*    */   
/*    */   public Component getReason() {
/* 29 */     return this.reason;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ClientboundDisconnectPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */