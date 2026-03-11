/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundBlockDestructionPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final int id;
/*    */   
/*    */   public ClientboundBlockDestructionPacket(int $$0, BlockPos $$1, int $$2) {
/* 13 */     this.id = $$0;
/* 14 */     this.pos = $$1;
/* 15 */     this.progress = $$2;
/*    */   }
/*    */   private final BlockPos pos; private final int progress;
/*    */   public ClientboundBlockDestructionPacket(FriendlyByteBuf $$0) {
/* 19 */     this.id = $$0.readVarInt();
/* 20 */     this.pos = $$0.readBlockPos();
/* 21 */     this.progress = $$0.readUnsignedByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 26 */     $$0.writeVarInt(this.id);
/* 27 */     $$0.writeBlockPos(this.pos);
/* 28 */     $$0.writeByte(this.progress);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 33 */     $$0.handleBlockDestruction(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 37 */     return this.id;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 41 */     return this.pos;
/*    */   }
/*    */   
/*    */   public int getProgress() {
/* 45 */     return this.progress;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBlockDestructionPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */