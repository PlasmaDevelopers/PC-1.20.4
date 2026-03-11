/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.border.WorldBorder;
/*    */ 
/*    */ public class ClientboundSetBorderCenterPacket implements Packet<ClientGamePacketListener> {
/*    */   private final double newCenterX;
/*    */   
/*    */   public ClientboundSetBorderCenterPacket(WorldBorder $$0) {
/* 12 */     this.newCenterX = $$0.getCenterX();
/* 13 */     this.newCenterZ = $$0.getCenterZ();
/*    */   }
/*    */   private final double newCenterZ;
/*    */   public ClientboundSetBorderCenterPacket(FriendlyByteBuf $$0) {
/* 17 */     this.newCenterX = $$0.readDouble();
/* 18 */     this.newCenterZ = $$0.readDouble();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeDouble(this.newCenterX);
/* 24 */     $$0.writeDouble(this.newCenterZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 29 */     $$0.handleSetBorderCenter(this);
/*    */   }
/*    */   
/*    */   public double getNewCenterZ() {
/* 33 */     return this.newCenterZ;
/*    */   }
/*    */   
/*    */   public double getNewCenterX() {
/* 37 */     return this.newCenterX;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetBorderCenterPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */