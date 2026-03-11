/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundPaddleBoatPacket implements Packet<ServerGamePacketListener> {
/*    */   private final boolean left;
/*    */   
/*    */   public ServerboundPaddleBoatPacket(boolean $$0, boolean $$1) {
/* 11 */     this.left = $$0;
/* 12 */     this.right = $$1;
/*    */   }
/*    */   private final boolean right;
/*    */   public ServerboundPaddleBoatPacket(FriendlyByteBuf $$0) {
/* 16 */     this.left = $$0.readBoolean();
/* 17 */     this.right = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 22 */     $$0.writeBoolean(this.left);
/* 23 */     $$0.writeBoolean(this.right);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 28 */     $$0.handlePaddleBoat(this);
/*    */   }
/*    */   
/*    */   public boolean getLeft() {
/* 32 */     return this.left;
/*    */   }
/*    */   
/*    */   public boolean getRight() {
/* 36 */     return this.right;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundPaddleBoatPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */