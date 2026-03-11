/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class ServerboundMoveVehiclePacket
/*    */   implements Packet<ServerGamePacketListener>
/*    */ {
/*    */   private final double x;
/*    */   private final double y;
/*    */   
/*    */   public ServerboundMoveVehiclePacket(Entity $$0) {
/* 15 */     this.x = $$0.getX();
/* 16 */     this.y = $$0.getY();
/* 17 */     this.z = $$0.getZ();
/* 18 */     this.yRot = $$0.getYRot();
/* 19 */     this.xRot = $$0.getXRot();
/*    */   }
/*    */   private final double z; private final float yRot; private final float xRot;
/*    */   public ServerboundMoveVehiclePacket(FriendlyByteBuf $$0) {
/* 23 */     this.x = $$0.readDouble();
/* 24 */     this.y = $$0.readDouble();
/* 25 */     this.z = $$0.readDouble();
/* 26 */     this.yRot = $$0.readFloat();
/* 27 */     this.xRot = $$0.readFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 32 */     $$0.writeDouble(this.x);
/* 33 */     $$0.writeDouble(this.y);
/* 34 */     $$0.writeDouble(this.z);
/* 35 */     $$0.writeFloat(this.yRot);
/* 36 */     $$0.writeFloat(this.xRot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 41 */     $$0.handleMoveVehicle(this);
/*    */   }
/*    */   
/*    */   public double getX() {
/* 45 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 49 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 53 */     return this.z;
/*    */   }
/*    */   
/*    */   public float getYRot() {
/* 57 */     return this.yRot;
/*    */   }
/*    */   
/*    */   public float getXRot() {
/* 61 */     return this.xRot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundMoveVehiclePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */