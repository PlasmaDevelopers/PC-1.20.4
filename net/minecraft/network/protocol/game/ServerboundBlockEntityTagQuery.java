/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundBlockEntityTagQuery implements Packet<ServerGamePacketListener> {
/*    */   private final int transactionId;
/*    */   
/*    */   public ServerboundBlockEntityTagQuery(int $$0, BlockPos $$1) {
/* 12 */     this.transactionId = $$0;
/* 13 */     this.pos = $$1;
/*    */   }
/*    */   private final BlockPos pos;
/*    */   public ServerboundBlockEntityTagQuery(FriendlyByteBuf $$0) {
/* 17 */     this.transactionId = $$0.readVarInt();
/* 18 */     this.pos = $$0.readBlockPos();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeVarInt(this.transactionId);
/* 24 */     $$0.writeBlockPos(this.pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 29 */     $$0.handleBlockEntityTagQuery(this);
/*    */   }
/*    */   
/*    */   public int getTransactionId() {
/* 33 */     return this.transactionId;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 37 */     return this.pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundBlockEntityTagQuery.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */