/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class ServerboundTeleportToEntityPacket implements Packet<ServerGamePacketListener> {
/*    */   private final UUID uuid;
/*    */   
/*    */   public ServerboundTeleportToEntityPacket(UUID $$0) {
/* 15 */     this.uuid = $$0;
/*    */   }
/*    */   
/*    */   public ServerboundTeleportToEntityPacket(FriendlyByteBuf $$0) {
/* 19 */     this.uuid = $$0.readUUID();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeUUID(this.uuid);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 29 */     $$0.handleTeleportToEntityPacket(this);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Entity getEntity(ServerLevel $$0) {
/* 34 */     return $$0.getEntity(this.uuid);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundTeleportToEntityPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */