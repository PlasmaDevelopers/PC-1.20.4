/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundServerDataPacket implements Packet<ClientGamePacketListener> {
/*    */   private final Component motd;
/*    */   private final Optional<byte[]> iconBytes;
/*    */   private final boolean enforcesSecureChat;
/*    */   
/*    */   public ClientboundServerDataPacket(Component $$0, Optional<byte[]> $$1, boolean $$2) {
/* 15 */     this.motd = $$0;
/* 16 */     this.iconBytes = $$1;
/* 17 */     this.enforcesSecureChat = $$2;
/*    */   }
/*    */   
/*    */   public ClientboundServerDataPacket(FriendlyByteBuf $$0) {
/* 21 */     this.motd = $$0.readComponentTrusted();
/* 22 */     this.iconBytes = $$0.readOptional(FriendlyByteBuf::readByteArray);
/* 23 */     this.enforcesSecureChat = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 28 */     $$0.writeComponent(this.motd);
/* 29 */     $$0.writeOptional(this.iconBytes, FriendlyByteBuf::writeByteArray);
/* 30 */     $$0.writeBoolean(this.enforcesSecureChat);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 35 */     $$0.handleServerData(this);
/*    */   }
/*    */   
/*    */   public Component getMotd() {
/* 39 */     return this.motd;
/*    */   }
/*    */   
/*    */   public Optional<byte[]> getIconBytes() {
/* 43 */     return this.iconBytes;
/*    */   }
/*    */   
/*    */   public boolean enforcesSecureChat() {
/* 47 */     return this.enforcesSecureChat;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundServerDataPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */