/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundSetSubtitleTextPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public ClientboundSetSubtitleTextPacket(Component $$0) {
/* 11 */     this.text = $$0;
/*    */   }
/*    */   private final Component text;
/*    */   public ClientboundSetSubtitleTextPacket(FriendlyByteBuf $$0) {
/* 15 */     this.text = $$0.readComponentTrusted();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeComponent(this.text);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 25 */     $$0.setSubtitleText(this);
/*    */   }
/*    */   
/*    */   public Component getText() {
/* 29 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetSubtitleTextPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */