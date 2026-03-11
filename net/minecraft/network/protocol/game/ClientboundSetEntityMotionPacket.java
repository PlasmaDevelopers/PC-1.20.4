/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ClientboundSetEntityMotionPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final int id;
/*    */   private final int xa;
/*    */   
/*    */   public ClientboundSetEntityMotionPacket(Entity $$0) {
/* 16 */     this($$0.getId(), $$0.getDeltaMovement());
/*    */   }
/*    */   private final int ya; private final int za;
/*    */   public ClientboundSetEntityMotionPacket(int $$0, Vec3 $$1) {
/* 20 */     this.id = $$0;
/* 21 */     double $$2 = 3.9D;
/* 22 */     double $$3 = Mth.clamp($$1.x, -3.9D, 3.9D);
/* 23 */     double $$4 = Mth.clamp($$1.y, -3.9D, 3.9D);
/* 24 */     double $$5 = Mth.clamp($$1.z, -3.9D, 3.9D);
/* 25 */     this.xa = (int)($$3 * 8000.0D);
/* 26 */     this.ya = (int)($$4 * 8000.0D);
/* 27 */     this.za = (int)($$5 * 8000.0D);
/*    */   }
/*    */   
/*    */   public ClientboundSetEntityMotionPacket(FriendlyByteBuf $$0) {
/* 31 */     this.id = $$0.readVarInt();
/* 32 */     this.xa = $$0.readShort();
/* 33 */     this.ya = $$0.readShort();
/* 34 */     this.za = $$0.readShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 39 */     $$0.writeVarInt(this.id);
/* 40 */     $$0.writeShort(this.xa);
/* 41 */     $$0.writeShort(this.ya);
/* 42 */     $$0.writeShort(this.za);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 47 */     $$0.handleSetEntityMotion(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 51 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getXa() {
/* 55 */     return this.xa;
/*    */   }
/*    */   
/*    */   public int getYa() {
/* 59 */     return this.ya;
/*    */   }
/*    */   
/*    */   public int getZa() {
/* 63 */     return this.za;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetEntityMotionPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */