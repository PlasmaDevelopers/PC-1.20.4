/*    */ package net.minecraft.network.protocol.login;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundLoginDisconnectPacket
/*    */   implements Packet<ClientLoginPacketListener> {
/*    */   private final Component reason;
/*    */   
/*    */   public ClientboundLoginDisconnectPacket(Component $$0) {
/* 13 */     this.reason = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public ClientboundLoginDisconnectPacket(FriendlyByteBuf $$0) {
/* 18 */     this.reason = (Component)Component.Serializer.fromJsonLenient($$0.readUtf(262144));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeUtf(Component.Serializer.toJson(this.reason));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientLoginPacketListener $$0) {
/* 28 */     $$0.handleDisconnect(this);
/*    */   }
/*    */   
/*    */   public Component getReason() {
/* 32 */     return this.reason;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ClientboundLoginDisconnectPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */