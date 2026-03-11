/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundJigsawGeneratePacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   private final BlockPos pos;
/*    */   
/*    */   public ServerboundJigsawGeneratePacket(BlockPos $$0, int $$1, boolean $$2) {
/* 13 */     this.pos = $$0;
/* 14 */     this.levels = $$1;
/* 15 */     this.keepJigsaws = $$2;
/*    */   }
/*    */   private final int levels; private final boolean keepJigsaws;
/*    */   public ServerboundJigsawGeneratePacket(FriendlyByteBuf $$0) {
/* 19 */     this.pos = $$0.readBlockPos();
/* 20 */     this.levels = $$0.readVarInt();
/* 21 */     this.keepJigsaws = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 26 */     $$0.writeBlockPos(this.pos);
/* 27 */     $$0.writeVarInt(this.levels);
/* 28 */     $$0.writeBoolean(this.keepJigsaws);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 33 */     $$0.handleJigsawGenerate(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 37 */     return this.pos;
/*    */   }
/*    */   
/*    */   public int levels() {
/* 41 */     return this.levels;
/*    */   }
/*    */   
/*    */   public boolean keepJigsaws() {
/* 45 */     return this.keepJigsaws;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundJigsawGeneratePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */