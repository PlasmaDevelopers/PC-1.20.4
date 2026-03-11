/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ 
/*    */ public class ServerboundUseItemPacket implements Packet<ServerGamePacketListener> {
/*    */   private final InteractionHand hand;
/*    */   
/*    */   public ServerboundUseItemPacket(InteractionHand $$0, int $$1) {
/* 12 */     this.hand = $$0;
/* 13 */     this.sequence = $$1;
/*    */   }
/*    */   private final int sequence;
/*    */   public ServerboundUseItemPacket(FriendlyByteBuf $$0) {
/* 17 */     this.hand = (InteractionHand)$$0.readEnum(InteractionHand.class);
/* 18 */     this.sequence = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeEnum((Enum)this.hand);
/* 24 */     $$0.writeVarInt(this.sequence);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 29 */     $$0.handleUseItem(this);
/*    */   }
/*    */   
/*    */   public InteractionHand getHand() {
/* 33 */     return this.hand;
/*    */   }
/*    */   
/*    */   public int getSequence() {
/* 37 */     return this.sequence;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundUseItemPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */