/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.border.WorldBorder;
/*    */ 
/*    */ public class ClientboundSetBorderWarningDistancePacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public ClientboundSetBorderWarningDistancePacket(WorldBorder $$0) {
/* 11 */     this.warningBlocks = $$0.getWarningBlocks();
/*    */   }
/*    */   private final int warningBlocks;
/*    */   public ClientboundSetBorderWarningDistancePacket(FriendlyByteBuf $$0) {
/* 15 */     this.warningBlocks = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeVarInt(this.warningBlocks);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 25 */     $$0.handleSetBorderWarningDistance(this);
/*    */   }
/*    */   
/*    */   public int getWarningBlocks() {
/* 29 */     return this.warningBlocks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetBorderWarningDistancePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */