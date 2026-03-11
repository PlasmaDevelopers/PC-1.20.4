/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.border.WorldBorder;
/*    */ 
/*    */ 
/*    */ public class ClientboundInitializeBorderPacket
/*    */   implements Packet<ClientGamePacketListener>
/*    */ {
/*    */   private final double newCenterX;
/*    */   private final double newCenterZ;
/*    */   private final double oldSize;
/*    */   private final double newSize;
/*    */   
/*    */   public ClientboundInitializeBorderPacket(FriendlyByteBuf $$0) {
/* 18 */     this.newCenterX = $$0.readDouble();
/* 19 */     this.newCenterZ = $$0.readDouble();
/* 20 */     this.oldSize = $$0.readDouble();
/* 21 */     this.newSize = $$0.readDouble();
/* 22 */     this.lerpTime = $$0.readVarLong();
/* 23 */     this.newAbsoluteMaxSize = $$0.readVarInt();
/* 24 */     this.warningBlocks = $$0.readVarInt();
/* 25 */     this.warningTime = $$0.readVarInt();
/*    */   }
/*    */   private final long lerpTime; private final int newAbsoluteMaxSize; private final int warningBlocks; private final int warningTime;
/*    */   public ClientboundInitializeBorderPacket(WorldBorder $$0) {
/* 29 */     this.newCenterX = $$0.getCenterX();
/* 30 */     this.newCenterZ = $$0.getCenterZ();
/* 31 */     this.oldSize = $$0.getSize();
/* 32 */     this.newSize = $$0.getLerpTarget();
/* 33 */     this.lerpTime = $$0.getLerpRemainingTime();
/* 34 */     this.newAbsoluteMaxSize = $$0.getAbsoluteMaxSize();
/* 35 */     this.warningBlocks = $$0.getWarningBlocks();
/* 36 */     this.warningTime = $$0.getWarningTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 41 */     $$0.writeDouble(this.newCenterX);
/* 42 */     $$0.writeDouble(this.newCenterZ);
/* 43 */     $$0.writeDouble(this.oldSize);
/* 44 */     $$0.writeDouble(this.newSize);
/* 45 */     $$0.writeVarLong(this.lerpTime);
/* 46 */     $$0.writeVarInt(this.newAbsoluteMaxSize);
/* 47 */     $$0.writeVarInt(this.warningBlocks);
/* 48 */     $$0.writeVarInt(this.warningTime);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 53 */     $$0.handleInitializeBorder(this);
/*    */   }
/*    */   
/*    */   public double getNewCenterX() {
/* 57 */     return this.newCenterX;
/*    */   }
/*    */   
/*    */   public double getNewCenterZ() {
/* 61 */     return this.newCenterZ;
/*    */   }
/*    */   
/*    */   public double getNewSize() {
/* 65 */     return this.newSize;
/*    */   }
/*    */   
/*    */   public double getOldSize() {
/* 69 */     return this.oldSize;
/*    */   }
/*    */   
/*    */   public long getLerpTime() {
/* 73 */     return this.lerpTime;
/*    */   }
/*    */   
/*    */   public int getNewAbsoluteMaxSize() {
/* 77 */     return this.newAbsoluteMaxSize;
/*    */   }
/*    */   
/*    */   public int getWarningTime() {
/* 81 */     return this.warningTime;
/*    */   }
/*    */   
/*    */   public int getWarningBlocks() {
/* 85 */     return this.warningBlocks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundInitializeBorderPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */