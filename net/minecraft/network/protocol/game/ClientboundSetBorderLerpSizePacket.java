/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.border.WorldBorder;
/*    */ 
/*    */ public class ClientboundSetBorderLerpSizePacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final double oldSize;
/*    */   
/*    */   public ClientboundSetBorderLerpSizePacket(WorldBorder $$0) {
/* 13 */     this.oldSize = $$0.getSize();
/* 14 */     this.newSize = $$0.getLerpTarget();
/* 15 */     this.lerpTime = $$0.getLerpRemainingTime();
/*    */   }
/*    */   private final double newSize; private final long lerpTime;
/*    */   public ClientboundSetBorderLerpSizePacket(FriendlyByteBuf $$0) {
/* 19 */     this.oldSize = $$0.readDouble();
/* 20 */     this.newSize = $$0.readDouble();
/* 21 */     this.lerpTime = $$0.readVarLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 26 */     $$0.writeDouble(this.oldSize);
/* 27 */     $$0.writeDouble(this.newSize);
/* 28 */     $$0.writeVarLong(this.lerpTime);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 33 */     $$0.handleSetBorderLerpSize(this);
/*    */   }
/*    */   
/*    */   public double getOldSize() {
/* 37 */     return this.oldSize;
/*    */   }
/*    */   
/*    */   public double getNewSize() {
/* 41 */     return this.newSize;
/*    */   }
/*    */   
/*    */   public long getLerpTime() {
/* 45 */     return this.lerpTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetBorderLerpSizePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */