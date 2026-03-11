/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.Difficulty;
/*    */ 
/*    */ public class ClientboundChangeDifficultyPacket implements Packet<ClientGamePacketListener> {
/*    */   private final Difficulty difficulty;
/*    */   
/*    */   public ClientboundChangeDifficultyPacket(Difficulty $$0, boolean $$1) {
/* 12 */     this.difficulty = $$0;
/* 13 */     this.locked = $$1;
/*    */   }
/*    */   private final boolean locked;
/*    */   public ClientboundChangeDifficultyPacket(FriendlyByteBuf $$0) {
/* 17 */     this.difficulty = Difficulty.byId($$0.readUnsignedByte());
/* 18 */     this.locked = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeByte(this.difficulty.getId());
/* 24 */     $$0.writeBoolean(this.locked);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 29 */     $$0.handleChangeDifficulty(this);
/*    */   }
/*    */   
/*    */   public boolean isLocked() {
/* 33 */     return this.locked;
/*    */   }
/*    */   
/*    */   public Difficulty getDifficulty() {
/* 37 */     return this.difficulty;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundChangeDifficultyPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */