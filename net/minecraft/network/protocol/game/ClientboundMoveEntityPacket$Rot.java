/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Rot
/*    */   extends ClientboundMoveEntityPacket
/*    */ {
/*    */   public Rot(int $$0, byte $$1, byte $$2, boolean $$3) {
/* 80 */     super($$0, (short)0, (short)0, (short)0, $$1, $$2, $$3, true, false);
/*    */   }
/*    */   
/*    */   public static Rot read(FriendlyByteBuf $$0) {
/* 84 */     int $$1 = $$0.readVarInt();
/* 85 */     byte $$2 = $$0.readByte();
/* 86 */     byte $$3 = $$0.readByte();
/* 87 */     boolean $$4 = $$0.readBoolean();
/*    */     
/* 89 */     return new Rot($$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 94 */     $$0.writeVarInt(this.entityId);
/* 95 */     $$0.writeByte(this.yRot);
/* 96 */     $$0.writeByte(this.xRot);
/* 97 */     $$0.writeBoolean(this.onGround);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundMoveEntityPacket$Rot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */