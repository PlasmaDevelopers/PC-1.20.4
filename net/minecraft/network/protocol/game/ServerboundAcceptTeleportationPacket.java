/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundAcceptTeleportationPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   public ServerboundAcceptTeleportationPacket(int $$0) {
/* 10 */     this.id = $$0;
/*    */   }
/*    */   private final int id;
/*    */   public ServerboundAcceptTeleportationPacket(FriendlyByteBuf $$0) {
/* 14 */     this.id = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeVarInt(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 24 */     $$0.handleAcceptTeleportPacket(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 28 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundAcceptTeleportationPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */