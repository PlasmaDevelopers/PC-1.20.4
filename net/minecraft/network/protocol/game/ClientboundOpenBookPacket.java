/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ 
/*    */ public class ClientboundOpenBookPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public ClientboundOpenBookPacket(InteractionHand $$0) {
/* 11 */     this.hand = $$0;
/*    */   }
/*    */   private final InteractionHand hand;
/*    */   public ClientboundOpenBookPacket(FriendlyByteBuf $$0) {
/* 15 */     this.hand = (InteractionHand)$$0.readEnum(InteractionHand.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeEnum((Enum)this.hand);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 25 */     $$0.handleOpenBook(this);
/*    */   }
/*    */   
/*    */   public InteractionHand getHand() {
/* 29 */     return this.hand;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundOpenBookPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */