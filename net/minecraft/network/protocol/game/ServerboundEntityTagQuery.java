/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundEntityTagQuery implements Packet<ServerGamePacketListener> {
/*    */   private final int transactionId;
/*    */   
/*    */   public ServerboundEntityTagQuery(int $$0, int $$1) {
/* 11 */     this.transactionId = $$0;
/* 12 */     this.entityId = $$1;
/*    */   }
/*    */   private final int entityId;
/*    */   public ServerboundEntityTagQuery(FriendlyByteBuf $$0) {
/* 16 */     this.transactionId = $$0.readVarInt();
/* 17 */     this.entityId = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 22 */     $$0.writeVarInt(this.transactionId);
/* 23 */     $$0.writeVarInt(this.entityId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 28 */     $$0.handleEntityTagQuery(this);
/*    */   }
/*    */   
/*    */   public int getTransactionId() {
/* 32 */     return this.transactionId;
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 36 */     return this.entityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundEntityTagQuery.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */