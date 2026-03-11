/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundSetCarriedItemPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int slot;
/*    */   
/*    */   public ClientboundSetCarriedItemPacket(int $$0) {
/* 11 */     this.slot = $$0;
/*    */   }
/*    */   
/*    */   public ClientboundSetCarriedItemPacket(FriendlyByteBuf $$0) {
/* 15 */     this.slot = $$0.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeByte(this.slot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 25 */     $$0.handleSetCarriedItem(this);
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 29 */     return this.slot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetCarriedItemPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */