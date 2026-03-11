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
/*    */ public class PosRot
/*    */   extends ClientboundMoveEntityPacket
/*    */ {
/*    */   public PosRot(int $$0, short $$1, short $$2, short $$3, byte $$4, byte $$5, boolean $$6) {
/* 26 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6, true, true);
/*    */   }
/*    */   
/*    */   public static PosRot read(FriendlyByteBuf $$0) {
/* 30 */     int $$1 = $$0.readVarInt();
/* 31 */     short $$2 = $$0.readShort();
/* 32 */     short $$3 = $$0.readShort();
/* 33 */     short $$4 = $$0.readShort();
/* 34 */     byte $$5 = $$0.readByte();
/* 35 */     byte $$6 = $$0.readByte();
/* 36 */     boolean $$7 = $$0.readBoolean();
/*    */     
/* 38 */     return new PosRot($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 43 */     $$0.writeVarInt(this.entityId);
/* 44 */     $$0.writeShort(this.xa);
/* 45 */     $$0.writeShort(this.ya);
/* 46 */     $$0.writeShort(this.za);
/* 47 */     $$0.writeByte(this.yRot);
/* 48 */     $$0.writeByte(this.xRot);
/* 49 */     $$0.writeBoolean(this.onGround);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundMoveEntityPacket$PosRot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */