/*    */ package net.minecraft.network.protocol.game;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.BundlePacket;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundBundlePacket extends BundlePacket<ClientGamePacketListener> {
/*    */   public ClientboundBundlePacket(Iterable<Packet<ClientGamePacketListener>> $$0) {
/*  8 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 13 */     $$0.handleBundlePacket(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBundlePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */