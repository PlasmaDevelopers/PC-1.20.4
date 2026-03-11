/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ 
/*    */ public class ClientboundTeleportEntityPacket
/*    */   implements Packet<ClientGamePacketListener>
/*    */ {
/*    */   private final int id;
/*    */   private final double x;
/*    */   private final double y;
/*    */   
/*    */   public ClientboundTeleportEntityPacket(Entity $$0) {
/* 18 */     this.id = $$0.getId();
/* 19 */     Vec3 $$1 = $$0.trackingPosition();
/* 20 */     this.x = $$1.x;
/* 21 */     this.y = $$1.y;
/* 22 */     this.z = $$1.z;
/* 23 */     this.yRot = (byte)(int)($$0.getYRot() * 256.0F / 360.0F);
/* 24 */     this.xRot = (byte)(int)($$0.getXRot() * 256.0F / 360.0F);
/* 25 */     this.onGround = $$0.onGround();
/*    */   }
/*    */   private final double z; private final byte yRot; private final byte xRot; private final boolean onGround;
/*    */   public ClientboundTeleportEntityPacket(FriendlyByteBuf $$0) {
/* 29 */     this.id = $$0.readVarInt();
/* 30 */     this.x = $$0.readDouble();
/* 31 */     this.y = $$0.readDouble();
/* 32 */     this.z = $$0.readDouble();
/* 33 */     this.yRot = $$0.readByte();
/* 34 */     this.xRot = $$0.readByte();
/* 35 */     this.onGround = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 40 */     $$0.writeVarInt(this.id);
/* 41 */     $$0.writeDouble(this.x);
/* 42 */     $$0.writeDouble(this.y);
/* 43 */     $$0.writeDouble(this.z);
/* 44 */     $$0.writeByte(this.yRot);
/* 45 */     $$0.writeByte(this.xRot);
/* 46 */     $$0.writeBoolean(this.onGround);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 51 */     $$0.handleTeleportEntity(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 55 */     return this.id;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 59 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 63 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 67 */     return this.z;
/*    */   }
/*    */   
/*    */   public byte getyRot() {
/* 71 */     return this.yRot;
/*    */   }
/*    */   
/*    */   public byte getxRot() {
/* 75 */     return this.xRot;
/*    */   }
/*    */   
/*    */   public boolean isOnGround() {
/* 79 */     return this.onGround;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundTeleportEntityPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */