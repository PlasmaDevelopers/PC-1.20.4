/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class ClientboundSetEntityLinkPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int sourceId;
/*    */   private final int destId;
/*    */   
/*    */   public ClientboundSetEntityLinkPacket(Entity $$0, @Nullable Entity $$1) {
/* 14 */     this.sourceId = $$0.getId();
/* 15 */     this.destId = ($$1 != null) ? $$1.getId() : 0;
/*    */   }
/*    */   
/*    */   public ClientboundSetEntityLinkPacket(FriendlyByteBuf $$0) {
/* 19 */     this.sourceId = $$0.readInt();
/* 20 */     this.destId = $$0.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeInt(this.sourceId);
/* 26 */     $$0.writeInt(this.destId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 31 */     $$0.handleEntityLinkPacket(this);
/*    */   }
/*    */   
/*    */   public int getSourceId() {
/* 35 */     return this.sourceId;
/*    */   }
/*    */   
/*    */   public int getDestId() {
/* 39 */     return this.destId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetEntityLinkPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */