/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundClearTitlesPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public ClientboundClearTitlesPacket(boolean $$0) {
/* 10 */     this.resetTimes = $$0;
/*    */   }
/*    */   private final boolean resetTimes;
/*    */   public ClientboundClearTitlesPacket(FriendlyByteBuf $$0) {
/* 14 */     this.resetTimes = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeBoolean(this.resetTimes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 24 */     $$0.handleTitlesClear(this);
/*    */   }
/*    */   
/*    */   public boolean shouldResetTimes() {
/* 28 */     return this.resetTimes;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundClearTitlesPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */