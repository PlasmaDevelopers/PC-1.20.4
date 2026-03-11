/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundSetChunkCacheCenterPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int x;
/*    */   
/*    */   public ClientboundSetChunkCacheCenterPacket(int $$0, int $$1) {
/* 11 */     this.x = $$0;
/* 12 */     this.z = $$1;
/*    */   }
/*    */   private final int z;
/*    */   public ClientboundSetChunkCacheCenterPacket(FriendlyByteBuf $$0) {
/* 16 */     this.x = $$0.readVarInt();
/* 17 */     this.z = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 22 */     $$0.writeVarInt(this.x);
/* 23 */     $$0.writeVarInt(this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 28 */     $$0.handleSetChunkCacheCenter(this);
/*    */   }
/*    */   
/*    */   public int getX() {
/* 32 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 36 */     return this.z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetChunkCacheCenterPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */