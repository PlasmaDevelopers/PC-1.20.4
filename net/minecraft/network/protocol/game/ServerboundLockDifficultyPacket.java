/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundLockDifficultyPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   public ServerboundLockDifficultyPacket(boolean $$0) {
/* 10 */     this.locked = $$0;
/*    */   }
/*    */   private final boolean locked;
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 15 */     $$0.handleLockDifficulty(this);
/*    */   }
/*    */   
/*    */   public ServerboundLockDifficultyPacket(FriendlyByteBuf $$0) {
/* 19 */     this.locked = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeBoolean(this.locked);
/*    */   }
/*    */   
/*    */   public boolean isLocked() {
/* 28 */     return this.locked;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundLockDifficultyPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */