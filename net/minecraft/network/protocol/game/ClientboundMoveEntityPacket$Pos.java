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
/*    */ public class Pos
/*    */   extends ClientboundMoveEntityPacket
/*    */ {
/*    */   public Pos(int $$0, short $$1, short $$2, short $$3, boolean $$4) {
/* 55 */     super($$0, $$1, $$2, $$3, (byte)0, (byte)0, $$4, false, true);
/*    */   }
/*    */   
/*    */   public static Pos read(FriendlyByteBuf $$0) {
/* 59 */     int $$1 = $$0.readVarInt();
/* 60 */     short $$2 = $$0.readShort();
/* 61 */     short $$3 = $$0.readShort();
/* 62 */     short $$4 = $$0.readShort();
/* 63 */     boolean $$5 = $$0.readBoolean();
/*    */     
/* 65 */     return new Pos($$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 70 */     $$0.writeVarInt(this.entityId);
/* 71 */     $$0.writeShort(this.xa);
/* 72 */     $$0.writeShort(this.ya);
/* 73 */     $$0.writeShort(this.za);
/* 74 */     $$0.writeBoolean(this.onGround);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundMoveEntityPacket$Pos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */