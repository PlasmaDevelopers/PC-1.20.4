/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundSelectTradePacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   public ServerboundSelectTradePacket(int $$0) {
/* 10 */     this.item = $$0;
/*    */   }
/*    */   private final int item;
/*    */   public ServerboundSelectTradePacket(FriendlyByteBuf $$0) {
/* 14 */     this.item = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeVarInt(this.item);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 24 */     $$0.handleSelectTrade(this);
/*    */   }
/*    */   
/*    */   public int getItem() {
/* 28 */     return this.item;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSelectTradePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */