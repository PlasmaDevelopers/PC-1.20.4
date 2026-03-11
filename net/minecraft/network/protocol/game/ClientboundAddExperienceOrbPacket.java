/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.ExperienceOrb;
/*    */ 
/*    */ public class ClientboundAddExperienceOrbPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int id;
/*    */   private final double x;
/*    */   private final double y;
/*    */   private final double z;
/*    */   private final int value;
/*    */   
/*    */   public ClientboundAddExperienceOrbPacket(ExperienceOrb $$0) {
/* 16 */     this.id = $$0.getId();
/* 17 */     this.x = $$0.getX();
/* 18 */     this.y = $$0.getY();
/* 19 */     this.z = $$0.getZ();
/* 20 */     this.value = $$0.getValue();
/*    */   }
/*    */   
/*    */   public ClientboundAddExperienceOrbPacket(FriendlyByteBuf $$0) {
/* 24 */     this.id = $$0.readVarInt();
/* 25 */     this.x = $$0.readDouble();
/* 26 */     this.y = $$0.readDouble();
/* 27 */     this.z = $$0.readDouble();
/* 28 */     this.value = $$0.readShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 33 */     $$0.writeVarInt(this.id);
/* 34 */     $$0.writeDouble(this.x);
/* 35 */     $$0.writeDouble(this.y);
/* 36 */     $$0.writeDouble(this.z);
/* 37 */     $$0.writeShort(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 42 */     $$0.handleAddExperienceOrb(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 46 */     return this.id;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 50 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 54 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 58 */     return this.z;
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 62 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundAddExperienceOrbPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */