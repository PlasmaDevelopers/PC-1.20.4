/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundLevelEventPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int type;
/*    */   private final BlockPos pos;
/*    */   private final int data;
/*    */   private final boolean globalEvent;
/*    */   
/*    */   public ClientboundLevelEventPacket(int $$0, BlockPos $$1, int $$2, boolean $$3) {
/* 15 */     this.type = $$0;
/* 16 */     this.pos = $$1.immutable();
/* 17 */     this.data = $$2;
/* 18 */     this.globalEvent = $$3;
/*    */   }
/*    */   
/*    */   public ClientboundLevelEventPacket(FriendlyByteBuf $$0) {
/* 22 */     this.type = $$0.readInt();
/* 23 */     this.pos = $$0.readBlockPos();
/* 24 */     this.data = $$0.readInt();
/* 25 */     this.globalEvent = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 30 */     $$0.writeInt(this.type);
/* 31 */     $$0.writeBlockPos(this.pos);
/* 32 */     $$0.writeInt(this.data);
/* 33 */     $$0.writeBoolean(this.globalEvent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 38 */     $$0.handleLevelEvent(this);
/*    */   }
/*    */   
/*    */   public boolean isGlobalEvent() {
/* 42 */     return this.globalEvent;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 46 */     return this.type;
/*    */   }
/*    */   
/*    */   public int getData() {
/* 50 */     return this.data;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 54 */     return this.pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundLevelEventPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */