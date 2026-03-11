/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.Difficulty;
/*    */ 
/*    */ public class ServerboundChangeDifficultyPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   public ServerboundChangeDifficultyPacket(Difficulty $$0) {
/* 11 */     this.difficulty = $$0;
/*    */   }
/*    */   private final Difficulty difficulty;
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 16 */     $$0.handleChangeDifficulty(this);
/*    */   }
/*    */   
/*    */   public ServerboundChangeDifficultyPacket(FriendlyByteBuf $$0) {
/* 20 */     this.difficulty = Difficulty.byId($$0.readUnsignedByte());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeByte(this.difficulty.getId());
/*    */   }
/*    */   
/*    */   public Difficulty getDifficulty() {
/* 29 */     return this.difficulty;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundChangeDifficultyPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */