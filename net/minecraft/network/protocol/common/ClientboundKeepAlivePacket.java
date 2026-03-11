/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundKeepAlivePacket
/*    */   implements Packet<ClientCommonPacketListener> {
/*    */   public ClientboundKeepAlivePacket(long $$0) {
/* 10 */     this.id = $$0;
/*    */   }
/*    */   private final long id;
/*    */   public ClientboundKeepAlivePacket(FriendlyByteBuf $$0) {
/* 14 */     this.id = $$0.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeLong(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientCommonPacketListener $$0) {
/* 24 */     $$0.handleKeepAlive(this);
/*    */   }
/*    */   
/*    */   public long getId() {
/* 28 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ClientboundKeepAlivePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */