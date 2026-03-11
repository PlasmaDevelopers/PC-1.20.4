/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundSetCarriedItemPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   public ServerboundSetCarriedItemPacket(int $$0) {
/* 10 */     this.slot = $$0;
/*    */   }
/*    */   private final int slot;
/*    */   public ServerboundSetCarriedItemPacket(FriendlyByteBuf $$0) {
/* 14 */     this.slot = $$0.readShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeShort(this.slot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 24 */     $$0.handleSetCarriedItem(this);
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 28 */     return this.slot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSetCarriedItemPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */