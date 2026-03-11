/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundTabListPacket implements Packet<ClientGamePacketListener> {
/*    */   private final Component header;
/*    */   private final Component footer;
/*    */   
/*    */   public ClientboundTabListPacket(Component $$0, Component $$1) {
/* 13 */     this.header = $$0;
/* 14 */     this.footer = $$1;
/*    */   }
/*    */   
/*    */   public ClientboundTabListPacket(FriendlyByteBuf $$0) {
/* 18 */     this.header = $$0.readComponentTrusted();
/* 19 */     this.footer = $$0.readComponentTrusted();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeComponent(this.header);
/* 25 */     $$0.writeComponent(this.footer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 30 */     $$0.handleTabListCustomisation(this);
/*    */   }
/*    */   
/*    */   public Component getHeader() {
/* 34 */     return this.header;
/*    */   }
/*    */   
/*    */   public Component getFooter() {
/* 38 */     return this.footer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundTabListPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */