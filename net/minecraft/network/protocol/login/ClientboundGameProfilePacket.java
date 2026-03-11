/*    */ package net.minecraft.network.protocol.login;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundGameProfilePacket
/*    */   implements Packet<ClientLoginPacketListener> {
/*    */   public ClientboundGameProfilePacket(GameProfile $$0) {
/* 12 */     this.gameProfile = $$0;
/*    */   }
/*    */   private final GameProfile gameProfile;
/*    */   public ClientboundGameProfilePacket(FriendlyByteBuf $$0) {
/* 16 */     this.gameProfile = $$0.readGameProfile();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 21 */     $$0.writeGameProfile(this.gameProfile);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientLoginPacketListener $$0) {
/* 26 */     $$0.handleGameProfile(this);
/*    */   }
/*    */   
/*    */   public GameProfile getGameProfile() {
/* 30 */     return this.gameProfile;
/*    */   }
/*    */ 
/*    */   
/*    */   public ConnectionProtocol nextProtocol() {
/* 35 */     return ConnectionProtocol.CONFIGURATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ClientboundGameProfilePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */