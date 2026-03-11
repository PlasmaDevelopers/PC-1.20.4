/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundSignUpdatePacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   private static final int MAX_STRING_LENGTH = 384;
/*    */   private final BlockPos pos;
/*    */   
/*    */   public ServerboundSignUpdatePacket(BlockPos $$0, boolean $$1, String $$2, String $$3, String $$4, String $$5) {
/* 14 */     this.pos = $$0;
/* 15 */     this.isFrontText = $$1;
/* 16 */     this.lines = new String[] { $$2, $$3, $$4, $$5 };
/*    */   }
/*    */   private final String[] lines; private final boolean isFrontText;
/*    */   public ServerboundSignUpdatePacket(FriendlyByteBuf $$0) {
/* 20 */     this.pos = $$0.readBlockPos();
/* 21 */     this.isFrontText = $$0.readBoolean();
/* 22 */     this.lines = new String[4];
/* 23 */     for (int $$1 = 0; $$1 < 4; $$1++) {
/* 24 */       this.lines[$$1] = $$0.readUtf(384);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 30 */     $$0.writeBlockPos(this.pos);
/* 31 */     $$0.writeBoolean(this.isFrontText);
/* 32 */     for (int $$1 = 0; $$1 < 4; $$1++) {
/* 33 */       $$0.writeUtf(this.lines[$$1]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 39 */     $$0.handleSignUpdate(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 43 */     return this.pos;
/*    */   }
/*    */   
/*    */   public boolean isFrontText() {
/* 47 */     return this.isFrontText;
/*    */   }
/*    */   
/*    */   public String[] getLines() {
/* 51 */     return this.lines;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSignUpdatePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */