/*    */ package net.minecraft.network.protocol.login;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundLoginCompressionPacket
/*    */   implements Packet<ClientLoginPacketListener> {
/*    */   public ClientboundLoginCompressionPacket(int $$0) {
/* 10 */     this.compressionThreshold = $$0;
/*    */   }
/*    */   private final int compressionThreshold;
/*    */   public ClientboundLoginCompressionPacket(FriendlyByteBuf $$0) {
/* 14 */     this.compressionThreshold = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeVarInt(this.compressionThreshold);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientLoginPacketListener $$0) {
/* 24 */     $$0.handleCompression(this);
/*    */   }
/*    */   
/*    */   public int getCompressionThreshold() {
/* 28 */     return this.compressionThreshold;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ClientboundLoginCompressionPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */