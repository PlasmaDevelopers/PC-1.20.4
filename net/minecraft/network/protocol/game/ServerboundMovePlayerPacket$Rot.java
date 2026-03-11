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
/*    */ public class Rot
/*    */   extends ServerboundMovePlayerPacket
/*    */ {
/*    */   public Rot(float $$0, float $$1, boolean $$2) {
/* 66 */     super(0.0D, 0.0D, 0.0D, $$0, $$1, $$2, false, true);
/*    */   }
/*    */   
/*    */   public static Rot read(FriendlyByteBuf $$0) {
/* 70 */     float $$1 = $$0.readFloat();
/* 71 */     float $$2 = $$0.readFloat();
/* 72 */     boolean $$3 = ($$0.readUnsignedByte() != 0);
/* 73 */     return new Rot($$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 78 */     $$0.writeFloat(this.yRot);
/* 79 */     $$0.writeFloat(this.xRot);
/* 80 */     $$0.writeByte(this.onGround ? 1 : 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundMovePlayerPacket$Rot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */