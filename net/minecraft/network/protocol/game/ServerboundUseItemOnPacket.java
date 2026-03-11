/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class ServerboundUseItemOnPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   private final BlockHitResult blockHit;
/*    */   
/*    */   public ServerboundUseItemOnPacket(InteractionHand $$0, BlockHitResult $$1, int $$2) {
/* 14 */     this.hand = $$0;
/* 15 */     this.blockHit = $$1;
/* 16 */     this.sequence = $$2;
/*    */   }
/*    */   private final InteractionHand hand; private final int sequence;
/*    */   public ServerboundUseItemOnPacket(FriendlyByteBuf $$0) {
/* 20 */     this.hand = (InteractionHand)$$0.readEnum(InteractionHand.class);
/* 21 */     this.blockHit = $$0.readBlockHitResult();
/* 22 */     this.sequence = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 27 */     $$0.writeEnum((Enum)this.hand);
/* 28 */     $$0.writeBlockHitResult(this.blockHit);
/* 29 */     $$0.writeVarInt(this.sequence);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 34 */     $$0.handleUseItemOn(this);
/*    */   }
/*    */   
/*    */   public InteractionHand getHand() {
/* 38 */     return this.hand;
/*    */   }
/*    */   
/*    */   public BlockHitResult getHitResult() {
/* 42 */     return this.blockHit;
/*    */   }
/*    */   
/*    */   public int getSequence() {
/* 46 */     return this.sequence;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundUseItemOnPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */