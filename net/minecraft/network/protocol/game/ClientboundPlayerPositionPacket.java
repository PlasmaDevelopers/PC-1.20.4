/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.RelativeMovement;
/*    */ 
/*    */ public class ClientboundPlayerPositionPacket implements Packet<ClientGamePacketListener> {
/*    */   private final double x;
/*    */   private final double y;
/*    */   private final double z;
/*    */   private final float yRot;
/*    */   private final float xRot;
/*    */   private final Set<RelativeMovement> relativeArguments;
/*    */   private final int id;
/*    */   
/*    */   public ClientboundPlayerPositionPacket(double $$0, double $$1, double $$2, float $$3, float $$4, Set<RelativeMovement> $$5, int $$6) {
/* 19 */     this.x = $$0;
/* 20 */     this.y = $$1;
/* 21 */     this.z = $$2;
/* 22 */     this.yRot = $$3;
/* 23 */     this.xRot = $$4;
/* 24 */     this.relativeArguments = $$5;
/* 25 */     this.id = $$6;
/*    */   }
/*    */   
/*    */   public ClientboundPlayerPositionPacket(FriendlyByteBuf $$0) {
/* 29 */     this.x = $$0.readDouble();
/* 30 */     this.y = $$0.readDouble();
/* 31 */     this.z = $$0.readDouble();
/* 32 */     this.yRot = $$0.readFloat();
/* 33 */     this.xRot = $$0.readFloat();
/* 34 */     this.relativeArguments = RelativeMovement.unpack($$0.readUnsignedByte());
/* 35 */     this.id = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 40 */     $$0.writeDouble(this.x);
/* 41 */     $$0.writeDouble(this.y);
/* 42 */     $$0.writeDouble(this.z);
/* 43 */     $$0.writeFloat(this.yRot);
/* 44 */     $$0.writeFloat(this.xRot);
/* 45 */     $$0.writeByte(RelativeMovement.pack(this.relativeArguments));
/* 46 */     $$0.writeVarInt(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 51 */     $$0.handleMovePlayer(this);
/*    */   }
/*    */   
/*    */   public double getX() {
/* 55 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 59 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 63 */     return this.z;
/*    */   }
/*    */   
/*    */   public float getYRot() {
/* 67 */     return this.yRot;
/*    */   }
/*    */   
/*    */   public float getXRot() {
/* 71 */     return this.xRot;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 75 */     return this.id;
/*    */   }
/*    */   
/*    */   public Set<RelativeMovement> getRelativeArguments() {
/* 79 */     return this.relativeArguments;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerPositionPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */