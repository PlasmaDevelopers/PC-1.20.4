/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.damagesource.CombatTracker;
/*    */ 
/*    */ public class ClientboundPlayerCombatEndPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public ClientboundPlayerCombatEndPacket(CombatTracker $$0) {
/* 11 */     this($$0.getCombatDuration());
/*    */   }
/*    */   private final int duration;
/*    */   public ClientboundPlayerCombatEndPacket(int $$0) {
/* 15 */     this.duration = $$0;
/*    */   }
/*    */   
/*    */   public ClientboundPlayerCombatEndPacket(FriendlyByteBuf $$0) {
/* 19 */     this.duration = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeVarInt(this.duration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 29 */     $$0.handlePlayerCombatEnd(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerCombatEndPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */