/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundPingPacket implements Packet<ClientCommonPacketListener> {
/*    */   private final int id;
/*    */   
/*    */   public ClientboundPingPacket(int $$0) {
/* 11 */     this.id = $$0;
/*    */   }
/*    */   
/*    */   public ClientboundPingPacket(FriendlyByteBuf $$0) {
/* 15 */     this.id = $$0.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeInt(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientCommonPacketListener $$0) {
/* 25 */     $$0.handlePing(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 29 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ClientboundPingPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */