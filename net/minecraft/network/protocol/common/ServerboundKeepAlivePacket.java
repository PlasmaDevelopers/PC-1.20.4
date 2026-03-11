/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundKeepAlivePacket
/*    */   implements Packet<ServerCommonPacketListener> {
/*    */   public ServerboundKeepAlivePacket(long $$0) {
/* 10 */     this.id = $$0;
/*    */   }
/*    */   private final long id;
/*    */   
/*    */   public void handle(ServerCommonPacketListener $$0) {
/* 15 */     $$0.handleKeepAlive(this);
/*    */   }
/*    */   
/*    */   public ServerboundKeepAlivePacket(FriendlyByteBuf $$0) {
/* 19 */     this.id = $$0.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeLong(this.id);
/*    */   }
/*    */   
/*    */   public long getId() {
/* 28 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ServerboundKeepAlivePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */