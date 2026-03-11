/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundSetTimePacket implements Packet<ClientGamePacketListener> {
/*    */   private final long gameTime;
/*    */   
/*    */   public ClientboundSetTimePacket(long $$0, long $$1, boolean $$2) {
/* 11 */     this.gameTime = $$0;
/* 12 */     long $$3 = $$1;
/*    */ 
/*    */     
/* 15 */     if (!$$2) {
/* 16 */       $$3 = -$$3;
/* 17 */       if ($$3 == 0L) {
/* 18 */         $$3 = -1L;
/*    */       }
/*    */     } 
/*    */     
/* 22 */     this.dayTime = $$3;
/*    */   }
/*    */   private final long dayTime;
/*    */   public ClientboundSetTimePacket(FriendlyByteBuf $$0) {
/* 26 */     this.gameTime = $$0.readLong();
/* 27 */     this.dayTime = $$0.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 32 */     $$0.writeLong(this.gameTime);
/* 33 */     $$0.writeLong(this.dayTime);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 38 */     $$0.handleSetTime(this);
/*    */   }
/*    */   
/*    */   public long getGameTime() {
/* 42 */     return this.gameTime;
/*    */   }
/*    */   
/*    */   public long getDayTime() {
/* 46 */     return this.dayTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetTimePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */