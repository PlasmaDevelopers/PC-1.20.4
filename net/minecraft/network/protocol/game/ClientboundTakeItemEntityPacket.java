/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundTakeItemEntityPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final int itemId;
/*    */   
/*    */   public ClientboundTakeItemEntityPacket(int $$0, int $$1, int $$2) {
/* 12 */     this.itemId = $$0;
/* 13 */     this.playerId = $$1;
/* 14 */     this.amount = $$2;
/*    */   }
/*    */   private final int playerId; private final int amount;
/*    */   public ClientboundTakeItemEntityPacket(FriendlyByteBuf $$0) {
/* 18 */     this.itemId = $$0.readVarInt();
/* 19 */     this.playerId = $$0.readVarInt();
/* 20 */     this.amount = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeVarInt(this.itemId);
/* 26 */     $$0.writeVarInt(this.playerId);
/* 27 */     $$0.writeVarInt(this.amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 32 */     $$0.handleTakeItemEntity(this);
/*    */   }
/*    */   
/*    */   public int getItemId() {
/* 36 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 40 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public int getAmount() {
/* 44 */     return this.amount;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundTakeItemEntityPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */