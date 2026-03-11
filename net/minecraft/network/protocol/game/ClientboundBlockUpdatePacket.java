/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class ClientboundBlockUpdatePacket implements Packet<ClientGamePacketListener> {
/*    */   private final BlockPos pos;
/*    */   
/*    */   public ClientboundBlockUpdatePacket(BlockPos $$0, BlockState $$1) {
/* 16 */     this.pos = $$0;
/* 17 */     this.blockState = $$1;
/*    */   }
/*    */   private final BlockState blockState;
/*    */   public ClientboundBlockUpdatePacket(BlockGetter $$0, BlockPos $$1) {
/* 21 */     this($$1, $$0.getBlockState($$1));
/*    */   }
/*    */   
/*    */   public ClientboundBlockUpdatePacket(FriendlyByteBuf $$0) {
/* 25 */     this.pos = $$0.readBlockPos();
/* 26 */     this.blockState = (BlockState)$$0.readById((IdMap)Block.BLOCK_STATE_REGISTRY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 31 */     $$0.writeBlockPos(this.pos);
/* 32 */     $$0.writeId((IdMap)Block.BLOCK_STATE_REGISTRY, this.blockState);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 37 */     $$0.handleBlockUpdate(this);
/*    */   }
/*    */   
/*    */   public BlockState getBlockState() {
/* 41 */     return this.blockState;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 45 */     return this.pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBlockUpdatePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */