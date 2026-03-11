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
/*    */ public class Pos
/*    */   extends ServerboundMovePlayerPacket
/*    */ {
/*    */   public Pos(double $$0, double $$1, double $$2, boolean $$3) {
/* 44 */     super($$0, $$1, $$2, 0.0F, 0.0F, $$3, true, false);
/*    */   }
/*    */   
/*    */   public static Pos read(FriendlyByteBuf $$0) {
/* 48 */     double $$1 = $$0.readDouble();
/* 49 */     double $$2 = $$0.readDouble();
/* 50 */     double $$3 = $$0.readDouble();
/* 51 */     boolean $$4 = ($$0.readUnsignedByte() != 0);
/* 52 */     return new Pos($$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 57 */     $$0.writeDouble(this.x);
/* 58 */     $$0.writeDouble(this.y);
/* 59 */     $$0.writeDouble(this.z);
/* 60 */     $$0.writeByte(this.onGround ? 1 : 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundMovePlayerPacket$Pos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */