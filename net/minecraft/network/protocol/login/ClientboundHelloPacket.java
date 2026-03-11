/*    */ package net.minecraft.network.protocol.login;
/*    */ 
/*    */ import java.security.PublicKey;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.util.Crypt;
/*    */ import net.minecraft.util.CryptException;
/*    */ 
/*    */ public class ClientboundHelloPacket implements Packet<ClientLoginPacketListener> {
/*    */   private final String serverId;
/*    */   private final byte[] publicKey;
/*    */   private final byte[] challenge;
/*    */   
/*    */   public ClientboundHelloPacket(String $$0, byte[] $$1, byte[] $$2) {
/* 16 */     this.serverId = $$0;
/* 17 */     this.publicKey = $$1;
/* 18 */     this.challenge = $$2;
/*    */   }
/*    */   
/*    */   public ClientboundHelloPacket(FriendlyByteBuf $$0) {
/* 22 */     this.serverId = $$0.readUtf(20);
/* 23 */     this.publicKey = $$0.readByteArray();
/* 24 */     this.challenge = $$0.readByteArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 29 */     $$0.writeUtf(this.serverId);
/* 30 */     $$0.writeByteArray(this.publicKey);
/* 31 */     $$0.writeByteArray(this.challenge);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientLoginPacketListener $$0) {
/* 36 */     $$0.handleHello(this);
/*    */   }
/*    */   
/*    */   public String getServerId() {
/* 40 */     return this.serverId;
/*    */   }
/*    */   
/*    */   public PublicKey getPublicKey() throws CryptException {
/* 44 */     return Crypt.byteToPublicKey(this.publicKey);
/*    */   }
/*    */   
/*    */   public byte[] getChallenge() {
/* 48 */     return this.challenge;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ClientboundHelloPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */