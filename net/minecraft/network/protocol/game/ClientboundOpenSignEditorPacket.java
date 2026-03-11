/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundOpenSignEditorPacket implements Packet<ClientGamePacketListener> {
/*    */   private final BlockPos pos;
/*    */   
/*    */   public ClientboundOpenSignEditorPacket(BlockPos $$0, boolean $$1) {
/* 12 */     this.pos = $$0;
/* 13 */     this.isFrontText = $$1;
/*    */   }
/*    */   private final boolean isFrontText;
/*    */   public ClientboundOpenSignEditorPacket(FriendlyByteBuf $$0) {
/* 17 */     this.pos = $$0.readBlockPos();
/* 18 */     this.isFrontText = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeBlockPos(this.pos);
/* 24 */     $$0.writeBoolean(this.isFrontText);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 29 */     $$0.handleOpenSignEditor(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 33 */     return this.pos;
/*    */   }
/*    */   
/*    */   public boolean isFrontText() {
/* 37 */     return this.isFrontText;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundOpenSignEditorPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */