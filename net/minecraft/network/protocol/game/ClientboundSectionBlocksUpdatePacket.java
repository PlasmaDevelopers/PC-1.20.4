/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*    */ import it.unimi.dsi.fastutil.shorts.ShortSet;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*    */ 
/*    */ public class ClientboundSectionBlocksUpdatePacket implements Packet<ClientGamePacketListener> {
/*    */   private static final int POS_IN_SECTION_BITS = 12;
/*    */   private final SectionPos sectionPos;
/*    */   private final short[] positions;
/*    */   private final BlockState[] states;
/*    */   
/*    */   public ClientboundSectionBlocksUpdatePacket(SectionPos $$0, ShortSet $$1, LevelChunkSection $$2) {
/* 22 */     this.sectionPos = $$0;
/* 23 */     int $$3 = $$1.size();
/* 24 */     this.positions = new short[$$3];
/* 25 */     this.states = new BlockState[$$3];
/*    */     
/* 27 */     int $$4 = 0;
/* 28 */     for (ShortIterator<Short> shortIterator = $$1.iterator(); shortIterator.hasNext(); ) { short $$5 = ((Short)shortIterator.next()).shortValue();
/* 29 */       this.positions[$$4] = $$5;
/* 30 */       this.states[$$4] = $$2.getBlockState(SectionPos.sectionRelativeX($$5), SectionPos.sectionRelativeY($$5), SectionPos.sectionRelativeZ($$5));
/* 31 */       $$4++; }
/*    */   
/*    */   }
/*    */   
/*    */   public ClientboundSectionBlocksUpdatePacket(FriendlyByteBuf $$0) {
/* 36 */     this.sectionPos = SectionPos.of($$0.readLong());
/* 37 */     int $$1 = $$0.readVarInt();
/* 38 */     this.positions = new short[$$1];
/* 39 */     this.states = new BlockState[$$1];
/*    */     
/* 41 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 42 */       long $$3 = $$0.readVarLong();
/* 43 */       this.positions[$$2] = (short)(int)($$3 & 0xFFFL);
/* 44 */       this.states[$$2] = (BlockState)Block.BLOCK_STATE_REGISTRY.byId((int)($$3 >>> 12L));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 50 */     $$0.writeLong(this.sectionPos.asLong());
/* 51 */     $$0.writeVarInt(this.positions.length);
/*    */     
/* 53 */     for (int $$1 = 0; $$1 < this.positions.length; $$1++) {
/* 54 */       $$0.writeVarLong(Block.getId(this.states[$$1]) << 12L | this.positions[$$1]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 60 */     $$0.handleChunkBlocksUpdate(this);
/*    */   }
/*    */   
/*    */   public void runUpdates(BiConsumer<BlockPos, BlockState> $$0) {
/* 64 */     BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/* 65 */     for (int $$2 = 0; $$2 < this.positions.length; $$2++) {
/* 66 */       short $$3 = this.positions[$$2];
/* 67 */       $$1.set(this.sectionPos.relativeToBlockX($$3), this.sectionPos.relativeToBlockY($$3), this.sectionPos.relativeToBlockZ($$3));
/* 68 */       $$0.accept($$1, this.states[$$2]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSectionBlocksUpdatePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */