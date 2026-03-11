/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundSetDefaultSpawnPositionPacket implements Packet<ClientGamePacketListener> {
/*    */   private final BlockPos pos;
/*    */   
/*    */   public ClientboundSetDefaultSpawnPositionPacket(BlockPos $$0, float $$1) {
/* 12 */     this.pos = $$0;
/* 13 */     this.angle = $$1;
/*    */   }
/*    */   private final float angle;
/*    */   public ClientboundSetDefaultSpawnPositionPacket(FriendlyByteBuf $$0) {
/* 17 */     this.pos = $$0.readBlockPos();
/* 18 */     this.angle = $$0.readFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeBlockPos(this.pos);
/* 24 */     $$0.writeFloat(this.angle);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 29 */     $$0.handleSetSpawn(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 33 */     return this.pos;
/*    */   }
/*    */   
/*    */   public float getAngle() {
/* 37 */     return this.angle;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetDefaultSpawnPositionPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */