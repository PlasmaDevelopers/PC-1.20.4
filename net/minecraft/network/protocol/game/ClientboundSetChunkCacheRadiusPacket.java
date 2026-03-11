/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundSetChunkCacheRadiusPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public ClientboundSetChunkCacheRadiusPacket(int $$0) {
/* 10 */     this.radius = $$0;
/*    */   }
/*    */   private final int radius;
/*    */   public ClientboundSetChunkCacheRadiusPacket(FriendlyByteBuf $$0) {
/* 14 */     this.radius = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeVarInt(this.radius);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 24 */     $$0.handleSetChunkCacheRadius(this);
/*    */   }
/*    */   
/*    */   public int getRadius() {
/* 28 */     return this.radius;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetChunkCacheRadiusPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */