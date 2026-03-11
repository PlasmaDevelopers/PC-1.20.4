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
/*    */ public class PosRot
/*    */   extends ServerboundMovePlayerPacket
/*    */ {
/*    */   public PosRot(double $$0, double $$1, double $$2, float $$3, float $$4, boolean $$5) {
/* 18 */     super($$0, $$1, $$2, $$3, $$4, $$5, true, true);
/*    */   }
/*    */   
/*    */   public static PosRot read(FriendlyByteBuf $$0) {
/* 22 */     double $$1 = $$0.readDouble();
/* 23 */     double $$2 = $$0.readDouble();
/* 24 */     double $$3 = $$0.readDouble();
/* 25 */     float $$4 = $$0.readFloat();
/* 26 */     float $$5 = $$0.readFloat();
/* 27 */     boolean $$6 = ($$0.readUnsignedByte() != 0);
/* 28 */     return new PosRot($$1, $$2, $$3, $$4, $$5, $$6);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 33 */     $$0.writeDouble(this.x);
/* 34 */     $$0.writeDouble(this.y);
/* 35 */     $$0.writeDouble(this.z);
/* 36 */     $$0.writeFloat(this.yRot);
/* 37 */     $$0.writeFloat(this.xRot);
/* 38 */     $$0.writeByte(this.onGround ? 1 : 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundMovePlayerPacket$PosRot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */