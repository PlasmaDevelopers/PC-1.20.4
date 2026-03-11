/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundContainerButtonClickPacket implements Packet<ServerGamePacketListener> {
/*    */   private final int containerId;
/*    */   
/*    */   public ServerboundContainerButtonClickPacket(int $$0, int $$1) {
/* 11 */     this.containerId = $$0;
/* 12 */     this.buttonId = $$1;
/*    */   }
/*    */   private final int buttonId;
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 17 */     $$0.handleContainerButtonClick(this);
/*    */   }
/*    */   
/*    */   public ServerboundContainerButtonClickPacket(FriendlyByteBuf $$0) {
/* 21 */     this.containerId = $$0.readByte();
/* 22 */     this.buttonId = $$0.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 27 */     $$0.writeByte(this.containerId);
/* 28 */     $$0.writeByte(this.buttonId);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 32 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public int getButtonId() {
/* 36 */     return this.buttonId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundContainerButtonClickPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */