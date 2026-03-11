/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class ClientboundAnimatePacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public static final int SWING_MAIN_HAND = 0;
/*    */   public static final int WAKE_UP = 2;
/*    */   public static final int SWING_OFF_HAND = 3;
/*    */   public static final int CRITICAL_HIT = 4;
/*    */   public static final int MAGIC_CRITICAL_HIT = 5;
/*    */   private final int id;
/*    */   private final int action;
/*    */   
/*    */   public ClientboundAnimatePacket(Entity $$0, int $$1) {
/* 19 */     this.id = $$0.getId();
/* 20 */     this.action = $$1;
/*    */   }
/*    */   
/*    */   public ClientboundAnimatePacket(FriendlyByteBuf $$0) {
/* 24 */     this.id = $$0.readVarInt();
/* 25 */     this.action = $$0.readUnsignedByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 30 */     $$0.writeVarInt(this.id);
/* 31 */     $$0.writeByte(this.action);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 36 */     $$0.handleAnimate(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 40 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getAction() {
/* 44 */     return this.action;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundAnimatePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */