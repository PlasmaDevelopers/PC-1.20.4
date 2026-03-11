/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ClientboundSetCameraPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int cameraId;
/*    */   
/*    */   public ClientboundSetCameraPacket(Entity $$0) {
/* 14 */     this.cameraId = $$0.getId();
/*    */   }
/*    */   
/*    */   public ClientboundSetCameraPacket(FriendlyByteBuf $$0) {
/* 18 */     this.cameraId = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeVarInt(this.cameraId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 28 */     $$0.handleSetCamera(this);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Entity getEntity(Level $$0) {
/* 33 */     return $$0.getEntity(this.cameraId);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetCameraPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */