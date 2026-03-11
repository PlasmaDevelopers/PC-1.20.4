/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundPlayerCombatKillPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int playerId;
/*    */   
/*    */   public ClientboundPlayerCombatKillPacket(int $$0, Component $$1) {
/* 12 */     this.playerId = $$0;
/* 13 */     this.message = $$1;
/*    */   }
/*    */   private final Component message;
/*    */   public ClientboundPlayerCombatKillPacket(FriendlyByteBuf $$0) {
/* 17 */     this.playerId = $$0.readVarInt();
/* 18 */     this.message = $$0.readComponentTrusted();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeVarInt(this.playerId);
/* 24 */     $$0.writeComponent(this.message);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 29 */     $$0.handlePlayerCombatKill(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSkippable() {
/* 34 */     return true;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 38 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public Component getMessage() {
/* 42 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerCombatKillPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */