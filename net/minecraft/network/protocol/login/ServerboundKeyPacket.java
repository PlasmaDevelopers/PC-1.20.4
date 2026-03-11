/*    */ package net.minecraft.network.protocol.login;
/*    */ 
/*    */ import java.security.PrivateKey;
/*    */ import java.security.PublicKey;
/*    */ import java.util.Arrays;
/*    */ import javax.crypto.SecretKey;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.util.Crypt;
/*    */ import net.minecraft.util.CryptException;
/*    */ 
/*    */ public class ServerboundKeyPacket implements Packet<ServerLoginPacketListener> {
/*    */   private final byte[] keybytes;
/*    */   private final byte[] encryptedChallenge;
/*    */   
/*    */   public ServerboundKeyPacket(SecretKey $$0, PublicKey $$1, byte[] $$2) throws CryptException {
/* 18 */     this.keybytes = Crypt.encryptUsingKey($$1, $$0.getEncoded());
/* 19 */     this.encryptedChallenge = Crypt.encryptUsingKey($$1, $$2);
/*    */   }
/*    */   
/*    */   public ServerboundKeyPacket(FriendlyByteBuf $$0) {
/* 23 */     this.keybytes = $$0.readByteArray();
/* 24 */     this.encryptedChallenge = $$0.readByteArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 29 */     $$0.writeByteArray(this.keybytes);
/* 30 */     $$0.writeByteArray(this.encryptedChallenge);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerLoginPacketListener $$0) {
/* 35 */     $$0.handleKey(this);
/*    */   }
/*    */   
/*    */   public SecretKey getSecretKey(PrivateKey $$0) throws CryptException {
/* 39 */     return Crypt.decryptByteToSecretKey($$0, this.keybytes);
/*    */   }
/*    */   
/*    */   public boolean isChallengeValid(byte[] $$0, PrivateKey $$1) {
/*    */     try {
/* 44 */       return Arrays.equals($$0, Crypt.decryptUsingKey($$1, this.encryptedChallenge));
/* 45 */     } catch (CryptException $$2) {
/* 46 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ServerboundKeyPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */