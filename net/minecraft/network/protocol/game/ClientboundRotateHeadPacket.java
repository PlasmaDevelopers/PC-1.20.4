/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ClientboundRotateHeadPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int entityId;
/*    */   private final byte yHeadRot;
/*    */   
/*    */   public ClientboundRotateHeadPacket(Entity $$0, byte $$1) {
/* 14 */     this.entityId = $$0.getId();
/* 15 */     this.yHeadRot = $$1;
/*    */   }
/*    */   
/*    */   public ClientboundRotateHeadPacket(FriendlyByteBuf $$0) {
/* 19 */     this.entityId = $$0.readVarInt();
/* 20 */     this.yHeadRot = $$0.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeVarInt(this.entityId);
/* 26 */     $$0.writeByte(this.yHeadRot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 31 */     $$0.handleRotateMob(this);
/*    */   }
/*    */   
/*    */   public Entity getEntity(Level $$0) {
/* 35 */     return $$0.getEntity(this.entityId);
/*    */   }
/*    */   
/*    */   public byte getYHeadRot() {
/* 39 */     return this.yHeadRot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundRotateHeadPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */