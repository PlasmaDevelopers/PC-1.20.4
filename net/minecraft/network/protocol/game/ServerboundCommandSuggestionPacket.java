/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundCommandSuggestionPacket implements Packet<ServerGamePacketListener> {
/*    */   private final int id;
/*    */   private final String command;
/*    */   
/*    */   public ServerboundCommandSuggestionPacket(int $$0, String $$1) {
/* 12 */     this.id = $$0;
/* 13 */     this.command = $$1;
/*    */   }
/*    */   
/*    */   public ServerboundCommandSuggestionPacket(FriendlyByteBuf $$0) {
/* 17 */     this.id = $$0.readVarInt();
/* 18 */     this.command = $$0.readUtf(32500);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     $$0.writeVarInt(this.id);
/* 24 */     $$0.writeUtf(this.command, 32500);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 29 */     $$0.handleCustomCommandSuggestions(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 33 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getCommand() {
/* 37 */     return this.command;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundCommandSuggestionPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */