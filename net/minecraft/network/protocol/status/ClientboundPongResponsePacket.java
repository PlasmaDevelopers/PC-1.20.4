/*    */ package net.minecraft.network.protocol.status;
/*    */ 
/*    */ import net.minecraft.network.ClientPongPacketListener;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundPongResponsePacket
/*    */   implements Packet<ClientPongPacketListener> {
/*    */   public ClientboundPongResponsePacket(long $$0) {
/* 11 */     this.time = $$0;
/*    */   }
/*    */   private final long time;
/*    */   public ClientboundPongResponsePacket(FriendlyByteBuf $$0) {
/* 15 */     this.time = $$0.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeLong(this.time);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientPongPacketListener $$0) {
/* 25 */     $$0.handlePongResponse(this);
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 29 */     return this.time;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\status\ClientboundPongResponsePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */