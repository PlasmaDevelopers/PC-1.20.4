/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundRenameItemPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   public ServerboundRenameItemPacket(String $$0) {
/* 10 */     this.name = $$0;
/*    */   }
/*    */   private final String name;
/*    */   public ServerboundRenameItemPacket(FriendlyByteBuf $$0) {
/* 14 */     this.name = $$0.readUtf();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeUtf(this.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 24 */     $$0.handleRenameItem(this);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 28 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundRenameItemPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */