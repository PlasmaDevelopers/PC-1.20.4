/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientboundPlayerCombatEnterPacket
/*    */   implements Packet<ClientGamePacketListener>
/*    */ {
/*    */   public ClientboundPlayerCombatEnterPacket() {}
/*    */   
/*    */   public ClientboundPlayerCombatEnterPacket(FriendlyByteBuf $$0) {}
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {}
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 19 */     $$0.handlePlayerCombatEnter(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerCombatEnterPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */