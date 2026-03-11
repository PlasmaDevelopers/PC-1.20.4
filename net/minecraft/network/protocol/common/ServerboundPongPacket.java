/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundPongPacket implements Packet<ServerCommonPacketListener> {
/*    */   private final int id;
/*    */   
/*    */   public ServerboundPongPacket(int $$0) {
/* 11 */     this.id = $$0;
/*    */   }
/*    */   
/*    */   public ServerboundPongPacket(FriendlyByteBuf $$0) {
/* 15 */     this.id = $$0.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeInt(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerCommonPacketListener $$0) {
/* 25 */     $$0.handlePong(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 29 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ServerboundPongPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */