/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class ClientboundBlockEventPacket implements Packet<ClientGamePacketListener> {
/*    */   private final BlockPos pos;
/*    */   private final int b0;
/*    */   
/*    */   public ClientboundBlockEventPacket(BlockPos $$0, Block $$1, int $$2, int $$3) {
/* 16 */     this.pos = $$0;
/* 17 */     this.block = $$1;
/* 18 */     this.b0 = $$2;
/* 19 */     this.b1 = $$3;
/*    */   }
/*    */   private final int b1; private final Block block;
/*    */   public ClientboundBlockEventPacket(FriendlyByteBuf $$0) {
/* 23 */     this.pos = $$0.readBlockPos();
/* 24 */     this.b0 = $$0.readUnsignedByte();
/* 25 */     this.b1 = $$0.readUnsignedByte();
/*    */     
/* 27 */     this.block = (Block)$$0.readById((IdMap)BuiltInRegistries.BLOCK);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 32 */     $$0.writeBlockPos(this.pos);
/* 33 */     $$0.writeByte(this.b0);
/* 34 */     $$0.writeByte(this.b1);
/* 35 */     $$0.writeId((IdMap)BuiltInRegistries.BLOCK, this.block);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 40 */     $$0.handleBlockEvent(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 44 */     return this.pos;
/*    */   }
/*    */   
/*    */   public int getB0() {
/* 48 */     return this.b0;
/*    */   }
/*    */   
/*    */   public int getB1() {
/* 52 */     return this.b1;
/*    */   }
/*    */   
/*    */   public Block getBlock() {
/* 56 */     return this.block;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBlockEventPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */