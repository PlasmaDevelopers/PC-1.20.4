/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ClientboundEntityEventPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final int entityId;
/*    */   private final byte eventId;
/*    */   
/*    */   public ClientboundEntityEventPacket(Entity $$0, byte $$1) {
/* 16 */     this.entityId = $$0.getId();
/* 17 */     this.eventId = $$1;
/*    */   }
/*    */   
/*    */   public ClientboundEntityEventPacket(FriendlyByteBuf $$0) {
/* 21 */     this.entityId = $$0.readInt();
/* 22 */     this.eventId = $$0.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 27 */     $$0.writeInt(this.entityId);
/* 28 */     $$0.writeByte(this.eventId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 33 */     $$0.handleEntityEvent(this);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Entity getEntity(Level $$0) {
/* 38 */     return $$0.getEntity(this.entityId);
/*    */   }
/*    */   
/*    */   public byte getEventId() {
/* 42 */     return this.eventId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundEntityEventPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */