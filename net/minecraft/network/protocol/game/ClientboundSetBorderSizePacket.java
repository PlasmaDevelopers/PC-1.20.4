/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.border.WorldBorder;
/*    */ 
/*    */ public class ClientboundSetBorderSizePacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public ClientboundSetBorderSizePacket(WorldBorder $$0) {
/* 11 */     this.size = $$0.getLerpTarget();
/*    */   }
/*    */   private final double size;
/*    */   public ClientboundSetBorderSizePacket(FriendlyByteBuf $$0) {
/* 15 */     this.size = $$0.readDouble();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeDouble(this.size);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 25 */     $$0.handleSetBorderSize(this);
/*    */   }
/*    */   
/*    */   public double getSize() {
/* 29 */     return this.size;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetBorderSizePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */