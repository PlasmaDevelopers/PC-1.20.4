/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundClientCommandPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   public ServerboundClientCommandPacket(Action $$0) {
/* 10 */     this.action = $$0;
/*    */   }
/*    */   private final Action action;
/*    */   public ServerboundClientCommandPacket(FriendlyByteBuf $$0) {
/* 14 */     this.action = (Action)$$0.readEnum(Action.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeEnum(this.action);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 24 */     $$0.handleClientCommand(this);
/*    */   }
/*    */   
/*    */   public Action getAction() {
/* 28 */     return this.action;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 32 */     PERFORM_RESPAWN,
/* 33 */     REQUEST_STATS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundClientCommandPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */