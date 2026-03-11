/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.border.WorldBorder;
/*    */ 
/*    */ public class ClientboundSetBorderWarningDelayPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public ClientboundSetBorderWarningDelayPacket(WorldBorder $$0) {
/* 11 */     this.warningDelay = $$0.getWarningTime();
/*    */   }
/*    */   private final int warningDelay;
/*    */   public ClientboundSetBorderWarningDelayPacket(FriendlyByteBuf $$0) {
/* 15 */     this.warningDelay = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeVarInt(this.warningDelay);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 25 */     $$0.handleSetBorderWarningDelay(this);
/*    */   }
/*    */   
/*    */   public int getWarningDelay() {
/* 29 */     return this.warningDelay;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetBorderWarningDelayPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */