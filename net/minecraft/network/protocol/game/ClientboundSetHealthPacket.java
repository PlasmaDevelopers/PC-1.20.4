/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundSetHealthPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final float health;
/*    */   
/*    */   public ClientboundSetHealthPacket(float $$0, int $$1, float $$2) {
/* 12 */     this.health = $$0;
/* 13 */     this.food = $$1;
/* 14 */     this.saturation = $$2;
/*    */   }
/*    */   private final int food; private final float saturation;
/*    */   public ClientboundSetHealthPacket(FriendlyByteBuf $$0) {
/* 18 */     this.health = $$0.readFloat();
/* 19 */     this.food = $$0.readVarInt();
/* 20 */     this.saturation = $$0.readFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeFloat(this.health);
/* 26 */     $$0.writeVarInt(this.food);
/* 27 */     $$0.writeFloat(this.saturation);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 32 */     $$0.handleSetHealth(this);
/*    */   }
/*    */   
/*    */   public float getHealth() {
/* 36 */     return this.health;
/*    */   }
/*    */   
/*    */   public int getFood() {
/* 40 */     return this.food;
/*    */   }
/*    */   
/*    */   public float getSaturation() {
/* 44 */     return this.saturation;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetHealthPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */